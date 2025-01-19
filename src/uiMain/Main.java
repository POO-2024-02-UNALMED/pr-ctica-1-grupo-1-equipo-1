package uiMain;
import java.time.LocalDateTime;
import gestorAplicacion.*;
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
                case 4:
                Pasajero pasajero1 = new Pasajero();
                Scanner sc = new Scanner(System.in);
                System.out.println("ingrese su numero de documento");
                int numId = sc.nextInt();
                System.out.println("ingrese el numero de la factura");
                int numfactura = sc.nextInt();
                System.out.println("La solicitud esta en proceso");
                System.out.println(pasajero1.solicitarReembolso(numId,numfactura, horaZero));
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