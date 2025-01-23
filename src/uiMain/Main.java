package uiMain;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import gestorAplicacion.administracion.*;
import gestorAplicacion.operacion.individuos.*;
import gestorAplicacion.operacion.logistica.*;

public class Main {
    public static final LocalDateTime horaZero = LocalDateTime.now(); // Captura el tiempo al ejecutarse el programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=============================================");
        System.out.println("        Terminal de Buses - Bienvenido       ");
        System.out.println("=============================================");

        int opcion = mostrarMenuPrincipal(scanner);
        ejecutarCaso(scanner, opcion);
    }

    private static int mostrarMenuPrincipal(Scanner scanner){
        System.out.println("\nOpciones disponibles:");
        System.out.println("1. Consultar Rutas");
        System.out.println("2. Comprar Pasaje");
        System.out.println("3. Ver Pasajes Comprados");
        System.out.println("4. Solicitar Reembolso");
        System.out.println("5. Panel Empresa");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción (1-6): ");

        int opcion = scanner.nextInt();
        scanner.nextLine();
        System.out.println("");
        return opcion;
    }

    private static void ejecutarCaso(Scanner scanner, int opcion){
        switch(opcion) {
            case 1:
                // Lógica para consultar rutas (pendiente de implementar)
                break;
            case 2:
                System.out.println("Bienvenido al sistema de compra de tiquetes");
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("ID: ");
                String id = scanner.nextLine();
                System.out.print("Edad: ");
                int edad = scanner.nextInt();
                scanner.nextLine();

                if (edad < 18) {
                    System.out.println("Debe registrar un acompañante adulto.");
                    System.out.print("Nombre acompañante: ");
                    String nombreAcomp = scanner.nextLine();
                    System.out.print("ID acompañante: ");
                    String idAcomp = scanner.nextLine();
                    System.out.print("Edad acompañante: ");
                    int edadAcomp = scanner.nextInt();
                    scanner.nextLine();
                    pasajero.registrarAcompanante(nombreAcomp, idAcomp, edadAcomp);
                }

                Pasajero pasajero = new Pasajero();
                System.out.print("Ingrese ciudad de origen: ");
                String lugarInicio = scanner.nextLine();
                System.out.print("Ingrese ciudad de destino: ");
                String lugarFinal = scanner.nextLine();

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
                    }
                    if(mensaje3.equals("El asiento liberado puede ser reservado nuevamente, Su reembolso sigue en proceso")){
                        //Sigue la ejecucion
                    }
                }
            case 5:
                System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                scanner.close();
                return;
            case 6:
                System.out.println("Panel Empresa");
                // Aquí se debería crear un panel de opciones, pero
                // no sé cuáles opciones extra hay.

                // Implementación de la funcionalidad 4.
                funcionalidad4(scanner);
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
                opcion = mostrarMenuPrincipal(scanner);
                ejecutarCaso(scanner, opcion);
                break;
        }
    }

    // Funcionalidad 4.
    private static int cantidadDigitos(int numero){
        // Devuelve la cantidad de dígitos de un número.
        int digitos = 0;
        while(numero > 0){
            numero /= 10;
            digitos++;
        }

        return digitos;
    }
    private static int[] escogerCiudades(Scanner scanner){
        /*
         * Panel para que el usuario escoja las ciudades de la nueva ruta, si desea.
         * 
         * Parámetros:
         *      - scanner: Scanner,
         *          Scanner que muestra los objetos en pantalla.
         * 
         * Retorna:
         *      - paradas: int[],
         *          Ordinales de las paradas de inicio y final de la ruta.
         */

        // Título
        System.out.println("");
        System.out.println("=============================================");
        System.out.println("            Ciudades disponibles             ");
        System.out.println("=============================================");
        System.out.println("");

        // Mostrando las ciudades de forma estética.
        int digitos = cantidadDigitos(Red.totalParadas);
        String vacio = "";
        for(int i = 0; i < Red.totalParadas; i++){
            /*
             * Calculando el espacio vacío necesario para que la lista se muestre de la forma:
             * 1.   A...
             * ...
             * 10.  A...
             * ...
             * 100. A...
             */
            for(int j = 0; j < digitos - cantidadDigitos(i); j++){
                vacio += " ";
            }

            System.out.print(i + ". ");
            System.out.print(vacio);
            System.out.println(Red.Parada(i));
        }

        // Dejando que el usuario escoja la opción que le convenga.
        System.out.println("Selección de ciudades (Si no quiere seleccionar, introduzca -1 como opción)");
        System.out.print("Seleccione la ciudad origen: ");
        int ordinalParadaOrigen = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Seleccione la ciudad destino: ");
        int ordinalParadaDestino = scanner.nextInt();
        scanner.nextLine();

        // Análisis de errores.
        if(ordinalParadaOrigen < -1 || ordinalParadaOrigen > Red.totalParadas){
            System.out.print("No existe esa ciudad para escoger como origen. " +
            "Como castigo tiene que volver a escoger ambas ciudades.");
            return escogerCiudades(scanner);
        }
        else if(ordinalParadaDestino < -1 || ordinalParadaDestino > Red.totalParadas){
            System.out.print("No existe esa ciudad para escoger como destino. " +
            "Como castigo tiene que volver a escoger ambas ciudades.");
            return escogerCiudades(scanner);
        }
        else if(ordinalParadaOrigen == ordinalParadaDestino){
            System.out.print("No puede seleccionar la misma ciudad como origen y destino. " +
            "Como castigo tiene que volver a escoger ambas ciudades.");
            return escogerCiudades(scanner);
        }

        return new int[] {ordinalParadaOrigen, ordinalParadaDestino};
    }

    private static Empresa escogerEmpresa(Scanner scanner){
        /*
         * Panel para que el usuario decida si quiere escoger las paradas.
         * 
         * Parámetros:
         *      - scanner: Scanner,
         *          Scanner que muestra los objetos en pantalla.
         */

        // Construir una lista con todas las empresas existentes.
        Empresa[] empresas = new Empresa[0];

        // Título
        System.out.println("¿Para cuál empresa quiere hacer el análisis?");
        System.out.println("");
        System.out.println("=============================================");
        System.out.println("            Empresas disponibles             ");
        System.out.println("=============================================");
        System.out.println("");

        // Mostrando las empresas a escoger de manera estética.
        int digitos = cantidadDigitos(Red.totalParadas);
        String vacio = "";
        for(int i = 0; i < empresas.length; i++){
            /*
             * Calculando el espacio vacío necesario para que la lista se muestre de la forma:
             * 1.   A...
             * ...
             * 10.  A...
             * ...
             * 100. A...
             */
            for(int j = 0; j < digitos - cantidadDigitos(i); j++){
                vacio += " ";
            }

            System.out.print(i + ". ");
            System.out.print(vacio);
            System.out.println(empresas[i].getNombre());
        }

        // Dejando que el usuario escoja la empresa.
        System.out.print("Empresa: ");
        int numeroEmpresa = scanner.nextInt();
        scanner.nextLine();

        // Análisis de error.
        if(numeroEmpresa < 0 || numeroEmpresa > empresas.length){
            System.out.print("No existe esa empresa. Escoja otra.");
            return escogerEmpresa(scanner);
        }
        
        return empresas[numeroEmpresa];
    }

    private static int[] quiereEscogerParadas(Empresa empresa, Scanner scanner){
        /*
         * Panel para que el usuario decida si quiere escoger las paradas.
         * Por default va a escoger las paradas con mayor demanda en la empresa.
         * 
         * Parámetros:
         *      - empresa: Empresa,
         *          Empresa a la cual se le van a escoger las paradas a añadir ruta.
         *      - scanner: Scanner,
         *          Scanner que muestra los objetos en pantalla.
         */

        // Dejando que el usuario escoja la opción que le convenga.
        System.out.println("¿Desea escoger las paradas de origen o destino de la nueva ruta?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        // Paradas escogidas.
        int[] ordinalesParadas = new int[2];

        // Ejecutando la opción elegida.
        int opcion = scanner.nextInt();
        scanner.nextLine();
        if(opcion == 1){
            ordinalesParadas = escogerCiudades(scanner);
        }
        else if(opcion == 2){
            System.out.println("Entendido. Entonces se va a hacer la nueva ruta escogiendo" +
                               " el origen y destino que más demanda tiene.");

            ordinalesParadas = new int[] {-1, -1};
        }
        else{
            System.out.println("Opción no válida. Intente nuevamente. >:(");
            return quiereEscogerParadas(empresa, scanner);
        }

        // Calculando los flujos.
        float[][] promedios = empresa.flujoPromedio();
        float[][] promediosSesgados = empresa.promediosSesgados(promedios, ordinalesParadas[0], ordinalesParadas[1]);
        Integer[] ordenados = Empresa.ordenar(promediosSesgados, true);

        // Mostrando en orden los flujos obtenidos (Primeros 10).
        int ubicacion, columna, fila;
        for(int i = 0; i < 10; i++){
            ubicacion = ordenados[i];
            fila = ubicacion / Red.totalParadas;
            columna = ubicacion % Red.totalParadas;
            if(promediosSesgados[fila][columna] > 0){
                System.out.println("Demada del trayecto con ciudad origen <" +
                                    Red.Parada(fila).toString() +
                                    "> y con ciudad destino <" +
                                    Red.Parada(columna).toString() +
                                    "> es: " + promediosSesgados[fila][columna]);
            }
        }

        // Hallando las paradas más populares.
        fila = ordenados[0] / Red.totalParadas;
        columna = ordenados[0] % Red.totalParadas;
        System.out.println("Más demandada: " + Red.Parada(fila).toString() +
                           " --> " + Red.Parada(columna).toString());

        return new int[] {fila, columna};
    }
    
    private static float[] ajustarParadas(Empresa empresa, int paradaOrigen, int paradaDestino, Scanner scanner){
        /*
         * Calcula el trayecto con parada origen -> parada destino, tal que
         * se cumple (Si se puede) el número de paradas deseadas y un factor de crecimiento
         * (Medido con base a la ruta óptima dada por el algoritmo de Bellman-Ford).
         * 
         * Parámetros:
         *      - empresa: Empresa,
         *          Empresa a la cual se le hallará la ruta.
         *      - paradaOrigen: int,
         *          Ordinal de la primera parada para la ruta.
         *      - paradaDestino: int,
         *          Ordinal de la última parada para la ruta.
         *      - scanner: Scanner,
         *          Scanner que muestra los objetos en pantalla.
         * 
         * Retorna:
         *      - paradasReales: int[],
         *          Ordinales de las paradas en el trayecto que cumplen
         *          (En la medida de lo posible) los requisitos.
         */

        // Hallando la ruta óptima.
        int[] ordinalesTrayecto = Red.algoritmoBellmanFord(paradaOrigen, paradaDestino);

        // Mostrando la ruta óptima.
        System.err.println("");
        System.err.println("El trayecto dado por el algoritmo Bellman-Ford es:");
        for(int i = 0; i < ordinalesTrayecto.length - 1; i++){
            System.out.print(Red.Parada(ordinalesTrayecto[i]).toString() +  "-->");
        }
        System.err.println(Red.Parada(ordinalesTrayecto[ordinalesTrayecto.length - 1]).toString());

        System.out.print("Número de paradas para la ruta (Mínimo 2): ");
        int numeroParadas = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Porcentaje máximo en lo cual puede aumentar respecto la ruta óptima: ");
        float factor = 1 + scanner.nextInt() / 100;
        scanner.nextLine();

        // Verificación de errores.
        if(factor < 1){
            System.out.println("Con este factor, la ruta se va a quedar igual" +
                               " en caso de tener que añadir paradas, ¿Desea continua?");
            System.out.println("1. Sí");
            System.out.println("2. No");
            int continuar = scanner.nextInt();
            scanner.nextLine();
            if(continuar == 2){
                return ajustarParadas(empresa, paradaOrigen, paradaDestino, scanner);
            }
            else if(continuar != 1){
                System.out.println("Como castigo tiene que volver a escoger el porcentaje y número de paradas.");
                return ajustarParadas(empresa, paradaOrigen, paradaDestino, scanner);
            }
        }
        if(numeroParadas < 2){
            System.out.println("Como castigo tiene que volver a escoger el porcentaje y número de paradas.");
            return ajustarParadas(empresa, paradaOrigen, paradaDestino, scanner);
        }

        //int[] paradasReales = empresa.ajustarParadas(paradaOrigen, paradaDestino, numeroParadas, factor);

        // Ajuste de la cantidad de paradas.
        float[][] promedios = empresa.flujoPromedio();
        if(numeroParadas < ordinalesTrayecto.length){
            System.out.println("Como se necesita reducir el número de paradas, " +
                               "se va a calcular cuántas personas se bajan en la " +
                               "parada desde el origen, y se van a ir quitando en" +
                               " orden descendente de estos valores.");

            /* 
             * Se buscará una ruta que maximice la cantidad de personas que usarán la ruta.
             * Para esto se verá las paradas con mayor cantidad de personas que realizan el viaje
             * parada inicial -> parada, para cada parada.
            */
            float[] salientes = new float[ordinalesTrayecto.length - 2];
            for(int i = 0; i < ordinalesTrayecto.length - 2; i++){
                int ordinalActual = ordinalesTrayecto[i + 1];
                salientes[i] = promedios[ordinalesTrayecto[0]][ordinalActual];
            }

            // Hallando el orden de la cantidad de personas que se bajan en esa parada.
            Integer[] concurrencia = Empresa.ordenar(salientes, true);

            // Mostrando las paradas en orden de cantidad de salida.
            for(int i = 0; i < concurrencia.length; i++){
                System.out.println(Red.Parada(ordinalesTrayecto[concurrencia[i]]).toString() +
                                   " tiene " + salientes[i] +
                                   " personas saliendo en promedio desde " +
                                   Red.Parada(ordinalesTrayecto[0]).toString());
            }
        }
        else if(numeroParadas > ordinalesTrayecto.length){
            System.out.println("Como se necesita aumentar el número de paradas, " +
                               "se va a calcular qué tantas personas se subirían o bajarían " +
                               "en promedio para cada parada no añadida en el trayecto si " +
                               "esta fuera añadida. Luego se irán incluyendo para completar " +
                               "las paradas pedidas. Y por último se irán quitando o añadiendo " +
                               "si el conjunto de paradas añadidas aumenta más del porcentaje " +
                               "especificado.");
            int longitud = ordinalesTrayecto.length;
            int primeraParada = ordinalesTrayecto[0];
            int ultimaParada = ordinalesTrayecto[longitud - 1];

            // Viendo la distancia del trayecto.
            int recorridoTotal = Red.distancias[primeraParada][ultimaParada];

            // Corrección de errores.
            if(numeroParadas > Red.totalParadas){
                numeroParadas = Red.totalParadas;
            }

            // Viendo las paradas que están y las que no en la ruta.
            int[] enRuta = new int[longitud];
            int[] noEnRuta = new int[Red.totalParadas - longitud];

            // Enlistando las que aparecen en la ruta,
            for(int i = 0; i < longitud; i++){
                enRuta[i] = ordinalesTrayecto[i];
            }

            // Enlistando las que no aparecen en la ruta.
            Arrays.sort(enRuta);
            int desplazamiento = 0;
            for(int i = 0; i < Red.totalParadas; i++){
                if(i < enRuta[desplazamiento]){noEnRuta[i - desplazamiento] = i;}
                else{desplazamiento++;}
            }

            // Viendo los flujos entrantes desde paradas en la ruta hasta paradas que no están en la ruta.
            float[] rutaANoRuta = new float[noEnRuta.length];
            int[] separacion;
            for(int i = 0; i < noEnRuta.length; i++){
                separacion = Red.posicion(ordinalesTrayecto, noEnRuta[i]);
                for(int j = 0; j < enRuta.length; j++){
                    // Viendo si la parada en la ruta está antes o después de la parada que no está en la ruta.
                    if(enRuta[j] <= separacion[0]){
                        // Si está antes, importa es cuántos se bajan en la parada que no está en la ruta.
                        rutaANoRuta[i] += promedios[enRuta[j]][noEnRuta[i]];
                    }
                    else if(enRuta[j] > separacion[0]){
                        // Si está después, importa es cuántos se suben en la parada que no está en la ruta.
                        rutaANoRuta[i] += promedios[noEnRuta[i]][enRuta[j]];
                    }
                }

                System.out.println(Red.Parada(noEnRuta[i]).toString() + " es pedido por " +
                                   rutaANoRuta[i] + " personas en promedio.");
            }

            // Visualizando la contribución individual en distancia de cada parada a añadir.
            int[] contribucionIndividual = new int[noEnRuta.length];
            int[] posicion; // Para ver la posición que debería ocupar.
            int contribucion; // Distancia que contribuye.
            int paradaAnterior, paradaPosterior; // Paradas donde se encuentra ensandwichado.
            for(int i = 0; i < noEnRuta.length; i++){
                posicion = Red.posicion(ordinalesTrayecto, noEnRuta[i]);
                paradaAnterior = ordinalesTrayecto[posicion[0] + 1];
                paradaPosterior = ordinalesTrayecto[posicion[1]];
                contribucion = Red.distancias[paradaAnterior][noEnRuta[i]] +
                               Red.distancias[paradaPosterior][noEnRuta[i]] -
                               Red.distancias[paradaAnterior][paradaPosterior];
                contribucionIndividual[i] = contribucion;

                System.out.println("Al ubicar a " + Red.Parada(noEnRuta[i]).toString() +
                                   " entre las ciudades " + Red.Parada(paradaAnterior).toString() +
                                   "-" + Red.Parada(paradaPosterior).toString() +
                                   " esta añade una distancia de " + contribucion);
            }

            // Añadiendo las rutas faltates en orden de flujo y viendo su aporte de distancia.
            int cantidadFaltante = numeroParadas - longitud;
            Integer[] paradasEnOrden = Empresa.ordenar(rutaANoRuta, true);
            int nuevaParada = 0;
            int[] nuevoTrayecto = new int[0];
            int[] aporte = new int[cantidadFaltante];
            for(int i = 0; i < cantidadFaltante; i++){
                nuevaParada = noEnRuta[paradasEnOrden[i]];
                nuevoTrayecto = Red.agregarParada(ordinalesTrayecto, nuevaParada);
                aporte[i] = contribucionIndividual[nuevaParada];
            }

            // Viendo si se cumple que no se sobrepasa la longitud máxima deseada.
            int nuevoRecorridoTotal = Red.longitud(nuevoTrayecto);
            int desfase = 0;
            Integer[] ordenDeAporte = new Integer[0];
            int paradaAEliminar; // Parada a eliminar en el siguiente paso.
            while((nuevoRecorridoTotal > (recorridoTotal * factor)) &&
                  (desfase < paradasEnOrden.length - cantidadFaltante)){
                // Eliminando la parada que más distancia individual contribuye.
                ordenDeAporte = Empresa.ordenar(aporte, true);
                paradaAEliminar = paradasEnOrden[desfase + ordenDeAporte[0]];
                nuevoTrayecto = Red.eliminarParada(nuevoTrayecto, paradaAEliminar);

                // Añadiendo la siguiente parada a analizar.
                nuevaParada = noEnRuta[paradasEnOrden[cantidadFaltante + desfase]];
                nuevoTrayecto = Red.agregarParada(nuevoTrayecto, nuevaParada);

                // Viendo la siguiente iteración.
                aporte[ordenDeAporte[0]] = contribucionIndividual[nuevaParada];
                nuevoRecorridoTotal = Red.longitud(nuevoTrayecto);
                desfase++;
            }

            if(desfase >= paradasEnOrden.length - cantidadFaltante){
                int cuentaRegresiva = 0;
                while((nuevoRecorridoTotal > (recorridoTotal * factor)) &&
                      (cuentaRegresiva < cantidadFaltante)){
                    // Eliminando progresivamente las paradas.
                    paradaAEliminar = paradasEnOrden[desfase + ordenDeAporte[cuentaRegresiva]];
                    nuevoTrayecto = Red.eliminarParada(nuevoTrayecto, paradaAEliminar);
                    
                    // Viendo la siguiente iteración.
                    nuevoRecorridoTotal = Red.longitud(nuevoTrayecto);
                    cuentaRegresiva++;
                }
            }
            
            if(numeroParadas > nuevoTrayecto.length){
                System.out.println("Lo sentimos, pero el porcentaje no permitió alcanzar el número de paradas.");
            }
        }

        System.out.println("Con esto, se pudo ajustar ");
        

        return new float[] {numeroParadas, factor};
    }

    private static void funcionalidad4(Scanner scanner){
        System.out.println("Crear una nueva Ruta");

        // Tomando la elección
        Empresa empresaEscogida = escogerEmpresa(scanner);
        int[] paradasEscogidas = quiereEscogerParadas(empresaEscogida, scanner);
        float[] a = ajustarParadas(empresaEscogida, paradasEscogidas[0], paradasEscogidas[1], scanner);
    }
}