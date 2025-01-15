package gestorAplicacion.administracion;
import gestorAplicacion.operacion.individuos.Chofer;
import gestorAplicacion.operacion.logistica.Bus;
import java.util.ArrayList; // Para crear la red de carreteras que tiene el terminal.
import java.util.Arrays; // Para ordenar arrays.
import java.util.Comparator;

public class Empresa{
    private String nombre;
    private Chofer[] empleados;
    private Bus[] busesTotales;
    private ArrayList<Ruta> rutas;
    private int caja;

    // Atributo donde se guardará el grafo de la red de carreteras
    static final int totalParadas = Parada.values().length;
    private static ArrayList<int[][]> redCarreteras;
    static{
        /* Creación de la red de carreteras:
         * Esta vendrá representada por un grafo.
         * Los vértices serán las paradas (Representadas por su ordinal).
         * Las aristas serán representadas por una matriz de enteros 2x2, cuyas entradas:
         * [ a11 a12]
         * [ a21 a22]
         * Significan:
         *      a11, a12 son las paradas,
         *      a21 es la distancia entre las paradas (En kilómetros),
         *      a22 es el tiempo de recorrido entre las paradas (En minutos).
         * 
         * Se supondrá que el tiempo y distancia entre paradas es la misma en ambas direcciones de recorrido.
         * Téngase en cuenta que la enumeración de ciudades está dada por:
         *      1. CIUDAD1.
         *      2. CIUDAD2.
         *      3. CIUDAD3.
         *      4. CIUDAD4.
         *      5. CIUDAD5.
         *      6. CIUDAD6.
         *      etc... (Aquí hay que enumerar todas las ciudades)
        */

        redCarreteras = new ArrayList<>();
        redCarreteras.add(new int[][] {{1, 2}, {200, 60}});
        redCarreteras.add(new int[][] {{1, 3}, {300, 120}});
        redCarreteras.add(new int[][] {{1, 4}, {100, 85}});
        redCarreteras.add(new int[][] {{1, 5}, {1000, 300}});
        redCarreteras.add(new int[][] {{1, 6}, {50, 30}});
        redCarreteras.add(new int[][] {{2, 3}, {700, 360}});
        redCarreteras.add(new int[][] {{3, 4}, {700, 380}});
        redCarreteras.add(new int[][] {{4, 5}, {200, 120}});
        redCarreteras.add(new int[][] {{5, 6}, {400, 180}});
        redCarreteras.add(new int[][] {{6, 2}, {20, 15}});
    }

    // Métodos get-set
    public static ArrayList<int[][]> getRedCarreteras(){
        return redCarreteras;
    }

    public static void setRedCarreteras(ArrayList<int[][]> nuevaRedCarreteras){
        redCarreteras = nuevaRedCarreteras;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nuevoNombre){
        nombre = nuevoNombre;
    }

    public Chofer[] getEmpleados(){
        return empleados;
    }

    public void setEmpleados(Chofer[] nuevosEmpleados){
        empleados = nuevosEmpleados;
    }

    public Bus[] getBusesTotales(){
        return busesTotales;
    }

    public void setBusesTotales(Bus[] nuevosBusesTotales){
        busesTotales = nuevosBusesTotales;
    }

    public ArrayList<Ruta> getRutas(){
        return rutas;
    }

    public void setRutas(ArrayList<Ruta> nuevasRutas){
        rutas = nuevasRutas;
    }

    public int getCaja(){
        return caja;
    }

    public void setCaja(int nuevaCaja){
        caja = nuevaCaja;
    }

    // Métodos de instancia

    // Todas las formas de contratar un chofer.
    public void contratarChofer(int sueldo){
        contratarChofer(new Chofer(sueldo), sueldo, null);
    }

    public void contratarChofer(int sueldo, ArrayList<int[]> horario){
        contratarChofer(new Chofer(sueldo), sueldo, horario);
    }

    public void contratarChofer(Chofer chofer, int sueldo){
        contratarChofer(chofer, sueldo, null);
    }

    public void contratarChofer(Chofer chofer, int sueldo, ArrayList<int[]> horario){
        Boolean hayPuesto = false;
        for(int i = 0; i < empleados.length; i++){
            if(empleados[i] == null){
                if(chofer.getSueldo() >= sueldo){
                    empleados[i] = chofer;
                    chofer.setEmpresa(this);
                    hayPuesto = true;
                }

                break;
            }
        }

        if(!hayPuesto){
            Chofer[] temp = new Chofer[empleados.length + 1];
            for(int i = 0; i < empleados.length; i++){
                temp[i] = empleados[i];
            }

            temp[empleados.length] = chofer;
            chofer.setEmpresa(this);
        }
    }

    public void asignarRuta(Bus bus, Ruta ruta){
        bus.anadirRuta(ruta);
    }

    public void reportarFinanzas(){

    }

    public void contratarEmpleado(){
        
    }

    // Algoritmo de Bellman-Ford para hacer la ruta más corta
    private int w(int distancia, int tiempo){
        /*
         * Devuelve el peso asociado a la arista de la red de carreteras.
         * 
         * Parámetros:
         *      - distancia: int,
         *          Distancia entre nodos.
         *      - tiempo: int,
         *          Tiempo de recorrido entre nodos.
         * 
         * Retorna:
         *      - peso: float,
         *          El peso asociado a la arista con {distancia, tiempo}.
         */

        int peso = distancia + 9 * tiempo;
        return peso;
    }

    public Ruta algoritmoBellmanFord(int verticeInicial, int verticeFinal){
        /*
         * Devuelve la ruta más corta dada la función de pesos entre los vértices de origen y llegada.
         * 
         * Parámetros:
         *      - verticeInicial: int,
         *          El vértice inicial de la ruta.
         *      - verticeFinal: int,
         *          El vértice final de la ruta.
         * 
         * Retorna:
         *      - rutaOptima: Ruta,
         *          La ruta que optimiza la distancia asociada a los pesos entre los vértices inicial y final.
         */

        // Se establece un predecesor (Padres) para cada vértice en la ruta óptima.
        // Se establece la distancia asociada óptima desde el vértice de origen a cada vértice.
        int numeroVertices = Parada.values().length;
        int[] padres = new int[numeroVertices];
        int[] recorrido  = new int[numeroVertices];
        recorrido[verticeInicial] = 0;

        // Estableciendo la distancia infinita y que el padre es null (Un padre inexistente será tomado como -1).
        for(int i = 0; i < numeroVertices; i++){
            padres[i] = -1;
            recorrido[i] = 100000;
        }

        // Implementación del algoritmo de Bell-Forman para hallar el predecesor y distancia asociada óptima
        for(int i = 1; i < numeroVertices; i++){
            for (int[][] arista: redCarreteras){
                int vertice1 = arista[0][0];
                int vertice2 = arista[0][1];
                int peso = w(arista[1][0], arista[1][1]);

                // Comparando si es mejor cambiar el camino hacia el vértice 2 pasando por el vértice 1.
                if(recorrido[vertice2] > recorrido[vertice1] + peso){
                    recorrido[vertice2] = recorrido[vertice1] + peso;
                    padres[vertice2] = vertice1;
                }

                // Comparando si es mejor cambiar el camino hacia el vértice 1 pasando por el vértice 2.
                if(recorrido[vertice1] > recorrido[vertice2] + peso){
                    recorrido[vertice1] = recorrido[vertice2] + peso;
                    padres[vertice1] = vertice2;
                }
            }
        }

        // Construcción de las paradas intermedias.
        ArrayList<Parada> paradas = new ArrayList<Parada>();
        int parada = verticeFinal;
        paradas.add(Parada.values()[parada]);
        while(padres[parada] > -1){
            // Añadiendo al padre a la lista.
            paradas.add(0, Parada.values()[padres[parada]]);

            // Haciendo
            parada = padres[parada];
        }

        // Añadiendo el vértice de origen a la parada.
        paradas.add(0, Parada.values()[verticeInicial]);

        // Construcción de la ruta óptima.
        Ruta rutaOptima = new Ruta(verticeInicial, verticeFinal, paradas);
        return rutaOptima;
    }

    // Implementación de la funcionalidad 4.
    // Creación de rutas con paradas muy concurridas.
    public float[] flujoPromedio(Boolean eje){// Posiblemente no nos sirva a futuro
        /*
         * Calcula el promedio de personas por viaje desde una parada a otra.
         * 
         * Parámetros:
         *      - eje: bool,
         *          Determina si el promedio se calcula en las filas (False) o columnas (True).
         * 
         * Retorna:
         *      - promedios: int[# Paradas][# Paradas],
         *          Matriz de cantidad de personas que compran este viaje.
         *          La entrada i, j representa el flujo desde la ciudad con ordinal i hasta la ciudad con ordinal j.
         */

        // 
        int[][] flujoDemanda = new int[totalParadas][totalParadas]; // Matriz de personas que realizan el viaje.
        int[][] flujoOferta = new int[totalParadas][totalParadas];  // Matriz de viajes totales realizados.
        ArrayList<Ruta> rutasCopia = new ArrayList<Ruta>();           // Contador de rutas ya analizadas.

        // Generando la copia del atributo rutas.
        for(Ruta ruta: rutas){rutasCopia.add(ruta);}

        // Contando el número de personas que viajan y viajes realizados (En la empresa).
        ArrayList<Factura> facturas = Contabilidad.getVentas(); // Las facturas llevan registro de esto.
        for(Factura factura: facturas){
            // Definiendo el origen y destino de la ruta.
            Ruta rutaAsociada = factura.getRutaElegida();
            int origen = factura.getOrigen().ordinal();
            int destino = factura.getDestino().ordinal();

            // Viendo si la ruta fue realizada por esta empresa.
            if(rutas.contains(rutaAsociada)){
                flujoDemanda[origen][destino] += factura.getAsientosAsignados();

                // Viendo si ya se tomó en cuenta esta ruta.
                if(rutasCopia.contains(rutaAsociada)){
                    flujoOferta[origen][destino]++;
                    flujoOferta[destino][origen]++;
                    rutasCopia.remove(rutaAsociada);
                }
            }
        }

        // Definiendo los promedios de flujo entre paradas.
        float[] promedios = new float[totalParadas];
        if(eje){
            for(int i = 0; i < totalParadas; i++){
                // Calculando el flujo de la columna i.
                int sumaOferta  = 0;
                int sumaDemanda = 0;
                for(int j = 0; j < totalParadas; j++){
                    sumaOferta  += flujoOferta[j][i];
                    sumaDemanda += flujoDemanda[j][i];
                }

                // Computando el promedio del flujo.
                if(sumaOferta != 0){
                    promedios[i] = sumaDemanda / sumaOferta;
                }
            }
        }
        else{
            for(int i = 0; i < totalParadas; i++){
                // Calculando el flujo de la fila i.
                int sumaOferta  = 0;
                int sumaDemanda = 0;
                for(int j = 0; j < totalParadas; j++){
                    sumaOferta  += flujoOferta[i][j];
                    sumaDemanda += flujoDemanda[i][j];
                }

                // Computando el promedio del flujo.
                if(sumaOferta != 0){
                    promedios[i] = sumaDemanda / sumaOferta;
                }
            }
        }

        return promedios;
    }

    public float[][] flujoPromedio(){
        /*
         * Calcula el promedio de personas por viaje desde una parada a otra.
         * 
         * Retorna:
         *      - promedios: int[# Paradas][# Paradas],
         *          Matriz de cantidad de personas que compran este viaje.
         *          La entrada i, j representa el flujo desde la ciudad con ordinal i hasta la ciudad con ordinal j.
         */

        // Definición de variables
        int[][] flujoDemanda = new int[totalParadas][totalParadas]; // Matriz de personas que realizan el viaje.
        int[][] flujoOferta = new int[totalParadas][totalParadas];  // Matriz de viajes totales realizados.
        ArrayList<Ruta> rutasCopia = new ArrayList<Ruta>();         // Contador de rutas ya analizadas.

        // Generando la copia del atributo rutas.
        for(Ruta ruta: rutas){rutasCopia.add(ruta);}

        // Contando el número de personas que viajan y viajes realizados (En la empresa).
        ArrayList<Factura> facturas = Contabilidad.getVentas(); // Las facturas llevan registro de esto.
        for(Factura factura: facturas){
            // Definiendo el origen y destino de la ruta.
            Ruta rutaAsociada = factura.getRutaElegida();
            int origen = factura.getOrigen().ordinal();
            int destino = factura.getDestino().ordinal();

            // Viendo si la ruta fue realizada por esta empresa.
            if(rutas.contains(rutaAsociada)){
                flujoDemanda[origen][destino] += factura.getAsientosAsignados();

                // Viendo si ya se tomó en cuenta esta ruta.
                if(rutasCopia.contains(rutaAsociada)){
                    flujoOferta[origen][destino]++;
                    flujoOferta[destino][origen]++;
                    rutasCopia.remove(rutaAsociada);
                }
            }
        }

        // Definiendo los promedios de flujo entre paradas.
        float[][] promedios = new float[totalParadas][totalParadas];
        for(int i = 0; i < totalParadas; i++){
            for(int j = 0; j < totalParadas; j++){
                if(flujoOferta[i][j] != 0){
                    promedios[i][j] = flujoDemanda[i][j] / flujoOferta[i][j];
                }
            }
        }

        return promedios;
    }

    public void funcionalidad4(){

    }

    public void funcionalidad4(int paradaOrigen, int paradaDestino, int numeroParadas, float factor){
        int totalParadas = Parada.values().length;
        Integer[] tuplasParadas = new Integer[totalParadas * totalParadas];
        float[][] promedios = flujoPromedio();

        for(int i = 0; i < totalParadas * totalParadas; i++){
            tuplasParadas[i] = i;
        }

        float[][] promediosSesgados = new float[totalParadas][totalParadas];
        for(int i = 0; i < totalParadas; i++){
            for(int j = 0; j < totalParadas; j++){
                if((paradaOrigen == -1 || paradaOrigen == i) && (paradaDestino == -1 || paradaDestino == j)){
                    promediosSesgados[i][j] = promedios[i][j];
                }
            }
        }

        Comparator<Integer> comparator = new Comparator<Integer>(){
            @Override
            public int compare(Integer n, Integer m){
                int cocienteN = n / totalParadas;
                int residuoN  = n % cocienteN;
                int cocienteM = m / totalParadas;
                int residuoM  = m % cocienteM;

                float promedioN = promediosSesgados[cocienteN][residuoN];
                float promedioM = promediosSesgados[cocienteM][residuoM];

                if(promedioN - promedioM > 0){return 1;}
                else if(promedioN - promedioM < 0){return -1;}
                else{return 0;}
            }
        };

        Arrays.sort(tuplasParadas, comparator);

        Ruta rutaMasSolicitada = algoritmoBellmanFord(paradaOrigen, paradaDestino);
    }

    public void funcionalidad4(String parada1, String parada2, int numeroParadas, float factor){

    }
}