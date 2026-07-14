/*
    INTERFACE GENERICA CRUD
        Métodos 
        + agregar(T): void
        + buscar(Long): T
        + listar(): void
        + eliminar(Long): void
        + modificar(T): void
        + getId(T): Long
 */
package interfaces;


public interface Crud<T>{
    
    void agregar(T entidad);
    T buscar(Long id);
    void listar();
    void eliminar(Long id);
    void modificar(T entidad);
    Long getId(T entidad);
}
