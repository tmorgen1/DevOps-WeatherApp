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
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;

/**
 * Defines the daily forecast page view.
 */
public class DailyForecastPage {

    /**
     * The number of days for the forecast to load
     */
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

    /**
     * The fahrenheit check menu item
     */
    @FXML
    private CheckMenuItem fahrenheitCheckMenuItem;

    /**
     * The celsius check menu item
     */
    @FXML
    private CheckMenuItem celsiusCheckMenuItem;

    /**
     * The kelvin check menu item
     */
    @FXML
    private CheckMenuItem kelvinCheckMenuItem;

    /**
     * The settings menu
     */
    @FXML
    private Menu settingMenu;

    /**
     * The current temperature suffix
     */
    private String TemperatureSuffix = " °F";

    /**
     * Initializes after all FXML fields are loaded. Sets the measurement settings
     * if it has been changed. Loads the daily forecast.
     */
    @FXML
    void initialize() {
        this.dayForecastPanes = new ArrayList<DayForecastPane>();
        this.setMeasurementSettings();
        this.viewModel = new DailyForecastPageViewModel(null, null);
        this.loadDayForecastComponents(DAYS);
    }

    /**
     * Handles the celsius check menu item selected event. Changes the current
     * temperature suffix. Selects the celsius check menu item and deselects all
     * others. Clears the currently saved current weather info. Pulls fresh daily
     * forecast data with the new settings.
     * 
     * @param event - the select event
     */
    @FXML
    void onCelsiusSelected(ActionEvent event) {
        this.TemperatureSuffix = " °C";
        this.setAllCheckMenuItemsFalse();
        CurrentWeatherInformation.setWeatherData(null);
        this.celsiusCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Metric);
        this.reloadForecastWithNewUnits();
    }

    /**
     * Handles the fahrenheit check menu item selected event. Changes the current
     * temperature suffix. Selects the fahrenheit check menu item and deselects all
     * others. Clears the currently saved current weather info. Pulls fresh daily
     * forecast data with the new settings.
     * 
     * @param event - the select event
     */
    @FXML
    void onFahrenheitSelected(ActionEvent event) {
        this.TemperatureSuffix = " °F";
        this.setAllCheckMenuItemsFalse();
        CurrentWeatherInformation.setWeatherData(null);
        this.fahrenheitCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Imperial);
        this.reloadForecastWithNewUnits();
    }

    /**
     * Handles the kelvin check menu item selected event. Changes the current
     * temperature suffix. Selects the kelvin check menu item and deselects all
     * others. Clears the currently saved current weather info. Pulls fresh daily
     * forecast data with the new settings.
     * 
     * @param event - the select event
     */
    @FXML
    void onKelvinSelected(ActionEvent event) {
        this.TemperatureSuffix = " K";
        this.setAllCheckMenuItemsFalse();
        CurrentWeatherInformation.setWeatherData(null);
        this.kelvinCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Kelvin);
        this.reloadForecastWithNewUnits();
    }

    /**
     * Hides the current daily forecast, shows the loading indication, and fetches
     * fresh forecast information.
     */
    private void reloadForecastWithNewUnits() {
        this.hideDailyForecastInformation();
        this.showLoadingIndication();
        this.loadDayForecastComponents(DAYS);
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
        this.viewModel.GetWeatherDataByWeatherLocation(CurrentWeatherInformation.getWeatherLocation(), DAYS);
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
        this.settingMenu.setDisable(false);
    }

    /**
     * Shows the loadings progress indicator and progress label
     */
    private void showLoadingIndication() {
        this.progressIndicator.setVisible(true);
        this.progressLabel.setVisible(true);
        this.settingMenu.setDisable(true);
    }

    /**
     * Shows the scroll pane that contains the daily forecast info
     */
    private void showDailyForecastInformation() {
        this.scrollPane.setVisible(true);
    }

    /**
     * Hides the scroll pane that contains the daily forecast info
     */
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
