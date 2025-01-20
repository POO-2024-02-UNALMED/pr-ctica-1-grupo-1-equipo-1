package uiMain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import gestorAplicacion.administracion.Factura;
import gestorAplicacion.operacion.individuos.Pasajero;
import gestorAplicacion.operacion.logistica.Ruta;

public class Main {
    public static void main(String[] args) {
        LocalDateTime horaZero = LocalDateTime.now(); // Captura el tiempo al ejecutarse el programa
        Scanner scanner = new Scanner(System.in);

        System.out.println("=============================================");
        System.out.println("        Terminal de Buses - Bienvenido       ");
        System.out.println("=============================================");

        while (true) {
            System.out.println("\nOpciones disponibles:");
            System.out.println("1. Consultar Rutas");
            System.out.println("2. Comprar Pasaje");
            System.out.println("3. Ver Pasajes Comprados");
            System.out.println("4. Solicitar Reembolso");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción (1-5): ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    // Lógica para consultar rutas (pendiente de implementar)
                    break;
                case 2:
                    Pasajero pasajero = new Pasajero();
                    System.out.print("Ingrese ciudad de origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Ingrese ciudad de destino: ");
                    String destino = scanner.nextLine();

                    List<Ruta> rutasDisponibles = Ruta.filtrarRutas(lugarInicio, lugarFinal);
                    if (rutasDisponibles.isEmpty()) {
                        System.out.println("No hay rutas disponibles entre " + lugarInicio + " y " + lugarFinal);
                        break;
                    }

                    System.out.println("Rutas disponibles:");
                    for (int i = 0; i < rutasDisponibles.size(); i++) {
                        System.out.println((i + 1) + ". " + rutasDisponibles.get(i));
                    }

                    System.out.print("Seleccione la opción de ruta (1-" + rutasDisponibles.size() + "): ");
                    int opcionRuta = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionRuta < 1 || opcionRuta > rutasDisponibles.size()) {
                        System.out.println("Opción inválida");
                        break;
                    }

                    Ruta rutaSeleccionada = rutasDisponibles.get(opcionRuta - 1);

                    System.out.print("Seleccione el tipo de asiento (1 para Estándar, 2 para VIP): ");
                    int tipoAsiento = scanner.nextInt();
                    scanner.nextLine();

                    pasajero.comprarTiquete(rutaSeleccionada, tipoAsiento);
                    break;
                case 3:
                    // Lógica para ver pasajes comprados (pendiente de implementar)
                    break;
                case 4:
                    System.out.print("Ingrese su número de documento: ");
                    int numId = scanner.nextInt();
                    System.out.print("Ingrese el número de la factura: ");
                    int numFactura = scanner.nextInt();
                    System.out.println("La solicitud está en proceso...");

                    Pasajero pasajero1 = new Pasajero();
                    ArrayList<Object> respuesta = pasajero1.solicitarReembolso(numId, numFactura, horaZero);
                    System.out.println(respuesta.get(0));
                    if (respuesta.size() > 1 && respuesta.get(1) instanceof Factura) {
                        Factura factura1 = (Factura) respuesta.get(1);
                        System.out.println(factura1.verificarBusAsociado());
                        System.out.println(factura1.verificarRutaAsociada());
                    }
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
