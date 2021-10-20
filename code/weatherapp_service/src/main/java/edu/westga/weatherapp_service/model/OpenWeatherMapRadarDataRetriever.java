package edu.westga.weatherapp_service.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.io.IOUtils;
import edu.westga.weatherapp_shared.interfaces.MapRadarDataRetriever;

/**
 * This weather data retriever uses the OpenWeather API, Leaflet JS, and OpenStreetMap to obtain
 * and display a map/radar of weather data.
 * Also extends the UnicastRemoteObject to allow for Remote Method Invocation.
 * 
 * @author Thomas Morgenstern
 */
public class OpenWeatherMapRadarDataRetriever extends UnicastRemoteObject implements MapRadarDataRetriever {

    /**
     * the HTML/JS/CSS for the radar/map web page
     */
    private String radarMapHtml;

    /**
     * Instantiates an OpenWeatherMapRadarDataRetriever object.  Requires the name/filepath to
     * the radar/map.
     * 
     * @param radarMapHTMLFileName - the name or file path to the html page
     * @throws RemoteException - exception in the event of an RMI error
     */
    public OpenWeatherMapRadarDataRetriever(String radarMapHTMLFileName) throws RemoteException {
        super();
        this.radarMapHtml = this.readRadarMapHtmlFile(radarMapHTMLFileName);
    }

    /**
     * Reads the file and returns the contents as a string.  If the file does not exist, returns null.
     * If there is an issue reading the file, returns null.
     * 
     * @param radarMapHTMLFileName - the name or file path to the html page
     * @return the read file as a string, null if there is an error
     */
    private String readRadarMapHtmlFile(String radarMapHTMLFileName) {
        String htmlString = null;

        File radarMapFile = new File(radarMapHTMLFileName);
        if (!radarMapFile.exists()) {
            return null;
        }

        try (InputStream inputStream = new FileInputStream(radarMapFile)) {
            htmlString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            System.err.println("Error while reading from input stream.");
            return null;
        }
        
        return htmlString;
    }

    @Override
    public String getMapRadarHTML() throws RemoteException {
        return this.radarMapHtml;
    }
    
}
