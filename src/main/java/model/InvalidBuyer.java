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
 *   <li>Authentication or authorization failures for the buyer</li>
 *   <li>Data consistency violations in customer-booking relationships</li>
 * </ul>
 * <p>
 * The exception provides both a default constructor for generic buyer validation errors
 * and a parameterized constructor that accepts a descriptive error message.
 * This allows for detailed error reporting and helps in debugging buyer-related
 * issues within the airport management system.
 * </p>
 * <p>
 * This exception is typically caught and handled by the booking controller,
 * customer service components, and user interface elements to provide appropriate
 * feedback to users when booking operations fail due to buyer validation errors.
 * The exception ensures that all bookings have a valid customer associated with them,
 * maintaining data integrity and enabling proper customer service operations.
 * </p>
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
   * <p>
   * This constructor creates an exception instance without a specific
   * error message. It is typically used when the context of the buyer
   * validation error is clear from the surrounding code or when a
   * generic buyer validation error needs to be signaled.
   * </p>
   */
  public InvalidBuyer(){}

  /**
   * Constructs a new InvalidBuyer exception with the specified detail message.
   * <p>
   * This constructor creates an exception instance with a descriptive
   * error message that provides specific information about the buyer
   * validation failure. The message is intended for diagnostic purposes,
   * logging, and user feedback to help identify and resolve buyer-related issues.
   * </p>
   *
   * @param message the detail message explaining the specific buyer validation error.
   *               The message is saved for later retrieval by the {@link #getMessage()} method.
   */
  public InvalidBuyer(String message) {
    super(message);
  }
}