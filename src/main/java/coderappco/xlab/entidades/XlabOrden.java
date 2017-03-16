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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "xlab_orden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "XlabOrden.findAll", query = "SELECT x FROM XlabOrden x"),
    @NamedQuery(name = "XlabOrden.findById", query = "SELECT x FROM XlabOrden x WHERE x.id = :id"),
    @NamedQuery(name = "XlabOrden.findByNroOrden", query = "SELECT x FROM XlabOrden x WHERE x.nroOrden = :nroOrden"),
    @NamedQuery(name = "XlabOrden.findByFechaOrden", query = "SELECT x FROM XlabOrden x WHERE x.fechaOrden = :fechaOrden"),
    @NamedQuery(name = "XlabOrden.findByEmbarazo", query = "SELECT x FROM XlabOrden x WHERE x.embarazo = :embarazo"),
    @NamedQuery(name = "XlabOrden.findByAutorizacion", query = "SELECT x FROM XlabOrden x WHERE x.autorizacion = :autorizacion"),
    @NamedQuery(name = "XlabOrden.findByFechaAutorizacion", query = "SELECT x FROM XlabOrden x WHERE x.fechaAutorizacion = :fechaAutorizacion"),
    @NamedQuery(name = "XlabOrden.findByNroExteno", query = "SELECT x FROM XlabOrden x WHERE x.nroExteno = :nroExteno"),
    @NamedQuery(name = "XlabOrden.findByCama", query = "SELECT x FROM XlabOrden x WHERE x.cama = :cama"),
    @NamedQuery(name = "XlabOrden.findByNota", query = "SELECT x FROM XlabOrden x WHERE x.nota = :nota"),
    @NamedQuery(name = "XlabOrden.findByEstado", query = "SELECT x FROM XlabOrden x WHERE x.estado = :estado"),
    @NamedQuery(name = "XlabOrden.findByFechaEstado", query = "SELECT x FROM XlabOrden x WHERE x.fechaEstado = :fechaEstado")})
public class XlabOrden implements Serializable {

    @OneToMany(mappedBy = "idOrden")
    private List<XlabOrdenVisor> xlabOrdenVisorList;
    @JoinColumn(name = "empresa_id", referencedColumnName = "cod_empresa")
    @ManyToOne
    private CfgEmpresa empresaId;
    @OneToMany(mappedBy = "ordenId")
    private List<XlabOrdenEstudiosPruebas> xlabOrdenEstudiosPruebasList;
    @Column(name = "fecha_eliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEliminacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @JoinColumn(name = "usuario_crea", referencedColumnName = "id_usuario")
    @ManyToOne
    private CfgUsuarios usuarioCrea;
    @JoinColumn(name = "usuario_elimina", referencedColumnName = "id_usuario")
    @ManyToOne
    private CfgUsuarios usuarioElimina;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "nro_orden")
    private String nroOrden;
    @Column(name = "fecha_orden")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOrden;
    @Column(name = "embarazo")
    private Integer embarazo;
    @Size(max = 20)
    @Column(name = "autorizacion")
    private String autorizacion;
    @Column(name = "fecha_autorizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAutorizacion;
    @Size(max = 10)
    @Column(name = "nro_exteno")
    private String nroExteno;
    @Column(name = "cama")
    private Integer cama;
    @Size(max = 2147483647)
    @Column(name = "nota")
    private String nota;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_estado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @JoinTable(name = "xlab_orden_estudios", joinColumns = {
        @JoinColumn(name = "orden_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "estudio_id", referencedColumnName = "id")})
    @ManyToMany
    private List<XlabEstudio> xlabEstudioList;
    @JoinColumn(name = "origen_id", referencedColumnName = "id_administradora")
    @ManyToOne
    private FacAdministradora origenId;
    @JoinColumn(name = "medico_id", referencedColumnName = "id_usuario")
    @ManyToOne
    private CfgUsuarios medicoId;
    @JoinColumn(name = "paciente_id", referencedColumnName = "id_paciente")
    @ManyToOne
    private CfgPacientes pacienteId;
    @JoinColumn(name = "diagnostico_id", referencedColumnName = "codigo_diagnostico")
    @ManyToOne
    private CfgDiagnostico diagnosticoId;
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones servicioId;

    public XlabOrden() {
        origenId = new FacAdministradora();
        diagnosticoId= new CfgDiagnostico();
        servicioId = new CfgClasificaciones();
        medicoId = new CfgUsuarios();
    }

    public XlabOrden(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNroOrden() {
        return nroOrden;
    }

    public void setNroOrden(String nroOrden) {
        this.nroOrden = nroOrden;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public Integer getEmbarazo() {
        return embarazo;
    }

    public void setEmbarazo(Integer embarazo) {
        this.embarazo = embarazo;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getNroExteno() {
        return nroExteno;
    }

    public void setNroExteno(String nroExteno) {
        this.nroExteno = nroExteno;
    }

    public Integer getCama() {
        return cama;
    }

    public void setCama(Integer cama) {
        this.cama = cama;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    @XmlTransient
    public List<XlabEstudio> getXlabEstudioList() {
        return xlabEstudioList;
    }

    public void setXlabEstudioList(List<XlabEstudio> xlabEstudioList) {
        this.xlabEstudioList = xlabEstudioList;
    }

    public FacAdministradora getOrigenId() {
        return origenId;
    }

    public void setOrigenId(FacAdministradora origenId) {
        this.origenId = origenId;
    }

    public CfgUsuarios getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(CfgUsuarios medicoId) {
        this.medicoId = medicoId;
    }

    public CfgPacientes getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(CfgPacientes pacienteId) {
        this.pacienteId = pacienteId;
    }

    public CfgDiagnostico getDiagnosticoId() {
        return diagnosticoId;
    }

    public void setDiagnosticoId(CfgDiagnostico diagnosticoId) {
        this.diagnosticoId = diagnosticoId;
    }

    public CfgClasificaciones getServicioId() {
        return servicioId;
    }

    public void setServicioId(CfgClasificaciones servicioId) {
        this.servicioId = servicioId;
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
        if (!(object instanceof XlabOrden)) {
            return false;
        }
        XlabOrden other = (XlabOrden) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.XlabOrden[ id=" + id + " ]";
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public CfgUsuarios getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(CfgUsuarios usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public CfgUsuarios getUsuarioElimina() {
        return usuarioElimina;
    }

    public void setUsuarioElimina(CfgUsuarios usuarioElimina) {
        this.usuarioElimina = usuarioElimina;
    }

    @XmlTransient
    public List<XlabOrdenEstudiosPruebas> getXlabOrdenEstudiosPruebasList() {
        return xlabOrdenEstudiosPruebasList;
    }

    public void setXlabOrdenEstudiosPruebasList(List<XlabOrdenEstudiosPruebas> xlabOrdenEstudiosPruebasList) {
        this.xlabOrdenEstudiosPruebasList = xlabOrdenEstudiosPruebasList;
    }

    public CfgEmpresa getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(CfgEmpresa empresaId) {
        this.empresaId = empresaId;
    }

    @XmlTransient
    public List<XlabOrdenVisor> getXlabOrdenVisorList() {
        return xlabOrdenVisorList;
    }

    public void setXlabOrdenVisorList(List<XlabOrdenVisor> xlabOrdenVisorList) {
        this.xlabOrdenVisorList = xlabOrdenVisorList;
    }
    
}
