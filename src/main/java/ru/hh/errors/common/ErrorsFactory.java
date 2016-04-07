package ru.hh.errors.common;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import java.util.function.BiFunction;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

/**
 * Conveniently create or throw WAE ({@link WebApplicationException}) with {@link Errors} as response entity.
 */
public class ErrorsFactory {

  // avoid instantiation
  private ErrorsFactory() {
  }

  private static BiFunction<String, Integer, Object> toEntityCreator(Object key) {
    return (s, i) -> new Errors(i, key, s);
  }

  /**
   * Construct WAE with provided {@link Errors} container as response entity.
   *
   * @param errors
   *          errors container
   */
  public static WebApplicationException error(Errors errors) {
    return new ErrorResponseBuilder(null).setStatus(errors.code).setEntityCreator((s, i) -> errors).toException();
  }

  /**
   * Construct WAE with {@link Errors} as response entity.
   *
   * @param code
   *          response status code
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException error(int code, Object errorKey, String description, Throwable cause) {
    return new ErrorResponseBuilder(description).setStatus(code).setEntityCreator(toEntityCreator(errorKey)).setCause(cause).toException();
  }

  /**
   * Construct WAE with {@link Errors} as response entity.
   *
   * @param status
   *          response status code
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException error(Status status, Object errorKey, String description, Throwable cause) {
    return error(status.getStatusCode(), errorKey, description, cause);
  }

  /**
   * Construct WAE with {@link Errors} as response entity.
   *
   * @param status
   *          response status code
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException error(Status status, Object errorKey, String description) {
    return error(status.getStatusCode(), errorKey, description);
  }

  /**
   * Construct WAE with {@link Errors} as response entity.
   *
   * @param code
   *          response status code
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException error(int code, Object errorKey, String description) {
    return new ErrorResponseBuilder(description).setStatus(code).setEntityCreator(toEntityCreator(errorKey)).toException();
  }

  /**
   * Construct WAE with {@link Errors} as response entity and {@link Status#BAD_REQUEST} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException errorBadRequest(Object errorKey, String description) {
    return error(BAD_REQUEST, errorKey, description, null);
  }

  /**
   * Construct and throw WAE with {@link Errors} as response entity and {@link Status#BAD_REQUEST} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static <T> T throwBadRequest(Object errorKey, String description) throws WebApplicationException {
    throw errorBadRequest(errorKey, description);
  }

  /**
   * Construct WAE with {@link Errors} as response entity and {@link Status#CONFLICT} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException errorConflict(Object errorKey, String description) {
    return error(CONFLICT, errorKey, description, null);
  }

  /**
   * Construct and throw WAE with {@link Errors} as response entity and {@link Status#CONFLICT} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static <T> T throwConflict(Object errorKey, String description) throws WebApplicationException {
    throw errorConflict(errorKey, description);
  }

  /**
   * Construct WAE with {@link Errors} as response entity and {@link Status#NOT_FOUND} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException errorNotFound(Object errorKey, String description) {
    return error(NOT_FOUND, errorKey, description, null);
  }

  /**
   * Construct and throw WAE with {@link Errors} as response entity and {@link Status#NOT_FOUND} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static <T> T throwNotFound(Object errorKey, String description) throws WebApplicationException {
    throw errorNotFound(errorKey, description);
  }

  /**
   * Construct WAE with {@link Errors} as response entity and {@link Status#FORBIDDEN} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException errorForbidden(Object errorKey, String description) {
    return error(FORBIDDEN, errorKey, description, null);
  }

  /**
   * Construct and throw WAE with {@link Errors} as response entity and {@link Status#FORBIDDEN} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static <T> T throwForbidden(Object errorKey, String description) throws WebApplicationException {
    throw errorForbidden(errorKey, description);
  }

  /**
   * Construct WAE with {@link Errors} as response entity and {@link Status#INTERNAL_SERVER_ERROR} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static WebApplicationException errorInternalServerError(Object errorKey, String description) {
    return error(INTERNAL_SERVER_ERROR, errorKey, description, null);
  }

  /**
   * Construct and throw WAE with {@link Errors} as response entity and {@link Status#INTERNAL_SERVER_ERROR} as response status code.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   */
  public static <T> T throwInternalServerError(Object errorKey, String description) throws WebApplicationException {
    throw errorInternalServerError(errorKey, description);
  }

}
