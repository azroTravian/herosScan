package scanheros.exception;

public class GenderNotFindException extends Exception {

    public GenderNotFindException() {
        super("Le genre du héros ne peut pas être déterminé");

    }

    public GenderNotFindException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public GenderNotFindException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public GenderNotFindException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
