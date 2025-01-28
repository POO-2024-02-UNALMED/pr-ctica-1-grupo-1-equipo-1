package baseDatos;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import gestorAplicacion.administracion.*;
import gestorAplicacion.administracion.Red.Parada;
import gestorAplicacion.operacion.individuos.Chofer;
import gestorAplicacion.operacion.logistica.Bus;

public class EscritoLector {

    static File archivo = new File("src/baseDatos/temp/Objetos.txt");
    // Igual que ruta7

    public static void main(String[] args) {
        // CREACIÓN DE OBJETOS
        Bus bus1 = new Bus("ABC-123", 40, Bus.PesoMaxEquipaje.MEDIO);
        Bus bus2 = new Bus("DEF-456", 50, Bus.PesoMaxEquipaje.PESADO);
        Bus bus3 = new Bus("GHI-789", 30, Bus.PesoMaxEquipaje.LIGERO);
        Bus bus4 = new Bus("JKL-012", 45, Bus.PesoMaxEquipaje.EXTRA_PESADO);
        Bus bus5 = new Bus("MNO-345", 55, Bus.PesoMaxEquipaje.MEDIO);
        Bus bus6 = new Bus("PQR-678", 35, Bus.PesoMaxEquipaje.PESADO);
        Bus bus7 = new Bus("STU-901", 50, Bus.PesoMaxEquipaje.LIGERO);
        Bus bus8 = new Bus("VWX-234", 40, Bus.PesoMaxEquipaje.EXTRA_PESADO);
        Bus bus9 = new Bus("YZA-567", 60, Bus.PesoMaxEquipaje.MEDIO);
        Bus bus10 = new Bus("BCD-890", 30, Bus.PesoMaxEquipaje.PESADO);
        Chofer chofer1 = new Chofer("Juan Pérez", 35, 123456789, 2500000);
        Chofer chofer2 = new Chofer("Ana García", 42, 987654321, 3000000);
        Chofer chofer3 = new Chofer("Pedro Rodríguez", 50, 567890123, 3500000);
        Chofer chofer4 = new Chofer("Luisa Martínez", 28, 432109876, 2000000);
        Chofer chofer5 = new Chofer("Carlos Sánchez", 48, 876543210, 4000000);
        Chofer chofer6 = new Chofer("María Gómez", 32, 321098765, 2800000);
        Chofer chofer7 = new Chofer("José Fernández", 55, 765432109, 4500000);
        Chofer chofer8 = new Chofer("Laura Torres", 25, 210987654, 1800000);
        Chofer chofer9 = new Chofer("David Ramírez", 40, 654321098, 3200000);
        Chofer chofer10 = new Chofer("Sofía Vargas", 38, 901234567, 2700000);
        Ruta ruta1 = new Ruta(Parada.BOGOTA, Parada.MEDELLIN, 400, 8, bus1, chofer1);
        Ruta ruta2 = new Ruta(Parada.MEDELLIN, Parada.CALI, 300, 6, bus2, chofer2);
        Ruta ruta3 = new Ruta(Parada.CALI, Parada.BARRANQUILLA, 500, 10, bus3, chofer3);
        Ruta ruta4 = new Ruta(Parada.BARRANQUILLA, Parada.VALLEDUPAR, 200, 4, bus4, chofer4);
        Ruta ruta5 = new Ruta(Parada.VALLEDUPAR, Parada.NEIVA, 350, 7, bus5, chofer5);
        Ruta ruta6 = new Ruta(Parada.NEIVA, Parada.BOGOTA, 250, 5, bus6, chofer6);
        Ruta ruta7 = new Ruta(Parada.BOGOTA, Parada.CALI, 450, 9, bus7, chofer7);
        Ruta ruta8 = new Ruta(Parada.MEDELLIN, Parada.BARRANQUILLA, 600, 12, bus8, chofer8);
        Ruta ruta9 = new Ruta(Parada.CALI, Parada.VALLEDUPAR, 380, 8, bus9, chofer9);
        Ruta ruta10 = new Ruta(Parada.BARRANQUILLA, Parada.NEIVA, 550, 11, bus10, chofer10);
        Ruta ruta11 = new Ruta(Parada.NEIVA, Parada.MEDELLIN, 650, 13, bus1, chofer1);
        Ruta ruta12 = new Ruta(Parada.CALI, Parada.NEIVA, 280, 6, bus2, chofer2);
        Ruta ruta13 = new Ruta(Parada.VALLEDUPAR, Parada.BOGOTA, 580, 12, bus3, chofer3);
        Ruta ruta14 = new Ruta(Parada.BARRANQUILLA, Parada.MEDELLIN, 700, 14, bus4, chofer4);
        Ruta ruta15 = new Ruta(Parada.BOGOTA, Parada.VALLEDUPAR, 480, 10, bus5, chofer5);
        Ruta ruta16 = new Ruta(Parada.MEDELLIN, Parada.NEIVA, 350, 7, bus6, chofer6);
        Ruta ruta17 = new Ruta(Parada.CALI, Parada.VALLEDUPAR, 420, 9, bus7, chofer7);
        Ruta ruta18 = new Ruta(Parada.NEIVA, Parada.BARRANQUILLA, 600, 12, bus8, chofer8);
        Ruta ruta19 = new Ruta(Parada.BOGOTA, Parada.BARRANQUILLA, 1000, 20, bus9, chofer9);
        Ruta ruta20 = new Ruta(Parada.MEDELLIN, Parada.VALLEDUPAR, 750, 15, bus10, chofer10);
        Ruta ruta21 = new Ruta(Parada.BOGOTA, Parada.MEDELLIN, 420, 9, bus6, chofer6); // Igual que ruta1
        Ruta ruta22 = new Ruta(Parada.MEDELLIN, Parada.CALI, 310, 7, bus7, chofer7); // Igual que ruta2
        Ruta ruta23 = new Ruta(Parada.CALI, Parada.BARRANQUILLA, 520, 11, bus8, chofer8); // Igual que ruta3
        Ruta ruta24 = new Ruta(Parada.VALLEDUPAR, Parada.NEIVA, 380, 8, bus9, chofer9); // Igual que ruta5
        Ruta ruta25 = new Ruta(Parada.NEIVA, Parada.BOGOTA, 270, 6, bus10, chofer10); // Igual que ruta6
        Ruta ruta26 = new Ruta(Parada.BARRANQUILLA, Parada.BOGOTA, 1050, 21, bus1, chofer1);
        Ruta ruta27 = new Ruta(Parada.NEIVA, Parada.VALLEDUPAR, 320, 7, bus2, chofer2);
        Ruta ruta28 = new Ruta(Parada.MEDELLIN, Parada.VALLEDUPAR, 700, 14, bus3, chofer3);
        Ruta ruta29 = new Ruta(Parada.CALI, Parada.NEIVA, 250, 5, bus4, chofer4);
        Ruta ruta30 = new Ruta(Parada.BOGOTA, Parada.CALI, 480, 10, bus5, chofer5);
        Chofer[] empleados1 = { chofer1, chofer2, chofer3 };
        Bus[] buses1 = { bus1, bus2, bus3 };
        ArrayList<Ruta> rutas1 = new ArrayList<>(Arrays.asList(ruta1, ruta2, ruta3));
        Empresa empresa1 = new Empresa("Empresa A", empleados1, buses1, rutas1, 10000000);

        Chofer[] empleados2 = { chofer4, chofer5, chofer6 };
        Bus[] buses2 = { bus4, bus5, bus6 };
        ArrayList<Ruta> rutas2 = new ArrayList<>(Arrays.asList(ruta4, ruta5, ruta6));
        Empresa empresa2 = new Empresa("Empresa B", empleados2, buses2, rutas2, 8000000);

        Chofer[] empleados3 = { chofer7, chofer8, chofer9, chofer10 };
        Bus[] buses3 = { bus7, bus8, bus9, bus10 };
        ArrayList<Ruta> rutas3 = new ArrayList<>(Arrays.asList(ruta7, ruta8, ruta9, ruta10));
        Empresa empresa3 = new Empresa("Empresa C", empleados3, buses3, rutas3, 12000000);
        // SERIALIZACIÓN
        try (FileOutputStream f = new FileOutputStream(archivo);
                ObjectOutputStream o = new ObjectOutputStream(f)) {

            o.writeObject(bus1);
            o.writeObject(bus2);
            o.writeObject(bus3);
            o.writeObject(bus4);
            o.writeObject(bus5);
            o.writeObject(bus6);
            o.writeObject(bus7);
            o.writeObject(bus8);
            o.writeObject(bus9);
            o.writeObject(bus10);

            // Escribir las rutas del 1 al 30
            o.writeObject(ruta1);
            o.writeObject(ruta2);
            o.writeObject(ruta3);
            o.writeObject(ruta4);
            o.writeObject(ruta5);
            o.writeObject(ruta6);
            o.writeObject(ruta7);
            o.writeObject(ruta8);
            o.writeObject(ruta9);
            o.writeObject(ruta10);
            o.writeObject(ruta11);
            o.writeObject(ruta12);
            o.writeObject(ruta13);
            o.writeObject(ruta14);
            o.writeObject(ruta15);
            o.writeObject(ruta16);
            o.writeObject(ruta17);
            o.writeObject(ruta18);
            o.writeObject(ruta19);
            o.writeObject(ruta20);
            o.writeObject(ruta21);
            o.writeObject(ruta22);
            o.writeObject(ruta23);
            o.writeObject(ruta24);
            o.writeObject(ruta25);
            o.writeObject(ruta26);
            o.writeObject(ruta27);
            o.writeObject(ruta28);
            o.writeObject(ruta29);
            o.writeObject(ruta30);

            // Escribir los choferes del 1 al 10
            o.writeObject(chofer1);
            o.writeObject(chofer2);
            o.writeObject(chofer3);
            o.writeObject(chofer4);
            o.writeObject(chofer5);
            o.writeObject(chofer6);
            o.writeObject(chofer7);
            o.writeObject(chofer8);
            o.writeObject(chofer9);
            o.writeObject(chofer10);

            // Escribir las empresas 1 al 3
            o.writeObject(empresa1);
            o.writeObject(empresa2);
            o.writeObject(empresa3);

        } catch (IOException e) {
            System.out.println("Error al serializar: " + e.getMessage());
        }

        // DESERIALIZACIÓN
        try (FileInputStream fi = new FileInputStream(archivo);
                ObjectInputStream oi = new ObjectInputStream(fi)) {

            Bus bus1Leido = (Bus) oi.readObject();
            Bus bus2Leido = (Bus) oi.readObject();
            Bus bus3Leido = (Bus) oi.readObject();
            Bus bus4Leido = (Bus) oi.readObject();
            Bus bus5Leido = (Bus) oi.readObject();
            Bus bus6Leido = (Bus) oi.readObject();
            Bus bus7Leido = (Bus) oi.readObject();
            Bus bus8Leido = (Bus) oi.readObject();
            Bus bus9Leido = (Bus) oi.readObject();
            Bus bus10Leido = (Bus) oi.readObject();

            // Leer las rutas del 1 al 30
            Ruta ruta1Leido = (Ruta) oi.readObject();
            Ruta ruta2Leido = (Ruta) oi.readObject();
            Ruta ruta3Leido = (Ruta) oi.readObject();
            Ruta ruta4Leido = (Ruta) oi.readObject();
            Ruta ruta5Leido = (Ruta) oi.readObject();
            Ruta ruta6Leido = (Ruta) oi.readObject();
            Ruta ruta7Leido = (Ruta) oi.readObject();
            Ruta ruta8Leido = (Ruta) oi.readObject();
            Ruta ruta9Leido = (Ruta) oi.readObject();
            Ruta ruta10Leido = (Ruta) oi.readObject();
            Ruta ruta11Leido = (Ruta) oi.readObject();
            Ruta ruta12Leido = (Ruta) oi.readObject();
            Ruta ruta13Leido = (Ruta) oi.readObject();
            Ruta ruta14Leido = (Ruta) oi.readObject();
            Ruta ruta15Leido = (Ruta) oi.readObject();
            Ruta ruta16Leido = (Ruta) oi.readObject();
            Ruta ruta17Leido = (Ruta) oi.readObject();
            Ruta ruta18Leido = (Ruta) oi.readObject();
            Ruta ruta19Leido = (Ruta) oi.readObject();
            Ruta ruta20Leido = (Ruta) oi.readObject();
            Ruta ruta21Leido = (Ruta) oi.readObject();
            Ruta ruta22Leido = (Ruta) oi.readObject();
            Ruta ruta23Leido = (Ruta) oi.readObject();
            Ruta ruta24Leido = (Ruta) oi.readObject();
            Ruta ruta25Leido = (Ruta) oi.readObject();
            Ruta ruta26Leido = (Ruta) oi.readObject();
            Ruta ruta27Leido = (Ruta) oi.readObject();
            Ruta ruta28Leido = (Ruta) oi.readObject();
            Ruta ruta29Leido = (Ruta) oi.readObject();
            Ruta ruta30Leido = (Ruta) oi.readObject();

            // Leer los choferes del 1 al 10
            Chofer chofer1Leido = (Chofer) oi.readObject();
            Chofer chofer2Leido = (Chofer) oi.readObject();
            Chofer chofer3Leido = (Chofer) oi.readObject();
            Chofer chofer4Leido = (Chofer) oi.readObject();
            Chofer chofer5Leido = (Chofer) oi.readObject();
            Chofer chofer6Leido = (Chofer) oi.readObject();
            Chofer chofer7Leido = (Chofer) oi.readObject();
            Chofer chofer8Leido = (Chofer) oi.readObject();
            Chofer chofer9Leido = (Chofer) oi.readObject();
            Chofer chofer10Leido = (Chofer) oi.readObject();

            // Leer las empresas 1 al 3
            Empresa empresa1Leido = (Empresa) oi.readObject();
            Empresa empresa2Leido = (Empresa) oi.readObject();
            Empresa empresa3Leido = (Empresa) oi.readObject();
            System.out.println(chofer10Leido.toString());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar: " + e.getMessage());
        }
    }
}