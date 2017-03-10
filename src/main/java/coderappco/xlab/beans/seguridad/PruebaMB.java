/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.seguridad;

import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "pruebaMB")
@ViewScoped
public class PruebaMB implements Serializable {

    @EJB
    private CfgClasificacionesFacade facade;
    
    private CfgClasificaciones clasificacion;
    private List<CfgClasificaciones> lista;
    /**
     * Creates a new instance of PruebaMB
     */
    public PruebaMB() {
        
        
    }
    
    @PostConstruct
    public void init(){
        lista = facade.buscarPorMaestro(ClasificacionesEnum.GrupoSanguineo.toString());
        clasificacion = new CfgClasificaciones();
    }
    public String toDo(){
        return "otra";
    }

    public CfgClasificaciones getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(CfgClasificaciones clasificacion) {
        this.clasificacion = clasificacion;
    }
    
    public void validar(){
        System.out.println(clasificacion);
    }

    public List<CfgClasificaciones> getLista() {
        return lista;
    }

    public void setLista(List<CfgClasificaciones> lista) {
        this.lista = lista;
    }
    
}
