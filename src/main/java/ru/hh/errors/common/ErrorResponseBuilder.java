package ru.hh.errors.common;

import java.util.Optional;
import java.util.function.BiFunction;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class ErrorResponseBuilder {

  private String message;
  private int status;
  private Optional<BiFunction<String, Integer, Object>> entityCreator = Optional.empty();
  private Optional<Throwable> cause = Optional.empty();

  public ErrorResponseBuilder(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public ErrorResponseBuilder setEntityCreator(BiFunction<String, Integer, Object> entityCreator) {
    this.entityCreator = Optional.of(entityCreator);
    return this;
  }

  public ErrorResponseBuilder setStatus(int status) {
    this.status = status;
    return this;
  }

  public ErrorResponseBuilder appendToMessage(String addition) {
    this.message += " " + addition;
    return this;
  }

  public ErrorResponseBuilder setCause(Throwable cause) {
    this.cause = Optional.of(cause);
    return this;
  }

  public WebApplicationException toException() {
    ResponseBuilder builder = Response.status(status);
    entityCreator.ifPresent(ec -> builder.entity(ec.apply(message, status)));
    Response response = builder.build();
    CustomWebApplicationException exception = cause
        .map(c -> new CustomWebApplicationException(c, response))
        .orElseGet(() -> new CustomWebApplicationException(response));
    exception.setMessage(message);
    return exception;
  }

}
