/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coderappco.xlab.business;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import coderappco.xlab.entidades.CfgClasificaciones;
import coderappco.xlab.facades.CfgClasificacionesFacade;
import javax.faces.model.SelectItem;

/**
 *
 * @author ArcoSoft-Pc1
 */
public abstract class Controlador {
    @EJB
    CfgClasificacionesFacade clasFacade;

    public abstract void nuevo();
    public abstract void guardar();
    public abstract void inicializarVariables();
    public abstract void cancelar();
    public abstract void consultar(Object o);
    public abstract void eliminar(Object o);
    public static Logger logger = Logger.getRootLogger();

    
    //Métodos Generales
    public void imprimirMensaje(String titulo, String mensaje, FacesMessage.Severity tipo) {
        if (tipo == FacesMessage.SEVERITY_INFO) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje));
        } else if (tipo == FacesMessage.SEVERITY_WARN) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensaje));
        } else if (tipo == FacesMessage.SEVERITY_ERROR) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje));
            //System.out.println("ERROR: ############### " + titulo + " ---- " + mensaje);
        } else if (tipo == FacesMessage.SEVERITY_FATAL) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, mensaje));
        }
    }
    public boolean fechaDentroDeRangoMas1Dia(Date inicioRango, Date finRango, Date fecBuscada) {
        //determinar si una fecha esta dentro de un rango; true = fecha en el rango
        //se suma 1 dia a la fecha final para que incluya el ultimo dia 
        Interval interval = new Interval(new DateTime(inicioRango), new DateTime(finRango).plusDays(1));
        return interval.contains(new DateTime(fecBuscada));
    }

    private boolean fechaDentroDeRango(Date inicioRango, Date finRango, Date fecBuscada) {
        //determinar si una fecha esta dentro de un rango; true = fecha en el rango
        //no es necesario aumentar el dia final por que se evaluan ambos limites de ambos rangos
        Interval interval = new Interval(new DateTime(inicioRango), new DateTime(finRango));
        return interval.contains(new DateTime(fecBuscada));
    }

    public boolean rangoDentroDeRango(Date inicioRango1, Date finRango1, Date inicioRango2, Date finRango2) {
        //determinar si un rango de fechas esta dentro de otro rango de fechas; true = se interceptan
        return fechaDentroDeRango(inicioRango1, finRango1, inicioRango2)
                || fechaDentroDeRango(inicioRango1, finRango1, finRango2)
                || fechaDentroDeRango(inicioRango2, finRango2, finRango1)
                || fechaDentroDeRango(inicioRango2, finRango2, finRango1);
    }

    public String calcularEdad(Date fechaNacimiento) {//calcular la edad a partir de la fecha de nacimiento    
        Period periodo = new Period(new DateTime(fechaNacimiento), new DateTime(new Date()));
        return String.valueOf(periodo.getYears()) + "A " + String.valueOf(periodo.getMonths()) + "M ";
    }

    public int calcularEdadInt(Date fechaNacimiento) {//calcular la edad en años
        Period periodo = new Period(new DateTime(fechaNacimiento), new DateTime(new Date()));
        if (periodo.getYears() == 0) {
            return 1;
        } else {
            return periodo.getYears();
        }
    }

    public String cerosIzquierda(int valor, int numCifras) {
        //aumenta ceros a la izquierda segun numCifas
        String v = String.valueOf(valor);
        int ceros = numCifras - v.length();
        if (v.length() < numCifras) {
            for (int i = 0; i < ceros; i++) {
                v = "0" + v;
            }
        }
        return v;
    }

    public static String getErrorExcepcion(Throwable e, int profundidad) {
        /*ESTE METODO PERMITE SACAR EN PROFUNDIDAD UN EXCEPCION CAPTURADA DESDE
         OTRAS CLASES O CLASES LOCALES*/
        int profundidadVo = profundidad;
        try {
            if (e != null) {
                if (e.getCause() != null && profundidadVo < 6) {
                    profundidadVo++;
                    return getErrorExcepcion(e.getCause(), profundidadVo);
                } else {
                    String error = e.toString() == null ? null : e.toString();
                    if (error != null) {
                        if (error.length() > 300) {
                            error = error.substring(0, 299);
                        }
                    }
                    return error;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            return "No fue posible obtener el error en la excepción debido a: " + ex.getMessage();
        }
    }

    public boolean esNumero(String valor) {
        //validar si una cadena es numerica
        try {
            Integer.parseInt(valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validarNoVacio(String valor) {
        //validas si una cadena no es vacia //true= no vacio //false=vacio
        if (valor == null) {
            return false;
        }
        return valor.trim().length() != 0;
    }

    public boolean validacionCampoVacio(String valor, String nombre) {
        /*false = lleno, true = vacio pero ademas imprime el mensaje de error
         */
        if (valor == null || valor.trim().length() == 0) {
            imprimirMensaje("Error", "El campo " + nombre + " es obligatorio", FacesMessage.SEVERITY_ERROR);
            return true;
        }
        return false;
    }

    public boolean validacionFechaVacia(Date f, String nombre) {
        /*false = lleno, true = vacio pero ademas imprime el mensaje de error
         */
        if (f == null) {
            imprimirMensaje("Error", "El campo " + nombre + " es obligatorio", FacesMessage.SEVERITY_ERROR);
            return true;
        }
        return false;
    }

    public List<SelectItem> cargarClasificacion(String maestro) {
        List<SelectItem> listaRetorno = new ArrayList<>();
        List<CfgClasificaciones> listaClasificaciones = clasFacade.buscarPorMaestro(maestro);
        for (CfgClasificaciones clasificacion : listaClasificaciones) {
            listaRetorno.add(new SelectItem(clasificacion.getId(), clasificacion.getDescripcion()));
        }
        return listaRetorno;
    }
}
