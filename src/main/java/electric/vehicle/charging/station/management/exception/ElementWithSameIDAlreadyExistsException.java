package electric.vehicle.charging.station.management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ElementWithSameIDAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 7246983447306271525L;

  private int code;
  private String msg;
}
