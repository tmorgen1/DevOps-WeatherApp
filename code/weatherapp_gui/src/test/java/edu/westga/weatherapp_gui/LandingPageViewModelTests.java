package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.rmi.RemoteException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.MockDataRetriever;
import edu.westga.weatherapp_gui.viewmodel.LandingPageViewModel;
import edu.westga.weatherapp_service.model.OpenWeatherCurrentDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherIconRetriever;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

public class LandingPageViewModelTests {

    @Test
    public void constructorValid() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertNotNull(viewModel);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void constructorWithWeatherDataRetrieverNull() {
        try {
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(null, iconRetriever);
            assertNotNull(viewModel);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void constructorWithIconRetrieverNull() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, null);
            assertNotNull(viewModel);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getWeatherDataByCityThrowsExceptionWithNullCity() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.getWeatherDataByCity(null);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getWeatherDataByCityWithEmptyCity() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.getWeatherDataByCity("");
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getWeatherDataByCityWithValidCity() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            JSONObject result = viewModel.getWeatherDataByCity("test");
            assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentTemperatureThrowsExceptionWithNoWeatherData() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.getCurrentTemperature();
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentTemperatureValid() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            viewModel.getWeatherDataByCity("test");
            String result = viewModel.getCurrentTemperature();
            assertEquals("70", result);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentHumidityThrowsExceptionNoWeatherData() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.getCurrentHumidity();
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentHumidityValid() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            viewModel.getWeatherDataByCity("test");
            String result = viewModel.getCurrentHumidity();
            assertEquals("80", result);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentWeatherDescriptionThrowsExceptionNoWeatherData() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.getCurrentWeatherDescription();
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentWeatherDescriptionValid() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            viewModel.getWeatherDataByCity("test");
            String result = viewModel.getCurrentWeatherDescription();
            assertEquals("Cloudy", result);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentWindSpeedThrowsExceptionNoWeatherData() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.getCurrentWindSpeed();
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentWindSpeedValid() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            viewModel.getWeatherDataByCity("test");
            String result = viewModel.getCurrentWindSpeed();
            assertEquals("5", result);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentWeatherIconThrowsExceptionNoWeatherData() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.getCurrentWeatherIcon();
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getCurrentWeatherIconValid() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            viewModel.getWeatherDataByCity("test");
            String result = viewModel.getCurrentWeatherIcon();
            assertEquals("http://openweathermap.org/img/wn/test@4x.png", result);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void setCurrentWeatherDataThrowsExceptionWithNullData() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.SetCurrentWeatherData(null);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void setCurrentWeatherDataValid() {
        try {
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever);
            JSONObject testObject = new JSONObject().put("test", 1);
            viewModel.SetCurrentWeatherData(testObject);
            assertNotNull(viewModel);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }
}
