package edu.westga.weatherapp_shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * Location Searcher interface.  Extends the remote class to allow for Remote Method Invocation.
 * Objects that implement this interface should be able to obtain WeatherLocations from some sort of
 * database/file and return a collection of WeatherLocations based on a search entry.
 */
public interface LocationSearcher extends Remote {
    
    /**
     * Searches the collection of all locations for a weather location containing a city that starts with the 
     * characters presented in the search entry, case insensitive.  This response is limited in the number
     * of entries based on the maxEntryResponse parameter.
     * 
     * @param searchEntry - search param for city name
     * @param maxEntryResponse - max number of entries of WeatherLocations returned in the collection
     * @return the Collection of WeatherLocations that contain cities whose names start with the search entry
     * @throws RemoteException
     */
    public Collection<WeatherLocation> searchLocations(String searchEntry, int maxEntryResponse) throws RemoteException;
    
    /**
     * Searches the database of all locations for a weather location that is near the location based on the
     * provided public IP Address.
     * 
     * @param ipAddress - the public IP Address
     * @return the WeatherLocation if a location is found near the IP Address.  Null if the ip address is formatted
     *          incorrectly or if it is not near any locations.
     * @throws RemoteException
     */
    public WeatherLocation getLocationByIP(String ipAddress) throws RemoteException;
}
