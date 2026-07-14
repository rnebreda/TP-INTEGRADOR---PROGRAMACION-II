
package excepciones;


public class EstadoNoModificableException extends RuntimeException{


    public EstadoNoModificableException() {
    }


    public EstadoNoModificableException(String msg) {
        super(msg);
    }
}
