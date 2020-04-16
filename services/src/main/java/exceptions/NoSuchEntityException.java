package exceptions;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(int entityId) {
        super("Entity with id not found : " + entityId);
    }

    public NoSuchEntityException(String s) {
        super(s);
    }

}
