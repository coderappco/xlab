/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "cfg_unidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfgUnidad.findAll", query = "SELECT c FROM CfgUnidad c"),
    @NamedQuery(name = "CfgUnidad.findById", query = "SELECT c FROM CfgUnidad c WHERE c.id = :id"),
    @NamedQuery(name = "CfgUnidad.findByCodigo", query = "SELECT c FROM CfgUnidad c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CfgUnidad.findByNombre", query = "SELECT c FROM CfgUnidad c WHERE c.nombre = :nombre")})
public class CfgUnidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "unidadPrueba")
    private List<XlabPrueba> xlabPruebaList;

    public CfgUnidad() {
    }

    public CfgUnidad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<XlabPrueba> getXlabPruebaList() {
        return xlabPruebaList;
    }

    public void setXlabPruebaList(List<XlabPrueba> xlabPruebaList) {
        this.xlabPruebaList = xlabPruebaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgUnidad)) {
            return false;
        }
        CfgUnidad other = (CfgUnidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.CfgUnidad[ id=" + id + " ]";
    }
    
}
