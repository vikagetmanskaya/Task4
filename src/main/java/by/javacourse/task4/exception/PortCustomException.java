package by.javacourse.task4.exception;

public class PortCustomException extends Exception{
    public PortCustomException() {
    }

    public PortCustomException(String message) {
        super(message);
    }

    public PortCustomException(String message, Exception e) {
        super(message, e);
    }

    public PortCustomException(Exception e) {
        super(e);
    }
}
