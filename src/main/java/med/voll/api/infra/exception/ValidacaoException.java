package med.voll.api.infra.exception;

import org.springframework.validation.Errors;

public class ValidacaoException extends RuntimeException {
    private String fieldName;

    public ValidacaoException(String fieldName, String message) {
    super(message);
    this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}
