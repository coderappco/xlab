/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.FacImpuestos;
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
public class FacImpuestosFacade extends AbstractFacade<FacImpuestos> {
    public FacImpuestosFacade() {
        super(FacImpuestos.class);
    }
    
    public List<FacImpuestos> buscarPorNombre(String nom) {
        try {
            Query query = getEntityManager().createQuery("SELECT c FROM FacImpuestos c WHERE c.nombre = ?1");
            query.setParameter(1, nom);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<FacImpuestos> buscarOrdenado() {
        try {
            String hql = "SELECT m FROM FacImpuestos m ORDER By m.fechaInicial DESC";
            return getEntityManager().createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    
    }
}
