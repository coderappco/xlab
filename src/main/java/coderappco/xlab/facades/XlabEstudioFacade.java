/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.XlabEstudio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;


/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class XlabEstudioFacade extends AbstractFacade<XlabEstudio> {
    public XlabEstudioFacade() {
        super(XlabEstudio.class);
    }
    
    public List<String> autocompletarDiagnostico(String txt) {
        try {
            return getEntityManager().createNativeQuery("select codigo||' - '||nombre from xlab_estudio where codigo||' - '||nombre ilike '%" + txt + "%' limit 10").getResultList();
        } catch (Exception e) {                          
            return null;
        }
    }
    public XlabEstudio buscarPorNombre(String txt) {
        Query q = getEntityManager().createQuery("select x from XlabEstudio x where concat(x.codigo,' - ',x.nombre) =:desc");
            q.setParameter("desc", txt);
            return (XlabEstudio)q.getSingleResult();
    }
}
