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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "cit_turnos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CitTurnos.findAll", query = "SELECT c FROM CitTurnos c"),
    @NamedQuery(name = "CitTurnos.findByIdTurno", query = "SELECT c FROM CitTurnos c WHERE c.idTurno = :idTurno"),
    @NamedQuery(name = "CitTurnos.findByFecha", query = "SELECT c FROM CitTurnos c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CitTurnos.findByHoraIni", query = "SELECT c FROM CitTurnos c WHERE c.horaIni = :horaIni"),
    @NamedQuery(name = "CitTurnos.findByHoraFin", query = "SELECT c FROM CitTurnos c WHERE c.horaFin = :horaFin"),
    @NamedQuery(name = "CitTurnos.findByEstado", query = "SELECT c FROM CitTurnos c WHERE c.estado = :estado"),
    @NamedQuery(name = "CitTurnos.findByConcurrencia", query = "SELECT c FROM CitTurnos c WHERE c.concurrencia = :concurrencia"),
    @NamedQuery(name = "CitTurnos.findByContador", query = "SELECT c FROM CitTurnos c WHERE c.contador = :contador"),
    @NamedQuery(name = "CitTurnos.findByFechaCreacion", query = "SELECT c FROM CitTurnos c WHERE c.fechaCreacion = :fechaCreacion")})
public class CitTurnos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_turno")
    private Integer idTurno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_ini")
    @Temporal(TemporalType.TIME)
    private Date horaIni;
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;
    @Size(max = 2147483647)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "concurrencia")
    private int concurrencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador")
    private int contador;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTurno")
    private List<CitCitas> citCitasList;
    @JoinColumn(name = "id_prestador", referencedColumnName = "id_usuario")
    @ManyToOne
    private CfgUsuarios idPrestador;
    @JoinColumn(name = "id_horario", referencedColumnName = "id_horario")
    @ManyToOne
    private CfgHorario idHorario;
    @JoinColumn(name = "id_consultorio", referencedColumnName = "id_consultorio")
    @ManyToOne
    private CfgConsultorios idConsultorio;

    public CitTurnos() {
    }

    public CitTurnos(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public CitTurnos(Integer idTurno, Date fecha, Date horaIni, int concurrencia, int contador) {
        this.idTurno = idTurno;
        this.fecha = fecha;
        this.horaIni = horaIni;
        this.concurrencia = concurrencia;
        this.contador = contador;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(Date horaIni) {
        this.horaIni = horaIni;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getConcurrencia() {
        return concurrencia;
    }

    public void setConcurrencia(int concurrencia) {
        this.concurrencia = concurrencia;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<CitCitas> getCitCitasList() {
        return citCitasList;
    }

    public void setCitCitasList(List<CitCitas> citCitasList) {
        this.citCitasList = citCitasList;
    }

    public CfgUsuarios getIdPrestador() {
        return idPrestador;
    }

    public void setIdPrestador(CfgUsuarios idPrestador) {
        this.idPrestador = idPrestador;
    }

    public CfgHorario getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(CfgHorario idHorario) {
        this.idHorario = idHorario;
    }

    public CfgConsultorios getIdConsultorio() {
        return idConsultorio;
    }

    public void setIdConsultorio(CfgConsultorios idConsultorio) {
        this.idConsultorio = idConsultorio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurno != null ? idTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitTurnos)) {
            return false;
        }
        CitTurnos other = (CitTurnos) object;
        if ((this.idTurno == null && other.idTurno != null) || (this.idTurno != null && !this.idTurno.equals(other.idTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.CitTurnos[ idTurno=" + idTurno + " ]";
    }
    
}
