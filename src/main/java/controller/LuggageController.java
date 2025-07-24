package controller;

import implementazioni_postgres_dao.LuggageDAOImpl;
import model.Luggage;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing luggage operations and status tracking in the airport management system.
 * <p>
 * This class serves as the specialized controller for luggage-related operations within the MVC
 * architecture of the airport management system. It handles luggage search results, status updates,
 * and provides methods for managing luggage-related administrative functions through integration
 * with the database layer and business logic components.
 * </p>
 * <p>
 * The LuggageController is responsible for:
 * </p>
 * <ul>
 *   <li>Managing luggage search results and associated metadata</li>
 *   <li>Handling luggage status updates and lost luggage reporting</li>
 *   <li>Coordinating with the {@link LuggageDAOImpl} for database operations</li>
 *   <li>Supporting administrative luggage management operations</li>
 *   <li>Maintaining collections of luggage objects and their database identifiers</li>
 *   <li>Providing access to luggage information for customer service operations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Luggage
 * @see LuggageDAOImpl
 * @see model.LuggageType
 * @see model.LuggageStatus
 * @see Controller
 */
public class LuggageController {

    /**
     * Collection of luggage objects returned from search operations.
     * Used for displaying luggage information and enabling administrative
     * operations on luggage items within booking contexts.
     */
    private ArrayList<Luggage> searchBookingResult;
    
    /**
     * Collection of database identifiers corresponding to luggage in searchBookingResult.
     * Maintains synchronized indexing to enable efficient luggage retrieval and selection
     * for administrative and customer service operations.
     */
    private ArrayList<Integer> searchBookingResultIds;

    /**
     * Retrieves the collection of luggage objects from search results.
     *
     * @return list of {@link Luggage} objects from search operations, or null if no results are available
     */
    public List<Luggage> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of luggage objects for search results.
     *
     * @param searchBookingResult list of {@link Luggage} objects to set as search results
     */
    public void setSearchBookingResult(List<Luggage> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Luggage>) searchBookingResult;
    }

    /**
     * Retrieves the collection of database IDs corresponding to luggage search results.
     *
     * @return list of database identifiers corresponding to search result luggage, or null if no results are available
     */
    public List<Integer> getSearchBookingResultIds() {
        return searchBookingResultIds;
    }

    /**
     * Sets the collection of database IDs corresponding to luggage search results.
     *
     * @param searchBookingResultIds list of database identifiers to set for search results
     */
    public void setSearchBookingResultIds(List<Integer> searchBookingResultIds) {
        this.searchBookingResultIds = (ArrayList<Integer>) searchBookingResultIds;
    }

    /**
     * Reports luggage as lost or updates luggage status based on operational requirements.
     * <p>
     * This method processes luggage status updates by interfacing with the database
     * through {@link LuggageDAOImpl} to update the status of luggage associated with
     * a specific ticket. The operation supports both lost luggage reporting and
     * status recovery operations depending on the provided status parameter.
     * </p>
     *
     * @param ticket the ticket number associated with the luggage to update
     * @param luggageStatus the new status to set for the luggage (e.g., "LOST", "WITHDRAWABLE")
     */
    public void lostLuggage (String ticket, String luggageStatus) {

        LuggageDAOImpl luggageDAO = new LuggageDAOImpl();

        luggageDAO.lostLuggage(ticket, luggageStatus);
    }
}