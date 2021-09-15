package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;

public class CurrentWeatherInformationTests {
    @Test
    public void getCityNameValid() {
        CurrentWeatherInformation.setCityName("test");
        String result = CurrentWeatherInformation.getCityName();
        assertEquals("test", result);
    }

    @Test
    public void getWeatherDataValid() {
        JSONObject testObject = new JSONObject().put("test", 1);
        CurrentWeatherInformation.setWeatherData(testObject);
        JSONObject result = CurrentWeatherInformation.getWeatherData();
        assertEquals(testObject, result);
    }

    @Test
    public void setCityNameThrowsExceptionWithNullCity() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrentWeatherInformation.setCityName(null);
        });
    }

    @Test
    public void setCityNameThrowsExceptionWithEmptyCity() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrentWeatherInformation.setCityName("");
        });
    }

    @Test
    public void setCityNameValid() {
        String testCityName = "test";
        CurrentWeatherInformation.setCityName(testCityName);
        String result = CurrentWeatherInformation.getCityName();
        assertEquals(testCityName, result);
    }

    @Test
    public void setWeatherDataThrowsExceptionWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrentWeatherInformation.setWeatherData(null);
        });
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
