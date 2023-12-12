package ru.hh.errors.common;

import java.util.stream.Stream;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

public class ErrorMapSerializationTest extends SerializationTestBase<ErrorMap> {
  protected ErrorMapSerializationTest() throws JAXBException {
    super(ErrorMap.class);
  }

  static Stream<ErrorMap> serializationArguments() {
    return Stream.of(
        new ErrorMap(Response.Status.BAD_REQUEST),
        new ErrorMap(400).put("entity", "key", "description", "location"),
        new ErrorMap(400)
            .put("e1", "key", "desc")
            .put("e2", "key")
    );
  }
}
