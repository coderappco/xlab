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
@Table(name = "fac_caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacCaja.findAll", query = "SELECT f FROM FacCaja f"),
    @NamedQuery(name = "FacCaja.findByIdCaja", query = "SELECT f FROM FacCaja f WHERE f.idCaja = :idCaja"),
    @NamedQuery(name = "FacCaja.findByNombreCaja", query = "SELECT f FROM FacCaja f WHERE f.nombreCaja = :nombreCaja"),
    @NamedQuery(name = "FacCaja.findByValorBaseDefecto", query = "SELECT f FROM FacCaja f WHERE f.valorBaseDefecto = :valorBaseDefecto"),
    @NamedQuery(name = "FacCaja.findByCodigoCaja", query = "SELECT f FROM FacCaja f WHERE f.codigoCaja = :codigoCaja"),
    @NamedQuery(name = "FacCaja.findByCerrada", query = "SELECT f FROM FacCaja f WHERE f.cerrada = :cerrada")})
public class FacCaja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_caja")
    private Integer idCaja;
    @Size(max = 100)
    @Column(name = "nombre_caja")
    private String nombreCaja;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_base_defecto")
    private Double valorBaseDefecto;
    @Size(max = 10)
    @Column(name = "codigo_caja")
    private String codigoCaja;
    @Column(name = "cerrada")
    private Boolean cerrada;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private CfgUsuarios idUsuario;
    @JoinColumn(name = "id_sede", referencedColumnName = "id_sede")
    @ManyToOne
    private CfgSede idSede;
    @OneToMany(mappedBy = "idCaja")
    private List<FacMovimientoCaja> facMovimientoCajaList;
    @OneToMany(mappedBy = "idCaja")
    private List<FacFacturaPaciente> facFacturaPacienteList;

    public FacCaja() {
    }

    public FacCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public Double getValorBaseDefecto() {
        return valorBaseDefecto;
    }

    public void setValorBaseDefecto(Double valorBaseDefecto) {
        this.valorBaseDefecto = valorBaseDefecto;
    }

    public String getCodigoCaja() {
        return codigoCaja;
    }

    public void setCodigoCaja(String codigoCaja) {
        this.codigoCaja = codigoCaja;
    }

    public Boolean getCerrada() {
        return cerrada;
    }

    public void setCerrada(Boolean cerrada) {
        this.cerrada = cerrada;
    }

    public CfgUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(CfgUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public CfgSede getIdSede() {
        return idSede;
    }

    public void setIdSede(CfgSede idSede) {
        this.idSede = idSede;
    }

    @XmlTransient
    public List<FacMovimientoCaja> getFacMovimientoCajaList() {
        return facMovimientoCajaList;
    }

    public void setFacMovimientoCajaList(List<FacMovimientoCaja> facMovimientoCajaList) {
        this.facMovimientoCajaList = facMovimientoCajaList;
    }

    @XmlTransient
    public List<FacFacturaPaciente> getFacFacturaPacienteList() {
        return facFacturaPacienteList;
    }

    public void setFacFacturaPacienteList(List<FacFacturaPaciente> facFacturaPacienteList) {
        this.facFacturaPacienteList = facFacturaPacienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaja != null ? idCaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCaja)) {
            return false;
        }
        FacCaja other = (FacCaja) object;
        if ((this.idCaja == null && other.idCaja != null) || (this.idCaja != null && !this.idCaja.equals(other.idCaja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.FacCaja[ idCaja=" + idCaja + " ]";
    }
    
}
