/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.utilidades;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author miguel
 */
public class SessionUtil {
        // Se crean las variables de sesion.
    public static void addSession(int userId, String userNombre, int empresaId,int perfilId) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);

        sesion.setAttribute("userId", userId);
        sesion.setAttribute("userNombre", userNombre);
        sesion.setAttribute("empresa", empresaId);
        sesion.setAttribute("perfil", perfilId);
        ServletContext ctx = (ServletContext) context.getExternalContext().getContext();
        sesion.setAttribute("urlProject", ctx.getRealPath("/"));
    }
    public static void setUrlImage(String url){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
        sesion.setAttribute("urlImage", url);
    }
    
    public static void setConsecutivoAutomatico(String valor){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
        sesion.setAttribute("consecutivoAutomatico", valor);
    }
    public static void setTipoDocumentoOrden(int valor){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
        sesion.setAttribute("tipoDocumentoOrden", valor);
    }

    // Se cierra la sesion.
    public static void closeSession() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) ctx.getSession(false)).invalidate();
    }

    // Recupera el código del usuario logueado.
    public static Long getUserLog() {
        try{
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        if(sesion.getAttribute("userId")!=null){
            Long userLog = Long.valueOf(sesion.getAttribute("userId").toString());
            return userLog;
        }else{
            return 0l;
        }
        }catch(Exception e){
            
        }
        return 0l;
    }
    public static int getPerfil() {
        try{
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        if(sesion.getAttribute("perfil")!=null){
            int userLog = Integer.parseInt(sesion.getAttribute("perfil").toString());
            return userLog;
        }else{
            return 0;
        }
        }catch(Exception e){
            
        }
        return 0;
    }

    // Recupera el nombre del usuario logueado.
    public static String getUserNombreLog() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String nombre = (String)sesion.getAttribute("userNombre");
        return nombre;
    }
    public static String getUrlImagen() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String nombre = (String)sesion.getAttribute("urlImage");
        return nombre;
    }
    public static int getTipoDocumentoOrden() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        int nombre = Integer.parseInt(sesion.getAttribute("tipoDocumentoOrden").toString());
        return nombre;
    }
    public static String getConsecutivoAutomatico(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String nombre = (String)sesion.getAttribute("consecutivoAutomatico");
        return nombre;
    }
    public static String getUrlPath(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String nombre = (String)sesion.getAttribute("urlProject");
        return nombre;
    }
    public static int getEmpresa() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        int nombre = Integer.parseInt(sesion.getAttribute("empresa")!=null?sesion.getAttribute("empresa").toString():"0");
        return nombre;
    }
    public static String getClaveUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String nombre = (String)sesion.getAttribute("claveUsuario");
        return nombre;
    }
    public static String getUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String nombre = (String)sesion.getAttribute("usuario");
        return nombre;
    }
    public static String getNitEmpresa() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
        String nombre = (String)sesion.getAttribute("nitEmpresa");
        return nombre;
    }

    // Nombre de la página actual que se está visitando
    public static String getPagina() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        return ctx.getRequestPathInfo();
    }

    // Método para redirigir a página del Sitio.
    public static void redirectTo(String url,boolean flash) {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
      
        if(flash)ctx.getFlash().setKeepMessages(true);
      
        try { ctx.redirect(ctxPath + url); }
        catch (IOException ex) { ex.printStackTrace();  }
    }

    public static void addErrorMessage(String titulo,String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    
    public static void addInfoMessage(String titulo,String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    public static void addWarningMessage(String titulo,String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
}
