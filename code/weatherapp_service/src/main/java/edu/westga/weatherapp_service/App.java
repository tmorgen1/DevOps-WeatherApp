package edu.westga.weatherapp_service;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherIconRetriever;
import edu.westga.weatherapp_shared.WeatherDataRetriever;
import edu.westga.weatherapp_shared.WeatherIconRetriever;

/**
 * Main class for the service provider for Group 6 DevOps Weather App.
 */
public class App 
{
    /**
     * RMI Registry Port.
     */
    private static final int RMI_PORT = 5000;

    /**
     * Entry point of the program.
     * 
     * @param args
     * @throws RemoteException
     */
    public static void main( String[] args ) throws RemoteException
    {
        LocateRegistry.createRegistry(App.RMI_PORT);
        
        try {
            WeatherDataRetriever currentWeatherSkeleton = new OpenWeatherCurrentDataRetriever();
            Naming.rebind("rmi://localhost:5000/current-weather", currentWeatherSkeleton);

            WeatherIconRetriever weatherIconSkeleton = new OpenWeatherIconRetriever();
            Naming.rebind("rmi://localhost:5000/weather-icons", weatherIconSkeleton);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
