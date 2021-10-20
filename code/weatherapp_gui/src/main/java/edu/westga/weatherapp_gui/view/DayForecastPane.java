package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The custom day forecast pane component
 * 
 * @author Michael Pavich
 */
public class DayForecastPane extends Pane {

    /**
     * The day of the week label
     */
    @FXML
    private Label dayOfWeekLabel;

    /**
     * The date label
     */
    @FXML
    private Label dateLabel;

    /**
     * The maximum temperature label
     */
    @FXML
    private Label maxTemperatureLabel;

    /**
     * The minimum temperature label
     */
    @FXML
    private Label minTemperatureLabel;

    /**
     * The weather icon image view
     */
    @FXML
    private ImageView weatherIconImageView;

    /**
     * Creates an instance of the day forecast pane and loads it
     * 
     * @param dayOfWeek - the initial day of the week
     * @param date - the initial date
     * @param maxTemp - the initial max temp
     * @param minTemp - the initial min temp
     * @param weatherIconUrl - the initial weather icon url
     * @param loader - the fxml loader
     */
    public DayForecastPane(String dayOfWeek, String date, String maxTemp, String minTemp, String weatherIconUrl, FXMLLoader loader) {
        loader.setRoot(this);
        loader.setController(this);
        
        try {
            loader.load();
            this.setDayOfTheWeekLabel(dayOfWeek);
            this.setDateLabel(date);
            this.setMaxTemperatureLabel(maxTemp);
            this.setMinTemperatureLabel(minTemp);
            this.setWeatherIconImageView(weatherIconUrl);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error loading day forecast pane component");
        }
    }

    /**
     * Sets the day of the week label to the specified day
     * 
     * @param dayOfWeek - the new day of the week
     */
    public void setDayOfTheWeekLabel(String dayOfWeek) {
        this.dayOfWeekLabel.setText(dayOfWeek);
    }

    /**
     * Sets the date label to the specified date
     * 
     * @param date - the new date
     */
    public void setDateLabel(String date) {
        this.dateLabel.setText(date);
    }

    /**
     * Sets the maximum temperature label to the specified temperature
     * 
     * @param temperature - the new temperature
     */
    public void setMaxTemperatureLabel(String temperature) {
        this.maxTemperatureLabel.setText(temperature);
    }

    /**
     * Sets the minimum temperature label to the specified temperature
     * 
     * @param temperature - the new temperature
     */
    public void setMinTemperatureLabel(String temperature) {
        this.minTemperatureLabel.setText(temperature);
    }

    /**
     * Sets the weather icon image view to the specified url location
     * 
     * @param url - the new image url
     */
    public void setWeatherIconImageView(String url) {
        Image image = new Image(url);
        this.weatherIconImageView.setImage(image);
    }
}
