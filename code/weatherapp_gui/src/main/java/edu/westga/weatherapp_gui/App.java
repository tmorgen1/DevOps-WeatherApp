package edu.westga.weatherapp_gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static final String LANDING_PAGE_TITLE = "Weather App - Home";
	public static final String LANDING_PAGE_VIEW = "/edu/westga/weatherapp_gui/view/LandingPage.fxml";

	public static final String DAILY_FORECAST_PAGE_TITLE = "Weather App - Daily Forecast";
	public static final String DAILY_FORECAST_VIEW = "/edu/westga/weatherapp_gui/view/DailyForecastPage.fxml";

	public static final String DAY_FORECAST_PANE_VIEW = "/edu/westga/weatherapp_gui/view/DayForecastPane.fxml";

	public static final String SEVERE_WARNINGS_PAGE_TITLE = "Weather App - Severe Weather Warnings";
	public static final String SEVERE_WARNINGS_PAGE_VIEW = "/edu/westga/weatherapp_gui/view/SevereWeatherWarningsPage.fxml";
	public static final String SEVERE_WARNINGS_TITLED_PANE_VIEW = "/edu/westga/weatherapp_gui/view/SevereWeatherWarningTitledPane.fxml";

	/**
	 * JavaFX entry point.
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @throws IOException
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(App.LANDING_PAGE_VIEW));
		Scene scene = new Scene(parent);
		primaryStage.setTitle(LANDING_PAGE_TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Primary Java entry point.
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		App.launch(args);
	}
}
