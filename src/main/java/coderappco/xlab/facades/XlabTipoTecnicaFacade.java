/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.XlabTipoTecnica;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class XlabTipoTecnicaFacade extends AbstractFacade<XlabTipoTecnica> {
    public XlabTipoTecnicaFacade() {
        super(XlabTipoTecnica.class);
    }
    
    public List<XlabTipoTecnica> tecnicasActivas(){
        return getEntityManager().createQuery("SELECT t from XlabTipoTecnica t where t.borrado='N'").getResultList();
    }
}
