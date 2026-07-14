
package excepciones;


public class FormatoInvalidoException extends RuntimeException{


    public FormatoInvalidoException() {
    }


    public FormatoInvalidoException(String msg) {
        super(msg);
    }
}
