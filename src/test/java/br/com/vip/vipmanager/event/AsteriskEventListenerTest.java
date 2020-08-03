package br.com.vip.vipmanager.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsteriskEventListenerTest {

    @Test
    void mustBeIPLocal(){
        assertEquals("10.0.1.10", new AsteriskEventListener().getMc().getHostname());
    }
}