/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.entidades;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "xlab_orden_estudio_prueba_resultados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findAll", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findById", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.id = :id"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByNeutrofilos", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.neutrofilos = :neutrofilos"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByLinfocitos", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.linfocitos = :linfocitos"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByCayados", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.cayados = :cayados"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByEosinofilos", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.eosinofilos = :eosinofilos"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByMonocitos", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.monocitos = :monocitos"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByBasofilos", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.basofilos = :basofilos"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByMetamielocitos", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.metamielocitos = :metamielocitos"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByNormoblastos", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.normoblastos = :normoblastos"),
    @NamedQuery(name = "XlabOrdenEstudioPruebaResultados.findByObservaciones", query = "SELECT x FROM XlabOrdenEstudioPruebaResultados x WHERE x.observaciones = :observaciones")})
public class XlabOrdenEstudioPruebaResultados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "neutrofilos")
    private Double neutrofilos;
    @Column(name = "linfocitos")
    private Double linfocitos;
    @Column(name = "cayados")
    private Double cayados;
    @Column(name = "eosinofilos")
    private Double eosinofilos;
    @Column(name = "monocitos")
    private Double monocitos;
    @Column(name = "basofilos")
    private Double basofilos;
    @Column(name = "metamielocitos")
    private Double metamielocitos;
    @Column(name = "normoblastos")
    private Double normoblastos;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "orden_estudios_pruebas_id", referencedColumnName = "id")
    @ManyToOne
    private XlabOrdenEstudiosPruebas ordenEstudiosPruebasId;

    public XlabOrdenEstudioPruebaResultados() {
    }

    public XlabOrdenEstudioPruebaResultados(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNeutrofilos() {
        return neutrofilos;
    }

    public void setNeutrofilos(Double neutrofilos) {
        this.neutrofilos = neutrofilos;
    }

    public Double getLinfocitos() {
        return linfocitos;
    }

    public void setLinfocitos(Double linfocitos) {
        this.linfocitos = linfocitos;
    }

    public Double getCayados() {
        return cayados;
    }

    public void setCayados(Double cayados) {
        this.cayados = cayados;
    }

    public Double getEosinofilos() {
        return eosinofilos;
    }

    public void setEosinofilos(Double eosinofilos) {
        this.eosinofilos = eosinofilos;
    }

    public Double getMonocitos() {
        return monocitos;
    }

    public void setMonocitos(Double monocitos) {
        this.monocitos = monocitos;
    }

    public Double getBasofilos() {
        return basofilos;
    }

    public void setBasofilos(Double basofilos) {
        this.basofilos = basofilos;
    }

    public Double getMetamielocitos() {
        return metamielocitos;
    }

    public void setMetamielocitos(Double metamielocitos) {
        this.metamielocitos = metamielocitos;
    }

    public Double getNormoblastos() {
        return normoblastos;
    }

    public void setNormoblastos(Double normoblastos) {
        this.normoblastos = normoblastos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public XlabOrdenEstudiosPruebas getOrdenEstudiosPruebasId() {
        return ordenEstudiosPruebasId;
    }

    public void setOrdenEstudiosPruebasId(XlabOrdenEstudiosPruebas ordenEstudiosPruebasId) {
        this.ordenEstudiosPruebasId = ordenEstudiosPruebasId;
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
        if (!(object instanceof XlabOrdenEstudioPruebaResultados)) {
            return false;
        }
        XlabOrdenEstudioPruebaResultados other = (XlabOrdenEstudioPruebaResultados) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.XlabOrdenEstudioPruebaResultados[ id=" + id + " ]";
    }
    
}
