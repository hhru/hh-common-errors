package ru.hh.errors.common;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * Container consisting of one or multiple {@link Error} objects.
 *
 * Can be serialized to suitable format by converter that accepts JAXB annotations (i.e. Jackson for JSON).
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public abstract class AbstractErrors<T extends Error> {

  protected int code;
  protected List<T> errors;

  // default constructor for deserialization
  public AbstractErrors() {
  }

  /**
   * Construct container with single error.
   *
   * @param statusCode
   *          response HTTP status code
   * @param key
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   * @param location
   *          location of the error, can be null
   */
  public AbstractErrors(int statusCode, Object key, String description, String location) {
    this(statusCode);
    add(key, description, location);
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
  public AbstractErrors(Status statusCode, Object errorKey, String description, String location) {
    this(statusCode.getStatusCode(), errorKey, description, location);
  }

  /**
   * Construct empty container.
   *
   * @param statusCode
   *          response HTTP status code
   */
  public AbstractErrors(int statusCode) {
    this.code = statusCode;
  }

  /**
   * Construct empty container.
   *
   * @param statusCode
   *          response HTTP status code
   */
  public AbstractErrors(Status statusCode) {
    this(statusCode.getStatusCode());
  }

  @XmlAttribute
  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public List<T> getErrors() {
    return errors;
  }

  public void setErrors(List<T> errors) {
    this.errors = errors;
  }

  protected abstract T createError(Object errorKey, String description, String location);

  private List<T> errors() {
    if (this.errors == null) {
      this.errors = new ArrayList<>();
    }
    return errors;
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
  public AbstractErrors<T> add(Object errorKey, String description, String location) {
    return add(createError(errorKey, description, location));
  }

  /**
   * Add error to container.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public AbstractErrors<T> add(Object errorKey, String description) {
    return add(errorKey, description, null);
  }

  public AbstractErrors<T> add(T error) {
    errors().add(error);
    return this;
  }

  public boolean hasErrors() {
    return errors != null && !errors.isEmpty();
  }

  /**
   * Construct WAE with this container as response entity.
   */
  public WebApplicationException toWebApplicationException() {
    return new WebApplicationException(toResponse());
  }

  public Response toResponse() {
    return Response.status(code).entity(this).build();
  }

}
