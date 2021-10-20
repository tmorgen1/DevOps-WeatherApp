package edu.westga.weatherapp_shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Map Radar Data Retriever interface.  Extends the remote class to allow for
 * Remote Method Invocation.  Objects that implement this interface should be able to
 * obtain current weather data given location details.
 * 
 * @author Thomas Morgenstern
 */
public interface MapRadarDataRetriever extends Remote {
    
    /**
     * Obtains the html/css/js for the web page to be displayed that contains a weather radar map.
     * 
     * @return the html/css/js for the web page as a String
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getMapRadarHTML() throws RemoteException;

}
