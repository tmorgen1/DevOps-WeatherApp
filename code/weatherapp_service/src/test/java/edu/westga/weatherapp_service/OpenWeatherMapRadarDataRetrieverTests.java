package edu.westga.weatherapp_service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.rmi.RemoteException;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_service.model.OpenWeatherMapRadarDataRetriever;

public class OpenWeatherMapRadarDataRetrieverTests {
    
    @Test
    public void constructorInstantiationNotNullButWithNonExistingFiles() {
        try {
            assertNotNull(new OpenWeatherMapRadarDataRetriever("file-that-does-not-exist"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote exception while testing");
        }
    }

    @Test
    public void constructorInstantiatesSuccessfully() {
        try {
            assertNotNull(new OpenWeatherMapRadarDataRetriever("web_radar_index.html"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote exception while testing");
        }
    }

    @Test
    public void getMapRadarHTMLReturnsNonNullStringAfterRead() {
        try {
            assertNotNull(new OpenWeatherMapRadarDataRetriever("web_radar_index.html").getMapRadarHTML());
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote exception while testing");
        }
    }

}
