package model;

import java.io.IOException;

/**
 * Exception thrown when passenger number validation fails in the airport management system.
 * <p>
 * This exception is raised when attempting to create or modify a {@link Booking}, {@link Ticket},
 * or {@link Passenger} object with invalid passenger data that violates business rules or data
 * integrity constraints. It extends {@link IOException} to provide specialized error handling
 * for passenger-related validation operations throughout the system.
 * </p>
 * <p>
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to create a booking without any passengers or tickets</li>
 *   <li>Trying to create a passenger without a valid SSN (Social Security Number)</li>
 *   <li>Creating tickets without an associated passenger</li>
 *   <li>Invalid passenger count in booking operations</li>
 *   <li>Missing required passenger identification information</li>
 *   <li>Data consistency violations in passenger-ticket relationships</li>
 * </ul>
 * <p>
 * The exception provides both a default constructor for generic passenger validation errors
 * and a parameterized constructor that accepts a descriptive error message.
 * This allows for detailed error reporting and helps in debugging passenger-related
 * issues within the airport management system.
 * </p>
 * <p>
 * This exception is typically caught and handled by the booking controller,
 * passenger management system, and user interface components to provide appropriate
 * feedback to users when passenger operations fail due to validation errors. It ensures
 * that all passenger-related operations maintain data integrity and that every booking
 * has at least one valid passenger associated with it.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Booking
 * @see Passenger
 * @see Ticket
 * @see BookingController
 * @see IOException
 */
public class InvalidPassengerNumber extends IOException {

    /**
     * Constructs a new InvalidPassengerNumber exception with no detail message.
     * <p>
     * This constructor creates an exception instance without a specific
     * error message. It is typically used when the context of the passenger
     * validation error is clear from the surrounding code or when a
     * generic passenger validation error needs to be signaled.
     * </p>
     */
    public InvalidPassengerNumber(){}

    /**
     * Constructs a new InvalidPassengerNumber exception with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive
     * error message that provides specific information about the passenger
     * validation failure. The message is intended for diagnostic purposes,
     * logging, and user feedback to help identify and resolve passenger-related issues.
     * </p>
     *
     * @param message the detail message explaining the specific passenger validation error.
     *               The message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public InvalidPassengerNumber(String message) {
        super(message);
    }
}