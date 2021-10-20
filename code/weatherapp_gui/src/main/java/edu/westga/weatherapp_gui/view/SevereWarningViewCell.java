package edu.westga.weatherapp_gui.view;

import edu.westga.weatherapp_gui.model.SevereWeatherWarning;
import javafx.scene.control.ListCell;

/**
 * Represents a cell in a view object that uses that displays a severe weather
 * warning object
 * 
 * @author Eboni Walker
 * @version 1.0
 */
public class SevereWarningViewCell extends ListCell<SevereWeatherWarning> {
    @Override
    protected void updateItem(SevereWeatherWarning item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            this.setText(null);
        } else {
            this.setText(item.getWarningName());
        }
    }
}
