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
 *   <li>Gate number conflicts or duplicate assignments</li>
 *   <li>Invalid gate references in flight operations</li>
 *   <li>Data consistency violations in gate management</li>
 * </ul>
 * <p>
 * The exception provides both a default constructor for generic gate validation errors
 * and a parameterized constructor that accepts a descriptive error message.
 * This allows for detailed error reporting and helps in debugging gate-related
 * issues within the airport management system.
 * </p>
 * <p>
 * This exception is typically caught and handled by the gate controller,
 * flight management system, and user interface components to provide appropriate
 * feedback to users when gate operations fail due to validation errors. It ensures
 * that all gate-related operations maintain data integrity and operational consistency
 * within the airport's physical infrastructure constraints.
 * </p>
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
     * <p>
     * This constructor creates an exception instance without a specific
     * error message. It is typically used when the context of the gate
     * validation error is clear from the surrounding code or when a
     * generic gate validation error needs to be signaled.
     * </p>
     */
    public InvalidGate(){}

    /**
     * Constructs a new InvalidGate exception with the specified detail message.
     * <p>
     * This constructor creates an exception instance with a descriptive
     * error message that provides specific information about the gate
     * validation failure. The message is intended for diagnostic purposes,
     * logging, and user feedback to help identify and resolve gate-related issues.
     * </p>
     *
     * @param message the detail message explaining the specific gate validation error.
     *               The message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public InvalidGate(String message) {
        super(message);
    }
}