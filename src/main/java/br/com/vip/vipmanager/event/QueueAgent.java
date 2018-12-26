/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vip.vipmanager.event;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class QueueAgent {

    private String agent;
    private String agentName;
    private PeerStatusControl peer;
    private String queue;
    private Integer penalty;
    private Integer callsTaken;
    private Long lastCall;
    private Integer status;
    private boolean pause;
    private String reason;
    private Long pauseTime;

    public QueueAgent(String agent, String queue, Integer penalty, Integer callsTaken, Long lastCall, Integer status,PeerStatusControl peer) {
        this.agent = agent;
        this.peer = peer;
        this.queue = queue;
        this.penalty = penalty;
        this.callsTaken = callsTaken;
        this.lastCall = lastCall;
        this.status = status;
    }

    public Long getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Long pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public PeerStatusControl getPeer() {
        return peer;
    }

    public void setPeer(PeerStatusControl peer) {
        this.peer = peer;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    public Integer getCallsTaken() {
        return callsTaken;
    }

    public void setCallsTaken(Integer callsTaken) {
        this.callsTaken = callsTaken;
    }

    public Long getLastCall() {
        return lastCall;
    }

    public void setLastCall(Long lastCall) {
        this.lastCall = lastCall;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
