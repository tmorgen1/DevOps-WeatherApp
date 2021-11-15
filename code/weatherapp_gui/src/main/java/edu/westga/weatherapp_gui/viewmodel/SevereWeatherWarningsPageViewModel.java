package edu.westga.weatherapp_gui.viewmodel;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.SevereWeatherWarningsRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;
import edu.westga.weatherapp_gui.model.SevereWeatherWarning;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

/**
 * The view model class for the severe weather warnings page.
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarningsPageViewModel {

    /**
     * the section of the json data that contains the Severe Weather Warnings
     */
    public static final String DATA_ALERTS_SECTION = "alerts";

    /**
     * rmi exception thrown text
     */
    public static final String  RMI_EXCEPTION_THROWN = "Error looking up java rmi binding";

    /**
     * location not found
     */
    public static final String LOCATION_NOT_FOUND_EXCEPTION_THROWN = "No location found.";

    /**
     * the error text string property for use in bindings
     */
    private StringProperty errorTextStringProperty;

    /**
     * the no warnings for location visibility property for use in bindings
     */
    private BooleanProperty noWarningsForLocationVisibilityProperty;

    /**
     * the severe warnings combo box visibility property for use in bindings
     */
    private BooleanProperty severeWarningComboBoxVisibilityProperty;

    /**
     * the severe warnings combo box list property for use in bindings
     */
    private ListProperty<SevereWeatherWarning> severeWarningComboBoxListProperty;

    /**
     * the error Text visibility property for use in bindings
     */
    private BooleanProperty errorTextVisibilityProperty;

    /**
     * the severe weather warnings retrieverr
     */
    private SevereWeatherWarningsRetriever severeWeatherWarningsRetriever;

    /**
     * Creates an instance of the Severe Weather Warnings Page View Model
     * 
     * 
     * @param dataRetriever the data retriever
     */
    public SevereWeatherWarningsPageViewModel(SevereWeatherWarningsRetriever dataRetriever) {
        this.severeWarningComboBoxListProperty = new SimpleListProperty<SevereWeatherWarning>(
                FXCollections.observableArrayList());
        this.errorTextStringProperty = new SimpleStringProperty();
        this.errorTextVisibilityProperty = new SimpleBooleanProperty();
        this.noWarningsForLocationVisibilityProperty = new SimpleBooleanProperty();
        this.severeWarningComboBoxVisibilityProperty = new SimpleBooleanProperty();
        if (dataRetriever != null) {
            this.severeWeatherWarningsRetriever = dataRetriever;
        } else {
            String lookUpUrl = "rmi:/" + "/localhost:5000/severe-warnings";
            this.initializeSevereWeatherWarningsRetrieverUsingNaming(lookUpUrl);
        }
    }

    /**
     * Initailizes the severeWeatherWarningsRetriever class pointed to by the lookUpUrl
     * 
     * @param lookUpUrl the url for Naming to get the class to link to
     */
    private void initializeSevereWeatherWarningsRetrieverUsingNaming(String lookUpUrl) {
        try {
            this.severeWeatherWarningsRetriever = (SevereWeatherWarningsRetriever) Naming.lookup(lookUpUrl);
        } catch (Exception ex) {
            this.setErrorTextStringPropertyValue(SevereWeatherWarningsPageViewModel.RMI_EXCEPTION_THROWN);
            this.setErrorTextVisibilityProperty(true);
        }
    }

    /**
     * Sets the string value of the severe weather warnings page's properties values
     * 
     * @precondition location != null && units != null
     * @param location the location for the warnings
     * @param units    the unit of measument the warning should come in
     */
    public void setsevereWeatherWarningsPagePropertiesValues(WeatherLocation location, MeasurementUnits units) {
        try {
            String data = this.severeWeatherWarningsRetriever.getSevereWeatherWarningsForLocation(location.getLatitude(),
                    location.getLongitude(), units);
            this.setSevereWarningPageComboBoxListProperty(data);
            this.setNoWarningsForLocationVisibilityPropertyValue();
            this.setSevereWarningComboBoxVisibilityPropertyValue();
        } catch (RemoteException | NullPointerException ex) {
            this.setErrorTextStringPropertyValue(SevereWeatherWarningsPageViewModel.LOCATION_NOT_FOUND_EXCEPTION_THROWN);
            this.setErrorTextVisibilityProperty(true);
        }
    }

    /**
     * Converts the severe weather warnings data from a location into a list of
     * severe weather warnings and sets the value of
     * severeWarningComboBoxListProperty to that list
     * 
     * @param data the raw data from the source
     */
    private void setSevereWarningPageComboBoxListProperty(String data) {
        if (!data.contains(SevereWeatherWarningsPageViewModel.DATA_ALERTS_SECTION)) {
            return;
        }
        JSONObject weatherWarningsData = new JSONObject(data);
        JSONArray warningsData = this.extractWarningDataFromJsonObjectAsAJsonArray(weatherWarningsData);
        ArrayList<SevereWeatherWarning> severeWeatherWarnings = new ArrayList<SevereWeatherWarning>();
        for (int ix = 0; ix < warningsData.length(); ix++) {
            String warningName = warningsData.getJSONObject(ix).getString("event");
            String start = String.valueOf(warningsData.getJSONObject(ix).getLong("start"));
            String end = String.valueOf(warningsData.getJSONObject(ix).getLong("end"));
            String timezone = String.valueOf(weatherWarningsData.getLong("timezone_offset"));
            String details = warningsData.getJSONObject(ix).getString("description");
            SevereWeatherWarning severeWarning = new SevereWeatherWarning(warningName, start, end, details, timezone);
            severeWeatherWarnings.add(severeWarning);
        }
        this.severeWarningComboBoxListProperty.setAll(FXCollections.observableArrayList(severeWeatherWarnings));
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
        JSONArray warningsData = data.optJSONArray(SevereWeatherWarningsPageViewModel.DATA_ALERTS_SECTION);
        return warningsData;
    }

    /**
     * Gets the severe warning combo box list property
     * 
     * @return the severeWarningComboBoxListProperty
     */
    public ListProperty<SevereWeatherWarning> getSevereWarningComboBoxListProperty() {
        return this.severeWarningComboBoxListProperty;
    }
    
    /**
     * Gets the error text string property
     * 
     * @return the errorTextStringProperty
     */
    public StringProperty getErrorTextStringProperty() {
        return this.errorTextStringProperty;
    }

    /**
     * Gets the no warnings for location visibility property
     * 
     * @return the noWarningsForLocationVisibilityProperty
     */
    public BooleanProperty getNoWarningsForLocationVisibilityProperty() {
        return this.noWarningsForLocationVisibilityProperty;
    }

    /**
     * Gets the severe warning combo box visiblity property
     * 
     * @return the severeWarningComboBoxVisibilityProperty
     */
    public BooleanProperty getSevereWarningComboBoxVisibilityProperty() {
        return this.severeWarningComboBoxVisibilityProperty;
    }

    /**
     * Gets the error text visibility property
     * 
     * @return the errorTextVisibilityProperty
     */
    public BooleanProperty getErrorTextVisibilityProperty() {
        return this.errorTextVisibilityProperty;
    }

    /**
     * Sets the error text visibility property
     * 
     * @param errorTextVisibilityPropertyValue the value to set it to
     */
    public void setErrorTextVisibilityProperty(boolean errorTextVisibilityPropertyValue) {
        this.errorTextVisibilityProperty.setValue(errorTextVisibilityPropertyValue);
    }

    /**
     * Sets the error text string property value
     * 
     * @param errorTextStringPropertyValue the value to set it to
     */
    public void setErrorTextStringPropertyValue(String errorTextStringPropertyValue) {
        this.errorTextStringProperty.setValue(errorTextStringPropertyValue);
    }

    /**
     * Sets the severe warning page combo box visibility property value
     * 
     * @param severeWarningComboBoxVisibilityPropertyValue the value to set it to
     */
    public void setSevereWarningComboBoxVisibilityPropertyValue(Boolean severeWarningComboBoxVisibilityPropertyValue) {
        this.severeWarningComboBoxVisibilityProperty.setValue(severeWarningComboBoxVisibilityPropertyValue);
    }

    /**
     * Sets the no warnings for location visibility property value
     */
    private void setNoWarningsForLocationVisibilityPropertyValue() {
        if (this.severeWarningComboBoxListProperty.isEmpty()) {
            this.noWarningsForLocationVisibilityProperty.setValue(true);
        } else {
            this.noWarningsForLocationVisibilityProperty.setValue(false);
        }
    }

    /**
     * Sets the severe warning combo box visibility property value
     */
    private void setSevereWarningComboBoxVisibilityPropertyValue() {
        if (this.severeWarningComboBoxListProperty.isEmpty()) {
            this.setSevereWarningComboBoxVisibilityPropertyValue(false);
        } else {
            this.setSevereWarningComboBoxVisibilityPropertyValue(true);
        }
    }
}
