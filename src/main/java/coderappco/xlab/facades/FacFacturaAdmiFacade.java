/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.FacFacturaAdmi;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class FacFacturaAdmiFacade extends AbstractFacade<FacFacturaAdmi> {
    public FacFacturaAdmiFacade() {
        super(FacFacturaAdmi.class);
    }
    
     public List<FacFacturaAdmi> buscarOrdenado() {
        try {
            String hql = "SELECT i FROM FacFacturaAdmi i ORDER BY i.idFactura ASC";
            return getEntityManager().createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<FacFacturaAdmi> buscarPorAministradora(String administradora) {
        try {
            String hql = "SELECT f FROM FacFacturaAdmi f WHERE f.idAdministradora.idAdministradora = " + administradora + " ORDER BY f.fecha DESC";
            return getEntityManager().createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public int numeroTotalRegistros() {
        try {
            return Integer.parseInt(getEntityManager().createNativeQuery("SELECT COUNT(*) FROM fac_factura_admi").getSingleResult().toString());
        } catch (Exception e) {
            return 0;
        }
    }
    
    public List<FacFacturaAdmi> consultaNativaFacturas(String sql) {
        List<FacFacturaAdmi> listaFacturas = (List<FacFacturaAdmi>) getEntityManager().createNativeQuery(sql, FacFacturaAdmi.class).getResultList();
        return listaFacturas;
    }
    
}
