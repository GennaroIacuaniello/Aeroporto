package model;

import java.io.IOException;

/**
 * The type Invalid luggage.
 */
public class InvalidLuggage extends IOException {
    /**
     * Instantiates a new Invalid luggage.
     */
    public InvalidLuggage(){}

    /**
     * Instantiates a new Invalid luggage.
     *
     * @param message the message
     */
    public InvalidLuggage(String message) {
        super(message);
    }
}
