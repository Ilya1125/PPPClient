package exceptions;

public class LoginInUseException extends InvalidDataException {
    public LoginInUseException(String message) {
        super(message);
    }
}
