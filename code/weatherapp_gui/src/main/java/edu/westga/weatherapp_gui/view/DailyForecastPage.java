package edu.westga.weatherapp_gui.view;

import java.io.IOException;
import java.util.ArrayList;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.model.DateTimeConverter;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.DailyForecastPageViewModel;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;

/**
 * Defines the daily forecast page view.
 */
public class DailyForecastPage {

    public static final int DAYS = 16;

    /**
     * The daily forecast page view model
     */
    private DailyForecastPageViewModel viewModel;

    /**
     * The array list of day forecast panes
     */
    private ArrayList<DayForecastPane> dayForecastPanes;

    /**
     * The daily forecast page pane
     */
    @FXML
    private Pane dailyForecastPagePane;

    /**
     * The back arrow image view
     */
    @FXML
    private ImageView backArrowImageView;

    /**
     * The daily forecast vertical box
     */
    @FXML
    private VBox dailyForecastVBox;

    /**
     * The progress indicator
     */
    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * The progress indicator label
     */
    @FXML
    private Label progressLabel;

    /**
     * The scroll pane
     */
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private CheckMenuItem fahrenheitCheckMenuItem;

    @FXML
    private CheckMenuItem celsiusCheckMenuItem;

    @FXML
    private CheckMenuItem kelvinCheckMenuItem;

    private String TemperatureSuffix = " °F";

    /**
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.dayForecastPanes = new ArrayList<DayForecastPane>();
        this.setMeasurementSettings();
        this.viewModel = new DailyForecastPageViewModel(null, null);
        this.loadDayForecastComponents(DAYS);
    }

    @FXML
    void onCelsiusSelected(ActionEvent event) {
        this.TemperatureSuffix = " °C";
        this.setAllCheckMenuItemsFalse();
        this.celsiusCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Metric);
        this.updateDataIfSearchedCity();
    }

    @FXML
    void onFahrenheitSelected(ActionEvent event) {
        this.TemperatureSuffix = " °F";
        this.setAllCheckMenuItemsFalse();
        this.fahrenheitCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Imperial);
        this.updateDataIfSearchedCity();
    }

    @FXML
    void onKelvinSelected(ActionEvent event) {
        this.TemperatureSuffix = " K";
        this.setAllCheckMenuItemsFalse();
        this.kelvinCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Kelvin);
        this.updateDataIfSearchedCity();
    }

    private void updateDataIfSearchedCity() {
        this.hideDailyForecastInformation();
        this.showLoadingIndication();
        this.loadDayForecastComponents(DAYS);
    }

    private void setAllCheckMenuItemsFalse() {
        this.fahrenheitCheckMenuItem.setSelected(false);
        this.celsiusCheckMenuItem.setSelected(false);
        this.kelvinCheckMenuItem.setSelected(false);
    }

    private void setMeasurementSettings() {
        this.setAllCheckMenuItemsFalse();
        if (CurrentWeatherInformation.getMeasurementUnits() == MeasurementUnits.Imperial) {
            this.fahrenheitCheckMenuItem.setSelected(true);
            this.TemperatureSuffix = " °F";
        } else if (CurrentWeatherInformation.getMeasurementUnits() == MeasurementUnits.Metric) {
            this.TemperatureSuffix = " °C";
            this.celsiusCheckMenuItem.setSelected(true);
        } else {
            this.TemperatureSuffix = " K";
            this.kelvinCheckMenuItem.setSelected(true);
        }
    }

    /**
     * Loads the DayForecastPane components based on the number of days inputed.
     * 
     * @param days - the number of days for the forecast
     */
    private void loadDayForecastComponents(int days) {
        this.viewModel.GetWeatherDataByCity(CurrentWeatherInformation.getCityName(), DAYS);
        this.dayForecastPanes.clear();
        this.dailyForecastVBox.getChildren().clear();
        Task<ArrayList<DayForecastPane>> task = new Task<ArrayList<DayForecastPane>>() {

            @Override
            public ArrayList<DayForecastPane> call() throws Exception {
                ArrayList<DayForecastPane> panes = new ArrayList<DayForecastPane>();
                for (int index = 0; index < days; index++) {
                    String dayOfWeek = DailyForecastPage.this.getDayOfWeek(index);
                    String dayIconUrl = DailyForecastPage.this.getDayIconUrl(index);
                    String date = DailyForecastPage.this.getDayDate(index);
                    String maxTemp = DailyForecastPage.this.getDayMaxTemperature(index);
                    String minTemp = DailyForecastPage.this.getDayMinTemperature(index);
                    DayForecastPane pane = new DayForecastPane(dayOfWeek, date, maxTemp, minTemp, dayIconUrl);
                    panes.add(pane);
                }
                return panes;
            }
        };

        task.setOnSucceeded(e -> this.handleTaskSucceeded(e));
        new Thread(task).start();
    }

    /**
     * Gets the day of the week name for the specified day index
     * 
     * @param dayIndex - the day index
     * @return the day of the week
     */
    private String getDayOfWeek(int dayIndex) {
        Long timezone = DailyForecastPage.this.viewModel.GetTimezone();
        Long utcDateTime = DailyForecastPage.this.viewModel.GetDayUtcDateTime(dayIndex);
        return DateTimeConverter.ConvertUtcToDayOfWeek(utcDateTime, timezone);
    }

    /**
     * Gets the day icon url for the specified day index
     * 
     * @param dayIndex - the day index
     * @return the icon url
     */
    private String getDayIconUrl(int dayIndex) {
        return this.viewModel.GetDayWeatherIcon(dayIndex);
    }

    /**
     * Gets the day date for the specified day index
     * 
     * @param dayIndex - the day index
     * @return the date
     */
    private String getDayDate(int dayIndex) {
        Long timezone = this.viewModel.GetTimezone();
        Long utcDateTime = this.viewModel.GetDayUtcDateTime(dayIndex);
        return DateTimeConverter.ConvertUtcToShortDate(utcDateTime, timezone);
    }

    /**
     * Gets the max temperature for the specified day index
     * 
     * @param dayIndex - the day index
     * @return the max temp
     */
    private String getDayMaxTemperature(int dayIndex) {
        String maxTemperature = this.viewModel.GetDayMaxTemperature(dayIndex);
        return maxTemperature + this.TemperatureSuffix;
    }

    /**
     * Gets the min temperature for the specified day index
     * 
     * @param dayIndex - the day index
     * @return the min temp
     */
    private String getDayMinTemperature(int dayIndex) {
        String minTemperature = this.viewModel.GetDayMinTemperature(dayIndex);
        return minTemperature + this.TemperatureSuffix;
    }

    /**
     * Event handler for the back arrow image view. Handles the on click mouse
     * event. Switches to the landing page scene
     * 
     * @param event - the clicked mouse event
     */
    @FXML
    private void onBackArrowClicked(MouseEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowGenerator.changeScene(currentStage, App.LANDING_PAGE_VIEW, App.LANDING_PAGE_TITLE);
        } catch (IOException exception) {
            System.err.println("IO Exception: Error switching scenes");
        }
    }

    /**
     * Hides the loading progress indicator and progress label
     */
    private void hideLoadingIndication() {
        this.progressIndicator.setVisible(false);
        this.progressLabel.setVisible(false);
    }

    private void showLoadingIndication() {
        this.progressIndicator.setVisible(true);
        this.progressLabel.setVisible(true);
    }

    /**
     * Shows the scroll pane that contains the daily forecast info
     */
    private void showDailyForecastInformation() {
        this.scrollPane.setVisible(true);
    }

    private void hideDailyForecastInformation() {
        this.scrollPane.setVisible(false);
    }

    /**
     * Handles the succession of loading the daily forecast information
     * 
     * @param event - the worker state event
     */
    @SuppressWarnings("unchecked")
    private void handleTaskSucceeded(WorkerStateEvent event) {
        if (event.getSource().getState() == State.SUCCEEDED && event.getSource().getValue() instanceof ArrayList) {
            this.dayForecastPanes = (ArrayList<DayForecastPane>) event.getSource().getValue();
            this.dailyForecastVBox.getChildren().addAll(this.dayForecastPanes);
            this.hideLoadingIndication();
            this.showDailyForecastInformation();
        }
    }
}
