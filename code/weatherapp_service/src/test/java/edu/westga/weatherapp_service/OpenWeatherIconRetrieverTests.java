package edu.westga.weatherapp_service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_service.model.OpenWeatherIconRetriever;

public class OpenWeatherIconRetrieverTests {
    
    @Test
    public void defaultConstructorTest() {
        assertDoesNotThrow(() -> {
            new OpenWeatherIconRetriever();
        });
    }

    @Test
    public void getWeatherIconWhenIconIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            OpenWeatherIconRetriever retriever = new OpenWeatherIconRetriever();
            retriever.getWeatherIconUrlByIconId(null);
        });
    }

    @Test
    public void getWeatherIconWhenIconIdIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            OpenWeatherIconRetriever retriever = new OpenWeatherIconRetriever();
            retriever.getWeatherIconUrlByIconId("");
        });
    }

    @Test
    public void getWeatherIconUrl() {
        assertDoesNotThrow(() -> {
            OpenWeatherIconRetriever retriever = new OpenWeatherIconRetriever();
            retriever.getWeatherIconUrlByIconId("04d");
        });
    }
}
