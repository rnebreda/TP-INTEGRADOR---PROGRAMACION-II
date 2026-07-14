/*
CLASE USUARIO (Hereda de Base) 
    Atributos 
        - nombre: String 
        - apellido: String 
        - mail: String 
        - celular: String 
        - contrasenia: String 
        - rol: Rol 
        - pedidos: List<Pedido> 
    Métodos de la consigna
        + getters / setters 
        + agregarPedido(Pedido): void
        + toString(): String [override] (IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE)
    Métodos Agregados    
        + borrarPedido(Pedido): void
        + vaciarListaPedidos(): void
        + imprimirReporte(): void


    Relaciones 
        Relación 1…n con Pedido (RELACION BIDIRECCIONAL)
 */
package entities;

    import enums.Estado;
    import enums.Rol;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;

public class Usuario extends Base {
    
    //CONTADOR DE USUARIOS CREADOS
    private static long contadorUsuarios = 0;
    
    //ATRIBUTOS
    private String nombre;
    private String apellido;
    private String mail = "no posee";
    private String celular = "no posee";
    private String contrasenia = "no posee";
    private Rol rol;
    private List<Pedido> pedidos = new ArrayList<> ();
    
    //CONSTRUCTOR
    public Usuario(String nombre, String apellido, Rol rol) {
        //LLama al constructor de BASE primero y asigna un id único para ésta 
        //clase al crear el objeto
        super();
        this.setId(++contadorUsuarios);
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
    }
    
    public Usuario(String nombre, String apellido, Rol rol, String mail, String celular) {
        this(nombre, apellido, rol);
        this.mail = mail;
        this.celular = celular;
    }

    //GETTERS& SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) { 
        //Si es null indica el texto "no posee"
        if (mail != null) {
            this.mail = mail;
        }
        
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        //Si es null indica el texto "no posee"
        if (celular != null) {
            this.celular = celular;
        }
        
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        //Si es null indica el texto "no posee"
        if (contrasenia != null) {
            this.contrasenia = contrasenia;
        }
        
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    
    //METODOS PUBLICOS
    public void agregarPedido(Pedido p){
        
        if (p != null && !pedidos.contains(p)) {
            pedidos.add(p);
        }
        if (p.getUsuario()!= this) {
            p.setUsuario(this);
        }
        
    }
    
    
        //Borra un pedido de la lista pedidos (no modifica el valor de Usuario del pedido)
    public void borrarPedido(Pedido p){
        
        if (p != null && pedidos.contains(p)) {
            if (p.getEstado().equals(Estado.PENDIENTE)) {
                p.vaciarListaDetalles();
                p.setEliminado(true);
            }else{
                if (p.getEstado().equals(Estado.CONFIRMADO)) {
                    p.setEstado(Estado.TERMINADO);
                }
            }
            pedidos.remove(p);
        }
        
    }
    
            
        //Vacía la lista de pedidos. (Previo a eliminar usuario)
    public void vaciarListaPedidos(){
        for (Pedido p : new ArrayList<>(pedidos)) {
            borrarPedido(p);
        }
    }
    
    
        //Imprime todos los pedidos del usuario y calcula el acumulado total de los mismos
    public void imprimirReporte(){
        //Acumula los totales por pedido del usuario
        Double acumulado = 0.0;
        
        //Imprime datos del usuario
        System.out.println(String.format("%84s", "").replace(' ', '='));
        System.out.print("USUARIO: " + String.format("[%20.20s] | ", nombre + " " + apellido));
        System.out.print("Mail: " + String.format("[%25.25s] | ", mail));
        System.out.println("Rol: " + String.format("[%7s]", rol));
        System.out.println(String.format("%84s", "").replace(' ', '='));
       
       //Define el formato deseado de fecha
       DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       
        for (Pedido p : pedidos) {
            //Imprime datos del Pedido si no fue borrado
            if (!p.isEliminado()) {
                
                System.out.print(" > PEDIDO: " + String.format("[%04d] | ", p.getId()));

                System.out.print("Fecha: " + String.format("[%10s] | ", p.getFecha().format(formato)));
                System.out.print("Estado: " + String.format("[%10s] | ", p.getEstado()));
                System.out.println("Forma de Pago: " + String.format("[%2s]", p.getFormaPago().getAbreviatura()));
                System.out.println(String.format("%42s", "").replace(' ', '-'));

                for (DetallePedido d : p.getDetalle()) {
                    //Imprime datos del detalle si no fue borrado
                    if (!d.isEliminado()) {
                        
                        int id = (int) (d.getId()%10000);
                        System.out.print("   - DETALLE: " + String.format("[%04d]: ", id));
                        System.out.print(String.format("[%24.24s] x [%2d] => ", d.getProducto().getNombre(), d.getCantidad()));
                        System.out.println("Subtotal: $" + String.format("[%,.2f]", d.getSubtotal()));
                    }
                }

                //Imprime total del pedido
                System.out.println("TOTAL PEDIDO: $" + String.format("[%,.2f]", p.getTotal()));
                System.out.println(String.format("%42s", "").replace(' ', '-'));

                //Suma el total del pedido al acumulador del total del usuario
                acumulado += p.getTotal();
            }
        }
        
        //Imprime total del usuario
        System.out.println("TOTAL ACUMULADO DEL USUARIO: $" + String.format("[%,.2f]", acumulado));
        System.out.println(String.format("%84s", "").replace(' ', '='));

    }
    
    //IMPLEMENTA EL MÉTODO ABSTRACTO DE BASE
    @Override
    public String toString() {
        
        return String.format("%04d | %-26.26s | ", this.getId(), this.getApellido() + ", " + 
        this.getNombre()) + "Mail: " + String.format("%-20.20s | ", this.getMail()) + 
        "Rol: " + String.format("%-7s | ", this.getRol()) + "Celular: " + String.format("%-10s", this.getCelular());

    }
    
}
