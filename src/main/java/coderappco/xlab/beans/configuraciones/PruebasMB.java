/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgUnidad;
import coderappco.xlab.entidades.XlabPrueba;
import coderappco.xlab.entidades.XlabPruebaReferencia;
import coderappco.xlab.entidades.XlabTipoTecnica;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.facades.CfgUnidadFacade;
import coderappco.xlab.facades.XlabPruebaFacade;
import coderappco.xlab.facades.XlabTipoTecnicaFacade;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "pruebasMB")
@SessionScoped
public class PruebasMB extends Controlador implements Serializable {

    @Resource
    private UserTransaction utx;
    @EJB
    CfgClasificacionesFacade clasificacionFacade;
    @EJB
    CfgUnidadFacade unidadFacade;
    @EJB
    XlabPruebaFacade pruebaFacade;
    @EJB
    XlabTipoTecnicaFacade tipoTecnicaFacade;
           
    
    private boolean renderForm;
    private List<XlabPrueba> listaPruebas;
    private List<XlabPrueba> listaPruebasFiltro;
    
    private Collection<SelectItem> lstUnidades;
    private List<SelectItem> lstTipoTecnicas;
    private XlabPrueba prueba;
    private List<XlabPruebaReferencia> lstReferencias;
    private String codigoArea;
    private boolean renderFormula;
    public PruebasMB() {
    }

    @PostConstruct
    public void inicializar(){
        listaPruebas = new ArrayList<>();
        listaPruebas = pruebaFacade.findAll();
        listaPruebasFiltro = null;
        prueba = new XlabPrueba();
        renderForm=false;
        lstReferencias = new ArrayList<>();
        codigoArea  ="";
        renderFormula= false;
    }
    @Override
    public void nuevo() {
        inicializarVariables();
        renderForm=true;
    }

    @Override
    public void guardar() {
        try {
            if(validarCampos()){
                utx.begin();
                if(prueba.getTipoTecnica()!=null){
                    if(prueba.getTipoTecnica().getId()==0)prueba.setTipoTecnica(null);
                }
                if(prueba.getUnidadPrueba().getId()==0)prueba.setUnidadPrueba(null);
                prueba.setXlabPruebaReferenciaList(lstReferencias);
                //Obtenemos area
                prueba.setGrupoArea(clasificacionFacade.getAreaCodigo(codigoArea));
                if(prueba.getId()==null){
                    pruebaFacade.create(prueba);
                }else{
                    pruebaFacade.edit(prueba);
                }
                utx.commit();
                SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente");
                inicializarVariables();
                listaPruebas = pruebaFacade.findAll();
            }
        }catch(NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e){
            SessionUtil.addErrorMessage("Error al guardar", "El código ya se encuentra registrado");
        }
    }

    @Override
    public void inicializarVariables() {
        renderForm = false;
        listaPruebasFiltro = null;
        prueba = new XlabPrueba();
        lstReferencias = new ArrayList<>();
        XlabPruebaReferencia xl = new XlabPruebaReferencia();
        xl.setTipo("M");
        xl.setNombreTipo("Mujeres");
        xl.setMinimoReferencia(0d);
        xl.setMaximoReferencia(0d);
        xl.setPruebaId(prueba);
        lstReferencias.add(xl);
        xl = new XlabPruebaReferencia();
        xl.setTipo("H");
        xl.setNombreTipo("Hombre");
        xl.setMinimoReferencia(0d);
        xl.setMaximoReferencia(0d);
        xl.setPruebaId(prueba);
        lstReferencias.add(xl);
        xl = new XlabPruebaReferencia();
        xl.setTipo("N");
        xl.setNombreTipo("Niños");
        xl.setMinimoReferencia(0d);
        xl.setMaximoReferencia(0d);
        xl.setPruebaId(prueba);
        lstReferencias.add(xl);
        codigoArea  ="";
        renderFormula = false;
    }

    @Override
    public void cancelar() {
        inicializarVariables();
    }

    @Override
    public void consultar(Object o) {
        prueba = (XlabPrueba)o;
        lstReferencias = prueba.getXlabPruebaReferenciaList();
        codigoArea = prueba.getGrupoArea().getCodigo()+" - "+prueba.getGrupoArea().getDescripcion();
        renderForm=true;
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

    public List<XlabPrueba> getListaPruebas() {
        return listaPruebas;
    }

    public void setListaPruebas(List<XlabPrueba> listaPruebas) {
        this.listaPruebas = listaPruebas;
    }

    public List<XlabPrueba> getListaPruebasFiltro() {
        return listaPruebasFiltro;
    }

    public void setListaPruebasFiltro(List<XlabPrueba> listaPruebasFiltro) {
        this.listaPruebasFiltro = listaPruebasFiltro;
    }

    public XlabPrueba getPrueba() {
        return prueba;
    }

    public void setPrueba(XlabPrueba prueba) {
        this.prueba = prueba;
    }

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public List<XlabPruebaReferencia> getLstReferencias() {
        return lstReferencias;
    }

    public void setLstReferencias(List<XlabPruebaReferencia> lstReferencias) {
        this.lstReferencias = lstReferencias;
    }

    public Collection<SelectItem> getLstUnidades() {
        if(lstUnidades==null){
            try {
                List<CfgUnidad> listaUnidades = unidadFacade.findAll();
                lstUnidades = new ArrayList<>();
                lstUnidades.add(new SelectItem("0","Seleccione unidad"));
                for(CfgUnidad u:listaUnidades){
                    lstUnidades.add(new SelectItem(u.getId(),u.getCodigo()));
                }
            } catch (Exception e) {
            }
        }
        return lstUnidades;
    }

    public void setLstUnidades(Collection<SelectItem> lstUnidades) {
        this.lstUnidades = lstUnidades;
    }

    public List<String> autocompletarPruebas(String txt) {//retorna una lista con los diagnosticos que contengan el parametro txt
        if (txt != null && txt.length() > 2) {
            return clasificacionFacade.autocompletarAreas(txt);
        } else {
            return null;
        }
    }
   
    private boolean validarCampos(){
        if(prueba.getCodigo()==null){
            SessionUtil.addErrorMessage("Error al guardar", "Digite código");
            return false;
        }else if(prueba.getNombre()==null){
            SessionUtil.addErrorMessage("Error al guardar", "Digite Nombre");
            return false;
        }else if(prueba.getFormatoResultado()==null){
            SessionUtil.addErrorMessage("Error al guardar", "Seleccione Formato resultado");
            return false;
        }else if(codigoArea==null){
            SessionUtil.addErrorMessage("Error al guardar", "Digite Area");
            return false;
        }else if(codigoArea.equals("")){
            SessionUtil.addErrorMessage("Error al guardar", "Digite Area");
            return false;
        }else if(prueba.getDecimalesPrueba()==null){
            SessionUtil.addErrorMessage("Error al guardar", "Digite Decimales de la prueba");
            return false;
        }
        return true;
    }
    
    public void validarTipo(){
        if(prueba.getFormatoResultado().equals("F")){
            renderFormula = true;
        }else renderFormula = false;
    }

    public boolean isRenderFormula() {
        return renderFormula;
    }

    public void setRenderFormula(boolean renderFormula) {
        this.renderFormula = renderFormula;
    }

    public List<SelectItem> getLstTipoTecnicas() {
        if(lstTipoTecnicas==null){
            List<XlabTipoTecnica> lst = tipoTecnicaFacade.tecnicasActivas();
            lstTipoTecnicas =  new ArrayList<>();
            for(XlabTipoTecnica x:lst){
                lstTipoTecnicas.add(new SelectItem(x.getId(),x.getNombre()));
            }
        }
        return lstTipoTecnicas;
    }

    public void setLstTipoTecnicas(List<SelectItem> lstTipoTecnicas) {
        this.lstTipoTecnicas = lstTipoTecnicas;
    }
}
