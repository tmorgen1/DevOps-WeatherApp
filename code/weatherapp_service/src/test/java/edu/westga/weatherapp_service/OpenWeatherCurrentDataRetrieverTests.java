package edu.westga.weatherapp_service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.rmi.RemoteException;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_service.enums.MeasurementUnits;
import edu.westga.weatherapp_service.mocks.MockDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;

/**
 * Unit test for the OpenWeatherCurrentDataRetriever class.
 */
public class OpenWeatherCurrentDataRetrieverTests 
{
    @Test
    public void oneParamConstructorShouldSetUnitsToImperial()
    {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertTrue(retriever.getUnitsOfMeasurement() == MeasurementUnits.Imperial);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void parameterizedConstructorShouldSetUnits() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever(), MeasurementUnits.Metric);
            assertTrue(retriever.getUnitsOfMeasurement() == MeasurementUnits.Metric);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void parameterizedConstructorThrowsExceptionWithNullRetriever() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherCurrentDataRetriever(null);
        });
    }

    @Test
    public void parameterizedConstructorThrowsExceptionWithNullUnits() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherCurrentDataRetriever(new MockDataRetriever(), null);
        });
    }

    @Test
    public void getDataByCityThrowsExceptionOnNullCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever(), MeasurementUnits.Kelvin);
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCity(null);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCity("");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCode(null, "GA");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCode("", "GA");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullStateCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCode("Newnan", null);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnEmptyStateCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCode("Newnan", "");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode(null, "GA", "US");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("", "GA", "US");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullStateCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("Newnan", null, "US");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyStateCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("Newnan", "", "US");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCountryCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("Newnan", "GA", null);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyCountryCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCitySuccessfully() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.GetDataByCity("newnan"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeSuccessfully() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.GetDataByCityAndStateCode("newnan", "GA"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeSuccessfully() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.GetDataByCityAndStateCodeAndCountryCode("newnan", "GA", "USA"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }
}
