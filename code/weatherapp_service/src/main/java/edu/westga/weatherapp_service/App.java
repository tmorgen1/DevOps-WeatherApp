package edu.westga.weatherapp_service;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import edu.westga.weatherapp_service.model.APIDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherDailyDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherIconRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherSevereWarningsRetriever;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.DailyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.SevereWeatherWarningsRetriever;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

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
            CurrentWeatherDataRetriever currentWeatherSkeleton = new OpenWeatherCurrentDataRetriever(new APIDataRetriever());
            Naming.rebind("rmi://localhost:5000/current-weather", currentWeatherSkeleton);

            WeatherIconRetriever weatherIconSkeleton = new OpenWeatherIconRetriever();
            Naming.rebind("rmi://localhost:5000/weather-icons", weatherIconSkeleton);

            DailyWeatherDataRetriever dailyWeatherSkeleton = new OpenWeatherDailyDataRetriever(new APIDataRetriever());
            Naming.rebind("rmi://localhost:5000/daily-weather", dailyWeatherSkeleton);

            SevereWeatherWarningsRetriever severeWarningsSkeleton = new OpenWeatherSevereWarningsRetriever(new APIDataRetriever());
            Naming.rebind("rmi:/" + "/localhost:5000/severe-warnings", severeWarningsSkeleton);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
