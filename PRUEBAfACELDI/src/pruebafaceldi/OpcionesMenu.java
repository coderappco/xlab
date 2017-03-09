/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebafaceldi;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Entity
@Table(name = "opciones_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OpcionesMenu.findAll", query = "SELECT o FROM OpcionesMenu o"),
    @NamedQuery(name = "OpcionesMenu.findByIdOpcion", query = "SELECT o FROM OpcionesMenu o WHERE o.idOpcion = :idOpcion"),
    @NamedQuery(name = "OpcionesMenu.findByNombreOpcion", query = "SELECT o FROM OpcionesMenu o WHERE o.nombreOpcion = :nombreOpcion"),
    @NamedQuery(name = "OpcionesMenu.findByUrlOpcion", query = "SELECT o FROM OpcionesMenu o WHERE o.urlOpcion = :urlOpcion")})
public class OpcionesMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_opcion")
    private Short idOpcion;
    @Basic(optional = false)
    @Column(name = "nombre_opcion")
    private String nombreOpcion;
    @Basic(optional = false)
    @Column(name = "url_opcion")
    private String urlOpcion;
    @OneToMany(mappedBy = "idOpcionPadre")
    private List<OpcionesMenu> opcionesMenuList;
    @JoinColumn(name = "id_opcion_padre", referencedColumnName = "id_opcion")
    @ManyToOne
    private OpcionesMenu idOpcionPadre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOpcion")
    private List<OpcionesPerfil> opcionesPerfilList;

    public OpcionesMenu() {
    }

    public OpcionesMenu(Short idOpcion) {
        this.idOpcion = idOpcion;
    }

    public OpcionesMenu(Short idOpcion, String nombreOpcion, String urlOpcion) {
        this.idOpcion = idOpcion;
        this.nombreOpcion = nombreOpcion;
        this.urlOpcion = urlOpcion;
    }

    public Short getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Short idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public String getUrlOpcion() {
        return urlOpcion;
    }

    public void setUrlOpcion(String urlOpcion) {
        this.urlOpcion = urlOpcion;
    }

    @XmlTransient
    public List<OpcionesMenu> getOpcionesMenuList() {
        return opcionesMenuList;
    }

    public void setOpcionesMenuList(List<OpcionesMenu> opcionesMenuList) {
        this.opcionesMenuList = opcionesMenuList;
    }

    public OpcionesMenu getIdOpcionPadre() {
        return idOpcionPadre;
    }

    public void setIdOpcionPadre(OpcionesMenu idOpcionPadre) {
        this.idOpcionPadre = idOpcionPadre;
    }

    @XmlTransient
    public List<OpcionesPerfil> getOpcionesPerfilList() {
        return opcionesPerfilList;
    }

    public void setOpcionesPerfilList(List<OpcionesPerfil> opcionesPerfilList) {
        this.opcionesPerfilList = opcionesPerfilList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcion != null ? idOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OpcionesMenu)) {
            return false;
        }
        OpcionesMenu other = (OpcionesMenu) object;
        if ((this.idOpcion == null && other.idOpcion != null) || (this.idOpcion != null && !this.idOpcion.equals(other.idOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebafaceldi.OpcionesMenu[ idOpcion=" + idOpcion + " ]";
    }
    
}
