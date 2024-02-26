package ua.sirkostya009.evchargervalidator.station;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Controller {
    @PostMapping("/api/validate")
    public Map<String, ?> validate(@Valid @RequestBody Station $) {
        return Map.of("valid", true);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, ?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Map.of(
                "valid", false,
                "errorCount", ex.getErrorCount(),
                "errors", ex.getFieldErrors().stream()
                        .map(e -> Map.of(
                                "objectName", e.getObjectName(),
                                "field", e.getField(),
                                "message", e.getDefaultMessage()
                        ))
                        .toList()
        );
    }
}
