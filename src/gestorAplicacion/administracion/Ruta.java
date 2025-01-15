package gestorAplicacion.administracion;

import gestorAplicacion.operacion.logistica.Bus;
import java.util.ArrayList;
import java.util.Arrays;

public class Ruta {
    private static int totalRutas;
    private int idRuta;
    private Bus busAsociado;
    private int fechaSalida; // Cambiar por objeto de tiempo
    private int fechaLlegada; // Cambiar por objeto de tiempo
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

    // Métodos get-set
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

    public int getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(int nuevafechaSalida) {
        fechaSalida = nuevafechaSalida;
    }

    public int getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(int nuevafechaLlegada) {
        fechaLlegada = nuevafechaLlegada;
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
        this.paradas = new Parada[0];

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

    public ArrayList<Ruta> filtrarRutas(){
        return null;
    }
    
    public void agregarParada(Parada nuevaParada) {
        /*
         * Añade la nueva parada haciendo minimizando su efecto en la ruta.
         * 
         * Parámetros:
         * - nuevaParada: Parada.
         * Parada a añadir
         */

        // Caso donde no existan paradas.
        if(paradas != null){
            if(paradas.length == 0){
                paradas = new Parada[] {nuevaParada};
                return;
            }
        }
        else{
            paradas = new Parada[] {nuevaParada};
            return;
        }

        // Tomando el número de paradas
        int nParadas = paradas.length;

        // Caso donde solo exista una parada.
        if(nParadas == 1){
            Parada[] temp = new Parada[2];
            temp[0] = paradas[0];
            temp[1] = nuevaParada;
            return;
        }

        // Viendo las distancias mínimas
        int[][] distancias = Empresa.distanciaMinima;

        // Encontrando dónde se minimiza la distancia
        int distanciaMinima = distancias[nuevaParada.ordinal()][paradas[0].ordinal()];
        int posicionDistanciaMinima = 0;
        for (int i = 1; i < nParadas; i++) {
            // Viendo que el punto no esté ya en el array.
            if(distancias[nuevaParada.ordinal()][paradas[i].ordinal()] == 0){
                return;
            }

            // Viendo si la distancia a este punto es menor a las anteriores.
            if (distanciaMinima > distancias[nuevaParada.ordinal()][paradas[i].ordinal()]) {
                distanciaMinima = distancias[nuevaParada.ordinal()][paradas[i].ordinal()];
                posicionDistanciaMinima = i;
            }
        }

        // Viendo si es mejor poner la nueva parada antes del punto con distancia mínima o después.
        int distanciaAnterior; // Distancia al agregar la nueva parada antes de la parada con distancia mínima.
        int distanciaPosterior; // Distancia al agregar la nueva parada después de la parada con distancia mínima.
        int anterior, minima, posterior; // Ordinal de paradas anterior al mínimo, mínimo y posterior al mínimo.
        int nueva = nuevaParada.ordinal(); // Ordinal de la nueva parada.
        minima = paradas[posicionDistanciaMinima].ordinal();
        if(posicionDistanciaMinima == 0){
            posterior = paradas[1].ordinal();

            // Distancia agregando la nueva parada al inicio.
            distanciaAnterior = distancias[minima][posterior] + distancias[minima][nueva];
            // Distancia agregando la nueva parada entre la primera y segunda parada.
            distanciaPosterior = distancias[minima][nueva] + distancias[nueva][posterior];
        }
        else if(posicionDistanciaMinima == nParadas - 1){
            anterior = paradas[posicionDistanciaMinima - 2].ordinal();
            // Distancia agregando la nueva parada entre la penúltima y última parada.
            distanciaAnterior = distancias[anterior][minima] + distancias[minima][nueva];
            // Distancia agregando la nueva parada al final.
            distanciaPosterior = distancias[anterior][nueva] + distancias[nueva][minima];
        }
        else{
            anterior = paradas[posicionDistanciaMinima - 1].ordinal();
            posterior = paradas[posicionDistanciaMinima + 1].ordinal();
            // Distancia agregando la nueva parada entre la penúltima y última parada.
            distanciaAnterior = distancias[anterior][nueva] + distancias[nueva][minima] +
                                distancias[minima][posterior];
            // Distancia agregando la nueva parada al final.
            distanciaPosterior = distancias[anterior][minima] + distancias[minima][nueva] +
                                 distancias[nueva][posterior];
        }
        // Poniendo la posición real de la nueva parada.
        if(distanciaAnterior > distanciaPosterior){posicionDistanciaMinima++;}

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

        // Garantizando el cambio.
        paradas = temp;
    }

    public void eliminarParada(Parada parada){
        /*
         * Añade la nueva parada haciendo minimizando su efecto en la ruta.
         * 
         * Parámetros:
         * - nuevaParada: Parada.
         *      Parada a eliminar
         */

        // Caso donde no existan paradas.
        if(paradas == null){
            paradas = new Parada[0];
            return;
        }

        // Tomando el número de paradas.
        int nParadas = paradas.length;

        // Creando un array donde se quitó la parada.
        Parada[] temp = new Parada[nParadas - 1];
        Boolean estaEnElArray = false;
        for(int i = 0; i < nParadas - 1; i++){
            if(paradas[i] == parada){estaEnElArray = true;}       // Viendo si está la parada.
            else{temp[i] = paradas[i - (estaEnElArray ? 1 : 0)];} // Desplazando lo adecuado.
        }

        // Preguntando si la parada se encontraba en el array.
        if(!estaEnElArray && paradas[nParadas - 1] != parada){
            return;
        }
        else if(!estaEnElArray && paradas[nParadas - 1] == parada){
            temp[nParadas - 2] = paradas[nParadas - 1];
        }

        // Guardando el cambio.
        paradas = temp;
    }

    public int contarPasajerosEnParada(Parada parada) {
        int contador = 0;
        for (Factura factura : Contabilidad.getVentas()) {
            if (factura.getRutaElegida().equals(this) && factura.getDestino().equals(parada)) {
                contador += factura.getAsientosAsignados();
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

}