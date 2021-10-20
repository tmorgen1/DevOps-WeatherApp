package edu.westga.weatherapp_shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;

/**
 * Hourly Weather Data Retriever interface.  Extends the remote class to allow for
 * Remote Method Invocation.  Objects that implement this interface should be able to
 * obtain hourly weather data given location details.
 * 
 * @author Thomas Morgenstern
 */
public interface HourlyWeatherDataRetriever extends Remote {
    
    /**
     * Obtains the hourly weather data for a location given the city name.
     * Returns null if data could not be retrieved.
     * 
     * @param cityName - the name of the city
     * @param numOfHours - the number of hours to gather data for
     * @return a String containing weather data for given location
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCity(String cityName, int numOfHours) throws RemoteException;

    /**
     * Obtains the hourly weather data for a location given the city name and state
     * code. Returns null if data could not be retrieved.
     * 
     * @param cityName   - the name of the city
     * @param numOfHours - the number of hours to gather data for
     * @param stateCode  - the code for the residing state
     * @return a String containing weather data for given location
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndStateCode(String cityName, String stateCode, int numOfHours) throws RemoteException;

    /**
     * Obtains the hourly weather data for a location given the city name and
     * country code. Returns null if data could not be retrieved.
     * 
     * @param cityName    - the name of the city
     * @param countryCode - the code for the residing country
     * @param numOfHours  - the number of hours to gather data for
     * @return a String containing weather data for given location
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndCountryCode(String cityName, String countryCode, int numOfHours) throws RemoteException;

    /**
     * Obtains the hourly weather data for a location given the city name, state
     * code, and country code. Returns null if data could not be retrieved.
     * 
     * @param cityName    - the name of the city
     * @param stateCode   - the code for the residing state
     * @param countryCode - the code for the residing country
     * @param numOfHours  - the number of hours to gather data for
     * @return a String containing weather data for given location
     * @throws RemoteException - exception in the event of an RMI error
     */
    String getDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode,
            int numOfHours) throws RemoteException;

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
