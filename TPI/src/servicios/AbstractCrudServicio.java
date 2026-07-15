<<<<<<< HEAD
/*
CLASE ABSTRACTA DE SERVICIO CON UN CRUD GENERICO
    A implementar por clasesServicios de clases heredadas de Base
    Implementa la interface genérica Crud

    //ATRIBUTOS
    protected List<T> elementos = new ArrayList<>();

    //METODOS
    public void agregar(T entidad)
    public T buscar(Long id)
    public void listar()
    public void eliminar(Long id)
    public void modificar(T entidad)
    protected boolean hayElementos()
    Long getId(T entidad);
 */

package servicios;

import entities.Base;
import interfaces.Crud;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;



public abstract class AbstractCrudServicio<T extends Base> implements Crud<T>{

    protected List<T> elementos = new ArrayList<>();

    @Override
    public void agregar(T entidad) {
        elementos.add(entidad);
    }

    @Override
    public T buscar(Long id) {
        if (hayElementos()) {
            for (T e : elementos) {
                if (Objects.equals(e.getId(), id) && !e.isEliminado()) { // ignora los borrados
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public void listar() {
        if (hayElementos()) {
            // Ordena por id y muestra en pantalla
            elementos.sort(Comparator.comparingLong(this::getId));
            for (T e : elementos) { // ignora los borrados
                if (!e.isEliminado()) {
                    System.out.println(e);
                }
            }
        }else{
            System.out.println("No hay elementos en la lista.");
        }
    }



    @Override
    public void eliminar(Long id) {
        if (hayElementos()) {
            for (T e : elementos) {
                if (e.getId() == id) {
                    e.setEliminado(true); // borrado soft
                    System.out.println("Elemento con id " + id + " marcado como eliminado.");
                    break;
                }
            }
        }
    }

    @Override
    public void modificar(T entidad) {
        if (hayElementos()) {
            for (int i = 0; i < elementos.size(); i++) {
                if (Objects.equals(elementos.get(i).getId(), entidad.getId())) {
                    elementos.set(i, entidad);
                    break;
                }
            }
        }
    }
    
    protected boolean hayElementos() {
        boolean hayNoEliminados = false;
        if (elementos != null && !elementos.isEmpty()) {
            for (T e : elementos) {
                if (!e.isEliminado()) {
                    hayNoEliminados = true;
                    break;
                }
            }
        }

        return hayNoEliminados;
    }
    
}
=======
/*
CLASE ABSTRACTA DE SERVICIO CON UN CRUD GENERICO
    A implementar por clasesServicios de clases heredadas de Base
    Implementa la interface genérica Crud

    //ATRIBUTOS
    protected List<T> elementos = new ArrayList<>();

    //METODOS
    public void agregar(T entidad)
    public T buscar(Long id)
    public void listar()
    public void eliminar(Long id)
    public void modificar(T entidad)
    protected boolean hayElementos()
    Long getId(T entidad);
 */

package servicios;

import entities.Base;
import interfaces.Crud;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;



public abstract class AbstractCrudServicio<T extends Base> implements Crud<T>{

    protected List<T> elementos = new ArrayList<>();

    @Override
    public void agregar(T entidad) {
        elementos.add(entidad);
    }

    @Override
    public T buscar(Long id) {
        if (hayElementos()) {
            for (T e : elementos) {
                if (Objects.equals(e.getId(), id) && !e.isEliminado()) { // ignora los borrados
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public void listar() {
        if (hayElementos()) {
            // Ordena por id y muestra en pantalla
            elementos.sort(Comparator.comparingLong(this::getId));
            for (T e : elementos) { // ignora los borrados
                if (!e.isEliminado()) {
                    System.out.println(e);
                }
            }
        }else{
            System.out.println("No hay elementos en la lista.");
        }
    }



    @Override
    public void eliminar(Long id) {
        if (hayElementos()) {
            for (T e : elementos) {
                if (e.getId() == id) {
                    e.setEliminado(true); // borrado soft
                    System.out.println("Elemento con id " + id + " marcado como eliminado.");
                    break;
                }
            }
        }
    }

    @Override
    public void modificar(T entidad) {
        if (hayElementos()) {
            for (int i = 0; i < elementos.size(); i++) {
                if (Objects.equals(elementos.get(i).getId(), entidad.getId())) {
                    elementos.set(i, entidad);
                    break;
                }
            }
        }
    }
    
    protected boolean hayElementos() {
        boolean hayNoEliminados = false;
        if (elementos != null && !elementos.isEmpty()) {
            for (T e : elementos) {
                if (!e.isEliminado()) {
                    hayNoEliminados = true;
                    break;
                }
            }
        }

        return hayNoEliminados;
    }
    
}
>>>>>>> 48128ae6c331506e3cd40b2fafd754ac81f96133
