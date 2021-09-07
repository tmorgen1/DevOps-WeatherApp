package edu.westga.weatherapp_service.interfaces;
import java.net.URL;
import edu.westga.weatherapp_service.enums.MeasurementUnits;

public interface DataRetriever {

    /**
     * Retrieves the http page content from the api call and translates it to a
     * String. If there was an error retrieving the data from the service, return
     * null.
     * 
     * @param apiCall - URL for the api call
     * @return a String containing the api call data
     */
    public String GetData(URL apiCall);

    /**
     * Concatinates the api call command and unit of meaurement into a URL. If there
     * was an issue in generating the api call, return null.
     * 
     * @param apiCallCommand - the part of the web call that decides what the api call should do
     * @param apiCallBase - the web address/host for the api call
     * @param apiKey - the api key
     * @param units - the unit of measurement for the call
     * @return URL for the api call
     */
    public URL GetServiceAPICallURL(String apiCallCommand, String apiCallBase, String apiKey, MeasurementUnits units);

}
