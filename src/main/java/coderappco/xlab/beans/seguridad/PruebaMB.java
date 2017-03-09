/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.seguridad;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "pruebaMB")
@SessionScoped
public class PruebaMB implements Serializable {

    /**
     * Creates a new instance of PruebaMB
     */
    public PruebaMB() {
    }
    
    public String toDo(){
        return "otra";
    }
    
}
