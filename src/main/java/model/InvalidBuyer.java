package model;

import java.io.IOException;

/**
 * The type Invalid buyer.
 */
public class InvalidBuyer extends IOException {
    /**
     * Instantiates a new Invalid buyer.
     */
    public InvalidBuyer(){}

    /**
     * Instantiates a new Invalid buyer.
     *
     * @param message the message
     */
    public InvalidBuyer(String message) {
    super(message);
  }
}
