package edu.westga.weatherapp_service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.rmi.RemoteException;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_service.mocks.MockDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherDailyDataRetriever;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;

public class OpenWeatherDailyDataRetrieverTests {
    
    @Test
    public void parameterizedConstructorShouldThrowExceptionOnNullDataRetriever() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherDailyDataRetriever(null, MeasurementUnits.Imperial);
        });
    }
    
    @Test
    public void oneParamConstructorShouldSetUnitsToImperial() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertTrue(retriever.getUnitsOfMeasurement() == MeasurementUnits.Imperial);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void parameterizedConstructorShouldSetUnits() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever(), MeasurementUnits.Metric);
            assertTrue(retriever.getUnitsOfMeasurement() == MeasurementUnits.Metric);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void parameterizedConstructorThrowsExceptionWithNullRetriever() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherDailyDataRetriever(null);
        });
    }

    @Test
    public void parameterizedConstructorThrowsExceptionWithNullUnits() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherDailyDataRetriever(new MockDataRetriever(), null);
        });
    }

    @Test
    public void getDataByCityThrowsExceptionOnNullCity() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever(), MeasurementUnits.Kelvin);
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity(null, 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity("", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnOutOfBoundsCountLower() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity("newnan", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnOutOfBoundsCountHigher() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity("newnan", 17);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode(null, "GA", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("", "GA", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullStateCode() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("Newnan", null, 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnEmptyStateCode() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("Newnan", "", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnOutOfBoundsCountLower() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("newnan", "GA", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnOutOfBoundsCountHigher() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("newnan", "GA", 17);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode(null, "GA", "US", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyCity() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("", "GA", "US", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullStateCode() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", null, "US", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyStateCode() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "", "US", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCountryCode() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", null, 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnEmptyCountryCode() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "", 4);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnOutOfBoundsCountLower() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "US", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnOutOfBoundsCountHigher() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "US", 17);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCitySuccessfully() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCity("newnan", 4));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeSuccessfully() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndStateCode("newnan", "GA", 4));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeSuccessfully() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndStateCodeAndCountryCode("newnan", "GA", "USA", 4));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void setUnitsOfMeasurementThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherDailyDataRetriever(new MockDataRetriever()).setUnitsOfMeasurement(null);
        });
    }

    @Test
    public void setUnitsOfMeasurementSuccessfully() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
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
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
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
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
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
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
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
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("cityName", "", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnNumOfDaysLessThan1() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("cityName", "countryCode", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnNumOfDaysGreaterThan16() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("cityName", "countryCode", 17);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeSuccessfully() {
        try {
            OpenWeatherDailyDataRetriever retriever = new OpenWeatherDailyDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndCountryCode("newnan", "US", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }
}
