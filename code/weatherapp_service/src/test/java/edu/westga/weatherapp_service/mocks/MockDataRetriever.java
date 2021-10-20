package edu.westga.weatherapp_service.mocks;

import java.net.MalformedURLException;
import java.net.URL;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;

public class MockDataRetriever implements DataRetriever {

    @Override
    public String getData(URL apiCall) {
        return "{\"someKey\":\"someValue\"}";
    }

    @Override
    public URL getServiceAPICallURL(String apiCallCommand, String apiCallBase, String apiKey, MeasurementUnits units) {
        URL url = null;
        try {
            url = new URL("http://localhost");
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL Exception: API call could not be generated");
        }

        return url;
    }
    
}
