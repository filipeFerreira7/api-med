package med.voll.api.infra.exception;

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
