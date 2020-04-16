package exceptions;

public class DeletingEmployeeException extends InvalidDataException {
    public DeletingEmployeeException(String message) {
        super(message);
    }
}
