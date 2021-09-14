package edu.westga.weatherapp_service.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

/**
 * This weather icon retriever uses the OpenWeather API to obtain weather icons.  Also extends the UnicastRemoteObject
 * to allow for Remote Method Invocation.
 */
public class OpenWeatherIconRetriever extends UnicastRemoteObject implements WeatherIconRetriever {
    
    /**
     * OpenWeather icon image URL base.
     */
    private static final String OPEN_WEATHER_ICON_BASE_URL = "http://openweathermap.org/img/wn/";

    /**
     * OpenWeather icon image URL suffix.
     */
    private static final String OPEN_WEATHER_ICON_SIZE_SUFFIX = "@4x.png";

    /**
     * Creates an OpenWeather icon retriever.
     * 
     * @throws RemoteException
     */
    public OpenWeatherIconRetriever() throws RemoteException {
        super();
    }

    @Override
    public String GetWeatherIconUrlByIconId(String iconId) throws RemoteException {
        if (iconId == null) {
            throw new IllegalArgumentException("Icon Id cannot be null");
        }
        if (iconId.isEmpty()) {
            throw new IllegalArgumentException("Icon Id cannot be empty.");
        }

        String imageURL = OpenWeatherIconRetriever.OPEN_WEATHER_ICON_BASE_URL + iconId + OpenWeatherIconRetriever.OPEN_WEATHER_ICON_SIZE_SUFFIX;
        return imageURL;
    }

}
