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
 *   <li>Invalid association between a booking and a flight</li>
 *   <li>Invalid association between a ticket and a flight</li>
 * </ul>
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
   */
  public InvalidFlight(){}

  /**
   * Constructs a new InvalidFlight exception with the specified detail message.
   * <p>
   * This constructor creates an exception instance with a descriptive
   * error message that provides specific information about the flight
   * validation failure.
   * </p>
   *
   * @param message the detail message explaining the specific flight validation error.
   */
  public InvalidFlight(String message) {
    super(message);
  }
}