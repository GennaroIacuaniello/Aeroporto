package model;

/**
 * Enumeration representing the possible states of luggage in the airport management system.
 * <p>
 * This enum defines the lifecycle states that a {@link Luggage} item can have throughout its
 * journey in the baggage handling system. The luggage status is used to track the progression of
 * baggage items from initial booking to final delivery or loss reporting, providing critical
 * information for baggage operations, passenger services, and system management.
 * </p>
 * <p>
 * The luggage status affects various operational aspects including:
 * </p>
 * <ul>
 *   <li>Baggage handling and tracking operations</li>
 *   <li>Passenger notification and communication</li>
 *   <li>Airport baggage carousel management</li>
 *   <li>Lost luggage reporting and recovery</li>
 *   <li>Baggage claim processes and procedures</li>
 *   <li>Customer service and support operations</li>
 * </ul>
 * <p>
 * The typical luggage lifecycle follows this sequence:
 * </p>
 * <ol>
 *   <li>{@link #BOOKED} - Initial state when luggage is registered with a booking</li>
 *   <li>{@link #LOADED} - Luggage has been loaded onto the aircraft</li>
 *   <li>{@link #WITHDRAWABLE} - Luggage is available for passenger collection</li>
 *   <li>{@link #LOST} - Luggage has been reported as lost or missing</li>
 * </ol>
 * <p>
 * Status transitions are managed by the luggage controller and baggage handling systems,
 * and are subject to operational procedures and validation constraints to ensure accurate
 * luggage tracking and proper passenger service throughout the baggage handling process.
 * </p>
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
     * processed through the baggage handling system.
     * </p>
     * <p>
     * Key characteristics of BOOKED luggage:
     * </p>
     * <ul>
     *   <li>Registered in the system with a valid ticket association</li>
     *   <li>Not yet physically processed or tagged</li>
     *   <li>Available for modification during booking changes</li>
     *   <li>Awaiting check-in and baggage drop-off procedures</li>
     *   <li>Can be cancelled or modified before physical processing</li>
     * </ul>
     */
    BOOKED,

    /**
     * Indicates that the luggage has been loaded onto the aircraft.
     * <p>
     * This status represents luggage that has been processed through the
     * baggage handling system, tagged, and successfully loaded onto the
     * aircraft for transport. LOADED luggage is in transit and cannot be
     * modified or retrieved until arrival at the destination.
     * </p>
     * <p>
     * Key characteristics of LOADED luggage:
     * </p>
     * <ul>
     *   <li>Physically loaded onto the aircraft cargo hold</li>
     *   <li>Cannot be modified or retrieved until arrival</li>
     *   <li>Tracked through the flight management system</li>
     *   <li>In transit to the destination airport</li>
     *   <li>Subject to aircraft security and safety protocols</li>
     * </ul>
     */
    LOADED,

    /**
     * Indicates that the luggage is available for passenger collection.
     * <p>
     * This status represents luggage that has arrived at the destination
     * and has been unloaded from the aircraft, making it available for
     * passenger retrieval at the baggage claim area. WITHDRAWABLE luggage
     * is ready for collection by the passenger or authorized representative.
     * </p>
     * <p>
     * Key characteristics of WITHDRAWABLE luggage:
     * </p>
     * <ul>
     *   <li>Available at the baggage claim carousel or collection area</li>
     *   <li>Passenger can collect the luggage with proper identification</li>
     *   <li>Tracked for collection time and passenger notification</li>
     *   <li>Subject to baggage claim time limits and policies</li>
     *   <li>May be transferred to lost luggage if not collected within time limit</li>
     * </ul>
     */
    WITHDRAWABLE,

    /**
     * Indicates that the luggage has been reported as lost or missing.
     * <p>
     * This status represents luggage that cannot be located or has been
     * reported missing by passengers or baggage handling systems. LOST
     * luggage triggers special handling procedures, passenger notifications,
     * and recovery efforts to locate and return the items to their owners.
     * </p>
     * <p>
     * Key characteristics of LOST luggage:
     * </p>
     * <ul>
     *   <li>Cannot be located in the baggage handling system</li>
     *   <li>Reported missing by passenger or detected by system</li>
     *   <li>Subject to lost luggage reporting and recovery procedures</li>
     *   <li>Triggers customer service and compensation processes</li>
     *   <li>May be recovered and status updated to WITHDRAWABLE</li>
     * </ul>
     */
    LOST

}