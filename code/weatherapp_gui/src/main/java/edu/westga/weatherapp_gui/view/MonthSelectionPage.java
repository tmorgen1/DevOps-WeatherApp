package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.view.utils.WindowGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * The month selection page that handles the interaction with the ui.
 * 
 * @author Michael Pavich
 */
public class MonthSelectionPage {

    @FXML
    private Pane dailyForecastPagePane;

    @FXML
    private ImageView backArrowImageView;

    @FXML
    private Menu settingMenu;

    @FXML
    private CheckMenuItem fahrenheitCheckMenuItem;

    @FXML
    private CheckMenuItem celsiusCheckMenuItem;

    @FXML
    private CheckMenuItem kelvinCheckMenuItem;

    @FXML
    private JFXButton januaryButton;

    @FXML
    private JFXButton februaryButton;

    @FXML
    private JFXButton marchButton;

    @FXML
    private JFXButton aprilButton;

    @FXML
    private JFXButton mayButton;

    @FXML
    private JFXButton juneButton;

    @FXML
    private JFXButton julyButton;

    @FXML
    private JFXButton augustButton;

    @FXML
    private JFXButton septermberButton;

    @FXML
    private JFXButton octoberButton;

    @FXML
    private JFXButton novemberButton;

    @FXML
    private JFXButton decemberButton;

    @FXML
    void onAprilButtonClicked(ActionEvent event) {

    }

    @FXML
    void onAugustButtonClicked(ActionEvent event) {

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

    @FXML
    void onCelsiusSelected(ActionEvent event) {

    }

    @FXML
    void onDecemberButtonClicked(ActionEvent event) {

    }

    @FXML
    void onFahrenheitSelected(ActionEvent event) {

    }

    @FXML
    void onFebruaryButtonClicked(ActionEvent event) {

    }

    @FXML
    void onJanuaryButtonClicked(ActionEvent event) {

    }

    @FXML
    void onJulyButtonClicked(ActionEvent event) {

    }

    @FXML
    void onJuneButtonClicked(ActionEvent event) {

    }

    @FXML
    void onKelvinSelected(ActionEvent event) {

    }

    @FXML
    void onMarchButtonClicked(ActionEvent event) {

    }

    @FXML
    void onMayButtonClicked(ActionEvent event) {

    }

    @FXML
    void onNovemberButtonClicked(ActionEvent event) {

    }

    @FXML
    void onOctoberButtonClicked(ActionEvent event) {

    }

    @FXML
    void onSeptemberButtonClicked(ActionEvent event) {

    }

}
