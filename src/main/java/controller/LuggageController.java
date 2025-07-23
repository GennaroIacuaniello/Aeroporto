package controller;

import implementazioni_postgres_dao.LuggageDAOImpl;
import model.Luggage;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Luggage controller.
 */
public class LuggageController {

    private ArrayList<Luggage> searchBookingResult;
    private ArrayList<Integer> searchBookingResultIds;

    /**
     * Gets search booking result.
     *
     * @return the search booking result
     */
    public List<Luggage> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets search booking result.
     *
     * @param searchBookingResult the search booking result
     */
    public void setSearchBookingResult(List<Luggage> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Luggage>) searchBookingResult;
    }

    /**
     * Gets search booking result ids.
     *
     * @return the search booking result ids
     */
    public List<Integer> getSearchBookingResultIds() {
        return searchBookingResultIds;
    }

    /**
     * Sets search booking result ids.
     *
     * @param searchBookingResultIds the search booking result ids
     */
    public void setSearchBookingResultIds(List<Integer> searchBookingResultIds) {
        this.searchBookingResultIds = (ArrayList<Integer>) searchBookingResultIds;
    }

    /**
     * Lost luggage.
     *
     * @param ticket        the ticket
     * @param luggageStatus the luggage status
     */
    public void lostLuggage (String ticket, String luggageStatus) {

        LuggageDAOImpl luggageDAO = new LuggageDAOImpl();

        luggageDAO.lostLuggage(ticket, luggageStatus);
    }
}
