package electric.vehicle.charging.station.management.exception;

import electric.vehicle.charging.station.management.dto.ErrorInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorInfoDTO> handleValidationException(BadRequestException exception) {
        log.error("Bad exception occurred with errors: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorInfoDTO(exception.getCode(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ElementWithSameIDAlreadyExistsException.class)
    public ResponseEntity<ErrorInfoDTO> handleTopUpException(ElementWithSameIDAlreadyExistsException exception) {
        log.error("ElementWithSameIDAlreadyExists exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ErrorInfoDTO(exception.getCode(), exception.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ErrorInfoDTO> handleNotFoundException(ElementNotFoundException exception) {
        log.error("NotFound exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ErrorInfoDTO(exception.getCode(), exception.getMsg()), HttpStatus.NOT_FOUND);
    }
}
