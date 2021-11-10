package edu.westga.weatherapp_gui.view.daily_forecast_page;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.view.ViewTestWithServer;
import javafx.scene.Node;

public class DailyForecastViewTests extends ViewTestWithServer {
    
    @Test
    public void selectingFarenheitSettingFetchesData() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#dailyForecastButton");
        progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#settingMenu");
        this.clickOn("#fahrenheitCheckMenuItem");
    }

    @Test
    public void selectingCelsiusSettingFetchesData() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#dailyForecastButton");
        progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#settingMenu");
        this.clickOn("#celsiusCheckMenuItem");
    }

    @Test
    public void selectingKelvinSettingFetchesData() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#dailyForecastButton");
        progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#settingMenu");
        this.clickOn("#kelvinCheckMenuItem");
    }

    @Test
    public void measurementSettingsStaySetToCelsius() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#settingMenu");
        this.clickOn("#celsiusCheckMenuItem");
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#dailyForecastButton");
        progressIndicator = this.lookup("#progressIndicator").tryQuery();
    }

    @Test
    public void measurementSettingsStaySetToKelvin() {
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
        this.clickOn("#dailyForecastButton");
        progressIndicator = this.lookup("#progressIndicator").tryQuery();
    }

}
