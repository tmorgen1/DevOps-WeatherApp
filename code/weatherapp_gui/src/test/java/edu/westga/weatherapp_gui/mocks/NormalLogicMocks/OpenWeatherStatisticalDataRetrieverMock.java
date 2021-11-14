package edu.westga.weatherapp_gui.mocks.NormalLogicMocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.interfaces.DataRetriever;
import edu.westga.weatherapp_shared.interfaces.StatisticalWeatherDataRetriever;

public class OpenWeatherStatisticalDataRetrieverMock implements StatisticalWeatherDataRetriever {

    private static final String JSON_RESULT = "{ result: { temp: { record_min: 200, record_max: 210, average_min: 220, average_max: 230, mean: 240 }, pressure: { min: 10, max: 20, median: 30, mean: 40 }, humidity: { min: 10, max: 20, median: 30, mean: 40 }, wind: { min: 10, max: 20, median: 30, mean: 40 }, precipitation: { min: 10, max: 20, median: 30, mean: 40 }, clouds: { min: 10, max: 20, median: 30, mean: 40 } } }";

    public OpenWeatherStatisticalDataRetrieverMock(DataRetriever retriever) {

    }

    @Override
    public String getDataByCity(String arg0, int arg1) throws RemoteException {
        return JSON_RESULT;
    }

    @Override
    public String getDataByCityAndCountryCode(String arg0, String arg1, int arg2) throws RemoteException {
        return JSON_RESULT;
    }

    @Override
    public String getDataByCityAndStateCode(String arg0, String arg1, int arg2) throws RemoteException {
        return JSON_RESULT;
    }

    @Override
    public String getDataByCityAndStateCodeAndCountryCode(String arg0, String arg1, String arg2, int arg3)
            throws RemoteException {
                return JSON_RESULT;
    }

    @Override
    public String getDataByCoordinates(double arg0, double arg1, int arg2) throws RemoteException {
        return JSON_RESULT;
    }

}
