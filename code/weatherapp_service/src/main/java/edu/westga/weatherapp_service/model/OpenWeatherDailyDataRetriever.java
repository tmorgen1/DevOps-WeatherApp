package edu.westga.weatherapp_service.model;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.westga.weatherapp_service.resources.ServiceConstants;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.DailyWeatherDataRetriever;

/**
 * This weather data retriever uses the OpenWeather API to obtain daily weather
 * data. Also extends the UnicastRemoteObject to allow for Remote Method
 * Invocation.
 * 
 * @author Thomas Morgenstern
 */
public class OpenWeatherDailyDataRetriever extends UnicastRemoteObject implements DailyWeatherDataRetriever {

    /**
     * Country code should not be empty error message.
     */
    private static final String COUNTRY_CODE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE = "countryCode should not be empty";

    /**
     * Country code should not be null error message.
     */
    private static final String COUNTRY_CODE_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "countryCode should not be null";

    /**
     * State code should not be empty error message.
     */
    private static final String STATE_CODE_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE = "stateCode should not be empty";

    /**
     * State code should not be null error message.
     */
    private static final String STATE_CODE_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "stateCode should not be null";

    /**
     * Num of days should be between 1 and 16 inclusive error message.
     */
    private static final String NUM_OF_DAYS_SHOULD_BE_BETWEEN_1_AND_16_INCLUSIVE_ERROR_MESSAGE = "numOfDays should be between 1 and 16, inclusive";

    /**
     * City name should not be empty error message.
     */
    private static final String CITY_NAME_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE = "cityName should not be empty";

    /**
     * City name should not be null error message.
     */
    private static final String CITY_NAME_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "cityName should not be null";

    /**
     * Units should not be null error message.
     */
    private static final String UNITS_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "units should not be null";

    /**
     * APIDataRetriever should not be null error message.
     */
    private static final String API_DATA_RETRIEVER_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "APIDataRetriever should not be null";

    /**
     * OpenWeather API Base call.
     */
    private static final String OPEN_WEATHER_API_CALL_BASE = "https://pro.openweathermap.org/data/2.5/forecast/daily?appid=";

    /**
     * The unit of measurement for api calls.
     */
    private MeasurementUnits units;

    /**
     * The object that handles data retrieval from the web calls.
     */
    private DataRetriever dataRetriever;

    /**
     * Creates an OpenWeather data retriever. Provides a daily forecast. Defaults to
     * imperial units.
     * 
     * @param retriever - the object to handle web url calls
     * @throws RemoteException - exception in the event of an RMI error
     */
    public OpenWeatherDailyDataRetriever(DataRetriever retriever) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException(API_DATA_RETRIEVER_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }

        this.dataRetriever = retriever;
        this.units = MeasurementUnits.Imperial;
    }

    /**
     * Creates an OpenWeather data retriever. Provides a daily forecast. Sets the
     * units of measurement to what is provided.
     * 
     * @param retriever - the object to handle web url calls
     * @param units     - the units of measurement
     * @throws RemoteException - exception in the event of an RMI error
     */
    public OpenWeatherDailyDataRetriever(DataRetriever retriever, MeasurementUnits units) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException(API_DATA_RETRIEVER_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (units == null) {
            throw new IllegalArgumentException(UNITS_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }

        this.dataRetriever = retriever;
        this.units = units;
    }

    @Override
    public String getDataByCity(String cityName, int numOfDays) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }
        if (numOfDays < 1 || numOfDays > 16) {
            throw new IllegalArgumentException(NUM_OF_DAYS_SHOULD_BE_BETWEEN_1_AND_16_INCLUSIVE_ERROR_MESSAGE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL("&q=" + cityName + "&cnt=" + numOfDays,
                OpenWeatherDailyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY, this.units);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndStateCode(String cityName, String stateCode, int numOfDays) throws RemoteException {
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
        if (numOfDays < 1 || numOfDays > 16) {
            throw new IllegalArgumentException(NUM_OF_DAYS_SHOULD_BE_BETWEEN_1_AND_16_INCLUSIVE_ERROR_MESSAGE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL("&q=" + cityName + "," + stateCode + "&cnt=" + numOfDays,
                OpenWeatherDailyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY, this.units);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndCountryCode(String cityName, String countryCode, int numOfDays)
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
        if (numOfDays < 1 || numOfDays > 16) {
            throw new IllegalArgumentException(NUM_OF_DAYS_SHOULD_BE_BETWEEN_1_AND_16_INCLUSIVE_ERROR_MESSAGE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL(
                "&q=" + cityName + "," + countryCode + "&cnt=" + numOfDays,
                OpenWeatherDailyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY, this.units);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode,
            int numOfDays) throws RemoteException {
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
        if (numOfDays < 1 || numOfDays > 16) {
            throw new IllegalArgumentException(NUM_OF_DAYS_SHOULD_BE_BETWEEN_1_AND_16_INCLUSIVE_ERROR_MESSAGE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL(
                "&q=" + cityName + "," + stateCode + "," + countryCode + "&cnt=" + numOfDays,
                OpenWeatherDailyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY, this.units);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public MeasurementUnits getUnitsOfMeasurement() throws RemoteException {
        return this.units;
    }

    @Override
    public void setUnitsOfMeasurement(MeasurementUnits units) throws RemoteException {
        if (units == null) {
            throw new IllegalArgumentException(UNITS_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }

        this.units = units;
    }

}
