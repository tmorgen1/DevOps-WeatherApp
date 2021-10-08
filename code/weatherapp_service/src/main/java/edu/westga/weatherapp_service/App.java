package edu.westga.weatherapp_service;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import edu.westga.weatherapp_service.model.APIDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherDailyDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherHourlyDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherIconRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherSevereWarningsRetriever;
import edu.westga.weatherapp_service.model.WeatherLocationSearcher;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.DailyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.HourlyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.interfaces.SevereWeatherWarningsRetriever;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

/**
 * Main class for the service provider for Group 6 DevOps Weather App.
 */
public class App 
{
    /**
     * Weather Locations Json File Name
     */
    public static final String WEATHER_LOCATIONS_FILE_NAME = "city.list.json";

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
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/current-weather", currentWeatherSkeleton);

            WeatherIconRetriever weatherIconSkeleton = new OpenWeatherIconRetriever();
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/weather-icons", weatherIconSkeleton);

            DailyWeatherDataRetriever dailyWeatherSkeleton = new OpenWeatherDailyDataRetriever(new APIDataRetriever());
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/daily-weather", dailyWeatherSkeleton);

            HourlyWeatherDataRetriever hourlyWeatherSkeleton = new OpenWeatherHourlyDataRetriever(new APIDataRetriever());
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/hourly-weather", hourlyWeatherSkeleton);

            SevereWeatherWarningsRetriever severeWarningsSkeleton = new OpenWeatherSevereWarningsRetriever(new APIDataRetriever());
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/severe-warnings", severeWarningsSkeleton);

            LocationSearcher weatherLocationSearcherSkeleton = new WeatherLocationSearcher(App.WEATHER_LOCATIONS_FILE_NAME);
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/location-searcher", weatherLocationSearcherSkeleton);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
