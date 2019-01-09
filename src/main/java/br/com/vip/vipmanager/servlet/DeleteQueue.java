/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vip.vipmanager.servlet;

import br.com.vip.vipmanager.event.QueueStatusCenterControl;
import br.com.vip.vipmanager.event.QueueStatusControl;
import br.com.vip.vipmanager.event.QueueSummaryCenterControl;
import br.com.vip.vipmanager.event.QueueSummaryControl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@WebServlet(name = "DeleteQueue", urlPatterns = {"/DeleteQueue"})
public class DeleteQueue extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Map<String,QueueSummaryControl> qscc=new QueueSummaryCenterControl().getQueueSummary();
        String name=request.getParameter("company")+"&"+request.getParameter("queue");
        out.print(qscc.remove(name)+"<br />");
        Map<String,QueueStatusControl> qstcc=new QueueStatusCenterControl().getQstcc();
        out.print(qstcc.remove(name)+"<br />");
        
        out.print("APAGANDO FILA "+name);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
