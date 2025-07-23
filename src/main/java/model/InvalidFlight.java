package model;

import java.io.IOException;

/**
 * The type Invalid flight.
 */
public class InvalidFlight extends IOException {
    /**
     * Instantiates a new Invalid flight.
     */
    public InvalidFlight(){}

    /**
     * Instantiates a new Invalid flight.
     *
     * @param message the message
     */
    public InvalidFlight(String message) {
    super(message);
  }
}
