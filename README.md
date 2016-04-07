# DTOs and factory code for errors in common format.

JAXB-annotated DTOs provide common way of describing response errors.

## Prerequisites

Library expects JAX-RS on the class path in some form. Usually Jersey provides it as required dependency in one of artifacts. Library can work with v1 or later. If your class path does not have it, you can add it manually: 

```
  <dependency>
      <groupId>javax.ws.rs</groupId>
      <artifactId>javax.ws.rs-api</artifactId>
      <version>2.0.1</version>
  </dependency>
```

## Usage

The best way to use the library is through static methods of `ru.hh.errors.common.ErrorsFactory` class. Consider the following examples:

```Java
if (incomingArg == null) {
  throw ErrorsFactory.errorBadRequest("INCOMING_ARG_REQUIRED", "incomingArg must be specified");
}
```

```Java
if (Mode.find(mode) == null) {
  throw ErrorsFactory.error(Status.NOT_IMPLEMENTED, "MODE_NOT_IMPLEMENTED", String.format("Mode %s is not implemented yet", mode));
}
```

```Java
String name = form.getName() == null ? ErrorsFactory.throwBadRequest("NAME_REQUIRED", "Name must be specified") : form.getName();
```

Sometimes multiple errors have to be accumulated:

```Java
Errors errors = new Errors(Status.BAD_REQUEST);
if (arg1 == null) {
  errors.add("ARG1_REQUIRED", "arg1 must be specified");
}
if (arg2 == null) {
  errors.add("ARG2_REQUIRED", "arg2 must be specified");
}
...
if (errors.hasErrors()) {
  throw errors.toException();
}
```

## Extending

Existing DTOs should be enough for most cases, but sometimes additional fields must be present. In that case both DTOs must be extended. `ErrorsFactory` methods that accept existing `Errors` entity can still be used for custom DTOs.

