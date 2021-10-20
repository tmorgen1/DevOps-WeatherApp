package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The custom hourly forecast pane component
 * 
 * @author Michael Pavich
 */
public class HourlyInfoPane extends Pane {

    /**
     * The weather icon image view
     */
    @FXML
    private ImageView iconImageView;

    /**
     * The time label
     */
    @FXML
    private Label timeLabel;

    /**
     * The temperature label
     */
    @FXML
    private Label temperatureLabel;

    /**
     * Creates an instance of the hourly forecast pane and loads it
     * 
     * @param temperature - the temperature for the hour
     * @param time - the time of the hour
     * @param weatherIconUrl - the weather icon for the hour
     * @param loader - the fxml loader
     */
    public HourlyInfoPane(String temperature, String time, String weatherIconUrl, FXMLLoader loader) {
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            this.setTemperatureLabel(temperature);
            this.setTimeLabel(time);
            this.setIconImageView(weatherIconUrl);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error loading hourly forecast pane component");
        }
    }

    /**
     * Sets the weather icon image view to the specified url location
     * 
     * @param url - the new image url
     */
    public void setIconImageView(String url) {
        Image image = new Image(url);
        this.iconImageView.setImage(image);
    }

    /**
     * Sets the temperature label to the specified temperature
     * 
     * @param temperature - the new temperature
     */
    public void setTemperatureLabel(String temperature) {
        this.temperatureLabel.setText(temperature);
    }

    /**
     * Sets the time label to the specified time
     * 
     * @param time - the new time
     */
    public void setTimeLabel(String time) {
        this.timeLabel.setText(time);
    }
}
