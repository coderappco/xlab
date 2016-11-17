/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Embeddable
public class FacFacturaPaquetePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_detalle")
    private int idDetalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_factura")
    private int idFactura;

    public FacFacturaPaquetePK() {
    }

    public FacFacturaPaquetePK(int idDetalle, int idFactura) {
        this.idDetalle = idDetalle;
        this.idFactura = idFactura;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDetalle;
        hash += (int) idFactura;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacFacturaPaquetePK)) {
            return false;
        }
        FacFacturaPaquetePK other = (FacFacturaPaquetePK) object;
        if (this.idDetalle != other.idDetalle) {
            return false;
        }
        if (this.idFactura != other.idFactura) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.FacFacturaPaquetePK[ idDetalle=" + idDetalle + ", idFactura=" + idFactura + " ]";
    }
    
}
