package edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.interfaces.MapRadarDataRetriever;

public class MapRadarDataRetreiverExceptionMock implements MapRadarDataRetriever {

    @Override
    public String getMapRadarHTML() throws RemoteException {
        throw new RemoteException();
    }
    
}
