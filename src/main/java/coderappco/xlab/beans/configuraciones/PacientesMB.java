/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.entidades.CfgPacientes;
import coderappco.xlab.entidades.FacAdministradora;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.facades.CfgPacientesFacade;
import coderappco.xlab.facades.FacAdministradoraFacade;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import coderappco.xlab.utilidades.SessionUtil;
import coderappco.xlab.utilidades.Utilidades;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "pacientesMB")
@SessionScoped
public class PacientesMB extends Controlador implements Serializable {
    
    @EJB
    private CfgClasificacionesFacade clasificacionFacade;
    @EJB
    private CfgPacientesFacade pacienteFacade;
    @EJB
    private FacAdministradoraFacade  administradorFacade;
    
    private String nroIdentificacion;
    private int tipoIdentificacion;
    private String nroIdentificacionAnterior;
    private String nombrePaciente;
    private boolean renderForm;
    private CfgPacientes paciente;
    
    private UploadedFile archivoFirma;
    private UploadedFile archivoFoto;
    private String firmaPorDefecto = "../resources/img/firma.png";
    private String fotoPorDefecto = "../resources/img/img_usuario.png";
    private String urlFirma = firmaPorDefecto;
    private String urlFoto = fotoPorDefecto;
    private boolean fotoTomadaWebCam = false;//saber si la foto se tomo de la webcam
    
    private List<SelectItem> listaTipoIdentificacion;
    private List<SelectItem> listaGenero;
    private List<SelectItem> listaGrupoSanguineo;
    private List<SelectItem> listaEstadoCivil;
    private List<SelectItem> listaOcupacion;
    private List<SelectItem> listaDpto;
    private List<SelectItem> listaMunicipio;
    private List<SelectItem> listaZona;
    
    private List<SelectItem> listaAdministradora;
    
    /**
     * Creates a new instance of PacientesMB
     */
    public PacientesMB() {
    }
    
    @PostConstruct
    public void inicializar(){
        paciente = new CfgPacientes();
        renderForm=false;
        nombrePaciente = "";
        nroIdentificacion ="";
        nroIdentificacionAnterior="";
        this.urlFirma = firmaPorDefecto;
        this.urlFoto = fotoPorDefecto;
        this.fotoTomadaWebCam = false;
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            if(!nroIdentificacionAnterior.equals(paciente.getIdentificacion())){
                //validamos si la cedula ya existe
                CfgPacientes c = pacienteFacade.buscarPorIdentificacion(paciente.getIdentificacion());
                if(c==null){
                    pacienteFacade.edit(paciente);
                    SessionUtil.addInfoMessage("Guardado", "Datos Actualizados");
                    inicializarVariables();
                }else{
                    SessionUtil.addErrorMessage("Error al guardar", "Este número de cédula ya esta asociado");
                }
            }else{
                pacienteFacade.edit(paciente);
                    SessionUtil.addInfoMessage("Guardado", "Datos Actualizados");
                    inicializarVariables();
            }
        } catch (Exception e) {
            e.printStackTrace();
            SessionUtil.addErrorMessage("Error al guardar", e.toString());
        }
    }

    @Override
    public void inicializarVariables() {
        paciente = new CfgPacientes();
        renderForm=false;
        nombrePaciente = "";
        nroIdentificacion ="";
        this.urlFirma = firmaPorDefecto;
        this.urlFoto = fotoPorDefecto;
        this.fotoTomadaWebCam = false;
        nroIdentificacionAnterior="";
    }

    @Override
    public void cancelar() {
        inicializarVariables();
    }

    @Override
    public void consultar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<String> autocompletarDiagnostico(String txt) {//retorna una lista con los diagnosticos que contengan el parametro txt
        if (txt != null && txt.length() > 2) {
            return pacienteFacade.autocompletarPaciente(txt);
        } else {
            return null;
        }
    }
    
    public void validarDiagnostico(SelectEvent event){
        try {
            CfgPacientes c = pacienteFacade.buscarPorNombre(event.getObject().toString());
            if(c!=null){
                paciente = c;
                FacAdministradora administradora = new FacAdministradora();
                administradora.setIdAdministradora(0);
                if(paciente.getIdAdministradora()==null)paciente.setIdAdministradora(administradora);
                cargarMunicipios();
                nombrePaciente = paciente.getPrimerNombre()+" "+paciente.getSegundoNombre()+ " "+paciente.getPrimerApellido()+" "+paciente.getSegundoApellido();
                nroIdentificacion = paciente.getIdentificacion();
                nroIdentificacionAnterior = paciente.getIdentificacion();
                tipoIdentificacion = paciente.getTipoIdentificacion().getId();
                //Cargamos fotos del paciente
                if(paciente.getFirma()!=null){
                    Utilidades.moverArchivo(SessionUtil.getUrlImagen()+paciente.getFirma().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+paciente.getFirma().getNombreEnServidor(),false);
                    urlFirma = "../imagenesOpenmedical/"+paciente.getFirma().getNombreEnServidor();
                }else{
                    urlFirma = this.firmaPorDefecto;
                }
                if(paciente.getFoto()!=null){
                    Utilidades.moverArchivo(SessionUtil.getUrlImagen()+paciente.getFoto().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+paciente.getFoto().getNombreEnServidor(),false);
                    urlFoto = "../imagenesOpenmedical/"+paciente.getFoto().getNombreEnServidor();
                }else{
                    urlFoto = this.fotoPorDefecto;
                }
                renderForm=true;
             }
        } catch (Exception e) {
        }
    }
    
    public void validarIdentificacion() {//verifica si existe la identificacion de lo contrario abre un dialogo para seleccionar el paciente de una tabla
        paciente = pacienteFacade.findPacienteByTipIden(tipoIdentificacion, nroIdentificacion);
        if(paciente==null){
            paciente = new CfgPacientes();
            SessionUtil.addWarningMessage("Paciente no existe", "No se encontró registro");
        }else{
            FacAdministradora administradora = new FacAdministradora();
            administradora.setIdAdministradora(0);
            if(paciente.getIdAdministradora()==null)paciente.setIdAdministradora(administradora);
            cargarMunicipios();
            nombrePaciente = paciente.getPrimerNombre()+" "+paciente.getSegundoNombre()+ " "+paciente.getPrimerApellido()+" "+paciente.getSegundoApellido();
            nroIdentificacion = paciente.getIdentificacion();
            nroIdentificacionAnterior = paciente.getIdentificacion();
            tipoIdentificacion = paciente.getTipoIdentificacion().getId();
            //Cargamos fotos del paciente
            if(paciente.getFirma()!=null){
                Utilidades.moverArchivo(SessionUtil.getUrlImagen()+paciente.getFirma().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+paciente.getFirma().getNombreEnServidor(),false);
                urlFirma = "../imagenesOpenmedical/"+paciente.getFirma().getNombreEnServidor();
            }else{
                urlFirma = this.firmaPorDefecto;
            }
            if(paciente.getFoto()!=null){
                Utilidades.moverArchivo(SessionUtil.getUrlImagen()+paciente.getFoto().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+paciente.getFoto().getNombreEnServidor(),false);
                urlFoto = "../imagenesOpenmedical/"+paciente.getFoto().getNombreEnServidor();
            }else{
                urlFoto = this.fotoPorDefecto;
            }
            renderForm=true;
            
        }
    }
    
    public void uploadFirma(FileUploadEvent event) {
        try {
            archivoFirma = event.getFile();
            String nombreImg = "firmaUsuario" //es firma de usuario
                    + SessionUtil.getUserLog() //diferenciar el usuario actual
                    + archivoFirma.getFileName().substring(archivoFirma.getFileName().lastIndexOf("."), archivoFirma.getFileName().length());//colocar extension

            if (uploadArchivo(archivoFirma, SessionUtil.getUrlPath()+"imagenesOpenmedical/"+ nombreImg)) {
                urlFirma = "../imagenesOpenmedical/" + nombreImg;
            } else {
                urlFirma = firmaPorDefecto;
                archivoFirma = null;
            }
        } catch (Exception ex) {
            System.out.println("Error 20 en " + this.getClass().getName() + ":" + ex.toString());
        }
    }

    public void uploadFoto(FileUploadEvent event) {
        try {
            archivoFoto = event.getFile();
            String nombreImg = "fotoUsuario" //es foto de usuario
                    + SessionUtil.getUserLog() //diferenciar el usuario actual
                    + archivoFoto.getFileName().substring(archivoFoto.getFileName().lastIndexOf("."), archivoFoto.getFileName().length());//colocar extension
            if (uploadArchivo(archivoFoto, SessionUtil.getUrlPath()+"imagenesOpenmedical/"+ nombreImg)) {
                urlFoto = "../imagenesOpenmedical/" +  nombreImg;
                fotoTomadaWebCam = false;
            } else {
                urlFoto = fotoPorDefecto;
                archivoFoto = null;
                fotoTomadaWebCam = false;
            }
        } catch (Exception ex) {
            System.out.println("Error 20 en " + this.getClass().getName() + ":" + ex.toString());
        }
    }

    public void tomarFoto(CaptureEvent captureEvent) {
        byte[] data = captureEvent.getData();
        FileImageOutputStream imageOutput;
        archivoFoto = null;
        try {
            File imagen = new File(SessionUtil.getUrlPath()+"imagenesOpenmedical/"+ "fotoUsuario" + SessionUtil.getUserLog()+ ".png"/*loginMB.getUrltmp() + "fotoUsuario" + loginMB.getUsuarioActual().getIdUsuario() + ".png"*/);
            if (imagen.exists()) {
                imagen.delete();
                imagen = new File(SessionUtil.getUrlPath()+"imagenesOpenmedical/"+ "fotoUsuario" + SessionUtil.getUserLog()+ ".png");
            }
            imageOutput = new FileImageOutputStream(imagen);
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            fotoTomadaWebCam = true;
            urlFoto = "../imagenesOpenmedical/" +  "fotoUsuario" + SessionUtil.getUserLog() + ".png";
            RequestContext.getCurrentInstance().update("frmPaciente");
        } catch (IOException e) {
            urlFoto = fotoPorDefecto;
            fotoTomadaWebCam = false;
            System.out.println("Error 02: " + e.getMessage());//imprimirMensaje("Error 02", e.getMessage(), FacesMessage.SEVERITY_ERROR);            
        }
    }

    public boolean uploadArchivo(UploadedFile archivoCargado, String rutaDestino) {
        File fichero = new java.io.File(rutaDestino);
        if (fichero.exists()) {//si existe se borra
            fichero.delete();
        }
        fichero = new java.io.File(rutaDestino);
        try (FileOutputStream fileOutput = new FileOutputStream(fichero)) {
            InputStream inputStream = archivoCargado.getInputstream();
            byte[] buffer = new byte[1024];
            int bufferLength;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            
            
            inputStream.close();
            
        } catch (Exception e) {
            System.out.println("Error 01: " + e.getMessage());
            return false;
        }
        return true;
    }

    
    public void cargarMunicipios() {
        listaMunicipio = new ArrayList<>();
        if (paciente.getDepartamento().getId() != 0) {
            List<CfgClasificaciones> listaM = clasificacionFacade.buscarMunicipioPorDepartamento(clasificacionFacade.find(paciente.getDepartamento().getId()).getCodigo());
            listaMunicipio.clear();
            int i = 0;
            for (CfgClasificaciones mun : listaM) {
                if(i==0)paciente.setMunicipio(mun);
                listaMunicipio.add(new SelectItem(mun.getId(), mun.getDescripcion()));
                i++;
            }
            
        }
    }
    public List<SelectItem> getListaTipoIdentificacion() {
        if(listaTipoIdentificacion==null){
            listaTipoIdentificacion  = new ArrayList<>();
            
          List<CfgClasificaciones> lstTi = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.TipoIdentificacion.toString());  
          int i=0;
          for(CfgClasificaciones cfg:lstTi){
              if(i==0)tipoIdentificacion=cfg.getId();
               listaTipoIdentificacion.add(new SelectItem(cfg.getId(),cfg.getObservacion()));
            i++;
          }
        }
            
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }
    
    public List<SelectItem> getListaGenero() {
        if(listaGenero==null){
            listaGenero = new ArrayList();
            List<CfgClasificaciones> lstGenero = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.Genero.toString());
            for(CfgClasificaciones g:lstGenero){
                listaGenero.add(new SelectItem(g.getId(),g.getDescripcion()));
            }
        }
        return listaGenero;
    }

    public void setListaGenero(List<SelectItem> listaGenero) {
        this.listaGenero = listaGenero;
    }

    public List<SelectItem> getListaGrupoSanguineo() {
        if(listaGrupoSanguineo==null){
            listaGrupoSanguineo = new ArrayList();
            List<CfgClasificaciones> lstGrupoSanguineo = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.GrupoSanguineo.toString());
            for(CfgClasificaciones g:lstGrupoSanguineo){
                listaGrupoSanguineo.add(new SelectItem(g.getId(),g.getDescripcion()));
            }
        }
        return listaGrupoSanguineo;
    }

    public void setListaGrupoSanguineo(List<SelectItem> listaGrupoSanguineo) {
        this.listaGrupoSanguineo = listaGrupoSanguineo;
    }

    public List<SelectItem> getListaEstadoCivil() {
        if(listaEstadoCivil==null){
            listaEstadoCivil = new ArrayList<>();
            List<CfgClasificaciones> lstEstado = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.EstadoCivil.toString());
            for(CfgClasificaciones e:lstEstado){
                listaEstadoCivil.add(new SelectItem(e.getId(),e.getDescripcion()));
            }
        }
        return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List<SelectItem> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    public List<SelectItem> getListaOcupacion() {
        if(listaOcupacion==null){
            listaOcupacion = new ArrayList<>();
            listaOcupacion.add(new SelectItem(0,"Seleccione Ocupaciòn"));
            List<CfgClasificaciones> lstOcupacion = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.Ocupacion.toString());
            for(CfgClasificaciones o:lstOcupacion){
                listaOcupacion.add(new SelectItem(o.getId(),o.getDescripcion()));
            }
        }
        return listaOcupacion;
    }

    public void setListaOcupacion(List<SelectItem> listaOcupacion) {
        this.listaOcupacion = listaOcupacion;
    }

    public List<SelectItem> getListaDpto() {
        if(listaDpto==null){
            listaDpto = new ArrayList<>();
            listaDpto.add(new SelectItem(0,"Seleccione Departamento"));
            List<CfgClasificaciones> lstDpto = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.DPTO.toString());
            for(CfgClasificaciones d:lstDpto){
                listaDpto.add(new SelectItem(d.getId(),d.getDescripcion()));
            }
        }
        return listaDpto;
    }

    public void setListaDpto(List<SelectItem> listaDpto) {
        this.listaDpto = listaDpto;
    }

    public List<SelectItem> getListaMunicipio() {
        return listaMunicipio;
    }

    public void setListaMunicipio(List<SelectItem> listaMunicipio) {
        this.listaMunicipio = listaMunicipio;
    }

    public List<SelectItem> getListaZona() {
        if(listaZona==null){
            listaZona = new ArrayList<>();
            List<CfgClasificaciones> lstZona = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.Zona.toString());
            for(CfgClasificaciones z:lstZona){
                listaZona.add(new SelectItem(z.getId(),z.getDescripcion()));
            }
        }
        return listaZona;
    }

    public void setListaZona(List<SelectItem> listaZona) {
        this.listaZona = listaZona;
    }
    public List<SelectItem> getListaAdministradora() {
        if(listaAdministradora== null){
            listaAdministradora = new ArrayList<>();
            List<FacAdministradora> lst = administradorFacade.findAll();
            for(FacAdministradora cf: lst){
                listaAdministradora.add(new SelectItem(cf.getIdAdministradora(),cf.getCodigoAdministradora() +" - "+cf.getRazonSocial()));
            }
        }
        return listaAdministradora;
    }

    public void setListaAdministradora(List<SelectItem> listaAdministradora) {
        this.listaAdministradora = listaAdministradora;
    }

    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public int getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(int tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public CfgPacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(CfgPacientes paciente) {
        this.paciente = paciente;
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public UploadedFile getArchivoFirma() {
        return archivoFirma;
    }

    public void setArchivoFirma(UploadedFile archivoFirma) {
        this.archivoFirma = archivoFirma;
    }

    public UploadedFile getArchivoFoto() {
        return archivoFoto;
    }

    public void setArchivoFoto(UploadedFile archivoFoto) {
        this.archivoFoto = archivoFoto;
    }

    public String getFirmaPorDefecto() {
        return firmaPorDefecto;
    }

    public void setFirmaPorDefecto(String firmaPorDefecto) {
        this.firmaPorDefecto = firmaPorDefecto;
    }

    public String getFotoPorDefecto() {
        return fotoPorDefecto;
    }

    public void setFotoPorDefecto(String fotoPorDefecto) {
        this.fotoPorDefecto = fotoPorDefecto;
    }

    public String getUrlFirma() {
        return urlFirma;
    }

    public void setUrlFirma(String urlFirma) {
        this.urlFirma = urlFirma;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean isFotoTomadaWebCam() {
        return fotoTomadaWebCam;
    }

    public void setFotoTomadaWebCam(boolean fotoTomadaWebCam) {
        this.fotoTomadaWebCam = fotoTomadaWebCam;
    }

}
