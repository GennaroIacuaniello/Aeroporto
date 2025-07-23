package controller;

import model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class PassengerController {

    private ArrayList<Passenger> searchBookingResult;

    public List<Passenger> getSearchBookingResult() {
        return searchBookingResult;
    }

    public void setSearchBookingResult(List<Passenger> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Passenger>) searchBookingResult;
    }

}
