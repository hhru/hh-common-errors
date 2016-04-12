package ru.hh.errors.common;

import java.util.List;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

  public Errors(int statusCode, Object key, String description) {
    super(statusCode, key, description);
  }

  public Errors(int statusCode) {
    super(statusCode);
  }

  public Errors(Status statusCode, Object errorKey, String description) {
    super(statusCode, errorKey, description);
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

  @Override
  protected Error createError(Object errorKey, String description) {
    return new Error(errorKey, description);
  }

}

