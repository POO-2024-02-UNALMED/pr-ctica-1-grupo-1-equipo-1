package uiMain;
import java.time.LocalDateTime;
import gestorAplicacion.*;
import java.util.scanner;


public class Main{
    public static void main(String[] args){
        //LocalDateTime horaZero = LocalDatetime.now();// Captura el tiempo al ejecutarse el programa
        Scanner scanner = new Scanner(System.in);



        // Testeo de funcionalidades.
        //boceto de funcionamiento del sistema
        // testing
        Pasajero pasajero1 = new pasajero();
        System.out.println("Escoja entre las opciones, ingrese el numero de la opciones que desee: \n1. Solicitud de Pasaje \n 2. blabla 3. Reembolso de tiquete");
        int valorOpcion= scanner.nextInt();
        switch (valorOpcion){
            case 1:
                break;
            case 2:
                break;
            case 3:
                Scannere sc = new Scanner(System.in);
                System.out.println("ingrese su numero de documento");
                int numId = sc.nextInt();
                System.out.println("ingrese el numero de la factura");
                int numfactura = sc.nextInt();
                System.out.println("La solicitud esta en proceso");
                System.out.println(pasajero1.solicitarReembolso(numId,numfactura),horaZero);
                sc.close();
                break;
        }

        scanner.close();
    }
}