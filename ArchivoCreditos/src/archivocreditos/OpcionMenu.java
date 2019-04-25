/**
 * OpcionMenu.java
 * Enumeración para las opciones del programa de consulta de crédito.
 */
package archivocreditos;

/**
 * Enumeración para las opciones del programa de consulta de crédito.
 * @author Ing. Ricardo Presilla
 * @version 1.0.
 */
public enum OpcionMenu {
    // declara el contenido del tipo enum
    SALDO_CERO(1),
    SALDO_CREDITO(2),
    SALDO_DEBITO(3),
    FIN(4);
    private final int valor; // opción actual del menú
    // constructor
    private OpcionMenu(int valor){
        this.valor = valor;
    }
}
