/*
CLASE PEDIDO (Hereda de Base) 
    Atributos 
        - fecha: LocalDate 
        - estado: Estado - total: Double 
        - formaPago: FormaPago 
        - detalles: List<DetallePedido> 
        - usuario: Usuario 
    Métodos de la consigna
        + getters / setters 
        + calcularTotal(): void (IMPLEMENTA EL MÉTODO ABSTRACTO DE CALCULABLE)
        + addDetallePedido(int, Producto): void 
        + findDetallePedidoByProducto(Producto): DetallePedido 
        + deleteDetallePedidoByProducto(Producto): void 
        + toString(): String [override] (IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE)
    Métodos agregados:
        + updateDetallePedidoByProducto(int , Producto): void
        + vaciarListaDetalles(): void
    Relaciones 
        Relación 1…n con DetallePedido (RELACION DE COMPOSICION)
        Relación n…1 con Usuario (RELACION BIDIRECCIONAL)
 */
package entities;

    import enums.Estado;
    import enums.FormaPago;
    import interfaces.Calculable;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;
import java.util.Objects;

public class Pedido extends Base implements Calculable{
    
    //CONTADOR DE PEDIDOS CREADOS
    private static long contadorPedidos = 0;
    
    //ATRIBUTOS
    private LocalDate fecha;
    private Estado estado = Estado.PENDIENTE;
    private Double total = 0.0;
    private FormaPago formaPago;
    private List<DetallePedido> detalle = new ArrayList<> ();
    private Usuario usuario;
    
    //CONTADOR DE DETALLES CREADOS EN CADA PEDIDO
    private long contadorDetalles = 0;

    //CONSTRUCTOR
    public Pedido(FormaPago formaPago, Usuario usuario) {
        //LLama al constructor de BASE primero y asigna un id único para ésta 
        //clase al crear el objeto
        super();
        this.setId(++contadorPedidos);
        //Toma la fecha del atributo createdAt de la clase Base
        this.fecha = super.getCreatedAt().toLocalDate();
        this.formaPago = formaPago;
        setUsuario(usuario);
    }

    //GETTERS & SETTERS
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public List<DetallePedido> getDetalle() {
        return detalle;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        // Si es ei mismo usuario, no hacer nada
        if (this.usuario == usuario) {
            return;
        }
        // Si tenía un usuario anterior,  se elimina de él la lista de pedidos el
        if (this.usuario != null) {
            this.usuario.borrarPedido(this);
        }
        // Establecer el nuevo usuario
        this.usuario = usuario;
        
        // Añadirse a la lista de pedidos del nuevo usuario (si no es nulo)
        if (usuario != null && !usuario.getPedidos().contains(this)){
            usuario.agregarPedido(this);
        }
    }
    
    //IMPLEMENTA EL MÉTODO ABSTRACTO DE LA INTERFACE CALCULABLE
    @Override
    public void calcularTotal() {
        total = 0.0;
        for (DetallePedido d : detalle) {
            if (!d.isEliminado()) {
                total += d.getSubtotal();
            }
            
            
        }
        
    }
    
    
    //METODOS PUBLICOS
    
    public void addDetallePedido(int cantidad, Producto producto){
        if (producto == null) {
            System.out.println("La operación falló. Producto nulo");
            return;
        }
        DetallePedido detP = findDetallePedidoByProducto(producto);
        boolean productoYaExisteEnPedido = detP != null;
        if (!productoYaExisteEnPedido && producto.consumirStock(cantidad)) {
            //Build Id detalles: numero de pedido (x 10000) + contador de detalles
            Long id = this.getId()*10000 + ++contadorDetalles;
            DetallePedido d = new DetallePedido(id, cantidad, producto);
            detalle.add(d);
            System.out.println("Detalle creado con éxito");
        }else{
            System.out.print("La operación falló. ");
            if (productoYaExisteEnPedido) {
                System.out.println("El producto " + producto.getNombre() 
                + " ya existe en el pedido.");
            }else{
                System.out.println("Stock insuficiente.");
            }
        }
        calcularTotal();//SOLICITADO EN LA CONSIGNA
        
    }
    
    
    public DetallePedido findDetallePedidoByProducto(Producto producto){
        if (producto == null) {   
            return null;
        }
        DetallePedido encontrado = null;
        for (DetallePedido d : detalle) {
            if (!d.isEliminado() && Objects.equals(d.getProducto().getId(), producto.getId())) {
                encontrado = d;
                break;
            }
        }
        return encontrado;
        
    }
    
    
    public void deleteDetallePedidoByProducto(Producto producto){
        if (producto == null) {
            System.out.println("La operación falló. Producto nulo");
            return;
        }
        DetallePedido d = findDetallePedidoByProducto(producto);
        if (d != null) {
            //Devueldve al stock del producto la cantidad de elementos del 
            //detalle eliminado para mantener su coherencia.
            producto.setStock(d.getCantidad());
            //detalle.remove(d);
            d.setEliminado(true);
            System.out.println("Detalle eliminado con éxito");
        }else{
            System.out.println("La operación falló. El producto " + producto.getNombre() 
            + " no existe en el pedido.");
        }
        calcularTotal();//SOLICITADO EN LA CONSIGNA
        
    }
    
    
            //Modifica la cantidad de producto en un detalle. Considera el stock 
            //del producto y la cantidad en el detalle para calcular el disponible
            //El stock del producto, el subtotal del detalle y el total del 
            //pedido quedan actualizados luego de modificar
        public void updateDetallePedidoByProducto(int cantidad, Producto producto){
        if (producto == null) {
            System.out.println("La operación falló. Producto nulo");
            return;
        }
        DetallePedido d = findDetallePedidoByProducto(producto);
        if (d != null) {
            //Primero verifica que el stock del producto + la cantidad del detalle 
            //superen a la cantidad solicitada. 
            //Luego modifica cantidad si no excede el stock disponible del producto

            if (producto.consumirStock(cantidad - d.getCantidad())){
                producto.setStock(d.getCantidad());
                d.setCantidad(cantidad);
                System.out.println("Detalle modificado con éxito");
            }else{
                System.out.println("Stock insuficiente.");
            }

        }else{
            System.out.println("La operación falló. El producto " + producto.getNombre() 
            + " no existe en el pedido.");
        }
        calcularTotal();//SOLICITADO EN LA CONSIGNA
        
    }
        
        
        //Elimina todos los detalles de un pedido, devolviendo al stock de cada 
        //producto la cantidad correspondiente (previo a borrar el pedido).
    public void vaciarListaDetalles (){
        
        for (DetallePedido d : new ArrayList<>(detalle)) {
            Producto pr = d.getProducto();
            if (!d.isEliminado())
                deleteDetallePedidoByProducto(pr);

        }
    }

    //IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE
    @Override
    public String toString() {
        
        // Define el formato deseado de fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        return String.format("Pedido: %04d", this.getId()) + "\n"
               + "Usuario: " + usuario.getNombre() + " " + usuario.getApellido() + "\n"
               + "Fecha: " + fecha.format(formato) + "\n" + "Estado: " + estado + "\n"
               + "Forma de Pago: " + formaPago + "\n"
               + "TOTAL PEDIDO: $" + String.format("%,.2f", total) + "\n" + "";

    }
   
    
}
