package ao.it.chandsoft.world.handler;

import ao.it.chandsoft.world.domain.error.ErroResponse;
import ao.it.chandsoft.world.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class WorldControllerHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity fieldErrorNotValid(ResourceNotFoundException exception) {
        return ResponseEntity.badRequest()
                .body(ErroResponse.builder()
                        .codigoHttp(404)
                        .titulo("Resource not found")
                        .detelhes(exception.getMessage())
                        .build()
                );

    }
}
