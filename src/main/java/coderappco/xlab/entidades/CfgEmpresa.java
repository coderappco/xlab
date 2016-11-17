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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "cfg_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfgEmpresa.findAll", query = "SELECT c FROM CfgEmpresa c"),
    @NamedQuery(name = "CfgEmpresa.findByCodEmpresa", query = "SELECT c FROM CfgEmpresa c WHERE c.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CfgEmpresa.findByNumIdentificacion", query = "SELECT c FROM CfgEmpresa c WHERE c.numIdentificacion = :numIdentificacion"),
    @NamedQuery(name = "CfgEmpresa.findByDv", query = "SELECT c FROM CfgEmpresa c WHERE c.dv = :dv"),
    @NamedQuery(name = "CfgEmpresa.findByRazonSocial", query = "SELECT c FROM CfgEmpresa c WHERE c.razonSocial = :razonSocial"),
    @NamedQuery(name = "CfgEmpresa.findByNumDocRepLegal", query = "SELECT c FROM CfgEmpresa c WHERE c.numDocRepLegal = :numDocRepLegal"),
    @NamedQuery(name = "CfgEmpresa.findByNomRepLegal", query = "SELECT c FROM CfgEmpresa c WHERE c.nomRepLegal = :nomRepLegal"),
    @NamedQuery(name = "CfgEmpresa.findByDireccion", query = "SELECT c FROM CfgEmpresa c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "CfgEmpresa.findByTelefono1", query = "SELECT c FROM CfgEmpresa c WHERE c.telefono1 = :telefono1"),
    @NamedQuery(name = "CfgEmpresa.findByTelefono2", query = "SELECT c FROM CfgEmpresa c WHERE c.telefono2 = :telefono2"),
    @NamedQuery(name = "CfgEmpresa.findByWebsite", query = "SELECT c FROM CfgEmpresa c WHERE c.website = :website"),
    @NamedQuery(name = "CfgEmpresa.findByObservaciones", query = "SELECT c FROM CfgEmpresa c WHERE c.observaciones = :observaciones"),
    @NamedQuery(name = "CfgEmpresa.findByCodigoEmpresa", query = "SELECT c FROM CfgEmpresa c WHERE c.codigoEmpresa = :codigoEmpresa"),
    @NamedQuery(name = "CfgEmpresa.findByRegimen", query = "SELECT c FROM CfgEmpresa c WHERE c.regimen = :regimen"),
    @NamedQuery(name = "CfgEmpresa.findByRazonRip", query = "SELECT c FROM CfgEmpresa c WHERE c.razonRip = :razonRip")})
public class CfgEmpresa implements Serializable {
    @OneToMany(mappedBy = "empresaId")
    private List<XlabOrden> xlabOrdenList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_empresa")
    private Integer codEmpresa;
    @Size(max = 20)
    @Column(name = "num_identificacion")
    private String numIdentificacion;
    @Size(max = 1)
    @Column(name = "dv")
    private String dv;
    @Size(max = 200)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 20)
    @Column(name = "num_doc_rep_legal")
    private String numDocRepLegal;
    @Size(max = 200)
    @Column(name = "nom_rep_legal")
    private String nomRepLegal;
    @Size(max = 200)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 10)
    @Column(name = "telefono_1")
    private String telefono1;
    @Size(max = 10)
    @Column(name = "telefono_2")
    private String telefono2;
    @Size(max = 100)
    @Column(name = "website")
    private String website;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 50)
    @Column(name = "codigo_empresa")
    private String codigoEmpresa;
    @Size(max = 50)
    @Column(name = "regimen")
    private String regimen;
    @Size(max = 2147483647)
    @Column(name = "razon_rip")
    private String razonRip;
    @JoinColumn(name = "logo", referencedColumnName = "id")
    @ManyToOne
    private CfgImagenes logo;
    @JoinColumn(name = "tipo_doc_rep_legal", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones tipoDocRepLegal;
    @JoinColumn(name = "tipo_doc", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones tipoDoc;
    @JoinColumn(name = "cod_municipio", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones codMunicipio;
    @JoinColumn(name = "cod_departamento", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones codDepartamento;

    public CfgEmpresa() {
    }

    public CfgEmpresa(Integer codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public Integer getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(Integer codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNumDocRepLegal() {
        return numDocRepLegal;
    }

    public void setNumDocRepLegal(String numDocRepLegal) {
        this.numDocRepLegal = numDocRepLegal;
    }

    public String getNomRepLegal() {
        return nomRepLegal;
    }

    public void setNomRepLegal(String nomRepLegal) {
        this.nomRepLegal = nomRepLegal;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getRazonRip() {
        return razonRip;
    }

    public void setRazonRip(String razonRip) {
        this.razonRip = razonRip;
    }

    public CfgImagenes getLogo() {
        return logo;
    }

    public void setLogo(CfgImagenes logo) {
        this.logo = logo;
    }

    public CfgClasificaciones getTipoDocRepLegal() {
        return tipoDocRepLegal;
    }

    public void setTipoDocRepLegal(CfgClasificaciones tipoDocRepLegal) {
        this.tipoDocRepLegal = tipoDocRepLegal;
    }

    public CfgClasificaciones getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(CfgClasificaciones tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public CfgClasificaciones getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(CfgClasificaciones codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public CfgClasificaciones getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(CfgClasificaciones codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgEmpresa)) {
            return false;
        }
        CfgEmpresa other = (CfgEmpresa) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.CfgEmpresa[ codEmpresa=" + codEmpresa + " ]";
    }

    @XmlTransient
    public List<XlabOrden> getXlabOrdenList() {
        return xlabOrdenList;
    }

    public void setXlabOrdenList(List<XlabOrden> xlabOrdenList) {
        this.xlabOrdenList = xlabOrdenList;
    }
    
}
