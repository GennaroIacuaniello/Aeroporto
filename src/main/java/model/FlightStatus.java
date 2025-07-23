package model;

/**
 * Enumeration representing the possible operational states of a flight in the airport management system.
 * <p>
 * This enum defines the lifecycle states that a {@link Flight} can have throughout its
 * operational existence in the system. The flight status is used to track the progression of
 * flights from initial scheduling to final completion, providing critical information for
 * airport operations, passenger services, and system management.
 * </p>
 * <p>
 * The flight status affects various operational aspects including:
 * </p>
 * <ul>
 *   <li>Gate assignment and management</li>
 *   <li>Passenger boarding and check-in procedures</li>
 *   <li>Airport display boards and announcements</li>
 *   <li>Baggage handling operations</li>
 *   <li>Resource allocation and scheduling</li>
 *   <li>Delay and cancellation notifications</li>
 * </ul>
 * <p>
 * The typical flight lifecycle follows this sequence for departing flights:
 * </p>
 * <ol>
 *   <li>{@link #PROGRAMMED} - Initial state when flight is scheduled</li>
 *   <li>{@link #DELAYED} - Flight experiences delays (optional)</li>
 *   <li>{@link #ABOUT_TO_DEPART} - Flight is ready for departure</li>
 *   <li>{@link #DEPARTED} - Flight has left the airport</li>
 * </ol>
 * <p>
 * For arriving flights, the typical sequence is:
 * </p>
 * <ol>
 *   <li>{@link #PROGRAMMED} - Initial state when flight is scheduled</li>
 *   <li>{@link #DELAYED} - Flight experiences delays (optional)</li>
 *   <li>{@link #ABOUT_TO_ARRIVE} - Flight is approaching the airport</li>
 *   <li>{@link #LANDED} - Flight has arrived at the airport</li>
 * </ol>
 * <p>
 * Status transitions are managed by the flight controller and are subject to
 * operational procedures and validation constraints to ensure system consistency
 * and accurate passenger information.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see Arriving
 * @see Departing
 * @see controller.FlightController
 */
public enum FlightStatus {

    /**
     * Indicates that the flight is scheduled and programmed according to the flight plan.
     * <p>
     * This is the initial status assigned to flights when they are first created in the system.
     * PROGRAMMED flights are operating according to their original schedule without any
     * operational disruptions or changes.
     * </p>
     * <p>
     * Key characteristics of PROGRAMMED flights:
     * </p>
     * <ul>
     *   <li>Operating on the original scheduled time</li>
     *   <li>No delays or operational issues reported</li>
     *   <li>Available for booking and passenger services</li>
     *   <li>Gate assignment may be pending or confirmed</li>
     *   <li>Normal check-in and boarding procedures apply</li>
     * </ul>
     */
    PROGRAMMED,

    /**
     * Indicates that the flight has been cancelled and will not operate.
     * <p>
     * This status represents flights that have been permanently cancelled due to
     * various reasons such as weather conditions, mechanical issues, operational
     * constraints, or airline decisions. Cancelled flights require passenger
     * rebooking and refund processing.
     * </p>
     * <p>
     * Key characteristics of CANCELLED flights:
     * </p>
     * <ul>
     *   <li>Flight will not operate as scheduled</li>
     *   <li>All passenger bookings require rebooking or refund</li>
     *   <li>Gate assignment is released</li>
     *   <li>Resources are made available for other flights</li>
     *   <li>Passenger notifications and customer service required</li>
     * </ul>
     */
    CANCELLED,

    /**
     * Indicates that the flight is experiencing delays from its scheduled time.
     * <p>
     * This status represents flights that are still operating but with a delayed
     * schedule. The actual delay duration is typically managed separately through
     * delay fields in the specific flight types (arriving or departing flights).
     * </p>
     * <p>
     * Key characteristics of DELAYED flights:
     * </p>
     * <ul>
     *   <li>Flight will operate but not at the originally scheduled time</li>
     *   <li>New estimated departure/arrival times are provided</li>
     *   <li>Passenger notifications are sent</li>
     *   <li>Gate assignments may be adjusted</li>
     *   <li>Check-in and boarding procedures may be modified</li>
     * </ul>
     */
    DELAYED,

    /**
     * Indicates that a departing flight is ready and about to depart.
     * <p>
     * This status represents the final preparation phase for departing flights,
     * where boarding is typically complete or in final stages, and the aircraft
     * is preparing for pushback from the gate.
     * </p>
     * <p>
     * Key characteristics of ABOUT_TO_DEPART flights:
     * </p>
     * <ul>
     *   <li>Boarding is complete or in final stages</li>
     *   <li>Aircraft doors are being prepared for closure</li>
     *   <li>Final passenger and baggage checks are performed</li>
     *   <li>Air traffic control clearance may be obtained</li>
     *   <li>Late passengers may be denied boarding</li>
     * </ul>
     */
    ABOUT_TO_DEPART,

    /**
     * Indicates that a departing flight has left the airport.
     * <p>
     * This status represents flights that have successfully completed the departure
     * process and are no longer at the airport. The aircraft has pushed back from
     * the gate and commenced its journey to the destination.
     * </p>
     * <p>
     * Key characteristics of DEPARTED flights:
     * </p>
     * <ul>
     *   <li>Aircraft has left the gate and airport</li>
     *   <li>Gate is available for other flights</li>
     *   <li>No further passenger services required at origin</li>
     *   <li>Baggage handling is complete</li>
     *   <li>Flight tracking continues to destination</li>
     * </ul>
     */
    DEPARTED,

    /**
     * Indicates that an arriving flight is approaching and about to arrive.
     * <p>
     * This status represents arriving flights that are in the final approach phase
     * or have been cleared for landing. Ground services and gate preparations
     * are typically being finalized for the arrival.
     * </p>
     * <p>
     * Key characteristics of ABOUT_TO_ARRIVE flights:
     * </p>
     * <ul>
     *   <li>Aircraft is on final approach or circling for landing</li>
     *   <li>Gate assignment is confirmed and being prepared</li>
     *   <li>Ground services are being positioned</li>
     *   <li>Baggage handling equipment is being prepared</li>
     *   <li>Immigration and customs are being notified if international</li>
     * </ul>
     */
    ABOUT_TO_ARRIVE,

    /**
     * Indicates that an arriving flight has successfully landed at the airport.
     * <p>
     * This status represents flights that have completed their journey and are
     * now at the airport. Passengers can disembark, and baggage handling
     * operations commence for arriving passengers.
     * </p>
     * <p>
     * Key characteristics of LANDED flights:
     * </p>
     * <ul>
     *   <li>Aircraft has safely landed and is taxiing to gate</li>
     *   <li>Passenger disembarkation can commence once at gate</li>
     *   <li>Baggage unloading and carousel assignment in progress</li>
     *   <li>Gate services are active for passenger assistance</li>
     *   <li>Aircraft turnaround preparations may begin</li>
     * </ul>
     */
    LANDED

}
