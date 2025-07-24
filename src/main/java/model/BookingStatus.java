package model;

/**
 * Enumeration representing the possible states of a booking in the airport management system.
 * <p>
 * This enum defines the lifecycle states that a {@link Booking} can have throughout its
 * existence in the system.
 * </p>
 * <p>
 * The booking status affects various business operations, including:
 * </p>
 * <ul>
 *   <li>Seat allocation and availability</li>
 *   <li>Check-in possibility determination</li>
 *   <li>Modification and cancellation</li>
 * </ul>
 * <p>
 * Status transitions are managed by the booking controller and are subject to
 * validation constraints to ensure data integrity and operational consistency.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Booking
 * @see Flight
 * @see Customer
 */
public enum BookingStatus {

    /**
     * Indicates that the booking has been confirmed and is valid for travel.
     * <p>
     * This status represents a booking that has been successfully validated.
     * For confirmed bookings the check-in is possible, and they can be modified
     * only following the system policies.
     * </p>
     * <p>
     * Key characteristics of CONFIRMED bookings:
     * </p>
     * <ul>
     *   <li>Seats are reserved and allocated</li>
     *   <li>Passengers can check in for the flight</li>
     * </ul>
     */
    CONFIRMED,

    /**
     * Indicates that the booking is awaiting confirmation.
     * <p>
     * This is the initial status assigned to new bookings that have not yet
     * been fully validated.
     * </p>
     * <p>
     * Key characteristics of PENDING bookings:
     * </p>
     * <ul>
     *   <li>Temporary seat reservation</li>
     *   <li>Not possible to check in</li>
     *   <li>May be automatically cancelled if not confirmed before the flight's opening of check-ins</li>
     *   <li>Can be easily modified</li>
     * </ul>
     */
    PENDING,

    /**
     * Indicates that the booking has been cancelled and is no longer valid.
     * <p>
     * This status represents bookings that have been cancelled either by the
     * customer, airline, or system due to various reasons such as
     *  a flight's opening of check-ins.
     * </p>
     * <p>
     * Key characteristics of CANCELLED bookings:
     * </p>
     * <ul>
     *   <li>Seats are released back to inventory</li>
     *   <li>No check-in possible</li>
     *   <li>Historical record maintained</li>
     *   <li>Cannot be reactivated (new booking required)</li>
     * </ul>
     */
    CANCELLED

}
