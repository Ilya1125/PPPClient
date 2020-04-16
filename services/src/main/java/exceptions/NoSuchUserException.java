package exceptions;

public class NoSuchUserException extends NoSuchEntityException {
    public NoSuchUserException(int employeeId) {
        super(employeeId);
    }

    public NoSuchUserException(String s) {
        super(s);
    }
}
