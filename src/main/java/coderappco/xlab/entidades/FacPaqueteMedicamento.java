/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "fac_paquete_medicamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacPaqueteMedicamento.findAll", query = "SELECT f FROM FacPaqueteMedicamento f"),
    @NamedQuery(name = "FacPaqueteMedicamento.findByIdPaquete", query = "SELECT f FROM FacPaqueteMedicamento f WHERE f.facPaqueteMedicamentoPK.idPaquete = :idPaquete"),
    @NamedQuery(name = "FacPaqueteMedicamento.findByIdMedicamento", query = "SELECT f FROM FacPaqueteMedicamento f WHERE f.facPaqueteMedicamentoPK.idMedicamento = :idMedicamento"),
    @NamedQuery(name = "FacPaqueteMedicamento.findByValorFinal", query = "SELECT f FROM FacPaqueteMedicamento f WHERE f.valorFinal = :valorFinal"),
    @NamedQuery(name = "FacPaqueteMedicamento.findByDescuento", query = "SELECT f FROM FacPaqueteMedicamento f WHERE f.descuento = :descuento"),
    @NamedQuery(name = "FacPaqueteMedicamento.findByObservacion", query = "SELECT f FROM FacPaqueteMedicamento f WHERE f.observacion = :observacion"),
    @NamedQuery(name = "FacPaqueteMedicamento.findByActivo", query = "SELECT f FROM FacPaqueteMedicamento f WHERE f.activo = :activo"),
    @NamedQuery(name = "FacPaqueteMedicamento.findByCantidad", query = "SELECT f FROM FacPaqueteMedicamento f WHERE f.cantidad = :cantidad"),
    @NamedQuery(name = "FacPaqueteMedicamento.findByValorUnitario", query = "SELECT f FROM FacPaqueteMedicamento f WHERE f.valorUnitario = :valorUnitario")})
public class FacPaqueteMedicamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacPaqueteMedicamentoPK facPaqueteMedicamentoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_final")
    private Double valorFinal;
    @Column(name = "descuento")
    private Double descuento;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "valor_unitario")
    private Double valorUnitario;
    @JoinColumn(name = "id_paquete", referencedColumnName = "id_paquete", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private FacPaquete facPaquete;
    @JoinColumn(name = "id_medicamento", referencedColumnName = "id_medicamento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CfgMedicamento cfgMedicamento;

    public FacPaqueteMedicamento() {
    }

    public FacPaqueteMedicamento(FacPaqueteMedicamentoPK facPaqueteMedicamentoPK) {
        this.facPaqueteMedicamentoPK = facPaqueteMedicamentoPK;
    }

    public FacPaqueteMedicamento(int idPaquete, int idMedicamento) {
        this.facPaqueteMedicamentoPK = new FacPaqueteMedicamentoPK(idPaquete, idMedicamento);
    }

    public FacPaqueteMedicamentoPK getFacPaqueteMedicamentoPK() {
        return facPaqueteMedicamentoPK;
    }

    public void setFacPaqueteMedicamentoPK(FacPaqueteMedicamentoPK facPaqueteMedicamentoPK) {
        this.facPaqueteMedicamentoPK = facPaqueteMedicamentoPK;
    }

    public Double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(Double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public FacPaquete getFacPaquete() {
        return facPaquete;
    }

    public void setFacPaquete(FacPaquete facPaquete) {
        this.facPaquete = facPaquete;
    }

    public CfgMedicamento getCfgMedicamento() {
        return cfgMedicamento;
    }

    public void setCfgMedicamento(CfgMedicamento cfgMedicamento) {
        this.cfgMedicamento = cfgMedicamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facPaqueteMedicamentoPK != null ? facPaqueteMedicamentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacPaqueteMedicamento)) {
            return false;
        }
        FacPaqueteMedicamento other = (FacPaqueteMedicamento) object;
        if ((this.facPaqueteMedicamentoPK == null && other.facPaqueteMedicamentoPK != null) || (this.facPaqueteMedicamentoPK != null && !this.facPaqueteMedicamentoPK.equals(other.facPaqueteMedicamentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.FacPaqueteMedicamento[ facPaqueteMedicamentoPK=" + facPaqueteMedicamentoPK + " ]";
    }
    
}
