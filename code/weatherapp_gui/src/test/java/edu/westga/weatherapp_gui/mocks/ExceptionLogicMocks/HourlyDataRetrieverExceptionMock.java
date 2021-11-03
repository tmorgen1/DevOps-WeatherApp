package edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks;

import java.rmi.RemoteException;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.HourlyWeatherDataRetriever;

public class HourlyDataRetrieverExceptionMock implements HourlyWeatherDataRetriever {

    public HourlyDataRetrieverExceptionMock(DataRetriever dataRetriever) {
        
    }

    @Override
    public String getDataByCity(String arg0, int arg1) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public String getDataByCityAndCountryCode(String arg0, String arg1, int arg2) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public String getDataByCityAndStateCode(String arg0, String arg1, int arg2) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public String getDataByCityAndStateCodeAndCountryCode(String arg0, String arg1, String arg2, int arg3)
            throws RemoteException {
                throw new RemoteException();
    }

    @Override
    public MeasurementUnits getUnitsOfMeasurement() throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void setUnitsOfMeasurement(MeasurementUnits arg0) throws RemoteException {
        throw new RemoteException();
    }
    
}
