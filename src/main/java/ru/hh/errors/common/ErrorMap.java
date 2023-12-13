package ru.hh.errors.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Map container for multiple {@link Error} objects mapped by unique keys of entities these errors are associated with.
 * Typical usage is constructing response for request to process multiple entities when failure to process single entity
 * does not cause the whole request to fail:
 * <pre>
 *   ErrorMap errorMap = new ErrorMap(409);
 *   for (Order order : ordersToProcess) {
 *     try {
 *       process(order);
 *     } catch (OrderProcessingException ope) {
 *       errorMap.put(order.getId(), ope.getReason(), ope.getMessage());
 *     }
 *   }
 *   if (errorMap.hasErrors()) {
 *     throw errorMap.toWebApplicationException();
 *   }
 * </pre>
 * Can be serialized to JSON (preferred way for client to relate each error to its source entity)
 * or XML (this works but is not convenient for front-end processing because XML has no native map representation)
 */
@XmlRootElement(name = "errorMap")
public class ErrorMap extends AbstractErrors<Error> {
  protected Map<String, Error> errorMap = new HashMap<>();

  /**
   * @deprecated Do not use this constructor directly. It is used by serialization frameworks
   */
  @Deprecated
  public ErrorMap() {
    super();
  }

  /**
   * Construct empty container.
   *
   * @param statusCode
   *          response HTTP status code
   */
  public ErrorMap(int statusCode) {
    super(statusCode);
  }

  /**
   * Construct empty container.
   *
   * @param statusCode
   *          response HTTP status code
   */
  public ErrorMap(Response.Status statusCode) {
    super(statusCode);
  }

  /**
   * Put error into map
   * @param entityKey
   *          unique entity key (will be converted to String using {@link Object#toString()} method)
   * @param errorKey
   *          error type key (will be converted to String using {@link Object#toString()} method)
   */
  public ErrorMap put(Object entityKey, Object errorKey) {
    return put(entityKey, errorKey, null, null);
  }

  /**
   * Put error into map
   * @param entityKey
   *          unique entity key (will be converted to String using {@link Object#toString()} method)
   * @param errorKey
   *          error type key (will be converted to String using {@link Object#toString()} method)
   * @param description
   *          text description of error for debug purposes, can be null
   */
  public ErrorMap put(Object entityKey, Object errorKey, String description) {
    return put(entityKey, errorKey, description, null);
  }

  /**
   * Put error into map
   * @param entityKey
   *          unique entity key (will be converted to String using {@link Object#toString()} method)
   * @param errorKey
   *          error type key (will be converted to String using {@link Object#toString()} method)
   * @param description
   *          text description of error for debug purposes, can be null
   * @param location
   *          location of the error, can be null
   */
  public ErrorMap put(Object entityKey, Object errorKey, String description, String location) {
    return put(entityKey, new Error(errorKey, description, location));
  }

  /**
   * Put error into map
   * @param entityKey
   *          unique entity key (will be converted to String using {@link Object#toString()} method)
   * @param error
   *          {@link Error} to put into map
   */
  public ErrorMap put(Object entityKey, Error error) {
    this.errorMap.put(entityKey.toString(), error);
    return this;
  }

  @XmlTransient
  @Override
  public Collection<Error> getErrors() {
    return this.errorMap.values();
  }

  @XmlElement(name = "errorMap")
  public Map<String, Error> getErrorMap() {
    return this.errorMap;
  }

  /**
   * Get {@link Error} associated with given entity key (or null if such key is absent)
   *
   * @param entityKey
   *          entity key (will be converted to String using {@link Object#toString()} method)
   */
  public Error get(Object entityKey) {
    return errorMap.get(entityKey.toString());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ErrorMap other = (ErrorMap) o;
    return Objects.equals(code, other.code) &&
        Objects.equals(errorMap, other.errorMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, errorMap);
  }

  @Override
  public String toString() {
    return "Errors{" +
        "code=" + code +
        ", errors=" + errorMap +
        '}';
  }
}
