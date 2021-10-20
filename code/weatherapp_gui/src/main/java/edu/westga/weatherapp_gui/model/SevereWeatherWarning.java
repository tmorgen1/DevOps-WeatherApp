package edu.westga.weatherapp_gui.model;

/**
 * A class to represent a severe weather warning
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarning {

    /**
     * If the warning's name cannot be found use this instead
     */
    public static final String UNDETERMINED_WARNING = "Undetermined Warning Type";

    /**
     * If the starting date cannot be found use this instead
     */
    public static final String UNDETERMINED_START = "Undetermined Start Date";

    /**
     * If the ending date cannot be found use this instead
     */
    public static final String UNDETERMINED_END = "Undetermined End Date";

    /**
     * If the details cannot be found use this instead
     */
    public static final String UNDETERMINED_DETAILS = "No Details";

    /**
     * If the timezone cannot be found use this instead
     */
    public static final String DEFAULT_TIMEZONE = "0000";

    /**
     * The windows nextline character
     */
    public static final String WINDOWS_NEXTLINE = "\n";

    /**
     * the warning name of the severe weather warning that this represents
     */
    private String warningName;

    /**
     * the starting date of the severe weather warning that this represents
     */
    private String startingDate;

    /**
     * the ending date of the severe weather warning that this represents
     */
    private String endingDate;

    /**
     * the timezone of the severe weather warning that this represents
     */
    private String timezone;

    /**
     * the details of the severe weather warning that this represents
     */
    private String details;

    /**
     * the raw data of the severe weather warning that this represents
     */
    private String rawData;

    /**
     * Creates an instance of the Severe Weather Warning
     * 
     * @param warningName  the warning's name
     * @param startingDate the start date of the warning's period of effect
     * @param endingDate   the end date of the warning's period of effect
     * @param details      a description of the warning
     * @param timezone     the timezone of the warning
     * @param rawData     the raw data of the warning
     */
    public SevereWeatherWarning(String warningName, String startingDate, String endingDate, String details,
            String timezone, String rawData) {
        
        this.setFieldValues(warningName, startingDate, endingDate, details, timezone, rawData);
    }

    /**
     * Sets this severe weather warning's field values
     */
    private void setFieldValues(String warningName, String startingDate, String endingDate, String details,
            String timezone, String rawData) {
        this.setWarningName(warningName);
        this.setTimezone(timezone);
        this.setStartingDate(startingDate);
        this.setEndingDate(endingDate);
        this.setDetails(details);
        this.rawData = rawData;
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
     * Gets the details of the severe weather warning.
     * 
     * @return the warning's details
     */
    public String getDetails() {
        return this.details;
    }

    /**
     * Gets the raw data of the severe weather warning.
     * 
     * @return the warning's raw data details
     */
    public String getRawData() {
        return this.rawData;
    }

    /**
     * Sets the name of the severe weather warning
     * 
     * @param warningName the name of the severe weather warning
     */
    private void setWarningName(String warningName) {
        if (warningName == null || warningName.isBlank()) {
            warningName = SevereWeatherWarning.UNDETERMINED_WARNING;
        }
        this.warningName = warningName;
    }

    /**
     * Sets the ending date of the severe weather warning
     * 
     * @param startingDate the starting date of the severe weather warning
     */
    private void setStartingDate(String startingDate) {
        if (startingDate == null || startingDate.isBlank()) {
            startingDate = SevereWeatherWarning.UNDETERMINED_START;
        }
        this.startingDate = DateTimeConverter.ConvertUtcToShortDate(Long.parseLong(startingDate),
                Long.parseLong(this.timezone));
    }

    /**
     * Sets the ending date of the severe weather warning
     * 
     * @param endingDate the ending date of the severe weather warning
     */
    private void setEndingDate(String endingDate) {
        if (endingDate == null || endingDate.isBlank()) {
            endingDate = SevereWeatherWarning.UNDETERMINED_END;
        }
        this.endingDate = DateTimeConverter.ConvertUtcToShortDate(Long.parseLong(endingDate),
                Long.parseLong(this.timezone));
    }

    /**
     * Sets the timezone of the severe weather warning
     * 
     * @param timezone the timezone of the severe weather warning
     */
    private void setTimezone(String timezone) {
        if (timezone == null || timezone.isBlank()) {
            timezone = SevereWeatherWarning.DEFAULT_TIMEZONE;
        }
        this.timezone = timezone;
    }

    /**
     * Sets the details of the severe weather warning's descrption.
     * 
     * @param details the details of the severe weather warning.
     */
    private void setDetails(String details) {
        if (details == null || details.isBlank()) {
            details = SevereWeatherWarning.UNDETERMINED_DETAILS;
        }
        this.details = details.replaceAll(SevereWeatherWarning.WINDOWS_NEXTLINE, " ");
    }
}
