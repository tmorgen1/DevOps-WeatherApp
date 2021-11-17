package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.view.utils.PageResizeHelper;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.WeatherRadarPageViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Defines the weather radar page view
 * 
 * @author Michael Pavich
 */
public class WeatherRadarPage {

    /**
     * The weather radar web view
     */
    @FXML
    private WebView weatherRadarWebView;

    /**
     * The weather radar page pane
     */
    @FXML
    private Pane weatherRadarPagePane;

    /**
     * The back arrow image view
     */
    @FXML
    private ImageView backArrowImageView;

    /**
     * The weather radar page view model
     */
    private WeatherRadarPageViewModel viewModel;

    /**
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.viewModel = new WeatherRadarPageViewModel(null, null);
        this.loadWeatherRadarMap();
        Platform.runLater(() -> new PageResizeHelper().setScalingRules(this.weatherRadarPagePane));
    }

    /**
     * Goes back to the landing page view
     * 
     * @param event - the
     */
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
     * Fetches the weather radar map html and loads it in the weather radar web view
     */
    private void loadWeatherRadarMap() {
        String weatherRadarHtmlString = this.viewModel.loadWeatherRadarMap();
        this.weatherRadarWebView.getEngine().loadContent(weatherRadarHtmlString);
    }

}
