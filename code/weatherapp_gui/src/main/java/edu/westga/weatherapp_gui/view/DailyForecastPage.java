package edu.westga.weatherapp_gui.view;

import java.io.IOException;
import java.util.ArrayList;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.model.DateTimeConverter;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.DailyForecastPageViewModel;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
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

    /**
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.viewModel = new DailyForecastPageViewModel(null, null);
        this.viewModel.GetWeatherDataByCity(CurrentWeatherInformation.getCityName(), DAYS);
        this.loadDayForecastComponents(DAYS);
    }

    /**
     * Loads the DayForecastPane components based on the number of days inputed.
     * 
     * @param days - the number of days for the forecast
     */
    private void loadDayForecastComponents(int days) {
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
        String temperatureSuffix = "°F";
        return maxTemperature + temperatureSuffix;
    }

    /**
     * Gets the min temperature for the specified day index
     * 
     * @param dayIndex - the day index
     * @return the min temp
     */
    private String getDayMinTemperature(int dayIndex) {
        String minTemperature = this.viewModel.GetDayMinTemperature(dayIndex);
        String temperatureSuffix = "°F";
        return minTemperature + temperatureSuffix;
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

    /**
     * Shows the scroll pane that contains the daily forecast info
     */
    private void showDailyForecastInformation() {
        this.scrollPane.setVisible(true);
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
