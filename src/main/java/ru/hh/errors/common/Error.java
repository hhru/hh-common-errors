package ru.hh.errors.common;

import static java.util.Objects.requireNonNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class Error {

  /**
   * Construct error object.
   *
   * @param errorKey
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public Error(Object key, String description) {
    requireNonNull(key, "key must be not null");
    this.key = key.toString();
    this.description = description;
  }

  // default constructor for deserialization
  public Error() {
  }

  @XmlElement
  public String key;

  @XmlElement
  public String description;

}
