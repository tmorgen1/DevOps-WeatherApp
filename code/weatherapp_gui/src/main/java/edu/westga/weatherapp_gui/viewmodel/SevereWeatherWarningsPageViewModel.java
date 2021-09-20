package edu.westga.weatherapp_gui.viewmodel;

import java.rmi.Naming;
import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.SevereWeatherWarningsRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * The view model class for the severe weather warnings page.
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarningsPageViewModel {

    /**
     * the severe weather warnings string property for use in bindings
     */
    private SimpleStringProperty severeWeatherWarningsStringProperty;

    /**
     * the error label string property for use in bindings
     */
    private SimpleStringProperty errorLabelStringProperty;

    /**
     * the no warnings for location visibility property for use in bindings
     */
    private BooleanProperty noWarningsForLocationVisibilityProperty;

    /**
     * the severe warnings accordion visibility property for use in bindings
     */
    private BooleanProperty severeWarningsAccordionVisibilityProperty;

    /**
     * the  error label visibility property for use in bindings
     */
    private BooleanProperty errorLabelVisibilityProperty;

    /**
     * the severe weather warnings retriever
     */
    private SevereWeatherWarningsRetriever severeWeatherWarningsRetriever;

    /**
     * Creates an instance of the Severe Weather Warnings Page View Model
     * 
     * 
     * @param dataRetriever the data retriever
     * @precondition none
     * @postcondition getsevereWeatherWarningsObjectProperty() != null
     */
    public SevereWeatherWarningsPageViewModel(SevereWeatherWarningsRetriever dataRetriever) {
        this.severeWeatherWarningsStringProperty = new SimpleStringProperty();
        this.errorLabelStringProperty = new SimpleStringProperty();
        this.errorLabelVisibilityProperty = new SimpleBooleanProperty();
        this.noWarningsForLocationVisibilityProperty = new SimpleBooleanProperty();
        this.severeWarningsAccordionVisibilityProperty = new SimpleBooleanProperty();
        if (dataRetriever != null) {
            this.severeWeatherWarningsRetriever = dataRetriever;
        } else {
            try {
                this.severeWeatherWarningsRetriever = (SevereWeatherWarningsRetriever) Naming
                        .lookup("rmi://localhost:5000/severe-warnings");
            } catch (Exception ex) {
                this.setErrorLabelStringPropertyValue("Error looking up java rmi binding");
                this.setErrorLabelVisibilityProperty(true);
            }
        }
    }

    /**
     * Sets the string value of the severe weather warnings object property value
     * 
     * @precondition location != null && units != null
     * @postcondition getsevereWeatherWarningsObjectProperty() != null &&
     *                (getsevereWeatherWarningsObjectProperty().equals(data) ||
     *                getsevereWeatherWarningsObjectProperty()equals(""))
     * 
     * @param location the location for the warnings
     * @param units    the unit of measument the warning should come in
     * @throws IllegalArgumentException
     * @throws RemoteException
     */
    public void setsevereWeatherWarningsObjectPropertyValue(WeatherLocation location, MeasurementUnits units) {
        if (location == null) {
            throw new IllegalArgumentException("The location cannot be null.");
        }
        String data;
        try {
            data = this.severeWeatherWarningsRetriever.getSevereWeatherWarningsForLocation(location.getLatitude(),
                    location.getLongitude(), units);
            this.severeWeatherWarningsStringProperty.setValue(data);
            this.setNoWarningsForLocationVisibilityPropertyValue(data);
            this.setSevereWarningAccordionVisibilityPropertyValue(data);
        } catch (RemoteException | IllegalArgumentException ex) {
            this.setErrorLabelStringPropertyValue(ex.getMessage());
        }
    }

    /**
     * Gets the error label string property
     * 
     * @return the errorLabelStringProperty
     */
    public SimpleStringProperty getErrorLabelStringProperty() {
        return this.errorLabelStringProperty;
    }

    /**
     * Gets the severe weather warnings string property
     * 
     * @return returns the severe weather warnings string property
     */
    public SimpleStringProperty getsevereWeatherWarningsStringProperty() {
        return this.severeWeatherWarningsStringProperty;
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
     * Gets the severe warnings accordion visibility property
     * 
     * @return the severeWarningsAccordionVisibilityProperty
     */
    public BooleanProperty getSevereWarningAccordionVisibilityProperty() {
        return this.severeWarningsAccordionVisibilityProperty;
    }

    /**
     * Gets the error label visibility property
     * 
     * @return the errorLabelVisibilityProperty
     */
    public BooleanProperty getErrorLabelVisibilityProperty() {
        return this.errorLabelVisibilityProperty;
    }

    /**
     * Sets the error label visibility property
     * 
     * @param errorLabelVisibilityPropertyValue the value to set it to
     */
    public void setErrorLabelVisibilityProperty(boolean errorLabelVisibilityPropertyValue) {
        this.errorLabelVisibilityProperty.setValue(errorLabelVisibilityPropertyValue);
    }

    /**
     * Sets the error error label string property value
     * 
     * @param errorLabelStringPropertyValue the value to set it to
     */
    public void setErrorLabelStringPropertyValue(String errorLabelStringPropertyValue) {
        this.errorLabelStringProperty.setValue(errorLabelStringPropertyValue);
    }

    /**
     * Sets the no warnings for location visibility property value
     * 
     * @param data the data that detemines the value of the property
     */
    private void setNoWarningsForLocationVisibilityPropertyValue(String data) {
        if (data == null || !data.contains("alerts")) {
            this.noWarningsForLocationVisibilityProperty.setValue(true);
        } else {
            this.noWarningsForLocationVisibilityProperty.setValue(false);
        }
    }

    /**
     * Sets the severe warning accordion visibility property value
     * 
     * @param data the data that detemines the value of the property
     */
    private void setSevereWarningAccordionVisibilityPropertyValue(String data) {
        if (data == null || !data.contains("alerts")) {
            this.severeWarningsAccordionVisibilityProperty.setValue(false);
        } else {
            this.severeWarningsAccordionVisibilityProperty.setValue(true);
        }
    }
}
