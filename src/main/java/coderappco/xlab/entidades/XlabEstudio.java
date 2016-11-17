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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "xlab_estudio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "XlabEstudio.findAll", query = "SELECT x FROM XlabEstudio x"),
    @NamedQuery(name = "XlabEstudio.findById", query = "SELECT x FROM XlabEstudio x WHERE x.id = :id"),
    @NamedQuery(name = "XlabEstudio.findByCodigo", query = "SELECT x FROM XlabEstudio x WHERE x.codigo = :codigo"),
    @NamedQuery(name = "XlabEstudio.findByNombre", query = "SELECT x FROM XlabEstudio x WHERE x.nombre = :nombre")})
public class XlabEstudio implements Serializable {
    @JoinTable(name = "xlab_orden_estudios", joinColumns = {
        @JoinColumn(name = "estudio_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "orden_id", referencedColumnName = "id")})
    @ManyToMany
    private List<XlabOrden> xlabOrdenList;
    @OneToMany(mappedBy = "estudioId")
    private List<XlabOrdenEstudiosPruebas> xlabOrdenEstudiosPruebasList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "xlab_estudio_pruebas", joinColumns = {
        @JoinColumn(name = "estudio_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "prueba_id", referencedColumnName = "id")})
    @ManyToMany
    private List<XlabPrueba> xlabPruebaList;

    public XlabEstudio() {
    }

    public XlabEstudio(Integer id) {
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
        if (!(object instanceof XlabEstudio)) {
            return false;
        }
        XlabEstudio other = (XlabEstudio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.XlabEstudio[ id=" + id + " ]";
    }

    @XmlTransient
    public List<XlabOrdenEstudiosPruebas> getXlabOrdenEstudiosPruebasList() {
        return xlabOrdenEstudiosPruebasList;
    }

    public void setXlabOrdenEstudiosPruebasList(List<XlabOrdenEstudiosPruebas> xlabOrdenEstudiosPruebasList) {
        this.xlabOrdenEstudiosPruebasList = xlabOrdenEstudiosPruebasList;
    }

    @XmlTransient
    public List<XlabOrden> getXlabOrdenList() {
        return xlabOrdenList;
    }

    public void setXlabOrdenList(List<XlabOrden> xlabOrdenList) {
        this.xlabOrdenList = xlabOrdenList;
    }

    
}
