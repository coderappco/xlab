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
import javax.persistence.ManyToOne;
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
@Table(name = "cfg_sede")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfgSede.findAll", query = "SELECT c FROM CfgSede c"),
    @NamedQuery(name = "CfgSede.findByIdSede", query = "SELECT c FROM CfgSede c WHERE c.idSede = :idSede"),
    @NamedQuery(name = "CfgSede.findByCodigoSede", query = "SELECT c FROM CfgSede c WHERE c.codigoSede = :codigoSede"),
    @NamedQuery(name = "CfgSede.findByNombreSede", query = "SELECT c FROM CfgSede c WHERE c.nombreSede = :nombreSede"),
    @NamedQuery(name = "CfgSede.findByEncargado", query = "SELECT c FROM CfgSede c WHERE c.encargado = :encargado"),
    @NamedQuery(name = "CfgSede.findByDireccion", query = "SELECT c FROM CfgSede c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "CfgSede.findByTelefono1", query = "SELECT c FROM CfgSede c WHERE c.telefono1 = :telefono1"),
    @NamedQuery(name = "CfgSede.findByTelefono2", query = "SELECT c FROM CfgSede c WHERE c.telefono2 = :telefono2")})
public class CfgSede implements Serializable {
    @OneToMany(mappedBy = "idSede")
    private List<FacCaja> facCajaList;
    @OneToMany(mappedBy = "idSede")
    private List<CfgConsultorios> cfgConsultoriosList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sede")
    private Integer idSede;
    @Size(max = 10)
    @Column(name = "codigo_sede")
    private String codigoSede;
    @Size(max = 200)
    @Column(name = "nombre_sede")
    private String nombreSede;
    @Size(max = 200)
    @Column(name = "encargado")
    private String encargado;
    @Size(max = 200)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 10)
    @Column(name = "telefono1")
    private String telefono1;
    @Size(max = 10)
    @Column(name = "telefono2")
    private String telefono2;
    @JoinColumn(name = "municipio", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones municipio;
    @JoinColumn(name = "departamento", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones departamento;

    public CfgSede() {
    }

    public CfgSede(Integer idSede) {
        this.idSede = idSede;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public String getCodigoSede() {
        return codigoSede;
    }

    public void setCodigoSede(String codigoSede) {
        this.codigoSede = codigoSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public CfgClasificaciones getMunicipio() {
        return municipio;
    }

    public void setMunicipio(CfgClasificaciones municipio) {
        this.municipio = municipio;
    }

    public CfgClasificaciones getDepartamento() {
        return departamento;
    }

    public void setDepartamento(CfgClasificaciones departamento) {
        this.departamento = departamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSede != null ? idSede.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgSede)) {
            return false;
        }
        CfgSede other = (CfgSede) object;
        if ((this.idSede == null && other.idSede != null) || (this.idSede != null && !this.idSede.equals(other.idSede))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.CfgSede[ idSede=" + idSede + " ]";
    }

    @XmlTransient
    public List<FacCaja> getFacCajaList() {
        return facCajaList;
    }

    public void setFacCajaList(List<FacCaja> facCajaList) {
        this.facCajaList = facCajaList;
    }

    @XmlTransient
    public List<CfgConsultorios> getCfgConsultoriosList() {
        return cfgConsultoriosList;
    }

    public void setCfgConsultoriosList(List<CfgConsultorios> cfgConsultoriosList) {
        this.cfgConsultoriosList = cfgConsultoriosList;
    }
    
}
