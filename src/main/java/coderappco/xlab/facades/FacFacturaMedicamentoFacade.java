/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.FacFacturaMedicamento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class FacFacturaMedicamentoFacade extends AbstractFacade<FacFacturaMedicamento> {
    @PersistenceContext(unitName = "XLABPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacFacturaMedicamentoFacade() {
        super(FacFacturaMedicamento.class);
    }
    
}
