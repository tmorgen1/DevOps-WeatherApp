package edu.westga.weatherapp_service.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import org.json.JSONObject;
import edu.westga.weatherapp_service.enums.MeasurementUnits;

/**
 * This weather data retriever uses the OpenWeather API to obtain weather data.  Also extends the UnicastRemoteObject
 * to allow for Remote Method Invocation.
 */
public class OpenWeatherCurrentDataRetriever extends UnicastRemoteObject implements WeatherDataRetriever {

    /**
     *OpenWeather API Base call.
     */
    private static final String CURRENT_WEATHER_API_CALL_BASE = "https://pro.openweathermap.org/data/2.5/weather?appid=";

    /**
     * OpenWeather API key for developer use.
     */
    private static final String API_KEY = "664db12c121ec6f39b98db6040a42f2c";

    /**
     * The unit of measurement for api calls.
     */
    private MeasurementUnits units;

    /**
     * Creates an OpenWeather data retriever.  Defaults to imperial unit system.
     * 
     * @throws RemoteException
     */
    public OpenWeatherCurrentDataRetriever() throws RemoteException {
        super();
        this.units = MeasurementUnits.Imperial;
    }

    /**
     * Creates an OpenWeather data retriever.  Sets the unit system to what is provided.
     * 
     * @param units - the unit of measurement system
     * @throws RemoteException
     */
    public OpenWeatherCurrentDataRetriever(MeasurementUnits units) throws RemoteException {
        super();
        this.units = units;
    }

    @Override
    public JSONObject GetDataByCity(String cityName) throws RemoteException {
        URL apiCall = this.getAPICallURL("&q=" + cityName);
        return this.GetData(apiCall); 
    }

    @Override
    public JSONObject GetDataByCityAndStateCode(String cityName, String stateCode) throws RemoteException {
        URL apiCall = this.getAPICallURL("&q=" + cityName + "," + stateCode);
        return this.GetData(apiCall); 
    }

    @Override
    public JSONObject GetDataByCityAndStateCodeAndCountryCode(String cityName, String stateCode, String countryCode) throws RemoteException {
        URL apiCall = this.getAPICallURL("&q=" + cityName + "," + stateCode + "," + countryCode);
        return this.GetData(apiCall);
    }

    /**
     * Retrieves the http page content from the api call and translates it to a JSONObject.
     * 
     * @param apiCall - URL for the api call
     * @return a JSONObject containing the weather data
     */
    private JSONObject GetData(URL apiCall) {
        try {
            StringBuffer jsonBuffer = new StringBuffer();
            try (Scanner scanner = new Scanner(apiCall.openStream())) {
                while (scanner.hasNext()) {
                    jsonBuffer.append(scanner.next());
                }
            }
            
            JSONObject jsonObject = new JSONObject(jsonBuffer.toString());
            
            return jsonObject;
        } catch (IOException exception) {
            // TODO: Handle exception
            System.err.println(exception.getMessage());
        }
        
        return null;
    }

    /**
     * Concatinates the api call command and unit of meaurement into a URL.
     * 
     * @param apiCallCommand - api call command
     * @return URL for the api call
     */
    private URL getAPICallURL(String apiCallCommand) {
        String unitTypeAppendString = null;

        if (this.units == MeasurementUnits.Kelvin) {
            unitTypeAppendString = "";
        } else if (this.units == MeasurementUnits.Metric) {
            unitTypeAppendString = "&units=metric";
        } else if (this.units == MeasurementUnits.Imperial) {
            unitTypeAppendString = "&units=imperial";
        }
        
        try {
            return new URL(OpenWeatherCurrentDataRetriever.CURRENT_WEATHER_API_CALL_BASE + OpenWeatherCurrentDataRetriever.API_KEY + unitTypeAppendString + apiCallCommand);
        } catch (MalformedURLException exception) {
            // TODO: Handle exception
            System.err.println(exception.getMessage());
        }

        return null;
    }
    
}
