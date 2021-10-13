package edu.westga.weatherapp_gui.mocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.HourlyWeatherDataRetriever;

public class OpenWeatherHourlyDataRetrieverMock implements HourlyWeatherDataRetriever {
    public OpenWeatherHourlyDataRetrieverMock(DataRetriever retriever) {

    }

    @Override
    public String GetDataByCity(String arg0, int arg1) throws RemoteException {
        return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"main\":{\"temp\": 98}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";

    }

    @Override
    public String GetDataByCityAndCountryCode(String arg0, String arg1, int arg2) throws RemoteException {
        return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"main\":{\"temp\": 98}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";

    }

    @Override
    public String GetDataByCityAndStateCode(String arg0, String arg1, int arg2) throws RemoteException {
        return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"main\":{\"temp\": 98}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";

    }

    @Override
    public String GetDataByCityAndStateCodeAndCountryCode(String arg0, String arg1, String arg2, int arg3)
            throws RemoteException {
                return "{\"city\": {\"timezone\": 100},\"weather\": [{\"main\": Cloudy, \"icon\": test}], \"main\": {\"temp\": 70, \"humidity\": 80}, \"wind\": {\"speed\": 5}, \"list\":[{\"main\":{\"temp\": 98}, \"dt\": 500,\"weather\":[{\"icon\": fake-url}]}]}";

    }

    @Override
    public MeasurementUnits getUnitsOfMeasurement() throws RemoteException {
        return MeasurementUnits.Imperial;
    }

    @Override
    public void setUnitsOfMeasurement(MeasurementUnits arg0) throws RemoteException {

    }
}
