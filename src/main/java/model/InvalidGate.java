package model;

import java.io.IOException;

/**
 * The type Invalid gate.
 */
public class InvalidGate extends IOException {
    /**
     * Instantiates a new Invalid gate.
     */
    public InvalidGate(){}

    /**
     * Instantiates a new Invalid gate.
     *
     * @param message the message
     */
    public InvalidGate(String message) {
        super(message);
    }
}
