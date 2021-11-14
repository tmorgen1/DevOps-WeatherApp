package edu.westga.weatherapp_gui.view.statistical_weather_page;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.view.ViewTestWithServer;
import javafx.scene.Node;

public class MonthSelectionPageViewTests extends ViewTestWithServer {
    
    @Test
    public void selectAllMonths() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#settingMenu");
        this.clickOn("#kelvinCheckMenuItem");
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#historicalButton");
        this.clickOn("#januaryButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#februaryButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#marchButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#aprilButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#mayButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#juneButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#julyButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#augustButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#septemberButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#octoberButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#novemberButton");
        this.clickOn("#backArrowImageView");

        this.clickOn("#decemberButton");
        this.clickOn("#backArrowImageView");
    }

    @Test
    public void selectMeasurementUnits() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#historicalButton");
        this.clickOn("#settingMenu");
        this.clickOn("#kelvinCheckMenuItem");
        this.clickOn("#settingMenu");
        this.clickOn("#celsiusCheckMenuItem");
        this.clickOn("#settingMenu");
        this.clickOn("#fahrenheitCheckMenuItem");
        this.clickOn("#backArrowImageView");
    }
}
