/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.CfgUsuarios;
import coderappco.xlab.entidades.FacCaja;
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
public class FacCajaFacade extends AbstractFacade<FacCaja> {
    public FacCajaFacade() {
        super(FacCaja.class);
    }

    public List<FacCaja> buscarOrdenado() {
        try {
            String hql = "SELECT u FROM FacCaja u ORDER BY u.nombreCaja";
            return getEntityManager().createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<FacCaja> buscarPorSede(String sede) {
        try {
            String hql = "SELECT f FROM FacCaja f WHERE f.idSede.idSede = " + sede + " ORDER BY f.idCaja ASC";
            return getEntityManager().createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public FacCaja getUsuarioCaja(CfgUsuarios usuario){
        try {
            Query query = getEntityManager().createQuery("SELECT f FROM FacCaja f WHERE f.idUsuario.idUsuario =:idUsuario");
           query.setParameter("idUsuario", usuario.getIdUsuario());
           return (FacCaja)query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
