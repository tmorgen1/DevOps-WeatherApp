package edu.westga.weatherapp_service;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import edu.westga.weatherapp_service.model.APIDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherDailyDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherHourlyDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherIconRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherMapRadarDataRetriever;
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
 * 
 * @author Thomas Morgenstern
 */
public class App {
    /**
     * Weather Locations Json File Name
     */
    public static final String WEATHER_SEARCH_LOCATIONS_FILE_NAME = "city.list.json";

    /**
     * Weather GEOIP Locations Database File Name
     */
    public static final String WEATHER_GEOIP_LOCATIONS_DATABASE_NAME = "GeoLite2-City.mmdb";

    /**
     * Weather Radar Map HTML File Name
     */
    public static final String WEATHER_RADAR_MAP_SITE_FILE_NAME = "web_radar_index.html";

    /**
     * RMI Registry Port.
     */
    private static final int RMI_PORT = 5000;

    /**
     * Entry point of the program.
     * 
     * @param args - unused (arguments for the program)
     * @throws RemoteException - exception in the event of an RMI error
     */
    public static void main(String[] args) throws RemoteException {
        LocateRegistry.createRegistry(App.RMI_PORT);

        try {
            CurrentWeatherDataRetriever currentWeatherSkeleton = new OpenWeatherCurrentDataRetriever(
                    new APIDataRetriever());
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/current-weather", currentWeatherSkeleton);

            WeatherIconRetriever weatherIconSkeleton = new OpenWeatherIconRetriever();
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/weather-icons", weatherIconSkeleton);

            DailyWeatherDataRetriever dailyWeatherSkeleton = new OpenWeatherDailyDataRetriever(new APIDataRetriever());
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/daily-weather", dailyWeatherSkeleton);

            HourlyWeatherDataRetriever hourlyWeatherSkeleton = new OpenWeatherHourlyDataRetriever(
                    new APIDataRetriever());
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/hourly-weather", hourlyWeatherSkeleton);

            SevereWeatherWarningsRetriever severeWarningsSkeleton = new OpenWeatherSevereWarningsRetriever(
                    new APIDataRetriever());
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/severe-warnings", severeWarningsSkeleton);

            LocationSearcher weatherLocationSearcherSkeleton = new WeatherLocationSearcher(
                    App.WEATHER_SEARCH_LOCATIONS_FILE_NAME, App.WEATHER_GEOIP_LOCATIONS_DATABASE_NAME);
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/location-searcher", weatherLocationSearcherSkeleton);

            OpenWeatherMapRadarDataRetriever mapRadarDataRetriever = new OpenWeatherMapRadarDataRetriever(App.WEATHER_RADAR_MAP_SITE_FILE_NAME);
            Naming.rebind("rmi://localhost:" + App.RMI_PORT + "/radar-weather", mapRadarDataRetriever);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
