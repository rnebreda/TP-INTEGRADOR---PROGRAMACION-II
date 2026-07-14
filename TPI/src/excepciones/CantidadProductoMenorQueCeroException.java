/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package excepciones;

/**
 *
 * @author Usuario
 */
public class CantidadProductoMenorQueCeroException extends RuntimeException{


    public CantidadProductoMenorQueCeroException() {
    }


    public CantidadProductoMenorQueCeroException(String msg) {
        super(msg);
    }
}
