package edu.westga.weatherapp_gui.view.weather_warnings_page;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.view.ViewTestWithServer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class WeatherWarningViewTests extends ViewTestWithServer {
    
    @Test
    public void selectWarningCreatesNewScene() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#locationSearchTextField");
        while (!((TextField) this.lookup("#locationSearchTextField").tryQuery().get()).getText().isEmpty())
        {
            this.type(KeyCode.BACK_SPACE);
        }
        this.type(KeyCode.S, KeyCode.E, KeyCode.A, KeyCode.T, KeyCode.T, KeyCode.L, KeyCode.E);
        sleep(20000);
        Point2D searchResultsPoint = this.lookup("#searchResultsListView").tryQuery().get().localToScreen(5, 5);
        this.clickOn(searchResultsPoint, MouseButton.PRIMARY);
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.doubleClickOn("#warningsButton");
        sleep(4000);
        this.clickOn("#severeWarningsComboBox");
        Point2D severeWarningsPoint = this.lookup("#severeWarningsComboBox").tryQuery().get().localToScreen(20, 50);
        this.clickOn(severeWarningsPoint, MouseButton.PRIMARY);
        this.clickOn("#backArrowImageView");
        this.doubleClickOn("#backArrowImageView");
        sleep(2000);
    }

}
