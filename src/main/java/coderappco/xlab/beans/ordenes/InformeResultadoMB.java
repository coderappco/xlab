/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.ordenes;

import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.entidades.ListaInforme;
import coderappco.xlab.entidades.XlabEstudio;
import coderappco.xlab.entidades.XlabOrden;
import coderappco.xlab.facades.XlabEstudioFacade;
import coderappco.xlab.facades.XlabOrdenFacade;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "informeResultadoMB")
@SessionScoped
public class InformeResultadoMB implements Serializable {

    @EJB
    private XlabEstudioFacade estudioFacade;
    @EJB
    private XlabOrdenFacade ordenFacade;
    
    private List<ListaInforme> listaOrdenes;
    private Date fechaDesde;
    private Date fechaHasta;
    private XlabEstudio estudio;
    public InformeResultadoMB() {
    }
    @PostConstruct
    public void inicializar(){
        fechaDesde = new Date();
        fechaHasta = new Date();
        listaOrdenes = ordenFacade.getInformeResultado(fechaDesde, fechaHasta, 0,SessionUtil.getEmpresa());
        ///System.out.println(listaOrdenes.get(0).getNroOrden());
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public XlabEstudio getEstudio() {
        return estudio;
    }

    public void setEstudio(XlabEstudio estudio) {
        this.estudio = estudio;
    }

    public List<ListaInforme> getListaOrdenes() {
        return listaOrdenes;
    }

    public void setListaOrdenes(List<ListaInforme> listaOrdenes) {
        this.listaOrdenes = listaOrdenes;
    }
    public List<String> autocompletarEstudio(String txt) {//retorna una lista con los diagnosticos que contengan el parametro txt
        if (txt != null && txt.length() > 2) {
            return estudioFacade.autocompletarDiagnostico(txt);
        } else {
            return null;
        }
    }
    
    public void validarEstudio(SelectEvent event){
         try {
            estudio = estudioFacade.buscarPorNombre(event.getObject().toString());
        } catch (Exception e) {
        }
    }

    public void exportar(){
      FacesContext context = FacesContext.getCurrentInstance();
        try{
            String baseURL = context.getExternalContext().getRequestContextPath();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            String fechaIni = sd.format(fechaDesde);
            String fechaFin = sd.format(fechaHasta);
            String url =  baseURL +"/InformeResultadoCSV?url="+baseURL+"&fechaInicial="+fechaIni+"&fechaFinal="+fechaFin+"&empresa="+SessionUtil.getEmpresa();
            String encodeURL = context.getExternalContext().encodeResourceURL(url);
        
            context.getExternalContext().redirect(encodeURL);
        }  catch(Exception e)    {
            e.printStackTrace();
        }
    }
}
