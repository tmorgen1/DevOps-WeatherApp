package edu.westga.weatherapp_service.model;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_service.resources.ServiceConstants;
import edu.westga.weatherapp_shared.interfaces.SevereWeatherWarningsRetriever;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This severe warnings retriever uses the OpenWeather API to obtain weather
 * icons. Also extends the UnicastRemoteObject to allow for Remote Method
 * Invocation.
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class OpenWeatherSevereWarningsRetriever extends UnicastRemoteObject implements SevereWeatherWarningsRetriever {

    public static final int LATITUDE_UPPER_BOUND = 90;
    public static final int LATITUDE_LOWER_BOUND = -90;
    public static final int LONGITUDE_UPPER_BOUND = 180;
    public static final int LONGITUDE_LOWER_BOUND = -180;
    private static final String SEVERE_WARNING_BASE_URL = "http:/"
            + "/pro.openweathermap.org/data/2.5/onecall?exclude=minutely,hourly,daily,current";
    private DataRetriever dataRetriever;

    /**
     * Creates an Open Weather Severe Warnings Retriever
     * 
     * @param dataRetriever the data retriever
     * 
     * @precondition dataRetriever != null
     * @postcondition none
     * @throws RemoteException
     */
    public OpenWeatherSevereWarningsRetriever(DataRetriever dataRetriever) throws RemoteException {
        super();
        if (dataRetriever == null) {
            throw new IllegalArgumentException("The retriever cannot be null");
        }
        this.dataRetriever = dataRetriever;
    }

    @Override
    public SevereWeatherWarning[] getSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> units)
            throws RemoteException, IllegalArgumentException {
        if (latitude < OpenWeatherSevereWarningsRetriever.LATITUDE_LOWER_BOUND
                || latitude > OpenWeatherSevereWarningsRetriever.LATITUDE_UPPER_BOUND) {
            throw new IllegalArgumentException(ServiceConstants.LATITUDE_OUT_OF_BOUND);
        }
        if (longitude < OpenWeatherSevereWarningsRetriever.LONGITUDE_LOWER_BOUND
                || longitude > OpenWeatherSevereWarningsRetriever.LONGITUDE_UPPER_BOUND) {
            throw new IllegalArgumentException(ServiceConstants.LONGITUDE_OUT_OF_BOUND);
        }
        if (units == null || !units.getClass().equals(MeasurementUnits.class)) {
            throw new IllegalArgumentException(ServiceConstants.INCORRECT_UNITS);
        }
        JSONObject data = new JSONObject(this.fetchDataOfSevereWeatherWarningsForLocation(latitude, longitude, units));
        SevereWeatherWarning[] severeWeatherWarnings = this.convertSevereWeatherWarningsForLocationFromJsonToArray(data);
        return severeWeatherWarnings;
    }

    private SevereWeatherWarning[] convertSevereWeatherWarningsForLocationFromJsonToArray(JSONObject data) {
        JSONArray warningsData = this.parseDataFromJsonObjectAsAJsonArray(data);
        SevereWeatherWarning[] severeWeatherWarnings = new SevereWeatherWarning[warningsData.length()];
        for (int ix = 0; ix < warningsData.length(); ix++) {
            String forecasterName = warningsData.getJSONObject(ix).getString("sender_name");
            String warningName = warningsData.getJSONObject(ix).getString("event");
            String start = String.valueOf(warningsData.getJSONObject(ix).getLong("start"));
            String end = String.valueOf(warningsData.getJSONObject(ix).getLong("end"));
            String description = warningsData.getJSONObject(ix).getString("description");
            severeWeatherWarnings[ix] = new SevereWeatherWarning(forecasterName, warningName, start, end, description);
        }
        return severeWeatherWarnings;
    }

    private String fetchDataOfSevereWeatherWarningsForLocation(double latitude, double longitude, Enum<?> units) {
        MeasurementUnits unit = Enum.valueOf(MeasurementUnits.class, units.name());
        String paramLatitude = "&lat=" + latitude;
        String paramLongitude = "&lon=" + longitude;
        URL apiCall = this.dataRetriever.GetServiceAPICallURL(paramLatitude + paramLongitude,
                OpenWeatherSevereWarningsRetriever.SEVERE_WARNING_BASE_URL, ServiceConstants.API_KEY, unit);
        return this.dataRetriever.GetData(apiCall);
    }

    private JSONArray parseDataFromJsonObjectAsAJsonArray(JSONObject data) {
        JSONArray warningsData = data.optJSONArray("alerts");
        if (warningsData == null) {
            warningsData = new JSONArray();
        }
        return warningsData;
    }
}