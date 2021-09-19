package edu.westga.weatherapp_gui.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.westga.weatherapp_gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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

    public static final String UNDETERMINED_WARNING = "Undetermined Warning Type";
    public static final String UNDETERMINED_START = "Undetermined Start Date";
    public static final String UNDETERMINED_END = "Undetermined End Date";
    public static final String UNDETERMINED_DESCRIPTION = "No Description";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-YY 00:00");

    @FXML
    private TitledPane severeWarningTitledPane;

    @FXML
    private Label warningName;

    @FXML
    private Label startingDate;

    @FXML
    private Text description;

    @FXML
    private Label endingDate;

    private String rawData;

   /**
    * TODO fix date parsing errors
    * TODO change arrow to icons
    * TODO display loading wheel until alerts are loaded
    * TODO set titled pane background
    * TODO change anchor pane to scroll pane
    * TODO Finish error label message and displaying add it toe the fxml as well
    */

    /**
     * Creates an instance of the Severe Weather Warning Titled Pane
     * 
     * @param warningName  the warning's name
     * @param startingDate the start date of the warning's period of effect
     * @param endingDate   the end date of the warning's period of effect
     * @param description  a description of the warning
     * @param rawData      the original raw data of the warning
     * @throws FileNotFoundException
     */
    public SevereWeatherWarningTitledPane(String warningName, String startingDate, String endingDate,
            String description, String rawData) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(App.SEVERE_WARNINGS_TITLED_PANE_VIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.setWarningNameTextValue(warningName);
            this.setStartingDateTextValue(startingDate);
            this.setEndingDateTextValue(endingDate);
            this.setDescriptionTextValue(description);
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
    public Label getWarningName() {
        return this.warningName;
    }

    /**
     * Gets the starting date of the severe weather warning.
     * 
     * @return the warning's starting date
     */
    public Label getStartingDate() {
        return this.startingDate;
    }

    /**
     * Gets the ending date of the severe weather warning.
     * 
     * @return the warning's ending date
     */
    public Label getEndingDate() {
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

    private void setWarningNameTextValue(String warningName) {
        if (warningName == null || warningName.isBlank()) {
            warningName = SevereWeatherWarningTitledPane.UNDETERMINED_WARNING;
        }
        this.warningName.setText(warningName);
        this.severeWarningTitledPane.setText(warningName);
    }

    private void setStartingDateTextValue(String startingDate) {
        if (startingDate == null || startingDate.isBlank()) {
            startingDate = SevereWeatherWarningTitledPane.UNDETERMINED_START;
        }
        this.startingDate.setText(this.parseDateToHumanReadableDate(startingDate));
    }

    private void setEndingDateTextValue(String endingDate) {
        if (endingDate == null || endingDate.isBlank()) {
            endingDate = SevereWeatherWarningTitledPane.UNDETERMINED_END;
        }
        this.endingDate.setText(this.parseDateToHumanReadableDate(endingDate));
    }
    
    private String parseDateToHumanReadableDate(String date) {
        Date warningPeriodDate = new Date(Long.parseLong(date));
        return SevereWeatherWarningTitledPane.DATE_FORMAT.format(warningPeriodDate);
    }

    private void setDescriptionTextValue(String description) {
        if (description == null || description.isBlank()) {
            description = SevereWeatherWarningTitledPane.UNDETERMINED_DESCRIPTION;
        }
        this.description.setText(description);
    }

    private void setRawData(String rawData) {
        this.rawData = rawData;
    }
}
