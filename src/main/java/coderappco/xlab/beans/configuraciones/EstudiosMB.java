/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.XlabEstudio;
import coderappco.xlab.entidades.XlabPrueba;
import coderappco.xlab.facades.XlabEstudioFacade;
import coderappco.xlab.facades.XlabPruebaFacade;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "estudiosMB")
@SessionScoped
public class EstudiosMB extends Controlador implements Serializable {
    @EJB
    private XlabPruebaFacade    pruebaFacade;
    @EJB
    private XlabEstudioFacade   estudioFacade;
    
    private boolean renderForm;
    private XlabEstudio estudio;
    private XlabPrueba prueba;
    private List<XlabPrueba> lstPruebas;
    private List<XlabEstudio> lstEstudios;
    private List<XlabEstudio> lstEstudiosFiltro;
    private String codigoPrueba;
    public EstudiosMB() {
    }
    @PostConstruct
    public void inicializar(){
        estudio = new XlabEstudio();
        renderForm = false;
        lstEstudios = estudioFacade.findAll();
        lstEstudiosFiltro =null;
        lstPruebas = new ArrayList<>();
        prueba = new XlabPrueba();
    }

    @Override
    public void nuevo() {
        inicializarVariables();
        renderForm = true;
    }

    @Override
    public void guardar() {
        try {
            estudio.setXlabPruebaList(lstPruebas);
            if(estudio.getId()==null){
                estudioFacade.create(estudio);
            }else estudioFacade.edit(estudio);
            SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente");
            inicializarVariables();
        } catch (Exception e) {
            SessionUtil.addErrorMessage("Error al guardar", e.getLocalizedMessage());
        }
    }

    @Override
    public void inicializarVariables() {
         estudio = new XlabEstudio();
        renderForm = false;
        lstEstudios = estudioFacade.findAll();
        lstEstudiosFiltro =null;
        lstPruebas = new ArrayList<>();
        prueba = new XlabPrueba();
    }

    @Override
    public void cancelar() {
        inicializarVariables();
    }

    @Override
    public void consultar(Object o) {
        estudio = (XlabEstudio)o;
        renderForm =true;
        lstPruebas = estudio.getXlabPruebaList();
    }

    @Override
    public void eliminar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void onItemSelect(SelectEvent event) {
        codigoPrueba = event.getObject().toString();
    }

    public void adicionar(){
        if(!codigoPrueba.equals("")){
            prueba = pruebaFacade.getPrueba(codigoPrueba);
            lstPruebas.add(prueba);
        }
        RequestContext.getCurrentInstance().execute("PF('pruebaDialog').hide();");
    }
    public void openDialog(){
        prueba   = new XlabPrueba();
        codigoPrueba = "";
    }
    public void eliminarPrueba(XlabPrueba p){
        lstPruebas.remove(p);
    }
            
        
    public List<String> autocompletarPruebas(String txt) {//retorna una lista con los diagnosticos que contengan el parametro txt
        if (txt != null && txt.length() > 2) {
            return pruebaFacade.autocompletarAreas(txt);
        } else {
            return null;
        }
    }
    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public XlabEstudio getEstudio() {
        return estudio;
    }

    public void setEstudio(XlabEstudio estudio) {
        this.estudio = estudio;
    }

    public List<XlabPrueba> getLstPruebas() {
        return lstPruebas;
    }

    public void setLstPruebas(List<XlabPrueba> lstPruebas) {
        this.lstPruebas = lstPruebas;
    }

    public List<XlabEstudio> getLstEstudios() {
        return lstEstudios;
    }

    public void setLstEstudios(List<XlabEstudio> lstEstudios) {
        this.lstEstudios = lstEstudios;
    }

    public List<XlabEstudio> getLstEstudiosFiltro() {
        return lstEstudiosFiltro;
    }

    public void setLstEstudiosFiltro(List<XlabEstudio> lstEstudiosFiltro) {
        this.lstEstudiosFiltro = lstEstudiosFiltro;
    }

    public XlabPrueba getPrueba() {
        return prueba;
    }

    public void setPrueba(XlabPrueba prueba) {
        this.prueba = prueba;
    }

    public String getCodigoPrueba() {
        return codigoPrueba;
    }

    public void setCodigoPrueba(String codigoPrueba) {
        this.codigoPrueba = codigoPrueba;
    }
    
}
