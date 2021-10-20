package edu.westga.weatherapp_service.model;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.SevereWeatherWarningsRetriever;
import edu.westga.weatherapp_service.resources.ServiceConstants;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This severe warnings retriever uses the OpenWeather API to obtain weather
 * icons. Also extends the UnicastRemoteObject to allow for Remote Method
 * Invocation.
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class OpenWeatherSevereWarningsRetriever extends UnicastRemoteObject implements SevereWeatherWarningsRetriever {

    public static final int LATITUDE_UPPER_BOUND = 90;
    public static final int LATITUDE_LOWER_BOUND = -90;
    public static final int LONGITUDE_UPPER_BOUND = 180;
    public static final int LONGITUDE_LOWER_BOUND = -180;
    public static final String LATITUDE_OUT_OF_BOUND = "Invalid Latitude - Latitude must be between -90 and 90 inclusive.";
    public static final String LONGITUDE_OUT_OF_BOUND = "Invalid Longitude - Longitude must be between -180 and 180 inclusive.";
    public static final String INCORRECT_UNITS = "Invalid Units - Units must not be null and be of enum type MeasurementUnits";
    public static final String SEVERE_WEATHER_WARNINGS_INFORMATION_KEY = "alerts";
    private static final String SEVERE_WARNING_BASE_URL = "http:/"
            + "/pro.openweathermap.org/data/2.5/onecall?exclude=minutely,hourly,daily,current&appid=";
    private DataRetriever dataRetriever;

    /**
     * Creates an Open Weather Severe Warnings Retriever
     * 
     * @param dataRetriever the data retriever
     * 
     * @precondition dataRetriever != null
     * @postcondition none
     * @throws RemoteException - exception in the event of an RMI error
     */
    public OpenWeatherSevereWarningsRetriever(DataRetriever dataRetriever) throws RemoteException {
        super();
        if (dataRetriever == null) {
            throw new IllegalArgumentException("The retriever cannot be null");
        }
        this.dataRetriever = dataRetriever;
    }

    @Override
    public String getSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> units)
            throws RemoteException, IllegalArgumentException {
        if (latitude < OpenWeatherSevereWarningsRetriever.LATITUDE_LOWER_BOUND
                || latitude > OpenWeatherSevereWarningsRetriever.LATITUDE_UPPER_BOUND) {
            throw new IllegalArgumentException(OpenWeatherSevereWarningsRetriever.LATITUDE_OUT_OF_BOUND);
        }
        if (longitude < OpenWeatherSevereWarningsRetriever.LONGITUDE_LOWER_BOUND
                || longitude > OpenWeatherSevereWarningsRetriever.LONGITUDE_UPPER_BOUND) {
            throw new IllegalArgumentException(OpenWeatherSevereWarningsRetriever.LONGITUDE_OUT_OF_BOUND);
        }
        if (units == null || !units.getClass().equals(MeasurementUnits.class)) {
            throw new IllegalArgumentException(OpenWeatherSevereWarningsRetriever.INCORRECT_UNITS);
        }
        String data = this.fetchDataOfSevereWeatherWarningsForLocation(latitude, longitude, units);

        return data;
    }

    private String fetchDataOfSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> units) {
        MeasurementUnits unit = Enum.valueOf(MeasurementUnits.class, units.name());
        String paramLatitude = "&lat=" + latitude;
        String paramLongitude = "&lon=" + longitude;
        URL apiCall = this.dataRetriever.getServiceAPICallURL(paramLatitude + paramLongitude,
                OpenWeatherSevereWarningsRetriever.SEVERE_WARNING_BASE_URL, ServiceConstants.API_KEY, unit);
        return this.dataRetriever.getData(apiCall);
    }
}