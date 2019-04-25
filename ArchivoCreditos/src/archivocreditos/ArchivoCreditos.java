/**
 * Este programa lee un archivo secuencialmente y muestra su
 * contenido con base en el tipo de cuenta que solicita el usuario
 * (saldo con crédito, saldo con débito o saldo de cero).
 */
package archivocreditos;

import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Este programa lee un archivo secuencialmente y muestra su
 * contenido con base en el tipo de cuenta que solicita el usuario
 * (saldo con crédito, saldo con débito o saldo de cero).
 * @author Ing. Ricardo Presilla
 * @version 1.0.
 */
public class ArchivoCreditos {
    private final static OpcionMenu[] opciones = OpcionMenu.values();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // obtiene la solicitud del usuario (saldo de cero, con crédito o con débito)
        OpcionMenu tipoCuenta = obtenerSolicitud();
        while (tipoCuenta != OpcionMenu.FIN){
            switch (tipoCuenta){
                case SALDO_CERO:
                    System.out.printf("%nCuentas con saldos de cero:%n");
                    break;
                case SALDO_CREDITO:
                    System.out.printf("%nCuentas con saldos con credito:%n");
                    break;
                case SALDO_DEBITO:
                    System.out.printf("%nCuentas con saldos con debito:%n");
                    break;
            }
            leerRegistros(tipoCuenta);
            tipoCuenta = obtenerSolicitud(); // obtiene la solicitud del usuario
        }
    }
    /** obtiene la solicitud del usuario. **/
    private static OpcionMenu obtenerSolicitud(){
        int solicitud = 4;
        // muestra opciones de solicitud
        System.out.printf("%nEscriba solicitud%n%s%n%s%n%s%n%s%n",
        " 1 - Lista de cuentas con saldos de cero",
        " 2 - Lista de cuentas con saldos con credito",
        " 3 - Lista de cuentas con saldos con debito",
        " 4 - Terminar programa");
        try{
            Scanner entrada = new Scanner(System.in);
            do // recibe solicitud del usuario
            {
            System.out.printf("%n? ");
            solicitud = entrada.nextInt();
            } while ((solicitud < 1) || (solicitud > 4));
        }catch (NoSuchElementException noSuchElementException){
            System.err.println("Entrada invalida. Terminando.");
        }
        return opciones[solicitud - 1]; // devuelve valor de enum para la opción
    }
    /** Lee los registros del archivo y muestra sólo los registros del tipo 
     * apropiado.
     * @param tipoCuenta    Tipo OpcionMenu.
     */
    private static void leerRegistros(OpcionMenu tipoCuenta){
    // abre el archivo y procesa el contenido
        try (Scanner entrada = new Scanner(Paths.get("clientes.txt"))){
            while (entrada.hasNext()){ // más datos a leer
                int numeroCuenta = entrada.nextInt();
                String primerNombre = entrada.next();
                String apellidoPaterno = entrada.next();
                double saldo = entrada.nextDouble();
                // si el tipo de cuenta es apropiado, muestra el registro
                if (debeMostrar(tipoCuenta, saldo))
                    System.out.printf("%-10d%-12s%-12s%10.2f%n", numeroCuenta,
                        primerNombre, apellidoPaterno, saldo);
                else
                    entrada.nextLine(); // descarta el resto del registro actual
            }
        }catch (NoSuchElementException | IllegalStateException | IOException e){
            System.err.println("Error al procesar el archivo. Terminando.");
            System.exit(1);
        }
    } // fin del método leerRegistros
    /** Usa el tipo de registro para determinar si el registro debe mostrarse.
     * @param tipoCuenta    Tipo OpcionMenu.
     * @param saldo         Tipo double.
     */
    private static boolean debeMostrar(OpcionMenu tipoCuenta, double saldo){
        if ((tipoCuenta == OpcionMenu.SALDO_CREDITO) && (saldo < 0))
            return true;
        else if ((tipoCuenta == OpcionMenu.SALDO_DEBITO) && (saldo > 0))
            return true;
        else if ((tipoCuenta == OpcionMenu.SALDO_CERO) && (saldo == 0))
            return true;
        return false;
    }    
}
