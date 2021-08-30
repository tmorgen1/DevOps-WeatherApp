package edu.westga.weatherapp_service;

import java.rmi.Naming;
import java.rmi.RemoteException;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;
import edu.westga.weatherapp_shared.WeatherDataRetriever;

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
        try {
            WeatherDataRetriever skeleton = new OpenWeatherCurrentDataRetriever();
            Naming.rebind("rmi://localhost:5000/current-weather", skeleton);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
