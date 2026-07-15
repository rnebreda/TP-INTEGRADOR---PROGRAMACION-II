<<<<<<< HEAD
/*
CLASE ABSTRACT BASE
    Atributos
        - id: Long 
        - eliminado: boolean 
        - createdAt: LocalDateTime
    Métodos 
        + getters / setters 
        + toString(): String [override] 
 */
package entities;

    import java.time.LocalDateTime;


public abstract class Base {
    
    //ATRIBUTOS
    private Long id;
    private boolean eliminado = false;
    private final LocalDateTime createdAt;

    //CONSTRUCTOR

    public Base() {
        // asigna la fecha y hora actual al momento de crear el objeto
        this.createdAt = LocalDateTime.now();
        
    }
    
    public Base(Long id) {
        this();
        this.id = id; 
    }
    
    
    //GETTERS & SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    
    //METODO ABSTRACT (a implementar por las clases hijas)

    @Override
    public abstract String toString();
    
    
}
=======
/*
CLASE ABSTRACT BASE
    Atributos
        - id: Long 
        - eliminado: boolean 
        - createdAt: LocalDateTime
    Métodos 
        + getters / setters 
        + toString(): String [override] 
 */
package entities;

    import java.time.LocalDateTime;


public abstract class Base {
    
    //ATRIBUTOS
    private Long id;
    private boolean eliminado = false;
    private final LocalDateTime createdAt;

    //CONSTRUCTOR

    public Base() {
        // asigna la fecha y hora actual al momento de crear el objeto
        this.createdAt = LocalDateTime.now();
        
    }
    
    public Base(Long id) {
        this();
        this.id = id; 
    }
    
    
    //GETTERS & SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    
    //METODO ABSTRACT (a implementar por las clases hijas)

    @Override
    public abstract String toString();
    
    
}
>>>>>>> 48128ae6c331506e3cd40b2fafd754ac81f96133
