/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vip.vipmanager.event;

import java.util.Map;
import org.asteriskjava.manager.event.AgentCalledEvent;
import org.asteriskjava.manager.event.AgentConnectEvent;
import org.asteriskjava.manager.event.LeaveEvent;
import org.asteriskjava.manager.event.QueueCallerAbandonEvent;
import org.asteriskjava.manager.event.QueueCallerLeaveEvent;
import org.asteriskjava.manager.event.QueueEntryEvent;
import org.asteriskjava.manager.event.QueueMemberEvent;
import org.asteriskjava.manager.event.QueueMemberPausedEvent;
import org.asteriskjava.manager.event.QueueMemberRemovedEvent;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
class HandleAgent {

    private Map<String,QueueStatusControl> qstcc;
    
    public HandleAgent() {
        qstcc=new QueueStatusCenterControl().getQstcc();
    }

    void handle(QueueMemberEvent qme) {
        if(qme.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(qme.getQueue());
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(qme.getName())){
                qa.setPause(qme.getPaused());
                qa.setPenalty(qme.getPenalty());
                qa.setCallsTaken(qme.getCallsTaken());
                qa.setLastCall(qme.getLastCall());
                qa.setStatus(qme.getStatus());
                return;
            }
        }
        String peer=qme.getStateinterface();
        if(peer.contains("Local")){
            peer=peer.replace("Local", "Khomp");
            peer=peer.split("@")[0];
        }
        PeerStatusControl psc=new PeerStatusCenterControl().getPeerStatusController().get(peer);
        if(psc==null){
            psc=new PeerStatusControl(peer, "Unregistered", "");
            new PeerStatusCenterControl().getPeerStatusController().put(peer, psc);
        }
        qsc.getAgents().add(new QueueAgent(qme.getName(), qme.getQueue(), qme.getPenalty(),
                qme.getCallsTaken(), qme.getLastCall(), qme.getStatus(),
                psc));
    }
    void handle(QueueMemberRemovedEvent qmre) {
        if(qmre.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(qmre.getQueue());
        QueueAgent del = null;
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(qmre.getMemberName())){
                del=qa;
                break;
            }
        }
        qsc.getAgents().remove(del);
    }
    void handle(QueueEntryEvent qee) {
        if(qee.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(qee.getQueue());
        for (QueueEntryEvent qe : qsc.getCallers()) {
            if(qe.getUniqueId().equals(qee.getUniqueId())){
                qe.setPosition(qee.getPosition());
            return;
            }
        }
        qsc.getCallers().add(qee);
    }
    void handle(AgentConnectEvent ace) {
        if(ace.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(ace.getQueue());
        QueueEntryEvent qee = null;
        for (QueueEntryEvent qe : qsc.getCallers()) {
            if(qe.getUniqueId().equals(ace.getUniqueId())){
                qee=qe;
                break;
            }
        }
        qsc.getCallers().remove(qee);
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(ace.getMemberName())){
                qa.getPeer().setExten(qee.getCallerIdNum());
                qa.getPeer().setDirection(1);
                break;
            }
        }
    }
    void handle(AgentCalledEvent ace) {
        if(ace.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(ace.getQueue());
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(ace.getAgentName())){
                qa.getPeer().setExten(ace.getCallerIdNum());
                qa.getPeer().setDirection(1);
                break;
            }
        }
    }
    void handle(QueueCallerAbandonEvent qcae) {
        if(qcae.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(qcae.getQueue());
        QueueEntryEvent qee = null;
        for (QueueEntryEvent qe : qsc.getCallers()) {
            if(qe.getUniqueId().equals(qcae.getUniqueId())){
                qee=qe;
                break;
            }
        }
        qsc.getCallers().remove(qee);
    }
        void handle(QueueCallerLeaveEvent qcl) { // CRIADO ESSE PRA TALVEZ FORCAR SAIDA DA LIGACAO DA FILA EM CASO DE LEAVEWHENEMPTY
        if(qcl.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(qcl.getQueue());
        QueueEntryEvent qee = null;
        for (QueueEntryEvent qe : qsc.getCallers()) {
            if(qe.getUniqueId().equals(qcl.getUniqueId())){
                qee=qe;
                break;
            }
        }
        qsc.getCallers().remove(qee);
    }
    void handle(LeaveEvent le) {
        if(le.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(le.getQueue());
        QueueEntryEvent qee = null;
        for (QueueEntryEvent qe : qsc.getCallers()) {
            if(qe.getUniqueId().equals(le.getUniqueId())){
                qee=qe;
                break;
            }
        }
        qsc.getCallers().remove(qee);
    }
    void handle(QueueMemberPausedEvent qmp) {
        if(qmp.getQueue().contains("callgrp"))
            return;
        QueueStatusControl qsc=qstcc.get(qmp.getQueue());
        for (QueueAgent qa : qsc.getAgents()) {
            if(qa.getAgent().equals(qmp.getMemberName())){
                qa.setReason(qmp.getReason());
                qa.setPauseTime(qmp.getDateReceived().getTime()/1000);
                return;
            }
        }
    }

    

    
}
