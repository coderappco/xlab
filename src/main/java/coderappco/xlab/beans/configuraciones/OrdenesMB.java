/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "ordenesMB")
@SessionScoped
public class OrdenesMB extends Controlador implements Serializable {

    /**
     * Creates a new instance of OrdenesMB
     */
    @EJB
    private CfgClasificacionesFacade clasificacionFacade;
    
    private CfgClasificaciones clasificacion;
    public OrdenesMB() {
    }

    @PostConstruct
    public void inicializar(){
        clasificacion = clasificacionFacade.buscarPorCodigo("1", ClasificacionesEnum.Ordenes.toString());
    }
    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            clasificacionFacade.edit(clasificacion);
            SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("Error", e.getLocalizedMessage());
        }
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

    public CfgClasificaciones getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(CfgClasificaciones clasificacion) {
        this.clasificacion = clasificacion;
    }
    
}
