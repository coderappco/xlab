/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.facades;

import coderappco.xlab.entidades.CfgOpcionesMenu;
import javax.ejb.Stateless;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Query;
/**
 *
 * @author ArcoSoft-Pc1
 */
@Stateless
public class CfgOpcionesMenuFacade extends AbstractFacade<CfgOpcionesMenu> {
    public CfgOpcionesMenuFacade() {
        super(CfgOpcionesMenu.class);
    }
    
    public List<CfgOpcionesMenu> getMenu(int perfilUsuario){
        List<CfgOpcionesMenu> lista =new ArrayList<>();
        try {
            Query query = getEntityManager().createNativeQuery("select padres.id_opcion_menu as id, " +
                                    "padres.nombre_opcion as modulo, " +
                                    "padres.style as icon, " +
                                    " hijos.* " +
                                    "from cfg_opciones_menu padres " +
                                    "inner join cfg_opciones_menu hijos on hijos.id_opcion_padre = padres.id_opcion_menu " +
                                    "inner join cfg_opciones_menu_perfil_usuario op on op.id_opcion_menu =hijos.id_opcion_menu " +
                                    "where padres.id_opcion_padre=0 " +
                                    "and padres.nombre_opcion!='raiz' " +
                                    "and op.id_perfil_usuario=? " +
                                    "union " +
                                    "select 0 as id, " +
                                    "'' as modulo, " +
                                    "'' as icon, " +
                                    "padres.* " +
                                    "from cfg_opciones_menu padres " +
                                    "inner join cfg_opciones_menu_perfil_usuario op on op.id_opcion_menu =padres.id_opcion_menu " +
                                    "where padres.id_opcion_padre=0 " +
                                    "and padres.url_opcion!='' " +
                                    "and padres.nombre_opcion!='raiz' " +
                                    "and op.id_perfil_usuario=? " +
                                    "order by 1");
            query.setParameter(1, perfilUsuario);
            query.setParameter(2, perfilUsuario);
            List<Object[]> lst = query.getResultList();
            for(Object[] om: lst){
                CfgOpcionesMenu co = new CfgOpcionesMenu();
                co.setIdOpcionMenu(Integer.parseInt(om[3].toString()));
                co.setNombreOpcion(om[5].toString());
                co.setUrlOpcion(om[7]!=null?om[7].toString():"");
                if(co.getUrlOpcion().equals("")){
                    Query query2 = getEntityManager().createNativeQuery("select hijos.* " +
                                                                    "from cfg_opciones_menu padres " +
                                                                    "inner join cfg_opciones_menu hijos on hijos.id_opcion_padre = padres.id_opcion_menu " +
                                                                    "inner join cfg_opciones_menu_perfil_usuario op on op.id_opcion_menu =hijos.id_opcion_menu " +
                                                                    "where hijos.id_opcion_padre=? " +
                                                                    "and padres.nombre_opcion!='raiz' " +
                                                                    "and op.id_perfil_usuario=?");
                    
                    
                    query2.setParameter(1, co.getIdOpcionMenu());
                    query2.setParameter(2, perfilUsuario);
                    List<Object[]> lst2 = query2.getResultList();
                    List<CfgOpcionesMenu> lstMenu = new ArrayList<>();
                    for(Object[] om2: lst2){
                        CfgOpcionesMenu sub = new CfgOpcionesMenu();
                        sub.setIdOpcionMenu(Integer.parseInt(om2[0].toString()));
                       sub.setNombreOpcion(om2[2].toString());
                        sub.setUrlOpcion(om2[4].toString());
                        lstMenu.add(sub);
                    }
                    co.setCfgOpcionesMenuList(lstMenu);
                }
                
                CfgOpcionesMenu padre = new CfgOpcionesMenu();
                padre.setIdOpcionMenu(Integer.parseInt(om[0].toString()));
                padre.setStyle(om[2].toString());
                padre.setNombreOpcion(om[1].toString());
                co.setIdOpcionPadre(padre);
                lista.add(co);
            }
        } catch (Exception e) {
            logger.error("Error en la clase "+ CfgOpcionesMenuFacade.class.getName() + ", mensaje: " + e.getMessage(),e);
        }
        return lista;
    }
    
}
