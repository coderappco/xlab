/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebafaceldi;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "opciones_perfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OpcionesPerfil.findAll", query = "SELECT o FROM OpcionesPerfil o"),
    @NamedQuery(name = "OpcionesPerfil.findByIdPerfil", query = "SELECT o FROM OpcionesPerfil o WHERE o.idPerfil = :idPerfil")})
public class OpcionesPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_perfil")
    private Short idPerfil;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "opcionesPerfil1")
    private OpcionesPerfil opcionesPerfil;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private OpcionesPerfil opcionesPerfil1;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id_opcion")
    @ManyToOne(optional = false)
    private OpcionesMenu idOpcion;

    public OpcionesPerfil() {
    }

    public OpcionesPerfil(Short idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Short getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Short idPerfil) {
        this.idPerfil = idPerfil;
    }

    public OpcionesPerfil getOpcionesPerfil() {
        return opcionesPerfil;
    }

    public void setOpcionesPerfil(OpcionesPerfil opcionesPerfil) {
        this.opcionesPerfil = opcionesPerfil;
    }

    public OpcionesPerfil getOpcionesPerfil1() {
        return opcionesPerfil1;
    }

    public void setOpcionesPerfil1(OpcionesPerfil opcionesPerfil1) {
        this.opcionesPerfil1 = opcionesPerfil1;
    }

    public OpcionesMenu getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(OpcionesMenu idOpcion) {
        this.idOpcion = idOpcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OpcionesPerfil)) {
            return false;
        }
        OpcionesPerfil other = (OpcionesPerfil) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebafaceldi.OpcionesPerfil[ idPerfil=" + idPerfil + " ]";
    }
    
}
