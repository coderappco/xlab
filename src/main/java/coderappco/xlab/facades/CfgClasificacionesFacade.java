/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.CfgClasificaciones;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;


/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class CfgClasificacionesFacade extends AbstractFacade<CfgClasificaciones> {
    
    public CfgClasificacionesFacade() {
        super(CfgClasificaciones.class);
    }
    
    public List<CfgClasificaciones> buscarPorMaestro(String maestro) {
        try {
            String hql = "SELECT c FROM CfgClasificaciones c WHERE c.maestro.maestro = :maestro order by c.codigo ASC";
            return getEntityManager().createQuery(hql).setParameter("maestro", maestro).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public CfgClasificaciones buscarPorCodigo(String codigo,String maestro) {
        try {
            String hql = "SELECT c FROM CfgClasificaciones c WHERE c.maestro.maestro = :maestro and c.codigo=:codigo order by c.codigo ASC";
            Query query = getEntityManager().createQuery(hql);
            query.setParameter("maestro", maestro);
            query.setParameter("codigo", codigo);
            return (CfgClasificaciones)query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<String> autocompletarAreas(String txt) {
        try {
            return getEntityManager().createNativeQuery("select codigo||' - '||descripcion from cfg_clasificaciones where codigo||' - '||descripcion ilike '%" + txt + "%' and maestro='GrupoArea' limit 10").getResultList();
        } catch (Exception e) {                          
            return null;
        }
    }
    
    public CfgClasificaciones getAreaCodigo(String txt) {
        try {
            Query q = getEntityManager().createQuery("select c from CfgClasificaciones c where concat(c.codigo,' - ',c.descripcion) =:desc and c.maestro.maestro='GrupoArea'");
            q.setParameter("desc", txt);
            return (CfgClasificaciones)q.getSingleResult();
        } catch (Exception e) {                          
            return null;
        }
    }
    public List<CfgClasificaciones> buscarMunicipioPorDepartamento(String departamento) {
        try {
            String hql = "SELECT c FROM CfgClasificaciones c WHERE c.maestro.maestro LIKE 'Municipios' AND c.codigo LIKE '" + departamento + "%' ORDER BY c.codigo";
            return getEntityManager().createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<CfgClasificaciones> buscarPorMaestroObservacion(String maestro, String observacion) {
        try {
            String hql = "SELECT c FROM CfgClasificaciones c WHERE c.maestro.maestro = :maestro AND c.observacion = :observacion order by c.codigo";
            return getEntityManager().createQuery(hql).setParameter("maestro", maestro).setParameter("observacion", observacion).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public CfgClasificaciones buscarPorMaestroDescripcion(String maestro, String descripcion) {
        try {
            String hql = "SELECT c FROM CfgClasificaciones c WHERE c.maestro.maestro = :maestro AND c.descripcion = :descripcion";
            return (CfgClasificaciones) getEntityManager().createQuery(hql).setParameter("maestro", maestro).setParameter("descripcion", descripcion).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Object> listaClasificaciones() {
        try {
            String hql = "SELECT DISTINCT c.maestro FROM CfgClasificaciones c ORDER BY c.maestro ASC";
            return getEntityManager().createQuery(hql).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
