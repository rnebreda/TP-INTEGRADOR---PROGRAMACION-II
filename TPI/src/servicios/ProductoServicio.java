<<<<<<< HEAD
/*
SERVICIOS PARA LA CLASE PRODUCTOS (COLLECTION + METODOS CRUD + MENU)

    //ATRIBUTOS DE PRODUCTOS
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock = 0;
    private String imagen = "Sin imagen";
    private boolean disponible = false;
    private Categoria categoria;

    //CONSTRUCTORES DE PRODUCTO
    Producto(String nombre, Double precio, String descripcion, Categoria categoria);

    Producto(String nombre, Double precio, String descripcion, int stock, 
    String imagen, boolean disponible, Categoria categoria)




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
    private final String[] opcionesMenu // opciones del menú PRODUCTOS
    private final CategoriaServicio cs; // utiliza métodos de la clase de servicio 
        para Categorías, por ejemplo para obtener un listado de las categorías 
        disponibles.

    //METODOS PUBLICOS PROPIOS (SOBRECARGA DE METODOS DE ABSTRACTCRUDSERVICIO)
    public void agregar(String nombre, Double precio, String descripcion, int stock, 
        String imagen, boolean disponible, Categoria categoria)
    public Producto buscar (String nombre) // BUSCA POR NOMBRE

    //METODODS DE MENU PRODUCTOS (IMPLEMENTA INTERFACE MENU)
    public void mostrarMenu()
    public void ejecutarOpcion(int opcion)
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException

    //METODOS PRIVADOS
    private String solicitarTexto(String tipo)
    private int solicitarStock(String operacion)
    private Double solicitarPrecio()
    private Categoria solicitarCategoria() throws ListaVaciaException
    private void listarCategorias() throws ListaVaciaException

    //MENU MODIFICAR PRODUCTO (UTILIZA METODOS DE INTERFACE MENU)
    private int menuModificar()
    private void modificar(int opcion, Producto pr)

    //REPORTES
    public void reporteProductosSinStock() throws ListaVaciaException
    public void reporteProductosCategoriaEliminada()throws ListaVaciaException

    //VALIDACIONES
    private Boolean stringCorrecto(String string)
        
    //UTILIDADES
    private void encabezado (String texto)

 */
package servicios;

    import entities.Categoria;
    import entities.Producto;
    import excepciones.CantidadProductoMenorQueCeroException;
    import excepciones.FormatoInvalidoException;
    import excepciones.IdMenorQueCeroException;
    import excepciones.ListaVaciaException;
    import excepciones.PrecioMenorQueCeroException;
    import interfaces.Menu;
    import java.util.ArrayList;
    import java.util.Comparator;
    import java.util.Iterator;
    import java.util.List;
    import java.util.Objects;
    import java.util.Scanner;

public class ProductoServicio extends AbstractCrudServicio<Producto> implements Menu{
    
    Scanner leer = new Scanner(System.in);
    
    //ATRIBUTOS
    
    private final String[] opcionesMenu = {
        "PRODUCTOS",
        "Agregar",
        "Buscar por Id o Nombre",
        "Modificar",
        "Eliminar",
        "Listar",
        "Reporte de Productos sin stock",
        "Reporte de Productos con Categoría Eliminada"};
    
    private final CategoriaServicio cs;


    // Inyección por constructor
    public ProductoServicio(CategoriaServicio cs) {
        this.cs = cs;

    }
     
    
    // SOBRECARGA DE METODOS
    
        //Agregar Producto
    public void agregar(String nombre, Double precio, String descripcion, int stock, 
        String imagen, boolean disponible, Categoria categoria){
        boolean nombreCorrecto = stringCorrecto(nombre);
        boolean descripcionCorrecta = stringCorrecto(descripcion);
        boolean precioCorrecto = precio > 0.0;
        boolean stockCorrecto = stock > 0;
        boolean imagenCorrecta = stringCorrecto(imagen);
        boolean categoriaCorrecta = categoria != null;
        boolean productoYaExiste = buscar(nombre)!= null;
        boolean correcto = nombreCorrecto && descripcionCorrecta && precioCorrecto &&
                stockCorrecto && imagenCorrecta && categoriaCorrecta && !productoYaExiste;
        
        if (correcto) {
            Producto pr = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoria);
            agregar(pr);
            categoria.agregarProducto(pr);
            System.out.println("Producto creado con éxito");
            System.out.println(pr);
        }else{
            System.out.println("La operación falló. Verifique los datos ingresados");
        }

    }
    
            //Buscar por nombre
        public Producto buscar (String nombre){
        Producto encontrado = null;
        Iterator<Producto> it = elementos.iterator();
        while (it.hasNext()) {
            
            Producto pr = it.next();
            if (pr.getNombre().equalsIgnoreCase(nombre) && !pr.isEliminado()) {
                encontrado = pr;
                break; // dejamos de buscar
            }
        }
        return encontrado;
    }
        
    @Override
    public Long getId(Producto entidad) {
        return entidad.getId();
    }
        

    //MENU PRODUCTOS
    public void mostrarMenu() {
        //Utiliza método mostrarMenu de la interface Menu
        mostrarMenu(opcionesMenu);
    }
    
        //Sobrescribe método ejecutarOpcion de interface Menu
    @Override
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> { //Agregar Producto            

                try{
                    Categoria c = null;
                    do{
                        c = solicitarCategoria();
                    }while (c == null);
                    String nombre = solicitarTexto("nombre del producto");
                    String descripcion = solicitarTexto("descripción");
                    String imagen = solicitarTexto("imágen");   
                    Double precio = solicitarPrecio();
                    int stock = solicitarStock("Agregar");
                    boolean disponible = stock > 0;
                    agregar(nombre, precio, descripcion, stock, imagen, disponible, c);
                }catch (ListaVaciaException e){
                    System.out.println("La operación falló. No hay Categorías");
                }
            }
            case 2,3,4 -> {
                try{
                    menuBuscarModificarEliminar(opcion);
                }catch (ListaVaciaException e){
                    System.out.println("No hay Productos");
                }
            }   
            case 5 -> { //Listar productos
                encabezado("PRODUCTOS DISPONIBLES");
                listar();
            }
            case 6 -> {
                try{
                    reporteProductosSinStock();
                }catch (ListaVaciaException e){
                    System.out.println("No hay productos");
                }
                
            }   
            case 7 -> {
                try{
                    reporteProductosCategoriaEliminada();
                }catch (ListaVaciaException e){
                    System.out.println("No hay productos");
                }
                
            }   
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción inválida");
        }
        System.out.println();
    }
    
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException{
        Producto buscado = null;
        boolean ingresoCorrecto = false;
        encabezado("PRODUCTOS DISPONIBLES");
        listar();
        if (hayElementos()) {
            do{
                System.out.print("Ingrese el Id o el nombre del producto buscado: ");
                String valor = leer.nextLine();
                try{
                    if (!stringCorrecto(valor)) {
                        throw new FormatoInvalidoException();
                    }
                    try{
                        Long l = Long.valueOf(valor);
                        if (l <= 0) {
                            throw new IdMenorQueCeroException();
                        }
                        buscado = buscar(l);
                    }catch (NumberFormatException e) {
                        //Si no es un Long(id) y da NumberFormatException busca por nombre
                        buscado = buscar(valor);    
                    }
                    ingresoCorrecto = true;
                    
                    if (buscado != null) {
                        switch (opcion) {
                            case 2 -> System.out.println(buscado); //Buscar
                            //Muestra info del producto

                            case 3 -> { //Modificar
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
                                System.out.println("Se va a eliminar el siguiente producto: ");
                                System.out.println(buscado);
                                System.out.print("Desea eliminar? (S = Si): ");
                                if (leer.nextLine().equalsIgnoreCase("S")) {
                                    eliminar(buscado.getId());
                                    System.out.println("Producto eliminado con éxito");
                                }
                            }
                        }
                    }else{
                        System.out.println("El producto ingresado no existe en el listado");
                    }

                }catch (IdMenorQueCeroException | FormatoInvalidoException m){
                    System.out.println("EL VALOR INGRESADO NO ES UN N° DE ID VÁLIDO");

                }
            }while(!ingresoCorrecto);
            
        }else{
            throw new ListaVaciaException();
        }
        System.out.println();
        
    }
    
    //METODOS PRIVADOS
    
        //Entrega y valida un valor para un atributo tipo String y que 
        //además no sea numérico (no comienza con signo o cifra)
    private String solicitarTexto(String tipo){    
        boolean ingresoCorrecto = false;
        String texto;
            do{
                System.out.print("Ingrese " + tipo + ": ");             
                texto = leer.nextLine();
                try{
                    if (!stringCorrecto(texto) || !Character.isLetter(texto.charAt(0))) {
                        throw new FormatoInvalidoException();
                    }
                    ingresoCorrecto = true;
                }catch (FormatoInvalidoException e){
                    System.out.println("FORMATO INVÁLIDO");
                }
            }while(!ingresoCorrecto);
        return texto;
    }
    
        // Entrega una cantidad (int) para agregar o consumir del stock del producto
    private int solicitarStock(String operacion){
        boolean ingresoCorrecto = false;
        String tipo = "agregar";
        if (operacion.equalsIgnoreCase("C")) {
            tipo = "consumir";
        }
        int cantidad = 0;
        do{
            System.out.print("Ingrese la cantidad a " + tipo + ": ");
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
            
        }while(!ingresoCorrecto);
        
        return cantidad;
            
    }
    
        //Entrega un Double (precio)
    private Double solicitarPrecio (){
        boolean ingresoCorrecto = false;
        Double precio = 0.0;
        do{
            System.out.print("Ingrese el precio del producto: ");
            String valorPrecio = leer.nextLine();
            try{
                precio = Double.valueOf(valorPrecio);
                if (precio <= 0.0) {
                    throw new PrecioMenorQueCeroException();
                }
                ingresoCorrecto = true;    
            }catch (NumberFormatException | PrecioMenorQueCeroException e) {
                System.out.println("EL VALOR INGRESADO ES INCORRECTO");
            }
                
        }while(!ingresoCorrecto);
        
        return precio;
                
    }
    
        //Entrega una Categoría. Primero muestra una lista de categorías 
        //existentes para luego ingresar el id de la categoría elegida.
    private Categoria solicitarCategoria() throws ListaVaciaException{
        boolean ingresoCorrecto = false;
        Categoria c = null;
        encabezado("CATEGORIAS DISPONIBLES");
        listarCategorias();
        do{
            System.out.print("Ingrese el id de la categoría del producto: ");
            String valorCategoria = leer.nextLine();
            try{
                Long l = Long.valueOf(valorCategoria);
                if (l <= 0) {
                    throw new IdMenorQueCeroException();
                }
                c = cs.buscar(l);
                if (c != null) {
                    ingresoCorrecto = true;
                }
            }catch (NumberFormatException | IdMenorQueCeroException e) {
                System.out.println("EL VALOR INGRESADO NO ES UN N° DE ID VÁLIDO");
            }
            if (!ingresoCorrecto) {
                System.out.println("El id ingresado no existe en el listado.");
            }                           
        }while(!ingresoCorrecto);
        return c;
        
    }
    
        //Lista las categorías existentes
    private void listarCategorias()throws ListaVaciaException{
        if (cs.hayElementos()) {
            cs.listar();
        }else{
            throw new ListaVaciaException();
        }
        
    }
    
    //MENU MODIFICAR PRODUCTO
    private int menuModificar(){

        String[] opcionesMenuModificar = {
            "MODIFICAR PRODUCTO",
            "Nombre",
            "Precio",
            "Descripción",
            "Stock",
            "Imagen",
            "Disponible",
            "Categoría"};
        
        //Utiliza método getOpcion de la interface Menu
        return Menu.super.getOpcion(opcionesMenuModificar);
    }
    
        //Modifica segun opcion de menuModificar
    private void modificar(int opcion, Producto pr){
         switch (opcion){
            case 1 -> { //Nombre
                String nombreActual = pr.getNombre();
                String nombre = solicitarTexto("el nuevo nombre");

                boolean productoYaExiste = buscar(nombre)!= null && buscar(nombre) != pr;
                if (!productoYaExiste && !nombre.equals(nombreActual)) {
                    pr.setNombre(nombre);
                    System.out.println("Nombre modificado con éxito");
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 2 -> { //Precio
                Double precioActual = pr.getPrecio(), precio;
                precio = solicitarPrecio();
                if (precio > 0 && !Objects.equals(precio, precioActual)) {
                    pr.setPrecio(precio);
                    System.out.println("Precio modificado con éxito");
                }else{
                    System.out.println("La operación falló");
                }          
            }
            case 3 -> { //Descripción
                String descripcionActual = pr.getDescripcion();
                String descripcion = solicitarTexto("nueva descripción");

                if (!Objects.equals(descripcion, descripcionActual)){
                    pr.setDescripcion(descripcion);
                    System.out.println("Descripción modificada con éxito");
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 4 -> { //Stock
                int stockActual = pr.getStock(), stock;
                boolean exito = false;
                //permite agregar o consumir stok de acuerdo a la opción elegida
                //"A": agregar. La cantidad ingresada se sumará al stock existente
                //"C": consumir. La cantidad ingresada se restará al stock existente,
                //siempre y cuando el stock no quede con un valor negativo.
                System.out.print("Desea agregar (A) o consumir (C) stock actual = " + stockActual + "? ");
                String operacion = leer.nextLine().toUpperCase();
                if (operacion.equals("A") || operacion.equals("C")) {
                    stock = solicitarStock(operacion);
                    if (stock > 0) {            
                        switch (operacion){
                            case "A" -> { //Agrega la cantidad al stock actual
                                pr.setStock(stock);
                                exito = true;
                            }
                            case "C" -> exito = pr.consumirStock(stock);
                            //Descuenta la cantidad al stock actual, 
                            //si el stock resultante no es menor que cero.
                        }
                    }
                    if (exito) {
                        System.out.println("Stock modificado con éxito");
                    }else{
                        System.out.println("La operación falló. Stock insuficiente.");
                    }
                }     
            }
            case 5 -> { //Imagen
                String imagenActual = pr.getImagen();
                String imagen = solicitarTexto("nueva imágen");

                if (!Objects.equals(imagen, imagenActual)){
                    pr.setImagen(imagen);
                    System.out.println("Imágen modificada con éxito");
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 6 -> { //Disponible
                System.out.print("Producto " + pr.getNombre());
                if (pr.isDisponible()) {
                    System.out.print(" Disponible. Stock = " + pr.getStock() );
                }else{
                    System.out.print(" No disponible. Stock = " + pr.getStock());
                }
                System.out.print(" Desea Modificar? (S = Si): ");
                if (leer.nextLine().equalsIgnoreCase("S")) {
                    pr.setDisponible(!pr.isDisponible());
                    System.out.println("Disponible modificado con éxito");
                }
            }
            case 7 -> { //Categoria
                boolean exito = false;           
                Categoria actual = pr.getCategoria(), categoria = null;
                do{
                    try{
                        categoria = solicitarCategoria();
                        exito = true;
                    }catch (ListaVaciaException e){
                        System.out.println("No hay categorías.");
                    }
                }while (categoria == null && exito);
                if (categoria != null && !Objects.equals(categoria, actual)) {
                    pr.setCategoria(categoria);
                    System.out.println("Categoría modificada con éxito");
                }else{
                    System.out.println("La operación falló");
                }

            }
            case 0 -> System.out.println("Volviendo al menú Productos...");
            default -> System.out.println("Opción inválida");
        }
        
    }
    
    
    //REPORTES
        //Imprime lista de productos con stock = 0, suponiendo que se desee 
        //posteriormente agregar stock
    public void reporteProductosSinStock() throws ListaVaciaException{
        
        if (hayElementos()) {
            List<Producto> sinStock = new ArrayList<>();
            elementos.sort(Comparator.comparingLong(Producto::getId));
            
            for (Producto pr : elementos) {
            //Imprime productos si no están borrados y no tienen stock
                if (!pr.isEliminado() && pr.getStock() == 0) {
                    sinStock.add(pr);
                }
            }
            
            if (!sinStock.isEmpty()) {
                System.out.println(String.format("%40s", "").replace(' ', '='));
                System.out.println("PRODUCTOS SIN STOCK");
                System.out.println(String.format("%20s", "").replace(' ', '='));
                for (Producto pr : sinStock) {
                    System.out.println(pr);
                }
            }else{
                System.out.println("No hay Productos sin stock.");
            }
        }else{
            throw new ListaVaciaException();
        }
    }
    
        //Imprime lista de productos cuya categoría ha sido eliminada
        //suponiendo que se desee posteriormente modificar la misma. 
    public void reporteProductosCategoriaEliminada()throws ListaVaciaException{
        
        if (hayElementos()) {
            List<Producto> categoriaEliminada = new ArrayList<>();
            elementos.sort(Comparator.comparingLong(Producto::getId));
            
            for (Producto pr : elementos) {
            //Imprime productos si no están borrados y tienen Categoría
            //null ya que su categoría original fue eliminada
                if (!pr.isEliminado() && pr.getCategoria() == null) {
                    categoriaEliminada.add(pr);
                }
            }
            
            if (!categoriaEliminada.isEmpty()) {
                System.out.println(String.format("%40s", "").replace(' ', '='));
                System.out.println("PRODUCTOS CON CATEGORIA ELIMINADA");
                System.out.println(String.format("%20s", "").replace(' ', '='));
                for (Producto pr : categoriaEliminada) {
                    System.out.println(pr);
                }
            }else{
                System.out.println("No hay productos con categoría eliminada.");
            }
        }else{
            throw new ListaVaciaException();
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
}

=======
/*
SERVICIOS PARA LA CLASE PRODUCTOS (COLLECTION + METODOS CRUD + MENU)

    //ATRIBUTOS DE PRODUCTOS
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock = 0;
    private String imagen = "Sin imagen";
    private boolean disponible = false;
    private Categoria categoria;

    //CONSTRUCTORES DE PRODUCTO
    Producto(String nombre, Double precio, String descripcion, Categoria categoria);

    Producto(String nombre, Double precio, String descripcion, int stock, 
    String imagen, boolean disponible, Categoria categoria)




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
    private final String[] opcionesMenu // opciones del menú PRODUCTOS
    private final CategoriaServicio cs; // utiliza métodos de la clase de servicio 
        para Categorías, por ejemplo para obtener un listado de las categorías 
        disponibles.

    //METODOS PUBLICOS PROPIOS (SOBRECARGA DE METODOS DE ABSTRACTCRUDSERVICIO)
    public void agregar(String nombre, Double precio, String descripcion, int stock, 
        String imagen, boolean disponible, Categoria categoria)
    public Producto buscar (String nombre) // BUSCA POR NOMBRE

    //METODODS DE MENU PRODUCTOS (IMPLEMENTA INTERFACE MENU)
    public void mostrarMenu()
    public void ejecutarOpcion(int opcion)
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException

    //METODOS PRIVADOS
    private String solicitarTexto(String tipo)
    private int solicitarStock(String operacion)
    private Double solicitarPrecio()
    private Categoria solicitarCategoria() throws ListaVaciaException
    private void listarCategorias() throws ListaVaciaException

    //MENU MODIFICAR PRODUCTO (UTILIZA METODOS DE INTERFACE MENU)
    private int menuModificar()
    private void modificar(int opcion, Producto pr)

    //REPORTES
    public void reporteProductosSinStock() throws ListaVaciaException
    public void reporteProductosCategoriaEliminada()throws ListaVaciaException

    //VALIDACIONES
    private Boolean stringCorrecto(String string)
        
    //UTILIDADES
    private void encabezado (String texto)

 */
package servicios;

    import entities.Categoria;
    import entities.Producto;
    import excepciones.CantidadProductoMenorQueCeroException;
    import excepciones.FormatoInvalidoException;
    import excepciones.IdMenorQueCeroException;
    import excepciones.ListaVaciaException;
    import excepciones.PrecioMenorQueCeroException;
    import interfaces.Menu;
    import java.util.ArrayList;
    import java.util.Comparator;
    import java.util.Iterator;
    import java.util.List;
    import java.util.Objects;
    import java.util.Scanner;

public class ProductoServicio extends AbstractCrudServicio<Producto> implements Menu{
    
    Scanner leer = new Scanner(System.in);
    
    //ATRIBUTOS
    
    private final String[] opcionesMenu = {
        "PRODUCTOS",
        "Agregar",
        "Buscar por Id o Nombre",
        "Modificar",
        "Eliminar",
        "Listar",
        "Reporte de Productos sin stock",
        "Reporte de Productos con Categoría Eliminada"};
    
    private final CategoriaServicio cs;


    // Inyección por constructor
    public ProductoServicio(CategoriaServicio cs) {
        this.cs = cs;

    }
     
    
    // SOBRECARGA DE METODOS
    
        //Agregar Producto
    public void agregar(String nombre, Double precio, String descripcion, int stock, 
        String imagen, boolean disponible, Categoria categoria){
        boolean nombreCorrecto = stringCorrecto(nombre);
        boolean descripcionCorrecta = stringCorrecto(descripcion);
        boolean precioCorrecto = precio > 0.0;
        boolean stockCorrecto = stock > 0;
        boolean imagenCorrecta = stringCorrecto(imagen);
        boolean categoriaCorrecta = categoria != null;
        boolean productoYaExiste = buscar(nombre)!= null;
        boolean correcto = nombreCorrecto && descripcionCorrecta && precioCorrecto &&
                stockCorrecto && imagenCorrecta && categoriaCorrecta && !productoYaExiste;
        
        if (correcto) {
            Producto pr = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoria);
            agregar(pr);
            categoria.agregarProducto(pr);
            System.out.println("Producto creado con éxito");
            System.out.println(pr);
        }else{
            System.out.println("La operación falló. Verifique los datos ingresados");
        }

    }
    
            //Buscar por nombre
        public Producto buscar (String nombre){
        Producto encontrado = null;
        Iterator<Producto> it = elementos.iterator();
        while (it.hasNext()) {
            
            Producto pr = it.next();
            if (pr.getNombre().equalsIgnoreCase(nombre) && !pr.isEliminado()) {
                encontrado = pr;
                break; // dejamos de buscar
            }
        }
        return encontrado;
    }
        
    @Override
    public Long getId(Producto entidad) {
        return entidad.getId();
    }
        

    //MENU PRODUCTOS
    public void mostrarMenu() {
        //Utiliza método mostrarMenu de la interface Menu
        mostrarMenu(opcionesMenu);
    }
    
        //Sobrescribe método ejecutarOpcion de interface Menu
    @Override
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> { //Agregar Producto            

                try{
                    Categoria c = null;
                    do{
                        c = solicitarCategoria();
                    }while (c == null);
                    String nombre = solicitarTexto("nombre del producto");
                    String descripcion = solicitarTexto("descripción");
                    String imagen = solicitarTexto("imágen");   
                    Double precio = solicitarPrecio();
                    int stock = solicitarStock("Agregar");
                    boolean disponible = stock > 0;
                    agregar(nombre, precio, descripcion, stock, imagen, disponible, c);
                }catch (ListaVaciaException e){
                    System.out.println("La operación falló. No hay Categorías");
                }
            }
            case 2,3,4 -> {
                try{
                    menuBuscarModificarEliminar(opcion);
                }catch (ListaVaciaException e){
                    System.out.println("No hay Productos");
                }
            }   
            case 5 -> { //Listar productos
                encabezado("PRODUCTOS DISPONIBLES");
                listar();
            }
            case 6 -> {
                try{
                    reporteProductosSinStock();
                }catch (ListaVaciaException e){
                    System.out.println("No hay productos");
                }
                
            }   
            case 7 -> {
                try{
                    reporteProductosCategoriaEliminada();
                }catch (ListaVaciaException e){
                    System.out.println("No hay productos");
                }
                
            }   
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción inválida");
        }
        System.out.println();
    }
    
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException{
        Producto buscado = null;
        boolean ingresoCorrecto = false;
        encabezado("PRODUCTOS DISPONIBLES");
        listar();
        if (hayElementos()) {
            do{
                System.out.print("Ingrese el Id o el nombre del producto buscado: ");
                String valor = leer.nextLine();
                try{
                    if (!stringCorrecto(valor)) {
                        throw new FormatoInvalidoException();
                    }
                    try{
                        Long l = Long.valueOf(valor);
                        if (l <= 0) {
                            throw new IdMenorQueCeroException();
                        }
                        buscado = buscar(l);
                    }catch (NumberFormatException e) {
                        //Si no es un Long(id) y da NumberFormatException busca por nombre
                        buscado = buscar(valor);    
                    }
                    ingresoCorrecto = true;
                    
                    if (buscado != null) {
                        switch (opcion) {
                            case 2 -> System.out.println(buscado); //Buscar
                            //Muestra info del producto

                            case 3 -> { //Modificar
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
                                System.out.println("Se va a eliminar el siguiente producto: ");
                                System.out.println(buscado);
                                System.out.print("Desea eliminar? (S = Si): ");
                                if (leer.nextLine().equalsIgnoreCase("S")) {
                                    eliminar(buscado.getId());
                                    System.out.println("Producto eliminado con éxito");
                                }
                            }
                        }
                    }else{
                        System.out.println("El producto ingresado no existe en el listado");
                    }

                }catch (IdMenorQueCeroException | FormatoInvalidoException m){
                    System.out.println("EL VALOR INGRESADO NO ES UN N° DE ID VÁLIDO");

                }
            }while(!ingresoCorrecto);
            
        }else{
            throw new ListaVaciaException();
        }
        System.out.println();
        
    }
    
    //METODOS PRIVADOS
    
        //Entrega y valida un valor para un atributo tipo String y que 
        //además no sea numérico (no comienza con signo o cifra)
    private String solicitarTexto(String tipo){    
        boolean ingresoCorrecto = false;
        String texto;
            do{
                System.out.print("Ingrese " + tipo + ": ");             
                texto = leer.nextLine();
                try{
                    if (!stringCorrecto(texto) || !Character.isLetter(texto.charAt(0))) {
                        throw new FormatoInvalidoException();
                    }
                    ingresoCorrecto = true;
                }catch (FormatoInvalidoException e){
                    System.out.println("FORMATO INVÁLIDO");
                }
            }while(!ingresoCorrecto);
        return texto;
    }
    
        // Entrega una cantidad (int) para agregar o consumir del stock del producto
    private int solicitarStock(String operacion){
        boolean ingresoCorrecto = false;
        String tipo = "agregar";
        if (operacion.equalsIgnoreCase("C")) {
            tipo = "consumir";
        }
        int cantidad = 0;
        do{
            System.out.print("Ingrese la cantidad a " + tipo + ": ");
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
            
        }while(!ingresoCorrecto);
        
        return cantidad;
            
    }
    
        //Entrega un Double (precio)
    private Double solicitarPrecio (){
        boolean ingresoCorrecto = false;
        Double precio = 0.0;
        do{
            System.out.print("Ingrese el precio del producto: ");
            String valorPrecio = leer.nextLine();
            try{
                precio = Double.valueOf(valorPrecio);
                if (precio <= 0.0) {
                    throw new PrecioMenorQueCeroException();
                }
                ingresoCorrecto = true;    
            }catch (NumberFormatException | PrecioMenorQueCeroException e) {
                System.out.println("EL VALOR INGRESADO ES INCORRECTO");
            }
                
        }while(!ingresoCorrecto);
        
        return precio;
                
    }
    
        //Entrega una Categoría. Primero muestra una lista de categorías 
        //existentes para luego ingresar el id de la categoría elegida.
    private Categoria solicitarCategoria() throws ListaVaciaException{
        boolean ingresoCorrecto = false;
        Categoria c = null;
        encabezado("CATEGORIAS DISPONIBLES");
        listarCategorias();
        do{
            System.out.print("Ingrese el id de la categoría del producto: ");
            String valorCategoria = leer.nextLine();
            try{
                Long l = Long.valueOf(valorCategoria);
                if (l <= 0) {
                    throw new IdMenorQueCeroException();
                }
                c = cs.buscar(l);
                if (c != null) {
                    ingresoCorrecto = true;
                }
            }catch (NumberFormatException | IdMenorQueCeroException e) {
                System.out.println("EL VALOR INGRESADO NO ES UN N° DE ID VÁLIDO");
            }
            if (!ingresoCorrecto) {
                System.out.println("El id ingresado no existe en el listado.");
            }                           
        }while(!ingresoCorrecto);
        return c;
        
    }
    
        //Lista las categorías existentes
    private void listarCategorias()throws ListaVaciaException{
        if (cs.hayElementos()) {
            cs.listar();
        }else{
            throw new ListaVaciaException();
        }
        
    }
    
    //MENU MODIFICAR PRODUCTO
    private int menuModificar(){

        String[] opcionesMenuModificar = {
            "MODIFICAR PRODUCTO",
            "Nombre",
            "Precio",
            "Descripción",
            "Stock",
            "Imagen",
            "Disponible",
            "Categoría"};
        
        //Utiliza método getOpcion de la interface Menu
        return Menu.super.getOpcion(opcionesMenuModificar);
    }
    
        //Modifica segun opcion de menuModificar
    private void modificar(int opcion, Producto pr){
         switch (opcion){
            case 1 -> { //Nombre
                String nombreActual = pr.getNombre();
                String nombre = solicitarTexto("el nuevo nombre");

                boolean productoYaExiste = buscar(nombre)!= null && buscar(nombre) != pr;
                if (!productoYaExiste && !nombre.equals(nombreActual)) {
                    pr.setNombre(nombre);
                    System.out.println("Nombre modificado con éxito");
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 2 -> { //Precio
                Double precioActual = pr.getPrecio(), precio;
                precio = solicitarPrecio();
                if (precio > 0 && !Objects.equals(precio, precioActual)) {
                    pr.setPrecio(precio);
                    System.out.println("Precio modificado con éxito");
                }else{
                    System.out.println("La operación falló");
                }          
            }
            case 3 -> { //Descripción
                String descripcionActual = pr.getDescripcion();
                String descripcion = solicitarTexto("nueva descripción");

                if (!Objects.equals(descripcion, descripcionActual)){
                    pr.setDescripcion(descripcion);
                    System.out.println("Descripción modificada con éxito");
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 4 -> { //Stock
                int stockActual = pr.getStock(), stock;
                boolean exito = false;
                //permite agregar o consumir stok de acuerdo a la opción elegida
                //"A": agregar. La cantidad ingresada se sumará al stock existente
                //"C": consumir. La cantidad ingresada se restará al stock existente,
                //siempre y cuando el stock no quede con un valor negativo.
                System.out.print("Desea agregar (A) o consumir (C) stock actual = " + stockActual + "? ");
                String operacion = leer.nextLine().toUpperCase();
                if (operacion.equals("A") || operacion.equals("C")) {
                    stock = solicitarStock(operacion);
                    if (stock > 0) {            
                        switch (operacion){
                            case "A" -> { //Agrega la cantidad al stock actual
                                pr.setStock(stock);
                                exito = true;
                            }
                            case "C" -> exito = pr.consumirStock(stock);
                            //Descuenta la cantidad al stock actual, 
                            //si el stock resultante no es menor que cero.
                        }
                    }
                    if (exito) {
                        System.out.println("Stock modificado con éxito");
                    }else{
                        System.out.println("La operación falló. Stock insuficiente.");
                    }
                }     
            }
            case 5 -> { //Imagen
                String imagenActual = pr.getImagen();
                String imagen = solicitarTexto("nueva imágen");

                if (!Objects.equals(imagen, imagenActual)){
                    pr.setImagen(imagen);
                    System.out.println("Imágen modificada con éxito");
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 6 -> { //Disponible
                System.out.print("Producto " + pr.getNombre());
                if (pr.isDisponible()) {
                    System.out.print(" Disponible. Stock = " + pr.getStock() );
                }else{
                    System.out.print(" No disponible. Stock = " + pr.getStock());
                }
                System.out.print(" Desea Modificar? (S = Si): ");
                if (leer.nextLine().equalsIgnoreCase("S")) {
                    pr.setDisponible(!pr.isDisponible());
                    System.out.println("Disponible modificado con éxito");
                }
            }
            case 7 -> { //Categoria
                boolean exito = false;           
                Categoria actual = pr.getCategoria(), categoria = null;
                do{
                    try{
                        categoria = solicitarCategoria();
                        exito = true;
                    }catch (ListaVaciaException e){
                        System.out.println("No hay categorías.");
                    }
                }while (categoria == null && exito);
                if (categoria != null && !Objects.equals(categoria, actual)) {
                    pr.setCategoria(categoria);
                    System.out.println("Categoría modificada con éxito");
                }else{
                    System.out.println("La operación falló");
                }

            }
            case 0 -> System.out.println("Volviendo al menú Productos...");
            default -> System.out.println("Opción inválida");
        }
        
    }
    
    
    //REPORTES
        //Imprime lista de productos con stock = 0, suponiendo que se desee 
        //posteriormente agregar stock
    public void reporteProductosSinStock() throws ListaVaciaException{
        
        if (hayElementos()) {
            List<Producto> sinStock = new ArrayList<>();
            elementos.sort(Comparator.comparingLong(Producto::getId));
            
            for (Producto pr : elementos) {
            //Imprime productos si no están borrados y no tienen stock
                if (!pr.isEliminado() && pr.getStock() == 0) {
                    sinStock.add(pr);
                }
            }
            
            if (!sinStock.isEmpty()) {
                System.out.println(String.format("%40s", "").replace(' ', '='));
                System.out.println("PRODUCTOS SIN STOCK");
                System.out.println(String.format("%20s", "").replace(' ', '='));
                for (Producto pr : sinStock) {
                    System.out.println(pr);
                }
            }else{
                System.out.println("No hay Productos sin stock.");
            }
        }else{
            throw new ListaVaciaException();
        }
    }
    
        //Imprime lista de productos cuya categoría ha sido eliminada
        //suponiendo que se desee posteriormente modificar la misma. 
    public void reporteProductosCategoriaEliminada()throws ListaVaciaException{
        
        if (hayElementos()) {
            List<Producto> categoriaEliminada = new ArrayList<>();
            elementos.sort(Comparator.comparingLong(Producto::getId));
            
            for (Producto pr : elementos) {
            //Imprime productos si no están borrados y tienen Categoría
            //null ya que su categoría original fue eliminada
                if (!pr.isEliminado() && pr.getCategoria() == null) {
                    categoriaEliminada.add(pr);
                }
            }
            
            if (!categoriaEliminada.isEmpty()) {
                System.out.println(String.format("%40s", "").replace(' ', '='));
                System.out.println("PRODUCTOS CON CATEGORIA ELIMINADA");
                System.out.println(String.format("%20s", "").replace(' ', '='));
                for (Producto pr : categoriaEliminada) {
                    System.out.println(pr);
                }
            }else{
                System.out.println("No hay productos con categoría eliminada.");
            }
        }else{
            throw new ListaVaciaException();
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
}

>>>>>>> 48128ae6c331506e3cd40b2fafd754ac81f96133
    