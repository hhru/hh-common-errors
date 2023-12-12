package ru.hh.errors.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.function.Function;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public abstract class SerializationTestBase<T> {
  protected final Class<T> objectClass;
  private final JAXBContext jaxbContext;
  private final ObjectMapper objectMapper;

  protected SerializationTestBase(Class<T> objectClass) throws JAXBException {
    this.objectClass = objectClass;
    jaxbContext = JAXBContext.newInstance(objectClass);
    objectMapper = new ObjectMapper().registerModule(new JaxbAnnotationModule());
  }

  @ParameterizedTest
  @MethodSource("serializationArguments")
  void jacksonJsonJackson(T object) {
    checkSerializationDeserialization(object, this::jacksonJsonSerialize, this::jacksonJsonDeserialize);
  }

  @ParameterizedTest
  @MethodSource("serializationArguments")
  void jaxbXmlJaxb(T object) {
    checkSerializationDeserialization(object, this::jaxbXmlSerialize, this::jaxbXmlDeserialize);
  }

  void checkSerializationDeserialization(T object, Function<T, String> serializer, Function<String, T> deserializer) {
    String serialized = serializer.apply(object);
    System.out.print("Serialized: ");
    System.out.println(serialized);
    T deserialized = deserializer.apply(serialized);
    assertEquals(object, deserialized);
  }

  String jaxbXmlSerialize(T object) {
    StringWriter writer = new StringWriter();
    try {
      jaxbContext.createMarshaller().marshal(object, writer);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
    return writer.toString();
  }

  T jaxbXmlDeserialize(String xml) {
    StringReader reader = new StringReader(xml);
    try {
      //noinspection unchecked
      return (T) jaxbContext.createUnmarshaller().unmarshal(reader);
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  String jacksonJsonSerialize(T object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  T jacksonJsonDeserialize(String json) {
    try {
      return objectMapper.readValue(json, objectClass);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
