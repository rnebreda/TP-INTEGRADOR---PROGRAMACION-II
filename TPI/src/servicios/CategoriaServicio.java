/*
SERVICIOS PARA LA CLASE CATEGORIAS (COLLECTION + METODOS CRUD + MENU)

    //ATRIBUTOS DE CATEGORIA
    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList <>();

    //CONSTRUCTOR
    Categoria(String nombre, String descripcion);



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
    private final String[] opcionesMenu // opciones del menú CATEGORIAS

    //METODOS PUBLICOS PROPIOS (SOBRECARGA DE METODOS DE ABSTRACTCRUDSERVICIO)
    public void agregar(String nombre, String descripcion)
    public Categoria buscar (String nombre)// BUSCA POR NOMBRE

    //METODODS DE MENU CATEGORIAS (IMPLEMENTA INTERFACE MENU)
    public void mostrarMenu()
    public void ejecutarOpcion(int opcion)
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException

    //METODOS PRIVADOS
    private String solicitarTexto(String tipo)

    //MENU MODIFICAR CATEGORIA (UTILIZA METODOS DE INTERFACE MENU)
    private int menuModificar()
    private void modificar(int opcion, Categoria c)

    //REPORTES
    private void imprimirCategoria (Categoria c) throws ListaVaciaException

    //VALIDACIONES
    private Boolean stringCorrecto(String string)
        
    //UTILIDADES
    private void encabezado (String texto)
    

 */
package servicios;

    import entities.Categoria;
    import entities.Producto;
    import excepciones.FormatoInvalidoException;
    import excepciones.IdMenorQueCeroException;
    import excepciones.ListaVaciaException;
    import interfaces.Menu;
    import java.util.Iterator;
    import java.util.Scanner;


public class CategoriaServicio extends AbstractCrudServicio<Categoria> implements Menu{
    
    Scanner leer = new Scanner(System.in);
    
    //ATRIBUTOS
    
    private final String[] opcionesMenu = {
        "CATEGORIAS",
        "Agregar",
        "Buscar por Id o Nombre",
        "Modificar",
        "Eliminar",
        "Listar"};
    
    
    
    //SOBRECARGA DE METODOS
    
        //Agregar Categoría
    public void agregar(String nombre, String descripcion){
        boolean nombreCorrecto = stringCorrecto(nombre);
        boolean descripcionCorrecta = stringCorrecto(descripcion);
        boolean categoriaYaExiste = buscar(nombre)!= null;
        boolean correcto = nombreCorrecto && descripcionCorrecta && !categoriaYaExiste;
        
        if (correcto) {
            Categoria c = new Categoria(nombre, descripcion);
            //Utiliza método agregar de la clase abstract
            agregar(c);            
            System.out.println("Categoría creada con éxito");
            System.out.println(c);
        }else{
            System.out.println("La operación falló. Verifique los datos ingresados");
        }
    }
        
        //Buscar por nombre
    public Categoria buscar (String nombre){
        Categoria encontrada = null;
        Iterator<Categoria> it = elementos.iterator();
        while (it.hasNext()) {
            
            Categoria c = it.next();
            if (c.getNombre().equalsIgnoreCase(nombre) && !c.isEliminado()) {
                encontrada = c;
                break; // dejamos de buscar
            }
        }
        return encontrada;
    }     
        
    @Override
    public Long getId(Categoria entidad) {
        return entidad.getId();
    }
    

    
    //MENU CATEGORIAS
    public void mostrarMenu() {
        //Utiliza método mostrarMenu de la interface Menu
        mostrarMenu(opcionesMenu);
    }
    
        //Sobrescribe método ejecutarOpcion de interface Menu
    @Override
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> { //Agregar Categoría

                String nombre = solicitarTexto("nombre de la categoría");
                String descripcion = solicitarTexto("descripción");
                agregar(nombre, descripcion);

            }
            case 2,3,4 -> {
                try{
                    menuBuscarModificarEliminar(opcion);
                }catch (ListaVaciaException e){
                    System.out.println("No hay Categorías");
                }
            }   
            case 5 -> { //Listar Categorías
                encabezado("CATEGORIAS DISPONIBLES");
                listar();
            }
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción inválida");
        }
        System.out.println();
    }
    
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException{
        Categoria buscada = null;
        boolean ingresoCorrecto = false;
        encabezado("CATEGORIAS DISPONIBLES");
        listar();
        if (hayElementos()) {
            do{
                System.out.print("Ingrese el Id o el nombre de la categoría buscada: ");
                String valor = leer.nextLine();
                System.out.println();
                try{
                    if (!stringCorrecto(valor)) {
                        throw new FormatoInvalidoException();
                    }
                    try{
                        Long l = Long.valueOf(valor);
                        if (l <= 0) {
                            throw new IdMenorQueCeroException();
                        }
                        buscada = buscar(l);
                    }catch (NumberFormatException e) {
                    //Si no es un Long(id) y da NumberFormatException busca por nombre
                        buscada = buscar(valor);
                    }
                    ingresoCorrecto = true;
                    if (buscada != null) {
                        switch (opcion) {
                            case 2 -> { //Buscar
                                //Muestra categoría con lista de productos
                                try{
                                    imprimirCategoria(buscada);
                                } catch (ListaVaciaException e){
                                    System.out.println("No hay productos en la lista.");
                                }
                            }
                            case 3 -> { //Modificar
                                System.out.println("Modificando... ");
                                System.out.println(buscada);
                                System.out.print("Desea modificar? (S = Si): ");
                                boolean modificar = leer.nextLine().equalsIgnoreCase("S");
                                do{
                                    if (modificar) {
                                        modificar(menuModificar(), buscada);
                                        System.out.println(buscada);
                                        System.out.print("Continua modificando? (S = Si): ");
                                        modificar = leer.nextLine().equalsIgnoreCase("S");
                                    }
                                }while(modificar);
                                System.out.println();
                                System.out.println(buscada);
                            }
                            case 4 -> { //Eliminar
                                System.out.println("Se va a eliminar la siguiente categoría: ");
                                System.out.println(buscada);
                                System.out.print("Desea eliminar? (S = Si): ");
                                if (leer.nextLine().equalsIgnoreCase("S")) {
                                    buscada.vaciarListaProductos();
                                    eliminar(buscada.getId());
                                }
                            }
                        }
                    }else{
                        System.out.println("La categoría ingresada no existe en el listado");
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
    
    

    //MENU MODIFICAR CATEGORIA
    private int menuModificar(){
   
        String[] opcionesMenuModificar = {
            "MODIFICAR CATEGORIA",
            "Nombre",
            "Descripción"};
        //Utiliza método getOpcion de la interface Menu
        return Menu.super.getOpcion(opcionesMenuModificar);
    }
    
    //Modifica segun opcion de menuModificar
    private void modificar(int opcion, Categoria c) {
         switch (opcion){
            case 1 -> { //Nombre
                String nombreActual = c.getNombre();
                boolean ingresoCorrecto = false;
                do{
                    System.out.print("Ingrese el nuevo nombre: ");
                    String nombre = leer.nextLine();
                    try{
                        if (!stringCorrecto(nombre)) {
                            throw new FormatoInvalidoException();
                        }
                        ingresoCorrecto = true;              
                        boolean categoriaYaExiste = buscar(nombre)!= null && buscar(nombre) != c;
                        if (!categoriaYaExiste && !nombre.equals(nombreActual)) {
                            c.setNombre(nombre);
                            System.out.println("Nombre modificado con éxito");
                        }else{
                            System.out.println("La operación falló");
                        }
                    }catch(FormatoInvalidoException e){
                        System.out.println("EL NOMBRE INGRESADO ES INVÁLIDO");
                    }
                }while(!ingresoCorrecto);
            }
            case 2 -> { //Descripcion
                String descripcionActual = c.getDescripcion();
                boolean ingresoCorrecto = false;
                do{
                    System.out.print("Ingrese la nueva descripción: ");
                    String descripcion = leer.nextLine();
                    try{
                        if (!stringCorrecto(descripcion)) {
                            throw new FormatoInvalidoException();
                        }
                        ingresoCorrecto = true;
                        if (!descripcion.equals(descripcionActual)){
                            c.setDescripcion(descripcion);
                            System.out.println("Descripción modificada con éxito");
                        }else{
                            System.out.println("La operación falló");
                        }
                    }catch(FormatoInvalidoException e){
                        System.out.println("LA DESCRIPCIÓN INGRESADA ES INVÁLIDA");
                    }
                }while(!ingresoCorrecto);
            }
            case 0 -> System.out.println("Volviendo al menú Categorías...");
            default -> System.out.println("Opción inválida");
        }
        
    }
    
    

    //REPORTES
        //Imprime Categoría y productos de la misma
    private void imprimirCategoria (Categoria c) throws ListaVaciaException{
        boolean hayNoEliminados = false;
        if (c.getProductos() != null && !c.getProductos().isEmpty()) {
            for (Producto p : c.getProductos()) {
                if (!p.isEliminado()) {
                    hayNoEliminados = true;
                    break;
                }
            }
        }
        System.out.println(c);
        if (hayNoEliminados) {
            System.out.println("PRODUCTOS DE LA CATEGORIA");
            for (Producto pr : c.getProductos()) {
                //Imprime productos de la categoría si no estan borrados
                if (!pr.isEliminado()) {
                    System.out.println(pr);
                }
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
