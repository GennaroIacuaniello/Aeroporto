package model;

/**
 * Enumeration representing the possible states of a booking in the airport management system.
 * <p>
 * This enum defines the lifecycle states that a {@link Booking} can have throughout its
 * existence in the system. The booking status is used to track the progression of
 * reservations from initial creation to final completion or cancellation.
 * </p>
 * <p>
 * The booking status affects various business operations including:
 * </p>
 * <ul>
 *   <li>Payment processing and validation</li>
 *   <li>Seat allocation and availability</li>
 *   <li>Customer notification workflows</li>
 *   <li>Check-in eligibility determination</li>
 *   <li>Modification and cancellation policies</li>
 * </ul>
 * <p>
 * The typical booking lifecycle follows this sequence:
 * </p>
 * <ol>
 *   <li>{@link #PENDING} - Initial state when booking is created but not yet confirmed</li>
 *   <li>{@link #CONFIRMED} - Booking is validated and payment is processed</li>
 *   <li>{@link #CANCELLED} - Booking is cancelled by customer or system</li>
 * </ol>
 * <p>
 * Status transitions are managed by the booking controller and are subject to
 * business rules and validation constraints to ensure data integrity and
 * operational consistency.
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
     * This status represents a booking that has been successfully validated,
     * payment has been processed, and seats have been allocated. Confirmed
     * bookings are eligible for check-in and can be modified according to
     * airline policies.
     * </p>
     * <p>
     * Key characteristics of CONFIRMED bookings:
     * </p>
     * <ul>
     *   <li>Payment has been successfully processed</li>
     *   <li>Seats are reserved and allocated</li>
     *   <li>Passengers can check in for the flight</li>
     *   <li>Booking appears in customer's confirmed reservations</li>
     *   <li>Subject to modification and cancellation policies</li>
     * </ul>
     */
    CONFIRMED,

    /**
     * Indicates that the booking is awaiting confirmation or payment processing.
     * <p>
     * This is the initial status assigned to new bookings that have not yet
     * been fully validated or paid for. Pending bookings have temporary seat
     * holds but are not guaranteed until confirmation is completed.
     * </p>
     * <p>
     * Key characteristics of PENDING bookings:
     * </p>
     * <ul>
     *   <li>Temporary seat reservation (subject to timeout)</li>
     *   <li>Payment processing may be in progress</li>
     *   <li>Not eligible for check-in procedures</li>
     *   <li>May be automatically cancelled if not confirmed within time limit</li>
     *   <li>Can be easily modified or upgraded</li>
     * </ul>
     */
    PENDING,

    /**
     * Indicates that the booking has been cancelled and is no longer valid.
     * <p>
     * This status represents bookings that have been cancelled either by the
     * customer, airline, or system due to various reasons such as flight
     * cancellations, payment failures, or customer requests.
     * </p>
     * <p>
     * Key characteristics of CANCELLED bookings:
     * </p>
     * <ul>
     *   <li>Seats are released back to inventory</li>
     *   <li>No check-in privileges</li>
     *   <li>Refund processing may be initiated</li>
     *   <li>Historical record maintained for auditing</li>
     *   <li>Cannot be reactivated (new booking required)</li>
     * </ul>
     */
    CANCELLED

}
