package edu.westga.weatherapp_gui.mocks;

import java.rmi.RemoteException;

import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

public class OpenWeatherIconRetrieverMock implements WeatherIconRetriever {

    @Override
    public String GetWeatherIconUrlByIconId(String arg0) throws RemoteException {
        return "http://openweathermap.org/img/wn/" + arg0 + "@4x.png";
    }
}
