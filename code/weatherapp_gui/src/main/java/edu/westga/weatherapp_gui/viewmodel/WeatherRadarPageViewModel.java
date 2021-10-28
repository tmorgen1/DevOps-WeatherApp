package edu.westga.weatherapp_gui.viewmodel;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.westga.weatherapp_gui.model.IpGrabber;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
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
     * The weather location searcher
     */
    private LocationSearcher locationSearcher;

    /**
     * Creates an instance of the weather radar page viewmodel and connects to the
     * java rmi data
     * 
     * @param mapRadarDataRetriever - the map radar data retriever
     * @param locationSearcher      - the location searcher
     */
    public WeatherRadarPageViewModel(MapRadarDataRetriever mapRadarDataRetriever, LocationSearcher locationSearcher) {
        if (mapRadarDataRetriever != null && locationSearcher != null) {
            this.mapRadarDataRetriever = mapRadarDataRetriever;
            this.locationSearcher = locationSearcher;
        } else {
            try {
                this.mapRadarDataRetriever = (MapRadarDataRetriever) Naming
                        .lookup("rmi://localhost:5000/radar-weather");
                this.locationSearcher = (LocationSearcher) Naming.lookup("rmi://localhost:5000/location-searcher");
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
            WeatherLocation currentLocation = this.getCurrentLocation();
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            String latLonString = latitude + ", " + longitude;
            return this.mapRadarDataRetriever.getMapRadarHTML().replace("{lat}, {lon}", latLonString);
        } catch (RemoteException e) {
            return null;
        }
    }

    /**
     * Gets the current location of the user based on their ip address
     * 
     * @throws RemoteException - in the event of a RMI exception
     * @return a weather location of the user's current location
     */
    private WeatherLocation getCurrentLocation() throws RemoteException {
        String ip = IpGrabber.getCurrentIpAddress();
        WeatherLocation currentLocation = this.locationSearcher.getLocationByIP(ip);

        return currentLocation;
    }
}
