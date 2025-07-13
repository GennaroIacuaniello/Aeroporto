package model;

import java.io.IOException;

public class InvalidLuggage extends IOException {
    public InvalidLuggage(){}
    public InvalidLuggage(String message) {
        super(message);
    }
}
