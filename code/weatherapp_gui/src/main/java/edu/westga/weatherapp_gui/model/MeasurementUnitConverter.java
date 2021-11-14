package edu.westga.weatherapp_gui.model;

import java.text.DecimalFormat;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;

/**
 * This class converts temperatures from Kelvin to Celsius or Fahrenheit
 * 
 * @author Michael Pavich
 */
public class MeasurementUnitConverter {

    /**
     * The format for all units with decimals
     */
    private static final String DECIMAL_FORMAT = "0.00";

    /**
     * Converts a given temperature from the starting unit to the ending unit
     * 
     * @param temp - the temperature to be converted
     * @param startingUnit - the starting unit of the temperature
     * @param endingUnit - the ending unit of the temperature
     * @return the converted temperature
     */
    public static double convertTemperature(double temp, MeasurementUnits startingUnit, MeasurementUnits endingUnit) {
        double convertedTemp = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);

        if (startingUnit == endingUnit) {
            return Double.parseDouble(decimalFormat.format(temp));
        }

        if (startingUnit == MeasurementUnits.Imperial) {
            if (endingUnit == MeasurementUnits.Metric) {
                convertedTemp = convertFahrenheitToCelsius(temp);
            } else if (endingUnit == MeasurementUnits.Kelvin) {
                convertedTemp = convertFahreenheitToKelvin(temp);
            }
        } else if (startingUnit == MeasurementUnits.Metric) {
            if (endingUnit == MeasurementUnits.Imperial) {
                convertedTemp = convertCelsiusToFahrenheit(temp);
            } else if (endingUnit == MeasurementUnits.Kelvin) {
                convertedTemp = convertCelsiusToKelvin(temp);
            }
        } else {
            if (endingUnit == MeasurementUnits.Imperial) {
                convertedTemp = convertKelvinToFahrenheit(temp);
            } else if (endingUnit == MeasurementUnits.Metric) {
                convertedTemp = convertKelvinToCelsius(temp);
            }
        }

        return Double.parseDouble(decimalFormat.format(convertedTemp));
    }

    /**
     * Converts a given pressure from the starting unit to the ending unit
     * 
     * @param pressure - the pressure to convert
     * @param startingUnit - the starting unit of the pressure
     * @param endingUnit - the ending unit of the pressure
     * @return the converted pressure
     */
    public static double convertPressure(double pressure, MeasurementUnits startingUnit, MeasurementUnits endingUnit) {
        double convertedPressure = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);

        if (startingUnit == endingUnit) {
            return Double.parseDouble(decimalFormat.format(pressure));
        }
    
        if (startingUnit == MeasurementUnits.Imperial) {
            if (endingUnit == MeasurementUnits.Metric) {
                convertedPressure = convertPsiToPa(pressure);
            } else if (endingUnit == MeasurementUnits.Kelvin) {
                convertedPressure = convertPsiToHpa(pressure);
            }
        } else if (startingUnit == MeasurementUnits.Metric) {
            if (endingUnit == MeasurementUnits.Imperial) {
                convertedPressure = convertPaToPsi(pressure);
            } else if (endingUnit == MeasurementUnits.Kelvin) {
                convertedPressure = convertPaToHpa(pressure);
            }
        } else {
            if (endingUnit == MeasurementUnits.Imperial) {
                convertedPressure = convertHpaToPsi(pressure);
            } else if (endingUnit == MeasurementUnits.Metric) {
                convertedPressure = convertHpaToPa(pressure);
            }
        }

        return Double.parseDouble(decimalFormat.format(convertedPressure));
    }

    /**
     * Converts a given wind speed from the starting unit to the ending unit
     * 
     * @param speed - the wind speed to convert
     * @param startingUnit - the starting unit of the wind speed
     * @param endingUnit - the ending unit of the wind speed
     * @return the converted wind speed
     */
    public static double convertWind(double speed, MeasurementUnits startingUnit, MeasurementUnits endingUnit) {
        double convertedSpeed = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);

        if (startingUnit == endingUnit) {
            return Double.parseDouble(decimalFormat.format(speed));
        }

        if ((startingUnit == MeasurementUnits.Metric || startingUnit == MeasurementUnits.Kelvin) && endingUnit == MeasurementUnits.Imperial) {
            convertedSpeed = convertMetersToFeet(speed);
        } else if (startingUnit == MeasurementUnits.Imperial && (endingUnit == MeasurementUnits.Metric || endingUnit == MeasurementUnits.Kelvin)) {
            convertedSpeed = convertFeetToMeters(speed);
        } else {
            convertedSpeed = speed;
        }

        return Double.parseDouble(decimalFormat.format(convertedSpeed));
    }

    /**
     * Converts a given precipitation volume from the starting unit to the ending unit
     * 
     * @param volume - the precipitation volume to convert
     * @param startingUnit - the starting unit of the precipitation volume
     * @param endingUnit - the ending unit of the precipitation volume
     * @return the converted precipitation volume
     */
    public static double convertPrecipitation(double volume, MeasurementUnits startingUnit, MeasurementUnits endingUnit) {
        double convertedVolume = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);

        if (startingUnit == endingUnit) {
            return Double.parseDouble(decimalFormat.format(volume));
        }

        if ((startingUnit == MeasurementUnits.Metric || startingUnit == MeasurementUnits.Kelvin) && endingUnit == MeasurementUnits.Imperial) {
            convertedVolume = convertMillimetersToInches(volume);
        } else if (startingUnit == MeasurementUnits.Imperial && (endingUnit == MeasurementUnits.Metric || endingUnit == MeasurementUnits.Kelvin)) {
            convertedVolume = convertInchesToMillimeters(volume);
        } else {
            convertedVolume = volume;
        }

        return Double.parseDouble(decimalFormat.format(convertedVolume));
    }
    
    /**
     * Converts a temperature from Kelvin to Fahrenheit
     * 
     * @param temperature - the temperature to convert
     * @return the temperature in Fahrenheit
     */
    public static double convertKelvinToFahrenheit(double temperature) {
        return (temperature - 273.15) * 9 / 5 + 32;
    }

    /**
     * Converts a temperature from Kelvin to Celsius
     * @param temperature - the temperature to convert
     * @return the temperature in Celsius
     */
    public static double convertKelvinToCelsius(double temperature) {
        return temperature - 273.15;
    }

    /**
     * Converts a temperature from Celsius to Fahrenheit
     * 
     * @param temperature - the temperature to convert
     * @return the temperature in Fahrenheit
     */
    public static double convertCelsiusToFahrenheit(double temperature) {
        return temperature * 9 / 5 + 32;
    }

    /**
     * Converts a temperature from Celsius to Kelvin
     * 
     * @param temperature - the temperature to convert
     * @return the temperature in Kelvin
     */
    public static double convertCelsiusToKelvin(double temperature) {
        return temperature + 273.15;
    }

    /**
     * Converts a temperature from Fahrenheit to Celsius
     * 
     * @param temperature - the temperature to convert
     * @return the temperature in Celsius
     */
    public static double convertFahrenheitToCelsius(double temperature) {
        return (temperature - 32) * 5 / 9;
    }

    /**
     * Converts a temperature from farhrenheit to Kelvin
     * 
     * @param temperature - the temperature to convert
     * @return the temperature in Kelvin
     */
    public static double convertFahreenheitToKelvin(double temperature) {
        return (temperature - 32) * 5 / 9 + 273.15;
    }

    /**
     * Converts a pressure from hpa to psi
     * 
     * @param pressure - the pressure to convert
     * @return the pressure in psi
     */
    public static double convertHpaToPsi(double pressure) {
        return pressure * 0.0145037738;
    }

    /**
     * Converts a pressure from hpa to pa
     * 
     * @param pressure - the pressure to convert
     * @return the pressure in pa
     */
    public static double convertHpaToPa(double pressure) {
        return pressure * 100;
    }

    /**
     * Converts a pressure from psi to hpa
     * 
     * @param pressure - the pressure to convert
     * @return the pressure in hpa
     */
    public static double convertPsiToHpa(double pressure) {
        return pressure * 68.9476;
    }

    /**
     * Converts a pressure from psi to pa
     * 
     * @param pressure - the pressure to convert
     * @return the pressure in pa
     */
    public static double convertPsiToPa(double pressure) {
        return pressure * 6894.75729;
    }

    /**
     * Converts a pressure from pa to hpa
     * 
     * @param pressure - the pressure to convert
     * @return the pressure in hpa
     */
    public static double convertPaToHpa(double pressure) {
        return pressure * 0.01;
    }

    /**
     * Converts a pressure from pa to psi
     * 
     * @param pressure - the pressure to convert
     * @return the pressure in psi
     */
    public static double convertPaToPsi(double pressure) {
        return pressure * 0.000145037738;
    }

    /**
     * Converts a wind speed from meters per second to feet per second
     * 
     * @param meters - the wind speed to convert
     * @return the wind speed in feet per second
     */
    public static double convertMetersToFeet(double meters) {
        return meters * 3.28084;
    }

    /**
     * Converts a wind speed from feet per second to meters per second
     * 
     * @param feet - the wind speed to convert
     * @return the wind speed in meters per second
     */
    public static double convertFeetToMeters(double feet) {
        return feet * 0.3048;
    }

    /**
     * Converts a precipitation volume from millimeters to inches
     * 
     * @param millimeters - the precipitation volume to convert
     * @return the precipitation volume in inches
     */
    public static double convertMillimetersToInches(double millimeters) {
        return millimeters * 0.0393700787;
    }

    /**
     * Converts a precipitation volume from inches to millimeters
     * 
     * @param inches - the precipitation volume to convert
     * @return the precipitation volume in millimeters
     */
    public static double convertInchesToMillimeters(double inches) {
        return inches * 25.4;
    }
}
