/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author miguel
 */
@Entity
@Table(name = "xlab_orden_visor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "XlabOrdenVisor.findAll", query = "SELECT x FROM XlabOrdenVisor x")
    , @NamedQuery(name = "XlabOrdenVisor.findByIdVisor", query = "SELECT x FROM XlabOrdenVisor x WHERE x.idVisor = :idVisor")
    , @NamedQuery(name = "XlabOrdenVisor.findByFecha", query = "SELECT x FROM XlabOrdenVisor x WHERE x.fecha = :fecha")
    , @NamedQuery(name = "XlabOrdenVisor.findByPrueba", query = "SELECT x FROM XlabOrdenVisor x WHERE x.prueba = :prueba")
    , @NamedQuery(name = "XlabOrdenVisor.findByOperacion", query = "SELECT x FROM XlabOrdenVisor x WHERE x.operacion = :operacion")
    , @NamedQuery(name = "XlabOrdenVisor.findByNota", query = "SELECT x FROM XlabOrdenVisor x WHERE x.nota = :nota")})
public class XlabOrdenVisor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_visor")
    private Integer idVisor;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 500)
    @Column(name = "prueba")
    private String prueba;
    @Size(max = 500)
    @Column(name = "operacion")
    private String operacion;
    @Size(max = 2147483647)
    @Column(name = "nota")
    private String nota;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private CfgUsuarios usuario;
    @JoinColumn(name = "id_orden", referencedColumnName = "id")
    @ManyToOne
    private XlabOrden idOrden;

    @Transient 
    private String fechaFormato;
    public XlabOrdenVisor() {
    }

    public XlabOrdenVisor(Integer idVisor) {
        this.idVisor = idVisor;
    }

    public Integer getIdVisor() {
        return idVisor;
    }

    public void setIdVisor(Integer idVisor) {
        this.idVisor = idVisor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public CfgUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(CfgUsuarios usuario) {
        this.usuario = usuario;
    }

    public XlabOrden getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(XlabOrden idOrden) {
        this.idOrden = idOrden;
    }

    public String getFechaFormato() {
        if(fecha!=null){
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            fechaFormato = formato.format(fecha);
        }
        return fechaFormato;
    }

    public void setFechaFormato(String fechaFormato) {
        this.fechaFormato = fechaFormato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisor != null ? idVisor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof XlabOrdenVisor)) {
            return false;
        }
        XlabOrdenVisor other = (XlabOrdenVisor) object;
        if ((this.idVisor == null && other.idVisor != null) || (this.idVisor != null && !this.idVisor.equals(other.idVisor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.XlabOrdenVisor[ idVisor=" + idVisor + " ]";
    }
    
}
