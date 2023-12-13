package ru.hh.errors.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Container consisting of one or multiple {@link Error} objects.
 * <p/>
 * Can be serialized to suitable format by converter that accepts JAXB annotations (i.e. Jackson for JSON).
 */
@XmlRootElement(name = "errors")
public class Errors extends AbstractErrors<Error> {
  protected List<Error> errors = new ArrayList<>();

  /**
   * @deprecated Do not use this constructor directly. It is used by serialization frameworks
   */
  @Deprecated
  public Errors() {
    super();
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
   * @param location
   *          location of the error, can be null
   */
  public Errors(int statusCode, Object errorKey, String description, String location) {
    super(statusCode);
    add(errorKey, description, location);
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
  public Errors(int statusCode, Object errorKey, String description) {
    this(statusCode, errorKey, description, null);
  }

  /**
   * Construct empty container.
   *
   * @param statusCode
   *          response HTTP status code
   */
  public Errors(int statusCode) {
    super(statusCode);
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
   * @param location
   *          location of the error, can be null
   */
  public Errors(Status statusCode, Object errorKey, String description, String location) {
    this(statusCode.getStatusCode(), errorKey, description, location);
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
  public Errors(Status statusCode, Object errorKey, String description) {
    this(statusCode.getStatusCode(), errorKey, description, null);
  }

  /**
   * Construct empty container.
   *
   * @param statusCode
   *          response HTTP status code
   */
  public Errors(Status statusCode) {
    super(statusCode);
  }

  // override for JAXB

  @Override
  @XmlElement(name = "error")
  public List<Error> getErrors() {
    return errors;
  }

  public void setErrors(List<Error> errors) {
    this.errors = errors;
  }

  /**
   * @deprecated Do not use this method: it is required only to serialize/deserialize 'errors' key
   */
  @XmlElement(name = "errors")
  @Deprecated
  public List<Error> getError() {
    return getErrors();
  }

  /**
   * @deprecated Do not use this method: it is required only to serialize/deserialize 'errors' key
   */
  @Deprecated
  public void setError(List<Error> errors) {
    setErrors(errors);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Errors other = (Errors) o;

    return Objects.equals(code, other.code) &&
        Objects.equals(errors, other.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, errors);
  }

  @Override
  public String toString() {
    return "Errors{" +
        "code=" + code +
        ", errors=" + errors +
        '}';
  }

  /**
   * Add error to container.
   */
  public Errors add(Error error) {
    this.errors.add(error);
    return this;
  }

  /**
   * Add error to container.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   * @param location
   *          location of the error, can be null
   */
  public Errors add(Object errorKey, String description, String location) {
    return add(new Error(errorKey, description, location));
  }

  /**
   * Add error to container.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public Errors add(Object errorKey, String description) {
    return add(errorKey, description, null);
  }
}
