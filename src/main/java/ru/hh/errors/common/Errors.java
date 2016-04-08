package ru.hh.errors.common;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Container consisting of one or multiple {@link Error} objects.
 *
 * Can be serialized to suitable format by converter that accepts JAXB annotations (i.e. Jackson for JSON).
 */
@XmlRootElement(name = "errors")
public class Errors {

  // default constructor for deserialization
  public Errors() {
  }

  @XmlAttribute
  public int code;

  @XmlElement(name = "error")
  public List<Error> errors;

  /**
   * Add error to container.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public Errors add(Object errorKey, String description) {
    this.errors.add(new Error(errorKey, description));
    return this;
  }

  public boolean hasErrors() {
    return errors != null && !errors.isEmpty();
  }

  /**
   * Construct WAE with this container as response entity.
   */
  public WebApplicationException toException() {
    return new WebApplicationException(Response.status(code).entity(this).build());
  }

  /**
   * Construct container with single error.
   *
   * @param statusCode
   *          response HTTP status code
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public static Errors of(int statusCode, Object key, String description) {
    Errors errors = new Errors();
    errors.code = statusCode;
    return errors.add(key, description);
  }

  /**
   * Construct container with single error.
   *
   * @param statusCode
   *          response HTTP status code
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public static Errors of(Status statusCode, Object errorKey, String description) {
    return of(statusCode.getStatusCode(), errorKey, description);
  }

  /**
   * Construct empty container.
   *
   * @param statusCode
   *          response HTTP status code
   */
  public static Errors of(int statusCode) {
    Errors errors = new Errors();
    errors.code = statusCode;
    return errors;
  }

  /**
   * Construct empty container.
   *
   * @param statusCode
   *          response HTTP status code
   */
  public static Errors of(Status statusCode) {
    return of(statusCode.getStatusCode());
  }

}

