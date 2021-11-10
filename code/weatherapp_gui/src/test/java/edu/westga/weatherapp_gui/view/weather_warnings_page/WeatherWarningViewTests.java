package edu.westga.weatherapp_gui.view.weather_warnings_page;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.view.ViewTestWithServer;
import javafx.scene.Node;
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
        this.doubleClickOn("#locationSearchTextField");
        this.type(KeyCode.S, KeyCode.E, KeyCode.A, KeyCode.T, KeyCode.T, KeyCode.L, KeyCode.E);
        sleep(6000);
        this.moveTo("#searchResultsListView");
        this.moveBy(0, -70);
        this.press(MouseButton.PRIMARY);
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.doubleClickOn("#warningsButton");
        sleep(4000);
        this.clickOn("#severeWarningsComboBox");
        this.moveBy(0, 40);
        this.press(MouseButton.PRIMARY);
        this.clickOn("#backArrowImageView");
        this.doubleClickOn("#backArrowImageView");
        sleep(2000);
    }

}
