package model;

/**
 * Enumeration representing the possible states of luggage in the airport management system.
 * <p>
 * This enum defines the lifecycle states that a {@link Luggage} item can have throughout its
 * journey in the luggage handling system. The luggage status is used to track the progression of
 * luggage items from initial booking to final delivery or loss reporting, providing critical
 * information for luggage operations, passenger services, and system management.
 * </p>
 * <p>
 * The luggage status affects various operational aspects including:
 * </p>
 * <ul>
 *   <li>luggage handling and tracking operations</li>
 *   <li>Lost luggage reporting and recovery</li>
 *   <li>luggage claim processes and procedures</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Luggage
 * @see LuggageType
 * @see Ticket
 * @see controller.LuggageController
 */
public enum LuggageStatus {

    /**
     * Indicates that the luggage has been registered and booked with a ticket.
     * <p>
     * This is the initial status assigned to luggage items when they are first
     * created during the booking process. BOOKED luggage represents items that
     * have been registered in the system but have not yet been physically
     * processed through the luggage handling system.
     * </p>
     * <p>
     * Key characteristics of BOOKED luggage:
     * </p>
     * <ul>
     *   <li>Registered in the system with a valid ticket association</li>
     *   <li>Not yet physically processed</li>
     *   <li>Available for modification during booking changes</li>
     *   <li>Awaiting check-in</li>
     *   <li>Can be cancelled or modified before physical processing</li>
     * </ul>
     */
    BOOKED,

    /**
     * Indicates that the luggage has been loaded onto the aircraft.
     * <p>
     * Key characteristics of LOADED luggage:
     * </p>
     * <ul>
     *   <li>Physically loaded onto the aircraft cargo hold</li>
     *   <li>In transit to the destination airport</li>
     * </ul>
     */
    LOADED,

    /**
     * Indicates that the luggage is available for passenger collection.
     * <p>
     * This status represents luggage that has arrived at the destination
     * and has been unloaded from the aircraft, making it available for
     * passenger retrieval at the luggage claim area. WITHDRAWABLE luggage
     * is ready for collection by the passenger, and it's the only status
     * in which a luggage can be reported as lost.
     * </p>
     * <p>
     * Key characteristics of WITHDRAWABLE luggage:
     * </p>
     * <ul>
     *   <li>Available at the luggage retrieval area</li>
     *   <li>Passenger can collect the luggage</li>
     *   <li>Can be reported as lost</li>
     * </ul>
     */
    WITHDRAWABLE,

    /**
     * Indicates that the luggage has been reported as lost.
     * <p>
     * This status represents luggages that have been reported missing
     * by passengers. LOST luggage triggers special handling procedures,
     * handled by the administrators of the system.
     * </p>
     * <p>
     * Key characteristics of LOST luggage:
     * </p>
     * <ul>
     *   <li>Reported missing by passenger</li>
     *   <li>Subject to lost luggage reporting and recovery procedures</li>
     *   <li>Administrators can handle the list of lost luggages</li>
     *   <li>May be recovered and status updated to WITHDRAWABLE</li>
     * </ul>
     */
    LOST

}