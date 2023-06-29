package ru.hh.errors.common;

import jakarta.ws.rs.core.Response.Status;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Container consisting of one or multiple {@link Error} objects.
 *
 * Can be serialized to suitable format by converter that accepts JAXB annotations (i.e. Jackson for JSON).
 */
@XmlRootElement(name = "errors")
public class Errors extends AbstractErrors<Error> {

  public Errors() {
    super();
  }

  public Errors(int statusCode, Object key, String description, String location) {
    super(statusCode, key, description, location);
  }

  public Errors(int statusCode, Object key, String description) {
    super(statusCode, key, description, null);
  }

  public Errors(int statusCode) {
    super(statusCode);
  }

  public Errors(Status statusCode, Object errorKey, String description, String location) {
    super(statusCode, errorKey, description, location);
  }

  public Errors(Status statusCode, Object errorKey, String description) {
    super(statusCode, errorKey, description, null);
  }

  public Errors(Status statusCode) {
    super(statusCode);
  }

  // override for JAXB

  @Override
  @XmlElement(name = "error")
  public List<Error> getErrors() {
    return super.getErrors();
  }

  @Override
  public void setErrors(List<Error> errors) {
    super.setErrors(errors);
  }

  @XmlElement(name = "errors")
  @Deprecated // do not use this method: it is required only to serialize/deserialize 'errors' key
  public List<Error> getError() {
    return super.getErrors();
  }

  @Deprecated // do not use this method: it is required only to serialize/deserialize 'errors' key
  public void setError(List<Error> errors) {
    super.setErrors(errors);
  }

  @Override
  protected Error createError(Object errorKey, String description, String location) {
    return new Error(errorKey, description, location);
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
}
