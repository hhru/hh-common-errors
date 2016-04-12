package ru.hh.errors.common;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Container consisting of one or multiple {@link Error} objects.
 *
 * Can be serialized to suitable format by converter that accepts JAXB annotations (i.e. Jackson for JSON).
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public abstract class AbstractErrors<T extends Error> {

  // default constructor for deserialization
  public AbstractErrors() {
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
  public AbstractErrors(int statusCode, Object key, String description) {
    this(statusCode);
    add(key, description);
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
  public AbstractErrors(Status statusCode, Object errorKey, String description) {
    this(statusCode.getStatusCode(), errorKey, description);
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

  protected int code;
  protected List<T> errors;

  @XmlAttribute
  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  @XmlElement(name = "error")
  public List<T> getErrors() {
    return errors;
  }

  public void setErrors(List<T> errors) {
    this.errors = errors;
  }

  protected abstract T createError(Object errorKey, String description);

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
   */
  public T add(Object errorKey, String description) {
    return add(createError(errorKey, description));
  }

  public T add(T error) {
    errors().add(error);
    return error;
  }

  public boolean hasErrors() {
    return errors != null && !errors.isEmpty();
  }

  /**
   * Construct WAE with this container as response entity.
   */
  public WebApplicationException toWebApplicationException() {
    return new WebApplicationException(Response.status(code).entity(this).build());
  }

}

