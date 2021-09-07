package edu.westga.weatherapp_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Current Weather Data Retriever interface.  Extends the remote class to allow for
 * Remote Method Invocation.  Objects that implement this interface should be able to
 * obtain current weather data given location details.
 */
public interface CurrentWeatherDataRetriever extends Remote {
    
    /**
     * Obtains the current weather data for a location given the city name.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @return a String containing weather data for given location
     * @throws RemoteException
     */
    public String GetDataByCity(String cityName) throws RemoteException;

    /**
     * Obtains the current weather data for a location given the city name and state code.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @param stateCode - the code for the residing state
     * @return a String containing weather data for given location
     * @throws RemoteException
     */
    public String GetDataByCityAndStateCode(String cityName, String stateCode) throws RemoteException;

    /**
     * Obtains the current weather data for a location given the city name, state code, and country code.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @param stateCode - the code for the residing state
     * @param countryCode - the code for the residing country
     * @return a String containing weather data for given location
     * @throws RemoteException
     */
    public String GetDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode) throws RemoteException;

    //TODO: implement geocoords for get location/ip
}
