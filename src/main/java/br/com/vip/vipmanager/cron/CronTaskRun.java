/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vip.vipmanager.cron;

import br.com.vip.vipmanager.event.AsteriskEventListener;
import it.sauronsoftware.cron4j.Scheduler;
import java.io.IOException;
import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class CronTaskRun implements SystemEventListener{

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        Scheduler cron=new Scheduler();
        Scheduler removeCalls=new Scheduler();
        AsteriskEventListener eventListener=new AsteriskEventListener();
        
        if(event instanceof PostConstructApplicationEvent){
            System.out.println("O VIP LISTENER iniciou! Vai CRON!!!");
            cron.schedule("* * * * *", new Runnable(){
                @Override
                public void run() {
                    new RenewQueueSummaryAndStatus().renew();
                }              
            });
            removeCalls.schedule("*/5 * * * *", new Runnable(){
                @Override
                public void run() {
                    new VerifyCallQueued().removeCallAfterOneHour();
                }              
            });
            System.out.println("INICIANDO LISTENER ASTERISK !!!");
            
            eventListener.getMc().addEventListener(eventListener);
            try {
                eventListener.getMc().login();
            } catch (IllegalStateException | IOException | AuthenticationFailedException | TimeoutException ex) {
                System.out.println("DEU RUIM INICIANDO LISTENER ASTERISK !!!");
            }
            cron.start();
            removeCalls.start();
            if(event instanceof PreDestroyApplicationEvent){
                System.out.println("O VIP LISTENER ESTA FINALIZANDO!");
                cron.stop();
                removeCalls.stop();
            }
        }
    }

    @Override
    public boolean isListenerForSource(Object value) {
        return (value instanceof Application);
    }

}
