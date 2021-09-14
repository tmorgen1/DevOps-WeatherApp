package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import edu.westga.weatherapp_gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The custom day forecast pane component
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
     */
    public DayForecastPane(String dayOfWeek, String date, String maxTemp, String minTemp, String weatherIconUrl) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(App.DAY_FORECAST_PANE_VIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
            this.SetDayOfTheWeekLabel(dayOfWeek);
            this.SetDateLabel(date);
            this.SetMaxTemperatureLabel(maxTemp);
            this.SetMinTemperatureLabel(minTemp);
            this.SetWeatherIconImageView(weatherIconUrl);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error loading day forecast pane component");
        }
    }

    /**
     * Sets the day of the week label to the specified day
     * 
     * @param dayOfWeek - the new day of the week
     */
    private void SetDayOfTheWeekLabel(String dayOfWeek) {
        this.dayOfWeekLabel.setText(dayOfWeek);
    }

    /**
     * Sets the date label to the specified date
     * 
     * @param date - the new date
     */
    private void SetDateLabel(String date) {
        this.dateLabel.setText(date);
    }

    /**
     * Sets the maximum temperature label to the specified temperature
     * 
     * @param temperature - the new temperature
     */
    private void SetMaxTemperatureLabel(String temperature) {
        this.maxTemperatureLabel.setText(temperature);
    }

    /**
     * Sets the minimum temperature label to the specified temperature
     * 
     * @param temperature - the new temperature
     */
    private void SetMinTemperatureLabel(String temperature) {
        this.minTemperatureLabel.setText(temperature);
    }

    /**
     * Sets the weather icon image view to the specified url location
     * 
     * @param url - the new image url
     */
    private void SetWeatherIconImageView(String url) {
        Image image = new Image(url);
        this.weatherIconImageView.setImage(image);
    }
}
