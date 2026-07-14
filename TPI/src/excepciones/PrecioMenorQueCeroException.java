
package excepciones;


public class PrecioMenorQueCeroException extends RuntimeException{


    public PrecioMenorQueCeroException() {
    }


    public PrecioMenorQueCeroException(String msg) {
        super(msg);
    }
}
