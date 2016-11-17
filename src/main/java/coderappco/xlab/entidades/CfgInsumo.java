/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "cfg_insumo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfgInsumo.findAll", query = "SELECT c FROM CfgInsumo c"),
    @NamedQuery(name = "CfgInsumo.findByIdInsumo", query = "SELECT c FROM CfgInsumo c WHERE c.idInsumo = :idInsumo"),
    @NamedQuery(name = "CfgInsumo.findByCodigoInsumo", query = "SELECT c FROM CfgInsumo c WHERE c.codigoInsumo = :codigoInsumo"),
    @NamedQuery(name = "CfgInsumo.findByNombreInsumo", query = "SELECT c FROM CfgInsumo c WHERE c.nombreInsumo = :nombreInsumo"),
    @NamedQuery(name = "CfgInsumo.findByObservacion", query = "SELECT c FROM CfgInsumo c WHERE c.observacion = :observacion"),
    @NamedQuery(name = "CfgInsumo.findByValor", query = "SELECT c FROM CfgInsumo c WHERE c.valor = :valor"),
    @NamedQuery(name = "CfgInsumo.findByAplicaIva", query = "SELECT c FROM CfgInsumo c WHERE c.aplicaIva = :aplicaIva"),
    @NamedQuery(name = "CfgInsumo.findByAplicaCree", query = "SELECT c FROM CfgInsumo c WHERE c.aplicaCree = :aplicaCree")})
public class CfgInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_insumo")
    private Integer idInsumo;
    @Size(max = 20)
    @Column(name = "codigo_insumo")
    private String codigoInsumo;
    @Size(max = 150)
    @Column(name = "nombre_insumo")
    private String nombreInsumo;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "aplica_iva")
    private Boolean aplicaIva;
    @Column(name = "aplica_cree")
    private Boolean aplicaCree;
    @OneToMany(mappedBy = "idInsumo")
    private List<FacConsumoInsumo> facConsumoInsumoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfgInsumo")
    private List<FacManualTarifarioInsumo> facManualTarifarioInsumoList;
    @OneToMany(mappedBy = "idInsumo")
    private List<FacFacturaInsumo> facFacturaInsumoList;
    @JoinColumn(name = "id_administradora", referencedColumnName = "id_administradora")
    @ManyToOne
    private FacAdministradora idAdministradora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfgInsumo")
    private List<FacPaqueteInsumo> facPaqueteInsumoList;

    public CfgInsumo() {
    }

    public CfgInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getCodigoInsumo() {
        return codigoInsumo;
    }

    public void setCodigoInsumo(String codigoInsumo) {
        this.codigoInsumo = codigoInsumo;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getAplicaIva() {
        return aplicaIva;
    }

    public void setAplicaIva(Boolean aplicaIva) {
        this.aplicaIva = aplicaIva;
    }

    public Boolean getAplicaCree() {
        return aplicaCree;
    }

    public void setAplicaCree(Boolean aplicaCree) {
        this.aplicaCree = aplicaCree;
    }

    @XmlTransient
    public List<FacConsumoInsumo> getFacConsumoInsumoList() {
        return facConsumoInsumoList;
    }

    public void setFacConsumoInsumoList(List<FacConsumoInsumo> facConsumoInsumoList) {
        this.facConsumoInsumoList = facConsumoInsumoList;
    }

    @XmlTransient
    public List<FacManualTarifarioInsumo> getFacManualTarifarioInsumoList() {
        return facManualTarifarioInsumoList;
    }

    public void setFacManualTarifarioInsumoList(List<FacManualTarifarioInsumo> facManualTarifarioInsumoList) {
        this.facManualTarifarioInsumoList = facManualTarifarioInsumoList;
    }

    @XmlTransient
    public List<FacFacturaInsumo> getFacFacturaInsumoList() {
        return facFacturaInsumoList;
    }

    public void setFacFacturaInsumoList(List<FacFacturaInsumo> facFacturaInsumoList) {
        this.facFacturaInsumoList = facFacturaInsumoList;
    }

    public FacAdministradora getIdAdministradora() {
        return idAdministradora;
    }

    public void setIdAdministradora(FacAdministradora idAdministradora) {
        this.idAdministradora = idAdministradora;
    }

    @XmlTransient
    public List<FacPaqueteInsumo> getFacPaqueteInsumoList() {
        return facPaqueteInsumoList;
    }

    public void setFacPaqueteInsumoList(List<FacPaqueteInsumo> facPaqueteInsumoList) {
        this.facPaqueteInsumoList = facPaqueteInsumoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInsumo != null ? idInsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgInsumo)) {
            return false;
        }
        CfgInsumo other = (CfgInsumo) object;
        if ((this.idInsumo == null && other.idInsumo != null) || (this.idInsumo != null && !this.idInsumo.equals(other.idInsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.CfgInsumo[ idInsumo=" + idInsumo + " ]";
    }
    
}
