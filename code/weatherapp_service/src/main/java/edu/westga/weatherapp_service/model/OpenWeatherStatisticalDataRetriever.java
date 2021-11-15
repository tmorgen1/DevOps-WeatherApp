package edu.westga.weatherapp_service.model;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.westga.weatherapp_service.resources.ServiceConstants;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.StatisticalWeatherDataRetriever;

/**
 * This weather data retriever uses the OpenWeather API to obtain statistical weather data based on historical weather data.
 * Also extends the UnicastRemoteObject to allow for Remote Method Invocation.
 * 
 * @author Thomas Morgenstern
 */
public class OpenWeatherStatisticalDataRetriever extends UnicastRemoteObject
        implements StatisticalWeatherDataRetriever {

    /**
     * Number of the month should be between 1 and 12, inclusive.
     */
    private static final String NUM_OF_THE_MONTH_SHOULD_BE_IN_BETWEEN_1_AND_12_INCLUSIVE = "numOfTheMonth should be in between 1 and 12, inclusive";

    /**
     * Country code should not be empty error message.
     */
    private static final String COUNTRY_CODE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE = "countryCode should not be empty";

    /**
     * Country code should not be null error message.
     */
    private static final String COUNTRY_CODE_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "countryCode should not be null";

    /**
     * State could should not be empty error message.
     */
    private static final String STATE_CODE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE = "stateCode should not be empty";

    /**
     * State code should not be null error message.
     */
    private static final String STATE_CODE_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "stateCode should not be null";

    /**
     * City name should not be empty error message.
     */
    private static final String CITY_NAME_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE = "cityName should not be empty";

    /**
     * City name should not be null error message.
     */
    private static final String CITY_NAME_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "cityName should not be null";

    /**
     * APIDataRetriever should not be null error message.
     */
    private static final String API_DATA_RETRIEVER_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "APIDataRetriever should not be null";

    /**
     * OpenWeather API Base call.
     */
    private static final String OPEN_WEATHER_API_CALL_BASE = "https://history.openweathermap.org/data/2.5/aggregated/month?appid=";

    /**
     * Latitude should be between -90 and 90, inclusive error message
     */
    private static final String LATITUDE_ERROR_MESSAGE = "latitude should be in between -90 and 90, inclusive";

    /**
     * Longitude should be between -180 and 180, inclusive error message
     */
    private static final String LONGITUDE_ERROR_MESSAGE = "longitude should be in between -180 and 180, inclusive";

    private DataRetriever dataRetriever;

    /**
     * Creates an OpenWeather statistical data retriever.
     * 
     * @param retriever - the object to handle web url calls
     * @throws RemoteException - exception in the event of an RMI error
     */
    public OpenWeatherStatisticalDataRetriever(DataRetriever retriever) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException(API_DATA_RETRIEVER_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }

        this.dataRetriever = retriever;
    }

    @Override
    public String getDataByCity(String cityName, int numOfTheMonth) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (numOfTheMonth < 1 || numOfTheMonth > 12) {
            throw new IllegalArgumentException(NUM_OF_THE_MONTH_SHOULD_BE_IN_BETWEEN_1_AND_12_INCLUSIVE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL("&q=" + cityName + "&month=" + numOfTheMonth,
                OpenWeatherStatisticalDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                MeasurementUnits.Imperial);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndCountryCode(String cityName, String countryCode, int numOfTheMonth)
            throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (countryCode == null) {
            throw new IllegalArgumentException(COUNTRY_CODE_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (countryCode.isEmpty()) {
            throw new IllegalArgumentException(COUNTRY_CODE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (numOfTheMonth < 1 || numOfTheMonth > 12) {
            throw new IllegalArgumentException(NUM_OF_THE_MONTH_SHOULD_BE_IN_BETWEEN_1_AND_12_INCLUSIVE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL(
                "&q=" + cityName + "," + countryCode + "&month=" + numOfTheMonth,
                OpenWeatherStatisticalDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                MeasurementUnits.Imperial);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndStateCode(String cityName, String stateCode, int numOfTheMonth)
            throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (stateCode == null) {
            throw new IllegalArgumentException(STATE_CODE_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (stateCode.isEmpty()) {
            throw new IllegalArgumentException(STATE_CODE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (numOfTheMonth < 1 || numOfTheMonth > 12) {
            throw new IllegalArgumentException(NUM_OF_THE_MONTH_SHOULD_BE_IN_BETWEEN_1_AND_12_INCLUSIVE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL(
                "&q=" + cityName + "," + stateCode + "&month=" + numOfTheMonth,
                OpenWeatherStatisticalDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                MeasurementUnits.Imperial);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode,
            int numOfTheMonth) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (stateCode == null) {
            throw new IllegalArgumentException(STATE_CODE_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (countryCode == null) {
            throw new IllegalArgumentException(COUNTRY_CODE_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (stateCode.isEmpty()) {
            throw new IllegalArgumentException(STATE_CODE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (countryCode.isEmpty()) {
            throw new IllegalArgumentException(COUNTRY_CODE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (numOfTheMonth < 1 || numOfTheMonth > 12) {
            throw new IllegalArgumentException(NUM_OF_THE_MONTH_SHOULD_BE_IN_BETWEEN_1_AND_12_INCLUSIVE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL(
                "&q=" + cityName + "," + stateCode + "," + countryCode + "&month=" + numOfTheMonth,
                OpenWeatherStatisticalDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                MeasurementUnits.Imperial);
        return this.dataRetriever.getData(apiCall);
    }
    
    @Override
    public String getDataByCoordinates(double latitude, double longitude, int numOfTheMonth) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException(LATITUDE_ERROR_MESSAGE);
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException(LONGITUDE_ERROR_MESSAGE);
        }
        if (numOfTheMonth < 1 || numOfTheMonth > 12) {
            throw new IllegalArgumentException(NUM_OF_THE_MONTH_SHOULD_BE_IN_BETWEEN_1_AND_12_INCLUSIVE);
        }
        
        URL apiCall = this.dataRetriever.getServiceAPICallURL(
                "&lat=" + latitude + "&lon=" + longitude + "&month=" + numOfTheMonth,
                OpenWeatherStatisticalDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                MeasurementUnits.Imperial);
        return this.dataRetriever.getData(apiCall);
    }

}
