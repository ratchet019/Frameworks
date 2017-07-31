/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.reporte;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/**
 *
 * @author User
 */
@WebServlet(name = "GenerarReporte1", urlPatterns = {"/GenerarReporte1"})
public class GenerarReporte1 extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        try {
          String tipo=request.getParameter("selTipo");
     //Devuelve un objeto de la clase ServletOutputStream que modela un flujo de salida que permite la escritura de datos a nivel de bytes

        ServletOutputStream out = response.getOutputStream();
            try {
                     //Obtenemos la conexion del pool de conexiones
                     Context init = new InitialContext();
                     Context context = (Context) init.lookup("java:comp/env");
                        DataSource dataSource =(DataSource)context.lookup("jdbc/mysql");
                    Connection conexion = dataSource.getConnection();
                    //Nombre del archivo .jasper
                    JasperReport reporte = (JasperReport)
                    JRLoader.loadObjectFromFile(getServletContext().getRealPath("/Reporte_Grafica.jasper"));
                    //Cargamos parametros del reporte (si tiene).
                    Map parameters = new HashMap();
                    parameters.put("parametro", "parametro");
                    //Enviamos la ruta del reporte los parametros y la conexion
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parameters, conexion);
                    JRExporter exporter = null;
                    if("pdf".equalsIgnoreCase(tipo)){
                    //Tipo de contenido a regresar al cliente
                             response.setContentType("application/pdf");
                        //Nombre del reporte
                        response.setHeader("Content-Disposition","attachment; filename=\"Reporte.pdf\";");
                //Exportar a pdf
                        exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                    }
                        else if("html".equalsIgnoreCase(tipo)){
                        response.setContentType("text/html;charset=UTF-8");
                 //response.setHeader("Content-Disposition","attachment; filename=\"Reporte.html\";");
                    //Exportar a html
                         exporter = new JRHtmlExporter();
                         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
                        getServletContext().getContextPath() + "/jasperImage?image=");

                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);

                        request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
                        }

                            conexion.close();
                            exporter.exportReport();
                        }
                            catch (Exception e)
                        {
                            e.printStackTrace();
                        }  
                            finally {
                                     out.close();
                            }
             
        } finally {            
          
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
