package edu.westga.weatherapp_gui.viewmodel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks.LocationSearcherExceptionMock;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.LocationSearcherMock;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;

public class ChangeLocationPageViewModelTests {
    
    @Test
    public void constructorValid() {
        LocationSearcherMock locationSearcherMock = new LocationSearcherMock();
        ChangeLocationPageViewModel viewModel = new ChangeLocationPageViewModel(locationSearcherMock);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorNullLocationSearcher() {
        ChangeLocationPageViewModel viewModel = new ChangeLocationPageViewModel(null);
        assertNotNull(viewModel);
    }

    @Test
    public void getLocationSearchResultsSuccessfullyCatchesException() {
        LocationSearcher locationSearcher = new LocationSearcherExceptionMock();
        ChangeLocationPageViewModel viewModel = new ChangeLocationPageViewModel(locationSearcher);
        assertNull(viewModel.getLocationSearchResults("Newnan"));
    }

    @Test
    public void getLocationSearchResultsThrowsExceptionWithNullCity() {
        LocationSearcher locationSearcher = new LocationSearcherMock();
        ChangeLocationPageViewModel viewModel = new ChangeLocationPageViewModel(locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getLocationSearchResults(null);
        });
    }
}
