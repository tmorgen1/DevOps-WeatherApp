package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * The month selection page that handles the interaction with the ui.
 * 
 * @author Michael Pavich
 */
public class MonthSelectionPage {

    @FXML
    private Pane monthSelectionPane;

    @FXML
    private ImageView backArrowImageView;

    @FXML
    private Menu settingMenu;

    @FXML
    private CheckMenuItem fahrenheitCheckMenuItem;

    @FXML
    private CheckMenuItem celsiusCheckMenuItem;

    @FXML
    private CheckMenuItem kelvinCheckMenuItem;

    @FXML
    private JFXButton januaryButton;

    @FXML
    private JFXButton februaryButton;

    @FXML
    private JFXButton marchButton;

    @FXML
    private JFXButton aprilButton;

    @FXML
    private JFXButton mayButton;

    @FXML
    private JFXButton juneButton;

    @FXML
    private JFXButton julyButton;

    @FXML
    private JFXButton augustButton;

    @FXML
    private JFXButton septermberButton;

    @FXML
    private JFXButton octoberButton;

    @FXML
    private JFXButton novemberButton;

    @FXML
    private JFXButton decemberButton;

    @FXML
    void initialize() {
        Platform.runLater(() -> this.monthSelectionPane.requestFocus());
        this.setMeasurementSettings();
    }

    @FXML
    void onJanuaryButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(1, event);
    }
    
    @FXML
    void onFebruaryButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(2, event);
    }

    @FXML
    void onMarchButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(3, event);
    }

    @FXML
    void onAprilButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(4, event);
    }

    @FXML
    void onMayButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(5, event);
    }

    @FXML
    void onJuneButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(6, event);
    }

    @FXML
    void onJulyButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(7, event);
    }

    @FXML
    void onAugustButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(8, event);
    }

    @FXML
    void onSeptemberButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(9, event);
    }

    @FXML
    void onOctoberButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(10, event);
    }

    @FXML
    void onNovemberButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(11, event);
    }

    @FXML
    void onDecemberButtonClicked(ActionEvent event) {
        this.navigateToStatisticalWeatherDataPage(12, event);
    }

    @FXML
    void onBackArrowClicked(MouseEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowGenerator.changeScene(currentStage, App.LANDING_PAGE_VIEW, App.LANDING_PAGE_TITLE);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error switching scenes");
        }
    }

    @FXML
    void onCelsiusSelected(ActionEvent event) {
        this.setAllCheckMenuItemsFalse();
        this.celsiusCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Metric);
    }

    @FXML
    void onFahrenheitSelected(ActionEvent event) {
        this.setAllCheckMenuItemsFalse();
        this.fahrenheitCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Imperial);
    }

    @FXML
    void onKelvinSelected(ActionEvent event) {
        this.setAllCheckMenuItemsFalse();
        this.kelvinCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Kelvin);
    }

    private void navigateToStatisticalWeatherDataPage(int month, ActionEvent event) {
        CurrentWeatherInformation.setStatisticalMonthSelected(month);
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowGenerator.changeScene(currentStage, App.STATISTICAL_WEATHER_PAGE_VIEW, App.STATISTICAL_WEATHER_PAGE_TITLE);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error switching scenes");
        }
    }

    /**
     * Sets all of the check menu items to be false.
     */
    private void setAllCheckMenuItemsFalse() {
        this.fahrenheitCheckMenuItem.setSelected(false);
        this.celsiusCheckMenuItem.setSelected(false);
        this.kelvinCheckMenuItem.setSelected(false);
    }

    /**
     * Sets the check menu item to the saved measurement units setting
     */
    private void setMeasurementSettings() {
        this.setAllCheckMenuItemsFalse();
        if (CurrentWeatherInformation.getMeasurementUnits() == MeasurementUnits.Imperial) {
            this.fahrenheitCheckMenuItem.setSelected(true);
        } else if (CurrentWeatherInformation.getMeasurementUnits() == MeasurementUnits.Metric) {
            this.celsiusCheckMenuItem.setSelected(true);
        } else {
            this.kelvinCheckMenuItem.setSelected(true);
        }
    }

}
