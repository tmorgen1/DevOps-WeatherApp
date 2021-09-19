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

    private SimpleStringProperty severeWeatherWarningsStringProperty;
    private SimpleStringProperty errorLabelStringProperty;
    private BooleanProperty noWarningsForLocationVisibilityProperty;
    private BooleanProperty severeWarningsAccordionVisibilityProperty;
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
        if (dataRetriever != null) {
            this.severeWeatherWarningsRetriever = dataRetriever;

        } else {
            try {
                this.severeWeatherWarningsRetriever = (SevereWeatherWarningsRetriever) Naming
                        .lookup("rmi://localhost:5000/severe-warnings");
            } catch (Exception exception) {
                System.err.println("Error looking up java rmi binding");
            }
        }
        this.severeWeatherWarningsStringProperty = new SimpleStringProperty();
        this.errorLabelStringProperty = new SimpleStringProperty();
        this.noWarningsForLocationVisibilityProperty = new SimpleBooleanProperty();
        this.severeWarningsAccordionVisibilityProperty = new SimpleBooleanProperty();
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
     * Sets the error error label string property value
     * 
     * @param errorLabelStringPropertyValue
     */
    public void setErrorLabelStringPropertyValue(String errorLabelStringPropertyValue) {
        this.errorLabelStringProperty.setValue(errorLabelStringPropertyValue);
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

    private void setNoWarningsForLocationVisibilityPropertyValue(String data) {
        if (data == null || !data.contains("alerts")) {
            this.noWarningsForLocationVisibilityProperty.setValue(true);
        } else {
            this.noWarningsForLocationVisibilityProperty.setValue(false);
        }
    }

    private void setSevereWarningAccordionVisibilityPropertyValue(String data) {
        if (data == null || !data.contains("alerts")) {
            this.severeWarningsAccordionVisibilityProperty.setValue(false);
        } else {
            this.severeWarningsAccordionVisibilityProperty.setValue(true);
        }
    }

}
