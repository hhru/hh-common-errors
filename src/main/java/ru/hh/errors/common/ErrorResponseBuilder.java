package ru.hh.errors.common;

import java.util.Optional;
import java.util.function.BiFunction;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Construct WAE ({@link WebApplicationException}) with different attributes of it's {@link Response}. See {@link ErrorsFactory} for convenient
 * shortcuts. If there is no suitable method consider adding it instead of using this class directly.
 */
public class ErrorResponseBuilder {

  private String message;
  private int status;
  private Optional<BiFunction<String, Integer, Object>> entityCreator = Optional.empty();
  private Optional<Throwable> cause = Optional.empty();

  /**
   * Construct builder.
   *
   * @param message
   *          used for setting {@link Throwable#getMessage()} as well as passing it for entity creator. Can be null.
   */
  public ErrorResponseBuilder(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  /**
   * Set function that is able to produce entity for WAE's response. Function accepts message and status code and produces any object that can be
   * serialized to framework's format of choice.
   */
  public ErrorResponseBuilder setEntityCreator(BiFunction<String, Integer, Object> entityCreator) {
    this.entityCreator = Optional.of(entityCreator);
    return this;
  }

  /**
   * Set response status code.
   */
  public ErrorResponseBuilder setStatus(int status) {
    this.status = status;
    return this;
  }

  /**
   * Append additional info to current message.
   */
  public ErrorResponseBuilder appendToMessage(String addition) {
    this.message = this.message == null ? addition : this.message + " " + addition;
    return this;
  }

  /**
   * Set cause for resulting WAE.
   */
  public ErrorResponseBuilder setCause(Throwable cause) {
    this.cause = Optional.of(cause);
    return this;
  }

  /**
   * Construct WAE.
   */
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
