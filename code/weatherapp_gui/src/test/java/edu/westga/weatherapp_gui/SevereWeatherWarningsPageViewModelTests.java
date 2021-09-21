package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.OpenWeatherSevereWarningsRetrieverMocks;
import edu.westga.weatherapp_gui.viewmodel.SevereWeatherWarningsPageViewModel;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class SevereWeatherWarningsPageViewModelTests {

	static final double DELTA = 0.0000;

	@Test
	public void testSevereWeatherWarningsPageViewModelConstructorGivenNullRetiever() {
		SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(null);
		assertAll(() -> {
			assertNotNull(viewModel);
		}, () -> {
			assertNotNull(viewModel.getErrorLabelStringProperty());
		}, () -> {
			assertNotNull(viewModel.getErrorLabelVisibilityProperty());
		}, () -> {
			assertNotNull(viewModel.getNoWarningsForLocationVisibilityProperty());
		}, () -> {
			assertNotNull(viewModel.getSevereWarningAccordionVisibilityProperty());
		}, () -> {
			assertNotNull(viewModel.getsevereWeatherWarningsStringProperty());
		});
	}

	@Test
	public void testSevereWeatherWarningsPageViewModelConstructorGivenRetriever() {
		SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
				new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockNoAlerts());
		assertAll(() -> {
			assertNotNull(viewModel);
		}, () -> {
			assertNotNull(viewModel.getErrorLabelStringProperty());
		}, () -> {
			assertNotNull(viewModel.getErrorLabelVisibilityProperty());
		}, () -> {
			assertNotNull(viewModel.getNoWarningsForLocationVisibilityProperty());
		}, () -> {
			assertNotNull(viewModel.getSevereWarningAccordionVisibilityProperty());
		}, () -> {
			assertNotNull(viewModel.getsevereWeatherWarningsStringProperty());
		});
	}

	@Test
	public void testSetSevereWeatherWarningsObjectPropertyValueGivenNullLocation() {

		SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
				new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockOneAlert());
		assertThrows(IllegalArgumentException.class, () -> {
			viewModel.setsevereWeatherWarningsObjectPropertyValue(null, MeasurementUnits.Metric);
		});
	}

	@Test
	public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationNoAlerts() {
		SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
				new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockNoAlerts());
		WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
		viewModel.setsevereWeatherWarningsObjectPropertyValue(location, MeasurementUnits.Metric);
		JSONObject data = new JSONObject(viewModel.getsevereWeatherWarningsStringProperty().getValue());
		assertAll(() -> {
			assertEquals(33.4484, data.getDouble("lat"), SevereWeatherWarningsPageViewModelTests.DELTA);
		}, () -> {
			assertEquals(-112.074, data.getDouble("lon"), SevereWeatherWarningsPageViewModelTests.DELTA);
		}, () -> {
			assertEquals(-25200, data.getLong("timezone_offset"));
		}, () -> {
			assertNull(data.optJSONArray("alerts"));
		}, () -> {
			assertTrue(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
		}, () -> {
			assertFalse(viewModel.getSevereWarningAccordionVisibilityProperty().getValue());
		});
	}

	@Test
	public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationAlertsAreNull() {
		SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
				new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockNullAlerts());
		WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
		viewModel.setsevereWeatherWarningsObjectPropertyValue(location, MeasurementUnits.Metric);
		String data = viewModel.getsevereWeatherWarningsStringProperty().getValue();
		assertAll(() -> {
			assertNull(data);
		}, () -> {
			assertTrue(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
		}, () -> {
			assertFalse(viewModel.getSevereWarningAccordionVisibilityProperty().getValue());
		});
	}

	@Test
	public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationOneAlert() {
		SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
				new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockOneAlert());
		WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
		viewModel.setsevereWeatherWarningsObjectPropertyValue(location, MeasurementUnits.Metric);
		JSONObject data = new JSONObject(viewModel.getsevereWeatherWarningsStringProperty().getValue());
		JSONArray warningData = data.optJSONArray("alerts");
		assertAll(() -> {
			assertNotNull(warningData);
		}, () -> {
			assertEquals("Weather Channel", warningData.getJSONObject(0).getString("sender_name"));
		}, () -> {
			assertEquals("Air Quality Alert", warningData.getJSONObject(0).getString("event"));
		}, () -> {
			assertEquals(1631722620L, warningData.getJSONObject(0).getLong("start"));
		}, () -> {
			assertEquals(1631856600L, warningData.getJSONObject(0).getLong("end"));
		}, () -> {
			assertEquals(" Ozone High Pollution Advisory for the Greater Phoenix Area through.",
					warningData.getJSONObject(0).getString("description"));
		}, () -> {
			assertFalse(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
		}, () -> {
			assertTrue(viewModel.getSevereWarningAccordionVisibilityProperty().getValue());
		});
	}

	@Test
	public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationTwoAlerts() {
		SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
				new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockTwoAlerts());
		WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
		viewModel.setsevereWeatherWarningsObjectPropertyValue(location, MeasurementUnits.Metric);
		JSONObject data = new JSONObject(viewModel.getsevereWeatherWarningsStringProperty().getValue());
		JSONArray warningData = data.optJSONArray("alerts");
		assertAll(() -> {
			assertNotNull(warningData);
		},() -> {
			assertFalse(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
		}, () -> {
			assertTrue(viewModel.getSevereWarningAccordionVisibilityProperty().getValue());
		}, () -> {
			assertEquals("Weather Channel", warningData.getJSONObject(0).getString("sender_name"));
		}, () -> {
			assertEquals("Air Quality Alert", warningData.getJSONObject(0).getString("event"));
		}, () -> {
			assertEquals(1631722620L, warningData.getJSONObject(0).getLong("start"));
		}, () -> {
			assertEquals(1631856600L, warningData.getJSONObject(0).getLong("end"));
		}, () -> {
			assertEquals(" Ozone High Pollution Advisory for the Greater Phoenix Area through.",
					warningData.getJSONObject(0).getString("description"));
		}, () -> {
			assertEquals("NWS Nashville (Central Tennessee)", warningData.getJSONObject(1).getString("sender_name"));
		}, () -> {
			assertEquals("Flash Flood Watch", warningData.getJSONObject(1).getString("event"));
		}, () -> {
			assertEquals(1632164880L, warningData.getJSONObject(1).getLong("start"));
		}, () -> {
			assertEquals(1632182400L, warningData.getJSONObject(1).getLong("end"));
		}, () -> {
			assertEquals(" The sky is green. Its raining in Pheonix.",
					warningData.getJSONObject(1).getString("description"));
		});
	}

	@Test
	public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationThreeAlerts() {
		SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
				new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockThreeAlerts());
		WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
		viewModel.setsevereWeatherWarningsObjectPropertyValue(location, MeasurementUnits.Metric);
		JSONObject data = new JSONObject(viewModel.getsevereWeatherWarningsStringProperty().getValue());
		JSONArray warningData = data.optJSONArray("alerts");
		assertAll(() -> {
			assertNotNull(warningData);
		}, () -> {
			assertFalse(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
		}, () -> {
			assertTrue(viewModel.getSevereWarningAccordionVisibilityProperty().getValue());
		}, () -> {
			assertEquals("Weather Channel", warningData.getJSONObject(0).getString("sender_name"));
		}, () -> {
			assertEquals("Air Quality Alert", warningData.getJSONObject(0).getString("event"));
		}, () -> {
			assertEquals(1631722620L, warningData.getJSONObject(0).getLong("start"));
		}, () -> {
			assertEquals(1631856600L, warningData.getJSONObject(0).getLong("end"));
		}, () -> {
			assertEquals(" Ozone High Pollution Advisory for the Greater Phoenix Area through.",
					warningData.getJSONObject(0).getString("description"));
		}, () -> {
			assertEquals("NWS Nashville (Central Tennessee)",
					warningData.getJSONObject(1).getString("sender_name"));
		}, () -> {
			assertEquals("Flash Flood Watch", warningData.getJSONObject(1).getString("event"));
		}, () -> {
			assertEquals(1632164880L, warningData.getJSONObject(1).getLong("start"));
		}, () -> {
			assertEquals(1632182400L, warningData.getJSONObject(1).getLong("end"));
		}, () -> {
			assertEquals(" The sky is green. Its raining in Pheonix.",
					warningData.getJSONObject(1).getString("description"));
		}, () -> {
			assertEquals("AIRNow Program, US Environmental Protection Agency",
					warningData.getJSONObject(2).getString("sender_name"));
		}, () -> {
			assertEquals("Ozone is forecast to reach 108 AQI - Unhealthy for Sensitive Groups on Tue 09/21/2021.",
					warningData.getJSONObject(2).getString("event"));
		}, () -> {
			assertEquals(1632124800L, warningData.getJSONObject(2).getLong("start"));
		}, () -> {
			assertEquals(1632211200L, warningData.getJSONObject(2).getLong("end"));
		}, () -> {
			assertEquals(" Bad air everywhere.", warningData.getJSONObject(2).getString("description"));
		});
	}

}
