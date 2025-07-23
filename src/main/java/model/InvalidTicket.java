package model;

import java.io.IOException;

/**
 * The type Invalid ticket.
 */
public class InvalidTicket  extends IOException {
    /**
     * Instantiates a new Invalid ticket.
     */
    public InvalidTicket(){}

    /**
     * Instantiates a new Invalid ticket.
     *
     * @param message the message
     */
    public InvalidTicket(String message) {
        super(message);
    }
}
