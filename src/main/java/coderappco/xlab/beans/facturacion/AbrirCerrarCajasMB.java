/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.facturacion;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgUsuarios;
import coderappco.xlab.entidades.FacCaja;
import coderappco.xlab.entidades.FacMovimientoCaja;
import coderappco.xlab.facades.CfgUsuariosFacade;
import coderappco.xlab.facades.FacCajaFacade;
import coderappco.xlab.facades.FacMovimientoCajaFacade;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "abrirCerrarCajasMB")
@SessionScoped
public class AbrirCerrarCajasMB extends Controlador implements Serializable {

    @EJB
    FacCajaFacade cajaFacade;
    @EJB
    CfgUsuariosFacade usuariosFacade;
    @EJB
    FacMovimientoCajaFacade movimientoCajaFacade;
    
    private FacCaja cajaSeleccionada;
    private CfgUsuarios usuarioActual;
    
    private String tituloTab = "";
    private String nombreCaja = "";
    private String codigoCaja = "";
    private double valorBaseCaja = 0;
    private boolean cajaAsignada = false;//determinar si el usurio tiene caja asignada
    private boolean cajaCerrada = false;//determinar si la caja esta cerrada=true o abierta=false
    
    @PostConstruct
    public void inicializar(){
        cajaSeleccionada=null;
         usuarioActual = usuariosFacade.find(SessionUtil.getUserLog().intValue());
             cajaAsignada = true;
                cajaSeleccionada = cajaFacade.find(usuarioActual.getFacCajaList().get(0).getIdCaja());
                cajaCerrada = cajaSeleccionada.getCerrada();
                codigoCaja = cajaSeleccionada.getCodigoCaja();
                nombreCaja = cajaSeleccionada.getNombreCaja();
                if (cajaCerrada) {
                    tituloTab = "Abrir caja";
                    valorBaseCaja = cajaSeleccionada.getValorBaseDefecto();
                } else {
                    tituloTab = "Cerrar caja";
                    valorBaseCaja = 0;
                }
         
         
                 
    }
    public AbrirCerrarCajasMB() {
    }
    
    public void abrirCaja() {
        if (cajaSeleccionada != null) {
            cajaSeleccionada.setCerrada(false);
            cajaFacade.edit(cajaSeleccionada);
            FacMovimientoCaja movimiento = new FacMovimientoCaja();
            movimiento.setIdCaja(cajaSeleccionada);
            movimiento.setFecha(new Date());
            movimiento.setValor(valorBaseCaja);
            movimiento.setAbrirCaja(true);//se abre caja
            movimientoCajaFacade.create(movimiento);
            inicializar();
            RequestContext.getCurrentInstance().update("IdFormCajas");
            SessionUtil.addInfoMessage("Correcto", "La caja ha sido abierta");
        } else {
            SessionUtil.addErrorMessage("Error", "No se ha seleccionado una caja");
        }
    }

    public void cerrarCaja() {
        if (cajaSeleccionada != null) {
            cajaSeleccionada.setCerrada(true);
            cajaFacade.edit(cajaSeleccionada);
            FacMovimientoCaja movimiento = new FacMovimientoCaja();
            movimiento.setIdCaja(cajaSeleccionada);
            movimiento.setFecha(new Date());
            movimiento.setValor(valorBaseCaja);
            movimiento.setAbrirCaja(false);//se cierra caja
            movimientoCajaFacade.create(movimiento);
            inicializar();
            RequestContext.getCurrentInstance().update("IdFormCajas");
            SessionUtil.addInfoMessage("Correcto", "La caja ha sido cerrada");
        } else {
            SessionUtil.addErrorMessage("Error", "No se ha seleccionado una caja");
        }
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

    public FacCaja getCajaSeleccionada() {
        return cajaSeleccionada;
    }

    public void setCajaSeleccionada(FacCaja cajaSeleccionada) {
        this.cajaSeleccionada = cajaSeleccionada;
    }

    public CfgUsuarios getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(CfgUsuarios usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getTituloTab() {
        return tituloTab;
    }

    public void setTituloTab(String tituloTab) {
        this.tituloTab = tituloTab;
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

    public boolean isCajaAsignada() {
        return cajaAsignada;
    }

    public void setCajaAsignada(boolean cajaAsignada) {
        this.cajaAsignada = cajaAsignada;
    }

    public boolean isCajaCerrada() {
        return cajaCerrada;
    }

    public void setCajaCerrada(boolean cajaCerrada) {
        this.cajaCerrada = cajaCerrada;
    }
    
}
