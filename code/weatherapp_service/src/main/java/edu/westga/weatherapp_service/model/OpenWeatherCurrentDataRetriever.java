package edu.westga.weatherapp_service.model;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import edu.westga.weatherapp_service.resources.ServiceConstants;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;

/**
 * This weather data retriever uses the OpenWeather API to obtain current weather data.
 * Also extends the UnicastRemoteObject to allow for Remote Method Invocation.
 * 
 * @author Thomas Morgenstern
 */
public class OpenWeatherCurrentDataRetriever extends UnicastRemoteObject implements CurrentWeatherDataRetriever {

    /**
     * Country code should not be empty error message.
     */
    private static final String COUNTRY_CODE_SHOULD_NOT_BE_EMPTY = "countryCode should not be empty";

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
     * Units should not be null error message.
     */
    private static final String UNITS_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "units should not be null";

    /**
     * OpenWeather API Base call.
     */
    private static final String OPEN_WEATHER_API_CALL_BASE = "https://pro.openweathermap.org/data/2.5/weather?appid=";

    /**
     * The unit of measurement for api calls.
     */
    private MeasurementUnits units;

    /**
     * The object that handles data retrieval from the web calls.
     */
    private DataRetriever dataRetriever;

    /**
     * Creates an OpenWeather data retriever. Defaults to imperial units of
     * measurement.
     * 
     * @param retriever - the object to handle web url calls
     * @throws RemoteException - exception in the event of an RMI error
     */
    public OpenWeatherCurrentDataRetriever(DataRetriever retriever) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException(API_DATA_RETRIEVER_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }

        this.dataRetriever = retriever;
        this.units = MeasurementUnits.Imperial;
    }

    /**
     * Creates an OpenWeather data retriever. Sets the units of measurement to what
     * is provided.
     * 
     * @param retriever - the object to handle web url calls
     * @param units     - the units of measurement
     * @throws RemoteException - exception in the event of an RMI error
     */
    public OpenWeatherCurrentDataRetriever(DataRetriever retriever, MeasurementUnits units) throws RemoteException {
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
    public String getDataByCity(String cityName) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException(CITY_NAME_SHOULD_NOT_BE_EMPTY_ERROR_MESSAGE);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL("&q=" + cityName,
                OpenWeatherCurrentDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndStateCode(String cityName, String stateCode) throws RemoteException {
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

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL("&q=" + cityName + "," + stateCode,
                OpenWeatherCurrentDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndCountryCode(String cityName, String countryCode) throws RemoteException {
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
            throw new IllegalArgumentException(COUNTRY_CODE_SHOULD_NOT_BE_EMPTY);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL("&q=" + cityName + "," + countryCode,
                OpenWeatherCurrentDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.getData(apiCall);
    }

    @Override
    public String getDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode)
            throws RemoteException {
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
            throw new IllegalArgumentException(COUNTRY_CODE_SHOULD_NOT_BE_EMPTY);
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.getServiceAPICallURL("&q=" + cityName + "," + stateCode + "," + countryCode,
                OpenWeatherCurrentDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
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
