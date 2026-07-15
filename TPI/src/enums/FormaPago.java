<<<<<<< HEAD
/*
ENUM FORMAPAGO
Valores 
    TARJETA 
    TRANSFERENCIA 
    EFECTIVO
Atributo
    - Abreviatura
        (Este atributo NO SOLICITADO EN LA CONSIGNA se utiliza en el método 
        imprimirReporte de la clase Usuario)
 */
package enums;


public enum FormaPago {
    
    TARJETA ("TC"), TRANSFERENCIA ("TR"), EFECTIVO ("EF");
    
    private final String abreviatura;

    //CONSTRUCTOR
    FormaPago(String abreviatura) {
        this.abreviatura = abreviatura;
    }
    
    //GETTER
    public String getAbreviatura() {
        return abreviatura;
    }
}
=======
/*
ENUM FORMAPAGO
Valores 
    TARJETA 
    TRANSFERENCIA 
    EFECTIVO
Atributo
    - Abreviatura
        (Este atributo NO SOLICITADO EN LA CONSIGNA se utiliza en el método 
        imprimirReporte de la clase Usuario)
 */
package enums;


public enum FormaPago {
    
    TARJETA ("TC"), TRANSFERENCIA ("TR"), EFECTIVO ("EF");
    
    private final String abreviatura;

    //CONSTRUCTOR
    FormaPago(String abreviatura) {
        this.abreviatura = abreviatura;
    }
    
    //GETTER
    public String getAbreviatura() {
        return abreviatura;
    }
}
>>>>>>> 48128ae6c331506e3cd40b2fafd754ac81f96133
