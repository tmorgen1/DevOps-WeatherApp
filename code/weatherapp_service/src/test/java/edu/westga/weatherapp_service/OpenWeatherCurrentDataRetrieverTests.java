package edu.westga.weatherapp_service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.rmi.RemoteException;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_service.enums.MeasurementUnits;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;

/**
 * Unit test for the OpenWeatherCurrentDataRetriever class.
 */
public class OpenWeatherCurrentDataRetrieverTests 
{
    @Test
    public void defaultConstructorShouldSetUnitsToImperial()
    {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertTrue(retriever.getUnitsOfMeasurement() == MeasurementUnits.Imperial);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parameterizedConstructorShouldSetUnits() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(MeasurementUnits.Metric);
            assertTrue(retriever.getUnitsOfMeasurement() == MeasurementUnits.Metric);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parameterizedConstructorThrowsExceptionWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherCurrentDataRetriever(null);
        });
    }

    @Test
    public void getDataByCityThrowsExceptionOnNullCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever(MeasurementUnits.Kelvin);
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCity(null);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCity("");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCode(null, "GA");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCode("", "GA");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullStateCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCode("Newnan", null);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnEmptyStateCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCode("Newnan", "");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode(null, "GA", "US");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("", "GA", "US");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullStateCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("Newnan", null, "US");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyStateCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("Newnan", "", "US");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCountryCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("Newnan", "GA", null);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyCountryCode() {
        try {
            OpenWeatherCurrentDataRetriever retriever = new OpenWeatherCurrentDataRetriever();
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.GetDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
