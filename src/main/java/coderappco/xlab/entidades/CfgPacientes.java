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
@Table(name = "cfg_pacientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CfgPacientes.findAll", query = "SELECT c FROM CfgPacientes c"),
    @NamedQuery(name = "CfgPacientes.findByIdPaciente", query = "SELECT c FROM CfgPacientes c WHERE c.idPaciente = :idPaciente"),
    @NamedQuery(name = "CfgPacientes.findByIdentificacion", query = "SELECT c FROM CfgPacientes c WHERE c.identificacion = :identificacion"),
    @NamedQuery(name = "CfgPacientes.findByLugarExpedicion", query = "SELECT c FROM CfgPacientes c WHERE c.lugarExpedicion = :lugarExpedicion"),
    @NamedQuery(name = "CfgPacientes.findByFechaNacimiento", query = "SELECT c FROM CfgPacientes c WHERE c.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "CfgPacientes.findByPrimerApellido", query = "SELECT c FROM CfgPacientes c WHERE c.primerApellido = :primerApellido"),
    @NamedQuery(name = "CfgPacientes.findBySegundoApellido", query = "SELECT c FROM CfgPacientes c WHERE c.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "CfgPacientes.findByPrimerNombre", query = "SELECT c FROM CfgPacientes c WHERE c.primerNombre = :primerNombre"),
    @NamedQuery(name = "CfgPacientes.findBySegundoNombre", query = "SELECT c FROM CfgPacientes c WHERE c.segundoNombre = :segundoNombre"),
    @NamedQuery(name = "CfgPacientes.findByActivo", query = "SELECT c FROM CfgPacientes c WHERE c.activo = :activo"),
    @NamedQuery(name = "CfgPacientes.findByDireccion", query = "SELECT c FROM CfgPacientes c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "CfgPacientes.findByTelefonoResidencia", query = "SELECT c FROM CfgPacientes c WHERE c.telefonoResidencia = :telefonoResidencia"),
    @NamedQuery(name = "CfgPacientes.findByTelefonoOficina", query = "SELECT c FROM CfgPacientes c WHERE c.telefonoOficina = :telefonoOficina"),
    @NamedQuery(name = "CfgPacientes.findByCelular", query = "SELECT c FROM CfgPacientes c WHERE c.celular = :celular"),
    @NamedQuery(name = "CfgPacientes.findByFechaAfiliacion", query = "SELECT c FROM CfgPacientes c WHERE c.fechaAfiliacion = :fechaAfiliacion"),
    @NamedQuery(name = "CfgPacientes.findByFechaSisben", query = "SELECT c FROM CfgPacientes c WHERE c.fechaSisben = :fechaSisben"),
    @NamedQuery(name = "CfgPacientes.findByCarnet", query = "SELECT c FROM CfgPacientes c WHERE c.carnet = :carnet"),
    @NamedQuery(name = "CfgPacientes.findByBarrio", query = "SELECT c FROM CfgPacientes c WHERE c.barrio = :barrio"),
    @NamedQuery(name = "CfgPacientes.findByResponsable", query = "SELECT c FROM CfgPacientes c WHERE c.responsable = :responsable"),
    @NamedQuery(name = "CfgPacientes.findByAcompanante", query = "SELECT c FROM CfgPacientes c WHERE c.acompanante = :acompanante"),
    @NamedQuery(name = "CfgPacientes.findByTelefonoAcompanante", query = "SELECT c FROM CfgPacientes c WHERE c.telefonoAcompanante = :telefonoAcompanante"),
    @NamedQuery(name = "CfgPacientes.findByHistoria", query = "SELECT c FROM CfgPacientes c WHERE c.historia = :historia"),
    @NamedQuery(name = "CfgPacientes.findByFechainsc", query = "SELECT c FROM CfgPacientes c WHERE c.fechainsc = :fechainsc"),
    @NamedQuery(name = "CfgPacientes.findByFacturarlo", query = "SELECT c FROM CfgPacientes c WHERE c.facturarlo = :facturarlo"),
    @NamedQuery(name = "CfgPacientes.findByDircatastrofe", query = "SELECT c FROM CfgPacientes c WHERE c.dircatastrofe = :dircatastrofe"),
    @NamedQuery(name = "CfgPacientes.findByMunicipiocatastrofe", query = "SELECT c FROM CfgPacientes c WHERE c.municipiocatastrofe = :municipiocatastrofe"),
    @NamedQuery(name = "CfgPacientes.findByDepartamentocatastrofe", query = "SELECT c FROM CfgPacientes c WHERE c.departamentocatastrofe = :departamentocatastrofe"),
    @NamedQuery(name = "CfgPacientes.findByZonacatastrofe", query = "SELECT c FROM CfgPacientes c WHERE c.zonacatastrofe = :zonacatastrofe"),
    @NamedQuery(name = "CfgPacientes.findByFechacatastrofe", query = "SELECT c FROM CfgPacientes c WHERE c.fechacatastrofe = :fechacatastrofe"),
    @NamedQuery(name = "CfgPacientes.findByFechaop", query = "SELECT c FROM CfgPacientes c WHERE c.fechaop = :fechaop"),
    @NamedQuery(name = "CfgPacientes.findByGrado", query = "SELECT c FROM CfgPacientes c WHERE c.grado = :grado"),
    @NamedQuery(name = "CfgPacientes.findByPensionado", query = "SELECT c FROM CfgPacientes c WHERE c.pensionado = :pensionado"),
    @NamedQuery(name = "CfgPacientes.findByEmail", query = "SELECT c FROM CfgPacientes c WHERE c.email = :email"),
    @NamedQuery(name = "CfgPacientes.findByNumeroAutorizacion", query = "SELECT c FROM CfgPacientes c WHERE c.numeroAutorizacion = :numeroAutorizacion"),
    @NamedQuery(name = "CfgPacientes.findByTelefonoResponsable", query = "SELECT c FROM CfgPacientes c WHERE c.telefonoResponsable = :telefonoResponsable"),
    @NamedQuery(name = "CfgPacientes.findByFechaVenceCarnet", query = "SELECT c FROM CfgPacientes c WHERE c.fechaVenceCarnet = :fechaVenceCarnet"),
    @NamedQuery(name = "CfgPacientes.findByObservaciones", query = "SELECT c FROM CfgPacientes c WHERE c.observaciones = :observaciones")})
public class CfgPacientes implements Serializable {

    @Column(name = "id_discapacidad")
    private Integer idDiscapacidad;
    @Column(name = "id_gestacion")
    private Integer idGestacion;
    @Column(name = "poblacion_lbgt")
    private Boolean poblacionLbgt;
    @Column(name = "desplazado")
    private Boolean desplazado;
    @Column(name = "id_religion")
    private Integer idReligion;
    @Column(name = "victima_maltrato")
    private Boolean victimaMaltrato;
    @Column(name = "victima_conflicto_armado")
    private Boolean victimaConflictoArmado;
    @OneToMany(mappedBy = "pacienteId")
    private List<XlabOrden> xlabOrdenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<CitCitas> citCitasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<FacConsumoInsumo> facConsumoInsumoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<FacConsumoMedicamento> facConsumoMedicamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<FacConsumoPaquete> facConsumoPaqueteList;
    @OneToMany(mappedBy = "paciente")
    private List<CitAutorizaciones> citAutorizacionesList;
    @OneToMany(mappedBy = "idPaciente")
    private List<FacFacturaPaciente> facFacturaPacienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<FacConsumoServicio> facConsumoServicioList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_paciente")
    private Integer idPaciente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 30)
    @Column(name = "lugar_expedicion")
    private String lugarExpedicion;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Size(max = 30)
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Size(max = 30)
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Size(max = 20)
    @Column(name = "primer_nombre")
    private String primerNombre;
    @Size(max = 20)
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    @Column(name = "activo")
    private Boolean activo;
    @Size(max = 80)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 100)
    @Column(name = "telefono_residencia")
    private String telefonoResidencia;
    @Size(max = 20)
    @Column(name = "telefono_oficina")
    private String telefonoOficina;
    @Size(max = 20)
    @Column(name = "celular")
    private String celular;
    @Column(name = "fecha_afiliacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAfiliacion;
    @Column(name = "fecha_sisben")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSisben;
    @Size(max = 40)
    @Column(name = "carnet")
    private String carnet;
    @Size(max = 2147483647)
    @Column(name = "barrio")
    private String barrio;
    @Size(max = 50)
    @Column(name = "responsable")
    private String responsable;
    @Size(max = 50)
    @Column(name = "acompanante")
    private String acompanante;
    @Size(max = 100)
    @Column(name = "telefono_acompanante")
    private String telefonoAcompanante;
    @Size(max = 10)
    @Column(name = "historia")
    private String historia;
    @Column(name = "fechainsc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainsc;
    @Column(name = "facturarlo")
    private Boolean facturarlo;
    @Size(max = 50)
    @Column(name = "dircatastrofe")
    private String dircatastrofe;
    @Column(name = "municipiocatastrofe")
    private Integer municipiocatastrofe;
    @Column(name = "departamentocatastrofe")
    private Integer departamentocatastrofe;
    @Size(max = 1)
    @Column(name = "zonacatastrofe")
    private String zonacatastrofe;
    @Column(name = "fechacatastrofe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacatastrofe;
    @Column(name = "fechaop")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaop;
    @Size(max = 30)
    @Column(name = "grado")
    private String grado;
    @Column(name = "pensionado")
    private Boolean pensionado;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 300)
    @Column(name = "email")
    private String email;
    @Size(max = 50)
    @Column(name = "numero_autorizacion")
    private String numeroAutorizacion;
    @Size(max = 20)
    @Column(name = "telefono_responsable")
    private String telefonoResponsable;
    @Column(name = "fecha_vence_carnet")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenceCarnet;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_administradora", referencedColumnName = "id_administradora")
    @ManyToOne
    private FacAdministradora idAdministradora;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne
    private CfgImagenes firma;
    @JoinColumn(name = "foto", referencedColumnName = "id")
    @ManyToOne
    private CfgImagenes foto;
    @JoinColumn(name = "regimen", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones regimen;
    @JoinColumn(name = "ocupacion", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones ocupacion;
    @JoinColumn(name = "nivel", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones nivel;
    @JoinColumn(name = "grupo_sanguineo", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones grupoSanguineo;
    @JoinColumn(name = "genero", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones genero;
    @JoinColumn(name = "categoria_paciente", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones categoriaPaciente;
    @JoinColumn(name = "dep_nacimiento", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones depNacimiento;
    @JoinColumn(name = "tipo_afiliado", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones tipoAfiliado;
    @JoinColumn(name = "tipo_identificacion", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones tipoIdentificacion;
    @JoinColumn(name = "zona", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones zona;
    @JoinColumn(name = "mun_nacimiento", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones munNacimiento;
    @JoinColumn(name = "municipio", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones municipio;
    @JoinColumn(name = "parentesco", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones parentesco;
    @JoinColumn(name = "departamento", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones departamento;
    @JoinColumn(name = "escolaridad", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones escolaridad;
    @JoinColumn(name = "estado_civil", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones estadoCivil;
    @JoinColumn(name = "etnia", referencedColumnName = "id")
    @ManyToOne
    private CfgClasificaciones etnia;

    public CfgPacientes() {
        genero = new CfgClasificaciones();
        estadoCivil = new CfgClasificaciones();
        ocupacion = new CfgClasificaciones();
        grupoSanguineo = new CfgClasificaciones();
        departamento = new CfgClasificaciones();
        municipio = new CfgClasificaciones();
        zona = new CfgClasificaciones();
        idAdministradora = new FacAdministradora();
    }

    public CfgPacientes(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public CfgPacientes(Integer idPaciente, String identificacion) {
        this.idPaciente = idPaciente;
        this.identificacion = identificacion;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getLugarExpedicion() {
        return lugarExpedicion;
    }

    public void setLugarExpedicion(String lugarExpedicion) {
        this.lugarExpedicion = lugarExpedicion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoResidencia() {
        return telefonoResidencia;
    }

    public void setTelefonoResidencia(String telefonoResidencia) {
        this.telefonoResidencia = telefonoResidencia;
    }

    public String getTelefonoOficina() {
        return telefonoOficina;
    }

    public void setTelefonoOficina(String telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(Date fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public Date getFechaSisben() {
        return fechaSisben;
    }

    public void setFechaSisben(Date fechaSisben) {
        this.fechaSisben = fechaSisben;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getAcompanante() {
        return acompanante;
    }

    public void setAcompanante(String acompanante) {
        this.acompanante = acompanante;
    }

    public String getTelefonoAcompanante() {
        return telefonoAcompanante;
    }

    public void setTelefonoAcompanante(String telefonoAcompanante) {
        this.telefonoAcompanante = telefonoAcompanante;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public Date getFechainsc() {
        return fechainsc;
    }

    public void setFechainsc(Date fechainsc) {
        this.fechainsc = fechainsc;
    }

    public Boolean getFacturarlo() {
        return facturarlo;
    }

    public void setFacturarlo(Boolean facturarlo) {
        this.facturarlo = facturarlo;
    }

    public String getDircatastrofe() {
        return dircatastrofe;
    }

    public void setDircatastrofe(String dircatastrofe) {
        this.dircatastrofe = dircatastrofe;
    }

    public Integer getMunicipiocatastrofe() {
        return municipiocatastrofe;
    }

    public void setMunicipiocatastrofe(Integer municipiocatastrofe) {
        this.municipiocatastrofe = municipiocatastrofe;
    }

    public Integer getDepartamentocatastrofe() {
        return departamentocatastrofe;
    }

    public void setDepartamentocatastrofe(Integer departamentocatastrofe) {
        this.departamentocatastrofe = departamentocatastrofe;
    }

    public String getZonacatastrofe() {
        return zonacatastrofe;
    }

    public void setZonacatastrofe(String zonacatastrofe) {
        this.zonacatastrofe = zonacatastrofe;
    }

    public Date getFechacatastrofe() {
        return fechacatastrofe;
    }

    public void setFechacatastrofe(Date fechacatastrofe) {
        this.fechacatastrofe = fechacatastrofe;
    }

    public Date getFechaop() {
        return fechaop;
    }

    public void setFechaop(Date fechaop) {
        this.fechaop = fechaop;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public Boolean getPensionado() {
        return pensionado;
    }

    public void setPensionado(Boolean pensionado) {
        this.pensionado = pensionado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public String getTelefonoResponsable() {
        return telefonoResponsable;
    }

    public void setTelefonoResponsable(String telefonoResponsable) {
        this.telefonoResponsable = telefonoResponsable;
    }

    public Date getFechaVenceCarnet() {
        return fechaVenceCarnet;
    }

    public void setFechaVenceCarnet(Date fechaVenceCarnet) {
        this.fechaVenceCarnet = fechaVenceCarnet;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public FacAdministradora getIdAdministradora() {
        return idAdministradora;
    }

    public void setIdAdministradora(FacAdministradora idAdministradora) {
        this.idAdministradora = idAdministradora;
    }

    public CfgImagenes getFirma() {
        return firma;
    }

    public void setFirma(CfgImagenes firma) {
        this.firma = firma;
    }

    public CfgImagenes getFoto() {
        return foto;
    }

    public void setFoto(CfgImagenes foto) {
        this.foto = foto;
    }

    public CfgClasificaciones getRegimen() {
        return regimen;
    }

    public void setRegimen(CfgClasificaciones regimen) {
        this.regimen = regimen;
    }

    public CfgClasificaciones getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(CfgClasificaciones ocupacion) {
        this.ocupacion = ocupacion;
    }

    public CfgClasificaciones getNivel() {
        return nivel;
    }

    public void setNivel(CfgClasificaciones nivel) {
        this.nivel = nivel;
    }

    public CfgClasificaciones getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(CfgClasificaciones grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public CfgClasificaciones getGenero() {
        return genero;
    }

    public void setGenero(CfgClasificaciones genero) {
        this.genero = genero;
    }

    public CfgClasificaciones getCategoriaPaciente() {
        return categoriaPaciente;
    }

    public void setCategoriaPaciente(CfgClasificaciones categoriaPaciente) {
        this.categoriaPaciente = categoriaPaciente;
    }

    public CfgClasificaciones getDepNacimiento() {
        return depNacimiento;
    }

    public void setDepNacimiento(CfgClasificaciones depNacimiento) {
        this.depNacimiento = depNacimiento;
    }

    public CfgClasificaciones getTipoAfiliado() {
        return tipoAfiliado;
    }

    public void setTipoAfiliado(CfgClasificaciones tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    public CfgClasificaciones getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(CfgClasificaciones tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public CfgClasificaciones getZona() {
        return zona;
    }

    public void setZona(CfgClasificaciones zona) {
        this.zona = zona;
    }

    public CfgClasificaciones getMunNacimiento() {
        return munNacimiento;
    }

    public void setMunNacimiento(CfgClasificaciones munNacimiento) {
        this.munNacimiento = munNacimiento;
    }

    public CfgClasificaciones getMunicipio() {
        return municipio;
    }

    public void setMunicipio(CfgClasificaciones municipio) {
        this.municipio = municipio;
    }

    public CfgClasificaciones getParentesco() {
        return parentesco;
    }

    public void setParentesco(CfgClasificaciones parentesco) {
        this.parentesco = parentesco;
    }

    public CfgClasificaciones getDepartamento() {
        return departamento;
    }

    public void setDepartamento(CfgClasificaciones departamento) {
        this.departamento = departamento;
    }

    public CfgClasificaciones getEscolaridad() {
        return escolaridad;
    }

    public void setEscolaridad(CfgClasificaciones escolaridad) {
        this.escolaridad = escolaridad;
    }

    public CfgClasificaciones getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(CfgClasificaciones estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public CfgClasificaciones getEtnia() {
        return etnia;
    }

    public void setEtnia(CfgClasificaciones etnia) {
        this.etnia = etnia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgPacientes)) {
            return false;
        }
        CfgPacientes other = (CfgPacientes) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.CfgPacientes[ idPaciente=" + idPaciente + " ]";
    }

    @XmlTransient
    public List<CitCitas> getCitCitasList() {
        return citCitasList;
    }

    public void setCitCitasList(List<CitCitas> citCitasList) {
        this.citCitasList = citCitasList;
    }

    @XmlTransient
    public List<FacConsumoInsumo> getFacConsumoInsumoList() {
        return facConsumoInsumoList;
    }

    public void setFacConsumoInsumoList(List<FacConsumoInsumo> facConsumoInsumoList) {
        this.facConsumoInsumoList = facConsumoInsumoList;
    }

    @XmlTransient
    public List<FacConsumoMedicamento> getFacConsumoMedicamentoList() {
        return facConsumoMedicamentoList;
    }

    public void setFacConsumoMedicamentoList(List<FacConsumoMedicamento> facConsumoMedicamentoList) {
        this.facConsumoMedicamentoList = facConsumoMedicamentoList;
    }

    @XmlTransient
    public List<FacConsumoPaquete> getFacConsumoPaqueteList() {
        return facConsumoPaqueteList;
    }

    public void setFacConsumoPaqueteList(List<FacConsumoPaquete> facConsumoPaqueteList) {
        this.facConsumoPaqueteList = facConsumoPaqueteList;
    }

    @XmlTransient
    public List<CitAutorizaciones> getCitAutorizacionesList() {
        return citAutorizacionesList;
    }

    public void setCitAutorizacionesList(List<CitAutorizaciones> citAutorizacionesList) {
        this.citAutorizacionesList = citAutorizacionesList;
    }

    @XmlTransient
    public List<FacFacturaPaciente> getFacFacturaPacienteList() {
        return facFacturaPacienteList;
    }

    public void setFacFacturaPacienteList(List<FacFacturaPaciente> facFacturaPacienteList) {
        this.facFacturaPacienteList = facFacturaPacienteList;
    }

    @XmlTransient
    public List<FacConsumoServicio> getFacConsumoServicioList() {
        return facConsumoServicioList;
    }

    public void setFacConsumoServicioList(List<FacConsumoServicio> facConsumoServicioList) {
        this.facConsumoServicioList = facConsumoServicioList;
    }

    @XmlTransient
    public List<XlabOrden> getXlabOrdenList() {
        return xlabOrdenList;
    }

    public void setXlabOrdenList(List<XlabOrden> xlabOrdenList) {
        this.xlabOrdenList = xlabOrdenList;
    }
    
    public String nombreCompleto() {
        String strNombre = "";
        if (primerNombre != null) {
            strNombre = strNombre + primerNombre + " ";
        }
        if (segundoNombre != null) {
            strNombre = strNombre + segundoNombre + " ";
        }
        if (primerApellido != null) {
            strNombre = strNombre + primerApellido + " ";
        }
        if (segundoApellido != null) {
            strNombre = strNombre + segundoApellido;
        }
        return strNombre;
    }

    public Integer getIdDiscapacidad() {
        return idDiscapacidad;
    }

    public void setIdDiscapacidad(Integer idDiscapacidad) {
        this.idDiscapacidad = idDiscapacidad;
    }

    public Integer getIdGestacion() {
        return idGestacion;
    }

    public void setIdGestacion(Integer idGestacion) {
        this.idGestacion = idGestacion;
    }

    public Boolean getPoblacionLbgt() {
        return poblacionLbgt;
    }

    public void setPoblacionLbgt(Boolean poblacionLbgt) {
        this.poblacionLbgt = poblacionLbgt;
    }

    public Boolean getDesplazado() {
        return desplazado;
    }

    public void setDesplazado(Boolean desplazado) {
        this.desplazado = desplazado;
    }

    public Integer getIdReligion() {
        return idReligion;
    }

    public void setIdReligion(Integer idReligion) {
        this.idReligion = idReligion;
    }

    public Boolean getVictimaMaltrato() {
        return victimaMaltrato;
    }

    public void setVictimaMaltrato(Boolean victimaMaltrato) {
        this.victimaMaltrato = victimaMaltrato;
    }

    public Boolean getVictimaConflictoArmado() {
        return victimaConflictoArmado;
    }

    public void setVictimaConflictoArmado(Boolean victimaConflictoArmado) {
        this.victimaConflictoArmado = victimaConflictoArmado;
    }

}
