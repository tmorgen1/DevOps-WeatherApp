package edu.westga.weatherapp_gui.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.MockDataRetriever;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.OpenWeatherStatisticalDataRetrieverMock;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class StatisticalWeatherPageViewModelTests {
    

    @Test
    public void testSevereWeatherWarningsPageViewModelConstructorGivenNullRetiever() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(null);
        assertNotNull(viewModel);
    }

    @Test
    public void testSevereWeatherWarningsPageViewModelConstructorGivenRetiever() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertNotNull(viewModel);
    }

    @Test
    public void getStatisticalWeatherDataByWeatherLocationNullWeatherLocation() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getStatisticalWeatherDataByWeatherLocation(null, 1);
        });
    }

    @Test
    public void getStatisticalWeatherDataByWeatherLocationSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        JSONObject result = viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        assertNotNull(result);
    }

    @Test
    public void getRecordMinTemperatureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMinTemperature();
        });
    }

    @Test
    public void getRecordMinTemperatureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMinTemperature();
        assertEquals(200, result);
    }

    @Test
    public void getRecordMaxTemperatureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMaxTemperature();
        });
    }

    @Test
    public void getRecordMaxTemperatureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMaxTemperature();
        assertEquals(210, result);
    }

    @Test
    public void getRecordAverageMinTemperatureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getAverageMinTemperature();
        });
    }

    @Test
    public void getRecordAverageMinTemperatureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getAverageMinTemperature();
        assertEquals(220, result);
    }

    @Test
    public void getRecordAverageMaxTemperatureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getAverageMaxTemperature();
        });
    }

    @Test
    public void getRecordAverageMaxTemperatureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getAverageMaxTemperature();
        assertEquals(230, result);
    }

    @Test
    public void getRecordAverageTemperatureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getAverageTemperature();
        });
    }

    @Test
    public void getRecordAverageTemperatureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getAverageTemperature();
        assertEquals(240, result);
    }

    @Test
    public void getRecordMinPressureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMinPressure();
        });
    }

    @Test
    public void getRecordMinPressureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMinPressure();
        assertEquals(10, result);
    }

    @Test
    public void getRecordMaxPressureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMaxPressure();
        });
    }

    @Test
    public void getRecordMaxPressureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMaxPressure();
        assertEquals(20, result);
    }

    @Test
    public void getMedianPressureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getMedianPressure();
        });
    }

    @Test
    public void getMedianPressureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getMedianPressure();
        assertEquals(30, result);
    }

    @Test
    public void getAveragePressureNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getAveragePressure();
        });
    }

    @Test
    public void getAveragePressureSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getAveragePressure();
        assertEquals(40, result);
    }

    @Test
    public void getRecordMinHumidityNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMinHumidity();
        });
    }

    @Test
    public void getRecordMinHumiditySuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMinHumidity();
        assertEquals(10, result);
    }

    @Test
    public void getRecordMaxHumidityNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMaxHumidity();
        });
    }

    @Test
    public void getRecordMaxHumiditySuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMaxHumidity();
        assertEquals(20, result);
    }

    @Test
    public void getMedianHumidityNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getMedianHumidity();
        });
    }

    @Test
    public void getMedianHumiditySuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getMedianHumidity();
        assertEquals(30, result);
    }

    @Test
    public void getAverageHumidityNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getAverageHumidity();
        });
    }

    @Test
    public void getAverageHumiditySuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getAverageHumidity();
        assertEquals(40, result);
    }

    @Test
    public void getRecordMinWindNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMinWind();
        });
    }

    @Test
    public void getRecordMinWindSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMinWind();
        assertEquals(10, result);
    }

    @Test
    public void getRecordMaxWindNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMaxWind();
        });
    }

    @Test
    public void getRecordMaxWindSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMaxWind();
        assertEquals(20, result);
    }

    @Test
    public void getMedianWindNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getMedianWind();
        });
    }

    @Test
    public void getMedianWindSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getMedianWind();
        assertEquals(30, result);
    }

    @Test
    public void getAverageWindNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getAverageWind();
        });
    }

    @Test
    public void getAverageWindSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getAverageWind();
        assertEquals(40, result);
    }

    @Test
    public void getRecordMinPrecipitationNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMinPrecipitation();
        });
    }

    @Test
    public void getRecordMinPrecipitationSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMinPrecipitation();
        assertEquals(10, result);
    }

    @Test
    public void getRecordMaxPrecipitationNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMaxPrecipitation();
        });
    }

    @Test
    public void getRecordMaxPrecipitationSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMaxPrecipitation();
        assertEquals(20, result);
    }

    @Test
    public void getMedianPrecipitationNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getMedianPrecipitation();
        });
    }

    @Test
    public void getMedianPrecipitationSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getMedianPrecipitation();
        assertEquals(30, result);
    }

    @Test
    public void getAveragePrecipitationNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getAveragePrecipitation();
        });
    }

    @Test
    public void getAveragePrecipitationSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getAveragePrecipitation();
        assertEquals(40, result);
    }

    @Test
    public void getRecordMinCloudsNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMinClouds();
        });
    }

    @Test
    public void getRecordMinCloudsSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMinClouds();
        assertEquals(10, result);
    }

    @Test
    public void getRecordMaxCloudsNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getRecordMaxClouds();
        });
    }

    @Test
    public void getRecordMaxCloudsSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getRecordMaxClouds();
        assertEquals(20, result);
    }

    @Test
    public void getMedianCloudsNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getMedianClouds();
        });
    }

    @Test
    public void getMedianCloudsSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getMedianClouds();
        assertEquals(30, result);
    }

    @Test
    public void getAverageCloudsNullWeatherData() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getAverageClouds();
        });
    }

    @Test
    public void getAverageCloudsSuccessfully() {
        StatisticalWeatherPageViewModel viewModel = new StatisticalWeatherPageViewModel(new OpenWeatherStatisticalDataRetrieverMock( new MockDataRetriever()));
        viewModel.getStatisticalWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 10.0, 10.0), 1);
        double result = viewModel.getAverageClouds();
        assertEquals(40, result);
    }
}
