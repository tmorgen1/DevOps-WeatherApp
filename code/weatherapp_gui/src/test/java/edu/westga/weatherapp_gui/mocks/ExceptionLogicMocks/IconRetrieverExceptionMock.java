package edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

public class IconRetrieverExceptionMock implements WeatherIconRetriever {

    @Override
    public String getWeatherIconUrlByIconId(String arg0) throws RemoteException {
        throw new RemoteException();
    }
    
}
