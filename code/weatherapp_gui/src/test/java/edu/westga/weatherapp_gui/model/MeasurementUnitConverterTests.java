package edu.westga.weatherapp_gui.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;

public class MeasurementUnitConverterTests {
    

    @Test
    public void convertTemperatureToSameUnits() {
        double result = MeasurementUnitConverter.convertTemperature(100.0, MeasurementUnits.Imperial, MeasurementUnits.Imperial);
        assertEquals(100.0, result);
    }

    @Test
    public void convertTemperatureImperialToMetric() {
        double result = MeasurementUnitConverter.convertTemperature(100.0, MeasurementUnits.Imperial, MeasurementUnits.Metric);
        assertEquals(37.78, result);
    }

    @Test
    public void convertTemperatureImperialToKelvin() {
        double result = MeasurementUnitConverter.convertTemperature(100.0, MeasurementUnits.Imperial, MeasurementUnits.Kelvin);
        assertEquals(310.93, result);
    }

    @Test
    public void convertTemperatureMetricToImperial() {
        double result = MeasurementUnitConverter.convertTemperature(100.0, MeasurementUnits.Metric, MeasurementUnits.Imperial);
        assertEquals(212, result);
    }

    @Test
    public void convertTemperatureMetricToKelvin() {
        double result = MeasurementUnitConverter.convertTemperature(100.0, MeasurementUnits.Metric, MeasurementUnits.Kelvin);
        assertEquals(373.15, result);
    }

    @Test
    public void convertTemperatureKelvinToMetric() {
        double result = MeasurementUnitConverter.convertTemperature(100.0, MeasurementUnits.Kelvin, MeasurementUnits.Metric);
        assertEquals(-173.15, result);
    }

    @Test
    public void convertTemperatureKelvinToImperial() {
        double result = MeasurementUnitConverter.convertTemperature(100.0, MeasurementUnits.Kelvin, MeasurementUnits.Imperial);
        assertEquals(-279.67, result);
    }

    @Test
    public void convertPressureToSameUnits() {
        double result = MeasurementUnitConverter.convertPressure(100.0, MeasurementUnits.Imperial, MeasurementUnits.Imperial);
        assertEquals(100.0, result);
    }

    @Test
    public void convertPressureImperialToMetric() {
        double result = MeasurementUnitConverter.convertPressure(100.0, MeasurementUnits.Imperial, MeasurementUnits.Metric);
        assertEquals(689475.73, result);
    }

    @Test
    public void convertPressureImperialToKelvin() {
        double result = MeasurementUnitConverter.convertPressure(100.0, MeasurementUnits.Imperial, MeasurementUnits.Kelvin);
        assertEquals(6894.76, result);
    }

    @Test
    public void convertPressureMetricToImperial() {
        double result = MeasurementUnitConverter.convertPressure(100.0, MeasurementUnits.Metric, MeasurementUnits.Imperial);
        assertEquals(0.01, result);
    }

    @Test
    public void convertPressureMetricToKelvin() {
        double result = MeasurementUnitConverter.convertPressure(100.0, MeasurementUnits.Metric, MeasurementUnits.Kelvin);
        assertEquals(1, result);
    }

    @Test
    public void convertPressureKelvinToImperial() {
        double result = MeasurementUnitConverter.convertPressure(100.0, MeasurementUnits.Kelvin, MeasurementUnits.Imperial);
        assertEquals(1.45, result);
    }

    @Test
    public void convertPressureKelvinToMetric() {
        double result = MeasurementUnitConverter.convertPressure(100.0, MeasurementUnits.Kelvin, MeasurementUnits.Metric);
        assertEquals(10000, result);
    }

    @Test
    public void convertWindToSameUnits() {
        double result = MeasurementUnitConverter.convertWind(100.0, MeasurementUnits.Imperial, MeasurementUnits.Imperial);
        assertEquals(100.0, result);
    }

    @Test
    public void convertWindImperialToMetric() {
        double result = MeasurementUnitConverter.convertWind(100.0, MeasurementUnits.Imperial, MeasurementUnits.Metric);
        assertEquals(30.48, result);
    }

    @Test
    public void convertWindImperialToKelvin() {
        double result = MeasurementUnitConverter.convertWind(100.0, MeasurementUnits.Imperial, MeasurementUnits.Kelvin);
        assertEquals(30.48, result);
    }

    @Test
    public void convertWindMetricToImperial() {
        double result = MeasurementUnitConverter.convertWind(100.0, MeasurementUnits.Metric, MeasurementUnits.Imperial);
        assertEquals(328.08, result);
    }

    @Test
    public void convertWindMetricToKelvin() {
        double result = MeasurementUnitConverter.convertWind(100.0, MeasurementUnits.Metric, MeasurementUnits.Kelvin);
        assertEquals(100.0, result);
    }

    @Test
    public void convertWindKelvinToImperial() {
        double result = MeasurementUnitConverter.convertWind(100.0, MeasurementUnits.Kelvin, MeasurementUnits.Imperial);
        assertEquals(328.08, result);
    }

    @Test
    public void convertWindKelvinToMetric() {
        double result = MeasurementUnitConverter.convertWind(100.0, MeasurementUnits.Kelvin, MeasurementUnits.Metric);
        assertEquals(100.0, result);
    }

    @Test
    public void convertPrecipitationToSameUnits() {
        double result = MeasurementUnitConverter.convertPrecipitation(100.0, MeasurementUnits.Imperial, MeasurementUnits.Imperial);
        assertEquals(100.0, result);
    }

    @Test
    public void convertPrecipitationImperialToMetric() {
        double result = MeasurementUnitConverter.convertPrecipitation(100.0, MeasurementUnits.Imperial, MeasurementUnits.Metric);
        assertEquals(2540, result);
    }

    @Test
    public void convertPrecipitationImperialToKelvin() {
        double result = MeasurementUnitConverter.convertPrecipitation(100.0, MeasurementUnits.Imperial, MeasurementUnits.Metric);
        assertEquals(2540, result);
    }

    @Test
    public void convertPrecipitationMetricToImperial() {
        double result = MeasurementUnitConverter.convertPrecipitation(100.0, MeasurementUnits.Metric, MeasurementUnits.Imperial);
        assertEquals(3.94, result);
    }

    @Test
    public void convertPrecipitationMetricToKelvin() {
        double result = MeasurementUnitConverter.convertPrecipitation(100.0, MeasurementUnits.Metric, MeasurementUnits.Kelvin);
        assertEquals(100.0, result);
    }

    @Test
    public void convertPrecipitationKelvinToImperial() {
        double result = MeasurementUnitConverter.convertPrecipitation(100.0, MeasurementUnits.Kelvin, MeasurementUnits.Imperial);
        assertEquals(3.94, result);
    }

    @Test
    public void convertPrecipitationKelvinToMetric() {
        double result = MeasurementUnitConverter.convertPrecipitation(100.0, MeasurementUnits.Kelvin, MeasurementUnits.Metric);
        assertEquals(100.0, result);
    }
}
