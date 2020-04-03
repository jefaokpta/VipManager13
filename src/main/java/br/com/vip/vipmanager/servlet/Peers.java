/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vip.vipmanager.servlet;

import br.com.vip.vipmanager.event.PeerStatusCenterControl;
import br.com.vip.vipmanager.event.PeerStatusControl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@WebServlet(name = "Peers", urlPatterns = {"/Peers"})
public class Peers extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        PeerStatusCenterControl pscc=new PeerStatusCenterControl();
        JSONArray peers=new JSONArray();
        JSONObject peer;
        for (Map.Entry<String, PeerStatusControl> entry : pscc.getProtectedPeerStatusController().entrySet()) {
            PeerStatusControl psc=entry.getValue();
            
            peer=new JSONObject();
            peer.put("peer", psc.getPeer());
            peer.put("channel", (psc.getChannel()==null?"null":psc.getChannel()));
            peer.put("channelAux", (psc.getChannelAux()==null?"null":psc.getChannelAux()));
            peer.put("registerStatus", psc.getRegisterStatus());
            peer.put("state", psc.getState());
            peer.put("stateDesc", psc.getStateDesc());
            peer.put("address", (psc.getAddress()==null?"null":psc.getAddress()));
            peer.put("exten",(psc.getExten()==null?"null":psc.getExten()));
            peer.put("uniqueid", (psc.getUniqueid()==null?"null":psc.getUniqueid()));
            peer.put("lastCall", (psc.getLastCall()==null?"null":psc.getLastCall()));
            peer.put("direction", psc.getDirection());
            peer.put("dialStatus", (psc.getDialStatus()==null?"null":psc.getDialStatus()));
            
            peers.put(peer);
        }
        out.print(peers);
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
