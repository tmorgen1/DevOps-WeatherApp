package edu.westga.weatherapp_shared.model;

import java.io.Serializable;

/**
 * This class wraps information regarding a weather location. Each data member
 * should be allowed to be null, because a user may only provide so much
 * information; however, no String can be empty.
 */
public class WeatherLocation implements Serializable {

    /**
     * The name of the city
     */
    private String city;

    /**
     * The country code as of OpenWeather
     */
    private String country;

    /**
     * The state code as of OpenWeather
     */
    private String state;

    /**
     * The longitude of the gps location (x)
     */
    private Double longitude;

    /**
     * The latitude of the gps location (y)
     */
    private Double latitude;

    /**
     * Instantiates a weather location object. None of the strings should be empty,
     * however any object can be null.
     * 
     * @param city      - city name
     * @param country   - country code, as of OpenWeather
     * @param state     - state code, as of OpenWeather
     * @param longitude - gps longitude, (x)
     * @param latitude  - gps latitude, (y)
     */
    public WeatherLocation(String city, String country, String state, Double longitude, Double latitude) {
        if (city != null) {
            if (city.isEmpty()) {
                throw new IllegalArgumentException("city should not be empty");
            }
        }
        if (country != null) {
            if (country.isEmpty()) {
                throw new IllegalArgumentException("country should not be empty");
            }
        }
        if (state != null) {
            if (state.isEmpty()) {
                throw new IllegalArgumentException("state should not be empty");
            }
        }

        this.city = city;
        this.country = country;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Gets the city name.
     * 
     * @return the city name
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Gets the country code
     * 
     * @return the country code
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Gets the state code
     * 
     * @return the state code
     */
    public String getState() {
        return this.state;
    }

    /**
     * Gets the latitude
     * 
     * @return the latitude
     */
    public Double getLatitude() {
        return this.latitude;
    }

    /**
     * Gets the longitude
     * 
     * @return the longitude
     */
    public Double getLongitude() {
        return this.longitude;
    }

    @Override
    public String toString() {
        if (this.state.equals("N/A")) {
            return this.city + " " + this.country + " " + this.longitude + " " + this.latitude;
        }

        return this.city + " " + this.country + " " + this.state + " " + this.longitude + " " + this.latitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        WeatherLocation location = (WeatherLocation) obj;
        if (location.city.equals(this.city) && location.country.equals(this.country)
                && location.state.equals(this.state) && location.latitude.equals(this.latitude)
                && location.longitude.equals(this.longitude)) {
            return true;
        }

        return false;
    }
}
