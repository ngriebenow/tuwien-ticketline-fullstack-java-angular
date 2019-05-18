package at.ac.tuwien.sepm.groupphase.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateEntry extends RuntimeException {
  public DuplicateEntry() {

  }

  public DuplicateEntry(String message) {
    super(message);
  }
}
