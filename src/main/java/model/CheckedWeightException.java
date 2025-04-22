package model;

import java.io.IOException;

public class CheckedWeightException extends IOException {
    public CheckedWeightException(){}
    public CheckedWeightException(String message) {
        super(message);
    }
}
