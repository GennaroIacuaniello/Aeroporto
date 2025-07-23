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
 * <p>
 * The class follows a search result management pattern, maintaining synchronized collections
 * of {@link Luggage} objects and their corresponding database IDs. This enables efficient
 * retrieval and correlation of luggage data for display purposes and administrative operations.
 * </p>
 * <p>
 * Luggage operations supported by this controller include:
 * </p>
 * <ul>
 *   <li>Lost luggage reporting and status updates</li>
 *   <li>Luggage search result management for booking operations</li>
 *   <li>Integration with customer service workflows</li>
 *   <li>Support for luggage recovery operations</li>
 * </ul>
 * <p>
 * The controller maintains tight integration with the database layer through {@link LuggageDAOImpl},
 * ensuring that luggage status changes are immediately persisted and available across all
 * system components for operational consistency and customer service accuracy.
 * </p>
 * <p>
 * All luggage operations are designed to maintain data integrity and provide reliable
 * tracking capabilities throughout the luggage lifecycle, from initial booking through
 * final delivery or loss reporting.
 * </p>
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
     * <p>
     * This method returns the list of {@link Luggage} objects that were retrieved
     * from search operations, typically used for administrative functions, customer
     * service operations, or luggage management tasks where luggage information
     * needs to be displayed or processed.
     * </p>
     * <p>
     * The search results contain luggage objects with complete information including
     * identification, type, status, and ticket associations, enabling comprehensive
     * luggage management and customer service support.
     * </p>
     * <p>
     * The returned collection maintains correlation with the corresponding database
     * IDs, enabling efficient data operations and selection workflows for luggage
     * management purposes.
     * </p>
     *
     * @return list of {@link Luggage} objects from search operations, or null if no results are available
     */
    public List<Luggage> getSearchBookingResult() {
        return searchBookingResult;
    }

    /**
     * Sets the collection of luggage objects for search results.
     * <p>
     * This method establishes the list of {@link Luggage} objects returned from
     * search operations, converting the input list to an ArrayList for internal
     * consistency and performance optimization. The search results are used for
     * administrative functions and luggage management operations.
     * </p>
     * <p>
     * The method ensures that luggage search results are properly stored and
     * available for subsequent retrieval and manipulation operations throughout
     * luggage management workflows and customer service interfaces.
     * </p>
     * <p>
     * This method is typically called by search operations in the main controller
     * or by administrative functions that need to populate luggage result sets
     * for display or processing purposes.
     * </p>
     *
     * @param searchBookingResult list of {@link Luggage} objects to set as search results
     */
    public void setSearchBookingResult(List<Luggage> searchBookingResult) {
        this.searchBookingResult = (ArrayList<Luggage>) searchBookingResult;
    }

    /**
     * Retrieves the collection of database IDs corresponding to luggage search results.
     * <p>
     * This method returns the list of database identifiers that correspond to the
     * luggage objects in the search results. The IDs maintain synchronized indexing
     * with the luggage objects, enabling efficient correlation between display
     * data and database operations.
     * </p>
     * <p>
     * The database IDs are essential for performing operations on selected luggage
     * items, such as status updates, administrative actions, or detailed information
     * retrieval. They provide the link between user interface selections and
     * database records.
     * </p>
     * <p>
     * The returned list maintains the same size and order as the corresponding
     * luggage objects list, ensuring proper data correlation for luggage
     * management operations.
     * </p>
     *
     * @return list of database identifiers corresponding to search result luggage, or null if no results are available
     */
    public List<Integer> getSearchBookingResultIds() {
        return searchBookingResultIds;
    }

    /**
     * Sets the collection of database IDs corresponding to luggage search results.
     * <p>
     * This method establishes the list of database identifiers that correspond to
     * luggage search results, converting the input list to an ArrayList for internal
     * consistency. The IDs must maintain synchronized indexing with the luggage
     * objects to ensure proper correlation between display data and database operations.
     * </p>
     * <p>
     * Proper synchronization between luggage objects and their database IDs is
     * essential for maintaining data integrity and enabling correct luggage
     * selection and operation targeting in administrative and customer service
     * functions.
     * </p>
     * <p>
     * This method is typically called in conjunction with setting the luggage
     * search results to ensure that both collections are properly initialized
     * and correlated.
     * </p>
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
     * <p>
     * Common usage scenarios include:
     * </p>
     * <ul>
     *   <li>Customer reporting luggage as lost (status: "LOST")</li>
     *   <li>Administrative marking luggage as found (status: "WITHDRAWABLE")</li>
     *   <li>System-initiated status updates during baggage handling</li>
     *   <li>Recovery operations for previously lost luggage</li>
     * </ul>
     * <p>
     * The method delegates the actual database operation to {@link LuggageDAOImpl},
     * which handles the SQL execution and transaction management. Status updates
     * are applied immediately to ensure real-time accuracy across all system
     * components and customer service interfaces.
     * </p>
     * <p>
     * This functionality is essential for maintaining accurate luggage tracking,
     * enabling effective customer service, and supporting luggage recovery
     * operations throughout the airport baggage handling system.
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