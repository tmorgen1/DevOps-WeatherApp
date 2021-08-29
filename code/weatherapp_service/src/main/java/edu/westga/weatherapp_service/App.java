package edu.westga.weatherapp_service;

import java.rmi.RemoteException;
import org.json.JSONObject;
import edu.westga.weatherapp_service.enums.MeasurementUnits;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;
import edu.westga.weatherapp_service.model.WeatherDataRetriever;

/**
 * Main class for the service provider for Group 6 DevOps Weather App.
 */
public class App 
{
    /**
     * Entry point of the program.
     * 
     * @param args
     * @throws RemoteException
     */
    public static void main( String[] args ) throws RemoteException
    {
        // Example of using the weather data retriever.
        WeatherDataRetriever weatherData = new OpenWeatherCurrentDataRetriever(MeasurementUnits.Imperial);
        JSONObject jsonTest = weatherData.GetDataByCityAndStateCodeAndCountryCode("Statesboro", "GA", "US");
        System.out.println(jsonTest.toString());
        System.exit(0);
    }
}
