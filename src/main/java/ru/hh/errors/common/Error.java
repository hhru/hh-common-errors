package ru.hh.errors.common;

import static java.util.Objects.requireNonNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Error {

  public Error(Object key, String description) {
    requireNonNull(key, "key must be not null");
    this.key = key.toString();
    this.description = description;
  }

  public Error() {
  }

  @XmlElement
  public String key;

  @XmlElement
  public String description;

}
