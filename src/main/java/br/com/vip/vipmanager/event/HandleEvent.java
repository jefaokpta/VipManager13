/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vip.vipmanager.event;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.GetVarAction;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.NewChannelEvent;
import org.asteriskjava.manager.event.NewStateEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;
import org.asteriskjava.manager.response.GetVarResponse;
import org.asteriskjava.manager.response.ManagerResponse;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
class HandleEvent {

    private Map<String,PeerStatusControl> pscc;
    
    public HandleEvent() {
        pscc=new PeerStatusCenterControl().getPeerStatusController();
    }

    void handle(PeerStatusEvent pse) {
        if(pscc.containsKey(pse.getPeer())){
            PeerStatusControl psc=pscc.get(pse.getPeer());
            psc.setRegisterStatus(pse.getPeerStatus());
            if(pse.getAddress()!=null)
                psc.setAddress(pse.getAddress());
            //System.out.println(":::::::::::::::::::::::: EDITADO DADO EM PSCC");
            return;
        }   
        pscc.put(pse.getPeer(),new PeerStatusControl(pse.getPeer(), pse.getPeerStatus(),pse.getAddress()));
    }
    void handle(NewChannelEvent nce) {
        if(nce.getContext().equals("Vip-Peers")){           
            if(pscc.containsKey(nce.getChannel().split("-")[0])){
                PeerStatusControl psc=pscc.get(nce.getChannel().split("-")[0]);
                
                if(!nce.getExten().equals("s")){
                    psc.setChannel(nce.getChannel());
                    psc.setStateDesc(nce.getChannelStateDesc());
                    psc.setState(nce.getChannelState());
                    psc.setExten(nce.getExten());
                    psc.setDirection(0);
                    return;
                }
                psc.setDirection(1);
                psc.setChannelAux(nce.getChannel());
                psc.setUniqueid(nce.getUniqueId());
                //psc.setLastCall(nce.getDateReceived());
                return;
            }
            pscc.put(nce.getChannel().split("-")[0],
                    new PeerStatusControl(nce.getChannel().split("-")[0], nce.getChannelStateDesc(), 
                            nce.getChannelState(), 
                            (nce.getExten().equals("s")?"":nce.getExten()),
                            nce.getUniqueId(), nce.getChannel(),
                            nce.getDateReceived()));
            return;
        }
        // SE FOR KHOMP
        if(nce.getChannel().split("/")[0].equals("Khomp")&&nce.getCallerIdNum().length()<5){
            if(pscc.containsKey(nce.getChannel().split("-")[0])){
                PeerStatusControl psc=pscc.get(nce.getChannel().split("-")[0]);
                psc.setExten(nce.getExten());
                psc.setDirection(0);
                psc.setChannel(nce.getChannel());
                psc.setStateDesc(nce.getChannelStateDesc());
                psc.setState(nce.getChannelState());
                psc.setUniqueid(nce.getUniqueId());
                psc.setPeer(nce.getCallerIdNum());
                return;
            }
            pscc.put(nce.getChannel().split("-")[0],
                    new PeerStatusControl(nce.getCallerIdNum(), nce.getChannelStateDesc(), 
                            nce.getChannelState(), 
                            (nce.getExten().equals("s")?"":nce.getExten()),
                            nce.getUniqueId(), nce.getChannel(),
                            nce.getDateReceived()));
        }
    }
    void handle(NewStateEvent nse) {
        if(pscc.containsKey(nse.getChannel().split("-")[0])){
            PeerStatusControl psc=pscc.get(nse.getChannel().split("-")[0]);
            psc.setState(nse.getChannelState());
            psc.setStateDesc(nse.getChannelStateDesc());
            if(nse.getChannelState()==5){
                psc.setDirection(1);
                psc.setExten(nse.getConnectedLineNum());               
                return;
            }
            if(nse.getChannelState()==6){
                psc.setLastCall(nse.getDateReceived());
                psc.setUniqueid(nse.getUniqueId());
                return;
            }
            psc.setDirection(0);
            psc.setLastCall(nse.getDateReceived());
        }
    }
    void handle(HangupEvent he) {
        if(pscc.containsKey(he.getChannel().split("-")[0])){
            PeerStatusControl psc=pscc.get(he.getChannel().split("-")[0]);
            if(psc.getState()==he.getChannelState()){
                psc.setState(0);
                psc.setStateDesc("Down");
            }                      
            if(he.getChannel().split("/")[0].equals("Khomp"))
                psc.setPeer("");
        }
    }
    void handle(DialEvent de) {
        
        if(de.getDialStatus() == null)
            pscc.get(de.getChannel().split("-")[0]).setDialStatus("Ring");
        else 
            pscc.get(de.getChannel().split("-")[0]).setDialStatus(de.getDialStatus());
        
//        if(de.getSubEvent().equals("Begin")&&de.getDestination().split("/")[0].equals("Khomp")){
//            String peer=de.getDialString().split("/")[1];
//            if(peer.length()<5){
////                if(peer.length()==3)
////                    peer="1"+peer;
//                if(pscc.containsKey(de.getDestination().split("-")[0])){
//                    PeerStatusControl psc=pscc.get(de.getDestination().split("-")[0]);
//                    psc.setExten(de.getExten());
//                    psc.setDirection(0);
//                    psc.setChannel(de.getChannel());
//                    psc.setStateDesc(de.getChannelStateDesc());
//                    psc.setState(de.getChannelState());
//                    psc.setUniqueid(de.getUniqueId());
//                    psc.setPeer(peer);
//                    return;
//                }
//                pscc.put(de.getDestination().split("-")[0],
//                    new PeerStatusControl(peer, de.getChannelStateDesc(), 
//                            de.getChannelState(), 
//                            (de.getExten().equals("s")?"":de.getExten()),
//                            de.getUniqueId(), de.getChannel(),
//                            de.getDateReceived()));
//            }
//        }
    }
}
