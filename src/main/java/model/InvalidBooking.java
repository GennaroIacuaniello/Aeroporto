package model;

import java.io.IOException;

/**
 * Exception thrown when booking validation fails in the airport management system.
 * <p>
 * This exception is raised when attempting to create or modify a {@link Booking}
 * object with invalid data that violates rules or data integrity constraints.
 * It extends {@link IOException} to provide specialized error handling for booking-related
 * operations throughout the system.
 * </p>
 * <p>
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to create a booking without required fields</li>
 *   <li>Invalid relationships between booking components</li>
 *   <li>Data consistency violations during booking operations</li>
 *   <li>Invalid booking state transitions</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Booking
 * @see BookingStatus
 * @see Ticket
 * @see IOException
 */
public class InvalidBooking extends IOException {

  /**
   * Constructs a new InvalidBooking exception with no detail message.
   */
  public InvalidBooking(){}

  /**
   * Constructs a new InvalidBooking exception with the specified detail message.
   * <p>
   * This constructor creates an exception instance with a descriptive
   * error message that provides specific information about the booking
   * validation failure.
   * </p>
   *
   * @param message the detail message explaining the specific booking validation error.
   */
  public InvalidBooking(String message) {
    super(message);
  }
}