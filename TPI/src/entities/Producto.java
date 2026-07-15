<<<<<<< HEAD
/*
CLASE PRODUCTO (Hereda de Base) 
    Atributos 
        - nombre: String 
        - precio: Double 
        - descripción: String 
        - stock: int 
        - imagen: String 
        - disponible: boolean
        - categoría: Categoria 
    Métodos de la consigna
        + getters / setters
        + toString(): String [override] (IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE)
    Métodos agregados    
        + consumirStock()
    Relaciones 
        Relación n…1 con Categoria (RELACION DE AGREGACION)
 */
package entities;


public class Producto extends Base {
    
    //CONTADOR DE PRODUCTOS CREADOS
    private static long contadorProductos = 0;
    
    //ATRIBUTOS
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock = 0;
    private String imagen = "Sin imagen";
    private boolean disponible = false;
    private Categoria categoria;

    //CONSTRUCTOR
    public Producto(String nombre, String descripcion) {
        //LLama al constructor de BASE primero y asigna un id único para ésta 
        //clase al crear el objeto
        super();
        this.setId(++contadorProductos);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public Producto(String nombre, Double precio, String descripcion, Categoria categoria) {
        //LLama al constructor de BASE primero y asigna un id único para ésta 
        //clase al crear el objeto
        this(nombre, descripcion);
        this.precio = precio;
        setCategoria(categoria);
    }
    
        //nombre, descripción, precio, stock, imagen, estado de disponibilidad, categoria
     public Producto(String nombre, Double precio, String descripcion, int stock, String imagen, boolean disponible, Categoria categoria) {

         this(nombre, precio, descripcion, categoria);
         this.stock = stock;
         this.imagen = imagen;
         this.disponible = disponible;
     }
     
    //GETTERS & SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        //Agrega al stock existente
        this.stock += stock;
        this.disponible = true;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        // Si es la misma categoria, no hacer nada
        if (this.categoria == categoria) {
            return;
        }
        // Si tenía una categoría anterior,  se elimina de élla
        if (this.categoria != null && categoria != null) {
            this.categoria.borrarProducto(this);
        }
        // Establecer la nueva categoría
        this.categoria = categoria;
        
        // Añadirse a la lista de la nueva categoría (si no es nulo)
        if (categoria != null && !categoria.getProductos().contains(this)){
            categoria.agregarProducto(this);
        }
    }
    
    //METODO PUBLICO
        //Al agregar una cantidad de producto a un pedido, se descuentan esas
        //unidades al stock del producto y se gestiona si tiene o no stock 
        //suficiente antes de agregarlo.
    public boolean consumirStock(int stock) {
        boolean consumido = false;
        //Si está disponible y tiene suficiente stock
        if (disponible && this.stock >= stock) {
            this.stock -= stock;
            consumido = true;
        }
        //Emite un aviso si el stock del producto llega a cero y lo marca como no disponible.
        if (this.stock == 0) {
            this.disponible = false;
            System.out.println("ATENCION!!! El stock del producto " + nombre + " es cero.");
            System.out.println("");
        }
        //Devuelve un boolean true si pudo consumir la cantidad deseada
        //Devuelve false si la operación falló.
        return consumido;
    }
    
    //IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE
    @Override
    public String toString() {
        //Producto(String nombre, Double precio, String descripcion, int stock, 
        //String imagen, boolean disponible, Categoria categoria)
        String disp = "No Disponible", categoria = "Sin categoría";
        if (this.isDisponible()) {
            disp = "Disponible";
        }
        if (this.categoria != null){
            categoria = this.getCategoria().getNombre();
        }
        return String.format("%04d | %-26.26s | Descripcion: %-26.26s | Precio: %,.2f", 
                this.getId(), this.getNombre(), this.getDescripcion(), this.getPrecio()) + "\n" + 
        String.format("     | Categoría: %-15.15s | Imagen: %-15.15s | %-13.13s | Stock: %,5d", 
                categoria, this.getImagen(), disp, this.getStock());
    }
    
}
=======
/*
CLASE PRODUCTO (Hereda de Base) 
    Atributos 
        - nombre: String 
        - precio: Double 
        - descripción: String 
        - stock: int 
        - imagen: String 
        - disponible: boolean
        - categoría: Categoria 
    Métodos de la consigna
        + getters / setters
        + toString(): String [override] (IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE)
    Métodos agregados    
        + consumirStock()
    Relaciones 
        Relación n…1 con Categoria (RELACION DE AGREGACION)
 */
package entities;


public class Producto extends Base {
    
    //CONTADOR DE PRODUCTOS CREADOS
    private static long contadorProductos = 0;
    
    //ATRIBUTOS
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock = 0;
    private String imagen = "Sin imagen";
    private boolean disponible = false;
    private Categoria categoria;

    //CONSTRUCTOR
    public Producto(String nombre, String descripcion) {
        //LLama al constructor de BASE primero y asigna un id único para ésta 
        //clase al crear el objeto
        super();
        this.setId(++contadorProductos);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public Producto(String nombre, Double precio, String descripcion, Categoria categoria) {
        //LLama al constructor de BASE primero y asigna un id único para ésta 
        //clase al crear el objeto
        this(nombre, descripcion);
        this.precio = precio;
        setCategoria(categoria);
    }
    
        //nombre, descripción, precio, stock, imagen, estado de disponibilidad, categoria
     public Producto(String nombre, Double precio, String descripcion, int stock, String imagen, boolean disponible, Categoria categoria) {

         this(nombre, precio, descripcion, categoria);
         this.stock = stock;
         this.imagen = imagen;
         this.disponible = disponible;
     }
     
    //GETTERS & SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        //Agrega al stock existente
        this.stock += stock;
        this.disponible = true;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        // Si es la misma categoria, no hacer nada
        if (this.categoria == categoria) {
            return;
        }
        // Si tenía una categoría anterior,  se elimina de élla
        if (this.categoria != null && categoria != null) {
            this.categoria.borrarProducto(this);
        }
        // Establecer la nueva categoría
        this.categoria = categoria;
        
        // Añadirse a la lista de la nueva categoría (si no es nulo)
        if (categoria != null && !categoria.getProductos().contains(this)){
            categoria.agregarProducto(this);
        }
    }
    
    //METODO PUBLICO
        //Al agregar una cantidad de producto a un pedido, se descuentan esas
        //unidades al stock del producto y se gestiona si tiene o no stock 
        //suficiente antes de agregarlo.
    public boolean consumirStock(int stock) {
        boolean consumido = false;
        //Si está disponible y tiene suficiente stock
        if (disponible && this.stock >= stock) {
            this.stock -= stock;
            consumido = true;
        }
        //Emite un aviso si el stock del producto llega a cero y lo marca como no disponible.
        if (this.stock == 0) {
            this.disponible = false;
            System.out.println("ATENCION!!! El stock del producto " + nombre + " es cero.");
            System.out.println("");
        }
        //Devuelve un boolean true si pudo consumir la cantidad deseada
        //Devuelve false si la operación falló.
        return consumido;
    }
    
    //IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE
    @Override
    public String toString() {
        //Producto(String nombre, Double precio, String descripcion, int stock, 
        //String imagen, boolean disponible, Categoria categoria)
        String disp = "No Disponible", categoria = "Sin categoría";
        if (this.isDisponible()) {
            disp = "Disponible";
        }
        if (this.categoria != null){
            categoria = this.getCategoria().getNombre();
        }
        return String.format("%04d | %-26.26s | Descripcion: %-26.26s | Precio: %,.2f", 
                this.getId(), this.getNombre(), this.getDescripcion(), this.getPrecio()) + "\n" + 
        String.format("     | Categoría: %-15.15s | Imagen: %-15.15s | %-13.13s | Stock: %,5d", 
                categoria, this.getImagen(), disp, this.getStock());
    }
    
}
>>>>>>> 48128ae6c331506e3cd40b2fafd754ac81f96133
