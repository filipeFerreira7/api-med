package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratamentoDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity exceptionNotFound(){
        return ResponseEntity.notFound().build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),HttpStatus.BAD_REQUEST);
        }

        private Map<String, List<String>> getErrorsMap(List<String> errors) {
            Map<String, List<String>> errorsResponse = new HashMap<>();
            errorsResponse.put("errors", errors);
            return errorsResponse;
        }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity businessRules (ValidacaoException ex){
        var error = new ValidacaoException(ex.getFieldName(), ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity invalidToken(TokenInvalidoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
