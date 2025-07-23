package controller;

import model.Passenger;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Passenger controller.
 */
public class PassengerController {

    private ArrayList<Passenger> searchBookingResult;

    /**
     * Gets search booking result.
     *
     * @return the search booking result
     */
    public List<Passenger> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets search booking result.
     *
     * @param searchBookingResult the search booking result
     */
    public void setSearchBookingResult(List<Passenger> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Passenger>) searchBookingResult;
    }

}
