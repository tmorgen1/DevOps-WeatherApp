package edu.westga.weatherapp_service.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Weather warnings hold the data for a severe weather warning
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarning {

    public static final String UNKNOWN_FORECASTER_NAME = "Unknown Report Origin";
    public static final String UNKNOWN_WARNING = "Unknown Warning Type";
    public static final String UNKNOWN_START = "Unknown Start Date";
    public static final String UNKNOWN_END = "Unknown End Date";
    public static final String UNKNOWN_DESCRIPTION = "No Description";
    public static final String DESCRIPTION_LINE_BREAK = "\n";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-YY 00:00");

    private String forecasterName;
    private String warningName;
    private String start;
    private String end;
    private String description;

    /**
     * Creates a Severe Weather Warning
     * 
     * @param forecasterName the name of the weather warning's forecaster
     * @param warningName    the type of weather warning
     * @param start          the start of the weather warning period
     * @param end            the end of the weather warning period
     * @param description    the description of the weather warning
     * 
     * @precondition none
     * @postcondition getForecasterName().equals(forecasterName) &&
     *                getWarningName().equals(warningName) &&
     *                getStart().equals(start) && getEnd().equals(end) &&
     *                getDescription().equals(description)
     */
    public SevereWeatherWarning(String forecasterName, String warningName, String start, String end,
            String description) {
        if (forecasterName == null || forecasterName.isBlank()) {
            forecasterName = SevereWeatherWarning.UNKNOWN_FORECASTER_NAME;
        }
        if (warningName == null || warningName.isBlank()) {
            warningName = SevereWeatherWarning.UNKNOWN_WARNING;
        }
        if (start == null || start.isBlank()) {
            start = SevereWeatherWarning.UNKNOWN_START;
        }
        if (end == null || end.isBlank()) {
            end = SevereWeatherWarning.UNKNOWN_END;
        }
        if (description == null || description.isBlank()) {
            description = SevereWeatherWarning.UNKNOWN_DESCRIPTION;
        }
        this.forecasterName = forecasterName;
        this.warningName = warningName;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    /**
     * Gets name of the forcast office that forecasted the warning
     * 
     * @return the reporterName
     */
    public String getForecasterName() {
        return this.forecasterName;
    }

    /**
     * Gets the name of the warning
     * 
     * @return the warningName
     */
    public String getWarningName() {
        return this.warningName;
    }

    /**
     * Gets the time the warning starts effect
     * 
     * @return the start
     */
    public String getStart() {
        if (!this.start.equals(SevereWeatherWarning.UNKNOWN_START)) {
            Date warningStart = new Date(Long.parseLong(this.start));
            this.start = SevereWeatherWarning.DATE_FORMAT.format(warningStart);
        }
        return this.start;
    }

    /**
     * Gets the time the warining ends effect
     * 
     * @return the end
     */
    public String getEnd() {
        if (!this.end.equals(SevereWeatherWarning.UNKNOWN_END)) {
            Date warningEnd = new Date(Long.parseLong(this.end));
            this.end = SevereWeatherWarning.DATE_FORMAT.format(warningEnd);
        }
        return this.end;
    }

    /**
     * Gets the description of th warning
     * 
     * @return the description
     */
    public String getDescription() {
        if (this.description.contains(SevereWeatherWarning.DESCRIPTION_LINE_BREAK)) {
            this.description = this.description.replace(SevereWeatherWarning.DESCRIPTION_LINE_BREAK,
                    System.lineSeparator());
        }
        return this.description;
    }
}