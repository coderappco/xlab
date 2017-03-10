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
@Table(name = "xlab_consecutivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "XlabConsecutivos.findAll", query = "SELECT x FROM XlabConsecutivos x"),
    @NamedQuery(name = "XlabConsecutivos.findById", query = "SELECT x FROM XlabConsecutivos x WHERE x.id = :id"),
    @NamedQuery(name = "XlabConsecutivos.findByNumeroActual", query = "SELECT x FROM XlabConsecutivos x WHERE x.numeroActual = :numeroActual"),
    @NamedQuery(name = "XlabConsecutivos.findByDecimales", query = "SELECT x FROM XlabConsecutivos x WHERE x.decimales = :decimales"),
    @NamedQuery(name = "XlabConsecutivos.findByPrefijo", query = "SELECT x FROM XlabConsecutivos x WHERE x.prefijo = :prefijo"),
    @NamedQuery(name = "XlabConsecutivos.findByTexto", query = "SELECT x FROM XlabConsecutivos x WHERE x.texto = :texto")})
public class XlabConsecutivos implements Serializable {
    @Size(max = 10)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "automatico")
    private Boolean automatico;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numero_actual")
    private Integer numeroActual;
    @Column(name = "decimales")
    private Integer decimales;
    @Size(max = 6)
    @Column(name = "prefijo")
    private String prefijo;
    @Size(max = 1000)
    @Column(name = "texto")
    private String texto;

    public XlabConsecutivos() {
    }

    public XlabConsecutivos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroActual() {
        return numeroActual;
    }

    public void setNumeroActual(Integer numeroActual) {
        this.numeroActual = numeroActual;
    }

    public Integer getDecimales() {
        return decimales;
    }

    public void setDecimales(Integer decimales) {
        this.decimales = decimales;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
        if (!(object instanceof XlabConsecutivos)) {
            return false;
        }
        XlabConsecutivos other = (XlabConsecutivos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coderappco.xlab.entidades.XlabConsecutivos[ id=" + id + " ]";
    }

    public Boolean getAutomatico() {
        return automatico;
    }

    public void setAutomatico(Boolean automatico) {
        this.automatico = automatico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
