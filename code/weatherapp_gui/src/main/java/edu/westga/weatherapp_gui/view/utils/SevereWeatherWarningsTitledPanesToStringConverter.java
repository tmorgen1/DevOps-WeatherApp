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

    @Override
    public List<TitledPane> fromString(String weatherWarnings) {
        JSONObject weatherWarningsData = new JSONObject(weatherWarnings);
        List<TitledPane> weatherWarningsTitledPanes = this
                .convertSevereWeatherWarningsForLocationFromJsonToArray(weatherWarningsData);
        return weatherWarningsTitledPanes;
    }

    private JSONArray parseDataFromJsonObjectAsAJsonArray(JSONObject data) {
        JSONArray warningsData = data.optJSONArray("alerts");
        if (warningsData == null) {
            warningsData = new JSONArray();
        }
        return warningsData;
    }

    private List<TitledPane> convertSevereWeatherWarningsForLocationFromJsonToArray(JSONObject data) {
        JSONArray warningsData = this.parseDataFromJsonObjectAsAJsonArray(data);
        List<TitledPane> severeWeatherWarningsTitledPanes = new ArrayList<TitledPane>();
        for (int ix = 0; ix < warningsData.length(); ix++) {
            String warningName = warningsData.getJSONObject(ix).getString("event");
            String start = String.valueOf(warningsData.getJSONObject(ix).getLong("start"));
            String end = String.valueOf(warningsData.getJSONObject(ix).getLong("end"));
            String description = warningsData.getJSONObject(ix).getString("description");
            SevereWeatherWarningTitledPane severeWarningTitledPane = new SevereWeatherWarningTitledPane(warningName,
                    start, end, description, data.toString());
            severeWeatherWarningsTitledPanes.add(severeWarningTitledPane);
        }
        return severeWeatherWarningsTitledPanes;
    }

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
