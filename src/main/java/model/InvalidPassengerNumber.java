package model;

import java.io.IOException;

/**
 * The type Invalid passenger number.
 */
public class InvalidPassengerNumber extends IOException {
    /**
     * Instantiates a new Invalid passenger number.
     */
    public InvalidPassengerNumber(){}

    /**
     * Instantiates a new Invalid passenger number.
     *
     * @param message the message
     */
    public InvalidPassengerNumber(String message) {
        super(message);
    }
}
