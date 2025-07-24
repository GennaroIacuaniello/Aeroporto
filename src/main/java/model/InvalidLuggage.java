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
 *   <li>Inconsistent luggage type or status combinations</li>
 *   <li>Invalid luggage status transitions</li>
 * </ul>
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
     */
    public InvalidLuggage(){}

    /**
     * Constructs a new InvalidLuggage exception with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive
     * error message that provides specific information about the luggage
     * validation failure.
     * </p>
     *
     * @param message the detail message explaining the specific luggage validation error.
     */
    public InvalidLuggage(String message) {
        super(message);
    }
}