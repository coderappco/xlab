/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.XlabConsecutivos;
import coderappco.xlab.facades.XlabConsecutivosFacade;
import coderappco.xlab.utilidades.Constante;
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
@Named(value = "consecutivoOrdenMB")
@SessionScoped
public class ConsecutivoOrdenMB extends Controlador implements Serializable {

    @EJB
    private XlabConsecutivosFacade consecutivoFacade;
    
    private XlabConsecutivos consecutivo;
    /**
     * Creates a new instance of ConsecutivoOrdenMB
     */
    public ConsecutivoOrdenMB() {
    }

    @PostConstruct
    public void inicializar(){
        consecutivo = consecutivoFacade.getName(Constante.ORDEN);
        if(consecutivo==null)consecutivo=new XlabConsecutivos();
    }
    @Override
    public void nuevo() {
        
    }

    @Override
    public void guardar() {
        try {
            if(consecutivo.getId()==null)
            consecutivoFacade.create(consecutivo);
        else 
            consecutivoFacade.edit(consecutivo);
        
        SessionUtil.addInfoMessage("Guardado", "Guardado Correctamente");
        } catch (Exception e) {
            SessionUtil.addErrorMessage("Error", e.toString());
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

    public XlabConsecutivos getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(XlabConsecutivos consecutivo) {
        this.consecutivo = consecutivo;
    }
    
}
