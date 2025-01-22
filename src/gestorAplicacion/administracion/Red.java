package gestorAplicacion.administracion;
import java.util.ArrayList;

public abstract class Red{
    public static enum Parada{
        CIUDAD1, CIUDAD2, CIUDAD3, CIUDAD4, CIUDAD5, CIUDAD6;
    }

    // Atributo donde se guardará el grafo de la red de carreteras y las distancias
    // mínimas entre trayecto.
    public static final int totalParadas = Parada.values().length;
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

    public static Parada Parada(int i){
        // Devuelve la parada con ordinal i.
        return Parada.values()[i];
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

    static int[] posicion(int[] ordinalTrayecto, int ordinal){
        /*
         * Dado un conjunto de trayecto y una parada extra (Ordinales), se mira dónde
         * debería ir la parada extra en el conjunto de trayecto para así
         * minimizar la distancia total en caso de añadir la parada extra.
         * 
         * Parámetros:
         *      - trayecto: int[],
         *          Conjunto de ordinales de las paradas en el trayecto.
         *      - parada: int,
         *          Ordinal de la parada a averiguar su posición óptima.
         * 
         * Retorna:
         *      - posiciones, int[].
         *          Las posiciones de la parada anterior y siguiente
         *          en la ruta donde se optimiza la posición de la nueva parada.
         */

        // Haciendo el cambio adecuado.
        return posicion(enteroAParada(ordinalTrayecto), Parada(ordinal));
    }

    static int[] posicion(Parada[] trayecto, Parada parada){
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

        // Trata de errores.
        if(trayecto.length < 1){
            // Mostrar error.
        }
        if(parada == null){
            return null;
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

    public static int longitud(int[] ordinalesTrayecto){
        /*
         * Retorna la longitud de un trayecto.
         * 
         * Parámetros:
         *      - ordinalesTrayecto: int[],
         *          Conjunto de paradas (Ordinales) a calcular su longitud.
         * 
         * Retorna:
         *      - longitud: int,
         *          Longitud del trayecto.
         */

        // Calculando la longitud del trayecto.
        int longitud = 0;
        for(int i = 0; i < ordinalesTrayecto.length - 1; i++){
            longitud += distancias[ordinalesTrayecto[i]][ordinalesTrayecto[i + 1]];
        }

        return longitud;
    }
    
    protected static int longitud(Parada[] trayecto){
        /*
         * Retorna la longitud de un trayecto.
         * 
         * Parámetros:
         *      - trayecto: Parada[],
         *          Conjunto de paradas (Trayecto) a calcular su longitud.
         * 
         * Retorna:
         *      - longitud: int,
         *          Longitud del trayecto.
         */

        // Pasando el conjunto de paradas a sus ordinales.
        int[] ordinalesTrayecto = new int[trayecto.length];
        for(int i = 0; i < trayecto.length; i++){
            ordinalesTrayecto[i] = trayecto[i].ordinal();
        }

        return longitud(ordinalesTrayecto);
    }

    // Algoritmo de Bellman-Ford para hacer la ruta más corta.
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
        // Se establece la distancia asociada óptima desde el vértice de origen a cada vértice.
        int[] padres = new int[totalParadas];
        int[] recorrido = new int[totalParadas];
        recorrido[verticeInicial] = 0;

        // Estableciendo la distancia infinita y que el padre es null (Un padre
        // inexistente será tomado como -1).
        for (int i = 0; i < totalParadas; i++) {
            padres[i] = -1;
            recorrido[i] = 100000; // Distancia infinita.
        }

        // Implementación del algoritmo de Bell-Forman para hallar el predecesor y
        // distancia asociada óptima
        for (int i = 1; i < totalParadas; i++) {
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
        paradas.add(Parada(parada));
        while (padres[parada] > -1) {
            // Añadiendo al padre a la lista.
            paradas.add(0, Parada(padres[parada]));

            // Haciendo inducción.
            parada = padres[parada];
        }

        // Añadiendo el vértice de origen a la parada.
        paradas.add(0, Parada(verticeInicial));

        // Retorno de las paradas.
        int[] ordinalesRutaOptima = new int[paradas.size()];
        for(int i = 0; i < paradas.size(); i++){
            ordinalesRutaOptima[i] = paradas.get(i).ordinal();
        }

        return ordinalesRutaOptima;
    }

    static int[] ordenarParadas(int[] ordinales){
        /*
         * Ordena las paradas de tal manera que el orden represente un recorrido que
         * optimiza la distancia total.
         * 
         * Parámetros:
         *      - ordinales: int[],
         *          Conjunto original de paradas (ordinales).
         * 
         * Retorna:
         *      - paradasOrdenadas: int[],
         *          Paradas (ordinales) organizadas para minimizar el orden.
         */

        // Creando el array que contendrá las apradas ordenadas.
        int[] paradasOrdenadas = new int[1];
        paradasOrdenadas[0] = ordinales[0];

        // Ordenando el array.
        int[] temp;
        for(int i = 1; i < ordinales.length; i++){
            // Viendo la posición del elemento i-ésimo.
            int[] posiciones = posicion(enteroAParada(paradasOrdenadas), Parada(ordinales[i]));

            // Añadiéndolo a la lista si es distinto.
            if(posiciones != null){
                temp = new int[i + 1];
                temp[posiciones[0] + 2] = ordinales[i];
                for(int j = 0; j < i + 1; j++){
                    if(j < posiciones[0] + 1){
                        temp[i] = paradasOrdenadas[i];
                    }
                    else if(j > posiciones[0] + 1){
                        temp[i] = paradasOrdenadas[i - 1];
                    }
                }

                paradasOrdenadas = temp;
            }
        }

        return paradasOrdenadas;
    }

    protected static Parada[] ordenarParadas(Parada[] paradas){
        /*
         * Ordena las paradas de tal manera que el orden represente un recorrido que
         * optimiza la distancia total.
         * 
         * Parámetros:
         *      - paradas: Parada[],
         *          Conjunto original de paradas.
         * 
         * Retorna:
         *      - paradasOrdenadas: Parada[],
         *          Paradas organizadas para minimizar el orden.
         */

        // Creando el array que contendrá las apradas ordenadas.
        Parada[] paradasOrdenadas = new Parada[1];
        paradasOrdenadas[0] = paradas[0];

        // Ordenando el array.
        Parada[] temp;
        for(int i = 1; i < paradas.length; i++){
            // Viendo la posición del elemento i-ésimo.
            int[] posiciones = posicion(paradasOrdenadas, paradas[i]);

            // Añadiéndolo a la lista si es distinto.
            if(posiciones != null){
                temp = new Parada[i + 1];
                temp[posiciones[0] + 2] = paradas[i];
                for(int j = 0; j < i + 1; j++){
                    if(j < posiciones[0] + 1){
                        temp[i] = paradasOrdenadas[i];
                    }
                    else if(j > posiciones[0] + 1){
                        temp[i] = paradasOrdenadas[i - 1];
                    }
                }

                paradasOrdenadas = temp;
            }
        }

        return paradasOrdenadas;
    }

    protected static Parada[] enteroAParada(int[] ordinales){
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
            paradas[i] = Parada(ordinales[i]);
        }

        return paradas;
    }

    static int[] agregarParada(int[] ordinalTrayecto, int ordinal){
        /*
         * Añade la nueva parada haciendo minimizando su efecto en la ruta.
         * 
         * Parámetros:
         *      - trayecto: int[],
         *          Conjunto de ordinales paradas en donde se va a añadir la nueva parada.
         *      - nuevaParada: Parada,
         *          Ordinal de la parada a añadir.
         * 
         * Retorna:
         *      - nuevoTrayecto: int[],
         *          El resultado de agregar la nueva parada.
         */

        // Tomando el número de paradas
        int numeroParadas = ordinalTrayecto.length;

        // Caso donde solo exista una parada.
        if(numeroParadas < 2){
            // Debe generar error.
        }

        int[] posicion = posicion(ordinalTrayecto, ordinal);
        if(posicion == null){
            return ordinalTrayecto;
        }

        // En caso de poderse agregar, se ve el puesto donde debe agregarse.
        int posicionDistanciaMinima = posicion[0] + 1;

        // Reorganizando el array de paradas para que la nueva parada quede en la posición indicada.
        int[] nuevoTrayecto = new int[numeroParadas + 1];
        nuevoTrayecto[posicionDistanciaMinima] = ordinal;
        for (int i = 0; i < numeroParadas; i++) {
            if (i < posicionDistanciaMinima) {
                nuevoTrayecto[i] = ordinalTrayecto[i];
            } else {
                nuevoTrayecto[i + 1] = ordinalTrayecto[i];
            }
        }

        if(nuevoTrayecto.length < 2){
            return ordinalTrayecto;
        }

        return nuevoTrayecto;
    }

    static int[] eliminarParada(int[] ordinalTrayecto, int ordinal){
        /*
         * Elimina una parada de un trayecto dado.
         * 
         * Parámetros:
         *      - trayecto: int[],
         *          Conjunto de ordinales paradas en donde se va a añadir la nueva parada.
         *      - nuevaParada: Parada,
         *          Ordinal de la parada a eliminar.
         * 
         * Retorna:
         *      - nuevoTrayecto: int[],
         *          El resultado de eliminar la parada.
         */

        // Tomando el número de paradas
        int numeroParadas = ordinalTrayecto.length;

        // Caso donde solo exista una parada.
        if(numeroParadas < 2){
            // Debe generar error.
        }

        // Viendo que el nuevo trayecto tenga al menos 2 paradas.
        if(numeroParadas - 1 < 2){
            return ordinalTrayecto;
        }

        // Creando un array donde se quitó la parada.
        int[] nuevoTrayecto = new int[numeroParadas - 1];
        Boolean estaEnElArray = false;
        for(int i = 0; i < numeroParadas - 1; i++){
            if(ordinalTrayecto[i] == ordinal){estaEnElArray = true;}       // Viendo si está la parada.
            else{
                // Desplazando lo adecuado.
                nuevoTrayecto[i] = ordinalTrayecto[i - (estaEnElArray ? 1 : 0)];
            }
        }

        // Preguntando si la parada se encontraba en el array.
        if(!estaEnElArray && ordinalTrayecto[numeroParadas - 1] != ordinal){
            return ordinalTrayecto;
        }
        else if(!estaEnElArray && ordinalTrayecto[numeroParadas - 1] == ordinal){
            nuevoTrayecto[numeroParadas - 2] = ordinalTrayecto[numeroParadas - 1];
        }

        return nuevoTrayecto;
    }

    public abstract void agregarParada(Parada nuevaParada);

    public abstract void eliminarParada(Parada nuevaParada);
}