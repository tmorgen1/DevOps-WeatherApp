package edu.westga.weatherapp_gui.viewmodel;

import edu.westga.weatherapp_gui.model.SevereWeatherWarning;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The view model class for the severe weather warnings titled pane.
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarningPageViewModel {

    /**
     * null pointer because of no severe weather warning was selected exception thrown
     */
    public static final String NULL_WARNING_EXCEPTION_THROWN = "No Severe Weather Warning selected";

    /**
     * the severe weather warining to get information from
     */
    private static ObjectProperty<SevereWeatherWarning> severeWeatherWarningObjectProperty = new SimpleObjectProperty<SevereWeatherWarning>();

    /**
     * the error Text visibility property for use in bindings
     */
    private BooleanProperty errorTextVisibilityProperty;

    /**
     * the error text string property for use in bindings
     */
    private StringProperty errorTextStringProperty;

    /**
     * the warning name text string property for use in bindings
     */
    private StringProperty warningNameTextStringProperty;

    /**
     * the description text string property for use in bindings
     */
    private StringProperty descriptionTextStringProperty;

    /**
     * Creates an instance of the Severe Weather Warning Page View Model
     */
    public SevereWeatherWarningPageViewModel() {
        this.warningNameTextStringProperty = new SimpleStringProperty();
        this.descriptionTextStringProperty = new SimpleStringProperty();
        this.errorTextStringProperty = new SimpleStringProperty();
        this.errorTextVisibilityProperty = new SimpleBooleanProperty();
        try {
            this.setWarningNameTextStringPropertyValue();
            this.setDescriptionTextStringPropertyValue();
        } catch (NullPointerException ex) {
            this.setErrorTextStringPropertyValue(SevereWeatherWarningPageViewModel.NULL_WARNING_EXCEPTION_THROWN);
            this.setErrorTextVisibilityProperty(true);
        }
    }

    /**
     * Gets the warning name text string property
     * 
     * @return the warningNameTextStringProperty
     */
    public StringProperty getWarningNameTextStringProperty() {
        return this.warningNameTextStringProperty;
    }

    /**
     * Sets the warning name text string property value
     */
    private void setWarningNameTextStringPropertyValue() {
        this.warningNameTextStringProperty.setValue(
                SevereWeatherWarningPageViewModel.severeWeatherWarningObjectProperty.getValue().getWarningName());
    }

    /**
     * Gets the description text property
     * 
     * @return the descriptionTextStringProperty
     */
    public StringProperty getDescriptionTextProperty() {
        return this.descriptionTextStringProperty;
    }

    /**
     * Sets the description text property value
     */
    private void setDescriptionTextStringPropertyValue() {
        String skipLine = System.lineSeparator() + System.lineSeparator();
        String termSpacer = "\t\t";
        String formattedDescription = String.format(
                "%1$s" + skipLine + "Starting Date: %2$s" + termSpacer + " Ending Date: %3$s" + skipLine + "%4$s",
                SevereWeatherWarningPageViewModel.severeWeatherWarningObjectProperty.getValue().getWarningName(),
                SevereWeatherWarningPageViewModel.severeWeatherWarningObjectProperty.getValue().getStartingDate(),
                SevereWeatherWarningPageViewModel.severeWeatherWarningObjectProperty.getValue().getEndingDate(),
                SevereWeatherWarningPageViewModel.severeWeatherWarningObjectProperty.getValue().getDetails());
        this.descriptionTextStringProperty.setValue(formattedDescription);
    }

    /**
     * Sets the severe weather warning object property value
     * 
     * @param warning the value to set it to
     */
    public static void setSevereWeatherWarningObjectPropertyValue(SevereWeatherWarning warning) {
        SevereWeatherWarningPageViewModel.severeWeatherWarningObjectProperty.setValue(warning);
    }

    /**
     * Gets the severe weather warning object property
     * 
     * @return the severeWeatherWarningObjectProperty
     */
    public static ObjectProperty<SevereWeatherWarning> getSevereWeatherWarningObjectProperty() {
        return SevereWeatherWarningPageViewModel.severeWeatherWarningObjectProperty;
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

}
