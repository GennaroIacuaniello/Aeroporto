package model;

import java.io.IOException;

/**
 * Exception thrown when buyer validation fails in the airport management system.
 * <p>
 * This exception is raised when attempting to create or modify a {@link Booking}
 * object with an invalid customer buyer that violates business rules or data integrity
 * constraints. It extends {@link IOException} to provide specialized error handling
 * for buyer-related validation operations throughout the system.
 * </p>
 * <p>
 * Common scenarios that trigger this exception include:
 * </p>
 * <ul>
 *   <li>Attempting to create a booking with a null buyer reference</li>
 *   <li>Providing an invalid or unregistered customer as the buyer</li>
 *   <li>Customer account status preventing booking operations</li>
 *   <li>Data consistency violations in customer-booking relationships</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Booking
 * @see Customer
 * @see controller.BookingController
 * @see IOException
 */
public class InvalidBuyer extends IOException {

  /**
   * Constructs a new InvalidBuyer exception with no detail message.
   */
  public InvalidBuyer(){}

  /**
   * Constructs a new InvalidBuyer exception with the specified detail message.
   * <p>
   * This constructor creates an exception instance with a descriptive
   * error message that provides specific information about the buyer
   * validation failure.
   * </p>
   *
   * @param message the detail message explaining the specific buyer validation error.
   */
  public InvalidBuyer(String message) {
    super(message);
  }
}