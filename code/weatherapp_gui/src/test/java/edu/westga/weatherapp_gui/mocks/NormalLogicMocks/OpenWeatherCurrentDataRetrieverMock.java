package edu.westga.weatherapp_gui.mocks.NormalLogicMocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;

public class OpenWeatherCurrentDataRetrieverMock implements CurrentWeatherDataRetriever {

    public OpenWeatherCurrentDataRetrieverMock(DataRetriever retriever) {

    }

    @Override
    public String getDataByCity(String arg0) throws RemoteException {
        return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"temp\":{\"max\": 98, \"min\": 90}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";
    }

    @Override
    public String getDataByCityAndCountryCode(String arg0, String arg1) throws RemoteException {
        return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"temp\":{\"max\": 98, \"min\": 90}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";
    }

    @Override
    public String getDataByCityAndStateCode(String arg0, String arg1) throws RemoteException {
        return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"temp\":{\"max\": 98, \"min\": 90}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";
    }

    @Override
    public String getDataByCityAndStateCodeAndCountryCode(String arg0, String arg1, String arg2) throws RemoteException {
        return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"temp\":{\"max\": 98, \"min\": 90}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";
    }

    @Override
    public MeasurementUnits getUnitsOfMeasurement() throws RemoteException {
        return MeasurementUnits.Imperial;
    }

    @Override
    public void setUnitsOfMeasurement(MeasurementUnits arg0) throws RemoteException {
        
    }
    
}
