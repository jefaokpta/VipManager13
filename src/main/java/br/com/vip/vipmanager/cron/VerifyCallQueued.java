/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vip.vipmanager.cron;

import br.com.vip.vipmanager.event.QueueStatusCenterControl;
import br.com.vip.vipmanager.event.QueueStatusControl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.asteriskjava.manager.event.QueueEntryEvent;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class VerifyCallQueued {

    private Map<String,QueueStatusControl> qstcc=new QueueStatusCenterControl().getQstcc();
    private long now=new Date().getTime()/1000;
    
    
    public void removeCallAfterOneHour(){
        for (Map.Entry<String, QueueStatusControl> entry : qstcc.entrySet()) {
            List<QueueEntryEvent> queue=entry.getValue().getCallers();
            List<QueueEntryEvent> removeList=new ArrayList<>(queue);
            for (QueueEntryEvent qe : removeList) {
                long callTime=Long.parseLong(qe.getUniqueId().substring(0, qe.getUniqueId().indexOf(".")))/1000;
                long less=callTime-now;
                less=less/60;
                less=less/60;
                if(less>0){
                    queue.remove(qe);
                }
            }
        }
    }
}
