package edu.westga.weatherapp_gui.mocks.NormalLogicMocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

public class OpenWeatherIconRetrieverMock implements WeatherIconRetriever {

    @Override
    public String getWeatherIconUrlByIconId(String arg0) throws RemoteException {
        return "http://openweathermap.org/img/wn/" + arg0 + "@4x.png";
    }
}
