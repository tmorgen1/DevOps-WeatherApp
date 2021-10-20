package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.model.IpGrabber;

public class IpGrabberTests {
    

    @Test
    public void getCurrentIpAddress() {
        String result = IpGrabber.getCurrentIpAddress();
        assertNotNull(result);
    }
}
