package edu.westga.weatherapp_gui.view.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.westga.weatherapp_gui.view.SevereWeatherWarningTitledPane;
import javafx.scene.control.TitledPane;
import javafx.util.StringConverter;

/**
 * SevereWeatherWarningsTitledPanesToStringConverter the raw weather warning
 * data to and from TitledPanes
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarningsTitledPanesToStringConverter extends StringConverter<List<TitledPane>> {

    /**
     * Converts the string of severe weather warnings to a list of titled panes
     * 
     * @param weatherWarnings the data potentially containing severe weather warning
     *                        data
     * 
     * @return the list of severe weather warning titled panes
     */
    @Override
    public List<TitledPane> fromString(String weatherWarnings) {
        JSONObject weatherWarningsData = new JSONObject(weatherWarnings);
        List<TitledPane> weatherWarningsTitledPanes = this
                .convertSevereWeatherWarningsForLocationFromJsonToArray(weatherWarningsData);
        return weatherWarningsTitledPanes;
    }

    /**
     * Converts the severe weather warnings data from a location into a list of
     * titled panes
     * 
     * @param data the raw data from the source
     * 
     * @return the list of severe weather warning titled panes
     */
    private List<TitledPane> convertSevereWeatherWarningsForLocationFromJsonToArray(JSONObject data) {
        JSONArray warningsData = this.extractWarningDataFromJsonObjectAsAJsonArray(data);
        List<TitledPane> severeWeatherWarningsTitledPanes = new ArrayList<TitledPane>();
        for (int ix = 0; ix < warningsData.length(); ix++) {
            String warningName = warningsData.getJSONObject(ix).getString("event");
            String start = String.valueOf(warningsData.getJSONObject(ix).getLong("start"));
            String end = String.valueOf(warningsData.getJSONObject(ix).getLong("end"));
            String timezone = String.valueOf(data.getLong("timezone_offset"));
            String details = warningsData.getJSONObject(ix).getString("description");
            SevereWeatherWarningTitledPane severeWarningTitledPane = new SevereWeatherWarningTitledPane(warningName,
                    start, end, details, timezone, data.toString());
            severeWeatherWarningsTitledPanes.add(severeWarningTitledPane);
        }
        return severeWeatherWarningsTitledPanes;
    }

    /**
     * 
     * Extracts the severe weather warning's data from the raw data as a jsonarray
     * 
     * @param data the raw data from the source
     * 
     * @return the json array of that data or an empty json array if no data found
     */

    private JSONArray extractWarningDataFromJsonObjectAsAJsonArray(JSONObject data) {
        JSONArray warningsData = data.optJSONArray("alerts");
        if (warningsData == null) {
            warningsData = new JSONArray();
        }
        return warningsData;
    }

    /**
     * Converts the SevereWeatherWarningTitledPane to string
     * 
     * @param weatherWarningTitledPanes the list of severe weather warnings titled
     *                                  panes
     * 
     * @return the string form of the list of severe weather warnings titled pane
     */
    @Override
    public String toString(List<TitledPane> weatherWarningTitledPanes) {
        String rawData = "";

        TitledPane firstTitledPane = null;

        if (weatherWarningTitledPanes != null && !weatherWarningTitledPanes.isEmpty()) {
            firstTitledPane = weatherWarningTitledPanes.get(0);
        }

        if (firstTitledPane != null && firstTitledPane instanceof SevereWeatherWarningTitledPane) {
            rawData = ((SevereWeatherWarningTitledPane) weatherWarningTitledPanes.get(0)).getRawData();
        }
        return rawData;
    }

}
