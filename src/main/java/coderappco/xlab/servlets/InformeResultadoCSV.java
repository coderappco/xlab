/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coderappco.xlab.servlets;

import coderappco.xlab.utilidades.DBConnector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 *
 * @author ArcoSoft-Pc1
 */
public class InformeResultadoCSV extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnector.getInstance().getConnection();
            String query  ="select distinct	xo.nro_orden as nroOrden, " +
                                                        "	xo.fecha_orden as fechaOrden, " +
                                                        "	cp.identificacion, " +
                                                        "	cp.primer_apellido as primerApellido, " +
                                                        "	cp.segundo_Apellido as segundoApellido, " +
                                                        "	cp.primer_nombre as primerNombre, " +
                                                        "	cp.segundo_nombre as segundoNombre, " +
                                                        "	cp.fecha_nacimiento as fechaNacimiento, " +
                                                        "	genero.observacion as genero, " +
                                                        "	cp.telefono_residencia as telefono, " +
                                                        "	estudio.nombre as estudio, " +
                                                        "	xp.resultado, " +
                                                        "	unidad.codigo as unidad, " +
                                                        "	xp.fecha_actualizacion as fechaPrueba, " +
                                                        "	fa.razon_social as entidad " +
                                                        "from xlab_orden xo " +
                                                        "inner join xlab_orden_estudios xs on xs.orden_id = xo.id " +
                                                        "inner join xlab_orden_estudios_pruebas xp on xp.estudio_id = xs.estudio_id and xp.orden_id=xo.id " +
                                                        "inner join xlab_prueba prueba on prueba.id = xp.prueba_id " +
                                                        "inner join xlab_estudio estudio on estudio.id = xs.estudio_id " +
                                                        "inner join cfg_pacientes cp on cp.id_paciente = xo.paciente_id " +
                                                        "inner join cfg_clasificaciones genero on genero.id = cp.genero " +
                                                        "inner join cfg_unidad unidad on unidad.id = prueba.unidad_prueba " +
                                                        "inner join fac_administradora fa on fa.id_administradora = cp.id_administradora";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            String nombreArchivo = "InformeResultado.xls";
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String url = getServletContext().getRealPath("/");
            String rutaArchivo = url + "/informes/reportes/";
            File archivo1 = new File(rutaArchivo);
            if (archivo1.exists()) {
                archivo1.delete();
            }
            archivo1.createNewFile();
            HSSFWorkbook libro = new HSSFWorkbook();

            HSSFCellStyle cellStyle = libro.createCellStyle();
            HSSFFont font = libro.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 10);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            font.setCharSet(HSSFFont.ANSI_CHARSET);
            cellStyle.setFont(font);
            /*Se inicializa el flujo de datos con el archivo xls*/
            FileOutputStream archi = new FileOutputStream(rutaArchivo);
            Sheet hoja;
            Cell celda;
            Row fila ;
            int i = 0;
            hoja = libro.createSheet("Informe de Resultados");
                    fila = hoja.createRow(i);
                    celda = fila.createCell(0);
                    celda.setCellValue("NRO ORDEN");
                    celda.setCellStyle(cellStyle);

                    celda = fila.createCell(1);
                    celda.setCellValue("FECHA ORDEN");
                    celda.setCellStyle(cellStyle);

                    celda = fila.createCell(2);
                    celda.setCellValue("DOCUMENTO");
                    celda.setCellStyle(cellStyle);

                    celda = fila.createCell(3);
                    celda.setCellValue("PRIMER APELLIDO");
                    celda.setCellStyle(cellStyle);

                    celda = fila.createCell(4);
                    celda.setCellValue("SEGUNDO APELLIDO");
                    celda.setCellStyle(cellStyle);

                    celda = fila.createCell(5);
                    celda.setCellValue("PRIMER NOMBRE");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(6);
                    celda.setCellValue("SEGUNDO NOMBRE");
                    celda.setCellStyle(cellStyle);

                    celda = fila.createCell(7);
                    celda.setCellValue("FECHA NACIMIENTO");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(8);
                    celda.setCellValue("SEXO");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(9);
                    celda.setCellValue("TELEFONO");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(10);
                    celda.setCellValue("EDAD");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(11);
                    celda.setCellValue("ESTUDIO");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(12);
                    celda.setCellValue("RESULTADO");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(13);
                    celda.setCellValue("UNIDAD");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(14);
                    celda.setCellValue("FECHA PRUEBA");
                    celda.setCellStyle(cellStyle);
                    
                    celda = fila.createCell(15);
                    celda.setCellValue("ENTIDAD");
                    celda.setCellStyle(cellStyle);
                    i = i + 1;
                    for (int j = 0; j <= 15; j++) {
                        hoja.autoSizeColumn(j);
                    }
                    
                    while (rs.next()) {
                        fila = hoja.createRow(i);
                        celda = fila.createCell(0);
                        celda.setCellValue(rs.getString(1));
                        
                        celda = fila.createCell(1);
                        celda.setCellValue(formato.format(rs.getDate(2)));
                        
                        celda = fila.createCell(2);
                        celda.setCellValue(rs.getString(3));
                        
                        celda = fila.createCell(3);
                        celda.setCellValue(rs.getString(4));
                        
                        celda = fila.createCell(4);
                        celda.setCellValue(rs.getString(5));
                        
                        celda = fila.createCell(5);
                        celda.setCellValue(rs.getString(6));
                        
                        celda = fila.createCell(6);
                        celda.setCellValue(rs.getString(7));
                        
                        celda = fila.createCell(7);
                        celda.setCellValue(formato.format(rs.getDate(8)));
                        
                        celda = fila.createCell(8);
                        celda.setCellValue(rs.getString(9));
                        
                        celda = fila.createCell(9);
                        celda.setCellValue(rs.getString(10));
                        
                        celda = fila.createCell(10);
                        Period periodo = new Period(new DateTime(rs.getDate(8)), new DateTime(new Date()));
                        String edad= String.valueOf(periodo.getYears()) + "A " + String.valueOf(periodo.getMonths()) + "M ";
                        celda.setCellValue(edad);
                        
                        celda = fila.createCell(11);
                        celda.setCellValue(rs.getString(11));
                        
                        celda = fila.createCell(12);
                        celda.setCellValue(rs.getString(12));
                        
                        celda = fila.createCell(13);
                        celda.setCellValue(rs.getString(13));
                        
                        celda = fila.createCell(14);
                        celda.setCellValue(formato.format(rs.getDate(14)));
                        
                        celda = fila.createCell(15);
                        celda.setCellValue(rs.getString(15));
                        ////celda.getStringCellValue().getBytes(Charset.forName("UTF-8"));
                        i = i + 1;
                    }

                    /*Escribimos en el libro*/
            libro.write(archi);
            /*Cerramos el flujo de datos*/
            archi.close();
            /*DESCARGAMOS EL ARCHIVO */
            File f;
            f = new File(rutaArchivo);
            int bit;
            InputStream in;
            ServletOutputStream out;
            response.setContentType("application/vnd.ms-excel"); //Tipo de fichero.
            response.setHeader("Content-Disposition", "attachment;filename=\"" + nombreArchivo + "\""); //Configurar cabecera http

            in = new FileInputStream(f);
            out = response.getOutputStream();

            bit = 256;
            while ((bit) >= 0) {
                bit = in.read();
                out.write(bit);
            }

            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
                DBConnector.getInstance().closeConnection();
            } catch (Exception e) {
            }
        }

        
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
