/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.seguridad;

import coderappco.xlab.entidades.*;
import coderappco.xlab.facades.*;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import coderappco.xlab.utilidades.Constante;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.ServletContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

    private final Logger logger = Logger.getRootLogger();
    private String usuario;
    private String password;
    private String nombreLogin="";
    private List<CfgOpcionesMenu> listMenu = new ArrayList<>();
    private CfgUsuarios usuarioActual;
    @EJB
    CfgOpcionesMenuFacade opcionesFacade;
    @EJB
    CfgSedeFacade sedesFacade;
    @EJB
    CfgUsuariosFacade usuarioFacade;
    @EJB
    CfgConfiguracionesFacade configuracionFacade;
    @EJB
    CfgClasificacionesFacade clasificacionFacade;
    @EJB
    CfgEmpresaFacade empresaFacade;
    //---------------------------------------------------
    //-----------------FACHADAS -------------------------
    //---------------------------------------------------
    @EJB
    CfgClasificacionesFacade clasificacionesFacade;
    @EJB
    CfgPerfilesUsuarioFacade perfilesUsuarioFacade;
    @EJB
    CfgUsuariosFacade usuariosFacade;
    @EJB
    FacServicioFacade servicioFacade;
    @EJB
    CfgSedeFacade cfgSedeFacade;
    @EJB
    FacAdministradoraFacade administradoraFacade;
    @EJB
    CfgInsumoFacade insumoFacade;
    @EJB
    CfgMedicamentoFacade medicamentoFacade;
    @EJB
    FacPaqueteFacade paqueteFacade;
    @EJB
    CfgConfiguracionesFacade configuracionesFacade;
    @EJB
    CfgCopiasSeguridadFacade copiasSeguridadFacade;
    @EJB
    XlabConsecutivosFacade consecutivoFacade;
    //---------------------------------------------------
    //-----------------ENTIDADES ------------------------
    //---------------------------------------------------
    
    private CfgEmpresa empresaActual;
    
    //private CfgConfiguraciones configuracionActual;
    private List<SelectItem> listaTipoIdentificacion;
    private List<SelectItem> listaAdministradorasItem;
    private List<SelectItem> listaGenero;
    private List<SelectItem> listaTipoEdad;
    private List<SelectItem> listaZona;
    private List<SelectItem> listaRegimen;
    private List<SelectItem> listaEstrato;
    private List<SelectItem> listaEstadoCivil;
    private List<SelectItem> listaGrupoSanguineo;
    private List<SelectItem> listaEtnia;
    private List<SelectItem> listaEscolaridad;
    private List<SelectItem> listaTipoAfiliado;
    private List<SelectItem> listaDepartamentos;
    private List<SelectItem> listaMunicipios;
    private List<SelectItem> listaEspecialidades;
    private List<SelectItem> listaOcupaciones;
    private List<SelectItem> listaTipoTarifa;
    private List<SelectItem> listaTipoUsuario;
    private List<SelectItem> listaTipoConsumo;
    //private List<SelectItem> listaFinalidad;
    private List<SelectItem> listaFinalidadConsulta;
    private List<SelectItem> listaFinalidadProcedimiento;
    private List<SelectItem> listaAmbito;
    private List<SelectItem> listaActoQuirurgico;
    private List<SelectItem> listaPersonalAtiende;
    private List<SelectItem> listaCausaExterna;
    private List<SelectItem> listaClasificacionEvento;
    private List<SelectItem> listaMotivoConsulta;
    private List<SelectItem> listaMotivoCancelacionCitas;
    private List<CfgPerfilesUsuario> listaPerfilesUsuario;
    private List<SelectItem> listaTipoDiagnosticoConsulta;
    private List<SelectItem> listaGenero3;
    private List<SelectItem> listaTipoConsulta;
    private List<SelectItem> listaTipoPago;
    private List<SelectItem> listaTipoFacturacion;
    //private List<SelectItem> listaTipoContrato;
    
    private List<FacServicio> listaServicios;
    private List<CfgInsumo> listaInsumos;
    private List<CfgMedicamento> listaMedicamentos;
    private List<FacPaquete> listaPaquetes;
    private List<CfgUsuarios> listaPrestadores;
    private List<CfgUsuarios> listaUsuarios;
    private List<SelectItem> listaSedes;
    private List<SelectItem> listaParentesco;
    private List<SelectItem> listaCategoriaPaciente;
    private List<FacAdministradora> listaAdministradoras;
    private List<SelectItem> listaTipoAdministradora;

    
    private List<SelectItem> listaMeses;
    public LoginMB() {
    }
    
    public void indexar(){
        usuario = "";
        password = "";
        SessionUtil.redirectTo("/faces/login.xhtml", false);
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CfgOpcionesMenu> getListMenu() {
        try {
            if (SessionUtil.getUserLog() == 0l) {
                SessionUtil.redirectTo("/faces/login.xhtml", false);
                nombreLogin="";
                SessionUtil.addErrorMessage("Sesión se ha cerrado", "Sesión se ha cerrado");
                listMenu = new ArrayList<>();
            }
            if (SessionUtil.getUserNombreLog() == null) {
                SessionUtil.redirectTo("/faces/login.xhtml", false);
                SessionUtil.addErrorMessage("Sesión se ha cerrado", "Sesión se ha cerrado");
                listMenu = new ArrayList<>();
                nombreLogin="";
            }
            if(listMenu.isEmpty())
                listMenu=opcionesFacade.getMenu(SessionUtil.getPerfil());
        }catch(Exception e){
            
        }
        return listMenu;
    }

    public void setListMenu(List<CfgOpcionesMenu> listMenu) {
        this.listMenu = listMenu;
    }

    public CfgUsuarios getUsuarioActual() {
        if(usuarioActual==null)
            usuarioActual = usuarioFacade.find(SessionUtil.getUserLog().intValue());
            
        return usuarioActual;
    }

    public void setUsuarioActual(CfgUsuarios usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    public String carga(){
        return "otra";
    }
    public String iniciarSesion(){
        try {
            try {
                usuarioActual = usuarioFacade.buscarPorLoginClave(this.usuario, this.password);
            if(usuarioActual!=null){
                SessionUtil.closeSession();
                CfgEmpresa empresa =  empresaFacade.find(1);
                SessionUtil.addSession(usuarioActual.getIdUsuario(), usuarioActual.getLoginUsuario(),empresa.getCodEmpresa(),usuarioActual.getIdPerfil().getIdPerfil());
                //Establecemos url imagenes
                CfgConfiguraciones cf = configuracionFacade.find(1);
                SessionUtil.setUrlImage(cf.getRutaImagenes());
                //Consecutivo orden
                //CfgClasificaciones ordenConsecutivo = clasificacionFacade.buscarPorCodigo("1",ClasificacionesEnum.Ordenes.toString());
                XlabConsecutivos consecutivos = consecutivoFacade.getName(Constante.ORDEN);
                SessionUtil.setConsecutivoAutomatico(consecutivos!=null?(consecutivos.getAutomatico()?"S":"N"):"N");
                //obtenemos tipo documento
                CfgClasificaciones tipoDocumento = clasificacionFacade.buscarPorCodigo("01", ClasificacionesEnum.ConsecutivoOrden.toString());
                SessionUtil.setTipoDocumentoOrden(tipoDocumento.getId());
                //SessionUtil.redirectTo("/faces/index.xhtml", false);        
                
                inicializar();
                return "ordenes/orden";
            }else{
                SessionUtil.addErrorMessage("Error al iniciar sesión", "email y/o clave incorrecta");
            }
                
        } catch (Exception e) {
            logger.error("Error en la clase "+ LoginMB.class.getName() + ", mensaje: " + e.getMessage(),e);
        }
        } catch (Exception e) {
        }
        return "";
    }
    
    public String irA(String action) {
        return "/"+action+"?faces-redirect=true";
    }

    public String getNombreLogin() {
        if(nombreLogin.equals(""))
            nombreLogin =  SessionUtil.getUserNombreLog();
        return nombreLogin;
    }

    public void setNombreLogin(String nombreLogin) {
        this.nombreLogin = nombreLogin;
    }
    
    
    public void cerrarSession() {
        SessionUtil.closeSession();
        SessionUtil.redirectTo("/faces/login.xhtml", false);
        nombreLogin="";
        listMenu = new ArrayList<>();
    }

    
    ///METODOS APLICACIONEs GENERAL B
    
    public void inicializar() {

        if (listaGenero == null) {//no se han cargaron las listas
            cargarClasificacion(ClasificacionesEnum.ActoQuirurgico);
            cargarClasificacion(ClasificacionesEnum.Administradoras);
            cargarClasificacion(ClasificacionesEnum.Ambito);
            cargarClasificacion(ClasificacionesEnum.CategoriaPaciente);
            cargarClasificacion(ClasificacionesEnum.CausaExterna);
            cargarClasificacion(ClasificacionesEnum.ClasificacionEvento);
            cargarClasificacion(ClasificacionesEnum.DPTO);
            cargarClasificacion(ClasificacionesEnum.Escolaridad);
            cargarClasificacion(ClasificacionesEnum.Etnia);
            cargarClasificacion(ClasificacionesEnum.EstadoCivil);
            cargarClasificacion(ClasificacionesEnum.Especialidad);
            //cargarClasificacion(ClasificacionesEnum.Finalidad);
            cargarClasificacion(ClasificacionesEnum.FinalidadConsulta);
            cargarClasificacion(ClasificacionesEnum.FinalidadProcedimiento);
            cargarClasificacion(ClasificacionesEnum.GrupoSanguineo);
            cargarClasificacion(ClasificacionesEnum.Genero);
            cargarClasificacion(ClasificacionesEnum.Genero3);
            cargarClasificacion(ClasificacionesEnum.Insumos);
            cargarClasificacion(ClasificacionesEnum.Medicamentos);
            cargarClasificacion(ClasificacionesEnum.Meses);
            cargarClasificacion(ClasificacionesEnum.MotiCancCitas);
            cargarClasificacion(ClasificacionesEnum.MotivoConsulta);
            cargarClasificacion(ClasificacionesEnum.Estrato);
            cargarClasificacion(ClasificacionesEnum.Ocupacion);
            cargarClasificacion(ClasificacionesEnum.Paquetes);
            cargarClasificacion(ClasificacionesEnum.Parentesco);
            cargarClasificacion(ClasificacionesEnum.PerfilesUsuario);
            cargarClasificacion(ClasificacionesEnum.PersonalAtiende);
            cargarClasificacion(ClasificacionesEnum.Prestadores);
            cargarClasificacion(ClasificacionesEnum.Regimen);
            cargarClasificacion(ClasificacionesEnum.Sedes);
            cargarClasificacion(ClasificacionesEnum.Servicios);
            cargarClasificacion(ClasificacionesEnum.TipoAfiliado);
            cargarClasificacion(ClasificacionesEnum.TipoAdministradora);
            cargarClasificacion(ClasificacionesEnum.TipoConsulta);
            //cargarClasificacion(ClasificacionesEnum.TipoContrato);
            cargarClasificacion(ClasificacionesEnum.TipoConsumo);
            cargarClasificacion(ClasificacionesEnum.TipoDiagnosticoConsulta);
            cargarClasificacion(ClasificacionesEnum.TipoEdad);
            cargarClasificacion(ClasificacionesEnum.TipoFacturacion);
            cargarClasificacion(ClasificacionesEnum.TipoIdentificacion);
            cargarClasificacion(ClasificacionesEnum.TipoPago);
            cargarClasificacion(ClasificacionesEnum.TipoRegistroClinico);
            cargarClasificacion(ClasificacionesEnum.TipoTarifa);
            cargarClasificacion(ClasificacionesEnum.TipoUsuario);
            cargarClasificacion(ClasificacionesEnum.Usuarios);
            cargarClasificacion(ClasificacionesEnum.Zona);
        }
    }

    public void cargarClasificacion(ClasificacionesEnum maestro) {

        switch (maestro) {

            case ActoQuirurgico:
                listaActoQuirurgico = cargarClasificacion(maestro.toString());
                break;
            case Administradoras:
                listaAdministradoras = administradoraFacade.buscarOrdenado();
                break;
            case Ambito:
                listaAmbito = cargarClasificacion(maestro.toString());
                break;
            case CategoriaPaciente:
                listaCategoriaPaciente = cargarClasificacion(maestro.toString());
                break;
            case CausaExterna:
                listaCausaExterna = cargarClasificacion(maestro.toString());
                break;
            case ClasificacionEvento:
                listaClasificacionEvento = cargarClasificacion(maestro.toString());
                break;
            case DPTO:
                listaDepartamentos = cargarClasificacion(maestro.toString());
                break;
            case Especialidad:
                listaEspecialidades = cargarClasificacion(maestro.toString());
                break;
            case Estrato:
                listaEstrato = cargarClasificacion(maestro.toString());
                break;
            case EstadoCivil:
                listaEstadoCivil = cargarClasificacion(maestro.toString());
                break;
            case Etnia:
                listaEtnia = cargarClasificacion(maestro.toString());
                break;
            case Escolaridad:
                listaEscolaridad = cargarClasificacion(maestro.toString());
                break;
//            case Finalidad:
//                listaFinalidad = cargarClasificacion(maestro.toString());
//                break;
            case FinalidadConsulta:
                listaFinalidadConsulta = cargarClasificacion(maestro.toString());
                break;
            case FinalidadProcedimiento:
                listaFinalidadProcedimiento = cargarClasificacion(maestro.toString());
                break;
            case GrupoSanguineo:
                listaGrupoSanguineo = cargarClasificacion(maestro.toString());
                break;
            case Genero:
                listaGenero = cargarClasificacion(maestro.toString());
                break;
            case Genero3:
                listaGenero3 = cargarClasificacion(maestro.toString());
                break;
            case Insumos:
                listaInsumos = insumoFacade.buscarOrdenado();
                break;
            case Medicamentos:
                listaMedicamentos = medicamentoFacade.buscarOrdenado();
                break;
            case Meses:
                cargarMeses();
                break;
            case MotiCancCitas:
                listaMotivoCancelacionCitas = cargarClasificacion(maestro.toString());
                break;
            case MotivoConsulta:
                listaMotivoConsulta = cargarClasificacion(maestro.toString());
            case Municipios:
                listaMunicipios = cargarClasificacion(maestro.toString());
                break;
            case Ocupacion:
                listaOcupaciones = cargarClasificacion(maestro.toString());
                break;
            case Paquetes:
                listaPaquetes = paqueteFacade.buscarOrdenado();
                break;
            case Parentesco:
                listaParentesco = cargarClasificacion(maestro.toString());
                break;
            case Prestadores:
                listaPrestadores = usuariosFacade.findPrestadores();
                break;
            case PerfilesUsuario:
                listaPerfilesUsuario = perfilesUsuarioFacade.findAll();
                break;
            case PersonalAtiende:
                listaPersonalAtiende = cargarClasificacion(maestro.toString());
                break;
            case Regimen:
                listaRegimen = cargarClasificacion(maestro.toString());
                break;
            case Servicios:
                listaServicios = servicioFacade.buscarActivos();
                break;
            case Sedes:
                listaSedes = generarSelectItem(cfgSedeFacade.findAll());
                break;
            case TipoAfiliado:
                listaTipoAfiliado = cargarClasificacion(maestro.toString());
                break;
            case TipoIdentificacion:
                listaTipoIdentificacion = cargarClasificacion(maestro.toString());
                break;
            case TipoEdad:
                listaTipoEdad = cargarClasificacion(maestro.toString());
                break;
            case TipoTarifa:
                listaTipoTarifa = cargarClasificacion(maestro.toString());
                break;
            case TipoUsuario:
                listaTipoUsuario = cargarClasificacion(maestro.toString());
                break;
            case TipoAdministradora:
                listaTipoAdministradora = cargarClasificacion(maestro.toString());
                break;
            case TipoDiagnosticoConsulta:
                listaTipoDiagnosticoConsulta = cargarClasificacion(maestro.toString());
                break;
            case TipoPago:
                listaTipoPago = cargarClasificacion(maestro.toString());
                break;
//            case TipoContrato:
//                listaTipoContrato = cargarClasificacion(maestro.toString());
//                break;
            case TipoConsumo:
                listaTipoConsumo = cargarClasificacion(maestro.toString());
                break;
            case TipoFacturacion:
                listaTipoFacturacion = cargarClasificacion(maestro.toString());
                break;
            case TipoConsulta:
                listaTipoConsulta = cargarClasificacion(maestro.toString(), "TRUE");//solo cargar los que esten activos
                break;
            case Usuarios:
                listaUsuarios = usuariosFacade.buscarOrdenado();
                break;
            case Zona:
                listaZona = cargarClasificacion(maestro.toString());
                break;
        }
    }

    private List<SelectItem> generarSelectItem(List<CfgSede> lista) {
        List<SelectItem> listaRetorno = new ArrayList<>();
        for (CfgSede cfgSede : lista) {
            listaRetorno.add(new SelectItem(cfgSede.getIdSede(), cfgSede.getNombreSede()));
        }
        return listaRetorno;

    }

    private List<SelectItem> cargarClasificacion(String maestro) {
        List<SelectItem> listaRetorno = new ArrayList<>();
        List<CfgClasificaciones> listaClasificaciones = clasificacionesFacade.buscarPorMaestro(maestro);
        for (CfgClasificaciones clasificacion : listaClasificaciones) {
            listaRetorno.add(new SelectItem(clasificacion.getId(), clasificacion.getCodigo() + " - " + clasificacion.getDescripcion()));
        }
        return listaRetorno;
    }

    private List<SelectItem> cargarClasificacion(String maestro, String observacion) {
        List<SelectItem> listaRetorno = new ArrayList<>();
        List<CfgClasificaciones> listaClasificaciones = clasificacionesFacade.buscarPorMaestroObservacion(maestro, observacion);
        for (CfgClasificaciones clasificacion : listaClasificaciones) {
            listaRetorno.add(new SelectItem(clasificacion.getId(), clasificacion.getCodigo() + " - " + clasificacion.getDescripcion()));
        }
        return listaRetorno;
    }

    public void cargarMeses() {
        listaMeses = new ArrayList<>();
        listaMeses.add(new SelectItem("0", "Enero"));
        listaMeses.add(new SelectItem("1", "Febrero"));
        listaMeses.add(new SelectItem("2", "Marzo"));
        listaMeses.add(new SelectItem("3", "Abril"));
        listaMeses.add(new SelectItem("4", "Mayo"));
        listaMeses.add(new SelectItem("5", "Junio"));
        listaMeses.add(new SelectItem("6", "Julio"));
        listaMeses.add(new SelectItem("7", "Agosto"));
        listaMeses.add(new SelectItem("8", "Septiembre"));
        listaMeses.add(new SelectItem("9", "Octubre"));
        listaMeses.add(new SelectItem("10", "Noviembre"));
        listaMeses.add(new SelectItem("11", "Diciembre"));
    }
    
    //---------------------------------------------------
    //-----------------FUNCIONES GET Y SET --------------
    //---------------------------------------------------
    public List<SelectItem> getListaTipoIdentificacion() {
        if(listaTipoIdentificacion == null) listaTipoIdentificacion = cargarClasificacion(ClasificacionesEnum.TipoIdentificacion.toString());
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public List<SelectItem> getListaGenero() {
        if(listaGenero == null) listaGenero = cargarClasificacion(ClasificacionesEnum.Genero.toString());
        return listaGenero;
    }

    public void setListaGenero(List<SelectItem> listaGenero) {
        this.listaGenero = listaGenero;
    }

    public List<SelectItem> getListaTipoEdad() {
        if(listaTipoEdad==null)listaTipoEdad = cargarClasificacion(ClasificacionesEnum.TipoEdad.toString());
        return listaTipoEdad;
    }

    public void setListaTipoEdad(List<SelectItem> listaTipoEdad) {
        this.listaTipoEdad = listaTipoEdad;
    }

    public List<SelectItem> getListaZona() {
        if(listaZona==null)listaZona = cargarClasificacion(ClasificacionesEnum.Zona.toString());
        return listaZona;
    }

    public void setListaZona(List<SelectItem> listaZona) {
        this.listaZona = listaZona;
    }

    public List<SelectItem> getListaRegimen() {
        if(listaRegimen==null)listaRegimen = cargarClasificacion(ClasificacionesEnum.Regimen.toString());
        return listaRegimen;
    }

    public void setListaRegimen(List<SelectItem> listaRegimen) {
        this.listaRegimen = listaRegimen;
    }

    public List<SelectItem> getListaEstrato() {
        if(listaEstrato == null)listaEstrato = cargarClasificacion(ClasificacionesEnum.Estrato.toString());
        return listaEstrato;
    }

    public void setListaEstrato(List<SelectItem> listaEstrato) {
        this.listaEstrato = listaEstrato;
    }

    public List<SelectItem> getListaEstadoCivil() {
        if(listaEstadoCivil == null) listaEstadoCivil = cargarClasificacion(ClasificacionesEnum.EstadoCivil.toString());
        return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List<SelectItem> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    public List<SelectItem> getListaGrupoSanguineo() {
        if(listaGrupoSanguineo==null)listaGrupoSanguineo = cargarClasificacion(ClasificacionesEnum.GrupoSanguineo.toString());
        return listaGrupoSanguineo;
    }

    public void setListaGrupoSanguineo(List<SelectItem> listaGrupoSanguineo) {
        this.listaGrupoSanguineo = listaGrupoSanguineo;
    }

    public List<SelectItem> getListaEtnia() {
        if(listaEtnia == null)listaEtnia = cargarClasificacion(ClasificacionesEnum.Etnia.toString());
        return listaEtnia;
    }

    public void setListaEtnia(List<SelectItem> listaEtnia) {
        this.listaEtnia = listaEtnia;
    }

    public List<SelectItem> getListaEscolaridad() {
        if(listaEscolaridad == null) listaEscolaridad = cargarClasificacion(ClasificacionesEnum.Escolaridad.toString());
        return listaEscolaridad;
    }

    public void setListaEscolaridad(List<SelectItem> listaEscolaridad) {
        this.listaEscolaridad = listaEscolaridad;
    }

    public List<SelectItem> getListaTipoAfiliado() {
        return listaTipoAfiliado;
    }

    public void setListaTipoAfiliado(List<SelectItem> listaTipoAfiliado) {
        this.listaTipoAfiliado = listaTipoAfiliado;
    }

    public List<SelectItem> getListaDepartamentos() {
        if(listaDepartamentos==null)listaDepartamentos = cargarClasificacion(ClasificacionesEnum.DPTO.toString());
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<SelectItem> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<SelectItem> getListaEspecialidades() {
        return listaEspecialidades;
    }

    public void setListaEspecialidades(List<SelectItem> listaEspecialidades) {
        this.listaEspecialidades = listaEspecialidades;
    }

    public List<SelectItem> getListaOcupaciones() {
        return listaOcupaciones;
    }

    public void setListaOcupaciones(List<SelectItem> listaOcupaciones) {
        this.listaOcupaciones = listaOcupaciones;
    }

    public List<SelectItem> getListaTipoUsuario() {
        return listaTipoUsuario;
    }

    public void setListaTipoUsuario(List<SelectItem> listaTipoUsuario) {
        this.listaTipoUsuario = listaTipoUsuario;
    }

    public List<CfgPerfilesUsuario> getListaPerfilesUsuario() {
        return listaPerfilesUsuario;
    }

    public void setListaPerfilesUsuario(List<CfgPerfilesUsuario> listaPerfilesUsuario) {
        this.listaPerfilesUsuario = listaPerfilesUsuario;
    }

    public List<CfgUsuarios> getListaUsuarios() {
        if(listaUsuarios==null)listaUsuarios=usuariosFacade.buscarOrdenado();
        return listaUsuarios;
    }

    public void setListaUsuarios(List<CfgUsuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<SelectItem> getListaGenero3() {
        if(listaGenero3==null)listaGenero3 = cargarClasificacion(ClasificacionesEnum.Genero3.toString());
        return listaGenero3;
    }

    public void setListaGenero3(List<SelectItem> listaGenero3) {
        this.listaGenero3 = listaGenero3;
    }

    public List<SelectItem> getListaMotivoConsulta() {
        return listaMotivoConsulta;
    }

    public void setListaMotivoConsulta(List<SelectItem> listaMotivoConsulta) {
        this.listaMotivoConsulta = listaMotivoConsulta;
    }

    public List<SelectItem> getListaTipoDiagnosticoConsulta() {
        return listaTipoDiagnosticoConsulta;
    }

    public void setListaTipoDiagnosticoConsulta(List<SelectItem> listaTipoDiagnosticoConsulta) {
        this.listaTipoDiagnosticoConsulta = listaTipoDiagnosticoConsulta;
    }

    public List<FacServicio> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<FacServicio> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public List<SelectItem> getListaTipoConsulta() {
        return listaTipoConsulta;
    }

    public void setListaTipoConsulta(List<SelectItem> listaTipoConsulta) {
        this.listaTipoConsulta = listaTipoConsulta;
    }

    public List<CfgUsuarios> getListaPrestadores() {
        return listaPrestadores;
    }

    public void setListaPrestadores(List<CfgUsuarios> listaPrestadores) {
        this.listaPrestadores = listaPrestadores;
    }

    public List<SelectItem> getListaMotivoCancelacionCitas() {
        return listaMotivoCancelacionCitas;
    }

    public void setListaMotivoCancelacionCitas(List<SelectItem> listaMotivoCancelacion) {
        this.listaMotivoCancelacionCitas = listaMotivoCancelacion;
    }

    public List<SelectItem> getListaSedes() {
        if(listaSedes==null)listaSedes = generarSelectItem(cfgSedeFacade.findAll());
        return listaSedes;
    }

    public void setListaSedes(List<SelectItem> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<SelectItem> getListaParentesco() {
        return listaParentesco;
    }

    public void setListaParentesco(List<SelectItem> listaParentesco) {
        this.listaParentesco = listaParentesco;
    }

    public List<FacAdministradora> getListaAdministradoras() {
        if(listaAdministradoras==null)listaAdministradoras = administradoraFacade.buscarOrdenado();
        return listaAdministradoras;
    }

    public void setListaAdministradoras(List<FacAdministradora> listaAdministradoras) {
        this.listaAdministradoras = listaAdministradoras;
    }

    public List<SelectItem> getListaCategoriaPaciente() {
        return listaCategoriaPaciente;
    }

    public void setListaCategoriaPaciente(List<SelectItem> listaCategoriaPaciente) {
        this.listaCategoriaPaciente = listaCategoriaPaciente;
    }

    public List<SelectItem> getListaTipoAdministradora() {
        if(listaTipoAdministradora==null)listaTipoAdministradora = cargarClasificacion(ClasificacionesEnum.TipoAdministradora.toString());
        return listaTipoAdministradora;
    }

    public void setListaTipoAdministradora(List<SelectItem> listaTipoAdministradora) {
        this.listaTipoAdministradora = listaTipoAdministradora;
    }

    public List<SelectItem> getListaTipoPago() {
        if(listaTipoPago==null)listaTipoPago = cargarClasificacion(ClasificacionesEnum.TipoPago.toString());
        return listaTipoPago;
    }

    public void setListaTipoPago(List<SelectItem> listaTipoPago) {
        this.listaTipoPago = listaTipoPago;
    }

    public List<SelectItem> getListaTipoFacturacion() {
        if(listaTipoFacturacion==null)listaTipoFacturacion = cargarClasificacion(ClasificacionesEnum.TipoFacturacion.toString());
        return listaTipoFacturacion;
    }

    public void setListaTipoFacturacion(List<SelectItem> listaTipoFacturacion) {
        this.listaTipoFacturacion = listaTipoFacturacion;
    }

//    public List<SelectItem> getListaTipoContrato() {
//        return listaTipoContrato;
//    }
//
//    public void setListaTipoContrato(List<SelectItem> listaTipoContrato) {
//        this.listaTipoContrato = listaTipoContrato;
//    }
    public List<CfgInsumo> getListaInsumos() {
        return listaInsumos;
    }

    public void setListaInsumos(List<CfgInsumo> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }

    public List<CfgMedicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public void setListaMedicamentos(List<CfgMedicamento> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    public List<FacPaquete> getListaPaquetes() {
        return listaPaquetes;
    }

    public void setListaPaquetes(List<FacPaquete> listaPaquetes) {
        this.listaPaquetes = listaPaquetes;
    }

    public List<SelectItem> getListaMeses() {
        listaMeses = new ArrayList<>();
        listaMeses.add(new SelectItem("0", "Enero"));
        listaMeses.add(new SelectItem("1", "Febrero"));
        listaMeses.add(new SelectItem("2", "Marzo"));
        listaMeses.add(new SelectItem("3", "Abril"));
        listaMeses.add(new SelectItem("4", "Mayo"));
        listaMeses.add(new SelectItem("5", "Junio"));
        listaMeses.add(new SelectItem("6", "Julio"));
        listaMeses.add(new SelectItem("7", "Agosto"));
        listaMeses.add(new SelectItem("8", "Septiembre"));
        listaMeses.add(new SelectItem("9", "Octubre"));
        listaMeses.add(new SelectItem("10", "Noviembre"));
        listaMeses.add(new SelectItem("11", "Diciembre"));
        return listaMeses;
    }

    public void setListaMeses(List<SelectItem> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public List<SelectItem> getListaTipoConsumo() {
        return listaTipoConsumo;
    }

    public void setListaTipoConsumo(List<SelectItem> listaTipoConsumo) {
        this.listaTipoConsumo = listaTipoConsumo;
    }

    public List<SelectItem> getListaTipoTarifa() {
        return listaTipoTarifa;
    }

    public void setListaTipoTarifa(List<SelectItem> listaTipoTarifa) {
        this.listaTipoTarifa = listaTipoTarifa;
    }

    public List<SelectItem> getListaFinalidadConsulta() {
        return listaFinalidadConsulta;
    }

    public void setListaFinalidadConsulta(List<SelectItem> listaFinalidadConsulta) {
        this.listaFinalidadConsulta = listaFinalidadConsulta;
    }

    public List<SelectItem> getListaFinalidadProcedimiento() {
        if(listaFinalidadProcedimiento==null)listaFinalidadProcedimiento=cargarClasificacion(ClasificacionesEnum.FinalidadProcedimiento.toString());
        return listaFinalidadProcedimiento;
    }

    public void setListaFinalidadProcedimiento(List<SelectItem> listaFinalidadProcedimiento) {
        this.listaFinalidadProcedimiento = listaFinalidadProcedimiento;
    }

    public List<SelectItem> getListaAmbito() {
        if(listaAmbito==null)listaAmbito = cargarClasificacion(ClasificacionesEnum.Ambito.toString());
        return listaAmbito;
    }

    public void setListaAmbito(List<SelectItem> listaAmbito) {
        this.listaAmbito = listaAmbito;
    }

    public List<SelectItem> getListaActoQuirurgico() {
        if(listaActoQuirurgico==null) listaActoQuirurgico = cargarClasificacion(ClasificacionesEnum.ActoQuirurgico.toString());
        return listaActoQuirurgico;
    }

    public void setListaActoQuirurgico(List<SelectItem> listaActoQuirurgico) {
        this.listaActoQuirurgico = listaActoQuirurgico;
    }

    public List<SelectItem> getListaPersonalAtiende() {
        if(listaPersonalAtiende==null)listaPersonalAtiende = cargarClasificacion(ClasificacionesEnum.PersonalAtiende.toString());
        return listaPersonalAtiende;
    }

    public void setListaPersonalAtiende(List<SelectItem> listaPersonalAtiende) {
        this.listaPersonalAtiende = listaPersonalAtiende;
    }

    public List<SelectItem> getListaCausaExterna() {
        if(listaCausaExterna == null) listaCausaExterna = cargarClasificacion(ClasificacionesEnum.CausaExterna.toString());
        return listaCausaExterna;
    }

    public void setListaCausaExterna(List<SelectItem> listaCausaExterna) {
        this.listaCausaExterna = listaCausaExterna;
    }

    public List<SelectItem> getListaClasificacionEvento() {
        if(listaClasificacionEvento==null)listaClasificacionEvento = cargarClasificacion(ClasificacionesEnum.ClasificacionEvento.toString());
        return listaClasificacionEvento;
    }

    public void setListaClasificacionEvento(List<SelectItem> listaClasificacionEvento) {
        this.listaClasificacionEvento = listaClasificacionEvento;
    }

    public List<SelectItem> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<SelectItem> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public List<SelectItem> getListaAdministradorasItem() {
        if(listaAdministradorasItem == null){
            listaAdministradorasItem = new ArrayList<>();
             listaAdministradoras = administradoraFacade.buscarOrdenado();
             for(FacAdministradora f:listaAdministradoras){
                 listaAdministradorasItem.add(new SelectItem(f.getIdAdministradora(),f.getRazonSocial()));
             }
        }
        return listaAdministradorasItem;
    }

    public void setListaAdministradorasItem(List<SelectItem> listaAdministradorasItem) {
        this.listaAdministradorasItem = listaAdministradorasItem;
    }

    public CfgEmpresa getEmpresaActual() {
        if(empresaActual==null)
            empresaActual = empresaFacade.find(1);
        return empresaActual;
    }

    public void setEmpresaActual(CfgEmpresa empresaActual) {
        this.empresaActual = empresaActual;
    }
}
