package edu.westga.weatherapp_service;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_service.mocks.MockDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherSevereWarningsRetriever;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OpenWeatherSevereWarningsRetrieverTests {

    private enum MockEnum {
        MOCK;
    }

    @Test
    public void ConstructorDoesNotThrowExceptionTest() {
        assertDoesNotThrow(() -> {
            new OpenWeatherSevereWarningsRetriever(new MockDataRetriever());
        });
    }

    @Test
    public void ConstructorDoesNotAllowNullDataRetrieverTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OpenWeatherSevereWarningsRetriever(null);
        });
    }

    @Test
    public void GetSevereWeatherWarningsForLocationLatitudeIsOneAboveUpperBoundary() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new MockDataRetriever());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(91, -180, MeasurementUnits.Metric);
        }, OpenWeatherSevereWarningsRetriever.LATITUDE_OUT_OF_BOUND);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationLatitudeIsOneBelowLowerBoundary() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new MockDataRetriever());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(-91, 180, MeasurementUnits.Metric);
        }, OpenWeatherSevereWarningsRetriever.LATITUDE_OUT_OF_BOUND);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationLongitudeIsOneAboveUpperBoundary() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new MockDataRetriever());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(-90, 181, MeasurementUnits.Metric);
        }, OpenWeatherSevereWarningsRetriever.LONGITUDE_OUT_OF_BOUND);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationLongitudeIsOneBelowLowerBoundary() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new MockDataRetriever());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(90, -181, MeasurementUnits.Metric);
        }, OpenWeatherSevereWarningsRetriever.LONGITUDE_OUT_OF_BOUND);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationUnitsIsNull() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new MockDataRetriever());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(89, 179, null);
        }, OpenWeatherSevereWarningsRetriever.INCORRECT_UNITS);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationUnitsIsTheWrongEnumType() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new MockDataRetriever());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(89, 179, MockEnum.MOCK);
        }, OpenWeatherSevereWarningsRetriever.INCORRECT_UNITS);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationWithValidInput() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new MockDataRetriever());
        String testData = test.getSevereWeatherWarningsForLocation(-89, -179, MeasurementUnits.Metric);
        assertNotNull(testData);
    }
}
