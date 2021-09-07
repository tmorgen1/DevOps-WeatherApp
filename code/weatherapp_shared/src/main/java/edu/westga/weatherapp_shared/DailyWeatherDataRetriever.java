package edu.westga.weatherapp_shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Daily Weather Data Retriever interface.  Extends the remote class to allow for
 * Remote Method Invocation.  Objects that implement this interface should be able to
 * obtain daily weather data given location details with the number of days.
 */
public interface DailyWeatherDataRetriever extends Remote {
    
    /**
     * Obtains the daily weather data for a location given the city name.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @param numOfDays - the number of days to gather data for
     * @return a String containing the weather data
     * @throws RemoteException
     */
    public String GetDataByCity(String cityName, int numOfDays) throws RemoteException;

    /**
     * Obtains the daily weather data for a location given the city name and state code.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @param stateCode - the state code
     * @param numOfDays - the number of days to gather data for
     * @return a String containing the weather data
     * @throws RemoteException
     */
    public String GetDataByCityAndStateCode(String cityName, String stateCode, int numOfDays) throws RemoteException;

    /**
     * Obtains the daily weather data for a location given the city name, state code, and country code.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @param stateCode - the state code
     * @param countryCode - the country code
     * @param numOfDays - the number of days to gather data for
     * @return a String containing the weather data
     * @throws RemoteException
     */
    public String GetDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode, int numOfDays) throws RemoteException;

}
