/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.entidades.CfgEmpresa;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.facades.CfgEmpresaFacade;
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
@Named(value = "empresaMB")
@SessionScoped
public class EmpresaMB extends Controlador implements Serializable {

    @EJB
    private CfgEmpresaFacade empresaFacade;
    @EJB
    private CfgClasificacionesFacade clasificacionFacade;
    private CfgEmpresa empresa;
    
    private List<SelectItem> listaTipoIdentificacion;
    private List<SelectItem> listaDpto;
    private List<SelectItem> listaMunicipio;
    /**
     * Creates a new instance of EmpresaMB
     */
    
    @PostConstruct
    public void inicializar(){
        empresa = empresaFacade.find(1);//primer y unica empresa
        cargarMunicipios();
    }
    public EmpresaMB() {
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            empresaFacade.edit(empresa);
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

    public CfgEmpresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(CfgEmpresa empresa) {
        this.empresa = empresa;
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
        if (empresa.getCodDepartamento().getId() != 0) {
            List<CfgClasificaciones> listaM = clasificacionFacade.buscarMunicipioPorDepartamento(clasificacionFacade.find(empresa.getCodDepartamento().getId()).getCodigo());
            listaMunicipio.clear();
            int i = 0;
            for (CfgClasificaciones mun : listaM) {
                if(i==0)empresa.setCodMunicipio(mun);
                listaMunicipio.add(new SelectItem(mun.getId(), mun.getDescripcion()));
                i++;
            }
            
        }
    }
    
}
