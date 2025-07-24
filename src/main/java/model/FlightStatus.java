package model;

/**
 * Enumeration representing the possible operational states of a flight in the airport management system.
 * <p>
 * This enum defines the lifecycle states that a {@link Flight} can have throughout its
 * operational existence in the system. The flight status is used to track the progression of
 * flights from initial scheduling to final completion.
 * </p>
 * <p>
 * The flight status affects various operational aspects, including:
 * </p>
 * <ul>
 *   <li>Gate assignment and management</li>
 *   <li>Passenger boarding and check-in procedures</li>
 *   <li>Airport display boards</li>
 *   <li>Luggage handling operations</li>
 *   <li>Delay and cancellation</li>
 * </ul>
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
     * operationalchanges.
     * </p>
     * <p>
     * Key characteristics of PROGRAMMED flights:
     * </p>
     * <ul>
     *   <li>Operating on the original scheduled time</li>
     *   <li>No delays or operational issues reported</li>
     *   <li>Available for booking and passenger services</li>
     * </ul>
     */
    PROGRAMMED,

    /**
     * Indicates that the flight has been cancelled and will not operate.
     * <p>
     * This status represents flights that have been permanently cancelled due to
     * various reasons such as weather conditions, mechanical issues, operational
     * constraints, or airline decisions.
     * </p>
     */
    CANCELLED,

    /**
     * Indicates that the flight is delayed from its scheduled time.
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
     *   <li>Gate assignments may be adjusted</li>
     * </ul>
     */
    DELAYED,

    /**
     * Indicates that check-ins are opened for a departing flight.
     */
    ABOUT_TO_DEPART,

    /**
     * Indicates that a departing flight has left the airport.
     * <p>
     * This status represents flights that have successfully completed the departure
     * process and are no longer at the airport.
     * </p>
     * <p>
     * Key characteristics of DEPARTED flights:
     * </p>
     * <ul>
     *   <li>Aircraft has left the gate and airport</li>
     *   <li>Luggage handling in the airport is completed</li>
     *   <li>Flight and luggage tracking continues to destination</li>
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
     *   <li>Gate assignment is being prepared or confirmed</li>
     *   <li>Luggage handling is being prepared</li>
     * </ul>
     */
    ABOUT_TO_ARRIVE,

    /**
     * Indicates that an arriving flight has successfully landed at the airport.
     * <p>
     * Key characteristics of LANDED flights:
     * </p>
     * <ul>
     *   <li>Aircraft has safely landed</li>
     *   <li>Passenger disembarkation can start once at gate</li>
     *   <li>Luggage unloading in progress or already ended</li>
     * </ul>
     */
    LANDED

}
