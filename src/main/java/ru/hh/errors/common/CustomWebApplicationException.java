package ru.hh.errors.common;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * {@link WebApplicationException} of initial version (used in jersey 1.x) does not provide constructor with message. This class allows to specify it.
 */
class CustomWebApplicationException extends WebApplicationException {

  CustomWebApplicationException(Throwable cause, Response response) {
    super(cause, response);
  }

  CustomWebApplicationException(Throwable cause) {
    super(cause);
  }

  CustomWebApplicationException(Response response) {
    super(response);
  }

  private String message;

  @Override
  public String getMessage() {
    return message;
  }

  void setMessage(String message) {
    this.message = message;
  }

}
