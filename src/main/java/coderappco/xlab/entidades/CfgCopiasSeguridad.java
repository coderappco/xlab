/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "cfg_copias_seguridad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfgCopiasSeguridad.findAll", query = "SELECT c FROM CfgCopiasSeguridad c"),
    @NamedQuery(name = "CfgCopiasSeguridad.findByIdCopia", query = "SELECT c FROM CfgCopiasSeguridad c WHERE c.idCopia = :idCopia"),
    @NamedQuery(name = "CfgCopiasSeguridad.findByNombre", query = "SELECT c FROM CfgCopiasSeguridad c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CfgCopiasSeguridad.findByFecha", query = "SELECT c FROM CfgCopiasSeguridad c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CfgCopiasSeguridad.findByRuta", query = "SELECT c FROM CfgCopiasSeguridad c WHERE c.ruta = :ruta"),
    @NamedQuery(name = "CfgCopiasSeguridad.findByTipo", query = "SELECT c FROM CfgCopiasSeguridad c WHERE c.tipo = :tipo")})
public class CfgCopiasSeguridad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_copia")
    private Integer idCopia;
    @Size(max = 300)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 2147483647)
    @Column(name = "ruta")
    private String ruta;
    @Size(max = 10)
    @Column(name = "tipo")
    private String tipo;

    public CfgCopiasSeguridad() {
    }

    public CfgCopiasSeguridad(Integer idCopia) {
        this.idCopia = idCopia;
    }

    public Integer getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(Integer idCopia) {
        this.idCopia = idCopia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCopia != null ? idCopia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgCopiasSeguridad)) {
            return false;
        }
        CfgCopiasSeguridad other = (CfgCopiasSeguridad) object;
        if ((this.idCopia == null && other.idCopia != null) || (this.idCopia != null && !this.idCopia.equals(other.idCopia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.CfgCopiasSeguridad[ idCopia=" + idCopia + " ]";
    }
    
}
