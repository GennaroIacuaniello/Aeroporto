package model;

import java.io.IOException;

public class InvalidGate extends IOException {
    public InvalidGate(){}
    public InvalidGate(String message) {
        super(message);
    }
}
