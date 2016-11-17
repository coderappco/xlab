/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.informes;

import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.utilidades.Constante;
import coderappco.xlab.utilidades.DBConnector;
import coderappco.xlab.utilidades.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "informesMB")
@SessionScoped
public class InformesMB implements Serializable {

    private List<SelectItem> listaReportes;
    private int idReporte;
    private List<SelectItem> listaAreas;
    private int idArea;
    private Date fechaDesde;
    private Date fechaHasta;
    private boolean codigoPruebas;
    private boolean pruebasPendientes;
    
    @EJB
    private CfgClasificacionesFacade areaFacade;
    /**
     * Creates a new instance of InformesMB
     */
    public InformesMB() {
    }

    public List<SelectItem> getListaReportes() {
        if(listaReportes==null){
            listaReportes = new ArrayList<>();
            listaReportes.add(new SelectItem(0,"Seleccione Reporte"));
            listaReportes.add(new SelectItem(1,"Listado de pruebas por área"));
        }
        return listaReportes;
    }

    public void setListaReportes(List<SelectItem> listaReportes) {
        this.listaReportes = listaReportes;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public List<SelectItem> getListaAreas() {
        if(listaAreas == null){
            listaAreas = new ArrayList<>();
            List<CfgClasificaciones> lstClasificaciones = areaFacade.buscarPorMaestro("GrupoArea");
            for(CfgClasificaciones cf: lstClasificaciones){
                listaAreas.add(new SelectItem(cf.getId(),cf.getDescripcion()));
            }
        }
        return listaAreas;
    }

    public void setListaAreas(List<SelectItem> listaAreas) {
        this.listaAreas = listaAreas;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public Date getFechaDesde() {
        if(fechaDesde==null)fechaDesde = new Date();
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        if(fechaHasta==null)fechaHasta = new Date();
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public boolean isCodigoPruebas() {
        codigoPruebas = true;
        return codigoPruebas;
    }

    public void setCodigoPruebas(boolean codigoPruebas) {
        this.codigoPruebas = codigoPruebas;
    }

    public boolean isPruebasPendientes() {
        pruebasPendientes = true;
        return pruebasPendientes;
    }

    public void setPruebasPendientes(boolean pruebasPendientes) {
        this.pruebasPendientes = pruebasPendientes;
    }

    public void printReporte(){
        switch(idReporte){
            case 1:
                reporteXArea();
                break;
        }
    }
    
    private void reporteXArea(){
        try {
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse httpServletResponse = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            try (ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream()) {
            httpServletResponse.setContentType("application/pdf");
                ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            String ruta ;
            ruta = servletContext.getRealPath("/informes/listatrabajos/r_master_pruebas_por_area.jasper");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("P_EMPRESA", SessionUtil.getEmpresa());
            parametros.put("P_ESTADO", Constante.PRUEBA_PENDIENTE);
            parametros.put("P_GRUPO_AREA", idArea);
            if(codigoPruebas)parametros.put("SUBREPORT_DIR", "r_prueba_orden_grupo_alfa_num.jasper");
            else parametros.put("SUBREPORT_DIR", "r_prueba_orden_grupo_no_prueba.jasper");
            try{
                parametros.put("P_FECHA_INICIO", fechaDesde);
                parametros.put("P_FECHA_FIN", fechaHasta);
                Connection con = DBConnector.getInstance().getConnection();
                JasperPrint jasperPrint = JasperFillManager.fillReport(ruta, parametros, con);
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                FacesContext.getCurrentInstance().responseComplete();
                if(con!=null)con.close();
                DBConnector.getInstance().closeConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
