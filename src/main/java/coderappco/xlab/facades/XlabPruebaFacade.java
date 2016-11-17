/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.XlabPrueba;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class XlabPruebaFacade extends AbstractFacade<XlabPrueba> {
    public XlabPruebaFacade() {
        super(XlabPrueba.class);
    }
    
    public List<String> autocompletarAreas(String txt) {
        try {
            return getEntityManager().createNativeQuery("select codigo||' - '||nombre from xlab_prueba where codigo||' - '||nombre ilike '%" + txt + "%' limit 10").getResultList();
        } catch (Exception e) {                          
            return null;
        }
    }
     public XlabPrueba getPrueba(String txt) {
        try {
            Query q = getEntityManager().createQuery("select x from XlabPrueba x where concat(x.codigo,' - ',x.nombre) =:desc");
            q.setParameter("desc", txt);
            return (XlabPrueba)q.getSingleResult();
        } catch (Exception e) {                          
            return null;
        }
    }
}
