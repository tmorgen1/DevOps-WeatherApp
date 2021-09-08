package edu.westga.weatherapp_gui.view;

import java.io.IOException;
import java.util.ArrayList;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
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
        this.loadDayForecastInformation();
    }

    public void loadDayForecastInformation() {
        Task<ArrayList<DayForecastPane>> task = new Task<ArrayList<DayForecastPane>>() {

            @Override
            public ArrayList<DayForecastPane> call() throws Exception {
                ArrayList<DayForecastPane> panes = new ArrayList<DayForecastPane>();
                for (int i = 0; i < 16; i++) {
                    DayForecastPane pane = new DayForecastPane();
                    panes.add(pane);
                }
                return panes;
            }
        };

        task.setOnSucceeded(e -> this.handleTaskSucceeded(e));
        new Thread(task).start();
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
        // TODO: only show n number of panes when selectbox implemented for number of
        // days

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
