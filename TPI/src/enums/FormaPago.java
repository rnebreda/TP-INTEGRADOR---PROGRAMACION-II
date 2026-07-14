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
