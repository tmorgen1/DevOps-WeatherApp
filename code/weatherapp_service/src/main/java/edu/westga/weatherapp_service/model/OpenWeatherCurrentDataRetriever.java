package edu.westga.weatherapp_service.model;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;

/**
 * This weather data retriever uses the OpenWeather API to obtain weather data.
 * Also extends the UnicastRemoteObject to allow for Remote Method Invocation.
 */
public class OpenWeatherCurrentDataRetriever extends UnicastRemoteObject implements CurrentWeatherDataRetriever {

    /**
     * OpenWeather API Base call.
     */
    private static final String OPEN_WEATHER_API_CALL_BASE = "https://pro.openweathermap.org/data/2.5/weather?appid=";

    /**
     * OpenWeather API key for developer use.
     */
    private static final String API_KEY = "664db12c121ec6f39b98db6040a42f2c";

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
     * @throws RemoteException
     */
    public OpenWeatherCurrentDataRetriever(DataRetriever retriever) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException("APIDataRetriever should not be null");
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
     * @throws RemoteException
     */
    public OpenWeatherCurrentDataRetriever(DataRetriever retriever, MeasurementUnits units) throws RemoteException {
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
    public String GetDataByCity(String cityName) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException("cityName should not be null");
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException("cityName should not be empty");
        }

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName,
                OpenWeatherCurrentDataRetriever.OPEN_WEATHER_API_CALL_BASE, OpenWeatherCurrentDataRetriever.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndStateCode(String cityName, String stateCode) throws RemoteException {
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

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + stateCode,
                OpenWeatherCurrentDataRetriever.OPEN_WEATHER_API_CALL_BASE, OpenWeatherCurrentDataRetriever.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndCountryCode(String cityName, String countryCode) throws RemoteException {
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

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + countryCode,
                OpenWeatherCurrentDataRetriever.OPEN_WEATHER_API_CALL_BASE, OpenWeatherCurrentDataRetriever.API_KEY,
                this.units);
        return this.dataRetriever.GetData(apiCall);
    }

    @Override
    public String GetDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode)
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

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + stateCode + "," + countryCode,
                OpenWeatherCurrentDataRetriever.OPEN_WEATHER_API_CALL_BASE, OpenWeatherCurrentDataRetriever.API_KEY,
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
