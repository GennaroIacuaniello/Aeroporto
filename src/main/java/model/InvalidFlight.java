package model;

import java.io.IOException;

/**
 * Exception thrown when flight validation fails in the airport management system.
 * <p>
 * This exception is raised when attempting to create or modify a {@link Flight}
 * object with invalid data that violates business rules or data integrity constraints.
 * It extends {@link IOException} to provide specialized error handling for flight-related
 * operations throughout the system.
 * </p>
 * <p>
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to create a flight with null or invalid flight references</li>
 *   <li>Invalid flight ID or company name parameters</li>
 *   <li>Inconsistent flight scheduling data (dates, times)</li>
 *   <li>Invalid seat configuration or capacity values</li>
 *   <li>Flight status transitions that violate business rules</li>
 *   <li>Gate assignment conflicts or invalid gate references</li>
 * </ul>
 * <p>
 * The exception provides both a default constructor for generic flight validation errors
 * and a parameterized constructor that accepts a descriptive error message.
 * This allows for detailed error reporting and helps in debugging flight-related
 * issues within the airport management system.
 * </p>
 * <p>
 * This exception is typically caught and handled by the flight controller,
 * booking system, and user interface components to provide appropriate feedback
 * to users when flight operations fail due to validation errors. It ensures that
 * all flight-related operations maintain data integrity and operational consistency.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see Arriving
 * @see Departing
 * @see FlightStatus
 * @see Ticket
 * @see Booking
 * @see IOException
 */
public class InvalidFlight extends IOException {

  /**
   * Constructs a new InvalidFlight exception with no detail message.
   * <p>
   * This constructor creates an exception instance without a specific
   * error message. It is typically used when the context of the flight
   * validation error is clear from the surrounding code or when a
   * generic flight validation error needs to be signaled.
   * </p>
   */
  public InvalidFlight(){}

  /**
   * Constructs a new InvalidFlight exception with the specified detail message.
   * <p>
   * This constructor creates an exception instance with a descriptive
   * error message that provides specific information about the flight
   * validation failure. The message is intended for diagnostic purposes,
   * logging, and user feedback to help identify and resolve flight-related issues.
   * </p>
   *
   * @param message the detail message explaining the specific flight validation error.
   *               The message is saved for later retrieval by the {@link #getMessage()} method.
   */
  public InvalidFlight(String message) {
    super(message);
  }
}