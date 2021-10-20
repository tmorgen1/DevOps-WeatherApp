package edu.westga.weatherapp_service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.rmi.RemoteException;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_service.mocks.MockDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherHourlyDataRetriever;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;

public class OpenWeatherHourlyDataRetrieverTests {
    
    @Test
    public void parameterizedConstructorShouldThrowExceptionOnNullDataRetriever() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherHourlyDataRetriever(null, MeasurementUnits.Imperial);
        });
    }
    
    @Test
    public void oneParamConstructorShouldSetUnitsToImperial() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertTrue(retriever.getUnitsOfMeasurement() == MeasurementUnits.Imperial);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void parameterizedConstructorShouldSetUnits() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever(), MeasurementUnits.Metric);
            assertTrue(retriever.getUnitsOfMeasurement() == MeasurementUnits.Metric);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void parameterizedConstructorThrowsExceptionWithNullRetriever() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherHourlyDataRetriever(null);
        });
    }

    @Test
    public void parameterizedConstructorThrowsExceptionWithNullUnits() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherHourlyDataRetriever(new MockDataRetriever(), null);
        });
    }

    @Test
    public void getDataByCityThrowsExceptionOnNullCity() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever(), MeasurementUnits.Kelvin);
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity(null, 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity("", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode(null, "GA", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("", "GA", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullStateCode() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("Newnan", null, 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnEmptyStateCode() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("Newnan", "", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode(null, "GA", "US", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("", "GA", "US", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullStateCode() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", null, "US", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyStateCode() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "", "US", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCountryCode() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", null, 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyCountryCode() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCitySuccessfully() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCity("newnan", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeSuccessfully() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndStateCode("newnan", "GA", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeSuccessfully() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndStateCodeAndCountryCode("newnan", "GA", "USA", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void setUnitsOfMeasurementThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherHourlyDataRetriever(new MockDataRetriever()).setUnitsOfMeasurement(null);
        });
    }

    @Test
    public void setUnitsOfMeasurementSuccessfully() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            retriever.setUnitsOfMeasurement(MeasurementUnits.Metric);
            assertEquals(MeasurementUnits.Metric, retriever.getUnitsOfMeasurement());
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnNullCityName() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode(null, "countryCode", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnNullCountryCode() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("cityName", null, 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnEmptyCityName() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("", "countryCode", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnEmptyCountryCode() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("cityName", "", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeSuccessfully() {
        try {
            OpenWeatherHourlyDataRetriever retriever = new OpenWeatherHourlyDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndCountryCode("newnan", "US", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

}
