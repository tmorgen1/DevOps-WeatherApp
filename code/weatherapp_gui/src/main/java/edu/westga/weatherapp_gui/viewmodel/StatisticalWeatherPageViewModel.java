package edu.westga.weatherapp_gui.viewmodel;

import java.rmi.Naming;

import org.json.JSONObject;

import edu.westga.weatherapp_shared.interfaces.StatisticalWeatherDataRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * This class is the ViewModel for the Statistical Weather Page.
 * 
 * @author Michael Pavich
 */
public class StatisticalWeatherPageViewModel {

    /**
     * The null weather data error message
     */
    private static final String NULL_WEATHER_DATA_ERROR_MESSAGE = "Weather data cannot be null";

    /**
     * The statistical weather data retriever
     */
    private StatisticalWeatherDataRetriever statisticalWeatherDataRetriever;

    /**
     * The current statistical weather data
     */
    private JSONObject statisticalWeatherData;
    
    /**
     * Constructor for StatisticalWeatherPageViewModel that connects to the statistical weather data service
     * 
     * @param statisticalWeatherDataRetriever - the statistical weather data retriever
     */
    public StatisticalWeatherPageViewModel(StatisticalWeatherDataRetriever statisticalWeatherDataRetriever) {
        if (statisticalWeatherDataRetriever != null) {
            this.statisticalWeatherDataRetriever = statisticalWeatherDataRetriever;
        } else {
            try {
                this.statisticalWeatherDataRetriever = (StatisticalWeatherDataRetriever) Naming.lookup("rmi://localhost:5000/statistical-weather");
            } catch (Exception exception) {
                System.err.println("Error looking up java rmi binding");
            }
        }
    }

    /**
     * Gets the statistical weather data for the given location and month
     * 
     * @param weatherLocation - the location to get the weather data for
     * @param month - the month to get the data for
     * @return the statistical weather data
     */
    public JSONObject getStatisticalWeatherDataByWeatherLocation(WeatherLocation weatherLocation, int month) {
        if (weatherLocation == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        double latitude = weatherLocation.getLatitude();
        double longitude = weatherLocation.getLongitude();

        try {
            this.statisticalWeatherData = new JSONObject(this.statisticalWeatherDataRetriever.getDataByCoordinates(latitude, longitude, month));
            return this.statisticalWeatherData;
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            return null;
        }
    }

    /**
     * Gets the record min temperature for the given month from the statistical weather data
     * 
     * @return the record min temperature
     */
    public double getRecordMinTemperature() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("temp").getDouble("record_min");
    }

    /**
     * Gets the record max temperature for the given month from the statistical weather data
     * 
     * @return the record max temperature
     */
    public double getRecordMaxTemperature() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("temp").getDouble("record_max");
    }

    /**
     * Gets the average min temperature for the given month from the statistical weather data
     * 
     * @return the average min temperature
     */
    public double getAverageMinTemperature() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("temp").getDouble("average_min");
    }

    /**
     * Gets the average max temperature for the given month from the statistical weather data
     * 
     * @return the average max temperature
     */
    public double getAverageMaxTemperature() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("temp").getDouble("average_max");
    }

    /**
     * Gets the average temperature for the given month from the statistical weather data
     * 
     * @return the average temperature
     */
    public double getAverageTemperature() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("temp").getDouble("mean");
    }

    /**
     * Gets the record min pressure for the given month from the statistical weather data
     * 
     * @return the record min pressure
     */
    public double getRecordMinPressure() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("pressure").getDouble("min");
    }

    /**
     * Gets the record max pressure for the given month from the statistical weather data
     * 
     * @return the record max pressure
     */
    public double getRecordMaxPressure() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("pressure").getDouble("max");
    }

    /**
     * Gets the median pressure for the given month from the statistical weather data
     * 
     * @return the median pressure
     */
    public double getMedianPressure() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("pressure").getDouble("median");
    }

    /**
     * Gets the average pressure for the given month from the statistical weather data
     * 
     * @return the average pressure
     */
    public double getAveragePressure() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("pressure").getDouble("mean");
    }

    /**
     * Gets the record max humidity for the given month from the statistical weather data
     * 
     * @return the record max humidity
     */
    public double getRecordMaxHumidity() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("humidity").getDouble("max");
    }

    /**
     * Gets the record min humidity for the given month from the statistical weather data
     * 
     * @return the record min humidity
     */
    public double getRecordMinHumidity() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("humidity").getDouble("min");
    }

    /**
     * Gets the median humidity for the given month from the statistical weather data
     * 
     * @return the median humidity
     */
    public double getMedianHumidity() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("humidity").getDouble("median");
    }

    /**
     * Gets the average humidity for the given month from the statistical weather data
     * 
     * @return the average humidity
     */
    public double getAverageHumidity() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("humidity").getDouble("mean");
    }

    /**
     * Gets the record max wind speed for the given month from the statistical weather data
     * 
     * @return the record max wind speed
     */
    public double getRecordMaxWind() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("wind").getDouble("max");
    }

    /**
     * Gets the record min wind speed for the given month from the statistical weather data
     * 
     * @return the record min wind speed
     */
    public double getRecordMinWind() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("wind").getDouble("min");
    }

    /**
     * Gets the median wind speed for the given month from the statistical weather data
     * 
     * @return the median wind speed
     */
    public double getMedianWind() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("wind").getDouble("median");
    }

    /**
     * Gets the average wind speed for the given month from the statistical weather data
     * 
     * @return the average wind speed
     */
    public double getAverageWind() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("wind").getDouble("mean");
    }

    /**
     * Gets the record max precipitation for the given month from the statistical weather data
     * 
     * @return the record max precipitation
     */
    public double getRecordMaxPrecipitation() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("precipitation").getDouble("max");
    }

    /**
     * Gets the record min precipitation for the given month from the statistical weather data
     * 
     * @return the record min precipitation
     */
    public double getRecordMinPrecipitation() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("precipitation").getDouble("min");
    }

    /**
     * Gets the median precipitation for the given month from the statistical weather data
     * 
     * @return the median precipitation
     */
    public double getMedianPrecipitation() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("precipitation").getDouble("median");
    }

    /**
     * Gets the average precipitation for the given month from the statistical weather data
     * 
     * @return the average precipitation
     */
    public double getAveragePrecipitation() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("precipitation").getDouble("mean");
    }

    /**
     * Gets the record max cloud coverage for the given month from the statistical weather data
     * 
     * @return the record max cloud coverage
     */
    public double getRecordMaxClouds() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("clouds").getDouble("max");
    }

    /**
     * Gets the record min cloud coverage for the given month from the statistical weather data
     * 
     * @return the record min cloud coverage
     */
    public double getRecordMinClouds() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("clouds").getDouble("min");
    }

    /**
     * Gets the median cloud coverage for the given month from the statistical weather data
     * 
     * @return the median cloud coverage
     */
    public double getMedianClouds() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("clouds").getDouble("median");
    }

    /**
     * Gets the average cloud coverage for the given month from the statistical weather data
     * 
     * @return the average cloud coverage
     */
    public double getAverageClouds() {
        if (this.statisticalWeatherData == null) {
            throw new IllegalArgumentException(NULL_WEATHER_DATA_ERROR_MESSAGE);
        }

        return this.statisticalWeatherData.getJSONObject("result").getJSONObject("clouds").getDouble("mean");
    }
}
