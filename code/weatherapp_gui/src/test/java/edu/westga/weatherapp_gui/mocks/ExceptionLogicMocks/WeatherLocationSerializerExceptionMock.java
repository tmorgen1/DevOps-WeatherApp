package edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks;

import java.io.IOException;
import java.util.Collection;

import edu.westga.weatherapp_gui.model.WeatherLocationSerializer;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class WeatherLocationSerializerExceptionMock extends WeatherLocationSerializer {
    
    @Override
    public void saveFavoritedLocationsToFile(Collection<WeatherLocation> locations) throws IOException {
        throw new IOException();
    }
    
}
