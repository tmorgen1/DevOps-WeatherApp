package edu.westga.weatherapp_gui.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.IpGrabber;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.interfaces.MapRadarDataRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Defines the weather radar page view
 */
public class WeatherRadarPage {

    /**
     * The weather radar web view
     */
    @FXML
    private WebView weatherRadarWebView;

    /**
     * The back arrow image view
     */
    @FXML
    private ImageView backArrowImageView;

    /**
     * The map radar data retriever
     */
    private MapRadarDataRetriever mapRadarDataRetriever;

    /**
     * The weather location searcher
     */
    private LocationSearcher weatherLocationSearcher;

    /**
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        try {
            this.mapRadarDataRetriever = (MapRadarDataRetriever) Naming.lookup("rmi://localhost:5000/radar-weather");
            this.weatherLocationSearcher = (LocationSearcher) Naming.lookup("rmi://localhost:5000/location-searcher");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.err.println("Error looking up java rmi binding");
        }

        this.loadWeatherRadarMap();
    }

    /**
     * Goes back to the landing page view
     * 
     * @param event - the
     */
    @FXML
    void onBackArrowClicked(MouseEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowGenerator.changeScene(currentStage, App.LANDING_PAGE_VIEW, App.LANDING_PAGE_TITLE);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error switching scenes");
        }
    }

    /**
     * Fetches the weather radar map html and loads it in the weather radar web view
     */
    private void loadWeatherRadarMap() {
        try {
            WeatherLocation currentLocation = this.GetCurrentLocation();
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            String latLonString = latitude + ", " + longitude;
            String weatherRadarHtmlString = this.mapRadarDataRetriever.GetMapRadarHTML().replace("{lat}, {lon}", latLonString);
            this.weatherRadarWebView.getEngine().loadContent(weatherRadarHtmlString);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the current location of the user based on their ip address
     * 
     * @return a weather location of the user's current location
     */
    public WeatherLocation GetCurrentLocation() {
        try {
            String ip = IpGrabber.GetCurrentIpAddress();
            WeatherLocation currentLocation = this.weatherLocationSearcher.getLocationByIP(ip);
            
            return currentLocation;
        } catch (RemoteException e) {
            return null;
        }
    }

}
