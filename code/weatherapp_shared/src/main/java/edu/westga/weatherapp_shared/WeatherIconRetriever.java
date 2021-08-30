package edu.westga.weatherapp_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Weather Icon Retriever interface.  Extends the remote class to allow for
 * Remote Method Invocation.  Objects that implement this interface should be able to
 * obtain weather icon urls given icon ids.
 */
public interface WeatherIconRetriever extends Remote {

    /**
     * Retrieves a weather icon url based on given url icon id.
     * 
     * @param iconId - the weather icon id
     * @return the url to the weather icon
     * @throws RemoteException
     */
    public String GetWeatherIconUrlByIconId(String iconId) throws RemoteException;
    
}
