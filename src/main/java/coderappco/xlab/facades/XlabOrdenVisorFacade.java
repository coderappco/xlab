/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.XlabOrdenVisor;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author miguel
 */
@Stateless
public class XlabOrdenVisorFacade extends AbstractFacade<XlabOrdenVisor> {

    public XlabOrdenVisorFacade() {
        super(XlabOrdenVisor.class);
    }
    
    public List<XlabOrdenVisor> visorXOrden(int idOrden){
        try {
            String hql  ="Select x FROM XlabOrdenVisor x where x.idOrden.id=:idOrden";
            Query q = getEntityManager().createQuery(hql).setParameter("idOrden", idOrden);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
}
