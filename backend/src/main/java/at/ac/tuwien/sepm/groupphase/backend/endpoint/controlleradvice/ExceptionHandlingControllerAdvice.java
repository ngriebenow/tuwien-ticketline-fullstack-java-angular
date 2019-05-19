package at.ac.tuwien.sepm.groupphase.backend.endpoint.controlleradvice;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

  private static final String DEFAULT_CONSTRAINT_VIOLATION_MESSAGE = "Couldn't validate reqeust";

  /** Turn failed validation, eg from `@Valid Entity entity` into 400 Responses. */
  @ExceptionHandler(ConstraintViolationException.class)
  public void handleConstraintViolationException(
      ConstraintViolationException exc, HttpServletResponse response) throws IOException {
    String message = "";
    for (ConstraintViolation<?> violation : exc.getConstraintViolations()) {
      String fieldName = getFieldName(violation.getPropertyPath().toString());
      message = String.format("%s: %s", fieldName, violation.getMessage());
    }
    if (message.length() <= 0) {
      message = DEFAULT_CONSTRAINT_VIOLATION_MESSAGE;
    }
    response.sendError(HttpStatus.BAD_REQUEST.value(), message);
  }

  /** Strips the mehtod name and argument position from the properyPath if present. */
  private static String getFieldName(String propertyPath) {
    int pathPrefixLength = 2;
    String fieldName = propertyPath;
    String[] tokens = propertyPath.split("\\.");
    if (tokens.length > pathPrefixLength) {
      fieldName = String.join("", Arrays.copyOfRange(tokens, pathPrefixLength, tokens.length));
    }
    return fieldName;
  }
}
