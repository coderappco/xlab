/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.beans.configuraciones;

import coderappco.xlab.business.Controlador;
import coderappco.xlab.entidades.CfgConfiguraciones;
import coderappco.xlab.entidades.CfgCopiasSeguridad;
import coderappco.xlab.facades.CfgConfiguracionesFacade;
import coderappco.xlab.facades.CfgCopiasSeguridadFacade;
import coderappco.xlab.utilidades.SessionUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "copiaSeguridadMB")
@SessionScoped
public class CopiaSeguridadMB extends Controlador implements Serializable {

    @EJB
    CfgConfiguracionesFacade configuracionesFacade;
    @EJB
    CfgCopiasSeguridadFacade copiasSeguridadFacade;

    private List<CfgCopiasSeguridad> listaCopiasSeguridad;
    private List<CfgCopiasSeguridad> listaCopiasSeguridadFiltro;
    private CfgCopiasSeguridad copiaSeguridadSeleccionada;
    private CfgConfiguraciones configuracionActual;
    private StreamedContent fileBackup;
    private String nombreCopiaDescargar = "";
    private boolean renderForm;
    private boolean renderRestaurar;
    private boolean renderGuardar;
    public CopiaSeguridadMB() {
    }
    @PostConstruct
    public void inicializar(){
        configuracionActual = configuracionesFacade.findAll().get(0);
        eliminarCopiasNoEncontradas();
        listaCopiasSeguridad = copiasSeguridadFacade.buscarOrdenado();
        copiaSeguridadSeleccionada = new CfgCopiasSeguridad();
        renderForm = false;
        listaCopiasSeguridadFiltro = null;
        renderRestaurar = false;
        renderGuardar =false;
    }

    @Override
    public void nuevo() {
        inicializar();
        copiaSeguridadSeleccionada.setFecha(new Date());
        copiaSeguridadSeleccionada.setTipo("MANUAL");
        renderForm  =true;
        renderRestaurar = false;
        renderGuardar =true;
    }

    @Override
    public void guardar() {
        boolean continueProcess;
        ResultSet rs;
        if (copiaSeguridadSeleccionada.getNombre() != null && copiaSeguridadSeleccionada.getNombre().trim().length() != 0) {//determinar si se ingreso nombre
            continueProcess = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se debe escribir un nombre para la copia de seguridad"));
            continueProcess = false;
        }

        if (continueProcess) {//determinar si el nombre ya esta ingresado                
            copiaSeguridadSeleccionada.setNombre(copiaSeguridadSeleccionada.getNombre()+".backup");
            if (copiasSeguridadFacade.buscarPorNombre(copiaSeguridadSeleccionada.getNombre()) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya existe una copia de seguridad con el nombre ingresado"));
                continueProcess = false;
            }
        }
        if (continueProcess) {//almaceno la informacion de la copia de seguridad a crear
            try {
                if (new java.io.File(configuracionActual.getRutaCopiasSeguridad()).exists()) {//verificar que el directorio exista                    
                    copiaSeguridadSeleccionada.setRuta(configuracionActual.getRutaCopiasSeguridad()+copiaSeguridadSeleccionada.getNombre());;
                    copiasSeguridadFacade.create(copiaSeguridadSeleccionada);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Directorio '" + configuracionActual.getRutaCopiasSeguridad() + "' no existe en el servidor"));
                    continueProcess = false;
                }
            } catch (Exception x) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", x.getMessage()));
            }
        }
        if (continueProcess) {//genero los archivos de copia de seguridad
            try {
                Process p;
                ProcessBuilder pb;
                String backupFilePath = configuracionActual.getRutaCopiasSeguridad() + copiaSeguridadSeleccionada.getNombre();
                File fiRcherofile = new java.io.File(backupFilePath);//si archivo od existe Lo eliminamos 
                if (fiRcherofile.exists()) {
                    fiRcherofile.delete();
                }
                //ejecutamos proceso de copia de seguridad
                pb = new ProcessBuilder(configuracionActual.getRutaBinPostgres() + "pg_dump", "-i", "-h", configuracionActual.getServidor(), "-p", "5432", "-U", configuracionActual.getUsuario(), "-F", "c", "-b", "-v", "-f", backupFilePath, configuracionActual.getNombreDb());
                pb.environment().put("PGPASSWORD", configuracionActual.getClave());
                pb.redirectErrorStream(true);
                p = pb.start();
                //imprimirSalidaDeProceso(p, " crear copia de seguridad: " + backupFilePath + "_od.backup");
                listaCopiasSeguridad = copiasSeguridadFacade.buscarOrdenado();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "La copia de seguridad ha sido creada correctamente"));
                inicializarVariables();
            } catch (IOException x) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", x.getMessage()));
            }
        }
    }

    @Override
    public void inicializarVariables() {
        listaCopiasSeguridad = copiasSeguridadFacade.buscarOrdenado();
        copiaSeguridadSeleccionada = null;
        renderForm = false;
        listaCopiasSeguridadFiltro = null;
        renderRestaurar = false;
        renderGuardar =false;
    }

    @Override
    public void cancelar() {
        inicializarVariables();
    }

    @Override
    public void consultar(Object o) {
        copiaSeguridadSeleccionada = (CfgCopiasSeguridad)o;
        renderForm = true;
        renderRestaurar = true;
        renderGuardar = false;
    }

    @Override
    public void eliminar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public StreamedContent getFileBackup() {//DESCARGA DE ARCHIVO DE COPIA DE SEGURIDAD
        InputStream input;
        try {
            File file = new File(copiaSeguridadSeleccionada.getRuta());
            input = new FileInputStream(file);
            fileBackup = new DefaultStreamedContent(input, "application/binary", file.getName());
            return fileBackup;
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR 001: " + ex.toString());
        }
        return null;
    }

    
    public void clickBtnRestaurarCopia() {
        if (copiaSeguridadSeleccionada == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se debe seleccionar una copia de seguridad para realizar la restauración"));
            return;
        }
        RequestContext.getCurrentInstance().execute("PF('dialogEdit').show();");
    }

    public void restaurarCopiaDeSeguridad() {
        /*
         * click sobre restaurar una copia de seguridad de od(sigeodep)
         */
        boolean continueProcess;

        if (copiaSeguridadSeleccionada != null) {
            continueProcess = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se debe seleccionar una copia de seguridad para realizar la restauración"));
            continueProcess = false;
        }
        if (continueProcess) {//valido que el archivo exista        
            if (!new java.io.File(copiaSeguridadSeleccionada.getRuta()).exists()) {//Probamos a ver si existe ese ultimo dato                    
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontro el archivo: " + copiaSeguridadSeleccionada.getRuta() + " en el servidor"));
                continueProcess = false;
            }
        }
        if (continueProcess) {//realizo la restauracion de la copia de seguridad            
            try {
                Process p;
                ProcessBuilder pb;
                pb = new ProcessBuilder(configuracionActual.getRutaBinPostgres() + "pg_restore", "-i", "-h", configuracionActual.getServidor(), "-p", "5432", "-U", configuracionActual.getUsuario(), "-d", configuracionActual.getNombreDb(), "-v", "-F", "c", "-c", copiaSeguridadSeleccionada.getRuta());
                pb.environment().put("PGPASSWORD", configuracionActual.getClave());
                pb.redirectErrorStream(true);
                p = pb.start();
                //imprimirSalidaDeProceso(p, " restauracion copia seguridad: " + copiaSeguridadSeleccionada.getRuta() + copiaSeguridadSeleccionada.getNombre());
            } catch (IOException x) {
                System.err.println("Caught: " + x.getMessage());
                continueProcess = false;
            }
        }
        if (continueProcess) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "La copia de seguridad se ha sido restaurada, por favor cierre la sesión."));
            try {//me redirijo a la pagina inicial
                SessionUtil.closeSession();
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
                ((HttpSession) ctx.getSession(false)).invalidate();
                //ctx.redirect(ctxPath + "/index.html");
            } catch (Exception ex) {
                System.out.println("Excepcion cuando se cierra sesions por restauración de copia de seguridad" + ex.toString());
            }
        }
    }

    public void clickBtnEliminarCopia() {
        if (copiaSeguridadSeleccionada == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se debe seleccionar una copia de seguridad para realizar la eliminación."));
            return;
        }
        RequestContext.getCurrentInstance().execute("PF('dialogDelete').show();");
    }

    public void eliminarCopiaDeSeguridad() {
        /*
         * click sobre eliminar un backup de od(sigeodep)
         */
        File backupFile;
        if (copiaSeguridadSeleccionada != null) {
            backupFile = new java.io.File(copiaSeguridadSeleccionada.getRuta());
            if (backupFile.exists()) {
                backupFile.delete();//elimino el archivo
            }
            copiasSeguridadFacade.remove(copiasSeguridadFacade.buscarPorNombre(copiaSeguridadSeleccionada.getNombre()));
            listaCopiasSeguridad = copiasSeguridadFacade.buscarOrdenado();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "La copia de seguridad se ha eliminado correctamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Se debe seleccionar una copia de seguridad para realizar la eliminación"));
        }
    }

    public List<CfgCopiasSeguridad> getListaCopiasSeguridad() {
        return listaCopiasSeguridad;
    }

    public void setListaCopiasSeguridad(List<CfgCopiasSeguridad> listaCopiasSeguridad) {
        this.listaCopiasSeguridad = listaCopiasSeguridad;
    }

    public CfgCopiasSeguridad getCopiaSeguridadSeleccionada() {
        return copiaSeguridadSeleccionada;
    }

    public void setCopiaSeguridadSeleccionada(CfgCopiasSeguridad copiaSeguridadSeleccionada) {
        this.copiaSeguridadSeleccionada = copiaSeguridadSeleccionada;
    }

    public CfgConfiguraciones getConfiguracionActual() {
        return configuracionActual;
    }

    public void setConfiguracionActual(CfgConfiguraciones configuracionActual) {
        this.configuracionActual = configuracionActual;
    }

    
    public void setFileBackup(StreamedContent fileBackup) {
        this.fileBackup = fileBackup;
    }

    public String getNombreCopiaDescargar() {
        return nombreCopiaDescargar;
    }

    public void setNombreCopiaDescargar(String nombreCopiaDescargar) {
        this.nombreCopiaDescargar = nombreCopiaDescargar;
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public List<CfgCopiasSeguridad> getListaCopiasSeguridadFiltro() {
        return listaCopiasSeguridadFiltro;
    }

    public void setListaCopiasSeguridadFiltro(List<CfgCopiasSeguridad> listaCopiasSeguridadFiltro) {
        this.listaCopiasSeguridadFiltro = listaCopiasSeguridadFiltro;
    }

    public boolean isRenderRestaurar() {
        return renderRestaurar;
    }

    public void setRenderRestaurar(boolean renderRestaurar) {
        this.renderRestaurar = renderRestaurar;
    }

    public boolean isRenderGuardar() {
        return renderGuardar;
    }

    public void setRenderGuardar(boolean renderGuardar) {
        this.renderGuardar = renderGuardar;
    }
    public void eliminarCopiasNoEncontradas() {// elimina copias que no tengan archivo almacenado en la carpeta del servidor
        ArrayList<CfgCopiasSeguridad> listaCopiasAEliminar = new ArrayList<>();
        listaCopiasSeguridad = copiasSeguridadFacade.buscarOrdenado();
        for (CfgCopiasSeguridad copia : listaCopiasSeguridad) {
            File archivoDeBackup = new File(copia.getRuta());
            if (!archivoDeBackup.exists()) {//si no existe se elimina de tabla backups
                listaCopiasAEliminar.add(copia);
            }
        }
        for (CfgCopiasSeguridad copia : listaCopiasAEliminar) {
            copiasSeguridadFacade.remove(copia);
        }
    }
    
}
