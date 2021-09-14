package edu.westga.weatherapp_gui.mocks;

import java.net.MalformedURLException;
import java.net.URL;
import edu.westga.weatherapp_service.enums.MeasurementUnits;
import edu.westga.weatherapp_service.interfaces.DataRetriever;

public class MockDataRetriever implements DataRetriever {

    @Override
    public String GetData(URL apiCall) {
        return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"temp\":{\"max\": 98, \"min\": 90}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";
    }

    @Override
    public URL GetServiceAPICallURL(String apiCallCommand, String apiCallBase, String apiKey, MeasurementUnits units) {
        URL url = null;
        try {
            url = new URL("http://localhost");
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL Exception: API call could not be generated");
        }

        return url;
    }
    
}
