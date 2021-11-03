package edu.westga.weatherapp_service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_service.mocks.MockDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherStatisticalDataRetriever;

public class OpenWeatherStatisticalDataRetrieverTests {

    @Test
    public void parameterizedConstructorThrowsExceptionWithNullDataRetriever() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherStatisticalDataRetriever(null);
        });
    }

    @Test
    public void oneParamConstructorShouldSuccessfullyCreateRetriever() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertNotNull(retriever);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnNullCity() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity("", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnOutOfLowerBoundsMonth() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity("Newnan", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityThrowsExceptionOnOutOfHigherBoundsMonth() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCity("Newnan", 13);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("Newnan", "", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStatCodeThrowsExceptionOnOutOfLowerBoundsMonth() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("Newnan", "GA", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStatCodeThrowsExceptionOnOutOfHigherBoundsMonth() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCode("Newnan", "GA", 13);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnNullCity() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnOutOfLowerBoundsMonth() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "US", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeThrowsExceptionOnOutOfHigherBoundsMonth() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndStateCodeAndCountryCode("Newnan", "GA", "US", 13);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCitySuccessfully() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCity("newnan", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeSuccessfully() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndStateCode("newnan", "GA", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndStateCodeAndCountryCodeSuccessfully() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndStateCodeAndCountryCode("newnan", "GA", "USA", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnNullCityName() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
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
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("cityName", "", 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnOutOfLowerBoundsMonth() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("Newnan", "US", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeThrowsExceptionOnOutOfHigherBoundsMonth() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertThrows(IllegalArgumentException.class, () -> {
                retriever.getDataByCityAndCountryCode("Newnan", "US", 13);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDataByCityAndCountryCodeSuccessfully() {
        try {
            OpenWeatherStatisticalDataRetriever retriever = new OpenWeatherStatisticalDataRetriever(new MockDataRetriever());
            assertNotNull(retriever.getDataByCityAndCountryCode("newnan", "US", 1));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }
    
}
