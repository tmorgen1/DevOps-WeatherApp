package edu.westga.weatherapp_gui.mocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.interfaces.SevereWeatherWarningsRetriever;

public class OpenWeatherSevereWarningsRetrieverMocks {

	public static class OpenWeatherSevereWarningRetrieverMockNoAlerts implements SevereWeatherWarningsRetriever {

		public OpenWeatherSevereWarningRetrieverMockNoAlerts() {
			super();
		}

		@Override
		public String getSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> unit)
				throws RemoteException, IllegalArgumentException {
			String mockReply = "{\"lat\":33.4484,\"lon\":-112.074,\"timezone\":\"America/Phoenix\",\"timezone_offset\":-25200}";
			return mockReply;
		}
	}

	public static class OpenWeatherSevereWarningRetrieverMockNullAlerts implements SevereWeatherWarningsRetriever {

		public OpenWeatherSevereWarningRetrieverMockNullAlerts() {
			super();
		}

		@Override
		public String getSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> unit)
				throws RemoteException, IllegalArgumentException {
			String mockReply = null;
			return mockReply;
		}
	}

	public static class OpenWeatherSevereWarningRetrieverMockOneAlert implements SevereWeatherWarningsRetriever {

		public OpenWeatherSevereWarningRetrieverMockOneAlert() {
			super();
		}

		@Override
		public String getSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> unit)
				throws RemoteException, IllegalArgumentException {
			String mockReply = "{\"lat\":33.4484,\"lon\":-112.074,\"timezone\":\"America/Phoenix\",\"timezone_offset\":-25200,"
					+ "\"alerts\" : [{\"sender_name\":\"Weather Channel\",\"event\":\"Air Quality Alert\",\"start\":1631722620,"
					+ "\"end\":1631856600,\"description\":\" Ozone High Pollution Advisory for the Greater Phoenix Area through.\","
					+ " \"tags\":[\"Air quality\"]}]}]";
			return mockReply;
		}
	}

	public static class OpenWeatherSevereWarningRetrieverMockTwoAlerts implements SevereWeatherWarningsRetriever {

		public OpenWeatherSevereWarningRetrieverMockTwoAlerts() {
			super();
		}

		@Override
		public String getSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> unit)
				throws RemoteException, IllegalArgumentException {
			String mockReply = "{\"lat\":33.4484,\"lon\":-112.074,\"timezone\":\"America/Phoenix\",\"timezone_offset\":-25200,"
					+ "\"alerts\" : [{\"sender_name\":\"Weather Channel\",\"event\":\"Air Quality Alert\",\"start\":1631722620,"
					+ "\"end\":1631856600,\"description\":\" Ozone High Pollution Advisory for the Greater Phoenix Area through.\","
					+ " \"tags\":[\"Air quality\"]},{\"sender_name\":\"NWS Nashville (Central Tennessee)\",\"event\":\"Flash Flood Watch\",\"start\":1632164880,"
					+ "\"end\":1632182400,\"description\":\" The sky is green. Its raining in Pheonix.\","
					+ " \"tags\":[\"Flood\"]}]}";
			return mockReply;
		}
	}

	public static class OpenWeatherSevereWarningRetrieverMockThreeAlerts implements SevereWeatherWarningsRetriever {

		public OpenWeatherSevereWarningRetrieverMockThreeAlerts() {
			super();
		}

		@Override
		public String getSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> unit)
				throws RemoteException, IllegalArgumentException {
			String mockReply = "{\"lat\":33.4484,\"lon\":-112.074,\"timezone\":\"America/Phoenix\",\"timezone_offset\":-25200,"
					+ "\"alerts\" : [{\"sender_name\":\"Weather Channel\",\"event\":\"Air Quality Alert\",\"start\":1631722620,"
					+ "\"end\":1631856600,\"description\":\" Ozone High Pollution Advisory for the Greater Phoenix Area through.\","
					+ " \"tags\":[\"Air quality\"]},{\"sender_name\":\"NWS Nashville (Central Tennessee)\",\"event\":\"Flash Flood Watch\",\"start\":1632164880,"
					+ "\"end\":1632182400,\"description\":\" The sky is green. Its raining in Pheonix.\","
					+ " \"tags\":[\"Flood\"]},{\"sender_name\":\"AIRNow Program, US Environmental Protection Agency\",\"event\":\"Ozone is forecast to reach 108 AQI - Unhealthy for Sensitive Groups on Tue 09/21/2021.\",\"start\":1632124800,"
					+ "\"end\":1632211200,\"description\":\" Bad air everywhere.\"," + " \"tags\":[\"Air quality\"]}]}";
			return mockReply;
		}
	}
}
