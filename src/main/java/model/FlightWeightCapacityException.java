package model;

import java.io.IOException;

public class FlightWeightCapacityException extends IOException {
    public FlightWeightCapacityException(){}
    public FlightWeightCapacityException(String message) {
        super(message);
    }
}
