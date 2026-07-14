/*
    MENU PRINCIPAL implementa Interface Menu

    //ATRIBUTOS PROPIOS
    CategoriaServicio cs; //Clase de servicios para Categorías
    ProductoServicio prs; //Clase de servicios para Productos
    FormaPagoServicio fps; //Clase de servicios para FormaPago
    EstadoServicio est; //Clase de servicios para Estado
    RolServicio rs; // Clase de servicios para Rol
    UsuarioServicio us; //Clase de servicios para Usuario
    PedidoServicio ps; //Clase de servicios para Pedidos

    private final String[] opcionesMenu // opciones del menú Principal


    //METODOS DE MENU PRINCIPAL (IMPLEMENTA INTERFACE MENU)
    public void mostrarMenu()
    public void ejecutarOpcion(int opcion)


 */
package servicios;

    import interfaces.Menu;

public class MenuServicio implements Menu{
    
    
    //ATRIBUTOS
    CategoriaServicio cs = new CategoriaServicio();
    ProductoServicio prs = new ProductoServicio(cs);
    FormaPagoServicio fps = new FormaPagoServicio();
    EstadoServicio est = new EstadoServicio();
    RolServicio rs = new RolServicio();
    UsuarioServicio us = new UsuarioServicio(rs);
    PedidoServicio ps = new PedidoServicio(us,fps,est, prs );
    
    private final String[] opcionesMenu = {
    
        "SISTEMA DE PEDIDOS (FOOD STORE)",
        "Categorías",
        "Productos",
        "Usuarios", 
        "Pedidos"};
    
    
    
    // MENU PRINCIPAL (SISTEMA DE PEDIDOS FAST FOOD)
    public void mostrarMenu(){
        mostrarMenu(opcionesMenu);
    }
    
    @Override
    public void ejecutarOpcion(int opcion){
        
         switch (opcion) {
             
            case 1 -> cs.mostrarMenu();
            case 2 -> prs.mostrarMenu();
            case 3 -> us.mostrarMenu();
            case 4 -> ps.mostrarMenu();
            case 0 -> System.out.println("Saliendo del programa...\nHasta Luego!!");
            default -> System.out.println("Opción inválida");
         }
    }
    
}
