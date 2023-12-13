package ru.hh.errors.common;


import java.util.stream.Stream;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

public class ErrorsSerializationTest extends SerializationTestBase<Errors> {

  public ErrorsSerializationTest() throws JAXBException {
    super(Errors.class);
  }

  static Stream<Errors> serializationArguments() {
    return Stream.of(
        new Errors(Response.Status.BAD_REQUEST),
        new Errors(400, "key", "description", "location"),
        new Errors(400)
            .add("key1", "desc1", "loc1")
            .add("key2", "desc2")
    );
  }
}
