/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vip.vipmanager.cron;

import br.com.vip.vipmanager.event.AsteriskEventListener;
import java.io.IOException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.QueueStatusAction;
import org.asteriskjava.manager.action.QueueSummaryAction;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class RenewQueueSummaryAndStatus {

    public void renew(){
        for (int vzs = 0; vzs < 30; vzs++) {
            try {
                new AsteriskEventListener().getMc().sendAction(new QueueSummaryAction());
                new AsteriskEventListener().getMc().sendAction(new QueueStatusAction());
                Thread.sleep(2000);
            } catch (InterruptedException | IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
                System.out.println("DEU RUIM RENEW QUEUE SUMMARY OU STATUS");
            }
        }
    }
}
