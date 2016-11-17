/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.FacConsumoMedicamento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class FacConsumoMedicamentoFacade extends AbstractFacade<FacConsumoMedicamento> {
    @PersistenceContext(unitName = "XLABPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacConsumoMedicamentoFacade() {
        super(FacConsumoMedicamento.class);
    }
    
}
