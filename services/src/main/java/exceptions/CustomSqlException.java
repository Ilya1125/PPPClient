package exceptions;

public class CustomSqlException extends RuntimeException {

    public CustomSqlException(String s) {
        super(s);
    }
}
