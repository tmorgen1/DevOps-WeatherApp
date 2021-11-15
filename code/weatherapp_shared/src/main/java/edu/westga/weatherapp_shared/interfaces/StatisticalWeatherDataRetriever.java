package edu.westga.weatherapp_shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Statistical Weather Data Retriever interface.  Extends the remote class to allow for
 * Remote Method Invocation.  Objects that implement this interface should be able to
 * obtain statistical weather data based on historical data given location details.
 * 
 * @author Thomas Morgenstern
 */
public interface StatisticalWeatherDataRetriever extends Remote {
    
    /**
     * Obtains the statistical weather data for a location given the city name and number of the month.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @param numOfTheMonth - the number of the month e.g. January - 1, December - 12
     * @return a String containing statistical weather data based on historical weather data
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCity(String cityName, int numOfTheMonth) throws RemoteException;

    /**
     * Obtains the statistical weather data for a location given the city name, 
     * state code, and number of the month.
     * 
     * @param cityName - the name of the city
     * @param stateCode - the state code
     * @param numOfTheMonth - the number of the month e.g. January - 1, December - 12
     * @return a String containing statistical weather data based on historical weather data
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndStateCode(String cityName, String stateCode, int numOfTheMonth) throws RemoteException;

    /**
     * Obtains the statistical weather data for a location given the city name,
     * country code, and number of the month.
     * 
     * @param cityName - the name of the city
     * @param countryCode - the country code
     * @param numOfTheMonth - the number of the month e.g. January - 1, December - 12
     * @return a String containing statistical weather data based on historical weather data
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndCountryCode(String cityName, String countryCode, int numOfTheMonth) throws RemoteException;

    /**
     * Obtains the statistical weather data for a location given the city name,
     * state code, country code, and number of the month.
     * 
     * @param cityName - the name of the city
     * @param stateCode - the state code
     * @param countryCode - the country code
     * @param numOfTheMonth - the number of the month e.g. January - 1, December - 12
     * @return a String containing statistical weather data based on historical weather data
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode, int numOfTheMonth) throws RemoteException;

    /**
     * Obtains the statistical weather data for a location given the coordinates
     * 
     * @param latitude - the latitude of the location
     * @param longitude - the longitude of the location
     * @param numOfTheMonth - the month to get the data for
     * 
     * @return the statistical weather data for the location
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCoordinates(double latitude, double longitude, int numOfTheMonth) throws RemoteException;

}
