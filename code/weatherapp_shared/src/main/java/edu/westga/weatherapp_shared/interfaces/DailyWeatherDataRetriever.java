package edu.westga.weatherapp_shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;

/**
 * Daily Weather Data Retriever interface.  Extends the remote class to allow for
 * Remote Method Invocation.  Objects that implement this interface should be able to
 * obtain daily weather data given location details with the number of days.
 * 
 * @author Thomas Morgenstern
 */
public interface DailyWeatherDataRetriever extends Remote {
    
    /**
     * Obtains the daily weather data for a location given the city name.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @param numOfDays - the number of days to gather data for
     * @return a String containing the weather data
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCity(String cityName, int numOfDays) throws RemoteException;

    /**
     * Obtains the daily weather data for a location given the city name and state
     * code. Returns null if data could not be retrieved.
     * 
     * @param cityName  - the name of the city
     * @param stateCode - the state code
     * @param numOfDays - the number of days to gather data for
     * @return a String containing the weather data
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndStateCode(String cityName, String stateCode, int numOfDays) throws RemoteException;

    /**
     * Obtains the daily weather data for a location given the city name and country
     * code. Returns null if data could not be retrieved.
     * 
     * @param cityName    - the name of the city
     * @param countryCode - the country code
     * @param numOfDays   - the number of days to gather data for
     * @return a String containing the weather data
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndCountryCode(String cityName, String countryCode, int numOfDays) throws RemoteException;

    /**
     * Obtains the daily weather data for a location given the city name, state
     * code, and country code. Returns null if data could not be retrieved.
     * 
     * @param cityName    - the name of the city
     * @param stateCode   - the state code
     * @param countryCode - the country code
     * @param numOfDays   - the number of days to gather data for
     * @return a String containing the weather data
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode, int numOfDays)
            throws RemoteException;

    /**
     * Sets the MeasurementUnits of the data retriever. Future data retrieval will
     * obtain data in new units specified.
     * 
     * @param units - units of measurement
     * @throws RemoteException - exception in the event of an RMI error
     */
    void setUnitsOfMeasurement(MeasurementUnits units) throws RemoteException;

    /**
     * Provides the MeasurementUnits of the data retriever.
     * 
     * @return the units of measurement
     * @throws RemoteException - exception in the event of an RMI error
     */
    MeasurementUnits getUnitsOfMeasurement() throws RemoteException;
}
