/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.XlabOrdenEstudiosPruebas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class XlabOrdenEstudiosPruebasFacade extends AbstractFacade<XlabOrdenEstudiosPruebas> {
    public XlabOrdenEstudiosPruebasFacade() {
        super(XlabOrdenEstudiosPruebas.class);
    }
    
    public List<XlabOrdenEstudiosPruebas> getPruebasXOrden(int orden){
        return getEntityManager().createQuery("Select x from XlabOrdenEstudiosPruebas x where x.ordenId.id = :orden").setParameter("orden", orden).getResultList();
    }
    
    public List<XlabOrdenEstudiosPruebas> getPruebasXOrdenXArea(int orden,int area){
        return getEntityManager().createQuery("SELECT x FROM XlabOrdenEstudiosPruebas x where x.ordenId.id = :orden and x.pruebaId.grupoArea.id= :area").setParameter("orden", orden).setParameter("area", area).getResultList();
    }
    public List<XlabOrdenEstudiosPruebas> getEvolutivoPacienteXprueba(int idPaciente,int prueba){
        return getEntityManager().createQuery("SELECT x FROM XlabOrdenEstudiosPruebas x where x.ordenId.pacienteId.idPaciente = :idPaciente and x.pruebaId.id= :prueba order by x.fechaActualizacion").setParameter("idPaciente", idPaciente).setParameter("prueba", prueba).getResultList();
    }
}
