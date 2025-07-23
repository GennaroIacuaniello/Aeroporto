package model;

import java.io.IOException;

public class InvalidBuyer extends IOException {
  public InvalidBuyer(){}
  public InvalidBuyer(String message) {
    super(message);
  }
}
