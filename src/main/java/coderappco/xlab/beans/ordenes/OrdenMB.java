/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.ordenes;

import coderappco.xlab.beans.seguridad.LoginMB;
import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.entidades.CfgEmpresa;
import coderappco.xlab.entidades.CfgImagenes;
import coderappco.xlab.entidades.CfgPacientes;
import coderappco.xlab.entidades.CfgUsuarios;
import coderappco.xlab.entidades.FacAdministradora;
import coderappco.xlab.entidades.FacConsecutivo;
import coderappco.xlab.entidades.XlabConsecutivos;
import coderappco.xlab.entidades.XlabEstudio;
import coderappco.xlab.entidades.XlabOrden;
import coderappco.xlab.entidades.XlabOrdenEstudioPruebaResultados;
import coderappco.xlab.entidades.XlabOrdenEstudiosPruebas;
import coderappco.xlab.entidades.XlabPrueba;
import coderappco.xlab.entidades.XlabPruebaReferencia;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.facades.CfgDiagnosticoFacade;
import coderappco.xlab.facades.CfgImagenesFacade;
import coderappco.xlab.facades.CfgPacientesFacade;
import coderappco.xlab.facades.CfgUsuariosFacade;
import coderappco.xlab.facades.FacAdministradoraFacade;
import coderappco.xlab.facades.FacConsecutivoFacade;
import coderappco.xlab.facades.XlabConsecutivosFacade;
import coderappco.xlab.facades.XlabEstudioFacade;
import coderappco.xlab.facades.XlabOrdenEstudioPruebaResultadosFacade;
import coderappco.xlab.facades.XlabOrdenEstudiosPruebasFacade;
import coderappco.xlab.facades.XlabOrdenFacade;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import coderappco.xlab.utilidades.Constante;
import coderappco.xlab.utilidades.DBConnector;
import coderappco.xlab.utilidades.SessionUtil;
import coderappco.xlab.utilidades.Utilidades;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "ordenMB")
@SessionScoped
public class OrdenMB extends Controlador implements Serializable {

    @EJB
    private CfgClasificacionesFacade clasificacionFacade;
    @EJB
    private CfgPacientesFacade pacienteFacade;
    @EJB
    private XlabConsecutivosFacade consecutivoFacade;
    @EJB
    private CfgUsuariosFacade usuarioFacade;
    @EJB
    private CfgDiagnosticoFacade  diagnosticoFacade;
    @EJB
    private XlabEstudioFacade estudioFacade;
    @EJB
    private FacAdministradoraFacade  administradorFacade;
    @EJB
    private XlabOrdenFacade  ordenFacade;
    @EJB
    private XlabOrdenEstudiosPruebasFacade ordenEstudioPruebaFacade;
    @EJB
    private XlabOrdenEstudioPruebaResultadosFacade resultadoCompuesto;
    @EJB
    private CfgImagenesFacade imagenesFacade;
    
    @Resource
    private UserTransaction utx;
    
    
    private List<CfgClasificaciones> listaTipoIdentificacion;
    private List<CfgClasificaciones> listaGenero;
    private List<CfgClasificaciones> listaGrupoSanguineo;
    private List<CfgClasificaciones> listaEstadoCivil;
    private List<CfgClasificaciones> listaOcupacion;
    private List<CfgClasificaciones> listaDpto;
    private List<CfgClasificaciones> listaMunicipio;
    private List<CfgClasificaciones> listaZona;
    
    private List<SelectItem> listaAdministradora;
    private List<SelectItem> listaMedicos;
    private List<SelectItem> listaServicios;
    private List<SelectItem>  listaAreas;
    
    private List<XlabEstudio> listaEstudio;
    private List<XlabEstudio> listaEstudioSeleccionados;
    private List<XlabOrdenEstudiosPruebas> listaPruebas;
    private CfgPacientes pacientes;
    
    private XlabOrden orden;
    private XlabEstudio estudioSeleccionado;
    private List<XlabOrdenEstudiosPruebas> pruebaSeleccionada;
    private XlabOrdenEstudiosPruebas pruebaValidada;
    private List<XlabOrdenEstudioPruebaResultados> resultadoPruebaSeleccionada;
    
    private String identificacionPaciente;
    private boolean lecturaOrden;
    private boolean lecturaEmbarazo;
    private UploadedFile archivoFirma;
    private UploadedFile archivoFoto;
    private String firmaPorDefecto = "../resources/img/firma.png";
    private String fotoPorDefecto = "../resources/img/img_usuario.png";
    private String urlFirma = firmaPorDefecto;
    private String urlFoto = fotoPorDefecto;
    private boolean fotoTomadaWebCam = false;//saber si la foto se tomo de la webcam
    private String diagnostico;
    private String estudio;
    private String edad;
    private boolean renderAccion;
    private int tabIndex;
    private CfgUsuarios usuarioActual;
    private XlabPruebaReferencia x1 ;
    private XlabPruebaReferencia x2 ;
    private XlabPruebaReferencia x3 ;
    private int areaId;
    private boolean renderDatosAlfanumericos;
    private boolean renderDatosCompuestos;
    private String observaciones;
    private String datosPrueba;
    private String labelDatosPrueba;
    private Double c1;
    private Double c2;
    private Double c3;
    private Double c4;
    private Double c5;
    private Double c6;
    private Double c7;
    private Double c8;
    private boolean disableBtnEdit;
    private boolean disableBtnClarear;
    private boolean disableBtnEvolutivo;
    private boolean disableBtnConfirmar;
    private boolean disableBtnValidar;
    private String valoresPruebaCompuesta;
    private boolean disableObservacionesPrueba;
    private boolean disableCampoPrueba;
    private boolean disableCampoPruebaCompuesta;
    private boolean disableEmbarazo;
    
    private int tipoIdentificacion;
    private int genero;
    private int rh;
    private int estadoCivil;
    private int dpto;
    private int mun;
    private int zona;
    private int ocupacion;
    private LineChartModel lineModelEvolutivo;
    public OrdenMB() {
    }
    @PostConstruct
    public void inicializar(){
        listas();
        listaPruebas = new ArrayList<>();
        pacientes =new CfgPacientes();
        identificacionPaciente="";
        orden = new XlabOrden();
        CfgUsuarios usuario = new CfgUsuarios();
        usuario.setIdUsuario(0);
        orden.setMedicoId(usuario);
        listaEstudioSeleccionados = new ArrayList<>();
        listaMunicipio = new ArrayList<>();
        lecturaOrden = false;
        edad = "";
        diagnostico="";
        estudio  ="";
        firmaPorDefecto = "../resources/img/firma.png";
        fotoPorDefecto = "../resources/img/img_usuario.png";
        lecturaEmbarazo= false;
        renderAccion = true;
        tabIndex = 0;
        usuarioActual = usuarioFacade.find(SessionUtil.getUserLog().intValue());
        x1 = new XlabPruebaReferencia();
        x1.setTipo("M");
        x1.setNombreTipo("Mujeres");
        x2 = new XlabPruebaReferencia();
        x2.setTipo("H");
        x2.setNombreTipo("Hombre");
        x3 = new XlabPruebaReferencia();
        x3 = new XlabPruebaReferencia();
        x3.setTipo("N");
        x3.setNombreTipo("Niños");
        pruebaSeleccionada = new ArrayList<>();
        disableBtnEdit =true;
        disableBtnClarear=true;
        disableBtnEvolutivo=true;
        disableBtnConfirmar=true;
        disableBtnValidar=true;
        renderDatosAlfanumericos = false;
        renderDatosCompuestos = false;
        labelDatosPrueba = "";
        c1=0d;
        c2=0d;
        c3=0d;
        c4=0d;
        c5=0d;
        c6=0d;
        c7=0d;
        c8=0d;
        resultadoPruebaSeleccionada = new ArrayList<>();
        disableObservacionesPrueba = false;
        disableCampoPrueba = false;
        disableCampoPruebaCompuesta = false;
        lineModelEvolutivo = new LineChartModel();
        observaciones="";
        disableEmbarazo = true;
        ChartSeries boys = new ChartSeries();
                    boys.setLabel("Boys");
                    boys.set("2004", 120);
                    boys.set("2005", 100);
                    boys.set("2006", 44);
                    boys.set("2007", 150);
                    boys.set("2008", 25);
                    LineChartModel model = new LineChartModel();
                    model.addSeries(boys);
                    lineModelEvolutivo = model;
                    lineModelEvolutivo.setTitle("Category Chart");
        lineModelEvolutivo.setLegendPosition("e");
        lineModelEvolutivo.setShowPointLabels(true);
        lineModelEvolutivo.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        Axis yAxis = lineModelEvolutivo.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    private void listas(){
        listaEstadoCivil = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.EstadoCivil.toString());
        listaOcupacion = new ArrayList<>();// clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.Ocupacion.toString());
        listaDpto = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.DPTO.toString());
        listaZona = new ArrayList<>();//clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.Zona.toString());
        listaTipoIdentificacion  =clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.TipoIdentificacion.toString());  
        listaGenero = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.Genero.toString());
        listaGrupoSanguineo = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.GrupoSanguineo.toString());
    }
    @Override
    public void nuevo() {
        inicializarVariables();
    }
    
    private boolean validarDatos(){
        if(identificacionPaciente.equals("")){
            imprimirMensaje("Error al guardar", "Digite número identificación paciente", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(pacientes.getIdAdministradora().getIdAdministradora()==null){
            imprimirMensaje("Error al guardar", "Seleccione administradora", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(pacientes.getIdAdministradora().getIdAdministradora()==0){
            imprimirMensaje("Error al guardar", "Seleccione administradora", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(pacientes.getPrimerNombre().equals("")){
            imprimirMensaje("Error al guardar", "Digite Primer Nombre paciente", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(pacientes.getPrimerApellido().equals("")){
            imprimirMensaje("Error al guardar", "Digite Primer Apellido paciente", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(pacientes.getFechaNacimiento()==null){
            imprimirMensaje("Error al guardar", "Seleccione fecha nacimiento paciente", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(dpto==0){
            imprimirMensaje("Error al guardar", "Seleccione departamento", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(mun==0){
            imprimirMensaje("Error al guardar", "Seleccione municipio", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(pacientes.getDireccion().equals("")){
            imprimirMensaje("Error al guardar", "Digite dirección", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(orden.getNroOrden().equals("")){
            imprimirMensaje("Error al guardar", "Digite Número de Orden", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(orden.getOrigenId().getIdAdministradora()==null){
            imprimirMensaje("Error al guardar", "Seleccione Origen", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        if(orden.getOrigenId().getIdAdministradora()==0){
            imprimirMensaje("Error al guardar", "Seleccione Origen", FacesMessage.SEVERITY_ERROR);
            return false;
        }
        
        return true;
    }

    @Override
    public void guardar() {
        try {
            if (validarDatos()) {
                //Guardamos fotos del paciente
                if (archivoFirma != null) {//se cargo firma         
                    String nombreImagenReal = archivoFirma.getFileName();
                    String extension = nombreImagenReal.substring(nombreImagenReal.lastIndexOf("."), nombreImagenReal.length());
                    CfgImagenes nuevaImagen = new CfgImagenes();
                    imagenesFacade.create(nuevaImagen);//crearlo para que me autogenere el ID            
                    String nombreImagenEnTmp = "firmaUsuario" + SessionUtil.getUserLog() + extension;
                    Utilidades.moverArchivo(SessionUtil.getUrlPath() + "imagenesOpenmedical/" + nombreImagenEnTmp, SessionUtil.getUrlImagen() + "Produccion/firmas/" + nuevaImagen.getId().toString() + extension, true);
                    nuevaImagen.setNombre(nombreImagenReal);
                    nuevaImagen.setNombreEnServidor(nuevaImagen.getId().toString() + extension);
                    nuevaImagen.setUrlImagen("Produccion/firmas/" + nuevaImagen.getId().toString() + extension);
                    imagenesFacade.edit(nuevaImagen);
                    pacientes.setFirma(nuevaImagen);
                }

                if (archivoFoto != null || fotoTomadaWebCam) {//se cargo foto
                    String nombreImagenReal = null;
                    String extension = null;
                    if (fotoTomadaWebCam) {//es por webCam
                        nombreImagenReal = "fotoWebCam.png";
                        extension = ".png";
                    } else {//es por carga de archivo
                        nombreImagenReal = archivoFoto.getFileName();
                        extension = nombreImagenReal.substring(nombreImagenReal.lastIndexOf("."), nombreImagenReal.length());
                    }

                    CfgImagenes nuevaImagen = new CfgImagenes();
                    imagenesFacade.create(nuevaImagen);//crearlo para que me autogenere el ID            
                    String nombreImagenEnTmp = "fotoUsuario" + SessionUtil.getUserLog() + extension;
                    Utilidades.moverArchivo(SessionUtil.getUrlPath() + "imagenesOpenmedical/" + nombreImagenEnTmp, SessionUtil.getUrlImagen() + "Produccion/fotos/" + nuevaImagen.getId().toString() + extension, true);
                    nuevaImagen.setNombre(nombreImagenReal);
                    nuevaImagen.setNombreEnServidor(nuevaImagen.getId().toString() + extension);
                    nuevaImagen.setUrlImagen("Produccion/fotos/" + nuevaImagen.getId().toString() + extension);
                    imagenesFacade.edit(nuevaImagen);
                    pacientes.setFoto(nuevaImagen);
                }

                //establecemos informacion del paciente
                pacientes.setMunicipio(clasificacionFacade.find(mun));
                pacientes.setGenero(clasificacionFacade.find(genero));
                pacientes.setGrupoSanguineo(clasificacionFacade.find(rh));
                pacientes.setEstadoCivil(clasificacionFacade.find(estadoCivil));
                pacientes.setDepartamento(clasificacionFacade.find(dpto));
                //pacientes.setZona(clasificacionFacade.find(zona));
                //pacientes.setOcupacion(clasificacionFacade.find(ocupacion));
                pacientes.setIdAdministradora(administradorFacade.find(pacientes.getIdAdministradora().getIdAdministradora()));

                if (pacientes.getIdPaciente() == null) {
                    CfgClasificaciones c = new CfgClasificaciones();
                    c.setId(tipoIdentificacion);
                    pacientes.setTipoIdentificacion(c);
                    pacientes.setIdentificacion(identificacionPaciente);
                    if (pacientes.getOcupacion().getId() == 0) {
                        pacientes.setOcupacion(null);
                    }
                    pacientes.setZona(null);
                    pacientes.setOcupacion(null);
                    pacienteFacade.create(pacientes);
                } else {
                    pacienteFacade.edit(pacientes);
                }
                if(!listaEstudioSeleccionados.isEmpty()){
                orden.setXlabEstudioList(listaEstudioSeleccionados);
            }
            if(diagnostico==null)orden.setDiagnosticoId(null);
            else if(diagnostico.equals(""))orden.setDiagnosticoId(null);
            XlabConsecutivos con = consecutivoFacade.getName(Constante.ORDEN);
            SimpleDateFormat formato = new SimpleDateFormat("yyMMdd");
            String format = "%0"+con.getDecimales()+"d";
            orden.setNroOrden(formato.format(new Date())+String.format(format, con.getNumeroActual()+1));
            CfgEmpresa empresa  = new CfgEmpresa();
            empresa.setCodEmpresa(SessionUtil.getEmpresa());
            orden.setEmpresaId(empresa);
            orden.setPacienteId(pacientes);
            orden.setEstado(Constante.ESTADO_APROBADO);
            orden.setFechaEstado(new Date());
            orden.setFechaCreacion(new Date());
            orden.setFechaActualizacion(new Date());
            orden.setUsuarioCrea(usuarioActual);
            orden.setUsuarioElimina(null);
            ordenFacade.create(orden);
            
            //Validamos si ya fue cargado las pruebas de lo contrario cargamos y guardamos
                    listaPruebas = ordenEstudioPruebaFacade.getPruebasXOrden(orden.getId());
                    if(listaPruebas.isEmpty()){
                        listaPruebas = new ArrayList<>();
                        for(XlabEstudio xe: orden.getXlabEstudioList()){
                            for(XlabPrueba xp: xe.getXlabPruebaList()){
                                XlabOrdenEstudiosPruebas xo = new XlabOrdenEstudiosPruebas();
                                xo.setOrdenId(orden);
                                xo.setPruebaId(xp);
                                xo.setEstudioId(xe);
                                xo.setEstado(Constante.PRUEBA_PENDIENTE);
                                xo.setFechaActualizacion(new Date());
                                xo.setConfirmado(false);
                                for(XlabPruebaReferencia xr:xp.getXlabPruebaReferenciaList()){
                                    if(pacientes.getGenero().getObservacion().equals("F") && xr.getTipo().equals("M")){
                                        if(xr.getMinimoReferencia()!=0){
                                            xo.setRefMin(xr.getMinimoReferencia());
                                            xo.setRefMax(xr.getMaximoReferencia());
                                        }
                                    }else if(pacientes.getGenero().getObservacion().equals("M") && xr.getTipo().equals("H")){
                                        if(xr.getMinimoReferencia()!=0){
                                            xo.setRefMin(xr.getMinimoReferencia());
                                            xo.setRefMax(xr.getMaximoReferencia());
                                        }
                                    }
                                }
                                ordenEstudioPruebaFacade.create(xo);
                                listaPruebas.add(xo);
                            }//fin for pruebas
                        }
                    }
                    con.setNumeroActual(con.getNumeroActual()+1);
                    consecutivoFacade.edit(con);
            
                
                //actualizamos 
                SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente Nro de orden " + orden.getNroOrden());
                inicializarVariables();
            }
        } catch (Exception e) {
            logger.error("Error en la clase " + OrdenMB.class.getName() + ", mensaje: " + e.getMessage(), e);
            SessionUtil.addErrorMessage("Error al guardar", e.toString());
        }
    }

    @Override
    public void inicializarVariables() {
        listaPruebas = new ArrayList<>();
        pacientes =new CfgPacientes();
        orden = new XlabOrden();
        CfgUsuarios usuario = new CfgUsuarios();
        usuario.setIdUsuario(0);
        orden.setMedicoId(usuario);
        identificacionPaciente="";
        listaEstudioSeleccionados = new ArrayList<>();
        listaMunicipio = new ArrayList<>();
        lecturaOrden = false;
        edad = "";
        diagnostico="";
        estudio  ="";
        firmaPorDefecto = "../resources/img/firma.png";
        fotoPorDefecto = "../resources/img/img_usuario.png";
        lecturaEmbarazo= false;
        renderAccion = true;
        tabIndex = 0;
        disableBtnEdit =true;
        disableBtnClarear=true;
        disableBtnEvolutivo=true;
        disableBtnConfirmar=true;
        disableBtnValidar=true;
        renderDatosAlfanumericos = false;
        renderDatosCompuestos = false;
        labelDatosPrueba = "";
        c1=0d;
        c2=0d;
        c3=0d;
        c4=0d;
        c5=0d;
        c6=0d;
        c7=0d;
        c8=0d;
        resultadoPruebaSeleccionada = new ArrayList<>();
        disableObservacionesPrueba = false;
        disableCampoPrueba = false;
        disableCampoPruebaCompuesta = false;
        observaciones="";
        dpto=0;
        mun=0;
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
        try {
            orden.setEstado(Constante.ESTADO_ELIMINADO);
            orden.setFechaEliminacion(new Date());
            orden.setFechaEstado(new Date());
            orden.setUsuarioElimina(usuarioActual);
            orden.setFechaActualizacion(new Date());
            ordenFacade.edit(orden);
            SessionUtil.addInfoMessage("Guardado", "Eliminado Correctamente");
            inicializarVariables();
        } catch (Exception e) {
            SessionUtil.addErrorMessage("Error al eliminar", e.toString());
        }
    }
    
    public void editar(){
        try {
            if(pacientes.getIdPaciente()==null){
                pacientes.setIdentificacion(identificacionPaciente);
                if(pacientes.getOcupacion().getId()==0)pacientes.setOcupacion(null);
                pacienteFacade.create(pacientes);
            }
            else pacienteFacade.edit(pacientes);
            
            if(!listaEstudioSeleccionados.isEmpty()){
                orden.setXlabEstudioList(listaEstudioSeleccionados);
            }
            if(diagnostico==null)orden.setDiagnosticoId(null);
            else if(diagnostico.equals(""))orden.setDiagnosticoId(null);
            orden.setPacienteId(pacientes);
            orden.setFechaActualizacion(new Date());
            ordenFacade.edit(orden);
            //actualizamos 
            SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente");
            inicializarVariables();
        } catch (Exception e) {
            SessionUtil.addErrorMessage("Error al guardar", e.toString());
        }
    }
    public void validarGenero(){
        disableEmbarazo = clasificacionFacade.find(genero).getObservacion().equals("M");
    }
    public void validarIdentificacion() {//verifica si existe la identificacion de lo contrario abre un dialogo para seleccionar el paciente de una tabla
        try{
        pacientes = pacienteFacade.findPacienteByTipIden(tipoIdentificacion, identificacionPaciente);
        if(pacientes==null){
            pacientes = new CfgPacientes();
            SessionUtil.addWarningMessage("Paciente no existe", "Se creará como nuevo registro");
        }else{
            FacAdministradora administradora = new FacAdministradora();
            administradora.setIdAdministradora(0);
            if(pacientes.getIdAdministradora()==null)pacientes.setIdAdministradora(administradora);
            validarEdad();
            lecturaEmbarazo = !pacientes.getGenero().getDescripcion().equals("F");
            cargarMunicipios();
            //Cargamos fotos del paciente
            if(pacientes.getFirma()!=null){
                Utilidades.moverArchivo(SessionUtil.getUrlImagen()+pacientes.getFirma().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+pacientes.getFirma().getNombreEnServidor(),false);
                urlFirma = "../imagenesOpenmedical/"+pacientes.getFirma().getNombreEnServidor();
            }else{
                urlFirma = this.firmaPorDefecto;
            }
            if(pacientes.getFoto()!=null){
                Utilidades.moverArchivo(SessionUtil.getUrlImagen()+pacientes.getFoto().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+pacientes.getFoto().getNombreEnServidor(),false);
                urlFoto = "../imagenesOpenmedical/"+pacientes.getFoto().getNombreEnServidor();
            }else{
                urlFoto = this.fotoPorDefecto;
            }
                
            //Cargamos informacion del paciente
            if(pacientes.getGenero()!=null){
                disableEmbarazo = !pacientes.getGenero().getObservacion().equals("F");
                genero = pacientes.getGenero().getId();
            }
            rh  =pacientes.getGrupoSanguineo()!=null?pacientes.getGrupoSanguineo().getId():0;
            estadoCivil =pacientes.getEstadoCivil()!=null?pacientes.getEstadoCivil().getId():0;
            dpto = pacientes.getDepartamento()!=null?pacientes.getDepartamento().getId():0;
            cargarMunicipios();
            mun=pacientes.getMunicipio()!=null?pacientes.getMunicipio().getId():0;
            //zona = pacientes.getZona()!=null?pacientes.getZona().getId():0;
            //ocupacion=pacientes.getOcupacion()!=null?pacientes.getOcupacion().getId():0;
            
        }
        establecerOrden();
        }catch(Exception ex){
            SessionUtil.addErrorMessage("Error al cargar", ex.toString());
        }
    }

    public void validarEdad(){
        try{
            if(pacientes.getFechaNacimiento()!=null){
                edad = calcularEdad(pacientes.getFechaNacimiento());
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
            
    }
    private void establecerOrden(){
        try{
            orden = new XlabOrden();
            orden.setFechaOrden(new Date());
            //Validamos si se genera automaticamente
            if(SessionUtil.getConsecutivoAutomatico().equals("S")){
                XlabConsecutivos con = consecutivoFacade.getName(Constante.ORDEN);
                SimpleDateFormat formato = new SimpleDateFormat("yyMMdd");
                String format = "%0"+con.getDecimales()+"d";
                orden.setNroOrden(formato.format(new Date())+String.format(format, con.getNumeroActual()+1));
                lecturaOrden = true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
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

    
    public List<CfgClasificaciones> getListaGenero() {
        return listaGenero;
    }

    public void setListaGenero(List<CfgClasificaciones> listaGenero) {
        this.listaGenero = listaGenero;
    }

    public List<CfgClasificaciones> getListaGrupoSanguineo() {
        return listaGrupoSanguineo;
    }

    public void setListaGrupoSanguineo(List<CfgClasificaciones> listaGrupoSanguineo) {
        this.listaGrupoSanguineo = listaGrupoSanguineo;
    }

    public List<CfgClasificaciones> getListaEstadoCivil() {
        return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List<CfgClasificaciones> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    public List<CfgClasificaciones> getListaOcupacion() {
        return listaOcupacion;
    }

    public void setListaOcupacion(List<CfgClasificaciones> listaOcupacion) {
        this.listaOcupacion = listaOcupacion;
    }

    public List<CfgClasificaciones> getListaDpto() {
        return listaDpto;
    }

    public void setListaDpto(List<CfgClasificaciones> listaDpto) {
        this.listaDpto = listaDpto;
    }

    public List<CfgClasificaciones> getListaMunicipio() {
        return listaMunicipio;
    }

    public void setListaMunicipio(List<CfgClasificaciones> listaMunicipio) {
        this.listaMunicipio = listaMunicipio;
    }

    public List<CfgClasificaciones> getListaZona() {
        return listaZona;
    }

    public void setListaZona(List<CfgClasificaciones> listaZona) {
        this.listaZona = listaZona;
    }

    public String getIdentificacionPaciente() {
        return identificacionPaciente;
    }

    public void setIdentificacionPaciente(String identificacionPaciente) {
        this.identificacionPaciente = identificacionPaciente;
    }

    public CfgPacientes getPacientes() {
        return pacientes;
    }

    public void setPacientes(CfgPacientes pacientes) {
        this.pacientes = pacientes;
    } 

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
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

    public List<SelectItem> getListaMedicos() {
        if(listaMedicos ==null){
            listaMedicos = new ArrayList<>();
            CfgClasificaciones cc = clasificacionFacade.buscarPorCodigo("2", ClasificacionesEnum.TipoUsuario.toString());
            List<CfgUsuarios> lst = usuarioFacade.getUsuariosPRestadores(cc.getId());
            for(CfgUsuarios cu:lst){
                listaMedicos.add(new SelectItem(cu.getIdUsuario(),cu.getPrimerNombre()+" "+cu.getPrimerApellido()));
            }
        }
        return listaMedicos;
    }

    public void setListaMedicos(List<SelectItem> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }

    public List<SelectItem> getListaServicios() {
        if(listaServicios==null){
            listaServicios = new ArrayList<>();
            List<CfgClasificaciones> lst = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.TipoServicio.toString());
            for(CfgClasificaciones cc:lst){
                listaServicios.add(new SelectItem(cc.getId(),cc.getDescripcion()));
            }
        }
        return listaServicios;
    }

    public void setListaServicios(List<SelectItem> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public List<XlabOrdenEstudiosPruebas> getListaPruebas() {
        return listaPruebas;
    }

    public void setListaPruebas(List<XlabOrdenEstudiosPruebas> listaPruebas) {
        this.listaPruebas = listaPruebas;
    }

    public XlabOrden getOrden() {
        return orden;
    }

    public void setOrden(XlabOrden orden) {
        this.orden = orden;
    }

    public boolean isLecturaOrden() {
        return lecturaOrden;
    }

    public void setLecturaOrden(boolean lecturaOrden) {
        this.lecturaOrden = lecturaOrden;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    } 

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    } 

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public List<XlabEstudio> getListaEstudio() {
        if(listaEstudio==null)listaEstudio = estudioFacade.findAll();
        return listaEstudio;
    }

    public void setListaEstudio(List<XlabEstudio> listaEstudio) {
        this.listaEstudio = listaEstudio;
    }
    public List<XlabEstudio> getListaEstudioSeleccionados() {
        return listaEstudioSeleccionados;
    }

    public void setListaEstudioSeleccionados(List<XlabEstudio> listaEstudioSeleccionados) {
        this.listaEstudioSeleccionados = listaEstudioSeleccionados;
    }

    public List<String> autocompletarDiagnostico(String txt) {//retorna una lista con los diagnosticos que contengan el parametro txt
        if (txt != null && txt.length() > 2) {
            return diagnosticoFacade.autocompletarDiagnostico(txt.toUpperCase());
        } else {
            return null;
        }
    }
    public List<String> autocompletarOrden(String txt) {//retorna una lista con los diagnosticos que contengan el parametro txt
        if (txt != null && txt.length() > 2) {
            return ordenFacade.autocompletarOrden(txt);
        } else {
            return null;
        }
    }
    
    public List<String> autocompletarEstudio(String txt) {//retorna una lista con los diagnosticos que contengan el parametro txt
        if (txt != null && txt.length() > 2) {
            return estudioFacade.autocompletarDiagnostico(txt);
        } else {
            return null;
        }
    }

    public XlabEstudio getEstudioSeleccionado() {
        return estudioSeleccionado;
    }

    public void setEstudioSeleccionado(XlabEstudio estudioSeleccionado) {
        this.estudioSeleccionado = estudioSeleccionado;
    }

    public boolean isLecturaEmbarazo() {
        return lecturaEmbarazo;
    }

    public void setLecturaEmbarazo(boolean lecturaEmbarazo) {
        this.lecturaEmbarazo = lecturaEmbarazo;
    }

    public void cargarEstudio(){
        if(estudioSeleccionado!=null){
            listaEstudioSeleccionados.add(estudioSeleccionado);
        }
    }

    public boolean isRenderAccion() {
        return renderAccion;
    }

    public void setRenderAccion(boolean renderAccion) {
        this.renderAccion = renderAccion;
    }
    
    public void cargarMunicipios() {
        try{
            listaMunicipio = new ArrayList<>();
            if (dpto != 0) {
                listaMunicipio = clasificacionFacade.buscarMunicipioPorDepartamento(clasificacionFacade.find(dpto).getCodigo());

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void cargarDialogEstudio(){
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("./configuraciones/estudios", options, null);
    }
    
    public List<CfgClasificaciones> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<CfgClasificaciones> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public void onRowSelect(SelectEvent event) {
        estudioSeleccionado = (XlabEstudio)event.getObject();
           if(estudioSeleccionado!=null){
            listaEstudioSeleccionados.add(estudioSeleccionado);
        }
    }
    public void validarDiagnostico(SelectEvent event){
        try {
            orden.setDiagnosticoId(diagnosticoFacade.buscarPorNombre(event.getObject().toString()));
        } catch (Exception e) {
        }
    }
    public void validarOrden(SelectEvent event){
        try {
            orden.setNroOrden(event.getObject().toString()); 
            cargarOrden();
        } catch (Exception e) {
        }
    }
    public void validarEstudio(SelectEvent event){
         try {
            estudioSeleccionado = estudioFacade.buscarPorNombre(event.getObject().toString());
           if(estudioSeleccionado!=null){
            listaEstudioSeleccionados.add(estudioSeleccionado);
            estudio  =null;
            } 
        } catch (Exception e) {
        }
    }
    
    public void cargarOrden(){
        if(orden.getNroOrden()!=null){
            orden = ordenFacade.getOrdenXNro(orden.getNroOrden());
            if(orden==null){
                orden = new XlabOrden();
                SessionUtil.addInfoMessage("No Encontrado", "No se encontró registro");
            }else{
                if(!orden.getEstado().equals(Constante.ESTADO_ELIMINADO)){
                    listaEstudioSeleccionados = orden.getXlabEstudioList();
                    pacientes = orden.getPacienteId();
                    if(orden.getDiagnosticoId()!=null){
                        diagnostico = orden.getDiagnosticoId().getCodigoDiagnostico()+" - "+orden.getDiagnosticoId().getNombreDiagnostico();
                    }
                    if(pacientes.getFechaNacimiento()!=null)
                        this.edad = calcularEdad(pacientes.getFechaNacimiento());

                    if(pacientes.getFirma()!=null){
                        Utilidades.moverArchivo(SessionUtil.getUrlImagen()+pacientes.getFirma().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+pacientes.getFirma().getNombreEnServidor(),false);
                        urlFirma = "../imagenesOpenmedical/"+pacientes.getFirma().getNombreEnServidor();
                    }
                    if(pacientes.getFoto()!=null){
                        Utilidades.moverArchivo(SessionUtil.getUrlImagen()+pacientes.getFoto().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+pacientes.getFoto().getNombreEnServidor(),false);
                        urlFoto = "../imagenesOpenmedical/"+pacientes.getFoto().getNombreEnServidor();
                    }
                    
                    //Cargamos informacion del paciente
                    if (pacientes.getGenero() != null) {
                        disableEmbarazo = !pacientes.getGenero().getObservacion().equals("F");
                        genero = pacientes.getGenero().getId();
                    }
                    rh = pacientes.getGrupoSanguineo() != null ? pacientes.getGrupoSanguineo().getId() : 0;
                    estadoCivil = pacientes.getEstadoCivil() != null ? pacientes.getEstadoCivil().getId() : 0;
                    dpto = pacientes.getDepartamento() != null ? pacientes.getDepartamento().getId() : 0;
                    cargarMunicipios();
                    mun = pacientes.getMunicipio() != null ? pacientes.getMunicipio().getId() : 0;
                    
                    identificacionPaciente = pacientes.getIdentificacion();
                    renderAccion = false;
                    tabIndex =1;
                    //Validamos si ya fue cargado las pruebas de lo contrario cargamos y guardamos
                    listaPruebas = ordenEstudioPruebaFacade.getPruebasXOrden(orden.getId());
                    if(listaPruebas.isEmpty()){
                        listaPruebas = new ArrayList<>();
                        for(XlabEstudio xe: orden.getXlabEstudioList()){
                            for(XlabPrueba xp: xe.getXlabPruebaList()){
                                XlabOrdenEstudiosPruebas xo = new XlabOrdenEstudiosPruebas();
                                xo.setOrdenId(orden);
                                xo.setPruebaId(xp);
                                xo.setEstudioId(xe);
                                xo.setEstado(Constante.PRUEBA_PENDIENTE);
                                xo.setFechaActualizacion(new Date());
                                xo.setConfirmado(false);
                                for(XlabPruebaReferencia xr:xp.getXlabPruebaReferenciaList()){
                                    if(pacientes.getGenero().getObservacion().equals("F") && xr.getTipo().equals("M")){
                                        if(xr.getMinimoReferencia()!=0){
                                            xo.setRefMin(xr.getMinimoReferencia());
                                            xo.setRefMax(xr.getMaximoReferencia());
                                        }
                                    }else if(pacientes.getGenero().getObservacion().equals("M") && xr.getTipo().equals("H")){
                                        if(xr.getMinimoReferencia()!=0){
                                            xo.setRefMin(xr.getMinimoReferencia());
                                            xo.setRefMax(xr.getMaximoReferencia());
                                        }
                                    }
                                }
                                ordenEstudioPruebaFacade.create(xo);
                                listaPruebas.add(xo);
                            }//fin for pruebas
                        }
                    }
                    //listaPruebas = orden.getXlabEstudioList().get(0).getXlabPruebaList();
                }else{
                    inicializarVariables();
                    SessionUtil.addInfoMessage("Orden", "Este Número de Orden se encuentra eliminado");
                }
            }
        }
    }
    public void editar(Object o){
    }
    
   
    public void valorAlfaNumericoDigitado(){
        if(pruebaValidada!=null){
            int i=0;
            for(XlabOrdenEstudiosPruebas xo:listaPruebas){
                if(Objects.equals(xo.getId(), pruebaValidada.getId())){
                    listaPruebas.get(i).setResultado(datosPrueba);
                    listaPruebas.get(i).setNota(observaciones);
                }
                i++;
            }
            RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
        }
    }
    public void onSelectPrueba(SelectEvent event) {
        pruebaValidada = (XlabOrdenEstudiosPruebas) event.getObject();
        if(pruebaValidada!=null){
            observaciones = pruebaValidada.getNota();
            if(pruebaValidada.getEstado().equals(Constante.PRUEBA_VALIDADA) || pruebaValidada.getEstado().equals(Constante.PRUEBA_CONFIRMADA)){
                disableObservacionesPrueba = true;
                disableCampoPrueba = true;
                disableCampoPruebaCompuesta = true;
            }else{
                disableObservacionesPrueba = false;
                disableCampoPrueba = false;
                disableCampoPruebaCompuesta = false;
            }
            if(pruebaValidada.getPruebaId().getFormatoResultado().equals("C")){
                renderDatosAlfanumericos = false;
                renderDatosCompuestos = true;
                labelDatosPrueba = "";
                valoresPruebaCompuesta = "";
                c1=0d;
                c2=0d;
                c3=0d;
                c4=0d;
                c5=0d;
                c6=0d;
                c7=0d;
                c8=0d;
                if(!resultadoPruebaSeleccionada.isEmpty()){
                    for(XlabOrdenEstudioPruebaResultados xr:resultadoPruebaSeleccionada){
                        if(xr.getOrdenEstudiosPruebasId().getId().equals(pruebaValidada.getId())){
                            if(xr.getNeutrofilos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Neutrofilos: " +xr.getNeutrofilos()+" %\n";
                            if(xr.getLinfocitos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Linfocitos: " +xr.getLinfocitos()+" %\n";
                            if(xr.getCayados()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Cayados: " +xr.getCayados()+" %\n";
                            if(xr.getEosinofilos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Eosinofilos: " +xr.getEosinofilos()+" %\n";
                            if(xr.getMonocitos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Monocitos: " +xr.getMonocitos()+" %\n";
                            if(xr.getBasofilos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Basofilos: " +xr.getBasofilos()+" %\n";
                            if(xr.getMetamielocitos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Metamielocitos: " +xr.getMetamielocitos()+" %\n";
                            if(xr.getNormoblastos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Normoblastos: " +xr.getNormoblastos()+" %\n";
                        }
                    }
                }else{
                    List<XlabOrdenEstudioPruebaResultados> listaResultados = resultadoCompuesto.getListaOrdenResultadoXPrueba(pruebaValidada.getId());
                    for(XlabOrdenEstudioPruebaResultados xr:listaResultados){
                        if(xr.getOrdenEstudiosPruebasId().getId().equals(pruebaValidada.getId())){
                            if(xr.getNeutrofilos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Neutrofilos: " +xr.getNeutrofilos()+" %\n";
                            if(xr.getLinfocitos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Linfocitos: " +xr.getLinfocitos()+" %\n";
                            if(xr.getCayados()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Cayados: " +xr.getCayados()+" %\n";
                            if(xr.getEosinofilos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Eosinofilos: " +xr.getEosinofilos()+" %\n";
                            if(xr.getMonocitos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Monocitos: " +xr.getMonocitos()+" %\n";
                            if(xr.getBasofilos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Basofilos: " +xr.getBasofilos()+" %\n";
                            if(xr.getMetamielocitos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Metamielocitos: " +xr.getMetamielocitos()+" %\n";
                            if(xr.getNormoblastos()!=null)valoresPruebaCompuesta =valoresPruebaCompuesta+"Normoblastos: " +xr.getNormoblastos()+" %\n";
                        }
                    }
                }
        }else{
            renderDatosAlfanumericos = true;
            renderDatosCompuestos = false;
            labelDatosPrueba = pruebaValidada.getPruebaId().getCodigo() +" - "+pruebaValidada.getPruebaId().getNombre() + " ("+ pruebaValidada.getPruebaId().getUnidadPrueba().getCodigo()+")";
            if(pruebaValidada.getResultado()!=null){
                if(!pruebaValidada.equals(""))
                    datosPrueba=pruebaValidada.getResultado();
                else datosPrueba="";
            }else datosPrueba="";
            
        }
            RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
            disableBtnEdit =true;
            disableBtnClarear=true;
            disableBtnEvolutivo=false;
            if(pruebaValidada.getEstado().equals(Constante.PRUEBA_PENDIENTE)){
                disableBtnConfirmar=true;
                disableBtnValidar=false;
            }else if(pruebaValidada.getEstado().equals(Constante.PRUEBA_VALIDADA)){
                disableBtnConfirmar=false;
                disableBtnValidar=true;
                if(usuarioActual.getIdPerfil().getNombrePerfil().equals("AdminXlab")){
                    disableBtnEdit=false;
                    disableBtnClarear =false;
                }
            }else if(pruebaValidada.getEstado().equals(Constante.PRUEBA_CONFIRMADA)){
                disableBtnConfirmar=true;
                disableBtnValidar=true;
                if(usuarioActual.getIdPerfil().getNombrePerfil().equals("AdminXlab")){
                    disableBtnEdit=false;
                    disableBtnClarear =false;
                }
            }
            
        }
    }
    
    public void validarResultado(XlabOrdenEstudiosPruebas prueba){
        pruebaValidada = prueba;
        if(pruebaValidada.getPruebaId().getFormatoResultado().equals("C")){
            renderDatosAlfanumericos = false;
            renderDatosCompuestos = true;
            labelDatosPrueba = "";
            c1=0d;
            c2=0d;
            c3=0d;
            c4=0d;
            c5=0d;
            c6=0d;
            c7=0d;
            c8=0d;
            RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
            RequestContext.getCurrentInstance().execute("PF('wVCompuestos').show();");
        }
    }
    
    public void cargarDialogCompuesto(){
        renderDatosAlfanumericos = false;
            renderDatosCompuestos = true;
            labelDatosPrueba = "";
            c1=0d;
            c3=0d;
            c4=0d;
            c5=0d;
            c6=0d;
            c7=0d;
            c8=0d;
            RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
            RequestContext.getCurrentInstance().execute("PF('wVCompuestos').show();");
    }

    public void cargarPruebasAreas(){
        if(areaId!=0)
            listaPruebas = ordenEstudioPruebaFacade.getPruebasXOrdenXArea(orden.getId(), areaId);
        else listaPruebas = ordenEstudioPruebaFacade.getPruebasXOrden(orden.getId());
        renderDatosAlfanumericos = false;
            renderDatosCompuestos = false;
            labelDatosPrueba = "";
            c1=0d;
            c2=0d;
            c3=0d;
            c4=0d;
            c5=0d;
            c6=0d;
            c7=0d;
            c8=0d;
    }
    public void aceptarCompuesto(){
        if(pruebaValidada!=null){
            XlabOrdenEstudioPruebaResultados xo = new XlabOrdenEstudioPruebaResultados();
            xo.setOrdenEstudiosPruebasId(pruebaValidada);
            valoresPruebaCompuesta ="";
            if(c1!=0){
                xo.setNeutrofilos(c1);
                valoresPruebaCompuesta =valoresPruebaCompuesta+"Neutrofilos: " +c1+" %\n";
            }
            if(c2!=0){
                xo.setLinfocitos(c2);
                valoresPruebaCompuesta =valoresPruebaCompuesta+"Linfocitos: "+ c2+" %\n";
            }
            if(c3!=0){
                xo.setCayados(c3);
                valoresPruebaCompuesta =valoresPruebaCompuesta+"Cayados: "+ c3+" %\n";
            }
            if(c4!=0){
                xo.setEosinofilos(c4);
                valoresPruebaCompuesta =valoresPruebaCompuesta+"Eosinofilos: "+ c4+" %\n";
            }
            if(c5!=0){
                xo.setMonocitos(c5);
                valoresPruebaCompuesta =valoresPruebaCompuesta+"Monocitos: " +c5+" %\n";
            }
            if(c6!=0){
                xo.setBasofilos(c6);
                valoresPruebaCompuesta =valoresPruebaCompuesta+"Basofilos: "+ c6+" %\n";
            }
            if(c7!=0){
                xo.setMetamielocitos(c7);
                valoresPruebaCompuesta =valoresPruebaCompuesta+"Metamielocitos: "+ c7.toString()+" %\n";
            }
            if(c8!=0){
                xo.setNormoblastos(c8);
                valoresPruebaCompuesta =valoresPruebaCompuesta+"Normoblastos: "+ c8+" %\n";
            }
            xo.setObservaciones(observaciones);
            if(resultadoPruebaSeleccionada.isEmpty()){
                xo.setOrdenEstudiosPruebasId(pruebaValidada);
                resultadoPruebaSeleccionada.add(xo);
            }else{
                int i=0;
                for(XlabOrdenEstudioPruebaResultados xr:resultadoPruebaSeleccionada){
                    if(xr.getOrdenEstudiosPruebasId().getId()==pruebaValidada.getId()){
                        resultadoPruebaSeleccionada.set(i, xo);
                    }
                    i++;
                }
            }
        }
        RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
        RequestContext.getCurrentInstance().execute("PF('wVCompuestos').hide();");
        
    }
     public void cancelarCompuesto(){
         c1=0d;
         c3=0d;
         c4=0d;
         c5=0d;
         c6=0d;
         c7=0d;
         c8=0d;
         RequestContext.getCurrentInstance().execute("PF('wVCompuestos').hide();");
         RequestContext.getCurrentInstance().update(":IdFormPanel");
     }

    public void btnValidar(){
        int i = 0;
        for(XlabOrdenEstudiosPruebas xo :pruebaSeleccionada){
            if(xo.getPruebaId().getFormatoResultado().equals("C")){
                try{
                    utx.begin();
                    xo.setEstado(Constante.PRUEBA_VALIDADA);
                    xo.setNota(observaciones);
                    xo.setFechaActualizacion(new Date());
                    ordenEstudioPruebaFacade.edit(xo);
                    for(XlabOrdenEstudioPruebaResultados xr:resultadoPruebaSeleccionada){
                        if(xr.getOrdenEstudiosPruebasId().getPruebaId()==xo.getPruebaId()){
                            resultadoCompuesto.edit(xr);
                        }
                    }
                    utx.commit();
                }catch(Exception e){
                    logger.error("Error en la clase " + OrdenMB.class.getName() + ", mensaje: " + e.getMessage(), e);
                }
            }else{
                xo.setEstado(Constante.PRUEBA_VALIDADA);
                xo.setNota(observaciones);
                xo.setFechaActualizacion(new Date());
                ordenEstudioPruebaFacade.edit(xo);
                pruebaSeleccionada.set(i, xo);
            }
            i++;
        }
        disableBtnValidar =true;
        disableBtnConfirmar=false;
        disableObservacionesPrueba = true;
        disableCampoPrueba = true;
        disableCampoPruebaCompuesta = true;
        RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
    }
    public void btnConfirmar(){
        int i = 0;
        for(XlabOrdenEstudiosPruebas xo :pruebaSeleccionada){
            xo.setEstado(Constante.PRUEBA_CONFIRMADA);
            xo.setConfirmado(true);
            xo.setFechaActualizacion(new Date());
            ordenEstudioPruebaFacade.edit(xo);
            pruebaSeleccionada.set(i, xo);
            i++;
            observaciones = xo.getNota();
        }
        disableBtnValidar =true;
        disableBtnConfirmar=true;
        
        RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
    }
    public void btnEvolutivo(){
        ChartSeries boys = new ChartSeries();
                    boys.setLabel("Boys");
                    boys.set("2004", 100);
                    boys.set("2005", 90);
                    boys.set("2006", 34);
                    boys.set("2007", 110);
                    boys.set("2008", 65);
                    LineChartModel model = new LineChartModel();
                    model.addSeries(boys);
                    System.out.println("Entro..");
                    lineModelEvolutivo = model;
                    lineModelEvolutivo.setTitle("Category Chart");
        lineModelEvolutivo.setLegendPosition("e");
        lineModelEvolutivo.setShowPointLabels(true);
        lineModelEvolutivo.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        Axis yAxis = lineModelEvolutivo.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
        RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
                        RequestContext.getCurrentInstance().execute("PF('wVEvolutivo').show();");
        if(pruebaValidada!=null)   {
             try {
                 List<XlabOrdenEstudiosPruebas> lista = ordenEstudioPruebaFacade.getEvolutivoPacienteXprueba(orden.getPacienteId().getIdPaciente(), pruebaValidada.getPruebaId().getId());
                 if(!pruebaValidada.getPruebaId().getFormatoResultado().equals("C")){
                      /*LineChartModel  model = new LineChartModel();
                       LineChartSeries   series1 = new LineChartSeries();
                        series1.setLabel(pruebaValidada.getPruebaId().getCodigo());
                        series1.set(3, 2);
                        series1.set(4, 1);
                        series1.set(5, 3);
                        for(XlabOrdenEstudiosPruebas xl:lista){
                            
                        }
                        
                        model.addSeries(series1);
                        lineModelEvolutivo = model;
                        lineModelEvolutivo.setTitle("EVOLUTIVO "+pruebaValidada.getPruebaId().getNombre());
                        lineModelEvolutivo.setLegendPosition("e");
                        lineModelEvolutivo.setShowPointLabels(true);
                        lineModelEvolutivo.setAnimate(true);
                        lineModelEvolutivo.getAxes().put(AxisType.X, new CategoryAxis("Fechas Resultados"));
                        Axis yAxis = lineModelEvolutivo.getAxis(AxisType.Y);
                        yAxis.setLabel(pruebaValidada.getPruebaId().getUnidadPrueba().getCodigo());
                        yAxis.setMin(0);
                        yAxis.setMax(10);
                        System.out.println(lineModelEvolutivo);*/
                        //RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
                }
                 
            } catch (Exception e) {
                logger.error("Error en la clase " + OrdenMB.class.getName() + ", mensaje: " + e.getMessage(), e);
            }
        }
    }
    public void btnClarear(){
        disableObservacionesPrueba = false;
        disableCampoPrueba = false;
        disableCampoPruebaCompuesta = false;
        datosPrueba = "";
        disableBtnValidar = false;
        int i=0;
        for(XlabOrdenEstudiosPruebas xo :pruebaSeleccionada){
            xo.setEstado(Constante.PRUEBA_PENDIENTE);
            xo.setConfirmado(false);
            xo.setResultado("");
            xo.setFechaActualizacion(new Date());
            ordenEstudioPruebaFacade.edit(xo);
            pruebaSeleccionada.set(i, xo);
            i++;
        }
        
        RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
    }
    public void btnEditar(){
        disableObservacionesPrueba = false;
        disableCampoPrueba = false;
        disableCampoPruebaCompuesta = false;
        disableBtnValidar = false;
        int i=0;
        for(XlabOrdenEstudiosPruebas xo :pruebaSeleccionada){
            xo.setEstado(Constante.PRUEBA_PENDIENTE);
            xo.setConfirmado(false);
            xo.setFechaActualizacion(new Date());
            ordenEstudioPruebaFacade.edit(xo);
            pruebaSeleccionada.set(i, xo);
            i++;
        }
        RequestContext.getCurrentInstance().update(":IdFormPanel:tabV");
    }
     
    public List<SelectItem> getListaAreas() {
        if(listaAreas==null){
            listaAreas = new ArrayList<>();
            List<CfgClasificaciones> lst = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.GrupoArea.toString());
            for(CfgClasificaciones c:lst){
                listaAreas.add(new SelectItem(c.getId(),c.getDescripcion()));
            }
        }
        return listaAreas;
    }

    public void setListaAreas(List<SelectItem> listaAreas) {
        this.listaAreas = listaAreas;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public CfgUsuarios getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(CfgUsuarios usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public XlabPruebaReferencia getX1() {
        return x1;
    }

    public void setX1(XlabPruebaReferencia x1) {
        this.x1 = x1;
    }

    public XlabPruebaReferencia getX2() {
        return x2;
    }

    public void setX2(XlabPruebaReferencia x2) {
        this.x2 = x2;
    }

    public XlabPruebaReferencia getX3() {
        return x3;
    }

    public void setX3(XlabPruebaReferencia x3) {
        this.x3 = x3;
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
            RequestContext.getCurrentInstance().update("IdFormPanel");
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
    public void reporteXId(){
        try {
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse httpServletResponse = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
            httpServletResponse.setContentType("application/pdf");
                ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String ruta ;
            ruta = servletContext.getRealPath("/informes/listatrabajos/r_master_pruebas_por_orden.jasper");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_EMPRESA", SessionUtil.getEmpresa());
            parametros.put("P_ORDEN", orden.getNroOrden());
            parametros.put("SUBREPORT_DIR", "r_prueba_orden_grupo_alfa_num_orden.jasper");
            try{
                Connection con = DBConnector.getInstance().getConnection();
                JasperPrint jasperPrint = JasperFillManager.fillReport(ruta, parametros, con);
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                FacesContext.getCurrentInstance().responseComplete();
                if(con!=null)con.close();
                DBConnector.getInstance().closeConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<XlabOrdenEstudiosPruebas> getPruebaSeleccionada() {
        return pruebaSeleccionada;
    }

    public void setPruebaSeleccionada(List<XlabOrdenEstudiosPruebas> pruebaSeleccionada) {
        this.pruebaSeleccionada = pruebaSeleccionada;
    }
    
    public void validar(){
        System.out.println(pruebaSeleccionada.size());
    }

    public boolean isRenderDatosAlfanumericos() {
        return renderDatosAlfanumericos;
    }

    public void setRenderDatosAlfanumericos(boolean renderDatosAlfanumericos) {
        this.renderDatosAlfanumericos = renderDatosAlfanumericos;
    }

    public boolean isRenderDatosCompuestos() {
        return renderDatosCompuestos;
    }

    public void setRenderDatosCompuestos(boolean renderDatosCompuestos) {
        this.renderDatosCompuestos = renderDatosCompuestos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDatosPrueba() {
        return datosPrueba;
    }

    public void setDatosPrueba(String datosPrueba) {
        this.datosPrueba = datosPrueba;
    }

    public String getLabelDatosPrueba() {
        return labelDatosPrueba;
    }

    public void setLabelDatosPrueba(String labelDatosPrueba) {
        this.labelDatosPrueba = labelDatosPrueba;
    }

    public Double getC1() {
        return c1;
    }

    public void setC1(Double c1) {
        this.c1 = c1;
    }

    public Double getC2() {
        return c2;
    }

    public void setC2(Double c2) {
        this.c2 = c2;
    }

    public Double getC3() {
        return c3;
    }

    public void setC3(Double c3) {
        this.c3 = c3;
    }

    public Double getC4() {
        return c4;
    }

    public void setC4(Double c4) {
        this.c4 = c4;
    }

    public Double getC5() {
        return c5;
    }

    public void setC5(Double c5) {
        this.c5 = c5;
    }

    public Double getC6() {
        return c6;
    }

    public void setC6(Double c6) {
        this.c6 = c6;
    }

    public Double getC7() {
        return c7;
    }

    public void setC7(Double c7) {
        this.c7 = c7;
    }

    public Double getC8() {
        return c8;
    }

    public void setC8(Double c8) {
        this.c8 = c8;
    }

    public XlabOrdenEstudiosPruebas getPruebaValidada() {
        return pruebaValidada;
    }

    public void setPruebaValidada(XlabOrdenEstudiosPruebas pruebaValidada) {
        this.pruebaValidada = pruebaValidada;
    }

    public boolean isDisableBtnEdit() {
        return disableBtnEdit;
    }

    public void setDisableBtnEdit(boolean disableBtnEdit) {
        this.disableBtnEdit = disableBtnEdit;
    }

    public boolean isDisableBtnClarear() {
        return disableBtnClarear;
    }

    public void setDisableBtnClarear(boolean disableBtnClarear) {
        this.disableBtnClarear = disableBtnClarear;
    }

    public boolean isDisableBtnEvolutivo() {
        return disableBtnEvolutivo;
    }

    public void setDisableBtnEvolutivo(boolean disableBtnEvolutivo) {
        this.disableBtnEvolutivo = disableBtnEvolutivo;
    }

    public boolean isDisableBtnConfirmar() {
        return disableBtnConfirmar;
    }

    public void setDisableBtnConfirmar(boolean disableBtnConfirmar) {
        this.disableBtnConfirmar = disableBtnConfirmar;
    }

    public boolean isDisableBtnValidar() {
        return disableBtnValidar;
    }

    public void setDisableBtnValidar(boolean disableBtnValidar) {
        this.disableBtnValidar = disableBtnValidar;
    }

    public boolean isFotoTomadaWebCam() {
        return fotoTomadaWebCam;
    }

    public void setFotoTomadaWebCam(boolean fotoTomadaWebCam) {
        this.fotoTomadaWebCam = fotoTomadaWebCam;
    }

    public XlabOrdenEstudioPruebaResultadosFacade getResultadoCompuesto() {
        return resultadoCompuesto;
    }

    public void setResultadoCompuesto(XlabOrdenEstudioPruebaResultadosFacade resultadoCompuesto) {
        this.resultadoCompuesto = resultadoCompuesto;
    }

    public List<XlabOrdenEstudioPruebaResultados> getResultadoPruebaSeleccionada() {
        return resultadoPruebaSeleccionada;
    }

    public void setResultadoPruebaSeleccionada(List<XlabOrdenEstudioPruebaResultados> resultadoPruebaSeleccionada) {
        this.resultadoPruebaSeleccionada = resultadoPruebaSeleccionada;
    }

    public String getValoresPruebaCompuesta() {
        return valoresPruebaCompuesta;
    }

    public void setValoresPruebaCompuesta(String valoresPruebaCompuesta) {
        this.valoresPruebaCompuesta = valoresPruebaCompuesta;
    }

    public boolean isDisableObservacionesPrueba() {
        return disableObservacionesPrueba;
    }

    public void setDisableObservacionesPrueba(boolean disableObservacionesPrueba) {
        this.disableObservacionesPrueba = disableObservacionesPrueba;
    }

    public boolean isDisableCampoPrueba() {
        return disableCampoPrueba;
    }

    public void setDisableCampoPrueba(boolean disableCampoPrueba) {
        this.disableCampoPrueba = disableCampoPrueba;
    }

    public boolean isDisableCampoPruebaCompuesta() {
        return disableCampoPruebaCompuesta;
    }

    public void setDisableCampoPruebaCompuesta(boolean disableCampoPruebaCompuesta) {
        this.disableCampoPruebaCompuesta = disableCampoPruebaCompuesta;
    }

    public LineChartModel getLineModelEvolutivo() {
        return lineModelEvolutivo;
    }

    public void setLineModelEvolutivo(LineChartModel lineModelEvolutivo) {
        this.lineModelEvolutivo = lineModelEvolutivo;
    }

    public int getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(int tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public boolean isDisableEmbarazo() {
        return disableEmbarazo;
    }

    public void setDisableEmbarazo(boolean disableEmbarazo) {
        this.disableEmbarazo = disableEmbarazo;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public int getRh() {
        return rh;
    }

    public void setRh(int rh) {
        this.rh = rh;
    }

    public int getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(int estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public int getDpto() {
        return dpto;
    }

    public void setDpto(int dpto) {
        this.dpto = dpto;
    }

    public int getMun() {
        return mun;
    }

    public void setMun(int mun) {
        this.mun = mun;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

    
    
}
