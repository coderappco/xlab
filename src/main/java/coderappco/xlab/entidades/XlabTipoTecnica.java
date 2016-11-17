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
@Table(name = "xlab_tipo_tecnica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "XlabTipoTecnica.findAll", query = "SELECT x FROM XlabTipoTecnica x"),
    @NamedQuery(name = "XlabTipoTecnica.findById", query = "SELECT x FROM XlabTipoTecnica x WHERE x.id = :id"),
    @NamedQuery(name = "XlabTipoTecnica.findByNombre", query = "SELECT x FROM XlabTipoTecnica x WHERE x.nombre = :nombre"),
    @NamedQuery(name = "XlabTipoTecnica.findByBorrado", query = "SELECT x FROM XlabTipoTecnica x WHERE x.borrado = :borrado")})
public class XlabTipoTecnica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 1)
    @Column(name = "borrado")
    private String borrado;
    @OneToMany(mappedBy = "tipoTecnica")
    private List<XlabPrueba> xlabPruebaList;

    public XlabTipoTecnica() {
        this.id=0;
    }

    public XlabTipoTecnica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBorrado() {
        return borrado;
    }

    public void setBorrado(String borrado) {
        this.borrado = borrado;
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
        if (!(object instanceof XlabTipoTecnica)) {
            return false;
        }
        XlabTipoTecnica other = (XlabTipoTecnica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.XlabTipoTecnica[ id=" + id + " ]";
    }
    
}
