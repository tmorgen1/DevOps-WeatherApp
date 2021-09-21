package edu.westga.weatherapp_gui.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.DateTimeConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;

/**
 * The Severe Weather Warning Titled Pane class for representing this specific
 * type of titled pane.
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarningTitledPane extends TitledPane {

    /**
     * If the warning's name cannot be found the titled pane uses instead
     */
    public static final String UNDETERMINED_WARNING = "Undetermined Warning Type";

    /**
     * If the starting date cannot be found the titled pane uses instead
     */
    public static final String UNDETERMINED_START = "Undetermined Start Date";

    /**
     * If the ending date cannot be found the titled pane uses instead
     */
    public static final String UNDETERMINED_END = "Undetermined End Date";

    /**
     * If the details cannot be found the titled pane uses instead
     */
    public static final String UNDETERMINED_DETAILS = "No Details";

    /**
     * this severe weather warning titled pane
     */
    @FXML
    private TitledPane severeWarningTitledPane;

    /**
     * this severe weather warning titled pane's scroll pane
     */
    @FXML
    private ScrollPane severeWarningScrollPane;

    /**
     * this severe weather warning titled pane's description textbox
     */
    @FXML
    private Text description;

    /**
     * the warning name of the severe weather warning that this titled pane
     * represents
     */
    private String warningName;

    /**
     * the starting date of the severe weather warning that this titled pane
     * represents
     */
    private String startingDate;

    /**
     * the ending date of the severe weather warning that this titled pane
     * represents
     */
    private String endingDate;

    /**
     * the timezone of the severe weather warning that this titled pane represents
     */
    private String timezone;

    /**
     * the details of the severe weather warning that this titled pane
     * represents
     */
    private String details;

    /**
     * the raw data of the severe weather warning that this titled pane
     * represents
     */
    private String rawData;

    /**
     * Creates an instance of the Severe Weather Warning Titled Pane
     * 
     * @param warningName  the warning's name
     * @param startingDate the start date of the warning's period of effect
     * @param endingDate   the end date of the warning's period of effect
     * @param details      a description of the warning
     * @param timezone     the timezone of the warning
     * @param rawData      the original raw data of the warning
     * @throws FileNotFoundException
     */
    public SevereWeatherWarningTitledPane(String warningName, String startingDate, String endingDate, String details,
             String timezone, String rawData) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(App.SEVERE_WARNINGS_TITLED_PANE_VIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.setWarningNameDisplayValue(warningName);
            this.setTimezone(timezone);
            this.setStartingDateDisplayValue(startingDate);
            this.setEndingDateDisplayValue(endingDate);
            this.setDetailsDisplayValue(details);
            this.setDescriptionTextValue();
            this.setRawData(rawData);
        } catch (IOException ex) {
            System.err.println("IO Exception - Error creating severe warning titled pane.");
            ex.printStackTrace();
        }
    }

    /**
     * Gets the name of the severe weather warning
     * 
     * @return the warning's name
     */
    public String getWarningName() {
        return this.warningName;
    }

    /**
     * Gets the String date of the severe weather warning.
     * 
     * @return the warning's starting date
     */
    public String getStartingDate() {
        return this.startingDate;
    }

    /**
     * Gets the ending date of the severe weather warning.
     * 
     * @return the warning's ending date
     */
    public String getEndingDate() {
        return this.endingDate;
    }

    /**
     * Gets the description of the severe weather warning
     * 
     * @return the warning's description
     */
    public Text getDescription() {
        return this.description;
    }

    /**
     * Gets the raw data of the severe weather warning
     * 
     * @return the warning's raw data
     */
    public String getRawData() {
        return this.rawData;
    }

    /**
     * Sets the name of the severe weather warning
     * 
     * @param warningName the name of the severe weather warning
     */
    private void setWarningNameDisplayValue(String warningName) {
        if (warningName == null || warningName.isBlank()) {
            warningName = SevereWeatherWarningTitledPane.UNDETERMINED_WARNING;
        }
        this.warningName = warningName;
        this.severeWarningTitledPane.setText(warningName);
    }

    /**
     * Sets the ending date of the severe weather warning
     * 
     * @param startingDate the starting date of the severe weather warning
     */
    private void setStartingDateDisplayValue(String startingDate) {
        if (startingDate == null || startingDate.isBlank()) {
            startingDate = SevereWeatherWarningTitledPane.UNDETERMINED_START;
        }
        this.startingDate = DateTimeConverter.ConvertUtcToShortDate(Long.parseLong(startingDate), Long.parseLong(this.timezone));
    }

    /**
     * Sets the ending date of the severe weather warning
     * 
     * @param endingDate the ending date of the severe weather warning
     */
    private void setEndingDateDisplayValue(String endingDate) {
        if (endingDate == null || endingDate.isBlank()) {
            endingDate = SevereWeatherWarningTitledPane.UNDETERMINED_END;
        }
        this.endingDate = DateTimeConverter.ConvertUtcToShortDate(Long.parseLong(endingDate), Long.parseLong(this.timezone));
    }

    /**
     * Sets the timezone of the severe weather warning
     * 
     * @param timezone the timezone of the severe weather warning
     */
    private void setTimezone(String timezone) {
        if (timezone == null || timezone.isBlank()) {
            timezone = "0000";
        }
        this.timezone = timezone;
    }

    /**
     * Sets the details of the severe weather warning's descrption.
     * 
     * @param details the details of the severe weather warning.
     */
    private void setDetailsDisplayValue(String details) {
        if (details == null || details.isBlank()) {
            details = SevereWeatherWarningTitledPane.UNDETERMINED_DETAILS;
        }
        this.details = details.replaceAll("\n", " ");
    }

    /**
     * Sets the raw data of the titled pane's severe weather warning data.
     * 
     * @param rawData the raw data of the titled pane's severe weather warning data.
     */
    private void setRawData(String rawData) {
        this.rawData = rawData;
    }

    /**
     * Sets the text content of the titled pane's drop down view.
     */
    private void setDescriptionTextValue() {
        String skipLine = System.lineSeparator() + System.lineSeparator();
        String termSpacer = "\t\t";
        String formattedDescription = String.format(
                "%1$s" + skipLine + "Starting Date: %2$s" + termSpacer + " Ending Date: %3$s" + skipLine + "%4$s",
                this.warningName, this.startingDate, this.endingDate, this.details);
        this.description.setText(formattedDescription);
    }
}
