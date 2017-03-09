/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.XlabConsecutivos;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class XlabConsecutivosFacade extends AbstractFacade<XlabConsecutivos> {
    public XlabConsecutivosFacade() {
        super(XlabConsecutivos.class);
    }
 
    public XlabConsecutivos getName(String nombre){
        try {
            String hql = "SELECT x FROM XlabConsecutivos x WHERE x.nombre = :nombre";
            Query query = getEntityManager().createQuery(hql);
            query.setParameter("nombre", nombre);
            return (XlabConsecutivos)query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
