/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.entidades.FacAdministradora;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.facades.FacAdministradoraFacade;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "administradoraMB")
@SessionScoped
public class AdministradoraMB extends Controlador implements Serializable {

    
    @EJB
    private FacAdministradoraFacade  administradoraFacade;
    @EJB
    private CfgClasificacionesFacade clasificacionFacade;
    
    private FacAdministradora administradora;
    
    private List<FacAdministradora> lstaAdminitradora;
    private List<FacAdministradora> lstaAdminitradoraFiltro;
    private List<SelectItem> listaTipoIdentificacion;
    private List<SelectItem> listaTipoEntidad;
    private List<SelectItem> listaDpto;
    private List<SelectItem> listaMunicipio;
    
    private boolean renderForm;
    public AdministradoraMB() {
    }
    @PostConstruct
    public void inicializar(){
        administradora = new FacAdministradora();
        lstaAdminitradora = new ArrayList<>();
        lstaAdminitradoraFiltro = null;
        setRenderForm(false);
        lstaAdminitradora = administradoraFacade.findAll();
    }
    @Override
    public void nuevo() {
        administradora = new FacAdministradora();
        renderForm = true;
    }

    @Override
    public void guardar() {
        try {
            if(administradora.getIdAdministradora()==null){
                administradoraFacade.create(administradora);
            }else{
                administradoraFacade.edit(administradora);
            }
            inicializarVariables();
            SessionUtil.addInfoMessage("Guardar", "Guardado correctamente");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("Error", e.getLocalizedMessage());
        }
    }

    @Override
    public void inicializarVariables() {
        administradora = new FacAdministradora();
        lstaAdminitradora = new ArrayList<>();
        lstaAdminitradoraFiltro = null;
        renderForm = false;
        lstaAdminitradora = administradoraFacade.findAll();
    }

    @Override
    public void cancelar() {
        inicializar();
    }

    @Override
    public void consultar(Object o) {
        administradora = (FacAdministradora)o;
        renderForm = true;
        cargarMunicipios();
    }

    @Override
    public void eliminar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public FacAdministradora getAdministradora() {
        return administradora;
    }

    public void setAdministradora(FacAdministradora administradora) {
        this.administradora = administradora;
    }

    public List<FacAdministradora> getLstaAdminitradora() {
        return lstaAdminitradora;
    }

    public void setLstaAdminitradora(List<FacAdministradora> lstaAdminitradora) {
        this.lstaAdminitradora = lstaAdminitradora;
    }

    public List<FacAdministradora> getLstaAdminitradoraFiltro() {
        return lstaAdminitradoraFiltro;
    }

    public void setLstaAdminitradoraFiltro(List<FacAdministradora> lstaAdminitradoraFiltro) {
        this.lstaAdminitradoraFiltro = lstaAdminitradoraFiltro;
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
    public void cargarMunicipios() {
        listaMunicipio = new ArrayList<>();
        if (administradora.getCodigoDepartamento().getId() != 0) {
            List<CfgClasificaciones> listaM = clasificacionFacade.buscarMunicipioPorDepartamento(clasificacionFacade.find(administradora.getCodigoDepartamento().getId()).getCodigo());
            listaMunicipio.clear();
            int i = 0;
            for (CfgClasificaciones mun : listaM) {
                if(i==0)administradora.setCodigoMunicipio(mun);
                listaMunicipio.add(new SelectItem(mun.getId(), mun.getDescripcion()));
                i++;
            }
            
        }
    }

    public List<SelectItem> getListaTipoEntidad() {
        if(listaTipoEntidad==null){
            listaTipoEntidad = new ArrayList<>();
            List<CfgClasificaciones> lstEntidad = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.TipoAdministradora.toString());
            for(CfgClasificaciones c:lstEntidad){
                listaTipoEntidad.add(new SelectItem(c.getId(),c.getDescripcion()));
            }
        }
        return listaTipoEntidad;
    }

    public void setListaTipoEntidad(List<SelectItem> listaTipoEntidad) {
        this.listaTipoEntidad = listaTipoEntidad;
    }
    
}
