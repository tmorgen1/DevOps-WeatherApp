package edu.westga.weatherapp_gui.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;

import edu.westga.weatherapp_gui.App;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewTestWithServer extends ApplicationTest {

    private static Process serverProcess = null;

    @Override
    public void start(Stage stage) throws IOException {
        CurrentWeatherInformation.resetInfo();

        URL viewURL = getClass().getResource(App.LANDING_PAGE_VIEW);
        Parent parent = FXMLLoader.load(viewURL);
		Scene scene = new Scene(parent);
		stage.setTitle(App.LANDING_PAGE_TITLE);
		stage.setScene(scene);
		stage.show();
    }

    @BeforeAll
    public static void setupServerForTests() {
        //runMavenPackageServer();
        runWeatherServer();
    }

    // private static void runMavenPackageServer() {
    //     try {
    //         String mavenPath = Paths.get("").toAbsolutePath().getParent().resolve("maven\\bin").toString();
    //         ProcessBuilder pb = new ProcessBuilder(mavenPath + "\\mvn.cmd", "--batch-mode", "clean", "package");
    //         String filePath = Paths.get("").toAbsolutePath().getParent().resolve("weatherapp_service").toString();
    //         pb.directory(new File(filePath));
    //         Process p = pb.start();
    //         p.waitFor();
    //     } catch (IOException | InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    private static void runWeatherServer() {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "target\\weatherapp_service-1.0-SNAPSHOT.jar");
            String filePath = Paths.get("").toAbsolutePath().getParent().resolve("weatherapp_service").toString();
            pb.directory(new File(filePath));
            serverProcess = pb.start();
            Thread.sleep(3000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void shutdownServer() {
        serverProcess.destroy();
    }
    
}
