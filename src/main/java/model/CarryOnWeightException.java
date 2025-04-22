package model;

import java.io.IOException;

public class CarryOnWeightException extends IOException {
    public CarryOnWeightException(){}
    public CarryOnWeightException(String message) {
        super(message);
    }
}
