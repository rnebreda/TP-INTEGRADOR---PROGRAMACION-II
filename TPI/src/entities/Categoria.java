/*
CLASE CATEGORIA (Hereda de Base) 
    Atributos 
        - nombre: String 
        - descripción: String 
        - productos: List<Producto> 
    Métodos de la consigna
        + getters / setters 
        + agregarProducto(Producto): void
        + toString(): String [override] (IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE)
    Métodos adicionales
        + borrarProducto (Producto): void 
        + vaciarListaProductos(): void

    Relaciones 
        Relación 1…n con Producto (RELACION DE AGREGACION)
 */
package entities;

    import java.util.ArrayList;
    import java.util.List;

public class Categoria extends Base {
    
    //CONTADOR DE CATEGORIAS
    private static long contadorCategoria = 0;
    
    //ATRIBUTOS
    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList <>();

    //CONSTRUCTOR
    public Categoria(String nombre, String descripcion) {
        //LLama al constructor de BASE primero y asigna un id único para ésta 
        //clase al crear el objeto
        super();
        this.setId(++contadorCategoria);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //GETTERS & SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    //METODOS PUBLICOS
    public void agregarProducto (Producto p){
        
        if (p != null && !productos.contains(p)) {
            productos.add(p);
        }
        if (p.getCategoria()!= this) {
            p.setCategoria(this);
        }
        
    }
        //Borra un producto de la lista de productos de la categoría
        //La categoría del producto queda con valor null y el producto queda
        //No disponible hasta que se le asigne otra categoría.
    public void borrarProducto (Producto p){
        
        if (p != null && productos.contains(p)) { 
            p.setCategoria(null);
            p.setDisponible(false);
            productos.remove(p);
        }

    }
    
        //Vacía la lista de productos de la categoría (antes de eliminarla
        //Deja en todos los productos el valor de categoría en null y No disponible
        //hasta que se le asigne otra categoría
    public void vaciarListaProductos(){
        
        for (Producto p : new ArrayList<>(productos)) {
            borrarProducto(p);
        }
    }
    
    //IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE
    @Override
    public String toString() {
    return String.format("%04d | Nombre: %-18.18s | Descripcion: %-26.26s",
           this.getId(), this.getNombre(), this.getDescripcion());
    }
    
    
}
