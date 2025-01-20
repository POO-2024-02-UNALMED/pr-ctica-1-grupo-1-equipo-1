package gestorAplicacion.administracion;

import gestorAplicacion.operacion.logistica.Bus;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;

public class Ruta extends Red {
    private static int totalRutas;
    private static ArrayList<Ruta> totalRutasLista = new ArrayList<>();
    private int idRuta;
    private Bus busAsociado;
    private int fechaSalida; // Cambiar por objeto de tiempo
    private int fechaLlegada;// Cambiar por objeto de tiempo
    private LocalDateTime fechaSalidaDateTime;
    private LocalDateTime fechaLlegadaDateTime;
    private Parada lugarInicio;
    private Parada lugarFinal;
    private Parada[] paradas;

    // Constructores
    public Ruta(Bus busAsociado, int fechaSalida, int fechaLlegada, // Cambiar por objetos de tiempo
            Parada lugarInicio, Parada lugarFinal, Parada[] paradas) {
        totalRutas++;
        this.idRuta = totalRutas;
        this.busAsociado = busAsociado;
        this.fechaSalida = fechaSalida; // Hay que verificar que la fecha de salida sea menor a la de llegada
        this.fechaLlegada = fechaLlegada; // ""
        this.lugarInicio = lugarInicio;
        this.lugarFinal = lugarFinal;
        this.setParadas(paradas);
    }

    public Ruta(Parada lugarInicio, Parada lugarFinal, Parada[] paradas) {
        // Cambiar por objetos de tiempo
        this(null, 0, 0, lugarInicio, lugarFinal, paradas);
    }

    public Ruta(int ordinalLugarInicio, int ordinalLugarFinal, int[] ordinalesParadas) {
        // Cambiar por objetos de tiempo
        this(null, 0, 0,
                Parada(ordinalLugarInicio),
                Parada(ordinalLugarFinal),
                Red.enteroAParada(ordinalesParadas));
    }

    // Métodos get-set
    static public ArrayList<Ruta> getTotalRutasLista(){
        return totalRutasLista;
    }
    static public void setTotalRutasLista(ArrayList<Ruta> nuevosTotalRutasLista) {
        totalRutasLista = nuevosTotalRutasLista;
    }
    public static int getTotalRutas() {
        return totalRutas;
    }

    public static void setTotalRutas(int nuevoTotalRutas) {
        totalRutas = nuevoTotalRutas;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int nuevoIdRuta) {
        idRuta = nuevoIdRuta;
    }

    public Bus getBusAsociado() {
        return busAsociado;
    }

    public void setBusAsociado(Bus nuevoBusAsociado) {
        busAsociado = nuevoBusAsociado;
    }

    // TEMPORAL REEMPLAZAR POR LOCALDATETIME
    public int getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(int nuevafechaSalida) {
        fechaSalida = nuevafechaSalida;
    }

    public LocalDateTime getFechaSalidaDateTime() {
        return fechaSalidaDateTime;
    }

    public void setFechaSalidaDateTime(LocalDateTime nuevafechaLlegada) {
        fechaLlegadaDateTime = nuevafechaLlegada;
    }

    // TEMPORAL REEMPLAZAR POR LOCALDATETIME
    public int getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(int nuevafechaLlegada) {
        fechaLlegada = nuevafechaLlegada;
    }

    public LocalDateTime getFechaLlegadaDateTime() {
        return fechaLlegadaDateTime;
    }

    public void setFechaLlegadaDateTime(LocalDateTime nuevafechaLlegada) {
        fechaLlegadaDateTime = nuevafechaLlegada;
    }

    public Parada getLugarInicio() {
        return lugarInicio;
    }

    public void setLugarInicio(Parada nuevoLugarInicio) {
        lugarInicio = nuevoLugarInicio;
    }

    public Parada getLugarFinal() {
        return lugarFinal;
    }

    public void setLugarFinal(Parada nuevoLugarFinal) {
        lugarFinal = nuevoLugarFinal;
    }

    public Parada[] getParadas() {
        return paradas;
    }

    public void setParadas(Parada[] nuevasParadas) {
        paradas = new Parada[2];
        paradas[0] = lugarInicio;
        paradas[1] = lugarFinal;

        // Poniendo todas las paradas en su lugar sin contar null.
        for (Parada parada : nuevasParadas) {
            this.agregarParada(parada);
        }
    }

    // Métodos de instancia
    public void calcularDistancia() {

    }

    public void finalizarRutas() {

    }

    static public ArrayList<Ruta> filtrarRutas(String origen, String destino) {
        /*
         * Filtra las rutas que cumplan con ciertas condiciones.
         * 
         * Parámetros:
         * - condiciones: Condiciones de filtrado.
         * Condiciones para filtrar rutas.
         * 
         * Retorna:
         * - ArrayList<Ruta>: Lista de rutas que cumplen con las condiciones.
         */
        
        // Implementación aquí.
        ArrayList<Ruta> rutasFiltradas = new ArrayList<>();

        // Iterar sobre todas las rutas disponibles en la red o sistema
        for (Ruta ruta : getTotalRutasLista()) { // Método que devuelve todas las rutas disponibles
            if (ruta.getLugarInicio().equals(origen) && ruta.getLugarFinal().equals(destino)) {
                rutasFiltradas.add(ruta);
            }
        }

        return rutasFiltradas;
    }

    public void agregarParada(Parada nuevaParada) {
        /*
         * Añade la nueva parada haciendo minimizando su efecto en la ruta.
         * 
         * Parámetros:
         * - nuevaParada: Parada.
         * Parada a añadir
         */

        // Tomando el número de paradas
        int nParadas = paradas.length;

        // Caso donde solo exista una parada.
        if (nParadas < 2) {
            // Debe generar error.
        }

        int[] posicion = Red.posicion(paradas, nuevaParada);
        int posicionDistanciaMinima = posicion[0] + 1;

        // Reorganizando el array de paradas para que la nueva parada quede en la
        // posición indicada.
        Parada[] temp = new Parada[nParadas + 1];
        temp[posicionDistanciaMinima] = nuevaParada;
        for (int i = 0; i < nParadas; i++) {
            if (i < posicionDistanciaMinima) {
                temp[i] = paradas[i];
            } else {
                temp[i + 1] = paradas[i];
            }
        }

        // Cambiando los lugares de origen y destino.
        lugarInicio = temp[0];
        lugarFinal = temp[nParadas - 1];

        // Garantizando el cambio.
        paradas = temp;
    }

    public void eliminarParada(Parada parada) {
        /*
         * Añade la nueva parada haciendo minimizando su efecto en la ruta.
         * 
         * Parámetros:
         * - nuevaParada: Parada.
         * Parada a eliminar
         */

        // Caso donde no existan paradas.
        if (paradas == null) {
            paradas = new Parada[0];
            return;
        }

        // Tomando el número de paradas.
        int nParadas = paradas.length;

        // Creando un array donde se quitó la parada.
        Parada[] temp = new Parada[nParadas - 1];
        Boolean estaEnElArray = false;
        for (int i = 0; i < nParadas - 1; i++) {
            if (paradas[i] == parada) {
                estaEnElArray = true;
            } // Viendo si está la parada.
            else {
                temp[i] = paradas[i - (estaEnElArray ? 1 : 0)];
            } // Desplazando lo adecuado.
        }

        // Preguntando si la parada se encontraba en el array.
        if (!estaEnElArray && paradas[nParadas - 1] != parada) {
            return;
        } else if (!estaEnElArray && paradas[nParadas - 1] == parada) {
            temp[nParadas - 2] = paradas[nParadas - 1];
        }

        // Guardando el cambio.
        paradas = temp;
    }

    public int contarPasajerosEnParada(Parada parada) {
        int contador = 0;
        for (Factura factura : Contabilidad.getVentas()) {
            if (factura.getRutaElegida().equals(this) && factura.getDestino().equals(parada)) {
                contador += factura.getnumAsientosAsignados();
            }
        }
        return contador;
    }

    // Este metodo obtiene todas las rutas alternativas que el usuario puede
    // obtener,
    // Tener en cuenta que se debe ejecutar por cada empresa que se quiera ver las
    // rutas alternativas
    public ArrayList<Ruta> obtenerRutasAlternativas() {
        ArrayList<Ruta> rutasAlternativas = new ArrayList<Ruta>();
        Empresa empresa = this.getBusAsociado().getEmpresa();
        // Iterar sobre las rutas de la empresa
        for (Ruta ruta : empresa.getRutas()) {

            if (ruta.getLugarFinal().equals(this.lugarFinal) && ruta.getLugarInicio() != this.lugarInicio) {
                // Verificar si la ruta esta activa y si el bus esta disponible tambien
                if (ruta.getBusAsociado() != null
                        && ruta.getBusAsociado().isDisponible(this.fechaSalida, this.fechaLlegada)) {

                    // Convertir los arreglos a ArrayList para poder trabajar con
                    ArrayList<Parada> paradasRutaActual = new ArrayList<>(Arrays.asList(this.getParadas()));
                    ArrayList<Parada> paradasRutasAlternativas = new ArrayList<>(Arrays.asList(ruta.getParadas()));

                    // Verificar si alguna parada de la ruta actual está en las paradas alternativas
                    for (Parada paradaActual : paradasRutaActual) {
                        if (paradasRutasAlternativas.contains(paradaActual)) {
                            rutasAlternativas.add(ruta);
                            break; // Salir del bucle si se encuentra una coincidencia
                        }
                    }
                }
            }
        }
        return rutasAlternativas;
    }

    //-
    // Método para calcular la distancia total de la ruta
public double calcularDistanciaRuta() {
    double distanciaTotal = 0.0; 
    // Inicializa la distancia total en 0.0
    int[][] distancias = Empresa.distanciaMinima; 
    // Obtiene la matriz de distancias mínimas desde la clase Empresa
    for (int i = 0; i < paradas.length - 1; i++) { 
        // Recorre el array de paradas, excepto la última parada
        distanciaTotal += distancias[paradas[i].ordinal()][paradas[i + 1].ordinal()]; 
        // Suma la distancia entre paradas consecutivas
    }
    return distanciaTotal; 
    // Devuelve la distancia total calculada
}

// Método para calcular el tiempo estimado de viaje
public int calcularTiempoEstimado() {
    int duracion = 0; // Inicializa la duración del viaje en 0 minutos
    // Aquí se debe agregar el código para calcular la duración del viaje según la lógica de negocio
    return duracion; 
    // Devuelve la duración del viaje calculada
}

// Método para evaluar la complejidad de la ruta
public double evaluarComplejidad() {
    double complejidad = 0.0; 
    // Inicializa la complejidad en 0.0
    for (Parada parada : paradas) { 
        // Recorre todas las paradas en la ruta
        complejidad += 1.0; 
        // Suma un valor fijo por cada parada para evaluar la complejidad
        // Aquí se podría agregar una lógica más compleja para evaluar la complejidad de cada parada
    }
    return complejidad; // Devuelve la complejidad total calculada
}


    
    //-

}
