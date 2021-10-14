package edu.westga.weatherapp_service.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;

public class APIDataRetriever implements DataRetriever {

    @Override
    public String GetData(URL apiCall) {
        if (apiCall == null) {
            throw new IllegalArgumentException("apiCall should not be null");
        }

        String data = null;

        try (Scanner scanner = new Scanner(apiCall.openStream())) {
            StringBuffer jsonBuffer = new StringBuffer();
            scanner.useDelimiter("\\A");
            while (scanner.hasNext()) {
                jsonBuffer.append(scanner.next());
            }

            data = jsonBuffer.toString();
        } catch (IOException exception) {
            System.err.println("IOException: api call data stream could not be opened");
        }

        return data;
    }

    @Override
    public URL GetServiceAPICallURL(String apiCallCommand, String apiCallBase, String apiKey, MeasurementUnits units) {
        if (apiCallCommand == null) {
            throw new IllegalArgumentException("apiCallCommand should not be null");
        }
        if (apiCallBase == null) {
            throw new IllegalArgumentException("apiCallBase should not be null");
        }
        if (apiKey == null) {
            throw new IllegalArgumentException("apiKey should not be null");
        }
        if (units == null) {
            throw new IllegalArgumentException("units should not be null");
        }

        String unitTypeAppendString = "&units=" + units.name().toLowerCase();
        apiCallCommand = apiCallCommand.replace(" ", "");

        URL apiCall = null;
        try {
            URI uri = new URI(apiCallBase + apiKey + unitTypeAppendString + apiCallCommand);
            apiCall = new URL(uri.toASCIIString());
        } catch (MalformedURLException | URISyntaxException exception) {
            System.err.println("Malformed URL Exception: API call could not be generated");
        }

        return apiCall;
    }
}
