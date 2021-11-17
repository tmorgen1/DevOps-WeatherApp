package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.view.utils.PageResizeHelper;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.SevereWeatherWarningPageViewModel;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Severe Weather Warning Page class for representing each weather warning
 * on its own page
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarningPage {

    /**
     * IO Exception thrown when switching scenes
     */
    public static final String IO_EXCEPTION_THROWN = "IO Exception: Error switching scenes";

    /**
     * this severe weather warning page's scroll pane
     */
    @FXML
    private ScrollPane severeWarningScrollPane;

    /**
     * this severe weather warning page's pane
     */
    @FXML
    private Pane severeWarningPane;

    /**
     * this severe weather warning page's warning name label
     */
    @FXML
    private Label warningNameLabel;

    /**
     * this severe weather warning page's description textbox
     */
    @FXML
    private Text description;

    /**
     * this severe weather warning page's error textbox
     */
    @FXML
    private Text errorText;
    /**
     * the view model for this page
     */
    private SevereWeatherWarningPageViewModel viewModel;

    /**
     * Initializes this page after all FXML fields are loaded
     * 
     * @throws IOException
     */
    @FXML
    void initialize() {
        this.viewModel = new SevereWeatherWarningPageViewModel();
        Platform.runLater(() -> new PageResizeHelper().setScalingRules(this.severeWarningPane));
        this.bindToViewModel();
    }

    /**
     * Binds the view's content properties to the view model
     */
    private void bindToViewModel() {
        this.warningNameLabel.textProperty().bind(this.viewModel.getWarningNameTextStringProperty());
        this.description.textProperty().bind(this.viewModel.getDescriptionTextProperty());
        this.errorText.textProperty().bindBidirectional(this.viewModel.getErrorTextStringProperty());
        this.errorText.visibleProperty().bindBidirectional(this.viewModel.getErrorTextVisibilityProperty());
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
            WindowGenerator.changeScene(currentStage, App.SEVERE_WARNINGS_PAGE_VIEW, App.SEVERE_WARNINGS_PAGE_TITLE);
        } catch (IOException exception) {
            this.viewModel.setErrorTextStringPropertyValue(SevereWeatherWarningPage.IO_EXCEPTION_THROWN);
            this.viewModel.setErrorTextVisibilityProperty(true);
        } 
    }
}
