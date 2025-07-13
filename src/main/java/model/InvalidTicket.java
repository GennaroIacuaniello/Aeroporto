package model;

import java.io.IOException;

public class InvalidTicket  extends IOException {
    public InvalidTicket(){}
    public InvalidTicket(String message) {
        super(message);
    }
}
