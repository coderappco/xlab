/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.CfgCorreo;
import javax.ejb.Stateless;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class CfgCorreoFacade extends AbstractFacade<CfgCorreo> {
    public CfgCorreoFacade() {
        super(CfgCorreo.class);
    }
    
}
