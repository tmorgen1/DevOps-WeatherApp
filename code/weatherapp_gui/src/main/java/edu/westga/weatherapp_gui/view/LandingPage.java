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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import edu.westga.weatherapp_gui.viewmodel.LandingPageViewModel;

/**
 * Defines the landing page view.
 */
public class LandingPage {

    private LandingPageViewModel viewModel;

    @FXML
    private Pane landingPagePane;

    @FXML
    private TextField locationSearchTextField;

    @FXML
    private ImageView weatherIconImageView;

    @FXML
    private Label weatherDescriptionLabel;

    @FXML
    private Label currentTemperatureLabel;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private JFXButton dailyForecastButton;

    @FXML
    private JFXButton warningsButton;

    @FXML
    private ImageView humidityImageView;

    @FXML
    private ImageView windImageView;

    @FXML
    private Label noWeatherInformationLabel;

    @FXML
    void initialize() {
        this.viewModel = new LandingPageViewModel();
    }

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

    @FXML
    void onDailyForecastClicked(ActionEvent event) {
        //TODO: add navigation to daily forecast page
    }

    @FXML
    void onWarningsClicked(ActionEvent event) {
        //TODO: add navigation to weather warnings page
    }

    private Boolean checkWeatherData(Boolean result) {
        if (!result) {
            this.hideWeatherInformation();
            this.showNoWeatherInformation();
            this.displayNoLocationSnackbar();
        }

        return result;
    }

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

    private void updateCurrentTemperature() {
        String temperature = this.viewModel.getCurrentTemperature();
        String temperatureSuffix = "°F";
        this.currentTemperatureLabel.setText(temperature + temperatureSuffix);
    }

    private void updateCurrentWeatherDescription() {
        String description = this.viewModel.getCurrentWeatherDescription();
        this.weatherDescriptionLabel.setText(description);
    }

    private void updateCurrentWeatherIcon() {
        String iconURL = this.viewModel.getCurrentWeatherIcon();
        Image iconImage = new Image(iconURL);
        this.weatherIconImageView.setImage(iconImage);
    }

    private void updateCurrentWindSpeed() {
        String windSpeed = this.viewModel.getCurrentWindSpeed();
        String windSpeedSuffix = "mi/h";
        this.windSpeedLabel.setText(windSpeed + windSpeedSuffix);
    }

    private void updateCurrentHumidity() {
        String humidity = this.viewModel.getCurrentHumidity();
        String humiditySuffix = "%";
        this.humidityLabel.setText(humidity + humiditySuffix);
    }

    private void showNoWeatherInformation() {
        this.noWeatherInformationLabel.setVisible(true);
    }

    private void hideNoWeatherInformation() {
        this.noWeatherInformationLabel.setVisible(false);
    }

    private void showWeatherInformation() {
        this.weatherIconImageView.setVisible(true);
        this.windImageView.setVisible(true);
        this.humidityImageView.setVisible(true);
        this.windSpeedLabel.setVisible(true);
        this.humidityLabel.setVisible(true);
        this.weatherDescriptionLabel.setVisible(true);
        this.currentTemperatureLabel.setVisible(true);
    }

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
