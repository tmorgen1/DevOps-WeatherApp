package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.model.GuiConstants;
import edu.westga.weatherapp_gui.model.MeasurementUnitConverter;
import edu.westga.weatherapp_gui.model.Months;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.StatisticalWeatherPageViewModel;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * This class is the controller for the Statistical Weather Page.
 * 
 * @author Michael Pavich
 */
public class StatisticalWeatherPage {

    private StatisticalWeatherPageViewModel viewModel;

    /**
     * The temperature suffix
     */
    private String temperatureSuffix = GuiConstants.KELVIN_SUFFIX;

    /**
     * The pressure suffix
     */
    private String pressureSuffix = GuiConstants.HECTOPASCAL_SUFFIX;

    /**
     * The wind speed suffix
     */
    private String windSuffix = GuiConstants.METERS_PER_SECOND_SUFFIX;
    
    /**
     * The precipitation suffix
     */
    private String precipitationSuffix = GuiConstants.MILLIMETERS_SUFFIX;

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
    private Pane contentPane;

    @FXML
    private Label recordMaxTempLabel;

    @FXML
    private Label recordMinTempLabel;

    @FXML
    private Label averageMaxTempLabel;

    @FXML
    private Label averageMinTempLabel;

    @FXML
    private Label averageTempLabel;

    @FXML
    private Label recordMaxPressureLabel;

    @FXML
    private Label recordMinPressureLabel;

    @FXML
    private Label averagePressureLabel;

    @FXML
    private Label medianPressureLabel;

    @FXML
    private Label recordMaxHumidityLabel;

    @FXML
    private Label recordMinHumidityLabel;

    @FXML
    private Label averageHumidityLabel;

    @FXML
    private Label medianHumidityLabel;

    @FXML
    private Label recordMaxWindLabel;

    @FXML
    private Label recordMinWindLabel;

    @FXML
    private Label averageWindLabel;

    @FXML
    private Label medianWindLabel;

    @FXML
    private Label recordMaxPrecipitationLabel;

    @FXML
    private Label recordMinPrecipitationLabel;

    @FXML
    private Label averagePrecipitationLabel;

    @FXML
    private Label medianPrecipitationLabel;

    @FXML
    private Label recordMaxCloudLabel;

    @FXML
    private Label recordMinCloudLabel;

    @FXML
    private Label averageCloudLabel;

    @FXML
    private Label medianCloudLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label progressLabel;

    @FXML
    void initialize() {
        this.viewModel = new StatisticalWeatherPageViewModel(null);
        this.setMeasurementSettings();
        this.loadStatisticalWeatherInfo();
        this.setMonthLabel();
    }

    private void setMonthLabel() {
        int monthIndex = CurrentWeatherInformation.getStatisticalMonthSelected() - 1;
        this.monthLabel.setText(Months.values()[monthIndex].toString());
    }

    private void loadStatisticalWeatherInfo() {
        Task<Boolean> task = new Task<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                StatisticalWeatherPage.this.viewModel.getStatisticalWeatherDataByWeatherLocation(
                        CurrentWeatherInformation.getWeatherLocation(),
                        CurrentWeatherInformation.getStatisticalMonthSelected());
                return true;
            }
        };

        task.setOnSucceeded(e -> this.handleTaskSucceeded(e));
        new Thread(task).start();
    }

    /**
     * Handles the succession of loading the hourly forecast information
     * 
     * @param event - the worker state event
     */
    private void handleTaskSucceeded(WorkerStateEvent event) {
        if (event.getSource().getState() == State.SUCCEEDED) {
            this.hideLoadingIndication();
            this.showWeatherInformation();
            this.setWeatherInformationLabels();
        }
    }

    private void setWeatherInformationLabels() {
        this.setTemperatureLabels();
        this.setPressureLabels();
        this.setHumidityLabels();
        this.setWindLabels();
        this.setPrecipitationLabels();
        this.setCloudLabels();
    }

    /**
     * Hides the progress indicator and progress indicator label
     */
    private void hideLoadingIndication() {
        this.progressIndicator.setVisible(false);
        this.progressLabel.setVisible(false);
        this.settingMenu.setDisable(false);
    }

    /**
     * Sets all the weather information to be visible
     */
    private void showWeatherInformation() {
        this.contentPane.setVisible(true);
    }

    private void setTemperatureLabels() {
        String recordMaxTemperature = String
                .valueOf(MeasurementUnitConverter.convertTemperature(this.viewModel.getRecordMaxTemperature(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String recordMinTemperature = String
                .valueOf(MeasurementUnitConverter.convertTemperature(this.viewModel.getRecordMinTemperature(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String averageMaxTemperature = String
                .valueOf(MeasurementUnitConverter.convertTemperature(this.viewModel.getAverageMaxTemperature(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String averageMinTemperature = String
                .valueOf(MeasurementUnitConverter.convertTemperature(this.viewModel.getAverageMinTemperature(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String averageTemperature = String
                .valueOf(MeasurementUnitConverter.convertTemperature(this.viewModel.getAverageTemperature(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));

        this.recordMaxTempLabel.setText(recordMaxTemperature + this.temperatureSuffix);
        this.recordMinTempLabel.setText(recordMinTemperature + this.temperatureSuffix);
        this.averageMaxTempLabel.setText(averageMaxTemperature + this.temperatureSuffix);
        this.averageMinTempLabel.setText(averageMinTemperature + this.temperatureSuffix);
        this.averageTempLabel.setText(averageTemperature + this.temperatureSuffix);
    }

    private void setPressureLabels() {
        String recordMaxPressure = String
                .valueOf(MeasurementUnitConverter.convertPressure(this.viewModel.getRecordMaxPressure(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String recordMinPressure = String
                .valueOf(MeasurementUnitConverter.convertPressure(this.viewModel.getRecordMinPressure(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String medianPressure = String
                .valueOf(MeasurementUnitConverter.convertPressure(this.viewModel.getMedianPressure(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String averagePressure = String
                .valueOf(MeasurementUnitConverter.convertPressure(this.viewModel.getAveragePressure(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));

        this.recordMaxPressureLabel.setText(recordMaxPressure + this.pressureSuffix);
        this.recordMinPressureLabel.setText(recordMinPressure + this.pressureSuffix);
        this.medianPressureLabel.setText(medianPressure + this.pressureSuffix);
        this.averagePressureLabel.setText(averagePressure + this.pressureSuffix);
    }

    private void setHumidityLabels() {
        String recordMaxHumidity = String.valueOf(this.viewModel.getRecordMaxHumidity());
        String recordMinHumidity = String.valueOf(this.viewModel.getRecordMinHumidity());
        String medianHumidity = String.valueOf(this.viewModel.getMedianHumidity());
        String averageHumidity = String.valueOf(this.viewModel.getAverageHumidity());

        this.recordMaxHumidityLabel.setText(recordMaxHumidity + GuiConstants.PERCENTAGE_SYMBOL);
        this.recordMinHumidityLabel.setText(recordMinHumidity + GuiConstants.PERCENTAGE_SYMBOL);
        this.medianHumidityLabel.setText(medianHumidity + GuiConstants.PERCENTAGE_SYMBOL);
        this.averageHumidityLabel.setText(averageHumidity + GuiConstants.PERCENTAGE_SYMBOL);
    }

    private void setWindLabels() {
        String recordMaxWind = String
                .valueOf(MeasurementUnitConverter.convertWind(this.viewModel.getRecordMaxWind(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String recordMinWind = String
                .valueOf(MeasurementUnitConverter.convertWind(this.viewModel.getRecordMinWind(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String medianWind = String
                .valueOf(MeasurementUnitConverter.convertWind(this.viewModel.getMedianWind(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String averageWind = String
                .valueOf(MeasurementUnitConverter.convertWind(this.viewModel.getAverageWind(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));

        this.recordMaxWindLabel.setText(recordMaxWind + this.windSuffix);
        this.recordMinWindLabel.setText(recordMinWind + this.windSuffix);
        this.medianWindLabel.setText(medianWind + this.windSuffix);
        this.averageWindLabel.setText(averageWind + this.windSuffix);
    }

    private void setPrecipitationLabels() {
        String recordMaxPrecipitation = String
                .valueOf(MeasurementUnitConverter.convertPrecipitation(this.viewModel.getRecordMaxPrecipitation(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String recordMinPrecipitation = String
                .valueOf(MeasurementUnitConverter.convertPrecipitation(this.viewModel.getRecordMinPrecipitation(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String medianPrecipitation = String
                .valueOf(MeasurementUnitConverter.convertPrecipitation(this.viewModel.getMedianPrecipitation(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));
        String averagePrecipitation = String
                .valueOf(MeasurementUnitConverter.convertPrecipitation(this.viewModel.getAveragePrecipitation(),
                        MeasurementUnits.Kelvin, CurrentWeatherInformation.getMeasurementUnits()));

        this.recordMaxPrecipitationLabel.setText(recordMaxPrecipitation + this.precipitationSuffix);
        this.recordMinPrecipitationLabel.setText(recordMinPrecipitation + this.precipitationSuffix);
        this.medianPrecipitationLabel.setText(medianPrecipitation + this.precipitationSuffix);
        this.averagePrecipitationLabel.setText(averagePrecipitation + this.precipitationSuffix);
    }

    private void setCloudLabels() {
        String recordMaxCloudCoverage = String.valueOf(this.viewModel.getRecordMaxClouds());
        String recordMinCloudCoverage = String.valueOf(this.viewModel.getRecordMinClouds());
        String medianCloudCoverage = String.valueOf(this.viewModel.getMedianClouds());
        String averageCloudCoverage = String.valueOf(this.viewModel.getAverageClouds());

        this.recordMaxCloudLabel.setText(recordMaxCloudCoverage + GuiConstants.PERCENTAGE_SYMBOL);
        this.recordMinCloudLabel.setText(recordMinCloudCoverage + GuiConstants.PERCENTAGE_SYMBOL);
        this.medianCloudLabel.setText(medianCloudCoverage + GuiConstants.PERCENTAGE_SYMBOL);
        this.averageCloudLabel.setText(averageCloudCoverage + GuiConstants.PERCENTAGE_SYMBOL);
    }

    @FXML
    void onBackArrowClicked(MouseEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowGenerator.changeScene(currentStage, App.MONTH_SELECTION_PAGE_VIEW, App.MONTH_SELECTION_PAGE_TITLE);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error switching scenes");
        }
    }

    @FXML
    void onCelsiusSelected(ActionEvent event) {
        this.setSuffixUnitsToMetric();
        this.setAllCheckMenuItemsFalse();
        this.celsiusCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Metric);
        this.setWeatherInformationLabels();
    }

    @FXML
    void onFahrenheitSelected(ActionEvent event) {
        this.setSuffixUnitsToImperial();
        this.setAllCheckMenuItemsFalse();
        this.fahrenheitCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Imperial);
        this.setWeatherInformationLabels();
    }

    @FXML
    void onKelvinSelected(ActionEvent event) {
        this.setSuffixUnitsToKelvin();
        this.setAllCheckMenuItemsFalse();
        this.kelvinCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Kelvin);
        this.setWeatherInformationLabels();
    }

    /**
     * Sets the check menu item to the saved measurement units setting
     */
    private void setMeasurementSettings() {
        this.setAllCheckMenuItemsFalse();
        if (CurrentWeatherInformation.getMeasurementUnits() == MeasurementUnits.Imperial) {
            this.setSuffixUnitsToImperial();
            this.fahrenheitCheckMenuItem.setSelected(true);
        } else if (CurrentWeatherInformation.getMeasurementUnits() == MeasurementUnits.Metric) {
            this.setSuffixUnitsToMetric();
            this.celsiusCheckMenuItem.setSelected(true);
        } else {
            this.setSuffixUnitsToKelvin();
            this.kelvinCheckMenuItem.setSelected(true);
        }
    }

    private void setSuffixUnitsToKelvin() {
        this.temperatureSuffix = GuiConstants.KELVIN_SUFFIX;
        this.pressureSuffix = GuiConstants.HECTOPASCAL_SUFFIX;
        this.windSuffix = GuiConstants.METERS_PER_SECOND_SUFFIX;
        this.precipitationSuffix = GuiConstants.MILLIMETERS_SUFFIX;
    }

    private void setSuffixUnitsToMetric() {
        this.temperatureSuffix = GuiConstants.CELSIUS_SUFFIX;
        this.pressureSuffix = GuiConstants.PASCAL_SUFFIX;
        this.windSuffix = GuiConstants.METERS_PER_SECOND_SUFFIX;
        this.precipitationSuffix = GuiConstants.MILLIMETERS_SUFFIX;
    }

    private void setSuffixUnitsToImperial() {
        this.temperatureSuffix = GuiConstants.FAHRENHEIT_SUFFIX;
        this.pressureSuffix = GuiConstants.POUNDS_PER_SQUARE_INCH_SUFFIX;
        this.windSuffix = GuiConstants.FEET_PER_SECOND_SUFFIX;
        this.precipitationSuffix = GuiConstants.INCHES_SUFFIX;
    }

    /**
     * Sets all of the check menu items to be false.
     */
    private void setAllCheckMenuItemsFalse() {
        this.fahrenheitCheckMenuItem.setSelected(false);
        this.celsiusCheckMenuItem.setSelected(false);
        this.kelvinCheckMenuItem.setSelected(false);
    }

}
