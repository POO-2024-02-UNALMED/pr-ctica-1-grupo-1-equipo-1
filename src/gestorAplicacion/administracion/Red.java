package gestorAplicacion.administracion;
import java.util.ArrayList;

public abstract class Red{
    public static enum Parada{
        CIUDAD1, CIUDAD2, CIUDAD3, CIUDAD4, CIUDAD5, CIUDAD6;
    }

    // Atributo donde se guardará el grafo de la red de carreteras y las distancias
    // mínimas entre trayecto.
    static final int totalParadas = Parada.values().length;
    public static final ArrayList<int[][]> carreteras = new ArrayList<int[][]>();
    public static final int[][] distancias = new int[totalParadas][totalParadas];
    static {
        /*
         * Creación de la red de carreteras:
         * Esta vendrá representada por un grafo.
         * Los vértices serán las trayecto (Representadas por su ordinal).
         * Las aristas serán representadas por una matriz de enteros 2x2, cuyas
         * entradas:
         * [ a11 a12]
         * [ a21 a22]
         * Significan:
         * a11, a12 son las trayecto,
         * a21 es la distancia entre las trayecto (En kilómetros),
         * a22 es el tiempo de recorrido entre las trayecto (En minutos).
         * 
         * Se supondrá que el tiempo y distancia entre trayecto es la misma en ambas
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
         * Finalmente se calculan todas las distancias mínimas entre trayecto.
         */

        carreteras.add(new int[][] { { 1, 2 }, { 200, 60 } });
        carreteras.add(new int[][] { { 1, 3 }, { 300, 120 } });
        carreteras.add(new int[][] { { 1, 4 }, { 100, 85 } });
        carreteras.add(new int[][] { { 1, 5 }, { 1000, 300 } });
        carreteras.add(new int[][] { { 1, 6 }, { 50, 30 } });
        carreteras.add(new int[][] { { 2, 3 }, { 700, 360 } });
        carreteras.add(new int[][] { { 3, 4 }, { 700, 380 } });
        carreteras.add(new int[][] { { 4, 5 }, { 200, 120 } });
        carreteras.add(new int[][] { { 5, 6 }, { 400, 180 } });
        carreteras.add(new int[][] { { 6, 2 }, { 20, 15 } });

        // Hallando las distancias mínimas entre cada par de trayecto con el algoritmo de Floyd-Warshall.
        // Iniciando la matriz de pesos.
        for (int[][] arista : carreteras) {
            distancias[arista[0][0]][arista[0][1]] = w(arista[1][0], arista[0][1]);
            distancias[arista[0][0]][arista[0][1]] = w(arista[1][0], arista[0][1]);
        }
        // Definiendo distancia infinita entre puntos que no tiene una ruta directa.
        for(int i = 0; i < totalParadas; i++){
            for(int j = i + 1; j < totalParadas; j++){
                if(distancias[i][j] == 0){
                    distancias[i][j] = 100000; //Distancia infinita.
                }
            }
        }

        // Algoritmo Floyd-Warshall.
        for (int i = 0; i < totalParadas; i++) {
            for (int j = 0; j < totalParadas; j++) {
                for (int k = 0; k < totalParadas; k++) {
                    distancias[i][j] = Math.min(distancias[i][j],
                            distancias[i][k] + distancias[k][j]);
                }
            }
        }
    }

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

    public static int[] posicion(Parada[] trayecto, Parada parada){
        /*
         * Dado un conjunto de trayecto y una parada extra, se mira dónde
         * debería ir la parada extra en el conjunto de trayecto para así
         * minimizar la distancia total en caso de añadir la parada extra.
         * 
         * Parámetros:
         *      - trayecto: Parada[],
         *          Conjunto de trayecto.
         *      - parada: Parada,
         *          Parada a averiguar su posición óptima.
         * 
         * Retorna:
         *      - posiciones, int[].
         *          Las posiciones de la parada anterior y siguiente
         *          en la ruta donde se optimiza la posición de la nueva parada.
         */

        // Tomando el número de trayecto
        int longitudTrayecto = trayecto.length;

        if(trayecto.length == 0){
            return new int[] {-1, 1};
        }

        if(trayecto.length == 1){
            return new int[] {0, 2};
        }

        // Encontrando dónde se minimiza la distancia
        int distanciaMinima = distancias[parada.ordinal()][trayecto[0].ordinal()];
        int posicionDistanciaMinima = 0;
        for (int i = 1; i < longitudTrayecto; i++) {
            // Viendo que el punto no esté ya en el array.
            if(distancias[parada.ordinal()][trayecto[i].ordinal()] == 0){
                return null;
            }

            // Viendo si la distancia a este punto es menor a las anteriores.
            if (distanciaMinima > distancias[parada.ordinal()][trayecto[i].ordinal()]) {
                distanciaMinima = distancias[parada.ordinal()][trayecto[i].ordinal()];
                posicionDistanciaMinima = i;
            }
        }

        // Viendo si es mejor poner la nueva parada antes del punto con distancia mínima o después.
        int distanciaAnterior; // Distancia al agregar la nueva parada antes de la parada con distancia mínima.
        int distanciaPosterior; // Distancia al agregar la nueva parada después de la parada con distancia mínima.
        int anterior, minima, posterior; // Ordinal de trayecto anterior al mínimo, mínimo y posterior al mínimo.
        int nueva = parada.ordinal(); // Ordinal de la nueva parada.
        minima = trayecto[posicionDistanciaMinima].ordinal();
        if(posicionDistanciaMinima == 0){
            posterior = trayecto[1].ordinal();

            // Distancia agregando la nueva parada al inicio.
            distanciaAnterior = distancias[minima][posterior] + distancias[minima][nueva];
            // Distancia agregando la nueva parada entre la primera y segunda parada.
            distanciaPosterior = distancias[minima][nueva] + distancias[nueva][posterior];
        }
        else if(posicionDistanciaMinima == longitudTrayecto - 1){
            anterior = trayecto[posicionDistanciaMinima - 2].ordinal();
            // Distancia agregando la nueva parada entre la penúltima y última parada.
            distanciaAnterior = distancias[anterior][minima] + distancias[minima][nueva];
            // Distancia agregando la nueva parada al final.
            distanciaPosterior = distancias[anterior][nueva] + distancias[nueva][minima];
        }
        else{
            anterior = trayecto[posicionDistanciaMinima - 1].ordinal();
            posterior = trayecto[posicionDistanciaMinima + 1].ordinal();
            // Distancia agregando la nueva parada entre la penúltima y última parada.
            distanciaAnterior = distancias[anterior][nueva] + distancias[nueva][minima] +
                                distancias[minima][posterior];
            // Distancia agregando la nueva parada al final.
            distanciaPosterior = distancias[anterior][minima] + distancias[minima][nueva] +
                                 distancias[nueva][posterior];
        }

        // Devolviendo las posiciones óptimas.
        int[] posiciones = new int[2];
        if(distanciaAnterior > distanciaPosterior){
            posiciones = new int[] {posicionDistanciaMinima - 1, posicionDistanciaMinima + 1};
        }
        else{
            posiciones = new int[] {posicionDistanciaMinima - 2, posicionDistanciaMinima};
        }

        return posiciones;
    }

    // Algoritmo de Bellman-Ford para hacer la ruta más corta
    public static int[] algoritmoBellmanFord(int verticeInicial, int verticeFinal) {
        /*
         * Devuelve la ruta más corta dada la función de pesos entre los vértices de
         * origen y llegada.
         * 
         * Parámetros:
         *      - verticeInicial: int,
         *          El vértice inicial de la ruta.
         *      - verticeFinal: int,
         *          El vértice final de la ruta.
         * 
         * Retorna:
         * - ordinalesRutaOptima: Ruta,
         *      La ruta que optimiza la distancia asociada a los pesos entre los vértices
         *      inicial y final, pero indicando el ordinal de cada parada.
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
            for (int[][] arista : carreteras) {
                int vertice1 = arista[0][0];
                int vertice2 = arista[0][1];
                int peso = w(arista[1][0], arista[1][1]);

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
        int[] ordinalesRutaOptima = new int[paradas.size()];
        for(int i = 0; i < paradas.size(); i++){
            ordinalesRutaOptima[i] = paradas.get(i).ordinal();
        }

        return ordinalesRutaOptima;
    }

    public static Parada[] enteroAParada(int[] ordinales){
        /*
         * Se transforma un conjunto de ordinales en el conjunto de paradas que
         * poseen estos ordinales.
         * 
         * Parámetros:
         *      - ordinales: int[],
         *          Ordinales de las paradas.
         * 
         * Retorna:
         *      - paradas: Parada[],
         *          Paradas asociadas.
         */
    
        Parada[] paradas = new Parada[ordinales.length];
        for(int i = 0; i < ordinales.length; i++){
            paradas[i] = Parada.values()[ordinales[i]];
        }

        return paradas;
    }

    public abstract void agregarParada(Parada nuevaParada);

    public abstract void eliminarParada(Parada nuevaParada);
}