/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.CfgDiagnostico;
import coderappco.xlab.entidades.XlabPrueba;
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
public class CfgDiagnosticoFacade extends AbstractFacade<CfgDiagnostico> {
    public CfgDiagnosticoFacade() {
        super(CfgDiagnostico.class);
    }
    public List<String> autocompletarDiagnostico(String txt) {
        try {
            return getEntityManager().createNativeQuery("select codigo_diagnostico||' - '||nombre_diagnostico from cfg_diagnostico where codigo_diagnostico||' - '||nombre_diagnostico ilike '%" + txt + "%' limit 10").getResultList();
        } catch (Exception e) {                          
            return null;
        }
    }
    public CfgDiagnostico buscarPorNombre(String txt) {
        Query q = getEntityManager().createQuery("select x from CfgDiagnostico x where concat(x.codigoDiagnostico,' - ',x.nombreDiagnostico) =:desc");
            q.setParameter("desc", txt);
            return (CfgDiagnostico)q.getSingleResult();
    }
}
