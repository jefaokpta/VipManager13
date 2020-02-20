/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vip.vipmanager.event;

import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.event.AgentCalledEvent;
import org.asteriskjava.manager.event.AgentCompleteEvent;
import org.asteriskjava.manager.event.AgentConnectEvent;
import org.asteriskjava.manager.event.AgentRingNoAnswerEvent;
import org.asteriskjava.manager.event.AttendedTransferEvent;
import org.asteriskjava.manager.event.BridgeCreateEvent;
import org.asteriskjava.manager.event.BridgeDestroyEvent;
import org.asteriskjava.manager.event.BridgeEnterEvent;
import org.asteriskjava.manager.event.BridgeEvent;
import org.asteriskjava.manager.event.BridgeLeaveEvent;
import org.asteriskjava.manager.event.BridgeMergeEvent;
import org.asteriskjava.manager.event.CdrEvent;
import org.asteriskjava.manager.event.ChallengeResponseFailedEvent;
import org.asteriskjava.manager.event.ChallengeSentEvent;
import org.asteriskjava.manager.event.ChannelReloadEvent;
import org.asteriskjava.manager.event.ChannelUpdateEvent;
import org.asteriskjava.manager.event.DeviceStateChangeEvent;
import org.asteriskjava.manager.event.DialEvent;
import org.asteriskjava.manager.event.DtmfEvent;
import org.asteriskjava.manager.event.ExtensionStatusEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.HangupRequestEvent;
import org.asteriskjava.manager.event.HoldEvent;
import org.asteriskjava.manager.event.InvalidPasswordEvent;
import org.asteriskjava.manager.event.JitterBufStatsEvent;
import org.asteriskjava.manager.event.JoinEvent;
import org.asteriskjava.manager.event.LeaveEvent;
import org.asteriskjava.manager.event.LocalBridgeEvent;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.event.MasqueradeEvent;
import org.asteriskjava.manager.event.MusicOnHoldEvent;
import org.asteriskjava.manager.event.NewAccountCodeEvent;
import org.asteriskjava.manager.event.NewCallerIdEvent;
import org.asteriskjava.manager.event.NewChannelEvent;
import org.asteriskjava.manager.event.NewConnectedLineEvent;
import org.asteriskjava.manager.event.NewExtenEvent;
import org.asteriskjava.manager.event.NewStateEvent;
import org.asteriskjava.manager.event.PeerStatusEvent;
import org.asteriskjava.manager.event.PickupEvent;
import org.asteriskjava.manager.event.QueueCallerAbandonEvent;
import org.asteriskjava.manager.event.QueueCallerJoinEvent;
import org.asteriskjava.manager.event.QueueCallerLeaveEvent;
import org.asteriskjava.manager.event.QueueEntryEvent;
import org.asteriskjava.manager.event.QueueMemberAddedEvent;
import org.asteriskjava.manager.event.QueueMemberEvent;
import org.asteriskjava.manager.event.QueueMemberPausedEvent;
import org.asteriskjava.manager.event.QueueMemberRemovedEvent;
import org.asteriskjava.manager.event.QueueParamsEvent;
import org.asteriskjava.manager.event.QueueStatusCompleteEvent;
import org.asteriskjava.manager.event.QueueSummaryCompleteEvent;
import org.asteriskjava.manager.event.QueueSummaryEvent;
import org.asteriskjava.manager.event.RegistryEvent;
import org.asteriskjava.manager.event.RenameEvent;
import org.asteriskjava.manager.event.RtcpReceivedEvent;
import org.asteriskjava.manager.event.RtcpSentEvent;
import org.asteriskjava.manager.event.SoftHangupRequestEvent;
import org.asteriskjava.manager.event.SuccessfulAuthEvent;
import org.asteriskjava.manager.event.UnholdEvent;
import org.asteriskjava.manager.event.UnlinkEvent;
import org.asteriskjava.manager.event.VarSetEvent;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class AsteriskEventListener implements ManagerEventListener{

    private static ManagerConnection mc;
    

    public  ManagerConnection getMc() {
        if(mc==null){
            ManagerConnectionFactory factory=new ManagerConnectionFactory(
                    //"sip.vipcc.vcomsolucoes.com.br","vip-events-test","vipEventsPass"
                    "10.0.1.10","vip-events","vipEventsPass"
            );       
            mc=factory.createManagerConnection();
        }
        return mc;
    }
    
    @Override
    public void onManagerEvent(ManagerEvent event) {
        
        if(event instanceof PeerStatusEvent){
            new HandleEvent().handle((PeerStatusEvent) event);
            return;
        }
        
        if(event instanceof NewChannelEvent){
            new HandleEvent().handle((NewChannelEvent) event);
            return;
        }
        
        if(event instanceof NewStateEvent){
            new HandleEvent().handle((NewStateEvent) event);
            return;
        }
            
        if(event instanceof HangupEvent){
            new HandleEvent().handle((HangupEvent) event);
            return;
        }
        if(event instanceof QueueSummaryEvent){
            new HandleQueue().handle((QueueSummaryEvent) event);
            return;
        }
        if(event instanceof QueueParamsEvent){
            new HandleQueue().handle((QueueParamsEvent) event);
            return;
        }
        if(event instanceof QueueMemberEvent){
            new HandleAgent().handle((QueueMemberEvent) event);
            return;
        }
        if(event instanceof QueueMemberRemovedEvent){
            new HandleAgent().handle((QueueMemberRemovedEvent) event);
            return;
        }
        if(event instanceof QueueEntryEvent){
            new HandleAgent().handle((QueueEntryEvent) event);
            return;
        }
        if(event instanceof AgentConnectEvent){
            new HandleAgent().handle((AgentConnectEvent) event);
            return;
        }
        if(event instanceof QueueCallerAbandonEvent){
            new HandleAgent().handle((QueueCallerAbandonEvent) event);
            return;
        }
        if(event instanceof LeaveEvent){
            new HandleAgent().handle((LeaveEvent) event);
            return;
        }
        if(event instanceof QueueMemberPausedEvent){
            new HandleAgent().handle((QueueMemberPausedEvent) event);
            return;
        }
        if(event instanceof AgentCalledEvent){
            new HandleAgent().handle((AgentCalledEvent) event);
            return;
        }
        if(event instanceof CdrEvent)
            return;
        if(event instanceof VarSetEvent)
            return;
        if(event instanceof ExtensionStatusEvent)
            return;
        if(event instanceof NewExtenEvent)
            return;
        if(event instanceof QueueSummaryCompleteEvent)
            return;
        if(event instanceof QueueStatusCompleteEvent)
            return;
        if(event instanceof RtcpSentEvent)
            return;
        if(event instanceof RtcpReceivedEvent)
            return;
        if(event instanceof ChannelReloadEvent)
            return;
        
        if(event instanceof NewCallerIdEvent)
            return;
        if(event instanceof BridgeEvent)
            return;
        if(event instanceof UnlinkEvent)
            return;
        if(event instanceof NewAccountCodeEvent)
            return;
        if(event instanceof ChannelUpdateEvent)
            return;
        if(event instanceof JitterBufStatsEvent)
            return;
        if(event instanceof DialEvent){
            new HandleEvent().handle((DialEvent) event);
            return;
        }
        if(event instanceof MusicOnHoldEvent)
            return;
        if(event instanceof RenameEvent)
            return;
        if(event instanceof MasqueradeEvent)
            return;
        if(event instanceof DtmfEvent)
            return;
        if(event instanceof JoinEvent)
            return;
        if(event instanceof QueueMemberAddedEvent)
            return;
        if(event instanceof RegistryEvent)
            return;        
        // APARICOES NO AST13
        if(event instanceof SoftHangupRequestEvent)
            return; 
        if(event instanceof DeviceStateChangeEvent)
            return; 
        if(event instanceof HangupRequestEvent)
            return; 
        if(event instanceof InvalidPasswordEvent)
            return; 
        if(event instanceof QueueCallerJoinEvent)
            return; 
        if(event instanceof BridgeEnterEvent)
            return; 
        if(event instanceof HoldEvent)
            return; 
        if(event instanceof BridgeCreateEvent)
            return; 
        if(event instanceof LocalBridgeEvent)
            return; 
        if(event instanceof UnholdEvent)
            return; 
        if(event instanceof BridgeLeaveEvent)
            return; 
        if(event instanceof SuccessfulAuthEvent)
            return;
        if(event instanceof ChallengeSentEvent)
            return;
        if(event instanceof ChallengeResponseFailedEvent)
            return;
        if(event instanceof NewConnectedLineEvent)
            return;
        if(event instanceof AgentCompleteEvent)
            return;
        if(event instanceof BridgeDestroyEvent)
            return;
        if(event instanceof QueueCallerLeaveEvent){
            new HandleAgent().handle((QueueCallerLeaveEvent) event);
            return;
        }
        if(event instanceof AgentCompleteEvent)
            return;
        if(event instanceof AgentRingNoAnswerEvent)
            return;
        if(event instanceof AttendedTransferEvent)
            return;
        if(event instanceof BridgeMergeEvent)
            return;
        if(event instanceof PickupEvent)
            return;
        
        System.out.println(event);
    }

    
}
