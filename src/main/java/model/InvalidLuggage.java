package model;

import java.io.IOException;

/**
 * Exception thrown when luggage validation fails in the airport management system.
 * <p>
 * This exception is raised when attempting to create or modify a {@link Luggage}
 * object with invalid data that violates business rules or data integrity constraints.
 * It extends {@link IOException} to provide specialized error handling for luggage-related
 * operations throughout the system.
 * </p>
 * <p>
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to create luggage without a valid ticket association</li>
 *   <li>Invalid luggage ID or identification parameters</li>
 *   <li>Inconsistent luggage type or status combinations</li>
 *   <li>Invalid luggage status transitions that violate business rules</li>
 *   <li>Data consistency violations in luggage management operations</li>
 *   <li>Missing required fields for luggage creation or updates</li>
 * </ul>
 * <p>
 * The exception provides both a default constructor for generic luggage validation errors
 * and a parameterized constructor that accepts a descriptive error message.
 * This allows for detailed error reporting and helps in debugging luggage-related
 * issues within the airport management system.
 * </p>
 * <p>
 * This exception is typically caught and handled by the luggage controller,
 * ticket management system, and user interface components to provide appropriate
 * feedback to users when luggage operations fail due to validation errors. It ensures
 * that all luggage-related operations maintain data integrity and operational consistency
 * within the baggage handling system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Luggage
 * @see LuggageType
 * @see LuggageStatus
 * @see Ticket
 * @see controller.LuggageController
 * @see IOException
 */
public class InvalidLuggage extends IOException {

    /**
     * Constructs a new InvalidLuggage exception with no detail message.
     * <p>
     * This constructor creates an exception instance without a specific
     * error message. It is typically used when the context of the luggage
     * validation error is clear from the surrounding code or when a
     * generic luggage validation error needs to be signaled.
     * </p>
     */
    public InvalidLuggage(){}

    /**
     * Constructs a new InvalidLuggage exception with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive
     * error message that provides specific information about the luggage
     * validation failure. The message is intended for diagnostic purposes,
     * logging, and user feedback to help identify and resolve luggage-related issues.
     * </p>
     *
     * @param message the detail message explaining the specific luggage validation error.
     *               The message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public InvalidLuggage(String message) {
        super(message);
    }
}