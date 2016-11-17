/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.XlabOrdenEstudioPruebaResultados;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class XlabOrdenEstudioPruebaResultadosFacade extends AbstractFacade<XlabOrdenEstudioPruebaResultados> {
    public XlabOrdenEstudioPruebaResultadosFacade() {
        super(XlabOrdenEstudioPruebaResultados.class);
    }
    
    public List<XlabOrdenEstudioPruebaResultados> getListaOrdenResultadoXPrueba(int ordenEstudioPrueba){
        return getEntityManager().createQuery("SELECT x FROM XlabOrdenEstudioPruebaResultados x where x.ordenEstudiosPruebasId.id=:ordenEstudioPrueba").setParameter("ordenEstudioPrueba", ordenEstudioPrueba).getResultList();
    }
}
