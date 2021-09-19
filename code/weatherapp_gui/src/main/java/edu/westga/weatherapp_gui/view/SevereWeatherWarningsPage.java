package edu.westga.weatherapp_gui.view;

import java.io.IOException;
import java.util.List;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.view.utils.SevereWeatherWarningsTitledPanesToStringConverter;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.viewmodel.SevereWeatherWarningsPageViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The severe weather warnings page class.
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWeatherWarningsPage {

    @FXML
    private Pane severeWarningsPagePane;

    @FXML
    private Accordion severeWarningsAccordion;

    @FXML
    private ImageView backArrowImageView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label progressLabel;

    @FXML
    private Label noWarningsForLocationLabel;

    private SevereWeatherWarningsPageViewModel viewModel;

    private ObjectProperty<List<TitledPane>> severeWarningsTitledPanesListProperty;

    /**
     * Initializes after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.viewModel = new SevereWeatherWarningsPageViewModel(null);
        this.severeWarningsTitledPanesListProperty = new SimpleObjectProperty<List<TitledPane>>();
        this.bindToViewModel();
        this.loadSevereWarningsTitlePanes();
    }

    private void bindToViewModel() {
        Bindings.bindBidirectional(this.viewModel.getsevereWeatherWarningsStringProperty(),
                this.severeWarningsTitledPanesListProperty, new SevereWeatherWarningsTitledPanesToStringConverter());
        this.noWarningsForLocationLabel.visibleProperty().bind(this.viewModel.getNoWarningsForLocationVisibilityProperty());
        this.severeWarningsAccordion.visibleProperty().bind(this.viewModel.getSevereWarningAccordionVisibilityProperty());
    }

    /**
     * Loads the titled panes for the severe weathe warnings
     */
    private void loadSevereWarningsTitlePanes() {
        this.hideLoadingIndication();
        this.viewModel.setsevereWeatherWarningsObjectPropertyValue(CurrentWeatherInformation.getWeatherLocation(),
                CurrentWeatherInformation.getMeasurementUnits());
        this.severeWarningsAccordion.getPanes()
                .setAll(FXCollections.observableArrayList(this.severeWarningsTitledPanesListProperty.getValue()));
    }

    /**
     * Hides the loading progress indicator and progress label
     */
    private void hideLoadingIndication() {
        this.progressIndicator.setVisible(false);
        this.progressLabel.setVisible(false);
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
            this.viewModel.setErrorLabelStringPropertyValue("IO Exception: Error switching scenes");
        }
    }
}
