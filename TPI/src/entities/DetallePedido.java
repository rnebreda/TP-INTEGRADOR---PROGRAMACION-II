/*
CLASE DETALLE PEDIDO (Hereda de Base) 
    Atributos 
        - cantidad: int 
        - subtotal: Double 
        - producto: Producto 
    Métodos 
        + getters / setters 
        - calcularSubtotal(): Double 
        + toString(): String [override] 
    Relaciones 
        Relación n…1 con Pedido (RELACION DE COMPOSICION)
        Relación n…1 con Producto (RELACION DE ASOCIACION)
 */
package entities;


public class DetallePedido extends Base {
    
    
    //ATRIBUTOS
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    //CONSTRUCTOR
    public DetallePedido(Long id, int cantidad, Producto producto) {
        //LLama al constructor de BASE primero y asigna un id único para ésta 
        //clase al crear el objeto
        super();
        this.setId(id);
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }
    
    //GETTERS & SETTERS
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }
 
    //IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE
    @Override
    public String toString() {
        
        return "DETALLE: " + String.format("%04d", getId()) +
                ", Producto: " + producto.getNombre() + String.format(", Cantidad: %2d", cantidad) + "\n" +
                "Subtotal: $" + String.format("%,.2f", subtotal);

    }
    
    //METODO PRIVADO
    private Double calcularSubtotal(){
        
        if (this.producto != null) {
            return cantidad * producto.getPrecio();
        } else {
            return 0.0;
        }
        
    }
    
    
}
