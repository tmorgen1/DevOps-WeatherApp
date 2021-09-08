package edu.westga.weatherapp_gui.view;

import java.io.IOException;

import edu.westga.weatherapp_gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class DayForecastPane extends Pane {

    @FXML
    private Label dayOfWeekLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dayTemperatureLabel;

    @FXML
    private Label weatherDescriptionLabel;

    @FXML
    private Label minTemperatureLabel;

    @FXML
    private Label maxTemperatureLabel;

    public DayForecastPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(App.DAY_FORECAST_PANE_VIEW));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            System.err.println("IO Exception: Error loading day forecast pane component");
        }
    }
}
