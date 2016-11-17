/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.CfgCopiasSeguridad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class CfgCopiasSeguridadFacade extends AbstractFacade<CfgCopiasSeguridad> {
    
    public CfgCopiasSeguridadFacade() {
        super(CfgCopiasSeguridad.class);
    }
    
    public CfgCopiasSeguridad buscarPorNombre(String nombreCopiaSeguridad) {
        try {
            String hql = "SELECT c FROM CfgCopiasSeguridad c WHERE c.nombre LIKE '" + nombreCopiaSeguridad + "'";
            return (CfgCopiasSeguridad) getEntityManager().createQuery(hql).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<CfgCopiasSeguridad> buscarOrdenado() {
        try {
            String hql = "SELECT c FROM CfgCopiasSeguridad c ORDER BY c.fecha DESC";
            return getEntityManager().createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public CfgCopiasSeguridad buscarPorFecha(String fechaStr) {
        try {
            return (CfgCopiasSeguridad) getEntityManager().createNativeQuery("SELECT * FROM backups WHERE date_backup::date = to_date('" + fechaStr + "','yyyy-MM-dd') id_backup < 11", CfgCopiasSeguridad.class).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
