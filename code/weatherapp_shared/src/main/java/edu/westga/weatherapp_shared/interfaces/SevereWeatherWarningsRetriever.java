package edu.westga.weatherapp_shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Severe Weather Warnings Retriever interface. Extends the remote class to
 * allow for Remote Method Invocation. Objects that implement this interface
 * should be able to obtain the severe weather warnings for a given location.
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public interface SevereWeatherWarningsRetriever extends Remote {

    /**
     * Gets an array of the severe weather warnings for the given location
     * 
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @param unit      the temperature unit to get data in
     * 
     * @precondition -90 >= latitude <= 90 && -180 >= longitude <= 180 && unit.class.equals(MeasurementUnits.class)
     * @postconditon none
     * 
     * @return a string array of the severe weather warnings for the given location
     * @throws RemoteException
     */
    Object[] getSevereWeatherWarningsForLocation(double latitude, double longitude,
            Enum<?> unit) throws RemoteException, IllegalArgumentException;
}