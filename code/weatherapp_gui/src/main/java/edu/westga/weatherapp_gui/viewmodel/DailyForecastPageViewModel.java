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

    /**
     * Creates a new instance of the daily forecast page view model. Binds to java
     * rmi if no data retrievers specified.
     * 
     * @param dataRetriever - the daily weather data retriever
     * @param iconRetriever - the weather icon data retriever
     */
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
     * @return the daily weather json data
     */
    public JSONObject GetWeatherDataByCity(String city, int days) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }

        try {
            this.dailyWeatherData = new JSONObject(this.dailyWeatherDataRetriever.GetDataByCity(city, days));
            return this.dailyWeatherData;
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets the weather icon for the specified day
     * 
     * @param day - the day index
     * @return the weather icon
     */
    public String GetDayWeatherIcon(int day) {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException("No daily weather data");
        }

        try {
            Object icon = this.dailyWeatherData.getJSONArray("list").getJSONObject(day).getJSONArray("weather")
                    .getJSONObject(0).get("icon");
            String iconString = String.valueOf(icon);

            return this.weatherIconRetriever.GetWeatherIconUrlByIconId(iconString);
        } catch (RemoteException exception) {
            System.err.println("Remote Exception: Error retrieving weather icon url by icon id");
            return null;
        }
    }

    /**
     * Gets the max temperature for the specified day
     * 
     * @param day - the day index
     * @return the max temperature
     */
    public String GetDayMaxTemperature(int day) {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException("No daily weather data");
        }

        Long temperature = Math.round(
                this.dailyWeatherData.getJSONArray("list").getJSONObject(day).getJSONObject("temp").getDouble("max"));
        return String.valueOf(temperature);
    }

    /**
     * Gets the min temperature for the specified day
     * 
     * @param day - the day index
     * @return the min temperature
     */
    public String GetDayMinTemperature(int day) {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException("No daily weather data");
        }

        Long temperature = Math.round(
                this.dailyWeatherData.getJSONArray("list").getJSONObject(day).getJSONObject("temp").getDouble("min"));
        return String.valueOf(temperature);
    }

    /**
     * Gets the day utc time for the specified day
     * 
     * @param day - the day index
     * @return the day utc time
     */
    public Long GetDayUtcDateTime(int day) {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException("No daily weather data");
        }

        return Math.round(this.dailyWeatherData.getJSONArray("list").getJSONObject(day).getDouble("dt"));
    }

    /**
     * Gets the timezone for the searched location
     * 
     * @return the timezone
     */
    public Long GetTimezone() {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException("No daily weather data");
        }

        return Math.round(this.dailyWeatherData.getJSONObject("city").getDouble("timezone"));
    }
}
