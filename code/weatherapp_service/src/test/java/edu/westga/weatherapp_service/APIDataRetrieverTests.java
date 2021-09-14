package edu.westga.weatherapp_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.net.URL;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_service.model.APIDataRetriever;

public class APIDataRetrieverTests {
    
    @Test
    public void getDataShouldThrowExceptionOnNullUrl() {
        assertThrows(IllegalArgumentException.class, () -> {
            new APIDataRetriever().GetData(null);
        });
    }

    @Test
    public void getDataShouldReturnDataSuccessfully() {
        APIDataRetriever data = new APIDataRetriever();

        URL url = getClass().getResource("");

        assertNotNull(data.GetData(url));
    }

    @Test
    public void getServiceAPICallURLShouldThrowOnAPICallCommandNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new APIDataRetriever().GetServiceAPICallURL(null, "apiCallBase", "apiKey", MeasurementUnits.Imperial);
        });
    }

    @Test
    public void getServiceAPICallURLShouldThrowOnAPICallBaseNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new APIDataRetriever().GetServiceAPICallURL("apiCallCommand", null, "apiKey", MeasurementUnits.Imperial);
        });
    }

    @Test
    public void getServiceAPICallURLShouldThrowOnAPIKeyCommandNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new APIDataRetriever().GetServiceAPICallURL("apiCallCommand", "apiCallBase", null, MeasurementUnits.Imperial);
        });
    }

    @Test
    public void getServiceAPICallURLShouldThrowOnUnitsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new APIDataRetriever().GetServiceAPICallURL("apiCallCommand", "apiCallBase", "apiKey", null);
        });
    }

    @Test
    public void getServiceAPICallURLSuccessfully() {
        assertNotNull(new APIDataRetriever().GetServiceAPICallURL("", "http://localhost:80/blah=blah", "", MeasurementUnits.Imperial));
    }

}
