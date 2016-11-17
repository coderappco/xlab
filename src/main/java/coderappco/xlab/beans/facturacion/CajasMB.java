/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.facturacion;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.*;
import coderappco.xlab.facades.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;
/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "cajasMB")
@SessionScoped
public class CajasMB extends Controlador implements Serializable {

    //---------------------------------------------------
    //-----------------FACHADAS -------------------------
    //---------------------------------------------------    
    @EJB
    CfgClasificacionesFacade clasificacionesFacade;
    @EJB
    CfgSedeFacade sedeFacade;
    @EJB
    FacCajaFacade cajaFacade;
    @EJB
    CfgUsuariosFacade usuariosFacade;

    //---------------------------------------------------
    //-----------------ENTIDADES -------------------------
    //---------------------------------------------------
    private CfgSede sedeSeleccionada;
    private FacCaja cajaSeleccionada;
    private FacCaja cajaSeleccionadaTabla;
    private List<FacCaja> listaCajas;

    //---------------------------------------------------
    //-----------------VARIABLES ------------------------
    //---------------------------------------------------
    private String tituloTabCaja = "Datos Nueva Caja";
    private String idSedeSeleccionada;
    private String nombreCaja = "";
    private String codigoCaja = "";
    private String usuarioCaja = "";
    private double valorBaseCaja = 0;
    private boolean mostrarTabView = false;//ocultar controles si no se tiene escogida la caja

    //---------------------------------------------------
    //------------- FUNCIONES INICIALES  ----------------
    //---------------------------------------------------      
    @PostConstruct
    public void inicializar() {
    }
    public CajasMB() {
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void inicializarVariables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void consultar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //---------------------------------------------------
    //-----------------FUNCIONES CAJAS ------------------
    //---------------------------------------------------
    public void clickBtnNuevaCaja() {
        limpiarFormularioCajas();
        mostrarTabView = true;
    }

    public void limpiarFormularioCajas() {
        tituloTabCaja = "Datos Nueva Caja";
        cajaSeleccionada = null;
        codigoCaja = "";
        nombreCaja = "";
        valorBaseCaja = 0;
        usuarioCaja = "";
    }

    public void buscarCaja() {
        listaCajas = cajaFacade.findAll();
        RequestContext.getCurrentInstance().execute("PF('wvTablaCajas').clearFilters(); PF('wvTablaCajas').getPaginator().setPage(0); PF('dialogoBuscarCajas').show();");
    }

    public void cargarCaja() {//click cobre editar caja (carga los datos de la caja)
        if (cajaSeleccionadaTabla == null) {
            imprimirMensaje("Error", "No se ha seleccionado ninguna caja de la tabla", FacesMessage.SEVERITY_ERROR);
            return;
        }
        limpiarFormularioCajas();
        mostrarTabView = true;
        cajaSeleccionada = cajaFacade.find(cajaSeleccionadaTabla.getIdCaja());
        idSedeSeleccionada = cajaSeleccionada.getIdSede().getIdSede().toString();
        codigoCaja = cajaSeleccionada.getCodigoCaja();
        nombreCaja = cajaSeleccionada.getNombreCaja();
        usuarioCaja = cajaSeleccionada.getIdUsuario().getIdUsuario().toString();
        valorBaseCaja = cajaSeleccionada.getValorBaseDefecto();
        tituloTabCaja = "Datos Caja: " + nombreCaja;
        RequestContext.getCurrentInstance().execute("PF('dialogoBuscarCajas').hide(); PF('wvTablaCajas').clearFilters();");

    }

    public void eliminarCaja() {
        if (cajaSeleccionada == null) {
            imprimirMensaje("Error", "No se ha cargado ninguna caja", FacesMessage.SEVERITY_ERROR);
            return;
        }
        RequestContext.getCurrentInstance().execute("PF('dialogoEliminarCaja').show();");
    }

    public void confirmarEliminarCaja() {
        if (cajaSeleccionada == null) {
            imprimirMensaje("Error", "No se ha seleccionado ninguna caja", FacesMessage.SEVERITY_ERROR);
            return;
        }
        try {
            cajaFacade.remove(cajaSeleccionada);
            //recargarCajas();
            limpiarFormularioCajas();
            mostrarTabView = false;
            RequestContext.getCurrentInstance().update("IdFormCajas");
            imprimirMensaje("Correcto", "La caja ha sido eliminada", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            imprimirMensaje("Error", "La caja que intenta eliminar esta siendo usada por el sistema", FacesMessage.SEVERITY_ERROR);
        }

    }

    public void guardarCaja() {

        if (validacionCampoVacio(idSedeSeleccionada, "Sede")) {
            return;
        }
        if (validacionCampoVacio(codigoCaja, "Código")) {
            return;
        }
        if (validacionCampoVacio(nombreCaja, "Nombre")) {
            return;
        }
        if (validacionCampoVacio(usuarioCaja, "Usuario")) {
            return;
        }
        if (cajaSeleccionada == null) {
            guardarNuevaCaja();
        } else {
            actualizarCajaExistente();
        }
    }

    private void guardarNuevaCaja() {
        FacCaja nuevaCaja = new FacCaja();
        nuevaCaja.setCodigoCaja(codigoCaja);
        nuevaCaja.setNombreCaja(nombreCaja);
        nuevaCaja.setValorBaseDefecto(valorBaseCaja);
        nuevaCaja.setIdUsuario(usuariosFacade.find(Integer.parseInt(usuarioCaja)));
        nuevaCaja.setIdSede(sedeFacade.find(Integer.parseInt(idSedeSeleccionada)));
        nuevaCaja.setCerrada(true);
        cajaFacade.create(nuevaCaja);
        limpiarFormularioCajas();
        mostrarTabView = false;
        //recargarCajas();
        RequestContext.getCurrentInstance().update("IdFormCajas");
        imprimirMensaje("Correcto", "La caja ha sido creada.", FacesMessage.SEVERITY_INFO);
    }

    private void actualizarCajaExistente() {//realiza la actualizacion del consultorio        
        cajaSeleccionada.setCodigoCaja(codigoCaja);
        cajaSeleccionada.setNombreCaja(nombreCaja);
        cajaSeleccionada.setValorBaseDefecto(valorBaseCaja);
        cajaSeleccionada.setIdUsuario(usuariosFacade.find(Integer.parseInt(usuarioCaja)));
        cajaSeleccionada.setIdSede(sedeFacade.find(Integer.parseInt(idSedeSeleccionada)));
        cajaFacade.edit(cajaSeleccionada);
        limpiarFormularioCajas();
        //recargarCajas();
        RequestContext.getCurrentInstance().update("IdFormCajas");
        mostrarTabView = false;
        imprimirMensaje("Correcto", "La caja ha sido actualizada.", FacesMessage.SEVERITY_INFO);
    }

//    private void recargarCajas() {
//        if (sedeSeleccionada != null) {
//            sedeSeleccionada = sedeFacade.find(sedeSeleccionada.getIdSede());//se actulice
//            listaCajas = sedeSeleccionada.getFacCajaList();
//        }
//    }
//    public void cambiaSede() {
//        sedeSeleccionada = null;
//        if (validarNoVacio(idSedeSeleccionada)) {
//            sedeSeleccionada = sedeFacade.find(Integer.parseInt(idSedeSeleccionada));
//            if (sedeSeleccionada != null) {
//                listaCajas = sedeSeleccionada.getFacCajaList();
//            } else {
//                listaCajas = new ArrayList<>();
//            }
//        }
//    }
    //---------------------------------------------------
    //-----------------FUNCIONES GET Y SET --------------
    //---------------------------------------------------    
    public CfgSede getSedeSeleccionada() {
        return sedeSeleccionada;
    }

    public void setSedeSeleccionada(CfgSede sedeSeleccionada) {
        this.sedeSeleccionada = sedeSeleccionada;
    }

    public List<FacCaja> getListaCajas() {
        return listaCajas;
    }

    public void setListaCajas(List<FacCaja> listaCajas) {
        this.listaCajas = listaCajas;
    }

    public FacCaja getCajaSeleccionada() {
        return cajaSeleccionada;
    }

    public void setCajaSeleccionada(FacCaja cajaSeleccionada) {
        this.cajaSeleccionada = cajaSeleccionada;
    }

    public String getUsuarioCaja() {
        return usuarioCaja;
    }

    public void setUsuarioCaja(String usuarioCaja) {
        this.usuarioCaja = usuarioCaja;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getCodigoCaja() {
        return codigoCaja;
    }

    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    public double getValorBaseCaja() {
        return valorBaseCaja;
    }

    public void setValorBaseCaja(double valorBaseCaja) {
        this.valorBaseCaja = valorBaseCaja;
    }

    public String getTituloTabCaja() {
        return tituloTabCaja;
    }

    public void setTituloTabCaja(String tituloTabCaja) {
        this.tituloTabCaja = tituloTabCaja;
    }

    public String getIdSedeSeleccionada() {
        return idSedeSeleccionada;
    }

    public void setIdSedeSeleccionada(String idSedeSeleccionada) {
        this.idSedeSeleccionada = idSedeSeleccionada;
    }

    public boolean isMostrarTabView() {
        return mostrarTabView;
    }

    public void setMostrarTabView(boolean mostrarTabView) {
        this.mostrarTabView = mostrarTabView;
    }

    public FacCaja getCajaSeleccionadaTabla() {
        return cajaSeleccionadaTabla;
    }

    public void setCajaSeleccionadaTabla(FacCaja cajaSeleccionadaTabla) {
        this.cajaSeleccionadaTabla = cajaSeleccionadaTabla;
    }
}
