package edu.westga.weatherapp_gui.view.change_location_page;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.view.ViewTestWithServer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class ChangeLocationViewTests extends ViewTestWithServer {
    
    @Test
    public void changeCurrentLocationWithSearchResults() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#locationIconImageView");
        this.clickOn("#locationSearchTextField");
        while (!((TextField) this.lookup("#locationSearchTextField").tryQuery().get()).getText().isEmpty())
        {
            this.type(KeyCode.BACK_SPACE);
        }
        this.type(KeyCode.S, KeyCode.E, KeyCode.A, KeyCode.T, KeyCode.T, KeyCode.L, KeyCode.E);
        sleep(10000);
        Point2D searchResultsPoint = this.lookup("#searchResultsListView").tryQuery().get().localToScreen(5, 5);
        this.clickOn(searchResultsPoint, MouseButton.PRIMARY);
        sleep(5000);
        this.clickOn("#backArrowImageView");
    }

    @Test
    public void changeCurrentLocationWithFavorites() {
        Optional<Node> progressIndicator = this.lookup("#progressIndicator").tryQuery();
        while (progressIndicator.get().isVisible())
        {
            sleep(10);
        }
        this.clickOn("#favoriteOutlineImageView");
        this.clickOn("#locationIconImageView");
        this.clickOn("#locationSearchTextField");
        Point2D searchResultsPoint = this.lookup("#favoritedListView").tryQuery().get().localToScreen(5, 5);
        this.clickOn(searchResultsPoint, MouseButton.PRIMARY);
        sleep(5000);
        this.clickOn("#backArrowImageView");
        this.clickOn("#favoriteFilledImageView");
    }
}
