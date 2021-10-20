package edu.westga.weatherapp_gui.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Defines the IpGrabber class that handles getting the users ip address
 * 
 * @author Michael Pavich
 */
public class IpGrabber {

    /**
     * Gets the current users ip address
     * 
     * @return the ip address
     */
    public static String getCurrentIpAddress() {
        
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine();
            
            return ip;
        } catch (IOException e) {
            System.err.println("Error grabbing current Ip Address");
            return null;
        }
    }
}
