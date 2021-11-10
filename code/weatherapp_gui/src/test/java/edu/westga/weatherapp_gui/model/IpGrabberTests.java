package edu.westga.weatherapp_gui.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class IpGrabberTests {

    @Test
    public void getCurrentIpAddress() {
        String result = IpGrabber.getCurrentIpAddress();
        assertNotNull(result);
    }
}
