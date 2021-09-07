package edu.westga.weatherapp_gui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.LandingPageViewModel;

/**
 * Defines the landing page view.
 */
public class LandingPage {

    /**
     * The landing page view model
     */
    private LandingPageViewModel viewModel;

    /**
     * The landing page pane
     */
    @FXML
    private Pane landingPagePane;

    /**
     * The location searching text field
     */
    @FXML
    private TextField locationSearchTextField;

    /**
     * The weather icon image view
     */
    @FXML
    private ImageView weatherIconImageView;

    /**
     * The weather description label
     */
    @FXML
    private Label weatherDescriptionLabel;

    /**
     * The current temperature label
     */
    @FXML
    private Label currentTemperatureLabel;

    /**
     * The wind speed label
     */
    @FXML
    private Label windSpeedLabel;

    /**
     * The humidity label
     */
    @FXML
    private Label humidityLabel;

    /**
     * The daily forecast jfx button
     */
    @FXML
    private JFXButton dailyForecastButton;

    /**
     * The warnings jfx button
     */
    @FXML
    private JFXButton warningsButton;

    /**
     * The humidity image view
     */
    @FXML
    private ImageView humidityImageView;

    /**
     * The wind image view
     */
    @FXML
    private ImageView windImageView;

    /**
     * The no weather information available label
     */
    @FXML
    private Label noWeatherInformationLabel;

    /**
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.viewModel = new LandingPageViewModel(null, null);
    }

    /**
     * Event handler for the location search text field. Handles the on enter
     * pressed key event. Updates all current weather data information if
     * successful.
     * 
     * @param event - the enter key event
     */
    @FXML
    void onEnterPressed(KeyEvent event) {
        if (!event.getCode().equals(KeyCode.ENTER)) {
            return;
        }

        String city = this.locationSearchTextField.getText();
        Boolean result = this.viewModel.getWeatherDataByCity(city);
        if (!this.checkWeatherData(result)) {
            return;
        }

        this.updateCurrentTemperature();
        this.updateCurrentWeatherDescription();
        this.updateCurrentWeatherIcon();
        this.updateCurrentWindSpeed();
        this.updateCurrentHumidity();

        this.showWeatherInformation();
        this.hideNoWeatherInformation();
    }

    /**
     * Event handler for the daily forecast button. Handles the on clicked action
     * event. Navigates to the daily forecast page.
     * 
     * @param event - The action event
     */
    @FXML
    void onDailyForecastClicked(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowGenerator.changeScene(currentStage, App.DAILY_FORECAST_VIEW, App.DAILY_FORECAST_PAGE_TITLE);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error switching scenes");
        }
    }

    /**
     * Event handler for the warnings button. Handles the on clicked action event.
     * Navigates to the warnings page.
     * 
     * @param event - The action event
     */
    @FXML
    void onWarningsClicked(ActionEvent event) {
        // TODO: add navigation to weather warnings page
    }

    /**
     * Checks if the given weather data is valid. Displays the no weather
     * information message and hides current weather data.
     * 
     * @param result - the retrieved weather data result
     * @return the retrieved weather data result
     */
    private Boolean checkWeatherData(Boolean result) {
        if (!result) {
            this.hideWeatherInformation();
            this.showNoWeatherInformation();
            this.displayNoLocationSnackbar();
        }

        return result;
    }

    /**
     * Displays the no location found snackbar.
     */
    private void displayNoLocationSnackbar() {
        JFXSnackbar snackbar = new JFXSnackbar(this.landingPagePane);
        StackPane pane = new StackPane();
        Label label = new Label("No Location Found");
        pane.setMinSize(this.landingPagePane.getWidth(), 50);
        pane.getChildren().add(label);
        StackPane.setAlignment(label, Pos.CENTER);
        pane.setStyle("-fx-background-color: #48484A");
        label.setStyle("-fx-font-size: 16; -fx-text-fill: #FFFFFF; -fx-font-family: Segoe UI");
        snackbar.enqueue(new SnackbarEvent(pane));
    }

    /**
     * Updates the current temperature label
     */
    private void updateCurrentTemperature() {
        //TODO: Implement appropriate temperature suffix based on current weather data

        String temperature = this.viewModel.getCurrentTemperature();
        String temperatureSuffix = "°F";
        this.currentTemperatureLabel.setText(temperature + temperatureSuffix);
    }

    /**
     * Updates the current weather description label
     */
    private void updateCurrentWeatherDescription() {
        String description = this.viewModel.getCurrentWeatherDescription();
        this.weatherDescriptionLabel.setText(description);
    }

    /**
     * Updates the current weather icon
     */
    private void updateCurrentWeatherIcon() {
        String iconURL = this.viewModel.getCurrentWeatherIcon();
        Image iconImage = new Image(iconURL);
        this.weatherIconImageView.setImage(iconImage);
    }

    /**
     * Updates the current wind speed label
     */
    private void updateCurrentWindSpeed() {
        String windSpeed = this.viewModel.getCurrentWindSpeed();
        String windSpeedSuffix = "mi/h";
        this.windSpeedLabel.setText(windSpeed + windSpeedSuffix);
    }

    /**
     * Updates the current humidity label
     */
    private void updateCurrentHumidity() {
        String humidity = this.viewModel.getCurrentHumidity();
        String humiditySuffix = "%";
        this.humidityLabel.setText(humidity + humiditySuffix);
    }

    /**
     * Sets the no weather information label to be visible
     */
    private void showNoWeatherInformation() {
        this.noWeatherInformationLabel.setVisible(true);
    }

    /**
     * Sets the no weather information label to be hidden
     */
    private void hideNoWeatherInformation() {
        this.noWeatherInformationLabel.setVisible(false);
    }

    /**
     * Sets all the current weather information to be visible
     */
    private void showWeatherInformation() {
        this.weatherIconImageView.setVisible(true);
        this.windImageView.setVisible(true);
        this.humidityImageView.setVisible(true);
        this.windSpeedLabel.setVisible(true);
        this.humidityLabel.setVisible(true);
        this.weatherDescriptionLabel.setVisible(true);
        this.currentTemperatureLabel.setVisible(true);
    }

    /**
     * Sets all the current weather information to be hidden
     */
    private void hideWeatherInformation() {
        this.weatherIconImageView.setVisible(false);
        this.windImageView.setVisible(false);
        this.humidityImageView.setVisible(false);
        this.windSpeedLabel.setVisible(false);
        this.humidityLabel.setVisible(false);
        this.weatherDescriptionLabel.setVisible(false);
        this.currentTemperatureLabel.setVisible(false);
    }
}
