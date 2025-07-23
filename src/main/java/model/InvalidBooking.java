package model;

import java.io.IOException;

/**
 * The type Invalid booking.
 */
public class InvalidBooking extends IOException {
    /**
     * Instantiates a new Invalid booking.
     */
    public InvalidBooking(){}

    /**
     * Instantiates a new Invalid booking.
     *
     * @param message the message
     */
    public InvalidBooking(String message) {
    super(message);
  }
}
