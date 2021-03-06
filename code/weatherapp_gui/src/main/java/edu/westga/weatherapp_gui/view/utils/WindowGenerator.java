package edu.westga.weatherapp_gui.view.utils;

import java.io.IOException;

import edu.westga.weatherapp_gui.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class to manage switching scenes.
 * 
 * @author Michael Pavich
 * @version Nov 17, 2021
 */
public class WindowGenerator {

    /**
     * Changes the scene to the given scene.
     *
     * @param currentStage       the current stage
     * @param viewSourceLocation the view source location
     * @param windowTitle        the window title
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void changeScene(Stage currentStage, String viewSourceLocation, String windowTitle)
            throws IOException {
        boolean wasMaximized = WindowGenerator.wasStageMaximized(currentStage);
        currentStage.setUserData(wasMaximized);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource(viewSourceLocation));
        loader.load();
        Scene recipeSelectScreen = new Scene(loader.getRoot());
        currentStage.setScene(recipeSelectScreen);
        currentStage.setTitle(windowTitle);
    }

    private static boolean wasStageMaximized(Stage currentStage) {
        boolean wasMaximized = false;
        if (currentStage.isMaximized()) {
            wasMaximized = true;
        }
        return wasMaximized;
    }
}
