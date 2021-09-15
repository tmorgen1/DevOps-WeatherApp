package edu.westga.weatherapp_service;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_service.mocks.MockDataRetriever;
import edu.westga.weatherapp_service.model.OpenWeatherSevereWarningsRetriever;
import edu.westga.weatherapp_service.model.SevereWeatherWarning;
import edu.westga.weatherapp_service.resources.ServiceConstants;

import java.rmi.RemoteException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OpenWeatherSevereWarningsRetrieverTests {

    private enum MockEnum {
        MOCK;
    }

    private class SevereWeatherWarningMockDataRetrieverWithAlert extends MockDataRetriever {
        @Override
        public String GetData(URL apiCall) {
            String mockReply = "{\"lat\":33.4484,\"lon\":-112.074,\"timezone\":\"America/Phoenix\",\"timezone_offset\":-25200, \"alerts\" : [{\"sender_name\":\"Weather Channel\",\"event\":\"Air Quality Alert\",\"start\":1631722620,\"end\":1631856600,\"description\":\" Ozone High Pollution Advisory for the Greater Phoenix Area through\", \"tags\":[\"Air quality\"]}]}";
            return mockReply;
        }
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
                new SevereWeatherWarningMockDataRetrieverWithAlert());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(91, -180, MeasurementUnits.Metric);
        }, ServiceConstants.LATITUDE_OUT_OF_BOUND);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationLatitudeIsOneBelowLowerBoundary() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new SevereWeatherWarningMockDataRetrieverWithAlert());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(-91, 180, MeasurementUnits.Metric);
        }, ServiceConstants.LATITUDE_OUT_OF_BOUND);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationLongitudeIsOneAboveUpperBoundary() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new SevereWeatherWarningMockDataRetrieverWithAlert());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(-90, 181, MeasurementUnits.Metric);
        }, ServiceConstants.LONGITUDE_OUT_OF_BOUND);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationLongitudeIsOneBelowLowerBoundary() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new SevereWeatherWarningMockDataRetrieverWithAlert());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(90, -181, MeasurementUnits.Metric);
        }, ServiceConstants.LONGITUDE_OUT_OF_BOUND);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationUnitsIsNull() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new SevereWeatherWarningMockDataRetrieverWithAlert());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(89, 179, null);
        }, ServiceConstants.INCORRECT_UNITS);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationUnitsIsTheWrongEnumType() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new SevereWeatherWarningMockDataRetrieverWithAlert());
        assertThrows(IllegalArgumentException.class, () -> {
            test.getSevereWeatherWarningsForLocation(89, 179, MockEnum.MOCK);
        }, ServiceConstants.INCORRECT_UNITS);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationWithValidInputButNoAlertsAreFound() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new MockDataRetriever());
        SevereWeatherWarning[] testData = test.getSevereWeatherWarningsForLocation(-89, -179, MeasurementUnits.Metric);
        assertEquals(0, testData.length);
    }

    @Test
    public void GetSevereWeatherWarningsForLocationWithValidInput() throws RemoteException {
        OpenWeatherSevereWarningsRetriever test = new OpenWeatherSevereWarningsRetriever(
                new SevereWeatherWarningMockDataRetrieverWithAlert());
        SevereWeatherWarning[] testData = test.getSevereWeatherWarningsForLocation(-89, -179, MeasurementUnits.Metric);
        assertEquals(1, testData.length);
    }
}
