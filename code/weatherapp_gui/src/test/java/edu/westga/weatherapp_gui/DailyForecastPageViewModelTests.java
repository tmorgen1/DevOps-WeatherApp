package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.rmi.RemoteException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.MockDataRetriever;
import edu.westga.weatherapp_gui.viewmodel.DailyForecastPageViewModel;
import edu.westga.weatherapp_service.model.OpenWeatherDailyDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherIconRetriever;
import edu.westga.weatherapp_shared.interfaces.DailyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

public class DailyForecastPageViewModelTests {

    @Test
    public void constructorValid() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            assertNotNull(viewModel);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void constructorWithDailyWeatherRetrieverNull() {
        try {
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(null, iconRetriever);
            assertNotNull(viewModel);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void constructorWithIconRetrieverNull() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, null);
            assertNotNull(viewModel);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getWeatherDataByCityThrowsExceptionWithNullCity() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.GetWeatherDataByCity(null, 1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getWeatherDataByCityWithValidCity() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            JSONObject result = viewModel.GetWeatherDataByCity("test", 1);
            assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDayWeatherIconThrowsExceptionWithNoWeatherData() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.GetDayWeatherIcon(0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDayWeatherIconValid() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            viewModel.GetWeatherDataByCity("test", 1);
            String result = viewModel.GetDayWeatherIcon(0);
            assertEquals("http://openweathermap.org/img/wn/fake-url@4x.png", result);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDayMaxTemperatureThrowsExceptionWithNoWeatherData() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.GetDayMaxTemperature(1);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDayMaxTemperatureValid() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            viewModel.GetWeatherDataByCity("city", 1);
            String result = viewModel.GetDayMaxTemperature(0);
            assertEquals("98", result);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDayMinTemperatureThrowsExceptionWithNoWeatherData() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.GetDayMinTemperature(0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDayMinTemperatureValid() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            viewModel.GetWeatherDataByCity("city", 1);
            String result = viewModel.GetDayMinTemperature(0);
            assertEquals("90", result);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDayUtcDateTimeThrowsExceptionWithNoWeatherData() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.GetDayUtcDateTime(0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getDayUtcDateTimeValid() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            viewModel.GetWeatherDataByCity("city", 1);
            Long utcTime = viewModel.GetDayUtcDateTime(0);
            assertEquals(500, utcTime);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getTimezoneThrowsExceptionWithNoWeatherData() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            assertThrows(IllegalArgumentException.class, () -> {
                viewModel.GetTimezone();
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getTimezoneValid() {
        try {
            DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetriever(
                    new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetriever();
            DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
            viewModel.GetWeatherDataByCity("city", 1);
            Long timezone = viewModel.GetTimezone();
            assertEquals(100, timezone);
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }
}
