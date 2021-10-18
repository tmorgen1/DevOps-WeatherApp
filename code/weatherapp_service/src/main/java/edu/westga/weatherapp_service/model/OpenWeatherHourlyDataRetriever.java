package edu.westga.weatherapp_service.model;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import edu.westga.weatherapp_service.resources.ServiceConstants;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.HourlyWeatherDataRetriever;

/**
 * This weather data retriever uses the OpenWeather API to obtain hourly weather data.
 * Also extends the UnicastRemoteObject to allow for Remote Method Invocation.
 */
public class OpenWeatherHourlyDataRetriever extends UnicastRemoteObject implements HourlyWeatherDataRetriever {
    
    /**
     * OpenWeather API Base call.
     */
    private static final String OPEN_WEATHER_API_CALL_BASE = "https://pro.openweathermap.org/data/2.5/forecast/hourly?appid=";

    /**
     * The unit of measurement for api calls.
     */
    private MeasurementUnits units;

    /**
     * The object that handles data retrieval from the web calls.
     */
    private DataRetriever dataRetriever;

    /**
     * Creates an OpenWeather hourly data retriever. Defaults to imperial units of
     * measurement.
     * 
     * @param retriever - the object to handle web url calls
     * @throws RemoteException
     */
    public OpenWeatherHourlyDataRetriever(DataRetriever retriever) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException("APIDataRetriever should not be null");
        }

        this.dataRetriever = retriever;
        this.units = MeasurementUnits.Imperial;
    }
    
    /**
     * Creates an OpenWeather hourly data retriever. Sets the units of measurement to what
     * is provided.
     * 
     * @param retriever - the object to handle web url calls
     * @param units     - the units of measurement
     * @throws RemoteException
     */
    public OpenWeatherHourlyDataRetriever(DataRetriever retriever, MeasurementUnits units) throws RemoteException {
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
    public String GetDataByCity(String cityName, int numOfHours) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException("cityName should not be null");
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException("cityName should not be empty");
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "&cnt=" + numOfHours,
                OpenWeatherHourlyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndStateCode(String cityName, String stateCode, int numOfHours) throws RemoteException {
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

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + stateCode + "&cnt=" + numOfHours,
                OpenWeatherHourlyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndCountryCode(String cityName, String countryCode, int numOfHours) throws RemoteException {
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

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + countryCode + "&cnt=" + numOfHours,
                OpenWeatherHourlyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode, int numOfHours)
            throws RemoteException {
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

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + stateCode + "," + countryCode + "&cnt=" + numOfHours,
                OpenWeatherHourlyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
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
