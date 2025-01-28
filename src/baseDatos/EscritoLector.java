package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import gestorAplicacion.administracion.*;
import gestorAplicacion.administracion.Red.Parada;
import gestorAplicacion.operacion.individuos.Chofer;
import gestorAplicacion.operacion.logistica.Bus;
import uiMain.*;

public class EscritoLector {

    static File archivo = new File("");

    public static void main(String[] args) {

        /*
         * Zona Para hacer la Serializacion Para sus Respectivos Casos de Pruebas
         *
         * Instrucciones Para crear un Objeto Serializado
         *
         *
         * // Paso 1: Creamos un objeto de la clase que queremos serializar
         * En la Zona de Creacion de objetos
         * ejm:
         * Bus bus1= new EscritoLector();
         *
         * Paso 2: Despues del Try en Zona de Escritura del objeto
         * utilizamos el comando o.writeObject(bus1);
         * 
         * Paso 3: Para Deserializarse en la Zona de Deserialización
         * escribimos comando :
         * Bus busr1 = (Bus) oi.readObject();
         * Seguido de:
         * System.out.println(busr1.toString());
         *
         *
         *
         */
        // ZONA DE CREACION DE OBJETOS

        Bus bus1 = new Bus("XYZ123", 40, Bus.PesoMaxEquipaje.MEDIO);
        Bus bus2 = new Bus("ABC789", 50, Bus.PesoMaxEquipaje.PESADO);
        Bus bus3 = new Bus("DEF456", 60, Bus.PesoMaxEquipaje.LIGERO);
        Bus bus4 = new Bus("GHI987", 30, Bus.PesoMaxEquipaje.MEDIO);
        Bus bus5 = new Bus("JKL098", 40, Bus.PesoMaxEquipaje.PESADO);
        Bus bus6 = new Bus("MNO654", 50, Bus.PesoMaxEquipaje.LIGERO);
        Bus bus7 = new Bus("PQR321", 40, Bus.PesoMaxEquipaje.PESADO);
        Bus bus8 = new Bus("STU210", 30, Bus.PesoMaxEquipaje.MEDIO);
        Bus bus9 = new Bus("VWX654", 40, Bus.PesoMaxEquipaje.MEDIO);
        Bus bus10 = new Bus("YZA123", 40, Bus.PesoMaxEquipaje.PESADO);

        // Create some Chofer objects
        Chofer chofer1 = new Chofer("Juan Perez", 30, 12345, 1000000);
        Chofer chofer2 = new Chofer("Maria Rodriguez", 35, 67890, 1200000);
        Chofer chofer3 = new Chofer("Pedro Garcia", 40, 98765, 1500000);
        Chofer chofer4 = new Chofer("Luis Martinez", 45, 34567, 1800000);
        Chofer chofer5 = new Chofer("Ana Gonzalez", 50, 23456, 2000000);
        Chofer chofer6 = new Chofer("Jose Maria", 55, 123456, 2200000);
        Chofer chofer7 = new Chofer("Marco Fernandez", 60, 45678, 2500000);
        Chofer chofer8 = new Chofer("Sofia Lopez", 65, 78901, 2800000);
        Chofer chofer9 = new Chofer("Jose Antonio", 70, 89012, 3000000);
        Chofer chofer10 = new Chofer("Natalia Fernandez", 75, 56789, 3200000);

        // Create some Empresa objects
        for (int[][] carretera : Red.carreteras) {
            Red.Parada origen = Red.Parada(carretera[0][0]);
            Red.Parada destino = Red.Parada(carretera[0][1]);
            int distancia = carretera[1][0];
            int tiempo = carretera[1][1];
            LocalDateTime[] dates = Main.generateDateTime(tiempo);
            LocalDateTime dateStart = dates[0];
            LocalDateTime dateEnd = dates[1];
            Ruta nuevaRuta = new Ruta(bus1, dateStart, dateEnd, origen, destino);
            Ruta.addRutas(nuevaRuta);
            
        }

        // Serialización (guardar objetos en el archivo)
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath() +
                    "\\src\\baseDatos\\temp\\Objetos.txt"));

            ObjectOutputStream o = new ObjectOutputStream(f);

            // ZONA DE ESCRITURA DEL OBJETO
            // Escribir objectos a un Archivo
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

            FileInputStream fi = new FileInputStream(new File(archivo.getAbsolutePath() +
                    "\\src\\baseDatos\\temp\\Objetos.txt"));

            ObjectInputStream oi = new ObjectInputStream(fi);

            // leer objetos

            Chofer pr1 = (Chofer) oi.readObject();

            System.out.println(pr1.toString());
            o.writeObject(chofer1);

            // Cerrar conexiones abiertas
            oi.close();
            fi.close();

            // Cerrar conexiones abiertas
            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra archivo");
        } catch (IOException e) {
            System.out.println("Error flujo de inicialización");
        } catch (ClassNotFoundException e) {
            // TODO Auto‐generated catch block
            e.printStackTrace();
        }
    }
}
