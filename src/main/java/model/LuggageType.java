package model;

/**
 * Enumeration representing the types of luggage in the airport management system.
 * <p>
 * This enum defines the classification types that a {@link Luggage} item can have
 * based on handling procedures and storage location during flight. The luggage type
 * determines where the luggage is stored and how it is processed throughout the
 * baggage handling system, affecting security procedures, weight limits, and
 * passenger access during travel.
 * </p>
 * <p>
 * The luggage type affects various operational aspects including:
 * </p>
 * <ul>
 *   <li>Baggage handling procedures and routing</li>
 *   <li>Security screening requirements and protocols</li>
 *   <li>Weight and size restrictions enforcement</li>
 *   <li>Storage location assignment (cabin vs cargo hold)</li>
 *   <li>Check-in and boarding procedures</li>
 *   <li>Passenger accessibility during flight</li>
 * </ul>
 * <p>
 * The two primary luggage classifications are:
 * </p>
 * <ul>
 *   <li>{@link #CARRY_ON} - Hand luggage that remains with the passenger in the cabin</li>
 *   <li>{@link #CHECKED} - Luggage stored in the aircraft cargo hold</li>
 * </ul>
 * <p>
 * Luggage type assignment is typically determined during the booking or check-in
 * process and affects handling procedures, security requirements, and passenger
 * services throughout the baggage management lifecycle.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Luggage
 * @see LuggageStatus
 * @see Ticket
 * @see LuggageController
 */
public enum LuggageType {

    /**
     * Indicates carry-on luggage that remains with the passenger in the aircraft cabin.
     * <p>
     * This type represents hand luggage (bagaglio a mano) that passengers are allowed
     * to bring into the aircraft cabin and store in overhead compartments or under
     * seats. CARRY_ON luggage remains accessible to passengers during the flight
     * and is subject to specific size and weight restrictions.
     * </p>
     * <p>
     * Key characteristics of CARRY_ON luggage:
     * </p>
     * <ul>
     *   <li>Remains with passenger in the aircraft cabin</li>
     *   <li>Subject to cabin baggage size and weight limits</li>
     *   <li>Accessible to passengers during flight</li>
     *   <li>Enhanced security screening at checkpoints</li>
     *   <li>Restrictions on liquids, sharp objects, and hazardous materials</li>
     *   <li>No baggage claim process required upon arrival</li>
     * </ul>
     */
    CARRY_ON,   //bagaglio a mano

    /**
     * Indicates checked luggage that is stored in the aircraft cargo hold.
     * <p>
     * This type represents luggage (bagaglio da stiva) that is checked in during
     * the departure process and stored in the aircraft's cargo compartment.
     * CHECKED luggage is not accessible to passengers during flight and must
     * be collected at the baggage claim area upon arrival.
     * </p>
     * <p>
     * Key characteristics of CHECKED luggage:
     * </p>
     * <ul>
     *   <li>Stored in aircraft cargo hold during flight</li>
     *   <li>Higher weight and size allowances compared to carry-on</li>
     *   <li>Not accessible to passengers during flight</li>
     *   <li>Standard security screening procedures</li>
     *   <li>Subject to baggage handling and tracking systems</li>
     *   <li>Requires baggage claim collection upon arrival</li>
     * </ul>
     */
    CHECKED    //bagaglio da stiva

}