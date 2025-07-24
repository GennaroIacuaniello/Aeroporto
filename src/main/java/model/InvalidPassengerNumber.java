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
 *   <li>Creating tickets without an associated passenger</li>
 *   <li>Data consistency violations in passenger-ticket relationships</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Booking
 * @see Passenger
 * @see Ticket
 * @see controller.BookingController
 * @see IOException
 */
public class InvalidPassengerNumber extends IOException {

    /**
     * Constructs a new InvalidPassengerNumber exception with no detail message.
     */
    public InvalidPassengerNumber(){}

    /**
     * Constructs a new InvalidPassengerNumber exception with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive
     * error message that provides specific information about the passenger
     * validation failure.
     * </p>
     *
     * @param message the detail message explaining the specific passenger validation error.
     */
    public InvalidPassengerNumber(String message) {
        super(message);
    }
}