/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.CfgUnidad;
import javax.ejb.Stateless;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class CfgUnidadFacade extends AbstractFacade<CfgUnidad> {
    public CfgUnidadFacade() {
        super(CfgUnidad.class);
    }
    
}
