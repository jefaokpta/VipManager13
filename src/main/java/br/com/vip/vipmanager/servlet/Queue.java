/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vip.vipmanager.servlet;

import br.com.vip.vipmanager.event.PeerStatusCenterControl;
import br.com.vip.vipmanager.event.PeerStatusControl;
import br.com.vip.vipmanager.event.QueueAgent;
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
import org.asteriskjava.manager.event.QueueEntryEvent;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@WebServlet(name = "Queue", urlPatterns = {"/Queue"})
public class Queue extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        Map<String,QueueSummaryControl> qscc=new QueueSummaryCenterControl().getQueueSummary();
        Map<String,QueueStatusControl> qstcc=new QueueStatusCenterControl().getQstcc();
        
        JSONObject queue;
        JSONObject agent;
        JSONObject peer;
        JSONArray agents;
        JSONObject call;
        JSONArray calls;
        if(request.getParameter("company")==null||request.getParameter("queue")==null){
            out.print("FALTANDO PARAMETROS");
            return;
        }
        String queueDescription=request.getParameter("company")+"&"+request.getParameter("queue");
        
        for (Map.Entry<String, QueueSummaryControl> entry : qscc.entrySet()) {
            QueueSummaryControl qsc = entry.getValue();
            QueueStatusControl qstc=qstcc.get(qsc.getQueue());
            if(!qsc.getQueue().equals(queueDescription))
                continue;
            queue=new JSONObject();
            // DADOS DE QUEUE SUMMARY
            queue.put("queue", qsc.getQueue());
            queue.put("queueName", qsc.getQueueName());
            queue.put("available", qsc.getAvailable());
            queue.put("talktime", qsc.getTalktime());
            queue.put("holdtime", qsc.getHoldtime());
            queue.put("loggedIn", qsc.getLoggedIn());
            queue.put("calls", qsc.getCallers());
            queue.put("longestHoldTime", qsc.getLongestHoldTime());
            
            // DADOS DE QUEUE STATUS
            queue.put("max", qstc.getMax());
            queue.put("strategy", qstc.getStrategy());
            queue.put("completed", qstc.getCompleted());
            queue.put("serviceLevel", qstc.getServiceLevel());
            queue.put("serviceLevelPerf", qstc.getServiceLevelPerf());
            queue.put("weight", qstc.getWeight());
            queue.put("abandoned", qstc.getAbandoned());
            
            // DADOS QUEUE ENTRY
            calls=new JSONArray();
            for (QueueEntryEvent qee : qstc.getCallers()) {
                call=new JSONObject();
                call.put("uniqueid", qee.getUniqueId());
                call.put("callerid", qee.getCallerIdNum());
                call.put("position", qee.getPosition());
                calls.put(call);
            }
            queue.put("calls", calls);
            
            // DADOS AGENT
            agents=new JSONArray();
            for (QueueAgent qa : qstc.getAgents()) {
                agent=new JSONObject();
                agent.put("agent", qa.getAgent());
                agent.put("agentName", qa.getAgentName());
                agent.put("penalty", qa.getPenalty());
                agent.put("callsTaken", qa.getCallsTaken());
                agent.put("lastCall", qa.getLastCall());
                agent.put("status", qa.getStatus());
                agent.put("pause", qa.isPause());
                agent.put("reason", (qa.getReason()==null?"null":qa.getReason()));
                agent.put("pauseTime", (qa.getPauseTime()==null?"null":qa.getPauseTime()));
                
                // DADOS PEER STATUS 
                PeerStatusControl psc=qa.getPeer();     
                peer=new JSONObject();
                if(psc!=null){
                    peer.put("peer", psc.getPeer());
                    peer.put("channel", (psc.getChannel()==null?"null":psc.getChannel()));
                    peer.put("registerStatus", psc.getRegisterStatus());
                    peer.put("state", psc.getState());
                    peer.put("stateDesc", (psc.getStateDesc()==null?"null":psc.getStateDesc()));
                    peer.put("address", (psc.getAddress()==null?"null":psc.getAddress()));
                    peer.put("exten",(psc.getExten()==null?"null":psc.getExten()));
                    peer.put("uniqueid", (psc.getUniqueid()==null?"null":psc.getUniqueid()));
                    peer.put("lastCall", (psc.getLastCall()==null?"null":psc.getLastCall().getTime()));
                    peer.put("direction", psc.getDirection());
                }
                //INICIO KHOMP
                if(psc.getPeer().contains("Khomp")){
                    String seekPeer=psc.getPeer().substring(9);
                    for (Map.Entry<String,PeerStatusControl> entry1 : new PeerStatusCenterControl().getPeerStatusController().entrySet()) {
                        PeerStatusControl pscc = entry1.getValue();
                        if(pscc.getPeer().equals(seekPeer)){
                            peer.put("channel", (pscc.getChannel()==null?"null":pscc.getChannel()));
                            peer.put("state", pscc.getState());
                            peer.put("stateDesc", (pscc.getStateDesc()==null?"null":pscc.getStateDesc()));
                            peer.put("exten",(pscc.getExten()==null?"null":pscc.getExten()));
                            peer.put("uniqueid", (pscc.getUniqueid()==null?"null":pscc.getUniqueid()));
                            peer.put("lastCall", (pscc.getLastCall()==null?"null":pscc.getLastCall().getTime()));
                            peer.put("direction", pscc.getDirection());
                            // ESCREVENDO ABAIXO NO PEER ATRELADO AO AGENTE
                            psc.setLastCall(pscc.getLastCall());
                            psc.setUniqueid(pscc.getUniqueid());
                            break;
                        }
                    }
                }
                // FIM KHOMP
                agent.put("peer", peer);
                agents.put(agent);
            }
            queue.put("agents", agents);
            out.print(queue.toString());
            return;
        }
        
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
