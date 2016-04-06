package ru.hh.errors.common;

import static java.util.Objects.requireNonNull;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "errors")
public class Errors {

  public Errors(int code, Object key, String description) {
    this(code);
    add(key, description);
  }

  public Errors(Status statusCode, Object key, String description) {
    this(requireNonNull(statusCode, "status must be not null").getStatusCode(), key, description);
  }

  public Errors(int code) {
    this.code = code;
    this.errors = new ArrayList<>();
  }

  public Errors(Status statusCode) {
    this(requireNonNull(statusCode, "status must be not null").getStatusCode());
  }

  public Errors() {
  }

  @XmlAttribute
  public int code;

  @XmlElement(name = "error")
  public List<Error> errors;

  public Errors add(Object key, String description) {
    this.errors.add(new Error(key, description));
    return this;
  }

  public WebApplicationException toException() {
    return ErrorsFactory.error(this);
  }

}

