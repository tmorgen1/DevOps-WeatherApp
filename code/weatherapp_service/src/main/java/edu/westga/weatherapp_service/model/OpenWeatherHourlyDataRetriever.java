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

public class OpenWeatherHourlyDataRetriever extends UnicastRemoteObject implements HourlyWeatherDataRetriever {
    
    private static final String OPEN_WEATHER_API_CALL_BASE = "https://pro.openweathermap.org/data/2.5/forecast/hourly?appid=";

    private MeasurementUnits units;

    private DataRetriever dataRetriever;

    public OpenWeatherHourlyDataRetriever(DataRetriever retriever) throws RemoteException {
        super();
        if (retriever == null) {
            throw new IllegalArgumentException("APIDataRetriever should not be null");
        }

        this.dataRetriever = retriever;
        this.units = MeasurementUnits.Imperial;
    }
    
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
    public String GetDataByCity(String cityName) throws RemoteException {
        if (cityName == null) {
            throw new IllegalArgumentException("cityName should not be null");
        }
        if (cityName.isEmpty()) {
            throw new IllegalArgumentException("cityName should not be empty");
        }

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName,
                OpenWeatherHourlyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
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

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + stateCode,
                OpenWeatherHourlyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
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

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + countryCode,
                OpenWeatherHourlyDataRetriever.OPEN_WEATHER_API_CALL_BASE, ServiceConstants.API_KEY,
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

        cityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);

        URL apiCall = this.dataRetriever.GetServiceAPICallURL("&q=" + cityName + "," + stateCode + "," + countryCode,
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
