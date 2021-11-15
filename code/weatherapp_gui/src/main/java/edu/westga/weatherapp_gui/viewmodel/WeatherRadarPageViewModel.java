package edu.westga.weatherapp_gui.viewmodel;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_shared.interfaces.MapRadarDataRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * Defines the weather radar page view model
 * 
 * @author Michael Pavich
 */
public class WeatherRadarPageViewModel {

    /**
     * The map radar data retriver
     */
    private MapRadarDataRetriever mapRadarDataRetriever;

    /**
     * Creates an instance of the weather radar page viewmodel and connects to the
     * java rmi data
     * 
     * @param mapRadarDataRetriever - the map radar data retriever
     */
    public WeatherRadarPageViewModel(MapRadarDataRetriever mapRadarDataRetriever) {
        if (mapRadarDataRetriever != null) {
            this.mapRadarDataRetriever = mapRadarDataRetriever;
        } else {
            try {
                this.mapRadarDataRetriever = (MapRadarDataRetriever) Naming
                        .lookup("rmi://localhost:5000/radar-weather");
            } catch (MalformedURLException | RemoteException | NotBoundException e) {
                System.err.println("Error looking up java rmi binding");
            }
        }
    }

    /**
     * Fetches the weather radar map html and loads it in the weather radar web view
     * 
     * @return the html for the weather radar map
     */
    public String loadWeatherRadarMap() {
        try {
            WeatherLocation currentLocation = CurrentWeatherInformation.getUserLocation();
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            String latLonString = latitude + ", " + longitude;
            return this.mapRadarDataRetriever.getMapRadarHTML().replace("{lat}, {lon}", latLonString);
        } catch (RemoteException e) {
            return null;
        }
    }
}
