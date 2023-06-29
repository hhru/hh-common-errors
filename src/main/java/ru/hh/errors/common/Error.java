package ru.hh.errors.common;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

@XmlRootElement(name = "error")
public class Error {

  /**
   * Construct error object.
   *
   * @param key
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public Error(Object key, String description) {
    requireNonNull(key, "key must be not null");
    this.key = key.toString();
    this.description = description;
  }

  /**
   * Construct error object.
   *
   * @param key
   *          key will be converted to string by {@link Object#toString()} method
   * @param description
   *          text description of error for debug purposes, can be null
   * @param location
   *          location of the error, can be null
   */
  public Error(Object key, String description, String location) {
    this(key, description);
    this.location = location;
  }

  // default constructor for deserialization
  public Error() {
  }

  @XmlElement
  public String key;

  @XmlElement
  public String description;

  @XmlElement
  public String location;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Error other = (Error) o;

    return Objects.equals(key, other.key) &&
        Objects.equals(location, other.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, location);
  }

  @Override
  public String toString() {
    return "Error{" +
        "key='" + key + '\'' +
        ", description='" + description + '\'' +
        ", location='" + location + '\'' +
        '}';
  }
}
