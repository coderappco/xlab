/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "xlab_orden_estudios_pruebas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findAll", query = "SELECT x FROM XlabOrdenEstudiosPruebas x"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findById", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.id = :id"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findByEstado", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.estado = :estado"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findByConfirmado", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.confirmado = :confirmado"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findByRefMin", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.refMin = :refMin"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findByRefMax", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.refMax = :refMax"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findByNota", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.nota = :nota"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findByFechaActualizacion", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findByEliminado", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.eliminado = :eliminado"),
    @NamedQuery(name = "XlabOrdenEstudiosPruebas.findByFechaEliminacion", query = "SELECT x FROM XlabOrdenEstudiosPruebas x WHERE x.fechaEliminacion = :fechaEliminacion")})
public class XlabOrdenEstudiosPruebas implements Serializable {
    @JoinColumn(name = "orden_id", referencedColumnName = "id")
    @ManyToOne
    private XlabOrden ordenId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Column(name = "confirmado")
    private Boolean confirmado;
    @Column(name = "resultado")
    private String resultado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ref_min")
    private Double refMin;
    @Column(name = "ref_max")
    private Double refMax;
    @Size(max = 2147483647)
    @Column(name = "nota")
    private String nota;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Column(name = "eliminado")
    private Boolean eliminado;
    @Column(name = "fecha_eliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @OneToMany(mappedBy = "ordenEstudiosPruebasId")
    private List<XlabOrdenEstudioPruebaResultados> xlabOrdenEstudioPruebaResultadosList;
    @JoinColumn(name = "prueba_id", referencedColumnName = "id")
    @ManyToOne
    private XlabPrueba pruebaId;
    @JoinColumn(name = "estudio_id", referencedColumnName = "id")
    @ManyToOne
    private XlabEstudio estudioId;
    @JoinColumn(name = "usuario_actualiza", referencedColumnName = "id_usuario")
    @ManyToOne
    private CfgUsuarios usuarioActualiza;
    @JoinColumn(name = "usuario_elimina", referencedColumnName = "id_usuario")
    @ManyToOne
    private CfgUsuarios usuarioElimina;

    public XlabOrdenEstudiosPruebas() {
    }

    public XlabOrdenEstudiosPruebas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public Double getRefMin() {
        return refMin;
    }

    public void setRefMin(Double refMin) {
        this.refMin = refMin;
    }

    public Double getRefMax() {
        return refMax;
    }

    public void setRefMax(Double refMax) {
        this.refMax = refMax;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    @XmlTransient
    public List<XlabOrdenEstudioPruebaResultados> getXlabOrdenEstudioPruebaResultadosList() {
        return xlabOrdenEstudioPruebaResultadosList;
    }

    public void setXlabOrdenEstudioPruebaResultadosList(List<XlabOrdenEstudioPruebaResultados> xlabOrdenEstudioPruebaResultadosList) {
        this.xlabOrdenEstudioPruebaResultadosList = xlabOrdenEstudioPruebaResultadosList;
    }

    public XlabPrueba getPruebaId() {
        return pruebaId;
    }

    public void setPruebaId(XlabPrueba pruebaId) {
        this.pruebaId = pruebaId;
    }

    public XlabEstudio getEstudioId() {
        return estudioId;
    }

    public void setEstudioId(XlabEstudio estudioId) {
        this.estudioId = estudioId;
    }

    public CfgUsuarios getUsuarioActualiza() {
        return usuarioActualiza;
    }

    public void setUsuarioActualiza(CfgUsuarios usuarioActualiza) {
        this.usuarioActualiza = usuarioActualiza;
    }

    public CfgUsuarios getUsuarioElimina() {
        return usuarioElimina;
    }

    public void setUsuarioElimina(CfgUsuarios usuarioElimina) {
        this.usuarioElimina = usuarioElimina;
    } 

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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
        if (!(object instanceof XlabOrdenEstudiosPruebas)) {
            return false;
        }
        XlabOrdenEstudiosPruebas other = (XlabOrdenEstudiosPruebas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.XlabOrdenEstudiosPruebas[ id=" + id + " ]";
    }

    public XlabOrden getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(XlabOrden ordenId) {
        this.ordenId = ordenId;
    }
    
}
