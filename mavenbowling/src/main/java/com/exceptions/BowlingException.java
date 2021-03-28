package com.exceptions;

public class BowlingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BowlingException(String message) {
    super(message);
  }
  
}
