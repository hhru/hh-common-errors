package ru.hh.errors.common;

import static java.util.Objects.requireNonNull;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
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

  /**
   * Construct container with single error.
   *
   * @param code
   *          actual HTTP status code
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public Errors(int code, Object errorKey, String description) {
    this(code);
    add(errorKey, description);
  }

  /**
   * Construct container with single error.
   *
   * @param statusCode
   *          actual HTTP status code
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public Errors(Status statusCode, Object errorKey, String description) {
    this(requireNonNull(statusCode, "status must be not null").getStatusCode(), errorKey, description);
  }

  /**
   * Construct empty container.
   *
   * @param code
   *          actual HTTP status code
   */
  public Errors(int code) {
    this.code = code;
    this.errors = new ArrayList<>();
  }

  /**
   * Construct empty container.
   *
   * @param code
   *          actual HTTP status code
   */
  public Errors(Status statusCode) {
    this(requireNonNull(statusCode, "status must be not null").getStatusCode());
  }

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
    return ErrorsFactory.error(this);
  }

}

