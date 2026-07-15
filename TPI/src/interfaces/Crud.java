<<<<<<< HEAD
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
=======
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
>>>>>>> 48128ae6c331506e3cd40b2fafd754ac81f96133
