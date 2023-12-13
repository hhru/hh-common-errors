package ru.hh.errors.common;

import java.util.Collection;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Container consisting of one or multiple {@link Error} objects.
 * <p/>
 * Can be serialized to suitable format by converter that accepts JAXB annotations (i.e. Jackson for JSON).
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public abstract class AbstractErrors<T extends Error> {

  protected int code;

  // default constructor for deserialization
  public AbstractErrors() {
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

  public abstract Collection<T> getErrors();

  public boolean hasErrors() {
    return getErrors() != null && !getErrors().isEmpty();
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
