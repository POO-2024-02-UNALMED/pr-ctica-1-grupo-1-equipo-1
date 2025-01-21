package baseDatos;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import gestorAplicacion.administracion.*;
import gestorAplicacion.operacion.*;
import gestorAplicacion.operacion.individuos.Chofer;
import uiMain.*;


public class EscritoLector {

    static File archivo = new File("");
    public static void main(String[] args) {

        /* Zona Para hacer la Serializacion Para sus Respectivos Casos de Pruebas
        *
        *   Instrucciones Para crear un Objeto Serializado
        *
        *
        *   // Paso 1: Creamos un objeto de la clase que queremos serializar
        *      En la Zona de Creacion de objetos
        *  ejm:
        *   Bus bus1= new EscritoLector();
        *
        *         Paso 2: Despues del Try en Zona de Escritura del objeto
        *              utilizamos el comando o.writeObject(bus1);
        *   
        *         Paso 3: Para Deserializarse en la Zona de Deserialización
        *           escribimos comando :
        *            Bus busr1 = (Bus) oi.readObject();
        *           Seguido de:
        *            System.out.println(busr1.toString());
        *
        *
        *
        */
        // ZONA DE CREACION DE OBJETOS


        Chofer p1 = new Chofer(1123);



        // Serialización (guardar objetos en el archivo)
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath()+
            "\\src\\baseDatos\\temp\\Objetos.txt"));
            
            ObjectOutputStream o = new ObjectOutputStream(f);




        // ZONA DE ESCRITURA DEL OBJETO
        // Escribir objectos a un Archivo
            o.writeObject(p1);




        FileInputStream fi = new FileInputStream(new File(archivo.getAbsolutePath()+
            "\\src\\baseDatos\\temp\\Objetos.txt"));
            
        ObjectInputStream oi = new ObjectInputStream(fi);

        
        // leer objetos

        Chofer pr1 = (Chofer) oi.readObject();


        System.out.println(pr1.toString());
        o.writeObject(p1);
            





        // Cerrar conexiones abiertas
        oi.close();
        fi.close();

        // Cerrar conexiones abiertas
        o.close();
        f.close();


        }catch (FileNotFoundException e) {
            System.out.println("No se encuentra archivo");
            }
            catch (IOException e) {
            System.out.println("Error flujo de inicialización");
            }
            catch (ClassNotFoundException e) {
            // TODO Auto‐generated catch block
            e.printStackTrace();
            }
        }   
}
