/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vip.vipmanager.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class PeerStatusCenterControl implements Serializable{
    
    private static Map<String,PeerStatusControl> peerStatusController;
    
    public Map<String,PeerStatusControl> getPeerStatusController() {
        if(peerStatusController==null){
            peerStatusController=new HashMap<>();
        }
        return peerStatusController;
    }
    
    public Map<String,PeerStatusControl> getProtectedPeerStatusController() {
        if(peerStatusController==null){
            peerStatusController=new HashMap<>();
        }
        return new HashMap<>(peerStatusController);
    }

    public void setPeerStatusController(Map<String, PeerStatusControl> peerStatusController) {
        PeerStatusCenterControl.peerStatusController = peerStatusController;
    }

}
