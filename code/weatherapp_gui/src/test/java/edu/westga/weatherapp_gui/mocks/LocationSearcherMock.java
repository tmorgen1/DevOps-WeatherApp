package edu.westga.weatherapp_gui.mocks;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class LocationSearcherMock implements LocationSearcher {

    @Override
    public Collection<WeatherLocation> searchLocations(String arg0, int arg1) throws RemoteException {
        return List.of(new WeatherLocation("city", "country", "state", 30.40, 30.40));
    }

    @Override
    public WeatherLocation getLocationByIP(String arg0) throws RemoteException {
        return new WeatherLocation("city", "country", "state", 30.40, 30.40);
    }
}