package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;

/**
 * Defines the daily forecast page view.
 */
public class DailyForecastPage {

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
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {

    }

    /**
     * Event handler for the back arrow image view. Handles the on click mouse
     * event. Switches to the landing page scene
     * 
     * @param event - the clicked mouse event
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
}
