package edu.westga.weatherapp_gui.mocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.interfaces.MapRadarDataRetriever;

public class OpenWeatherMapRadarDataRetrieverMock implements MapRadarDataRetriever {

    @Override
    public String GetMapRadarHTML() throws RemoteException {
        return "test";
    }
    
}
