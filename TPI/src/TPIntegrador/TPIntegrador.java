/*
Objetivo 
    Implementar en Java el modelo orientado a objetos del sistema Food Store a partir del 
    diagrama UML provisto en el TPIntegrador de la materia, sin utilizar persistencia con JDBC. El foco de 
    esta evaluación es verificar que el alumno domina los conceptos fundamentales de 
    programación Orientada a Objetos (POO): herencia, interfaces, encapsulamiento, 
    polimorfismo y relaciones entre clases. 
Consigna General 
    A partir del diagrama UML del TPIntegrador Food Store, el alumno deberá: 
        • Crear un proyecto Java (Java 11 o superior). 
        • Implementar TODAS las clases, interfaces y enums del UML. 
        • Respetar la jerarquía de herencia, relaciones y métodos definidos. 
        • En el método main(), instanciar los objetos indicados en la sección 4. 
        • Imprimir por consola la información de cada usuario con sus pedidos y el detalle de los 
        mismos. 
IMPORTANTE: No se debe usar ninguna librería externa ni base de datos. Todos los datos se 
            manejan en memoria.
 */

package TPIntegrador;

    import servicios.MenuServicio;


public class TPIntegrador {

    /*
        La clase main TPIntegrador solo accede a la clase de servicio MenuServicio
        y genera una instancia de la misma para utilizar el método mostrarMenu()
    */
    public static void main(String[] args) {
        
        MenuServicio menu = new MenuServicio();
        menu.mostrarMenu();

    }
    
}
