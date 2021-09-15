package edu.westga.weatherapp_service.resources;

/**
 * A class to hold the constants that are shared between the classes of the model
 * 
 * @author Eboni Walker, Thomas Morgenstern, Micheal Pavich
 * @version 1.0
 */
public class ServiceConstants {

    public static final String API_KEY = "&appid=664db12c121ec6f39b98db6040a42f2c";
    public static final String LATITUDE_OUT_OF_BOUND = "Invalid Latitude - Latitude must be between -90 and 90 inclusive.";
    public static final String LONGITUDE_OUT_OF_BOUND = "Invalid Longitude - Longitude must be between -180 and 180 inclusive.";
    public static final String INCORRECT_UNITS = "Invalid Units - Units must not be null and be of enum type MeasurementUnits";
}
