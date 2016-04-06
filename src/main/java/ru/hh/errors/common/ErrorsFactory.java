package ru.hh.errors.common;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import java.util.function.BiFunction;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class ErrorsFactory {

  static BiFunction<String, Integer, Object> error(Object key) {
    return (s, i) -> new Errors(i, key, s);
  }

  /**
   * Constructs WAE with provided {@link Errors} container as response entity.
   *
   * @param errors
   *          errors container
   */
  public static WebApplicationException error(Errors errors) {
    return new ErrorResponseBuilder(null).setStatus(errors.code).setEntityCreator((s, i) -> errors).toException();
  }

  /**
   * Constructs WAE with {@link Errors} as response entity.
   *
   * @param code
   *          response status code
   */
  public static WebApplicationException error(int code, Object errorKey, String description, Throwable cause) {
    return new ErrorResponseBuilder(description).setStatus(code).setEntityCreator(error(errorKey)).setCause(cause).toException();
  }

  /**
   * Constructs WAE with {@link Errors} as response entity.
   *
   * @param status
   *          response status code
   */
  public static WebApplicationException error(Status status, Object errorKey, String description, Throwable cause) {
    return error(status.getStatusCode(), errorKey, description, cause);
  }

  /**
   * Constructs WAE with {@link Errors} as response entity and {@link Status#BAD_REQUEST} as response status code.
   */
  public static WebApplicationException errorBadRequest(Object errorKey, String description) {
    return error(BAD_REQUEST, errorKey, description, null);
  }

  /**
   * Constructs and throws WAE with {@link Errors} as response entity and {@link Status#BAD_REQUEST} as response status code.
   */
  public static <T> T throwBadRequest(Object errorKey, String description) throws WebApplicationException {
    throw errorBadRequest(errorKey, description);
  }

  /**
   * Constructs WAE with {@link Errors} as response entity and {@link Status#CONFLICT} as response status code.
   */
  public static WebApplicationException errorConflict(Object errorKey, String description) {
    return error(CONFLICT, errorKey, description, null);
  }

  /**
   * Constructs and throws WAE with {@link Errors} as response entity and {@link Status#CONFLICT} as response status code.
   */
  public static <T> T throwConflict(Object errorKey, String description) throws WebApplicationException {
    throw errorConflict(errorKey, description);
  }

  /**
   * Constructs WAE with {@link Errors} as response entity and {@link Status#NOT_FOUND} as response status code.
   */
  public static WebApplicationException errorNotFound(Object errorKey, String description) {
    return error(NOT_FOUND, errorKey, description, null);
  }

  /**
   * Constructs and throws WAE with {@link Errors} as response entity and {@link Status#NOT_FOUND} as response status code.
   */
  public static <T> T throwNotFound(Object errorKey, String description) throws WebApplicationException {
    throw errorNotFound(errorKey, description);
  }

  /**
   * Constructs WAE with {@link Errors} as response entity and {@link Status#FORBIDDEN} as response status code.
   */
  public static WebApplicationException errorForbidden(Object errorKey, String description) {
    return error(FORBIDDEN, errorKey, description, null);
  }

  /**
   * Constructs and throws WAE with {@link Errors} as response entity and {@link Status#FORBIDDEN} as response status code.
   */
  public static <T> T throwForbidden(Object errorKey, String description) throws WebApplicationException {
    throw errorForbidden(errorKey, description);
  }

  /**
   * Constructs WAE with {@link Errors} as response entity and {@link Status#INTERNAL_SERVER_ERROR} as response status code.
   */
  public static WebApplicationException errorInternalServerError(Object errorKey, String description) {
    return error(INTERNAL_SERVER_ERROR, errorKey, description, null);
  }

  /**
   * Constructs and throws WAE with {@link Errors} as response entity and {@link Status#INTERNAL_SERVER_ERROR} as response status code.
   */
  public static <T> T throwInternalServerError(Object errorKey, String description) throws WebApplicationException {
    throw errorInternalServerError(errorKey, description);
  }

}
