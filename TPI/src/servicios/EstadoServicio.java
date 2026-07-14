/*
SERVICIOS PARA LA CLASE ENUM ESTADO (MENU)

    //ATRIBUTOS DE ESTADO
        PENDIENTE, CONFIRMADO, TERMINADO, CANCELADO;

 */
package servicios;

    import enums.Estado;
    import interfaces.Menu;
    import java.util.Scanner;

public class EstadoServicio implements Menu{
    
    private String[] opcionesMenu = {
        "ESTADO DE PEDIDO",
        "PENDIENTE",
        "CONFIRMADO",
        "TERMINADO",
        "CANCELADO"};

    
    public Estado asignarEstado(Scanner leer){
        Estado[] estados = Estado.values();
        int indice = getOpcion(opcionesMenu);
        if ( 0 < indice  && indice <= opcionesMenu.length) {
            Estado nuevo = estados[indice-1];
            return nuevo;
        }
        return null;

    }
    
}
