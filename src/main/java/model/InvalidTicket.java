package model;

import java.io.IOException;

/**
 * Exception thrown when ticket validation fails in the airport management system.
 * <p>
 * This exception is raised when attempting to create or modify a {@link Ticket}
 * object with invalid data that violates business rules or data integrity constraints.
 * It extends {@link IOException} to provide specialized error handling for ticket-related
 * operations throughout the system.
 * </p>
 * <p>
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to create a ticket without a valid ticket number</li>
 *   <li>Creating tickets without proper flight associations</li>
 *   <li>Invalid ticket-passenger relationships during creation</li>
 *   <li>Missing required booking references for ticket operations</li>
 *   <li>Luggage association failures with ticket objects</li>
 *   <li>Data consistency violations in ticket management operations</li>
 * </ul>
 * <p>
 * The exception provides both a default constructor for generic ticket validation errors
 * and a parameterized constructor that accepts a descriptive error message.
 * This allows for detailed error reporting and helps in debugging ticket-related
 * issues within the airport management system.
 * </p>
 * <p>
 * This exception is typically caught and handled by the ticket controller,
 * booking system, passenger management components, and user interface elements
 * to provide appropriate feedback to users when ticket operations fail due to
 * validation errors. It ensures that all ticket-related operations maintain
 * data integrity and proper associations between tickets, passengers, flights,
 * bookings, and luggage within the system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Ticket
 * @see Passenger
 * @see Flight
 * @see Booking
 * @see Luggage
 * @see TicketController
 * @see IOException
 */
public class InvalidTicket extends IOException {

    /**
     * Constructs a new InvalidTicket exception with no detail message.
     * <p>
     * This constructor creates an exception instance without a specific
     * error message. It is typically used when the context of the ticket
     * validation error is clear from the surrounding code or when a
     * generic ticket validation error needs to be signaled.
     * </p>
     */
    public InvalidTicket(){}

    /**
     * Constructs a new InvalidTicket exception with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive
     * error message that provides specific information about the ticket
     * validation failure. The message is intended for diagnostic purposes,
     * logging, and user feedback to help identify and resolve ticket-related issues.
     * </p>
     *
     * @param message the detail message explaining the specific ticket validation error.
     *               The message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public InvalidTicket(String message) {
        super(message);
    }
}