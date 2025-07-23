package model;

import java.io.IOException;

public class InvalidBooking extends IOException {
  public InvalidBooking(){}
  public InvalidBooking(String message) {
    super(message);
  }
}
