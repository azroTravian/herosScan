package scanheros.exception;

public class MissingCredentialsException extends Exception {

    public MissingCredentialsException() {
        super("Les informations de connexions n'ont pas été trouvées");

    }

    public MissingCredentialsException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public MissingCredentialsException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public MissingCredentialsException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
