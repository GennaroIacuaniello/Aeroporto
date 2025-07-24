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
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Ticket
 * @see Passenger
 * @see Flight
 * @see Booking
 * @see Luggage
 * @see controller.TicketController
 * @see IOException
 */
public class InvalidTicket extends IOException {

    /**
     * Constructs a new InvalidTicket exception with no detail message.
     */
    public InvalidTicket(){}

    /**
     * Constructs a new InvalidTicket exception with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive
     * error message that provides specific information about the ticket
     * validation failure.
     * </p>
     *
     * @param message the detail message explaining the specific ticket validation error.
     */
    public InvalidTicket(String message) {
        super(message);
    }
}