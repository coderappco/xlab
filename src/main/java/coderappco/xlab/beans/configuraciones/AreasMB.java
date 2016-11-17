/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.entidades.CfgMaestrosClasificaciones;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "areasMB")
@SessionScoped
public class AreasMB extends Controlador implements Serializable {

    @EJB
    CfgClasificacionesFacade clasificacionFacade;
    
    private List<CfgClasificaciones> lstClasificaciones;
    private List<CfgClasificaciones> lstClasificacionesFiltro;
    private boolean renderForm;
    private CfgClasificaciones area;
    public AreasMB() {
    }

    @PostConstruct
    public void inicializar(){
        renderForm=false;
        lstClasificacionesFiltro = null;
        area = new CfgClasificaciones();
        CfgMaestrosClasificaciones maestro = new CfgMaestrosClasificaciones();
        maestro.setMaestro(ClasificacionesEnum.GrupoArea.toString());
        area.setMaestro(maestro);
    }
    @Override
    public void nuevo() {
        inicializarVariables();
        renderForm = true;
    }

    @Override
    public void guardar() {
        try {
            
            CfgClasificaciones a = clasificacionFacade.buscarPorCodigo(area.getCodigo(),ClasificacionesEnum.GrupoArea.toString());
            if(area.getId()==0){
                if(a==null){
                    clasificacionFacade.create(area);
                    SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente");
                    inicializarVariables();
                }else{
                    SessionUtil.addErrorMessage("Error al guardar", "Ya se encuentra este código registrado");
                }
            }else{
                if(a!=null){
                    if(a.getId().equals(area.getId())){
                    clasificacionFacade.edit(area);
                    SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente");
                    inicializarVariables();
                }else{
                    SessionUtil.addErrorMessage("Error al guardar", "Ya se encuentra este código registrado");
                }
               }else{
                    clasificacionFacade.edit(area);
                    SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente");
                    inicializarVariables();
                }
                
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inicializarVariables() {
        renderForm=false;
        lstClasificacionesFiltro = null;
        area = new CfgClasificaciones();
        CfgMaestrosClasificaciones maestro = new CfgMaestrosClasificaciones();
        maestro.setMaestro(ClasificacionesEnum.GrupoArea.toString());
        area.setMaestro(maestro);
        renderForm = false;
    }

    @Override
    public void cancelar() {
        inicializarVariables();
    }

    @Override
    public void consultar(Object o) {
        area = (CfgClasificaciones)o;
        renderForm = true;
    }

    @Override
    public void eliminar(Object o) {
        try {
            
        } catch (Exception e) {
        }
    }

    public List<CfgClasificaciones> getLstClasificaciones() {
        if(lstClasificaciones==null){
            lstClasificaciones = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.GrupoArea.toString());
        }
        return lstClasificaciones;
    }

    public void setLstClasificaciones(List<CfgClasificaciones> lstClasificaciones) {
        this.lstClasificaciones = lstClasificaciones;
    }

    public List<CfgClasificaciones> getLstClasificacionesFiltro() {
        return lstClasificacionesFiltro;
    }

    public void setLstClasificacionesFiltro(List<CfgClasificaciones> lstClasificacionesFiltro) {
        this.lstClasificacionesFiltro = lstClasificacionesFiltro;
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public CfgClasificaciones getArea() {
        return area;
    }

    public void setArea(CfgClasificaciones area) {
        this.area = area;
    }
    
    
}
