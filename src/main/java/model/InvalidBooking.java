package model;

import java.io.IOException;

/**
 * Exception thrown when booking validation fails in the airport management system.
 * <p>
 * This exception is raised when attempting to create or modify a {@link Booking}
 * object with invalid data that violates business rules or data integrity constraints.
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
 *   <li>Business rule violations in booking processing</li>
 *   <li>Invalid booking state transitions</li>
 * </ul>
 * <p>
 * The exception provides both a default constructor for generic booking errors
 * and a parameterized constructor that accepts a descriptive error message.
 * This allows for detailed error reporting and helps in debugging booking-related
 * issues within the airport management system.
 * </p>
 * <p>
 * This exception is typically caught and handled by the booking controller
 * and user interface components to provide appropriate feedback to users
 * when booking operations fail due to validation errors.
 * </p>
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
   * <p>
   * This constructor creates an exception instance without a specific
   * error message. It is typically used when the context of the error
   * is clear from the surrounding code or when a generic booking
   * validation error needs to be signaled.
   * </p>
   */
  public InvalidBooking(){}

  /**
   * Constructs a new InvalidBooking exception with the specified detail message.
   * <p>
   * This constructor creates an exception instance with a descriptive
   * error message that provides specific information about the booking
   * validation failure. The message is intended for diagnostic purposes
   * and user feedback.
   * </p>
   *
   * @param message the detail message explaining the specific booking validation error.
   *               The message is saved for later retrieval by the {@link #getMessage()} method.
   */
  public InvalidBooking(String message) {
    super(message);
  }
}