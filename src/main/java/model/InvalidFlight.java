package model;

import java.io.IOException;

public class InvalidFlight extends IOException {
  public InvalidFlight(){}
  public InvalidFlight(String message) {
    super(message);
  }
}
