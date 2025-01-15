package gestorAplicacion.administracion;

import gestorAplicacion.operacion.individuos.Chofer;
import gestorAplicacion.operacion.individuos.Pasajero;
import gestorAplicacion.operacion.logistica.Bus;
//import gestorAplicacion.operacion.individuos.*;
import java.util.ArrayList; // Para crear la red de carreteras que tiene el terminal.
import java.util.Arrays; // Para ordenar arrays.
import java.util.Comparator;
//import java.util.List;

public class Empresa {
    private String nombre;
    private Chofer[] empleados;
    private Bus[] busesTotales;
    private ArrayList<Ruta> rutas;
    private int caja;

    // Atributo donde se guardará el grafo de la red de carreteras y las distancias
    // mínimas entre paradas.
    static final int totalParadas = Parada.values().length;
    public static final ArrayList<int[][]> redCarreteras = new ArrayList<int[][]>();
    public static final int[][] distanciaMinima = new int[totalParadas][totalParadas];
    static {
        /*
         * Creación de la red de carreteras:
         * Esta vendrá representada por un grafo.
         * Los vértices serán las paradas (Representadas por su ordinal).
         * Las aristas serán representadas por una matriz de enteros 2x2, cuyas
         * entradas:
         * [ a11 a12]
         * [ a21 a22]
         * Significan:
         * a11, a12 son las paradas,
         * a21 es la distancia entre las paradas (En kilómetros),
         * a22 es el tiempo de recorrido entre las paradas (En minutos).
         * 
         * Se supondrá que el tiempo y distancia entre paradas es la misma en ambas
         * direcciones de recorrido.
         * Téngase en cuenta que la enumeración de ciudades está dada por:
         * 1. CIUDAD1.
         * 2. CIUDAD2.
         * 3. CIUDAD3.
         * 4. CIUDAD4.
         * 5. CIUDAD5.
         * 6. CIUDAD6.
         * etc... (Aquí hay que enumerar todas las ciudades)
         * 
         * Finalmente se calculan todas las distancias mínimas entre paradas.
         */

        redCarreteras.add(new int[][] { { 1, 2 }, { 200, 60 } });
        redCarreteras.add(new int[][] { { 1, 3 }, { 300, 120 } });
        redCarreteras.add(new int[][] { { 1, 4 }, { 100, 85 } });
        redCarreteras.add(new int[][] { { 1, 5 }, { 1000, 300 } });
        redCarreteras.add(new int[][] { { 1, 6 }, { 50, 30 } });
        redCarreteras.add(new int[][] { { 2, 3 }, { 700, 360 } });
        redCarreteras.add(new int[][] { { 3, 4 }, { 700, 380 } });
        redCarreteras.add(new int[][] { { 4, 5 }, { 200, 120 } });
        redCarreteras.add(new int[][] { { 5, 6 }, { 400, 180 } });
        redCarreteras.add(new int[][] { { 6, 2 }, { 20, 15 } });

        // Hallando las distancias mínimas entre cada par de paradas con el algoritmo de
        // Floyd-Warshall.
        // Iniciando la matriz de pesos.
        for (int[][] arista : redCarreteras) {
            distanciaMinima[arista[0][0]][arista[0][1]] = Empresa.w(arista[1][0], arista[0][1]);
            distanciaMinima[arista[0][0]][arista[0][1]] = Empresa.w(arista[1][0], arista[0][1]);
        }
        // Definiendo distancia infinita entre puntos que no tiene una ruta directa.
        for(int i = 0; i < totalParadas; i++){
            for(int j = i + 1; j < totalParadas; j++){
                if(distanciaMinima[i][j] == 0){
                    distanciaMinima[i][j] = 100000; //Distancia infinita.
                }
            }
        }

        // Algoritmo Floyd-Warshall.
        for (int i = 0; i < totalParadas; i++) {
            for (int j = 0; j < totalParadas; j++) {
                for (int k = 0; k < totalParadas; k++) {
                    distanciaMinima[i][j] = Math.min(distanciaMinima[i][j],
                            distanciaMinima[i][k] + distanciaMinima[k][j]);
                }
            }
        }
    }

    // Métodos get-set
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nuevoNombre) {
        nombre = nuevoNombre;
    }

    public Chofer[] getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Chofer[] nuevosEmpleados) {
        empleados = nuevosEmpleados;
    }

    public Bus[] getBusesTotales() {
        return busesTotales;
    }

    public void setBusesTotales(Bus[] nuevosBusesTotales) {
        busesTotales = nuevosBusesTotales;
    }

    public ArrayList<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(ArrayList<Ruta> nuevasRutas) {
        rutas = nuevasRutas;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int nuevaCaja) {
        caja = nuevaCaja;
    }

    // Métodos de instancia

    // Todas las formas de contratar un chofer.
    public void contratar(int sueldo) {
        contratar(new Chofer(sueldo), sueldo, null);
    }

    public void contratar(int sueldo, ArrayList<int[]> horario) {
        contratar(new Chofer(sueldo), sueldo, horario);
    }

    public void contratar(Chofer chofer, int sueldo) {
        contratar(chofer, sueldo, null);
    }

    public void contratar(Chofer chofer, int sueldo, ArrayList<int[]> horario) {
        /*
         * Agrega a un chofer a la nómina.
         * 
         * Parámetros:
         * - chofer: Chofer,
         * Persona a contratar.
         * - sueldo: int,
         * Sueldo ofrecido para contratar.
         * - horario: ArrayList<int[]>,
         * Horario con el que va a iniciar.
         */

        // Como los empleados se almacenan en un array, se busca si está disponible un
        // espacio para incluirlo.
        Boolean hayPuesto = false;
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] == null) {
                // Se mira si la persona acepta el empleo.
                if (chofer.getSueldo() >= sueldo) {
                    // En caso de estar vinculado a una empresa existente, se le pide la renuncia.
                    if (chofer.getEmpresa() != null) {
                        chofer.getEmpresa().despedir(chofer);
                    }

                    // Se incluye al nuevo empleado en la nómina.
                    empleados[i] = chofer;
                    chofer.setEmpresa(this);
                    hayPuesto = true;
                }

                break;
            }
        }

        // En caso de no haber espacio en el array, se crea uno nuevo con un espacio
        // extra.
        if (!hayPuesto) {
            // Se crea un array temporal para almacenar a todos los empleados
            Chofer[] temp = new Chofer[empleados.length + 1];
            for (int i = 0; i < empleados.length; i++) {
                temp[i] = empleados[i];
            }

            // Se incluye el nuevo empleado.
            temp[empleados.length] = chofer;
            chofer.setEmpresa(this);

            // Se cambia el array anterior por el nuevo.
            empleados = temp;
        }
    }

    // Despedir un chofer.
    public void despedir(Chofer chofer) {
        /*
         * Quita al chofer indicado de la nómina.
         * 
         * Parámetros:
         * - chofer: Chofer,
         * Empleado a despedir.
         */

        // Buscando si el empleado está vinculado a la empresa y limpiando toda relación
        // con el mismo.
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] == chofer) {
                chofer.setSueldo(0);
                chofer.setCantidadHorasConducidas(0);
                chofer.setHorario(null);
                chofer.setEmpresa(null);
                empleados[i] = null;
            }
        }
    }

    public void asignarRuta(Bus bus, Ruta ruta) {
        bus.anadirRuta(ruta);
    }

    public void reportarFinanzas() {

    }

    // Algoritmo de Bellman-Ford para hacer la ruta más corta
    private static int w(int distancia, int tiempo) {
        /*
         * Devuelve el peso asociado a la arista de la red de carreteras.
         * 
         * Parámetros:
         * - distancia: int,
         * Distancia entre nodos.
         * - tiempo: int,
         * Tiempo de recorrido entre nodos.
         * 
         * Retorna:
         * - peso: float,
         * El peso asociado a la arista con {distancia, tiempo}.
         */

        int peso = distancia + 9 * tiempo;
        return peso;
    }

    public ArrayList<Parada> algoritmoBellmanFord(int verticeInicial, int verticeFinal) {
        /*
         * Devuelve la ruta más corta dada la función de pesos entre los vértices de
         * origen y llegada.
         * 
         * Parámetros:
         * - verticeInicial: int,
         * El vértice inicial de la ruta.
         * - verticeFinal: int,
         * El vértice final de la ruta.
         * 
         * Retorna:
         * - rutaOptima: Ruta,
         * La ruta que optimiza la distancia asociada a los pesos entre los vértices
         * inicial y final.
         */

        // Se establece un predecesor (Padres) para cada vértice en la ruta óptima.
        // Se establece la distancia asociada óptima desde el vértice de origen a cada
        // vértice.
        int numeroVertices = Parada.values().length;
        int[] padres = new int[numeroVertices];
        int[] recorrido = new int[numeroVertices];
        recorrido[verticeInicial] = 0;

        // Estableciendo la distancia infinita y que el padre es null (Un padre
        // inexistente será tomado como -1).
        for (int i = 0; i < numeroVertices; i++) {
            padres[i] = -1;
            recorrido[i] = 100000; // Distancia infinita.
        }

        // Implementación del algoritmo de Bell-Forman para hallar el predecesor y
        // distancia asociada óptima
        for (int i = 1; i < numeroVertices; i++) {
            for (int[][] arista : redCarreteras) {
                int vertice1 = arista[0][0];
                int vertice2 = arista[0][1];
                int peso = Empresa.w(arista[1][0], arista[1][1]);

                // Comparando si es mejor cambiar el camino hacia el vértice 2 pasando por el
                // vértice 1.
                if (recorrido[vertice2] > recorrido[vertice1] + peso) {
                    recorrido[vertice2] = recorrido[vertice1] + peso;
                    padres[vertice2] = vertice1;
                }

                // Comparando si es mejor cambiar el camino hacia el vértice 1 pasando por el
                // vértice 2.
                if (recorrido[vertice1] > recorrido[vertice2] + peso) {
                    recorrido[vertice1] = recorrido[vertice2] + peso;
                    padres[vertice1] = vertice2;
                }
            }
        }

        // Construcción de las paradas intermedias.
        ArrayList<Parada> paradas = new ArrayList<Parada>();
        int parada = verticeFinal;
        paradas.add(Parada.values()[parada]);
        while (padres[parada] > -1) {
            // Añadiendo al padre a la lista.
            paradas.add(0, Parada.values()[padres[parada]]);

            // Haciendo inducción.
            parada = padres[parada];
        }

        // Añadiendo el vértice de origen a la parada.
        paradas.add(0, Parada.values()[verticeInicial]);

        // Retorno de las paradas.
        return paradas;
    }

    // Implementación de la funcionalidad 4.
    // Creación de rutas con paradas muy concurridas.
    public float[] flujoPromedio(Boolean eje) {// Posiblemente no nos sirva a futuro
        /*
         * Calcula el promedio de personas por viaje desde una parada a otra.
         * 
         * Parámetros:
         * - eje: bool,
         * Determina si el promedio se calcula en las filas (False) o columnas (True).
         * 
         * Retorna:
         * - promedios: int[# Paradas][# Paradas],
         * Matriz de cantidad de personas que compran este viaje.
         * La entrada i, j representa el flujo desde la ciudad con ordinal i hasta la
         * ciudad con ordinal j.
         */

        //
        int[][] flujoDemanda = new int[totalParadas][totalParadas]; // Matriz de personas que realizan el viaje.
        int[][] flujoOferta = new int[totalParadas][totalParadas]; // Matriz de viajes totales realizados.
        ArrayList<Ruta> rutasCopia = new ArrayList<Ruta>(); // Contador de rutas ya analizadas.

        // Generando la copia del atributo rutas.
        for (Ruta ruta : rutas) {
            rutasCopia.add(ruta);
        }

        // Contando el número de personas que viajan y viajes realizados (En la
        // empresa).
        ArrayList<Factura> facturas = Contabilidad.getVentas(); // Las facturas llevan registro de esto.
        for (Factura factura : facturas) {
            // Definiendo el origen y destino de la ruta.
            Ruta rutaAsociada = factura.getRutaElegida();
            int origen = factura.getOrigen().ordinal();
            int destino = factura.getDestino().ordinal();

            // Viendo si la ruta fue realizada por esta empresa.
            if (rutas.contains(rutaAsociada)) {
                flujoDemanda[origen][destino] += factura.getAsientosAsignados();

                // Viendo si ya se tomó en cuenta esta ruta.
                if (rutasCopia.contains(rutaAsociada)) {
                    flujoOferta[origen][destino]++;
                    flujoOferta[destino][origen]++;
                    rutasCopia.remove(rutaAsociada);
                }
            }
        }

        // Definiendo los promedios de flujo entre paradas.
        float[] promedios = new float[totalParadas];
        if (eje) {
            for (int i = 0; i < totalParadas; i++) {
                // Calculando el flujo de la columna i.
                int sumaOferta = 0;
                int sumaDemanda = 0;
                for (int j = 0; j < totalParadas; j++) {
                    sumaOferta += flujoOferta[j][i];
                    sumaDemanda += flujoDemanda[j][i];
                }

                // Computando el promedio del flujo.
                if (sumaOferta != 0) {
                    promedios[i] = sumaDemanda / sumaOferta;
                }
            }
        } else {
            for (int i = 0; i < totalParadas; i++) {
                // Calculando el flujo de la fila i.
                int sumaOferta = 0;
                int sumaDemanda = 0;
                for (int j = 0; j < totalParadas; j++) {
                    sumaOferta += flujoOferta[i][j];
                    sumaDemanda += flujoDemanda[i][j];
                }

                // Computando el promedio del flujo.
                if (sumaOferta != 0) {
                    promedios[i] = sumaDemanda / sumaOferta;
                }
            }
        }

        return promedios;
    }

    public float[][] flujoPromedio() {
        /*
         * Calcula el promedio de personas por viaje desde una parada a otra.
         * 
         * Retorna:
         * - promedios: int[# Paradas][# Paradas],
         * Matriz de cantidad de personas que compran este viaje.
         * La entrada i, j representa el flujo desde la ciudad con ordinal i hasta la
         * ciudad con ordinal j.
         */

        // Definición de variables
        int[][] flujoDemanda = new int[totalParadas][totalParadas]; // Matriz de personas que realizan el viaje.
        int[][] flujoOferta = new int[totalParadas][totalParadas]; // Matriz de viajes totales realizados.
        ArrayList<Ruta> rutasCopia = new ArrayList<Ruta>(); // Contador de rutas ya analizadas.

        // Generando la copia del atributo rutas.
        for (Ruta ruta : rutas) {
            rutasCopia.add(ruta);
        }

        // Contando el número de personas que viajan y viajes realizados (En la
        // empresa).
        ArrayList<Factura> facturas = Contabilidad.getVentas(); // Las facturas llevan registro de esto.
        for (Factura factura : facturas) {
            // Definiendo el origen y destino de la ruta.
            Ruta rutaAsociada = factura.getRutaElegida();
            int origen = factura.getOrigen().ordinal();
            int destino = factura.getDestino().ordinal();

            // Viendo si la ruta fue realizada por esta empresa.
            if (rutas.contains(rutaAsociada)) {
                flujoDemanda[origen][destino] += factura.getAsientosAsignados();

                // Viendo si ya se tomó en cuenta esta ruta.
                if (rutasCopia.contains(rutaAsociada)) {
                    flujoOferta[origen][destino]++;
                    flujoOferta[destino][origen]++;
                    rutasCopia.remove(rutaAsociada);
                }
            }
        }

        // Definiendo los promedios de flujo entre paradas.
        float[][] promedios = new float[totalParadas][totalParadas];
        for (int i = 0; i < totalParadas; i++) {
            for (int j = 0; j < totalParadas; j++) {
                if (flujoOferta[i][j] != 0) {
                    promedios[i][j] = flujoDemanda[i][j] / flujoOferta[i][j];
                }
            }
        }

        return promedios;
    }

    public void funcionalidad4() {

    }

    public void funcionalidad4(int paradaOrigen, int paradaDestino, int numeroParadas, float factor) {

        // Calculando el promedio de personas que hacen los trayectos.
        int totalParadas = Parada.values().length;
        Integer[] tuplasParadas = new Integer[totalParadas * totalParadas];
        float[][] promedios = flujoPromedio();

        /*
         * Contruyendo un array que involucre todos los trayectos posibles
         * Aquí, la entrada i = totalParadas * q + r (Expresión con el algoritmo de la
         * división), nos indica
         * que es el trayecto desde la parada con ordinal (q) hacia la ciudad con
         * ordinal (r).
         */
        for (int i = 0; i < totalParadas * totalParadas; i++) {
            tuplasParadas[i] = i;
        }

        // Viendo solo los trayectos que se solicitan
        float[][] promediosSesgados = new float[totalParadas][totalParadas];
        for (int i = 0; i < totalParadas; i++) {
            for (int j = 0; j < totalParadas; j++) {
                // En caso de especificar una parada, solo se involucran los valores que tengan
                // esas paradas.
                // En caso de no especificar una parada, se tomará como -1 y verán todos los
                // posibles valores.
                if ((paradaOrigen == -1 || paradaOrigen == i) && (paradaDestino == -1 || paradaDestino == j)) {
                    promediosSesgados[i][j] = promedios[i][j];
                }
            }
        }

        // Comparador que indica cómo ordenar los trayectos más solicitados según
        // especificaciones de paradas.
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer n, Integer m) {
                // Hallando la relación entre los números y el trayecto que representan.
                int cocienteN = n / totalParadas;
                int residuoN = n % cocienteN;
                int cocienteM = m / totalParadas;
                int residuoM = m % cocienteM;

                // Encontrando el flujo de ese trayecto.
                float promedioN = promediosSesgados[cocienteN][residuoN];
                float promedioM = promediosSesgados[cocienteM][residuoM];

                if (promedioN - promedioM > 0) {
                    return 1;
                } else if (promedioN - promedioM < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        // Ordenando el array.
        Arrays.sort(tuplasParadas, comparator);

        // Tomando la ruta según el algoritmo de BellmanFord modificado.
        //ArrayList<Parada> paradas = algoritmoBellmanFord(paradaOrigen, paradaDestino);
    }

    public void funcionalidad4(String parada1, String parada2, int numeroParadas, float factor) {

    }

    public void optimizarEscalas(Pasajero pasajero) {
        for (Ruta ruta : rutas) {
            int pasajerosEnParada = ruta.contarPasajerosEnParada(ruta.getLugarFinal());
            if (pasajerosEnParada >= ruta.getBusAsociado().getCantidadAsientos() - 2) { // Umbral de minimo dos asientos
                                                                                        // libres
                ArrayList<Pasajero> listaEspera = new ArrayList<Pasajero>();
                for (Factura factura : Contabilidad.getVentas()) {
                    if (factura.getRutaElegida().equals(ruta)) {
                        Pasajero pasajeroEnEspera = pasajero;
                        if (pasajeroEnEspera.aceptarCambio()) {
                            listaEspera.add(pasajeroEnEspera);
                        }
                    }
                }
                double compensacion = Contabilidad.calcularCompensacion(listaEspera.size());
                System.out.println("Costo de compensación: " + compensacion);
                // Llamada a el meteod reasignarPasajeros para darles una ruta diferente
                reasignarPasajeros(listaEspera, ruta);
            }
        }
    }

    public void reasignarPasajeros(ArrayList<Pasajero> listaEspera, Ruta rutaOrigen) {
        for (Pasajero pasajero : listaEspera) {
            ArrayList<Ruta> rutasAlternativas = rutaOrigen.obtenerRutasAlternativas();
            boolean reasingnado = false;

            for (Ruta nuevaRuta : rutasAlternativas) {
                if (nuevaRuta.getBusAsociado().isDisponible(rutaOrigen.getFechaLlegada(),
                        rutaOrigen.getFechaSalida())) {
                    // Reasignacion de el pasajero a una nueva ruta
                    nuevaRuta.getBusAsociado().asignarPasajero(pasajero);
                    rutaOrigen.getBusAsociado().eliminarPasajero(pasajero);
                    System.out.println("Pasajero " + pasajero.getNombre() + " ha sido reasignado a la ruta "
                            + nuevaRuta.getIdRuta());
                    reasingnado = true;
                    break;
                }
            }
            if (!reasingnado) {
                System.out.println("No se encontró una ruta alternativa disponible para " + pasajero.getNombre());
            }
        }
    }
}