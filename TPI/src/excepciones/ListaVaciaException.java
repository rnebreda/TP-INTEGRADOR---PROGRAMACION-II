
package excepciones;


public class ListaVaciaException extends RuntimeException{


    public ListaVaciaException() {
    }


    public ListaVaciaException(String msg) {
        super(msg);
    }
}
