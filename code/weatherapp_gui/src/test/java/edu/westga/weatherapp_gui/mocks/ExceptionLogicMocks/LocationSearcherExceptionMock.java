package edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks;

import java.rmi.RemoteException;
import java.util.Collection;

import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class LocationSearcherExceptionMock implements LocationSearcher {

    @Override
    public WeatherLocation getLocationByIP(String arg0) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public Collection<WeatherLocation> searchLocations(String arg0, int arg1, double arg2, double arg3)
            throws RemoteException {
        throw new RemoteException();
    }
    
}
