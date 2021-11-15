package edu.westga.weatherapp_gui.view;

import java.io.IOException;
import java.util.Collection;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.ChangeLocationPageViewModel;
import edu.westga.weatherapp_shared.model.WeatherLocation;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;

/**
 * The controller for the Change Location page.
 * 
 * @author Michael Pavich
 */
public class ChangeLocationPage {

    private ChangeLocationPageViewModel viewModel;

    @FXML
    private Pane changeLocationPane;

    @FXML
    private ImageView backArrowImageView;

    @FXML
    private TextField locationSearchTextField;

    @FXML
    private Pane searchPane;

    @FXML
    private ListView<WeatherLocation> favoritedListView;

    @FXML
    private ListView<WeatherLocation> searchResultsListView;

    /**
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.viewModel = new ChangeLocationPageViewModel(null);
        this.setupLocationSearchTextChangedListener();
        this.setupSearchListViewSelectionListener();
        this.setFavoritedLocationsListItems();
        this.setupFavoritesListViewSelectionListener();
        this.setCurrentUserLocation();
        this.removeFocusFromSearchBar();
    }

    private void setCurrentUserLocation() {
        this.locationSearchTextField.setText(CurrentWeatherInformation.getUserLocation().getCity());
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
                    ChangeLocationPage.this.updateSelectedWeatherLocation(newValue);
                }
            }
        });
    }

    /**
     * Removes the focus from the search bar.
     */
    private void removeFocusFromSearchBar() {
        Platform.runLater(() -> this.favoritedListView.getSelectionModel().clearSelection());
        Platform.runLater(() -> this.searchResultsListView.getSelectionModel().clearSelection());
        this.changeLocationPane.requestFocus();
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
                    ChangeLocationPage.this.updateSelectedWeatherLocation(newValue);
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
        CurrentWeatherInformation.setUserLocation(newWeatherLocation);
        this.locationSearchTextField.setText(newWeatherLocation.getCity());
        this.removeFocusFromSearchBar();
        this.displaySnackbar("Current location changed successfully!");
    }

    /**
     * Sets up the location search text field text change listener. Searches for
     * similar locations based on the user's input and sets the list view to the
     * retrieved similar locations.
     */
    private void setupLocationSearchTextChangedListener() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        this.locationSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> ChangeLocationPage.this.setSearchedLocationsListItems(newValue));
            pause.playFromStart();
        });
    }

    /**
     * Sets the fetched similar search results to the search results list view.
     * 
     * @param city - the user's searched city
     */
    private void setSearchedLocationsListItems(String city) {
        if (city != null && !city.isEmpty()) {
            Collection<WeatherLocation> result = this.viewModel.getLocationSearchResults(city);
            if (result != null) {
                ObservableList<WeatherLocation> searchItems = FXCollections.observableArrayList(result);
                this.searchResultsListView.setItems(searchItems);
            }
        } else {
            this.searchResultsListView.setItems(null);
        }
    }

    /**
     * Sets the favorited list view to fake data.
     */
    private void setFavoritedLocationsListItems() {
        ObservableList<WeatherLocation> favoritedItems = FXCollections.observableArrayList(this.viewModel.getFavoritedWeatherLocations());
        this.favoritedListView.setItems(favoritedItems);
    }

    /**
     * Displays the no location found snackbar.
     */
    private void displaySnackbar(String errorMessage) {
        JFXSnackbar snackbar = new JFXSnackbar(this.changeLocationPane);
        StackPane pane = new StackPane();
        Label label = new Label(errorMessage);
        pane.setMinSize(this.changeLocationPane.getWidth(), 50);
        pane.getChildren().add(label);
        StackPane.setAlignment(label, Pos.CENTER);
        pane.setStyle("-fx-background-color: #4BB543");
        label.setStyle("-fx-font-size: 16; -fx-text-fill: #FFFFFF; -fx-font-family: Segoe UI");
        snackbar.enqueue(new SnackbarEvent(pane));
    }
}
