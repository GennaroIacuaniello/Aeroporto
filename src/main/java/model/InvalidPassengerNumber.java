package model;

import java.io.IOException;

public class InvalidPassengerNumber extends IOException {
    public InvalidPassengerNumber(){}
    public InvalidPassengerNumber(String message) {
        super(message);
    }
}
