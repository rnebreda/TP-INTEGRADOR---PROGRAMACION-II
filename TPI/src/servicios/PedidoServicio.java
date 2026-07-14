/*
SERVICIOS PARA LA CLASE PEDIDOS (COLLECTION + METODOS CRUD + MENU)

    //ATRIBUTOS DE PEDIDO
    private LocalDate fecha;
    private Estado estado = Estado.PENDIENTE;
    private Double total = 0.0;
    private FormaPago formaPago;
    private List<DetallePedido> detalle = new ArrayList<> ();
    private Usuario usuario;

    //CONSTRUCTOR
    Pedido(FormaPago formaPago, Usuario usuario);



    //METODOS Y ATRIBUTOS DE ABSTRACTCRUDSERVICIO
    protected List<T> elementos = new ArrayList<>();
    public void agregar(T entidad)
    public T buscar(Long id)
    public void listar()
    public void eliminar(Long id)
    public void modificar(T entidad)
    protected boolean hayElementos()
    Long getId(T entidad);



    //ATRIBUTOS PROPIOS
    private final UsuarioServicio us; // utiliza métodos de la clase de servicio 
        para Usuarios, por ejemplo para obtener un listado de los usuarios 
        disponibles.
    private final FormaPagoServicio fp; // utiliza para acceder a menu para 
        seleccionar valor de forma de pago
    private final EstadoServicio est; // utiliza para acceder a menu para 
        seleccionar valor de Estado
    private final ProductoServicio prs; // utiliza métodos de la clase de servicio 
        para Productos, por ejemplo para obtener un listado de los productos 
        disponibles.
    
    private final String[] opcionesMenu  // opciones del menú PEDIDOS

    //METODOS PUBLICOS PROPIOS (SOBRECARGA DE METODOS DE ABSTRACTCRUDSERVICIO)
    public void agregar(FormaPago formaPago, Usuario usuario)

    //METODODS DE MENU PEDIDOS (IMPLEMENTA INTERFACE MENU)
    public void mostrarMenu()
    public void ejecutarOpcion(int opcion)
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException

    //METODOS PRIVADOS
    private Pedido solicitarPedido() throws ListaVaciaException
    private Producto solicitarProducto() throws ListaVaciaException
    private int solicitarCantidad (Producto pr)
    private Usuario solicitarUsuario() throws ListaVaciaException
    private void listarUsuarios() throws ListaVaciaException
    private void listarProductos() throws ListaVaciaException
    
    //MENU MODIFICAR PEDIDO (UTILIZA METODOS DE INTERFACE MENU)
    private int menuModificar()
    private void modificar(int opcion, Pedido p)

    //REPORTES
    public void imprimirPedido(Pedido p)

    //DETALLES DE PEDIDO
    public void detalleNuevo(Pedido p)throws ListaVaciaException
    public void agregarDetalle(Pedido p, int cantidad, Producto producto)
    public DetallePedido buscarDetallePorProducto(Pedido p, Producto producto)
    public void eliminarDetallePorProducto(Pedido p, Producto producto)
    public void modificarDetallePorProducto(Pedido p, int cantidad, Producto producto)

    //MENU DETALLES DE PEDIDO (UTILIZA METODOS DE INTERFACE MENU)
    private int menuDetalles() throws ListaVaciaException
    private void ejecutarOpcionDetalles(int opcion){

    //VALIDACIONES
    private Boolean stringCorrecto(String string)
        
    //UTILIDADES
    private void encabezado (String texto)
    private boolean hayDetalles (Pedido p)

 */
package servicios;

    import entities.DetallePedido;
    import entities.Pedido;
    import entities.Producto;
    import entities.Usuario;
    import enums.Estado;
    import enums.FormaPago;
    import excepciones.CantidadProductoMenorQueCeroException;
    import excepciones.EstadoNoModificableException;
    import excepciones.FormatoInvalidoException;
    import excepciones.IdMenorQueCeroException;
    import excepciones.ListaVaciaException;
    import interfaces.Menu;
    import java.time.format.DateTimeFormatter;
    import java.util.Scanner;

public class PedidoServicio extends AbstractCrudServicio<Pedido> implements Menu{
    
    Scanner leer = new Scanner(System.in);
    
    //ATRIBUTOS
    private final UsuarioServicio us;
    private final FormaPagoServicio fp;
    private final EstadoServicio est;
    private final ProductoServicio prs;
    
    private final String[] opcionesMenu = {
        "PEDIDOS",
        "Pedido nuevo",
        "Buscar Pedido por Id",
        "Modificar Datos Pedido",
        "Eliminar",
        "Listar",
        "Detalles"};

        // Inyección por constructor
    public PedidoServicio(UsuarioServicio us, FormaPagoServicio fp, EstadoServicio est, ProductoServicio prs) {
        this.us = us;
        this.fp = fp;
        this.est = est;
        this.prs = prs;

    }
    
    
    
    // SOBRECARGA DE METODOS
        
        //Agregar Pedido (sin detalles)
    public Pedido agregar(FormaPago formaPago, Usuario usuario){
        boolean correcto = formaPago != null && usuario != null;
        
        if (correcto) {
            Pedido p = new Pedido(formaPago, usuario);
            elementos.add(p);
            usuario.agregarPedido(p);
            System.out.println("Pedido creado con éxito");
            System.out.println(p);
            return p;
        }else{
            System.out.println("La operación falló. Verifique los datos ingresados");
        }
        return null;
    }
    
    @Override
    public Long getId(Pedido entidad) {
        return entidad.getId();
    }
    
    
    
    // MENU PEDIDOS
    public void mostrarMenu() {
        //Utiliza método mostrarMenu de la interface Menu
        mostrarMenu(opcionesMenu);
    }

        //Sobrescribe método ejecutarOpcion de interface Menu
    @Override
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> { //"Pedido nuevo"
                Pedido p = null;
                try{
                    Usuario u = solicitarUsuario();

                    System.out.println("Ingrese la forma de pago: ");
                    FormaPago f = fp.asignarFormaPago(leer);
                    if(f != null){
                        p = agregar(f, u);
                    }else{
                        System.out.println("La operación falló");
                    }

                }catch (ListaVaciaException e){
                    System.out.println("La operación falló. No hay usuarios en la lista");
                }
                
                if (p != null) {
                    boolean agregarNuevoDetalle = false;
                    try{
                        do{
                            detalleNuevo(p);
                            System.out.print("Desea agregar nuevo detalle? (S = si): ");
                            agregarNuevoDetalle = leer.nextLine().equalsIgnoreCase("S");
                        }while(agregarNuevoDetalle);
                        System.out.println();
                        imprimirPedido(p);
                    }catch (ListaVaciaException e){
                        System.out.println("No hay productos. No se pueden generar detalles.");
                    }
                }
            } 
            case 2,3,4 -> {
                try{
                    menuBuscarModificarEliminar(opcion);
                }catch (ListaVaciaException e){
                    System.out.println("No hay Pedidos");
                }
            }   
            case 5 -> {  //"Listar Pedidos"
                encabezado("PEDIDOS DISPONIBLES");
                listar();
            }              
            case 6 -> {//"Crud Detalle"
                try{
                    ejecutarOpcionDetalles(menuDetalles());
                    
                }catch (ListaVaciaException e){
                    System.out.println("No hay Pedidos");
                }
            }  
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción inválida");
        }
        System.out.println();
    }
    
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException{
        Pedido buscado = null;
        boolean ingresoCorrecto = false;
        encabezado("PEDIDOS DISPONIBLES");
        listar();
        if (hayElementos()) {
            do{
                System.out.print("Ingrese el Id del pedido buscado: ");
                String valor = leer.nextLine();
                try{
                    Long l = Long.valueOf(valor);
                    if (l <= 0) {
                        throw new IdMenorQueCeroException();
                    }
                    buscado = buscar(l);
                    ingresoCorrecto = true;
                    
                    if (buscado != null) {
                        switch (opcion) {
                            case 2 -> imprimirPedido(buscado); //Buscar
                            //Muestra info del pedido con lista de detalles

                            case 3 -> { // Modificar                                                         
                                System.out.println("Modificando... ");
                                System.out.println(buscado);
                                System.out.print("Desea modificar? (S = Si): ");
                                boolean modificar = leer.nextLine().equalsIgnoreCase("S");
                                do{
                                    if (modificar) { 
                                        modificar(menuModificar(), buscado);
                                        System.out.println(buscado);
                                        System.out.print("Continua modificando? (S = Si): ");
                                        modificar = leer.nextLine().equalsIgnoreCase("S");
                                    }
                                    
                                }while(modificar);
                                System.out.println();
                                System.out.println(buscado);

                            }
                            case 4 -> { //Eliminar
                                
                                if (buscado.getEstado().equals(Estado.PENDIENTE)) {
                                                                 
                                    System.out.println("Se va a eliminar el siguiente pedido: ");
                                    imprimirPedido(buscado);
                                    System.out.print("Desea eliminar? (S = Si): ");
                                    if (leer.nextLine().equalsIgnoreCase("S")) {
                                        if (hayDetalles(buscado)) {
                                            buscado.vaciarListaDetalles();
                                        }
                                        Usuario u = buscado.getUsuario();
                                        u.borrarPedido(buscado);
                                        eliminar(buscado.getId());
                                        System.out.println("Pedido eliminado con éxito");
                                    }
                                }else{
                                    System.out.println("El pedido no puede eliminarse en Estado " + buscado.getEstado());
                                }
                            }
                        }
                    }else{
                        System.out.println("El N° de pedido ingresado no existe en el listado");
                    }
                              
                }catch (NumberFormatException | IdMenorQueCeroException m) {
                    System.out.println("EL VALOR INGRESADO NO ES UN N° DE ID VÁLIDO");

                }

            }while (!ingresoCorrecto);
             
        }else{
            throw new ListaVaciaException();
        }
        System.out.println();  
        
    }
    

    
    //METODOS PRIVADOS
    
        //Lista los pedidos existentes y entrega un pedido elegido según su id
    private Pedido solicitarPedido() throws ListaVaciaException{
        boolean ingresoCorrecto = false;
        Pedido p = null;
        encabezado("PEDIDOS DISPONIBLES");
        listar();
        if (hayElementos()) {
            do{
                System.out.print("Ingrese el id del pedido: ");
                String valorPedido = leer.nextLine();
                try{
                    if (!stringCorrecto(valorPedido)) {
                        throw new FormatoInvalidoException();
                    }
                    Long l = Long.valueOf(valorPedido);
                    if (l <= 0) {
                        throw new IdMenorQueCeroException();
                    }
                    p = buscar(l);
                    if (p != null) {
                        ingresoCorrecto = true;
                    }

                }catch (NumberFormatException | IdMenorQueCeroException | FormatoInvalidoException e) {
                    System.out.println("EL VALOR INGRESADO NO ES UN N° DE ID VÁLIDO");
                }
                if (!ingresoCorrecto) {
                    System.out.println("El id ingresado no existe en el listado.");
                }

            }while(!ingresoCorrecto);
        }else{
             throw new ListaVaciaException(); 
        }
        
        return p;
        
    }
    
        //Lista los productos existentes y entrega un producto elegido por su id o nombre
    private Producto solicitarProducto() throws ListaVaciaException {
        boolean ingresoCorrecto = false;
        Producto pr = null;
        encabezado("PRODUCTOS DISPONIBLES");
        listarProductos();
        if (prs.hayElementos()) {
            do{
                System.out.print("Ingrese el id del producto: ");
                String valorProducto = leer.nextLine();
            
                try{
                    if (!stringCorrecto(valorProducto)) {
                        throw new FormatoInvalidoException();
                    }
                    Long l = Long.valueOf(valorProducto);
                    if (l <= 0) {
                        throw new IdMenorQueCeroException();
                    }
                    pr = prs.buscar(l);
                    if (pr != null) {
                        ingresoCorrecto = true;
                    }              
                }catch (IdMenorQueCeroException | FormatoInvalidoException m){
                    System.out.println("EL VALOR INGRESADO NO ES UN N° DE ID VÁLIDO");
                }catch (NumberFormatException e) {
                    //Si no es un Long(id) y da NumberFormatException busca por nombre
                    pr = prs.buscar(valorProducto);
                    if (pr != null) {
                        ingresoCorrecto = true;
                    }     
                }
                if (!ingresoCorrecto) {
                    System.out.println("El id ingresado no existe en el listado.");
                }

            }while(!ingresoCorrecto);
            
        }else{
             throw new ListaVaciaException(); 
        }
        return pr;
    }
    
        //Entrega un entero con la cantidad de producto a agregar al detalle del pedido
    private int solicitarCantidad (Producto pr){
        boolean ingresoCorrecto = false;
        int cantidad = 0;
        do{
            System.out.print("Ingrese la cantidad del producto " + pr.getNombre() + ": ");
            String valorCantidad = leer.nextLine();
            try{
                cantidad = Integer.parseInt(valorCantidad);
                if (cantidad <= 0) {
                    throw new CantidadProductoMenorQueCeroException();
                }
                ingresoCorrecto = true;
            }catch (NumberFormatException | CantidadProductoMenorQueCeroException e) {
                System.out.println("EL VALOR INGRESADO ES INCORRECTO");
            }
        }while(!ingresoCorrecto) ;      
        
        return cantidad;
                
    }
    
        //Entrega un Usuario. Primero muestra una lista de usuarios 
        //existentes para luego ingresar el id del usuario elegido.
    private Usuario solicitarUsuario() throws ListaVaciaException{
        boolean ingresoCorrecto = false;
        Usuario u = null;
        encabezado("USUARIOS DISPONIBLES");
        listarUsuarios();
        do{
            System.out.print("Ingrese el id del usuario del pedido: ");
            String valorUsuario = leer.nextLine();
            try{
                Long l = Long.valueOf(valorUsuario);
                if (l <= 0) {
                    throw new IdMenorQueCeroException();
                }
                u = us.buscar(l);
                if (u != null) {
                    ingresoCorrecto = true;
                }     
            }catch (NumberFormatException | IdMenorQueCeroException e) {
                System.out.println("EL VALOR INGRESADO NO ES UN N° DE ID VÁLIDO");
            }
            if (!ingresoCorrecto) {
                System.out.println("El id ingresado no existe en el listado.");
            }
        }while(!ingresoCorrecto);
        
        return u;
        
    }
    
        //Lista los usuarios disponibles
    private void listarUsuarios() throws ListaVaciaException{
        if (us.hayElementos()) {
            us.listar();
        }else{
            throw new ListaVaciaException();
        }
        
    }
    
        //Lista los Productos disponibles
    private void listarProductos() throws ListaVaciaException{
        if (prs.hayElementos()) {
            prs.listar();
        }else{
            throw new ListaVaciaException();
        }
        
    }
    
    
    
    //MENU MODIFICAR PEDIDO
    // NO se modifica el usuario
    private int menuModificar(){

        String[] opcionesMenuModificar = {
            "MODIFICAR DATOS PEDIDO",
            "Forma de Pago",
            "Estado"};
        
        //Utiliza método getOpcion de la interface Menu
        return Menu.super.getOpcion(opcionesMenuModificar);
    }
    
        //Modifica segun opcion de menuModificar
    private void modificar(int opcion, Pedido p){
         switch (opcion){
            case 1 -> { //Formapago
                if (p.getEstado().equals(Estado.PENDIENTE)) {
                    FormaPago formaPagoActual = p.getFormaPago();
                    System.out.println("Ingrese la nueva forma de pago: ");
                    FormaPago f = fp.asignarFormaPago(leer);
                    
                    if (f != null && !f.equals(formaPagoActual)) {
                        p.setFormaPago(f);
                        System.out.println("Forma de pago modificada con éxito");
                    }else{
                        System.out.println("La operación falló");
                    }
                }else{
                    System.out.println("Pedido con estado " + p.getEstado() + " NO se puede modificar.");
                }
            }
            case 2 -> { //Estado
                boolean modificar = true;
                Estado estadoActual = p.getEstado();
                int ordinalEstadoActual = estadoActual.ordinal(), ordinalEstado = 0;
                
                if (estadoActual.equals(Estado.PENDIENTE)){                
                    System.out.println("Una vez modificado el Estado, "
                        + "el pedido no se podrá modificar ni eliminar."
                        + " Desea modificar? (S = si): ");
                    modificar = leer.nextLine().equalsIgnoreCase("S");

                }
                
                if (modificar) {
                    if (hayDetalles(p)) {
                        System.out.println("Ingrese el nuevo Estado: ");
                        Estado estado = est.asignarEstado(leer);
                        if (estado != null) {
                            ordinalEstado = estado.ordinal();
                        }
                        

                        if (estado != null && ordinalEstado > ordinalEstadoActual) {
                            p.setEstado(estado);
                            System.out.println("Estado modificado con éxito");
                        }else{
                            System.out.println("La operación falló.");
                            if (estado != null) {
                                System.out.println("El estado del Pedido no puede cambiar de " 
                                + estadoActual + " a " + estado);
                            }
                        }
                    }else{
                        System.out.println("La operación falló.");
                        System.out.println("El pedido no posee detalles aún.");
                    }
                }else{
                        System.out.println("El pedido no tiene detalles aún.");
                }
            }
            case 0 -> System.out.println("Volviendo al menú Pedidos...");
            default -> System.out.println("Opción inválida");
        }
        System.out.println();
        
    }
    
    
    
    //REPORTES
    
    public void imprimirPedido(Pedido p){
        //Define el formato deseado de fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Imprime datos del Pedido
        System.out.print(" > PEDIDO: " + String.format("[%04d] | ", p.getId()));

        System.out.print("Fecha: " + String.format("[%10s] | ", p.getFecha().format(formato)));
        System.out.print("Estado: " + String.format("[%10s] | ", p.getEstado()));
        System.out.println("Forma de Pago: " + String.format("[%2s]", p.getFormaPago().getAbreviatura()));
        System.out.println(String.format("%42s", "").replace(' ', '-'));
        
        try{
            if (!hayDetalles(p)) {
                throw new ListaVaciaException();
            }
            
            for (DetallePedido d : p.getDetalle()) {
            //Imprime datos del detalle si no estan borrados
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
        
        }catch (ListaVaciaException e){
            System.out.println("El pedido no tiene detalles aún.");    
        }
            
    }
    

    
    //DETALLES DE PEDIDO
    
        //Crea un detalle de pedido nuevo a un pedido nuevo
    public void detalleNuevo(Pedido p)throws ListaVaciaException{
        if (prs.hayElementos()) {
            Producto pr = null;
            pr = solicitarProducto();
            int cantidad = solicitarCantidad(pr);
            p.addDetallePedido(cantidad, pr);
        }else{
            throw new ListaVaciaException();
        }
    }
        //Agrega una linea de detalle a un pedido dado 
        //para un producto no existente en el pedido
    public void agregarDetalle(Pedido p, int cantidad, Producto producto){
        p.addDetallePedido(cantidad, producto);
    }
        //Busca una línea de detalle en un pedido dado según el producto 
        //y lo muestra si lo encuentra
    public DetallePedido buscarDetallePorProducto(Pedido p, Producto producto){
        return p.findDetallePedidoByProducto(producto);
    }
        //Elimina una linea de detalle en un pedido dado según el producto
    public void eliminarDetallePorProducto(Pedido p, Producto producto){
        p.deleteDetallePedidoByProducto(producto);     
    }
        //Modifica una linea de detalle en un pedido 
        //(elimina la existe y genera una nueva con la cantidad correcta según el producto)
    public void modificarDetallePorProducto(Pedido p, int cantidad, Producto producto){
        p.updateDetallePedidoByProducto(cantidad, producto);
    }
    
    
    
    //MENU DETALLES DE PEDIDO
    private int menuDetalles() throws ListaVaciaException{

        String[] opcionesMenuDetalles = {
            "DETALLES DE PEDIDO",
            "Agregar Detalle a pedido",
            "Buscar Detalle por Producto",
            "Modificar Detalle por Producto",
            "Eliminar Detalle por producto"};
        
        if (hayElementos()) {
            //Utiliza método getOpcion de la interface Menu
            return Menu.super.getOpcion(opcionesMenuDetalles);
        }else{
            throw new ListaVaciaException();
        }

    }
    
    //ejecuta segun opcion de menuDetalles
    private void ejecutarOpcionDetalles(int opcion){

        try{
            Pedido p = solicitarPedido();
            if (!p.getEstado().equals(Estado.PENDIENTE)) {
                throw new EstadoNoModificableException();
            }
            imprimirPedido(p);
            int cantidad = 0;
            Producto pr = null;
            
            try{
                //Si el pedido elegido no tiene detalles solo permite agregar detalle
                if (!hayDetalles(p) && opcion > 1 && opcion <= 4) {
                    throw new ListaVaciaException();
                }

            }catch(ListaVaciaException e){
                System.out.print("Desea agregar detalles? (S = Si): ");
                if (leer.nextLine().equalsIgnoreCase("S")) {
                    opcion = 1;

                }else
                    opcion = 0;
            }
            
               
            try{
                do{
                    pr = solicitarProducto();
                }while (pr == null);

                switch (opcion){
                    case 1 -> { //"Agregar Detalle a pedido"
                        cantidad = solicitarCantidad(pr);
                        p.addDetallePedido(cantidad, pr);
                    }
                    case 2 -> { //"Buscar Detalle por Producto"
                        DetallePedido d = buscarDetallePorProducto(p, pr);
                        System.out.println(d);
                    }
                    case 3 -> { //"Modificar Detalle por Producto"
                        DetallePedido d = buscarDetallePorProducto(p, pr);
                        System.out.println(d);
                        cantidad = solicitarCantidad(pr);
                        modificarDetallePorProducto(p, cantidad, pr);   
                    }
                    case 4 -> { //"Eliminar Detalle por producto"
                        DetallePedido d = buscarDetallePorProducto(p, pr);
                        System.out.println("Se va a eliminar la siguiente Detalle: ");
                        System.out.println(d);
                        System.out.print("Desea eliminar? (S = Si): ");
                        if (leer.nextLine().equalsIgnoreCase("S")) {
                            eliminarDetallePorProducto(p,pr);
                        }
                    }
                    case 0 -> System.out.println("Volviendo al menú Pedidos...");
                    default -> System.out.println("Opción inválida");
                }
                System.out.println();
                imprimirPedido(p);

            }catch (ListaVaciaException e){
                System.out.println("La operación falló. No hay productos");

        }
            
        }catch (ListaVaciaException e){
            System.out.println("La operación falló. No hay pedidos.");
        }catch (EstadoNoModificableException e){
                System.out.println("La operación falló. El pedido solo puede modificarse en Estado PENDIENTE.");
        }

        
    }
    
    
    
    //VALIDACIONES
    private Boolean stringCorrecto(String string){
        return string != null && !string.isEmpty() && !string.isBlank();
    }     
    
    
    
    //UTILIDADES
    private void encabezado (String texto){
        System.out.println(String.format("%40s", "").replace(' ', '='));
        System.out.println(texto);
        System.out.println(String.format("%20s", "").replace(' ', '='));
    }
    
    private boolean hayDetalles (Pedido p){
        return p.getTotal() > 0.0;
    }
}
