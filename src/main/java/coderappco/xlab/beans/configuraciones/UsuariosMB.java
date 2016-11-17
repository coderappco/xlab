/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.entidades.CfgImagenes;
import coderappco.xlab.entidades.CfgPerfilesUsuario;
import coderappco.xlab.entidades.CfgUsuarios;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import coderappco.xlab.facades.CfgImagenesFacade;
import coderappco.xlab.facades.CfgPerfilesUsuarioFacade;
import coderappco.xlab.facades.CfgUsuariosFacade;
import coderappco.xlab.utilidades.ClasificacionesEnum;
import coderappco.xlab.utilidades.SessionUtil;
import coderappco.xlab.utilidades.Utilidades;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "usuariosMB")
@SessionScoped
public class UsuariosMB extends Controlador implements Serializable {

    private boolean renderForm;
    private boolean renderTipoUsuario;
    private UploadedFile archivoFirma;
    private UploadedFile archivoFoto;
    private String firmaPorDefecto = "../resources/img/firma.png";
    private String fotoPorDefecto = "../resources/img/img_usuario.png";
    private String urlFirma = firmaPorDefecto;
    private String urlFoto = fotoPorDefecto;
    private boolean fotoTomadaWebCam = false;//saber si la foto se tomo de la webcam
    private String password2;
    @EJB
    private CfgUsuariosFacade usuarioFacade;
    @EJB
    private CfgClasificacionesFacade clasificacionFacade;
    @EJB
    private CfgPerfilesUsuarioFacade perfilusuarioFacade;
    @EJB
    CfgImagenesFacade imagenesFacade;
    
    private CfgUsuarios usuario;
    
    private List<CfgUsuarios> lstUsuarios;
    private List<CfgUsuarios> lstUsuariosFiltro;
    
    private List<SelectItem> listaTipoIdentificacion;
    private List<SelectItem> listaGenero;
    private List<SelectItem> listaDpto;
    private List<SelectItem> listaMunicipio;
    private List<SelectItem> listaTipoUsuario;
    private List<SelectItem> listaPerfil;
    private List<SelectItem> listaEspecialidad;
    private List<SelectItem> listaTipoPersonal;
    /**
     * Creates a new instance of UsuariosMB
     */
    public UsuariosMB() {
        
    }
    @PostConstruct
    public void inicializar(){
        lstUsuarios = new ArrayList<>();
        lstUsuariosFiltro = null;
        lstUsuarios = usuarioFacade.findAll();
        renderForm = false;
        usuario = new CfgUsuarios();
        renderTipoUsuario = false;
    }

    @Override
    public void nuevo() {
        usuario = new CfgUsuarios();
        usuario.setVisible(true);
        usuario.setMostrarEnHistorias(true);
        usuario.setEstadoCuenta(true);
        renderForm = true;
        renderTipoUsuario = false;
    }

    @Override
    public void guardar() {
        try {
            if(usuario.getPersonalAtiende().getId()==0)usuario.setPersonalAtiende(null);
            if(usuario.getEspecialidad().getId()==0)usuario.setEspecialidad(null);
            if(usuario.getDepartamento().getId()==0)usuario.setDepartamento(null);
            if(usuario.getMunicipio()!=null){
                if(usuario.getMunicipio().getId()==null)usuario.setMunicipio(null);
                else if(usuario.getMunicipio().getId()==0)usuario.setMunicipio(null);
            }
            if (archivoFirma != null) {//se cargo firma         
                String nombreImagenReal = archivoFirma.getFileName();
                String extension = nombreImagenReal.substring(nombreImagenReal.lastIndexOf("."), nombreImagenReal.length());
                CfgImagenes nuevaImagen = new CfgImagenes();
                imagenesFacade.create(nuevaImagen);//crearlo para que me autogenere el ID            
                String nombreImagenEnTmp = "firmaUsuario" + SessionUtil.getUserLog() + extension;
                Utilidades.moverArchivo(SessionUtil.getUrlPath() +"imagenesOpenmedical/"+ nombreImagenEnTmp, SessionUtil.getUrlImagen()+"Produccion/firmas/" + nuevaImagen.getId().toString() + extension,true);
                nuevaImagen.setNombre(nombreImagenReal);
                nuevaImagen.setNombreEnServidor(nuevaImagen.getId().toString() + extension);
                nuevaImagen.setUrlImagen("Produccion/firmas/" + nuevaImagen.getId().toString() + extension);
                imagenesFacade.edit(nuevaImagen);
                usuario.setFirma(nuevaImagen);
            }
            
            if (archivoFoto != null || fotoTomadaWebCam) {//se cargo foto
                String nombreImagenReal = null;
                String extension = null;
            if (fotoTomadaWebCam) {//es por webCam
                nombreImagenReal = "fotoWebCam.png";
                extension = ".png";
            } else {//es por carga de archivo
                nombreImagenReal = archivoFoto.getFileName();
                extension = nombreImagenReal.substring(nombreImagenReal.lastIndexOf("."), nombreImagenReal.length());
            }

            CfgImagenes nuevaImagen = new CfgImagenes();
            imagenesFacade.create(nuevaImagen);//crearlo para que me autogenere el ID            
            String nombreImagenEnTmp = "fotoUsuario" + SessionUtil.getUserLog() + extension;
            Utilidades.moverArchivo(SessionUtil.getUrlPath() +"imagenesOpenmedical/"+ nombreImagenEnTmp, SessionUtil.getUrlImagen()+"Produccion/fotos/" + nuevaImagen.getId().toString() + extension,true);
            nuevaImagen.setNombre(nombreImagenReal);
             nuevaImagen.setNombreEnServidor(nuevaImagen.getId().toString() + extension);
            nuevaImagen.setUrlImagen("Produccion/fotos/" + nuevaImagen.getId().toString() + extension);
            imagenesFacade.edit(nuevaImagen);
            usuario.setFoto(nuevaImagen);
        }
            
            if(usuario.getIdUsuario()==null){
                usuarioFacade.create(usuario);
            }else usuarioFacade.edit(usuario);
            inicializarVariables();
            SessionUtil.addInfoMessage("Guardado ", "Guardado Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            SessionUtil.addErrorMessage("Error al guardar", e.getLocalizedMessage());
        }
    }

    @Override
    public void inicializarVariables() {
        lstUsuarios = new ArrayList<>();
        lstUsuariosFiltro = null;
        lstUsuarios = usuarioFacade.findAll();
        renderForm = false;
        usuario = new CfgUsuarios();
        renderTipoUsuario = false;
        urlFoto = fotoPorDefecto;
        urlFirma = firmaPorDefecto;
    }

    @Override
    public void cancelar() {
        inicializarVariables();
    }

    @Override
    public void consultar(Object o) {
        usuario = (CfgUsuarios)o;
        //cargamos imagen a local
        if(usuario.getFirma()!=null){
            Utilidades.moverArchivo(SessionUtil.getUrlImagen()+usuario.getFirma().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+usuario.getFirma().getNombreEnServidor(),false);
            urlFirma = "../imagenesOpenmedical/"+usuario.getFirma().getNombreEnServidor();
        }
        if(usuario.getFoto()!=null){
            Utilidades.moverArchivo(SessionUtil.getUrlImagen()+usuario.getFoto().getUrlImagen(), SessionUtil.getUrlPath()+"imagenesOpenmedical/"+usuario.getFoto().getNombreEnServidor(),false);
            urlFoto = "../imagenesOpenmedical/"+usuario.getFoto().getNombreEnServidor();
        }
        if(usuario.getTipoIdentificacion()==null)usuario.setTipoIdentificacion(new CfgClasificaciones());
        if(usuario.getDepartamento()==null)usuario.setDepartamento(new CfgClasificaciones());
        if(usuario.getPersonalAtiende()==null)usuario.setPersonalAtiende(new CfgClasificaciones());
        cargarMunicipios();
        if(usuario.getMunicipio()==null)usuario.setMunicipio(new CfgClasificaciones());
        if(usuario.getEspecialidad()==null)usuario.setEspecialidad(new CfgClasificaciones());
        validarTipoUsuario();
        renderForm = true;
        
    }

    @Override
    public void eliminar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public CfgUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(CfgUsuarios usuario) {
        this.usuario = usuario;
    }

    public List<CfgUsuarios> getLstUsuarios() {
        return lstUsuarios;
    }

    public void setLstUsuarios(List<CfgUsuarios> lstUsuarios) {
        this.lstUsuarios = lstUsuarios;
    }

    public List<CfgUsuarios> getLstUsuariosFiltro() {
        return lstUsuariosFiltro;
    }

    public void setLstUsuariosFiltro(List<CfgUsuarios> lstUsuariosFiltro) {
        this.lstUsuariosFiltro = lstUsuariosFiltro;
    }

    public List<SelectItem> getListaTipoIdentificacion() {
        if(listaTipoIdentificacion==null){
            listaTipoIdentificacion  = new ArrayList<>();
            
          List<CfgClasificaciones> lstTi = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.TipoIdentificacion.toString());  
          for(CfgClasificaciones cfg:lstTi){
            listaTipoIdentificacion.add(new SelectItem(cfg.getId(),cfg.getObservacion()));
          }
        }
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public List<SelectItem> getListaGenero() {
        if(listaGenero==null){
            listaGenero = new ArrayList<>();
            List<CfgClasificaciones> lst = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.Genero.toString());
            for(CfgClasificaciones cfg:lst){
                listaGenero.add(new SelectItem(cfg.getId(),cfg.getDescripcion()));
            }
        }
        return listaGenero;
    }

    public void setListaGenero(List<SelectItem> listaGenero) {
        this.listaGenero = listaGenero;
    }

    public List<SelectItem> getListaDpto() {
        if(listaDpto==null){
            listaDpto = new ArrayList<>();
            listaDpto.add(new SelectItem(0,"Seleccione Departamento"));
            List<CfgClasificaciones> lstDpto = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.DPTO.toString());
            for(CfgClasificaciones d:lstDpto){
                listaDpto.add(new SelectItem(d.getId(),d.getDescripcion()));
            }
        }
        return listaDpto;
    }

    public void setListaDpto(List<SelectItem> listaDpto) {
        this.listaDpto = listaDpto;
    }

    public List<SelectItem> getListaMunicipio() {
        return listaMunicipio;
    }

    public void setListaMunicipio(List<SelectItem> listaMunicipio) {
        this.listaMunicipio = listaMunicipio;
    }

    public List<SelectItem> getListaTipoUsuario() {
        if(listaTipoUsuario==null){
            listaTipoUsuario = new ArrayList<>();
            List<CfgClasificaciones> lst = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.TipoUsuario.toString());
            for(CfgClasificaciones cfg:lst){
                listaTipoUsuario.add(new SelectItem(cfg.getId(),cfg.getDescripcion()));
            }
        }
        return listaTipoUsuario;
    }

    public void setListaTipoUsuario(List<SelectItem> listaTipoUsuario) {
        this.listaTipoUsuario = listaTipoUsuario;
    }

    public List<SelectItem> getListaPerfil() {
        if(listaPerfil==null){
            listaPerfil = new ArrayList<>();
            List<CfgPerfilesUsuario> lst = perfilusuarioFacade.findAll();
            for(CfgPerfilesUsuario cf:lst){
                listaPerfil.add(new SelectItem(cf.getIdPerfil(),cf.getNombrePerfil()));
            }
        }
        
        return listaPerfil;
    }

    public void setListaPerfil(List<SelectItem> listaPerfil) {
        this.listaPerfil = listaPerfil;
    }

    public UploadedFile getArchivoFirma() {
        return archivoFirma;
    }

    public void setArchivoFirma(UploadedFile archivoFirma) {
        this.archivoFirma = archivoFirma;
    }

    public UploadedFile getArchivoFoto() {
        return archivoFoto;
    }

    public void setArchivoFoto(UploadedFile archivoFoto) {
        this.archivoFoto = archivoFoto;
    }

    public String getUrlFirma() {
        return urlFirma;
    }

    public void setUrlFirma(String urlFirma) {
        this.urlFirma = urlFirma;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getFirmaPorDefecto() {
        return firmaPorDefecto;
    }

    public void setFirmaPorDefecto(String firmaPorDefecto) {
        this.firmaPorDefecto = firmaPorDefecto;
    }

    public String getFotoPorDefecto() {
        return fotoPorDefecto;
    }

    public void setFotoPorDefecto(String fotoPorDefecto) {
        this.fotoPorDefecto = fotoPorDefecto;
    }
    
    public void cargarMunicipios() {
        listaMunicipio = new ArrayList<>();
        if (usuario.getDepartamento().getId() != 0) {
            List<CfgClasificaciones> listaM = clasificacionFacade.buscarMunicipioPorDepartamento(clasificacionFacade.find(usuario.getDepartamento().getId()).getCodigo());
            listaMunicipio.clear();
            int i = 0;
            for (CfgClasificaciones mun : listaM) {
                if(i==0)usuario.setMunicipio(mun);
                listaMunicipio.add(new SelectItem(mun.getId(), mun.getDescripcion()));
                i++;
            }
            
        }
    }

    public boolean isRenderTipoUsuario() {
        return renderTipoUsuario;
    }

    public void setRenderTipoUsuario(boolean renderTipoUsuario) {
        this.renderTipoUsuario = renderTipoUsuario;
    }

    public List<SelectItem> getListaEspecialidad() {
        if(listaEspecialidad ==null){
            listaEspecialidad = new ArrayList<>();
            List<CfgClasificaciones> lst = clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.Especialidad.toString());
            for(CfgClasificaciones cfg:lst){
                listaEspecialidad.add(new SelectItem(cfg.getId(),cfg.getDescripcion()));
            }
            
        }
        return listaEspecialidad;
    }

    public void setListaEspecialidad(List<SelectItem> listaEspecialidad) {
        this.listaEspecialidad = listaEspecialidad;
    }

    public List<SelectItem> getListaTipoPersonal() {
        if(listaTipoPersonal == null){
            listaTipoPersonal = new ArrayList<>();
            List<CfgClasificaciones> lst  =clasificacionFacade.buscarPorMaestro(ClasificacionesEnum.PersonalAtiende.toString());
            for(CfgClasificaciones cfg:lst){
                listaTipoPersonal.add(new SelectItem(cfg.getId(),cfg.getDescripcion()));
            }
        }
        return listaTipoPersonal;
    }

    public void setListaTipoPersonal(List<SelectItem> listaTipoPersonal) {
        this.listaTipoPersonal = listaTipoPersonal;
    }
    
    public void validarTipoUsuario(){
        renderTipoUsuario = usuario.getTipoUsuario().getId() != null && usuario.getTipoUsuario().getId().equals(1774); //es prestador
    }

    public boolean isFotoTomadaWebCam() {
        return fotoTomadaWebCam;
    }

    public void setFotoTomadaWebCam(boolean fotoTomadaWebCam) {
        this.fotoTomadaWebCam = fotoTomadaWebCam;
    }
    
    public void uploadFirma(FileUploadEvent event) {
        try {
            archivoFirma = event.getFile();
            String nombreImg = "firmaUsuario" //es firma de usuario
                    + SessionUtil.getUserLog() //diferenciar el usuario actual
                    + archivoFirma.getFileName().substring(archivoFirma.getFileName().lastIndexOf("."), archivoFirma.getFileName().length());//colocar extension

            if (uploadArchivo(archivoFirma, SessionUtil.getUrlPath()+"imagenesOpenmedical/"+ nombreImg)) {
                urlFirma = "../imagenesOpenmedical/" + nombreImg;
            } else {
                urlFirma = firmaPorDefecto;
                archivoFirma = null;
            }
        } catch (Exception ex) {
            System.out.println("Error 20 en " + this.getClass().getName() + ":" + ex.toString());
        }
    }

    public void uploadFoto(FileUploadEvent event) {
        try {
            archivoFoto = event.getFile();
            String nombreImg = "fotoUsuario" //es foto de usuario
                    + SessionUtil.getUserLog() //diferenciar el usuario actual
                    + archivoFoto.getFileName().substring(archivoFoto.getFileName().lastIndexOf("."), archivoFoto.getFileName().length());//colocar extension
            if (uploadArchivo(archivoFoto, SessionUtil.getUrlPath()+"imagenesOpenmedical/"+ nombreImg)) {
                urlFoto = "../imagenesOpenmedical/" +  nombreImg;
                fotoTomadaWebCam = false;
            } else {
                urlFoto = fotoPorDefecto;
                archivoFoto = null;
                fotoTomadaWebCam = false;
            }
        } catch (Exception ex) {
            System.out.println("Error 20 en " + this.getClass().getName() + ":" + ex.toString());
        }
    }

    public void tomarFoto(CaptureEvent captureEvent) {
        byte[] data = captureEvent.getData();
        FileImageOutputStream imageOutput;
        archivoFoto = null;
        try {
            File imagen = new File(SessionUtil.getUrlPath()+"imagenesOpenmedical/"+ "fotoUsuario" + SessionUtil.getUserLog()+ ".png"/*loginMB.getUrltmp() + "fotoUsuario" + loginMB.getUsuarioActual().getIdUsuario() + ".png"*/);
            if (imagen.exists()) {
                imagen.delete();
                imagen = new File(SessionUtil.getUrlPath()+"imagenesOpenmedical/"+ "fotoUsuario" + SessionUtil.getUserLog()+ ".png");
            }
            imageOutput = new FileImageOutputStream(imagen);
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            fotoTomadaWebCam = true;
            urlFoto = "../imagenesOpenmedical/" +  "fotoUsuario" + SessionUtil.getUserLog() + ".png";
            RequestContext.getCurrentInstance().update("frmUsuario:opUsuario");
        } catch (IOException e) {
            urlFoto = fotoPorDefecto;
            fotoTomadaWebCam = false;
            System.out.println("Error 02: " + e.getMessage());//imprimirMensaje("Error 02", e.getMessage(), FacesMessage.SEVERITY_ERROR);            
        }
    }

    public boolean uploadArchivo(UploadedFile archivoCargado, String rutaDestino) {
        File fichero = new java.io.File(rutaDestino);
        if (fichero.exists()) {//si existe se borra
            fichero.delete();
        }
        fichero = new java.io.File(rutaDestino);
        try (FileOutputStream fileOutput = new FileOutputStream(fichero)) {
            InputStream inputStream = archivoCargado.getInputstream();
            byte[] buffer = new byte[1024];
            int bufferLength;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            
            
            inputStream.close();
            
        } catch (Exception e) {
            System.out.println("Error 01: " + e.getMessage());
            return false;
        }
        return true;
    }
}
