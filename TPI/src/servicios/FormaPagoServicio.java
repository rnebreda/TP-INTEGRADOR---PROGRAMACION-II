/*
SERVICIOS PARA LA CLASE ENUM FORMAPAGO (MENU)

    //ATRIBUTOS DE FORMAPAGO
        TARJETA ("TC"), TRANSFERENCIA ("TR"), EFECTIVO ("EF");
        private String abreviatura;

    //CONSTRUCTOR
        FormaPago(String abreviatura);

 */
package servicios;

    import enums.FormaPago;
    import interfaces.Menu;
    import java.util.Scanner;

public class FormaPagoServicio implements Menu{
    
    private String[] opcionesMenu = {
            "FORMA DE PAGO",
            "TARJETA (TC)",
            "TRANSFERENCIA (TR)",
            "EFECTIVO (EF)"};
    
     
    public FormaPago asignarFormaPago(Scanner leer){
        FormaPago[] fp = FormaPago.values();
        int indice = getOpcion(opcionesMenu);
        if ( 0 < indice  && indice <= opcionesMenu.length) {
            FormaPago nuevo = fp[indice-1];
            return nuevo;
        }
        return null;
    }

}