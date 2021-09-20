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
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
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

    /**
     * this severe warnings page's pane
     */
    @FXML
    private Pane severeWarningsPagePane;

    /**
     * the severe weather warning's accordion
     */
    @FXML
    private Accordion severeWarningsAccordion;

    /**
     * The back arrow image view
     */
    @FXML
    private ImageView backArrowImageView;

    /**
     * the label that displays if there are no severe weather warnings for a location
     */
    @FXML
    private Label noWarningsForLocationLabel;

    /**
     * the label that displays if the page encounters errors
     */
    @FXML
    private Label errorLabel;

    /**
     * the view model for this page
     */
    private SevereWeatherWarningsPageViewModel viewModel;

    /**
     * the titled Pane list list property that the accooridion gets its values from
     */
    private ObjectProperty<List<TitledPane>> severeWarningsTitledPanesListProperty;

    /**
     * Initializes this page after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.viewModel = new SevereWeatherWarningsPageViewModel(null);
        this.severeWarningsTitledPanesListProperty = new SimpleObjectProperty<List<TitledPane>>();
        this.bindToViewModel();
        this.loadSevereWarningsTitlePanes();
    }

    /**
     * Binds the view's content properties to the view model
     */
    private void bindToViewModel() {
        Bindings.bindBidirectional(this.viewModel.getsevereWeatherWarningsStringProperty(),
                this.severeWarningsTitledPanesListProperty, new SevereWeatherWarningsTitledPanesToStringConverter());
        this.noWarningsForLocationLabel.visibleProperty()
                .bind(this.viewModel.getNoWarningsForLocationVisibilityProperty());
        this.severeWarningsAccordion.visibleProperty()
                .bind(this.viewModel.getSevereWarningAccordionVisibilityProperty());
        this.errorLabel.textProperty().bind(this.viewModel.getErrorLabelStringProperty());
        this.errorLabel.visibleProperty().bind(this.viewModel.getErrorLabelVisibilityProperty());
    }

    /**
     * Loads the titled panes for the severe weathe warnings
     */
    private void loadSevereWarningsTitlePanes() {
        this.viewModel.setsevereWeatherWarningsObjectPropertyValue(CurrentWeatherInformation.getWeatherLocation(),
                CurrentWeatherInformation.getMeasurementUnits());
        ObservableList<TitledPane> accordionSevereWarningsTitledPanes = FXCollections
                .observableArrayList(this.severeWarningsTitledPanesListProperty.getValue());
        this.severeWarningsAccordion.getPanes().setAll(accordionSevereWarningsTitledPanes);
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
