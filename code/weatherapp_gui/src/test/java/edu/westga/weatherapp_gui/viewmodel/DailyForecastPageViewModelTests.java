package edu.westga.weatherapp_gui.viewmodel;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks.DailyDataRetrieverExceptionMock;
import edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks.IconRetrieverExceptionMock;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.MockDataRetriever;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.OpenWeatherDailyDataRetrieverMock;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.OpenWeatherIconRetrieverMock;
import edu.westga.weatherapp_gui.viewmodel.DailyForecastPageViewModel;
import edu.westga.weatherapp_shared.interfaces.DailyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class DailyForecastPageViewModelTests {

    @Test
    public void constructorValid() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithDailyWeatherRetrieverNull() {
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(null, iconRetriever);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithIconRetrieverNull() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, null);
        assertNotNull(viewModel);
    }

    @Test
    public void getWeatherDataByWeatherLocationSuccessfullyCatchesException() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new DailyDataRetrieverExceptionMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        assertNull(viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1));
    }

    @Test
    public void getWeatherDataByCityThrowsExceptionWithNullCity() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getWeatherDataByWeatherLocation(null, 1);
        });
    }

    @Test
    public void getWeatherDataByCityWithValidCity() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        JSONObject result = viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
    }

    @Test
    public void getWeatherDataByWeatherLocationWithEmptyState() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        JSONObject result = viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "N/A", 30.40, 30.40), 1);
        assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
    }

    @Test
    public void getDayWeatherIconSuccessfullyCatchesRemoteException() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new IconRetrieverExceptionMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "N/A", 30.40, 30.40), 1);
        assertNull(viewModel.getDayWeatherIcon(0));
    }

    @Test
    public void getDayWeatherIconThrowsExceptionWithNoWeatherData() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getDayWeatherIcon(0);
        });
    }

    @Test
    public void getDayWeatherIconValid() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        String result = viewModel.getDayWeatherIcon(0);
        assertEquals("http://openweathermap.org/img/wn/fake-url@4x.png", result);
    }

    @Test
    public void getDayMaxTemperatureThrowsExceptionWithNoWeatherData() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getDayMaxTemperature(1);
        });
    }

    @Test
    public void getDayMaxTemperatureValid() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        String result = viewModel.getDayMaxTemperature(0);
        assertEquals("98", result);
    }

    @Test
    public void getDayMinTemperatureThrowsExceptionWithNoWeatherData() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getDayMinTemperature(0);
        });
    }

    @Test
    public void getDayMinTemperatureValid() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        String result = viewModel.getDayMinTemperature(0);
        assertEquals("90", result);
    }

    @Test
    public void getDayUtcDateTimeThrowsExceptionWithNoWeatherData() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getDayUtcDateTime(0);
        });
    }

    @Test
    public void getDayUtcDateTimeValid() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        Long utcTime = viewModel.getDayUtcDateTime(0);
        assertEquals(500, utcTime);
    }

    @Test
    public void getTimezoneThrowsExceptionWithNoWeatherData() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getTimezone();
        });
    }

    @Test
    public void getTimezoneValid() {
        DailyWeatherDataRetriever dailyWeatherRetriever = new OpenWeatherDailyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        DailyForecastPageViewModel viewModel = new DailyForecastPageViewModel(dailyWeatherRetriever, iconRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        Long timezone = viewModel.getTimezone();
        assertEquals(100, timezone);
    }
}
