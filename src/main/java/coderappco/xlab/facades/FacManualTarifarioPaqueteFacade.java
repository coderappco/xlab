/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.FacManualTarifarioPaquete;
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
public class FacManualTarifarioPaqueteFacade extends AbstractFacade<FacManualTarifarioPaquete> {
    public FacManualTarifarioPaqueteFacade() {
        super(FacManualTarifarioPaquete.class);
    }
    public List<FacManualTarifarioPaquete> buscarPorManualTarifario(Integer idManual) {
        Query query;
        try {
            query = getEntityManager().createQuery("SELECT m FROM FacManualTarifarioPaquete m WHERE m.facManualTarifario.idManualTarifario = :idManual ORDER BY m.facManualTarifarioPaquetePK.idPaquete").setParameter("idManual", idManual);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
