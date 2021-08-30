package edu.westga.weatherapp_gui.view;

import java.rmi.Naming;
import org.json.JSONObject;
import edu.westga.weatherapp_shared.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class LandingPage {

    @FXML
    private Pane landingPagePane;

    @FXML
    private TextField locationSearchTextField;

    @FXML
    private ImageView weatherIconImageView;

    @FXML
    private Label weatherDescriptionLabel;

    @FXML
    private Label currentTemperatureLabel;

    @FXML
    void initialize() {
        try {
            WeatherDataRetriever stub = (WeatherDataRetriever) Naming.lookup("rmi://localhost:5000/current-weather");
            JSONObject json = new JSONObject(stub.GetDataByCityAndStateCodeAndCountryCode("Carrollton", "GA", "US"));
            Long temperature = Math.round(json.getJSONObject("main").getDouble("temp"));
            String temperatureString = String.valueOf(temperature);
            this.currentTemperatureLabel.setText(temperatureString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
