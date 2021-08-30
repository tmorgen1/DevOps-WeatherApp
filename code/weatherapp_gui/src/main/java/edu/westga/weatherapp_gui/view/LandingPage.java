package edu.westga.weatherapp_gui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
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
    private JFXSnackbar noLocationSnackbar;

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
    }

    private Boolean checkWeatherData(Boolean result) {
        if (!result) {
            JFXSnackbarLayout layout = new JFXSnackbarLayout("No Location Found");
            layout.setStyle("-fx-background-color: #E5E5E5; -fx-font-size: 16");
            this.noLocationSnackbar.fireEvent(new SnackbarEvent(layout));
        }

        return result;
    }

    private void updateCurrentTemperature() {
        String temperature = this.viewModel.getCurrentTemperature();
        String formattedTemperature = temperature + "°F";
        this.currentTemperatureLabel.setText(formattedTemperature);
    }

    private void updateCurrentWeatherDescription() {
        String description = this.viewModel.getCurrentWeatherDescription();
        this.weatherDescriptionLabel.setText(description);
    }

    private void updateCurrentWeatherIcon() {
        String iconURL = this.viewModel.getCurrentWeatherIcon();
        System.out.println(iconURL);
        Image iconImage = new Image(iconURL);
        this.weatherIconImageView.setImage(iconImage);
    }

}
