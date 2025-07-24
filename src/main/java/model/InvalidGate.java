package model;

import java.io.IOException;

/**
 * Exception thrown when gate validation fails in the airport management system.
 * <p>
 * This exception is raised when attempting to create or modify a {@link Gate}
 * object with invalid data that violates business rules or data integrity constraints.
 * It extends {@link IOException} to provide specialized error handling for gate-related
 * operations throughout the system.
 * </p>
 * <p>
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to create a gate with an ID outside the valid range (1-20)</li>
 *   <li>Invalid gate assignment operations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Gate
 * @see Flight
 * @see controller.GateController
 * @see IOException
 */
public class InvalidGate extends IOException {

    /**
     * Constructs a new InvalidGate exception with no detail message.
     */
    public InvalidGate(){}

    /**
     * Constructs a new InvalidGate exception with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive
     * error message that provides specific information about the gate
     * validation failure.
     * </p>
     *
     * @param message the detail message explaining the specific gate validation error.
     */
    public InvalidGate(String message) {
        super(message);
    }
}