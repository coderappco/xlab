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
@Table(name = "fac_manual_tarifario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacManualTarifario.findAll", query = "SELECT f FROM FacManualTarifario f"),
    @NamedQuery(name = "FacManualTarifario.findByIdManualTarifario", query = "SELECT f FROM FacManualTarifario f WHERE f.idManualTarifario = :idManualTarifario"),
    @NamedQuery(name = "FacManualTarifario.findByCodigoManualTarifario", query = "SELECT f FROM FacManualTarifario f WHERE f.codigoManualTarifario = :codigoManualTarifario"),
    @NamedQuery(name = "FacManualTarifario.findByNombreManualTarifario", query = "SELECT f FROM FacManualTarifario f WHERE f.nombreManualTarifario = :nombreManualTarifario")})
public class FacManualTarifario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_manual_tarifario")
    private Integer idManualTarifario;
    @Size(max = 50)
    @Column(name = "codigo_manual_tarifario")
    private String codigoManualTarifario;
    @Size(max = 150)
    @Column(name = "nombre_manual_tarifario")
    private String nombreManualTarifario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facManualTarifario")
    private List<FacManualTarifarioServicio> facManualTarifarioServicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facManualTarifario")
    private List<FacManualTarifarioMedicamento> facManualTarifarioMedicamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facManualTarifario")
    private List<FacManualTarifarioInsumo> facManualTarifarioInsumoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facManualTarifario")
    private List<FacManualTarifarioPaquete> facManualTarifarioPaqueteList;
    @OneToMany(mappedBy = "idManualTarifario")
    private List<FacPrograma> facProgramaList;
    @OneToMany(mappedBy = "idManualTarifario")
    private List<FacContrato> facContratoList;

    public FacManualTarifario() {
    }

    public FacManualTarifario(Integer idManualTarifario) {
        this.idManualTarifario = idManualTarifario;
    }

    public Integer getIdManualTarifario() {
        return idManualTarifario;
    }

    public void setIdManualTarifario(Integer idManualTarifario) {
        this.idManualTarifario = idManualTarifario;
    }

    public String getCodigoManualTarifario() {
        return codigoManualTarifario;
    }

    public void setCodigoManualTarifario(String codigoManualTarifario) {
        this.codigoManualTarifario = codigoManualTarifario;
    }

    public String getNombreManualTarifario() {
        return nombreManualTarifario;
    }

    public void setNombreManualTarifario(String nombreManualTarifario) {
        this.nombreManualTarifario = nombreManualTarifario;
    }

    @XmlTransient
    public List<FacManualTarifarioServicio> getFacManualTarifarioServicioList() {
        return facManualTarifarioServicioList;
    }

    public void setFacManualTarifarioServicioList(List<FacManualTarifarioServicio> facManualTarifarioServicioList) {
        this.facManualTarifarioServicioList = facManualTarifarioServicioList;
    }

    @XmlTransient
    public List<FacManualTarifarioMedicamento> getFacManualTarifarioMedicamentoList() {
        return facManualTarifarioMedicamentoList;
    }

    public void setFacManualTarifarioMedicamentoList(List<FacManualTarifarioMedicamento> facManualTarifarioMedicamentoList) {
        this.facManualTarifarioMedicamentoList = facManualTarifarioMedicamentoList;
    }

    @XmlTransient
    public List<FacManualTarifarioInsumo> getFacManualTarifarioInsumoList() {
        return facManualTarifarioInsumoList;
    }

    public void setFacManualTarifarioInsumoList(List<FacManualTarifarioInsumo> facManualTarifarioInsumoList) {
        this.facManualTarifarioInsumoList = facManualTarifarioInsumoList;
    }

    @XmlTransient
    public List<FacManualTarifarioPaquete> getFacManualTarifarioPaqueteList() {
        return facManualTarifarioPaqueteList;
    }

    public void setFacManualTarifarioPaqueteList(List<FacManualTarifarioPaquete> facManualTarifarioPaqueteList) {
        this.facManualTarifarioPaqueteList = facManualTarifarioPaqueteList;
    }

    @XmlTransient
    public List<FacPrograma> getFacProgramaList() {
        return facProgramaList;
    }

    public void setFacProgramaList(List<FacPrograma> facProgramaList) {
        this.facProgramaList = facProgramaList;
    }

    @XmlTransient
    public List<FacContrato> getFacContratoList() {
        return facContratoList;
    }

    public void setFacContratoList(List<FacContrato> facContratoList) {
        this.facContratoList = facContratoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idManualTarifario != null ? idManualTarifario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacManualTarifario)) {
            return false;
        }
        FacManualTarifario other = (FacManualTarifario) object;
        if ((this.idManualTarifario == null && other.idManualTarifario != null) || (this.idManualTarifario != null && !this.idManualTarifario.equals(other.idManualTarifario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.FacManualTarifario[ idManualTarifario=" + idManualTarifario + " ]";
    }
    
}
