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
 * The two primary luggage classifications are:
 * </p>
 * <ul>
 *   <li>{@link #CARRY_ON} - Hand luggage that remains with the passenger in the cabin</li>
 *   <li>{@link #CHECKED} - Luggage stored in the aircraft cargo hold</li>
 * </ul>
 * <p>
 * Luggage type assignment is typically determined during the booking or check-in
 * process and affects handling procedures.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Luggage
 * @see LuggageStatus
 * @see Ticket
 * @see controller.LuggageController
 */
public enum LuggageType {

    /**
     * Indicates carry-on luggage that remains with the passenger in the aircraft cabin.
     * <p>
     * This type represents hand luggage that passengers are allowed
     * to bring into the aircraft cabin and store in overhead compartments or under
     * seats. CARRY_ON luggage remains accessible to passengers during the flight
     * and is subject to specific size and weight restrictions.
     * </p>
     */
    CARRY_ON,   //bagaglio a mano

    /**
     * Indicates checked luggage that is stored in the aircraft cargo hold.
     * <p>
     * This type represents luggage that is checked in during
     * the departure process and stored in the aircraft's cargo compartment.
     * CHECKED luggage is not accessible to passengers during flight and must
     * be collected at the luggage claim area after arrival.
     * </p>
     */
    CHECKED    //bagaglio da stiva

}