package controller;

import model.Luggage;

import java.util.ArrayList;
import java.util.List;

public class LuggageController {

    private ArrayList<Luggage> searchBookingResult;
    private ArrayList<Integer> searchBookingResultIds;

    public List<Luggage> getSearchBookingResult() {
        return searchBookingResult;
    }

    public void setSearchBookingResult(List<Luggage> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Luggage>) searchBookingResult;
    }

    public List<Integer> getSearchBookingResultIds() {
        return searchBookingResultIds;
    }

    public void setSearchBookingResultIds(List<Integer> searchBookingResultIds) {
        this.searchBookingResultIds = (ArrayList<Integer>) searchBookingResultIds;
    }
}
