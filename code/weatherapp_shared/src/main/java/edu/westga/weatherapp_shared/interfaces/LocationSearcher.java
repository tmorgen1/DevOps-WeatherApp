package edu.westga.weatherapp_shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * Location Searcher interface.  Extends the remote class to allow for Remote Method Invocation.
 * Objects that implement this interface should be able to obtain WeatherLocations from some sort of
 * database/file and return a collection of WeatherLocations based on a search entry.
 * 
 * @author Thomas Morgenstern
 */
public interface LocationSearcher extends Remote {
    
    /**
     * Searches the collection of all locations for a weather location containing a city that starts with the 
     * characters presented in the search entry, case insensitive.  This response is limited in the number
     * of entries based on the maxEntryResponse parameter.
     * 
     * @param searchEntry - search param for city name
     * @param maxEntryResponse - max number of entries of WeatherLocations returned in the collection
     * @param latitude - the closest latitude to the user
     * @param longitude - the closest longitude to the user
     * @return the Collection of WeatherLocations that contain cities whose names start with the search entry
     * @throws RemoteException - exception in the event of an RMI error
     */
    Collection<WeatherLocation> searchLocations(String searchEntry, int maxEntryResponse, double latitude, double longitude) throws RemoteException;
    
    /**
     * Searches the database of all locations for a weather location that is near the location based on the
     * provided public IP Address.
     * 
     * @param ipAddress - the public IP Address
     * @return the WeatherLocation if a location is found near the IP Address.  Null if the ip address is formatted
     *          incorrectly or if it is not near any locations.
     * @throws RemoteException - exception in the event of an RMI error
     */
    WeatherLocation getLocationByIP(String ipAddress) throws RemoteException;
}
