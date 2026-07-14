/*
SERVICIOS PARA LA CLASE USUARIO (COLLECTION + METODOS CRUD + MENU)

    //ATRIBUTOS DE USUARIOS
    private String nombre;
    private String apellido;
    private String mail = "no posee";
    private String celular = "no posee";
    private String contrasenia = "no posee";
    private Rol rol;
    private List<Pedido> pedidos = new ArrayList<> ();

    //CONSTRUCTORES
    Usuario(String nombre, String apellido, Rol rol);
    Usuario(String nombre, String apellido, Rol rol, String mail, String celular);



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
    private final String[] opcionesMenu // opciones del menú USUARIOS
    private final RolServicio rs; // utiliza para acceder a menu para 
        seleccionar valor de Rol

    //METODOS PUBLICOS PROPIOS (SOBRECARGA DE METODOS DE ABSTRACTCRUDSERVICIO)
    public void agregar(String nombre, String apellido, Rol rol, String mail, String celular)
    public Usuario buscar (String nombre, String apellido) // BUSCA POR NOMBRE Y APELLIDO
    public Usuario buscar (String mail) // BUSCA POR MAIL

    //METODODS DE MENU PRODUCTOS (IMPLEMENTA INTERFACE MENU)
    public void mostrarMenu()
    public void ejecutarOpcion(int opcion)
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException

    //METODOS PRIVADOS
    private String solicitarTexto(String tipo)
    private String solicitarCelular()
    private String solicitarMail(){

    //MENU MODIFICAR USUARIO (UTILIZA METODOS DE INTERFACE MENU)
    private int menuModificar()
    private void modificar(int opcion, Usuario u)

    //VALIDACIONES
    private Boolean stringCorrecto(String string)
        
    //UTILIDADES
    private void encabezado (String texto)


 */
package servicios;

    import entities.Usuario;
    import enums.Rol;
    import excepciones.FormatoInvalidoException;
    import excepciones.IdMenorQueCeroException;
    import excepciones.ListaVaciaException;
    import interfaces.Menu;
    import java.util.Iterator;
    import java.util.Scanner;

public class UsuarioServicio extends AbstractCrudServicio<Usuario> implements Menu{
    
    Scanner leer = new Scanner(System.in);
    
    //ATRIBUTOS
    
    private final String[] opcionesMenu = {
    
        "USUARIOS",
        "Agregar",
        "Buscar por Id",
        "Modificar",
        "Eliminar",
        "Listar"};
    
    private final RolServicio rs;
    
        // Inyección por constructor
    public UsuarioServicio(RolServicio rs) {
        this.rs = rs;

    }
    
    
    
    // SOBRECARGA DE METODOS

        //Agregar usuario
    public void agregar(String nombre, String apellido, Rol rol, String mail, String celular){ 

        boolean nombreCorrecto = stringCorrecto(nombre);
        boolean apellidoCorrecto = stringCorrecto(apellido);
        boolean celularCorrecto = stringCorrecto(celular);
        boolean mailYaExiste = buscar(mail)!= null;
        boolean rolCorrecto = rol != null;
        boolean mailCorrecto = stringCorrecto(mail) && !mailYaExiste;
        boolean usuarioYaExiste = buscar(nombre, apellido)!= null;
        boolean correcto = nombreCorrecto && apellidoCorrecto && rolCorrecto 
                && celularCorrecto && mailCorrecto && !usuarioYaExiste;
        
        if (correcto) {
            Usuario u = new Usuario(nombre, apellido, rol, mail, celular);
            agregar(u);
            System.out.println("Usuario creado con éxito");
            System.out.println(u);
        }else{
            System.out.println("La operación falló. Verifique los datos ingresados");
        }
    }
        
        //Buscar por nombre y apellido
    public Usuario buscar (String nombre, String apellido){
        Usuario encontrada = null;
        Iterator<Usuario> it = elementos.iterator();
        while (it.hasNext()) {        
            Usuario u = it.next();
            if (!u.isEliminado() && u.getNombre().equalsIgnoreCase(nombre) 
                    && u.getApellido().equalsIgnoreCase(apellido)) {
                encontrada = u;
                break; // dejamos de buscar
            }
        }
        return encontrada;
    }
    
        //Buscar por mail
    public Usuario buscar (String mail){
        Usuario encontrada = null;
        Iterator<Usuario> it = elementos.iterator();
        while (it.hasNext()) {        
            Usuario u = it.next();
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                encontrada = u;
                break; // dejamos de buscar
            }
        }
        return encontrada;
    }   
        
    @Override
    public Long getId(Usuario entidad) {
        return entidad.getId();
    }
   
    

    //MENU USUARIOS
    public void mostrarMenu() {
        //Utiliza método mostrarMenu de la interface Menu
        mostrarMenu(opcionesMenu);
    }
    
        //Sobrescribe método ejecutarOpcion de interface Menu
    @Override
    public void ejecutarOpcion(int opcion) {

        switch (opcion) {
            case 1 -> { //Agregar usuario

                String nombre = solicitarTexto("nombre del usuario");
                String apellido = solicitarTexto("apellido");                
                
                String celular = null;
                do{
                    celular = solicitarCelular();
                }while(celular == null);
                
                
                String mail = null;
                do{
                    mail = solicitarMail();
                }while(mail == null);
                
                
                Rol rol = null;
                System.out.println("Ingrese el Rol del usuario: ");
                rol = rs.asignarRol(leer);
                if (rol != null) {
                    agregar(nombre, apellido, rol, mail, celular);
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 2,3,4 -> {
                try{
                    menuBuscarModificarEliminar(opcion);
                }catch (ListaVaciaException e){
                    System.out.println("No hay Usuarios");
                }
            }   
            case 5 -> { //Listar usuarios
                encabezado("USUARIOS DISPONIBLES");
                listar();
            }
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción inválida");
        }
        System.out.println();
    }
    
    private void menuBuscarModificarEliminar(int opcion)throws ListaVaciaException{
        Usuario buscado = null;
        boolean ingresoCorrecto = false;
        encabezado("USUARIOS DISPONIBLES");
        listar();
        if (hayElementos()) {
            do{
                System.out.print("Ingrese el Id del usuario buscado: ");
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
                            case 2 -> buscado.imprimirReporte(); //Buscar
                            //Muestra info del usuario con lista de pedidos

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
                                System.out.println("Se va a eliminar el siguiente usuario: ");
                                buscado.imprimirReporte();
                                System.out.print("Desea eliminar? (S = Si): ");
                                if (leer.nextLine().equalsIgnoreCase("S")) {
                                    buscado.vaciarListaPedidos();
                                    eliminar(buscado.getId());
                                    System.out.println("Usuario eliminado con éxito");
                                }
                            }               
                        }
                    }else{
                        throw new ListaVaciaException();
                    }
                                
                }catch (NumberFormatException | IdMenorQueCeroException e) {
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
    
        //Entrega un String con el número de celular.
        //Si es cero devuelve "no posee"
    private String solicitarCelular(){
        boolean ingresoCorrecto = false;
        Long nroCelular = 0L;
        do{
            try{
                System.out.print("Ingrese el N° de celular del usuario (0 = no posee): ");
                String celular = leer.nextLine();
                Boolean formatoValido = false;
                nroCelular = Long.valueOf(celular);
                formatoValido = nroCelular >= 0L && nroCelular < Math.pow(10, 15);
                if (!formatoValido) {
                    throw new FormatoInvalidoException();
                }
                ingresoCorrecto = true;

                if (nroCelular != 0){
                    return celular;

                } else{         
                    return "no posee";

                }
            }catch (NumberFormatException | FormatoInvalidoException e) {
                System.out.println("EL VALOR INGRESADO ES INCORRECTO");
            }
        }while (!ingresoCorrecto)   ;
        return null;
        
    }
    
        //Entrega un String con el mail del usuario
        //Valida que sea único en el listado y que el formato sea válido
        //(Para este trabajo, formato válido implica que tiene un "@" y es única,
        // y que no está ni al principio ni al final del mail ingresado)
        // Si se ingresa "0" implica que no posee mail
    private String solicitarMail(){
        boolean ingresoCorrecto = false;
        boolean encontrado = false;
        do{
            try{     
                System.out.print("Ingrese el mail del usuario (0 = no posee) : ");
                String mail = leer.nextLine();
                //Valida que solo contiene una "@" y que no está ni al principio ni al final
                boolean formatoValido = stringCorrecto(mail) 
                && (((mail.length() - mail.replace("@", "").length()) == 1 &&
                !mail.startsWith("@") && !mail.endsWith("@")) || mail.equals("0"));

                if (!formatoValido) {
                    throw new FormatoInvalidoException();
                }
                ingresoCorrecto = true;
                encontrado = buscar(mail) != null;
                if (!encontrado && !mail.equals("0")) {
                    return mail;
                }else{
                    if (mail.equals("0")) {
                        return "no posee";
                    }else{
                        System.out.println("El mail ingresado ya existe en el listado.");
                    }
                }
                
            }catch (FormatoInvalidoException e){
                System.out.println("EL FORMATO DEL MAIL INGRESADO ES INVÁLIDO");
            }
        }while(!ingresoCorrecto);
        return null;

    }

    
    
  //MENU MODIFICAR USUARIO
    private int menuModificar(){

        String[] opcionesMenuModificar = {
            "MODIFICAR USUARIO",
            "Nombre",
            "Apellido",
            "Rol",
            "Mail",
            "Celular"};
        
        //Utiliza método getOpcion de la interface Menu
        return Menu.super.getOpcion(opcionesMenuModificar);
    }
    
        //Modifica segun opcion de menuModificar
    private void modificar(int opcion, Usuario u){
         switch (opcion){
            case 1 -> { //Nombre
                String nombreActual = u.getNombre();
                String apellido = u.getApellido();
                String nombre = solicitarTexto("el nuevo nombre");

                boolean usuarioYaExiste = buscar(nombre, apellido)!= null && buscar(nombre, apellido) != u;
                if (!usuarioYaExiste && !nombre.equals(nombreActual)) {
                    u.setNombre(nombre);
                    System.out.println("Nombre modificado con éxito");
                }else{
                    System.out.println("La operación fallo");
                }
            }
            case 2 -> { //Apellido
                String nombre = u.getNombre();
                String apellidoActual = u.getApellido();
                String apellido = solicitarTexto("el nuevo apellido");

                boolean usuarioYaExiste = buscar(nombre, apellido)!= null && buscar(nombre, apellido) != u;
                if (!usuarioYaExiste && !apellido.equals(apellidoActual)) {
                    u.setApellido(apellido);
                    System.out.println("Apellido modificado con éxito");
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 3 -> { //Rol
                Rol rolActual = u.getRol();
                System.out.println("Ingrese el nuevo Rol: ");
                Rol rol = rs.asignarRol(leer);
                if (rol != null && !rol.equals(rolActual)) {
                    u.setRol(rol);
                    System.out.println("Rol modificado con éxito");
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 4 -> { //Mail
                String mailActual = u.getMail();
                String mail = null;
                do{
                    mail = solicitarMail();
                }while(mail == null);
                if (!mail.equals(mailActual)) {
                    u.setMail(mail);
                    System.out.println("Mail modificado con éxito");  
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 5 -> { //Celular
                String celularActual = u.getCelular();
                String celular = null;
                do{
                    celular = solicitarCelular();
                }while (celular == null);
                if (!celular.equals(celularActual)) {
                    u.setCelular(celular);
                    System.out.println("Celular modificado con éxito");  
                }else{
                    System.out.println("La operación falló");
                }
            }
            case 0 -> System.out.println("Volviendo al menú Usuarios...");
            default -> System.out.println("Opción inválida");
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
