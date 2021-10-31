package edu.westga.weatherapp_gui.view.landing_page;

import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import edu.westga.weatherapp_gui.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestDailyForecastButton extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws IOException {
        URL test = getClass().getResource(App.LANDING_PAGE_VIEW);
        Parent parent = FXMLLoader.load(test);
		Scene scene = new Scene(parent);
		stage.setTitle(App.LANDING_PAGE_TITLE);
		stage.setScene(scene);
		stage.show();
    }

    @Test
    public void selectingDailyForecastButtonChangesScene() {
        this.clickOn("#dailyForecastButton");
    }

}
