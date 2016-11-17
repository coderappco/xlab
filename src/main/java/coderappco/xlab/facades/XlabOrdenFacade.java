/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.ListaInforme;
import coderappco.xlab.entidades.XlabOrden;
import coderappco.xlab.utilidades.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class XlabOrdenFacade extends AbstractFacade<XlabOrden> {
    public XlabOrdenFacade() {
        super(XlabOrden.class);
    }
    
    public XlabOrden getOrdenXNro(String nroOden){
        try {
            Query q = getEntityManager().createQuery("select x from XlabOrden x where x.nroOrden=:nroOrden");
            q.setParameter("nroOrden", nroOden);
            return (XlabOrden)q.getSingleResult();
        } catch (Exception e) {                          
            logger.error("Error en la clase " + XlabOrdenFacade.class.getName() + ", mensaje: " + e.getMessage(), e);
        }
        return null;
    }
    public XlabOrden getOrdenXNro(String identificacion,int tipoIdentificacion){
        try {
            Query q = getEntityManager().createQuery("select x from XlabOrden x where x.pacienteId.identificacion=:identificacion and x.pacienteId.tipoIdentificacion.id=:tipoIdentificacion order by x.id desc");
            q.setParameter("identificacion", identificacion);
            q.setParameter("tipoIdentificacion", tipoIdentificacion);
            return (XlabOrden)q.getSingleResult();
        } catch (Exception e) {                          
            logger.error("Error en la clase " + XlabOrdenFacade.class.getName() + ", mensaje: " + e.getMessage(), e);
        }
        return null;
    }
    
    public List<ListaInforme> getInformeResultado(Date fechaDesde, Date fechaHasta,int estudio,int empresaId){
        List<ListaInforme> lst = new ArrayList<>();
        Query q = getEntityManager().createNativeQuery("select 	xo.nro_orden as nroOrden, " +
                                                        "	xo.fecha_orden as fechaOrden, " +
                                                        "	cp.identificacion, " +
                                                        "	cp.primer_apellido as primerApellido, " +
                                                        "	cp.segundo_Apellido as segundoApellido, " +
                                                        "	cp.primer_nombre as primerNombre, " +
                                                        "	cp.segundo_nombre as segundoNombre, " +
                                                        "	cp.fecha_nacimiento as fechaNacimiento, " +
                                                        "	genero.observacion as genero, " +
                                                        "	cp.telefono_residencia as telefono, " +
                                                        "	estudio.nombre as estudio, " +
                                                        "	xp.resultado, " +
                                                        "	unidad.codigo as unidad, " +
                                                        "	xp.fecha_actualizacion as fechaPrueba, " +
                                                        "	fa.razon_social as entidad " +
                                                        "from xlab_orden xo " +
                                                        "inner join xlab_orden_estudios xs on xs.orden_id = xo.id " +
                                                        "inner join xlab_orden_estudios_pruebas xp on xp.estudio_id = xs.estudio_id and xp.orden_id=xo.id " +
                                                        "inner join xlab_prueba prueba on prueba.id = xp.prueba_id " +
                                                        "inner join xlab_estudio estudio on estudio.id = xs.estudio_id " +
                                                        "inner join cfg_pacientes cp on cp.id_paciente = xo.paciente_id " +
                                                        "inner join cfg_clasificaciones genero on genero.id = cp.genero " +
                                                        "inner join cfg_unidad unidad on unidad.id = prueba.unidad_prueba " +
                                                        "inner join fac_administradora fa on fa.id_administradora = cp.id_administradora");
        List<Object[]> lst2 = q.getResultList();
        for(Object[] o:lst2){
            ListaInforme li = new ListaInforme();
            li.setNroOrden(o[0].toString());
            li.setFechaOrden(Utilidades.DeStringADate(o[1].toString()));
            li.setDocumento(o[2].toString());
            li.setPrimerNombre(o[3].toString());
            li.setSegundoNombre(o[4].toString());
            li.setPrimerApellido(o[5].toString());
            li.setSegundoApellido(o[6].toString());
            li.setFechaNacimiento(Utilidades.DeStringADate(o[7].toString()));
            li.setGenero(o[8].toString());
            li.setTelefono(o[9].toString());
            li.setEstudio(o[10].toString());
            li.setResultado(o[11].toString());
            li.setUnidad(o[12].toString());
            li.setFechaPrueba(Utilidades.DeStringADate(o[13].toString()));
            li.setEntidad(o[14].toString());
            lst.add(li);
        }
        return lst;
        
    }
}
