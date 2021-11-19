package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import com.jfoenix.controls.JFXComboBox;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.model.SevereWeatherWarning;
import edu.westga.weatherapp_gui.view.utils.PageResizeHelper;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import edu.westga.weatherapp_gui.viewmodel.SevereWeatherWarningPageViewModel;
import edu.westga.weatherapp_gui.viewmodel.SevereWeatherWarningsPageViewModel;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

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
     * the severe weather warning's combobox
     */
    @FXML
    private JFXComboBox<SevereWeatherWarning> severeWarningsComboBox;

    /**
     * the back arrow image view
     */
    @FXML
    private ImageView backArrowImageView;

    /**
     * the label that displays if there are no severe weather warnings for a
     * location
     */
    @FXML
    private Label noWarningsForLocationLabel;

    /**
     * the Text that displays if the page encounters errors
     */
    @FXML
    private Text errorText;

    /**
     * the view model for this page
     */
    private SevereWeatherWarningsPageViewModel viewModel;

    /**
     * Initializes this page after all FXML fields are loaded
     */
    @FXML
    void initialize() {
        this.viewModel = new SevereWeatherWarningsPageViewModel(null);
        this.setSevereWarningsComboBoxCellFactory();
        Platform.runLater(() -> new PageResizeHelper().setScalingRules(this.severeWarningsPagePane));
        this.setSevereWarningsComboBoxSelectionChangeListener();
        this.bindToViewModel();
        this.viewModel.setsevereWeatherWarningsPagePropertiesValues(CurrentWeatherInformation.getWeatherLocation(),
                CurrentWeatherInformation.getMeasurementUnits());
    }

    /**
     * Binds the view's content properties to the view model
     */
    private void bindToViewModel() {
        this.severeWarningsComboBox.itemsProperty().bind(this.viewModel.getSevereWarningComboBoxListProperty());
        this.noWarningsForLocationLabel.visibleProperty()
                .bind(this.viewModel.getNoWarningsForLocationVisibilityProperty());
        this.severeWarningsComboBox.visibleProperty().bind(this.viewModel.getSevereWarningComboBoxVisibilityProperty());
        this.errorText.textProperty().bind(this.viewModel.getErrorTextStringProperty());
        this.errorText.visibleProperty().bind(this.viewModel.getErrorTextVisibilityProperty());
    }

    /**
     * sets the cell factory for the severe weather warnings combo box
     */
    private void setSevereWarningsComboBoxCellFactory() {
        this.severeWarningsComboBox
                .setCellFactory(new Callback<ListView<SevereWeatherWarning>, ListCell<SevereWeatherWarning>>() {
                    @Override
                    public ListCell<SevereWeatherWarning> call(ListView<SevereWeatherWarning> list) {
                        SevereWarningViewCell cell = new SevereWarningViewCell();
                        return cell;
                    }
                });

    }

    /**
     * sets the selection change listener for the severe weather warnings combo box
     */
    private void setSevereWarningsComboBoxSelectionChangeListener() {
        this.severeWarningsComboBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    SevereWeatherWarningPageViewModel.setSevereWeatherWarningObjectPropertyValue(newValue);
                    if (newValue != null) {
                        this.changePage(this.severeWarningsPagePane, App.SEVERE_WARNING_PAGE_VIEW,
                                newValue.getWarningName());
                    }
                });
    }

    /**
     * Event handler for the back arrow image view. Handles the on click mouse
     * event. Switches to the landing page scene
     * 
     * @param event - the clicked mouse event
     */
    @FXML
    private void onBackArrowClicked(MouseEvent event) {
        Node sceneNode = (Node) event.getSource();
        this.changePage(sceneNode, App.LANDING_PAGE_VIEW, App.LANDING_PAGE_TITLE);
    }

    /**
     * Handles switching to different pages for this page
     * 
     * @param event     the event that triggers the change
     * @param pageUri   the page's uri
     * @param pageTitle the page's title
     */
    @FXML
    private void changePage(Node node, String pageUri, String pageTitle) {
        try {
            Stage currentStage = (Stage) node.getScene().getWindow();
            WindowGenerator.changeScene(currentStage, pageUri, pageTitle);
        } catch (IOException exception) {
            this.viewModel.setSevereWarningComboBoxVisibilityPropertyValue(false);
            this.viewModel.setErrorTextStringPropertyValue("IO Exception: Error switching scenes");
            this.viewModel.setErrorTextVisibilityProperty(true);
        }
    }
}
