package edu.westga.weatherapp_gui.viewmodel;

import java.rmi.Naming;
import java.rmi.RemoteException;

import org.json.JSONObject;

import edu.westga.weatherapp_shared.interfaces.DailyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

/**
 * Defines the daily forecast page view model class and contains all
 * functionality for the daily forecast page view
 */
public class DailyForecastPageViewModel {

    /**
     * The daily weather data retreiver
     */
    private DailyWeatherDataRetriever dailyWeatherDataRetriever;

    /**
     * The weather icon retriever
     */
    private WeatherIconRetriever weatherIconRetriever;

    /**
     * The retrieved daily weather data
     */
    private JSONObject dailyWeatherData;

    public DailyForecastPageViewModel(DailyWeatherDataRetriever dataRetriever, WeatherIconRetriever iconRetriever) {
        if (dataRetriever != null && iconRetriever != null) {
            this.dailyWeatherDataRetriever = dataRetriever;
            this.weatherIconRetriever = iconRetriever;
        } else {
            try {
                this.dailyWeatherDataRetriever = (DailyWeatherDataRetriever) Naming
                        .lookup("rmi://localhost:5000/daily-weather");
                this.weatherIconRetriever = (WeatherIconRetriever) Naming.lookup("rmi://localhost:5000/weather-icons");
            } catch (Exception exception) {
                System.err.println("Error looking up java rmi binding");
            }
        }
    }

    /**
     * Gets the daily weather data for the given amount of days from the weather
     * data retriever by city name
     * 
     * @param city - the name of the city
     * @param days - the number of days to retrieve
     * @return True if successful, false otherwise
     */
    public boolean getWeatherDataByCity(String city, int days) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }

        try {
            this.dailyWeatherData = new JSONObject(this.dailyWeatherDataRetriever.GetDataByCity(city, days));
            return true;
        } catch (RemoteException exception) {
            return false;
        }
    }

    
}
