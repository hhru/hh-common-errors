package ru.hh.errors.common;

import javax.ws.rs.core.Response.Status;

public abstract class ErrorUtils {

  public static void checkTrue(boolean result, Status status, String key, String description) {
    if (!result) {
      throw new Errors(status, key, description).toWebApplicationException();
    }
  }
}
