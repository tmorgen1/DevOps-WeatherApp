package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class CurrentWeatherInformationTests {
    @AfterEach
    public void resetMeasurementUnits() {
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Imperial);
    }

    @Test
    public void getWeatherLocationValid() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.40, 34.00);
        CurrentWeatherInformation.setWeatherLocation(location);
        WeatherLocation result = CurrentWeatherInformation.getWeatherLocation();
        assertEquals(location, result);
    }

    @Test
    public void getWeatherDataValid() {
        JSONObject testObject = new JSONObject().put("test", 1);
        CurrentWeatherInformation.setWeatherData(testObject);
        JSONObject result = CurrentWeatherInformation.getWeatherData();
        assertEquals(testObject, result);
    }

    @Test
    public void setWeatherLocationThrowsExceptionWithNullCity() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrentWeatherInformation.setWeatherLocation(null);
        });
    }

    @Test
    public void setWeatherLocationValid() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.40, 34.00);
        CurrentWeatherInformation.setWeatherLocation(location);
        WeatherLocation result = CurrentWeatherInformation.getWeatherLocation();
        assertEquals(location, result);
    }

    @Test
    public void setWeatherDataValid() {
        JSONObject testObject = new JSONObject().put("test", 1);
        CurrentWeatherInformation.setWeatherData(testObject);
        JSONObject result = CurrentWeatherInformation.getWeatherData();
        assertEquals(testObject, result);
    }

    @Test
    public void getMeasurementUnitsValid() {
        MeasurementUnits units = CurrentWeatherInformation.getMeasurementUnits();
        assertEquals(MeasurementUnits.Imperial, units);
    }

    @Test
    public void setMeasurementUnitsValid() {
        MeasurementUnits units = MeasurementUnits.Kelvin;
        CurrentWeatherInformation.setMeasurementUnits(units);
        MeasurementUnits result = CurrentWeatherInformation.getMeasurementUnits();
        assertEquals(result, units);
    }
}
