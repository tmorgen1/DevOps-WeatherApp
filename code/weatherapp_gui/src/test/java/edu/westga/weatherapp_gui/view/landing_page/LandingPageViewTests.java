package edu.westga.weatherapp_gui.view.landing_page;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.view.ViewTestWithServer;
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class LandingPageViewTests extends ViewTestWithServer {

    @Test
    public void selectingDailyForecastButtonChangesScene() {
        this.clickOn("#dailyForecastButton");
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#backArrowImageView");
    }

    @Test
    public void selectingWeatherRadarButtonChangesScene() {
        this.clickOn("#mapRadarImageView");
        sleep(1000);
        this.clickOn("#backArrowImageView");
    }

    @Test
    public void selectingFarenheitSettingFetchesData() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
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
        this.clickOn("#settingMenu");
        this.clickOn("#kelvinCheckMenuItem");
    }

    @Test
    public void selectingFavoriteButtonChangesButtonIcon() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.doubleClickOn("#locationSearchTextField");
        this.type(KeyCode.N, KeyCode.E, KeyCode.W, KeyCode.N, KeyCode.A, KeyCode.N);
        sleep(2000);
        this.type(KeyCode.ENTER);
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#favoriteOutlineImageView");
        sleep(1000);
        this.clickOn("#favoriteFilledImageView");
        sleep(1000);
    }

    @Test
    public void pressingEnterSelectsTopMostSearchOption() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.doubleClickOn("#locationSearchTextField");
        this.type(KeyCode.N, KeyCode.E, KeyCode.W, KeyCode.N, KeyCode.A, KeyCode.N, KeyCode.ENTER);
    }

    @Test
    public void selectTopResultInSearch() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.doubleClickOn("#locationSearchTextField");
        this.type(KeyCode.N, KeyCode.E, KeyCode.W, KeyCode.N, KeyCode.A, KeyCode.N);
        this.moveTo("#searchResultsListView");
        this.moveBy(0, -70);
        this.press(MouseButton.PRIMARY);
    }

    @Test
    public void selectFromFavoritesList() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.doubleClickOn("#locationSearchTextField");
        this.type(KeyCode.N, KeyCode.E, KeyCode.W, KeyCode.N, KeyCode.A, KeyCode.N);
        sleep(2000);
        this.type(KeyCode.ENTER);
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#favoriteOutlineImageView");
        this.clickOn("#locationSearchTextField");
        this.moveTo("#searchResultsListView");
        this.moveBy(0, 120);
        this.press(MouseButton.PRIMARY);
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.doubleClickOn("#favoriteFilledImageView");
    }

    @Test
    public void ensureScrollMovesHBox() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.moveTo("#hourlyForecastHBox");
        this.scroll(3, VerticalDirection.DOWN);
    }
}
