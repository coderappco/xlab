/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgCorreo;
import coderappco.xlab.facades.CfgCorreoFacade;
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
@Named(value = "emailMB")
@SessionScoped
public class EmailMB extends Controlador implements Serializable {

    @EJB
    private CfgCorreoFacade correoFacade;
    
    private CfgCorreo correo;
    
    public EmailMB() {
    }
    
    @PostConstruct
    public void inicializar(){
        correo = correoFacade.find(1);
        if(correo==null){
            correo=new CfgCorreo();
            correo.setId(1);
            correo.setEmail("");
            correo.setHost("");
            correo.setPort("");
            correo.setPassword("");
            correoFacade.create(correo);
        }
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            correoFacade.edit(correo);
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

    public CfgCorreo getCorreo() {
        return correo;
    }

    public void setCorreo(CfgCorreo correo) {
        this.correo = correo;
    }
    
}
