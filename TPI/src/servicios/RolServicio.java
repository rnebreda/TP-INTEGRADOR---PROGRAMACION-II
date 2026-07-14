/*
SERVICIOS PARA LA CLASE ENUM ROL (MENU)

    //ATRIBUTOS DE ROL
        ADMIN, USUARIO;

 */
package servicios;

import enums.Rol;
import interfaces.Menu;
import java.util.Scanner;


public class RolServicio implements Menu{
    
    private String[] opcionesMenu = {
        "ROLES",
        "ADMIN",
        "USUARIO"};

    
    public Rol asignarRol(Scanner leer){
        Rol[] roles = Rol.values();
        int indice = getOpcion(opcionesMenu);
        if ( 0 < indice  && indice <= opcionesMenu.length) {
            Rol nuevo = roles[indice-1];
            return nuevo;
        }
        return null;

    }
}
