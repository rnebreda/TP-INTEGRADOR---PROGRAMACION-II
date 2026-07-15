<<<<<<< HEAD
/*
INTERFACE MENU
    Posee métodos a ser implementados en clases que utilicen un menú de opciones.
    Estos métodos son default.

    Métodos:
        + mostrarMenu(String[]): void
        + ejecutarOpcion(int): void
        + getOpcion(String[]): int

 */
package interfaces;

import java.util.InputMismatchException;
import java.util.Scanner;


public interface Menu {
    
    Scanner leer = new Scanner(System.in);
    
    default void mostrarMenu(String[] opciones){   
        int opcion = -1;
    
        do{
            opcion = getOpcion(opciones);
            ejecutarOpcion(opcion);
            
        } while (opcion != 0);
        
    }
    
    
    
    default void ejecutarOpcion(int opcion){ // ejecuta acciones según la opción
        
    }
    
    default int getOpcion (String[] opciones){
        int opcion = -1;
        boolean elegidaCorrecta = false;
        do{
            System.out.println(String.format("%40s", "").replace(' ', '='));
            System.out.println(opciones[0]);
            System.out.println(String.format("%20s", "").replace(' ', '='));
        
            for (int i = 1; i < opciones.length; i++) {
                System.out.println((i) + ". " + opciones[i]);
            }
            System.out.println("0. Salir");
            System.out.println(String.format("%40s", "").replace(' ', '='));
            System.out.print("Elija una opción: ");
            try{
                opcion = leer.nextInt();
                elegidaCorrecta = true;
            }catch(InputMismatchException e){
                System.out.println("El valor ingresado no es un entero. ");
            }
            finally{
                leer.nextLine(); // limpiar buffer
            }
            
        }while(!elegidaCorrecta);
        System.out.println();
        return opcion;    
        
    }
}
=======
/*
INTERFACE MENU
    Posee métodos a ser implementados en clases que utilicen un menú de opciones.
    Estos métodos son default.

    Métodos:
        + mostrarMenu(String[]): void
        + ejecutarOpcion(int): void
        + getOpcion(String[]): int

 */
package interfaces;

import java.util.InputMismatchException;
import java.util.Scanner;


public interface Menu {
    
    Scanner leer = new Scanner(System.in);
    
    default void mostrarMenu(String[] opciones){   
        int opcion = -1;
    
        do{
            opcion = getOpcion(opciones);
            ejecutarOpcion(opcion);
            
        } while (opcion != 0);
        
    }
    
    
    
    default void ejecutarOpcion(int opcion){ // ejecuta acciones según la opción
        
    }
    
    default int getOpcion (String[] opciones){
        int opcion = -1;
        boolean elegidaCorrecta = false;
        do{
            System.out.println(String.format("%40s", "").replace(' ', '='));
            System.out.println(opciones[0]);
            System.out.println(String.format("%20s", "").replace(' ', '='));
        
            for (int i = 1; i < opciones.length; i++) {
                System.out.println((i) + ". " + opciones[i]);
            }
            System.out.println("0. Salir");
            System.out.println(String.format("%40s", "").replace(' ', '='));
            System.out.print("Elija una opción: ");
            try{
                opcion = leer.nextInt();
                elegidaCorrecta = true;
            }catch(InputMismatchException e){
                System.out.println("El valor ingresado no es un entero. ");
            }
            finally{
                leer.nextLine(); // limpiar buffer
            }
            
        }while(!elegidaCorrecta);
        System.out.println();
        return opcion;    
        
    }
}
>>>>>>> 48128ae6c331506e3cd40b2fafd754ac81f96133
