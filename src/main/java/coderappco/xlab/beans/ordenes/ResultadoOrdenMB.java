/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.ordenes;

import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.entidades.CfgCorreo;
import coderappco.xlab.entidades.CfgPacientes;
import coderappco.xlab.entidades.XlabOrden;
import coderappco.xlab.entidades.XlabOrdenEstudiosPruebas;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.facades.CfgCorreoFacade;
import coderappco.xlab.facades.XlabOrdenEstudiosPruebasFacade;
import coderappco.xlab.facades.XlabOrdenFacade;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import coderappco.xlab.utilidades.Constante;
import coderappco.xlab.utilidades.DBConnector;
import coderappco.xlab.utilidades.SessionUtil;
import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "resultadoOrdenMB")
@SessionScoped
public class ResultadoOrdenMB implements Serializable {

    private Logger logger = Logger.getRootLogger();
    @EJB
    private XlabOrdenFacade  ordenFacade;
    @EJB
    private XlabOrdenEstudiosPruebasFacade ordenEstudioPruebaFacade;
    @EJB
    private CfgClasificacionesFacade clasificacionFacade;
    @EJB
    private CfgCorreoFacade correoFacade;
    
    private XlabOrden orden;
    private CfgPacientes pacientes;
    private CfgCorreo correo;
    
    private String identificacionPaciente;
    private int tipoIdentificacion;
    private boolean resultadosConfirmado;
    private boolean renderInfo;
    private String emails;
    
    private List<XlabOrdenEstudiosPruebas> listaPruebas;
    private List<SelectItem> listaTipoIdentificacion;
    /**
     * Creates a new instance of ResultadoOrdenMB
     */
    public ResultadoOrdenMB(){
        
    }
    @PostConstruct
    public void inicializar(){
        orden = new XlabOrden();
        pacientes = new CfgPacientes();
        renderInfo = false;
        identificacionPaciente="";
        resultadosConfirmado =true;
        correo = correoFacade.find(1);
        emails="";
    }
    public void cargarOrden(){
        if(orden.getNroOrden()!=null){
         orden = ordenFacade.getOrdenXNro(orden.getNroOrden());
         cargarNro();
        }
    } 
    public void validarIdentificacion() {
        if(identificacionPaciente!=null){
            orden = ordenFacade.getOrdenXNro(identificacionPaciente, tipoIdentificacion);
            cargarNro();
        }
    }

    private void cargarNro(){
        if(orden!=null){
             if(!orden.getEstado().equals(Constante.ESTADO_ELIMINADO)){
                 pacientes = orden.getPacienteId();
                 identificacionPaciente = pacientes.getIdentificacion();
                 listaPruebas = ordenEstudioPruebaFacade.getPruebasXOrden(orden.getId());
                 renderInfo=true;
                 validarResultados();
                 RequestContext.getCurrentInstance().update(":frmOrden");
             }else{
                 orden = new XlabOrden();
                 SessionUtil.addInfoMessage("Orden", "Este Número de Orden se encuentra eliminado");
             }
         }else{
             orden = new XlabOrden();
             SessionUtil.addInfoMessage("No hay registro", "No sé econtró número de orden");
         }
    }
    
    private void validarResultados(){
        for(XlabOrdenEstudiosPruebas xo:listaPruebas){
            if(!xo.getEstado().equals("C")){
                resultadosConfirmado = false;
                break;
            }
        }
        if(!resultadosConfirmado){
            SessionUtil.addWarningMessage("Resultados", "Resultados no confirmados");
        }
    }
    public void imprimir(){
        if (resultadosConfirmado) {
            try {
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse httpServletResponse = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
            httpServletResponse.setContentType("application/pdf");
                ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String ruta ;
            ruta = servletContext.getRealPath("/informes/resultados/r_resultados_orden.jasper");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_EMPRESA", SessionUtil.getEmpresa());
            parametros.put("P_ORDEN", orden.getNroOrden());
            try{
                Connection con = DBConnector.getInstance().getConnection();
                JasperPrint jasperPrint = JasperFillManager.fillReport(ruta, parametros, con);
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                FacesContext.getCurrentInstance().responseComplete();
                if(con!=null)con.close();
                DBConnector.getInstance().closeConnection();
            }catch(Exception e){
                logger.error("Error en la clase "+ ResultadoOrdenMB.class.getName() + ", mensaje: " + e.getMessage(),e);
            }
        }
        } catch (Exception e) {
            logger.error("Error en la clase "+ ResultadoOrdenMB.class.getName() + ", mensaje: " + e.getMessage(),e);
        }
        }else {
            SessionUtil.addWarningMessage("Resultados", "Resultados no confirmados");
        }
    }
    public void email(){
        if (resultadosConfirmado) {
            if (emails != null) {
                if (!emails.equals("")) {
                    if (correo != null) {
                        if (!correo.getEmail().equals("")) {
                            JasperReport jasperReport;
                            JasperPrint jasperPrint;  
                            try {
                                Connection con = DBConnector.getInstance().getConnection();
                                FacesContext facesContext = FacesContext.getCurrentInstance();
                                ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
                                File  in= new File(servletContext.getRealPath("/informes/resultados/r_resultados_orden.jasper"));
                                Map<String, Object> parametros = new HashMap<>();
                                parametros.put("P_EMPRESA", SessionUtil.getEmpresa());
                                parametros.put("P_ORDEN", orden.getNroOrden());
                                jasperReport=(JasperReport)JRLoader.loadObject(in);
                                 jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,con);
                                //se crea el archivo PDF
                                JasperExportManager.exportReportToPdfFile( jasperPrint, SessionUtil.getUrlPath()+"/reporte_"+pacientes.getIdentificacion()+".pdf");
                                Properties props = System.getProperties();  
                                MimeMultipart multipart = new MimeMultipart();
                                props.put("mail.smtp.auth", "true");
                                props.put("mail.smtp.starttls.enable", "true");
                                props.put("mail.smtp.host", correo.getHost());
                                props.put("mail.smtp.ssl.trust", correo.getHost());
                                props.put("mail.smtp.port", correo.getPort());
                                //Recorremos los email a enviar
                                String[] destinos = emails.split(",");
                                for (String destino : destinos) {
                                    Authenticator auth = new MyAuthenticator();
                                    Session smtpSession = Session.getInstance(props, auth);
                                    smtpSession.setDebug(false);
                                    MimeMessage message = new MimeMessage(smtpSession);
                                    message.setFrom(new InternetAddress(correo.getEmail(),"XLAB - Resultados Laboratorios["+pacientes.getPrimerNombre()+" "+pacientes.getSegundoNombre()+" "+pacientes.getPrimerApellido()+" "+pacientes.getSegundoApellido()+"]"));
                                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
                                    final String encoding = "UTF-8";
                                    BodyPart adjunto = new MimeBodyPart();
                                    adjunto.setDataHandler(new DataHandler(new FileDataSource(SessionUtil.getUrlPath()+"/reporte_"+pacientes.getIdentificacion()+".pdf")));
                                    adjunto.setFileName("resultado_"+pacientes.getIdentificacion()+".pdf");
                                    message.setSubject("XLAB - Resultados Laboratorios["+pacientes.getPrimerNombre()+" "+pacientes.getSegundoNombre()+" "+pacientes.getPrimerApellido()+" "+pacientes.getSegundoApellido()+"]", encoding);
                                    BodyPart mbp = new MimeBodyPart();
                                    mbp.setContent("Buen día, adjunto econtrará informe de resultados de laboratorio", "text/html"); 
                                    multipart.addBodyPart(mbp);
                                    multipart.addBodyPart(adjunto);
                                    message.setContent(multipart);
                                    Transport.send(message);
                                }
                                File pdf = new File(SessionUtil.getUrlPath()+"/reporte_"+pacientes.getIdentificacion()+".pdf");
                                pdf.delete();
                                
                                SessionUtil.addInfoMessage("Enviado", "Enviado Correctamente");
                            } catch (Exception e) {
                                logger.error("Error en la clase "+ ResultadoOrdenMB.class.getName() + ", mensaje: " + e.getMessage(),e);
                            }
                        } else {
                            SessionUtil.addWarningMessage("Correo no enviado", "No hay correo configurado");
                        }
                    } else {
                        SessionUtil.addWarningMessage("Correo no enviado", "No hay correo configurado");
                    }
                } else {
                    SessionUtil.addWarningMessage("Correo no enviado", "El paciente no tiene correo");
                }
            } else {
                SessionUtil.addWarningMessage("Correo no enviado", "El paciente no tiene correo");
            }
        } else {
            SessionUtil.addWarningMessage("Resultados", "Resultados no confirmados");
        }
    }
    public XlabOrden getOrden() {
        return orden;
    }

    public void setOrden(XlabOrden orden) {
        this.orden = orden;
    }

    public CfgPacientes getPacientes() {
        return pacientes;
    }

    public void setPacientes(CfgPacientes pacientes) {
        this.pacientes = pacientes;
    }

    public String getIdentificacionPaciente() {
        return identificacionPaciente;
    }

    public void setIdentificacionPaciente(String identificacionPaciente) {
        this.identificacionPaciente = identificacionPaciente;
    }

    public List<XlabOrdenEstudiosPruebas> getListaPruebas() {
        return listaPruebas;
    }

    public void setListaPruebas(List<XlabOrdenEstudiosPruebas> listaPruebas) {
        this.listaPruebas = listaPruebas;
    }

    public int getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(int tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public List<SelectItem> getListaTipoIdentificacion() {
        if(listaTipoIdentificacion==null){
            listaTipoIdentificacion  = new ArrayList<>();
            
          List<CfgClasificaciones> lstTi = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.TipoIdentificacion.toString());  
          for(CfgClasificaciones cfg:lstTi){
            listaTipoIdentificacion.add(new SelectItem(cfg.getId(),cfg.getObservacion()));
          }
        }
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public boolean isRenderInfo() {
        return renderInfo;
    }

    public void setRenderInfo(boolean renderInfo) {
        this.renderInfo = renderInfo;
    }
    private class MyAuthenticator extends Authenticator {  
        public PasswordAuthentication getPasswordAuthentication() {  
            String username = "miguel.arango@arcosoft.co";  
            String password = "(mab315)";  
       
            return new PasswordAuthentication(username, password);  
        }  
    }
}
