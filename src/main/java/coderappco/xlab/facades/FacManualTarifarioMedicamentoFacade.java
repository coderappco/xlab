/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.FacManualTarifarioMedicamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class FacManualTarifarioMedicamentoFacade extends AbstractFacade<FacManualTarifarioMedicamento> {
    public FacManualTarifarioMedicamentoFacade() {
        super(FacManualTarifarioMedicamento.class);
    }
    public List<FacManualTarifarioMedicamento> buscarPorManualTarifario(Integer idManual) {
        Query query;
        try {
            query = getEntityManager().createQuery("SELECT m FROM FacManualTarifarioMedicamento m WHERE m.facManualTarifario.idManualTarifario = :idManual ORDER BY m.facManualTarifarioMedicamentoPK.idMedicamento").setParameter("idManual", idManual);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    
}
