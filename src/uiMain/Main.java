package uiMain;
import java.time.LocalDateTime;
import gestorAplicacion.*;
import gestorAplicacion.administracion.Factura;

import java.util.ArrayList;
import java.util.Scanner;
import gestorAplicacion.operacion.individuos.*;;


public class Main{
    public static void main(String[] args){
        LocalDateTime horaZero = LocalDateTime.now();// Captura el tiempo al ejecutarse el programa
        Scanner scanner = new Scanner(System.in);

        
        // Mostrar un mensaje de bienvenida
        System.out.println("=============================================");
        System.out.println("        Terminal de Buses - Bienvenido       ");
        System.out.println("=============================================");
        
        // Menú principal
        while (true) {
            System.out.println("\nOpciones disponibles:");
            System.out.println("1. Consultar Rutas");
            System.out.println("2. Comprar Pasaje");
            System.out.println("3. Ver Pasajes Comprados");
            System.out.println("4. Solicitar Reembolso");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción (1-5): ");
            
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:
                    break;
                case 4: //Funcionalidad 3 Reembolso de Tiquete
                
                Pasajero pasajero1 = new Pasajero();
                Scanner sc = new Scanner(System.in);
                System.out.println("ingrese su numero de documento");
                int numId = sc.nextInt();
                System.out.println("ingrese el numero de la factura");
                int numfactura = sc.nextInt();
                System.out.println("La solicitud esta en proceso");
                // Primero Solicitaremos El reembolso
                ArrayList<Object> respuesta =pasajero1.solicitarReembolso(numId,numfactura, horaZero);
                // Se Hacen las primeras Comprobaciones superficiales
                String mensaje1 = (String) respuesta.get(0);
                System.out.println(mensaje1);
                // Si el Proceso sigue Se verificara si existe el Bus asociado
                if (mensaje1.equals("Su solicitud sigue en proceso, valoramos su paciencia y gracias por escojernos")) {
                    Factura factura1 =(Factura) respuesta.get(1);
                    String mensaje2=factura1.verificarBusAsociado();
                    System.out.println(mensaje2);
                    // Si el proceso Sigue Se verificara si existe una Ruta asociada
                    if (mensaje2.equals("Existe un Bus Asociado a la ruta de la factura, Su solicitud seguira en proceso")) {
                        String mensaje3 = factura1.verificarRutaAsociada();
                        System.out.println(mensaje3);
                    if(mensaje3.equals("El asiento liberado puede ser reservado nuevamente, Su reembolso sigue en proceso")){
                        String mensaje4 =pasajero1.EliminarPasajes();
                        System.out.println(mensaje4);
                    if(mensaje4.equals("Asiento quitado a " + pasajero1.getNombre() + " en el bus " + pasajero1.getFactura().getRutaElegida().getBusAsociado().getPlaca())){}
                    
                }
                    else{// Esto es en caso que Ocurra un Error
                        pasajero1.RevertirPasajes();
                    }
                }}
                sc.close();
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

    }
}