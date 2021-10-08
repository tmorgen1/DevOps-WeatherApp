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

public class OpenWeatherDailyDataRetriever extends UnicastRemoteObject implements DailyWeatherDataRetriever {

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
     * @throws RemoteException
     */
    public OpenWeatherDailyDataRetriever(DataRetriever retriever) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException("APIDataRetriever should not be null");
        }

        this.dataRetriever = retriever;
        this.units = MeasurementUnits.Imperial;
    }

    /**
     * Creates an OpenWeather data retriever. Provides a daily forecast. Sets the
     * units of measurement to what is provided.
     * 
     * @param units - the units of measurement
     * @throws RemoteException
     */
    public OpenWeatherDailyDataRetriever(DataRetriever retriever, MeasurementUnits units) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException("APIDataRetriever should not be null");
        }
        if (units == null) {
            throw new IllegalArgumentException("units should not be null");
        }

        this.dataRetriever = retriever;
        this.units = units;
    }

    @Override
    public String GetDataByCity(String cityName, int numOfDays) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException("cityName should not be null");
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException("cityName should not be empty");
        }
        if (numOfDays < 1 || numOfDays > 16) {
            throw new IllegalArgumentException("numOfDays should be between 1 and 16, inclusive");
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "&cnt=" + numOfDays,
                OpenWeatherDailyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndStateCode(String cityName, String stateCode, int numOfDays) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException("cityName should not be null");
        }
        if (stateCode == null) {
            throw new IllegalArgumentException("stateCode should not be null");
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException("cityName should not be empty");
        }
        if (stateCode.isEmpty()) {
            throw new IllegalArgumentException("stateCode should not be empty");
        }
        if (numOfDays < 1 || numOfDays > 16) {
            throw new IllegalArgumentException("numOfDays should be between 1 and 16, inclusive");
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + stateCode + "&cnt=" + numOfDays,
                OpenWeatherDailyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndCountryCode(String cityName, String countryCode, int numOfDays)
            throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException("cityName should not be null");
        }
        if (countryCode == null) {
            throw new IllegalArgumentException("countryCode should not be null");
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException("cityName should not be empty");
        }
        if (countryCode.isEmpty()) {
            throw new IllegalArgumentException("countryCode should not be empty");
        }
        if (numOfDays < 1 || numOfDays > 16) {
            throw new IllegalArgumentException("numOfDays should be between 1 and 16, inclusive");
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL(
                "&q=" + cityName + "," + countryCode + "&cnt=" + numOfDays,
                OpenWeatherDailyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode,
            int numOfDays) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException("cityName should not be null");
        }
        if (stateCode == null) {
            throw new IllegalArgumentException("stateCode should not be null");
        }
        if (countryCode == null) {
            throw new IllegalArgumentException("countryCode should not be null");
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException("cityName should not be empty");
        }
        if (stateCode.isEmpty()) {
            throw new IllegalArgumentException("stateCode should not be empty");
        }
        if (countryCode.isEmpty()) {
            throw new IllegalArgumentException("countryCode should not be empty");
        }
        if (numOfDays < 1 || numOfDays > 16) {
            throw new IllegalArgumentException("numOfDays should be between 1 and 16, inclusive");
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL(
                "&q=" + cityName + "," + stateCode + "," + countryCode + "&cnt=" + numOfDays,
                OpenWeatherDailyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public MeasurementUnits getUnitsOfMeasurement() throws RemoteException {
        return this.units;
    }

    @Override
    public void setUnitsOfMeasurement(MeasurementUnits units) throws RemoteException {
        if (units == null) {
            throw new IllegalArgumentException("units should not be null");
        }

        this.units = units;
    }

}
