package edu.westga.weatherapp_gui.view;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Cursor;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import org.json.JSONObject;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.model.DateTimeConverter;
import edu.westga.weatherapp_shared.model.WeatherLocation;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.LandingPageViewModel;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;

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
     * The search pane
     */
    @FXML
    private Pane searchPane;

    /**
     * The favorited list view
     */
    @FXML
    private ListView<WeatherLocation> favoritedListView;

    /**
     * The search results list view
     */
    @FXML
    private ListView<WeatherLocation> searchResultsListView;

    /**
     * The favorite outline image view
     */
    @FXML
    private ImageView favoriteOutlineImageView;

    /**
     * The favorite filled image view
     */
    @FXML
    private ImageView favoriteFilledImageView;

    /**
     * The hourly forecast HBox
     */
    @FXML
    private HBox hourlyForecastHBox;

    /**
     * The hourly info scroll pane
     */
    @FXML
    private ScrollPane hourlyInfoScrollPane;

    /**
     * The loading progress indicator
     */
    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * The loading progress label
     */
    @FXML
    private Label progressLabel;

    /**
     * The settings menu
     */
    @FXML
    private Menu settingMenu;

    /**
     * The temperature suffix
     */
    private String TemperatureSuffix = " °F";

    /**
     * The wind speed suffix
     */
    private String WindSpeedSuffix = " mi/h";

    /**
     * The array list of hourly forecast panes
     */
    private ArrayList<HourlyInfoPane> hourlyInfoPanes;

    /**
     * The number of hours for the forecast to load
     */
    public static final int HOURS = 24;

    /**
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.hourlyInfoPanes = new ArrayList<HourlyInfoPane>();
        this.viewModel = new LandingPageViewModel(null, null, null, null);
        Platform.runLater(() -> this.landingPagePane.requestFocus());
        this.setFavoritedLocationsListItems();
        this.setupSearchListViewSelectionListener();
        this.setupLocationSearchTextChangedListener();
        this.setupFavoritesListViewSelectionListener();
        this.setMeasurementSettings();
        this.setupSearchTextFieldOnFocusListener();
        this.checkForSavedCurrentWeatherData();
        this.updateFavoriteIcon();
        this.loadHourlyForecastInfoPanes();
    }

    /**
     * Loads the HourlyInfoPane components and fetches the hourly forecast data.
     */
    private void loadHourlyForecastInfoPanes() {
        if (CurrentWeatherInformation.getWeatherLocation() == null && CurrentWeatherInformation.isFinishedFirstLoadIpGrab()) {
            return;
        } else if (CurrentWeatherInformation.getWeatherLocation() == null && !CurrentWeatherInformation.isFinishedFirstLoadIpGrab()) {
            WeatherLocation currentLocation = this.viewModel.GetCurrentLocation();
            CurrentWeatherInformation.setWeatherLocation(currentLocation);
            this.updateSelectedWeatherLocation(currentLocation);
        }

        this.hideNoWeatherInformation();
        this.showLoadingIndication();
        WeatherLocation weatherLocation = CurrentWeatherInformation.getWeatherLocation();
        this.viewModel.GetHourlyForecastDataByWeatherLocation(weatherLocation, HOURS);
        this.hourlyForecastHBox.getChildren().clear();

        if (CurrentWeatherInformation.getHourlyInfoPanes() != null) {
            this.updateLoadedHourlyInfoPanes();
            return;
        }

        this.hourlyInfoPanes.clear();
        this.createHourlyInfoPanes();
    }

    /**
     * Creates and loads hourly info panes
     */
    private void createHourlyInfoPanes() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(App.HOURLY_FORECAST_PANE_VIEW));
        Task<ArrayList<HourlyInfoPane>> task = new Task<ArrayList<HourlyInfoPane>>() {

            @Override
            public ArrayList<HourlyInfoPane> call() throws Exception {
                ArrayList<HourlyInfoPane> panes = new ArrayList<HourlyInfoPane>();
                for (int index = 0; index < HOURS; index++) {
                    String dayIconUrl = LandingPage.this.getDayIconUrl(index);
                    String hour = LandingPage.this.getHour(index);
                    String temp = LandingPage.this.getTemperature(index);
                    HourlyInfoPane pane = new HourlyInfoPane(temp, hour, dayIconUrl, loader);
                    panes.add(pane);
                }
                return panes;
            }
        };

        task.setOnSucceeded(e -> this.handleTaskSucceeded(e));
        new Thread(task).start();
    }

    /**
     * Updates the previously loaded hourly info panes rather than creating new ones to improve load times.
     */
    private void updateLoadedHourlyInfoPanes() {
        Task<ArrayList<HourlyInfoPane>> task = new Task<ArrayList<HourlyInfoPane>>() {

            @Override
            public ArrayList<HourlyInfoPane> call() throws Exception {
                ArrayList<HourlyInfoPane> previousPanes = CurrentWeatherInformation.getHourlyInfoPanes();
                for (int index = 0; index < HOURS; index++) {
                    String dayIconUrl = LandingPage.this.getDayIconUrl(index);
                    String hour = LandingPage.this.getHour(index);
                    String temp = LandingPage.this.getTemperature(index);
        
                    previousPanes.get(index).SetIconImageView(dayIconUrl);
                    previousPanes.get(index).SetTemperatureLabel(temp);
                    previousPanes.get(index).SetTimeLabel(hour);
                }

                return previousPanes;
            }
        };

        task.setOnSucceeded(e -> this.handleTaskSucceeded(e));
        new Thread(task).start();
    }

    /**
     * Gets the current hour for the specified hourly info pane
     * 
     * @param index - the index of the specified hourly info pane
     * @return the hour
     */
    private String getHour(int index) {
        Long timezone = this.viewModel.GetTimezone();
        Long utcDateTime = this.viewModel.GetHourUtcDateTime(index);
        return DateTimeConverter.ConvertUtcToHour(utcDateTime, timezone);
    }

    /**
     * Gets the temperature for the specified hourly info pane
     * @param index - the index of the specified hourly info pane
     * @return the temperature
     */
    private String getTemperature(int index) {
        String maxTemperature = this.viewModel.GetHourTemperature(index);
        return maxTemperature + this.TemperatureSuffix;
    }

    /**
     * Gets the day icon url for the specified hourly info pane
     * @param index - the index of the specified hourly info pane
     * @return the weather icon url
     */
    private String getDayIconUrl(int index) {
        return this.viewModel.GetDayWeatherIcon(index);
    }

    /**
     * Handles the succession of loading the hourly forecast information
     * 
     * @param event - the worker state event
     */
    @SuppressWarnings("unchecked")
    private void handleTaskSucceeded(WorkerStateEvent event) {
        if (event.getSource().getState() == State.SUCCEEDED && event.getSource().getValue() instanceof ArrayList) {
            this.hourlyInfoPanes = (ArrayList<HourlyInfoPane>) event.getSource().getValue();
            this.hourlyForecastHBox.getChildren().clear();
            this.hourlyForecastHBox.getChildren().addAll(this.hourlyInfoPanes);
            this.hideLoadingIndication();
            this.showWeatherInformation();
            CurrentWeatherInformation.setHourlyInfoPanes(this.hourlyInfoPanes);
        }
    }

    /**
     * Hides the progress indicator and progress indicator label
     */
    private void hideLoadingIndication() {
        this.progressIndicator.setVisible(false);
        this.progressLabel.setVisible(false);
        this.locationSearchTextField.setDisable(false);
        this.settingMenu.setDisable(false);
    }

    /**
     * Displays the progress indicator and progress indicator label
     */
    private void showLoadingIndication() {
        this.locationSearchTextField.setDisable(true);
        this.progressIndicator.setVisible(true);
        this.progressLabel.setVisible(true);
        this.settingMenu.setDisable(true);
    }

    /**
     * Sets up the location search text field text change listener. Searches for
     * similar locations based on the user's input and sets the list view to the
     * retrieved similar locations.
     */
    private void setupLocationSearchTextChangedListener() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        this.locationSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> LandingPage.this.setSearchedLocationsListItems(newValue));
            pause.playFromStart();
        });
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

        this.removeFocusFromSearchBar();
        this.tryGetAndUpdateWeatherData();
    }

    /**
     * Handles the mouse click event for the weather radar clicked. Moves to the weather radar page
     * 
     * @param event - the mouse click event
     */
    @FXML
    void onWeatherRadarClicked(MouseEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowGenerator.changeScene(currentStage, App.WEATHER_RADAR_PAGE_VIEW, App.WEATHER_RADAR_PAGE_TITLE);
        } catch (IOException exception) {
            this.displayNoLocationSnackbar("Please Enter a Location First");
        }
    }

    /**
     * Scrolls the hbox horizontally instead of vertically when a scroll event is fired
     * 
     * @param event - the scroll event
     */
    @FXML
    void hourlyInfoOnScroll(ScrollEvent event) {
        if(event.getDeltaX() == 0 && event.getDeltaY() != 0) {
            this.hourlyInfoScrollPane.setHvalue(this.hourlyInfoScrollPane.getHvalue() - event.getDeltaY() / this.hourlyForecastHBox.getWidth());
        }
    }

    /**
     * Loads the saved weather data and weather location if it exists
     */
    private void checkForSavedCurrentWeatherData() {
        if (CurrentWeatherInformation.getWeatherLocation() != null
                && CurrentWeatherInformation.getWeatherData() != null) {
            this.viewModel.SetCurrentWeatherData(CurrentWeatherInformation.getWeatherData());
            this.locationSearchTextField.setText(CurrentWeatherInformation.getWeatherLocation().getCity());
            this.updateAllWeatherInformation();
        } else if (CurrentWeatherInformation.getWeatherLocation() != null
                && CurrentWeatherInformation.getWeatherData() == null) {
            this.locationSearchTextField.setText(CurrentWeatherInformation.getWeatherLocation().getCity());
            this.tryGetAndUpdateWeatherData();
        }
    }

    /**
     * Sets the fetched similar search results to the search results list view.
     * 
     * @param city - the user's searched city
     */
    private void setSearchedLocationsListItems(String city) {
        if (city != null && !city.isEmpty()) {
            Collection<WeatherLocation> result = this.viewModel.GetLocationSearchResults(city);
            if (result != null) {
                ObservableList<WeatherLocation> searchItems = FXCollections.observableArrayList(result);
                this.searchResultsListView.setItems(searchItems);
            }
        } else {
            this.searchResultsListView.setItems(null);
        }
    }

    /**
     * Sets up the search list view selection listener. Then calls
     * updateSelectedWeatherLocation.
     */
    private void setupSearchListViewSelectionListener() {
        this.searchResultsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WeatherLocation>() {
            @Override
            public void changed(ObservableValue<? extends WeatherLocation> observable, WeatherLocation oldValue,
                    WeatherLocation newValue) {
                if (newValue != null) {
                    LandingPage.this.updateSelectedWeatherLocation(newValue);
                }
            }
        });
    }

    /**
     * Sets up the favorites list view selection listener. Then calls
     * updateSelectedWeatherLocation.
     */
    private void setupFavoritesListViewSelectionListener() {
        this.favoritedListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WeatherLocation>() {
            @Override
            public void changed(ObservableValue<? extends WeatherLocation> observable, WeatherLocation oldValue,
                    WeatherLocation newValue) {
                if (newValue != null) {
                    LandingPage.this.updateSelectedWeatherLocation(newValue);
                }
            }
        });
    }

    /**
     * Saves the weather location, removes the search bar from focus, sets the city
     * to the location search text field, and tries to pull data for the selected
     * city.
     * 
     * @param newWeatherLocation - the selected weather location
     */
    private void updateSelectedWeatherLocation(WeatherLocation newWeatherLocation) {
        CurrentWeatherInformation.setWeatherLocation(newWeatherLocation);
        this.removeFocusFromSearchBar();
        this.locationSearchTextField.setText(newWeatherLocation.getCity());
        this.tryGetAndUpdateWeatherData();
    }

    /**
     * Sets the favorited list view to fake data.
     */
    private void setFavoritedLocationsListItems() {
        ObservableList<WeatherLocation> favoritedItems = FXCollections.observableArrayList(this.viewModel.GetFavoritedWeatherLocations());
        this.favoritedListView.setItems(favoritedItems);
    }

    /**
     * Gets the selected weather location information then tries to fetch the
     * weather location data. Then it updates the weather information javafx info.
     */
    private void tryGetAndUpdateWeatherData() {
        try {
            WeatherLocation location = CurrentWeatherInformation.getWeatherLocation();
            JSONObject result = this.viewModel.GetWeatherDataByWeatherLocation(location);
            if (!this.checkWeatherData(result)) {
                return;
            }

            this.hideWeatherInformation();
            this.updateAllWeatherInformation();
            this.updateFavoriteIcon();
            this.loadHourlyForecastInfoPanes();
        } catch (IllegalArgumentException e) {
            this.displayNoLocationSnackbar("No Location Found");
        }
    }

    /**
     * Updates the favorite icon to be filled if it was favorited, or unfilled if it was unfavorited
     */
    private void updateFavoriteIcon() {
        if (CurrentWeatherInformation.getWeatherLocation() != null) {
            if (this.viewModel.FavoritesContainsWeatherLocation(CurrentWeatherInformation.getWeatherLocation())) {
                this.switchToFilledFavoriteIcon();
            } else {
                this.switchToOutlineFavoriteIcon();
            }
        }
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
        this.WindSpeedSuffix = " km/h";
        this.setAllCheckMenuItemsFalse();
        this.celsiusCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Metric);
        this.updateDataIfSearchedCity();
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
        this.WindSpeedSuffix = " mi/h";
        this.setAllCheckMenuItemsFalse();
        this.fahrenheitCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Imperial);
        this.updateDataIfSearchedCity();
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
        this.WindSpeedSuffix = " km/h";
        this.setAllCheckMenuItemsFalse();
        this.kelvinCheckMenuItem.setSelected(true);
        CurrentWeatherInformation.setMeasurementUnits(MeasurementUnits.Kelvin);
        this.updateDataIfSearchedCity();
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
     * Sets the focus to the landing page pane
     * 
     * @param event - the mouse click event
     */
    @FXML
    void onLandingPagePaneClicked(MouseEvent event) {
        this.removeFocusFromSearchBar();
    }

    /**
     * Removes the focus from the search bar.
     */
    private void removeFocusFromSearchBar() {
        Platform.runLater(() -> this.favoritedListView.getSelectionModel().clearSelection());
        Platform.runLater(() -> this.searchResultsListView.getSelectionModel().clearSelection());
        this.landingPagePane.requestFocus();
    }

    /**
     * Removes the location from the favorites list and saves the file.
     */
    @FXML
    void onFavoriteFilledClicked(MouseEvent event) {
        if (CurrentWeatherInformation.getWeatherLocation() != null) {
            this.viewModel.RemoveFavoritedLocation(CurrentWeatherInformation.getWeatherLocation());
            this.switchToOutlineFavoriteIcon();
            this.setFavoritedLocationsListItems();
        }
    }

    /**
     * Hides the filled favorite icon image view and shows the outline favorite icon image view
     */
    private void switchToOutlineFavoriteIcon() {
        this.favoriteOutlineImageView.setVisible(true);
        this.favoriteFilledImageView.setVisible(false);
    }

    /**
     * Adds the location to the favorites list and saves the file.
     */
    @FXML
    void onFavoriteOutlineClicked(MouseEvent event) {
        if (CurrentWeatherInformation.getWeatherLocation() != null) {
            this.viewModel.AddFavoritedLocation(CurrentWeatherInformation.getWeatherLocation());
            this.switchToFilledFavoriteIcon();
            this.setFavoritedLocationsListItems();
        }
    }

    /**
     * Hides the outline favorite icon image view and shows the filled favorite icon image view
     */
    private void switchToFilledFavoriteIcon() {
        this.favoriteOutlineImageView.setVisible(false);
        this.favoriteFilledImageView.setVisible(true);
    }

    /**
     * Sets up the search text field focus changed listener. If the search bar is
     * selected, it sets the search results pane to be visible, otherwise it sets
     * the search results pane to hidden.
     */
    private void setupSearchTextFieldOnFocusListener() {
        this.locationSearchTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
                    Boolean newPropertyValue) {
                if (newPropertyValue) {
                    LandingPage.this.searchPane.setVisible(true);
                } else {
                    LandingPage.this.searchPane.setVisible(false);
                }
            }
        });
    }

    /**
     * Tries to fetch and update the weather location data if the location search
     * text field is not empty
     */
    private void updateDataIfSearchedCity() {
        if (!this.locationSearchTextField.getText().isEmpty()) {
            this.tryGetAndUpdateWeatherData();
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
            this.displayNoLocationSnackbar("Please Enter a Location First");
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
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowGenerator.changeScene(currentStage, App.SEVERE_WARNINGS_PAGE_VIEW, App.SEVERE_WARNINGS_PAGE_TITLE);
        } catch (IOException exception) {
            this.displayNoLocationSnackbar("Please Enter a Location First");
        }
    }

    /**
     * Updates the temperature label, weather description label, weather icon, wind
     * speed label, and humidity label to the retrieved current weather information.
     * Hides the no weather location alert and displays the actual weather
     * information.
     */
    private void updateAllWeatherInformation() {
        this.updateCurrentTemperature();
        this.updateCurrentWeatherDescription();
        this.updateCurrentWeatherIcon();
        this.updateCurrentWindSpeed();
        this.updateCurrentHumidity();

        this.hideNoWeatherInformation();
    }

    /**
     * Checks if the given weather data is valid. Displays the no weather
     * information message and hides current weather data.
     * 
     * @param result - the retrieved weather data result
     * @return the retrieved weather data result
     */
    private Boolean checkWeatherData(JSONObject result) {
        if (result == null) {
            this.hideWeatherInformation();
            this.showNoWeatherInformation();
            this.displayNoLocationSnackbar("No Location Found");

            return false;
        }

        return true;
    }

    /**
     * Displays the no location found snackbar.
     */
    private void displayNoLocationSnackbar(String errorMessage) {
        JFXSnackbar snackbar = new JFXSnackbar(this.landingPagePane);
        StackPane pane = new StackPane();
        Label label = new Label(errorMessage);
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
        String temperature = this.viewModel.GetCurrentTemperature();
        this.currentTemperatureLabel.setText(temperature + this.TemperatureSuffix);
    }

    /**
     * Updates the current weather description label
     */
    private void updateCurrentWeatherDescription() {
        String description = this.viewModel.GetCurrentWeatherDescription();
        this.weatherDescriptionLabel.setText(description);
    }

    /**
     * Updates the current weather icon
     */
    private void updateCurrentWeatherIcon() {
        String iconURL = this.viewModel.GetCurrentWeatherIcon();
        Image iconImage = new Image(iconURL);
        this.weatherIconImageView.setImage(iconImage);
    }

    /**
     * Updates the current wind speed label
     */
    private void updateCurrentWindSpeed() {
        String windSpeed = this.viewModel.GetCurrentWindSpeed();
        this.windSpeedLabel.setText(windSpeed + this.WindSpeedSuffix);
    }

    /**
     * Updates the current humidity label
     */
    private void updateCurrentHumidity() {
        String humidity = this.viewModel.GetCurrentHumidity();
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
