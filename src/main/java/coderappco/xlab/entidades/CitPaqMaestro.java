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
@Table(name = "cit_paq_maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CitPaqMaestro.findAll", query = "SELECT c FROM CitPaqMaestro c"),
    @NamedQuery(name = "CitPaqMaestro.findByIdPaqMaestro", query = "SELECT c FROM CitPaqMaestro c WHERE c.idPaqMaestro = :idPaqMaestro"),
    @NamedQuery(name = "CitPaqMaestro.findByCodPaquete", query = "SELECT c FROM CitPaqMaestro c WHERE c.codPaquete = :codPaquete"),
    @NamedQuery(name = "CitPaqMaestro.findByNomPaquete", query = "SELECT c FROM CitPaqMaestro c WHERE c.nomPaquete = :nomPaquete"),
    @NamedQuery(name = "CitPaqMaestro.findByDias", query = "SELECT c FROM CitPaqMaestro c WHERE c.dias = :dias"),
    @NamedQuery(name = "CitPaqMaestro.findByNoPaqAplicado", query = "SELECT c FROM CitPaqMaestro c WHERE c.noPaqAplicado = :noPaqAplicado")})
public class CitPaqMaestro implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaqMaestro")
    private List<CitPaqDetalle> citPaqDetalleList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_paq_maestro")
    private Integer idPaqMaestro;
    @Size(max = 5)
    @Column(name = "cod_paquete")
    private String codPaquete;
    @Size(max = 200)
    @Column(name = "nom_paquete")
    private String nomPaquete;
    @Size(max = 7)
    @Column(name = "dias")
    private String dias;
    @Column(name = "no_paq_aplicado")
    private Integer noPaqAplicado;
    @OneToMany(mappedBy = "idPaquete")
    private List<CitCitas> citCitasList;

    public CitPaqMaestro() {
    }

    public CitPaqMaestro(Integer idPaqMaestro) {
        this.idPaqMaestro = idPaqMaestro;
    }

    public Integer getIdPaqMaestro() {
        return idPaqMaestro;
    }

    public void setIdPaqMaestro(Integer idPaqMaestro) {
        this.idPaqMaestro = idPaqMaestro;
    }

    public String getCodPaquete() {
        return codPaquete;
    }

    public void setCodPaquete(String codPaquete) {
        this.codPaquete = codPaquete;
    }

    public String getNomPaquete() {
        return nomPaquete;
    }

    public void setNomPaquete(String nomPaquete) {
        this.nomPaquete = nomPaquete;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public Integer getNoPaqAplicado() {
        return noPaqAplicado;
    }

    public void setNoPaqAplicado(Integer noPaqAplicado) {
        this.noPaqAplicado = noPaqAplicado;
    }

    @XmlTransient
    public List<CitCitas> getCitCitasList() {
        return citCitasList;
    }

    public void setCitCitasList(List<CitCitas> citCitasList) {
        this.citCitasList = citCitasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaqMaestro != null ? idPaqMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitPaqMaestro)) {
            return false;
        }
        CitPaqMaestro other = (CitPaqMaestro) object;
        if ((this.idPaqMaestro == null && other.idPaqMaestro != null) || (this.idPaqMaestro != null && !this.idPaqMaestro.equals(other.idPaqMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.CitPaqMaestro[ idPaqMaestro=" + idPaqMaestro + " ]";
    }

    @XmlTransient
    public List<CitPaqDetalle> getCitPaqDetalleList() {
        return citPaqDetalleList;
    }

    public void setCitPaqDetalleList(List<CitPaqDetalle> citPaqDetalleList) {
        this.citPaqDetalleList = citPaqDetalleList;
    }
    
}
