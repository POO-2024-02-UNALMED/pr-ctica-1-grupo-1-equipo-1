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

    public void finalizarRuta() {

    }

    public void agregarParada(Parada nuevaParada) {
        /*
         * Añade la nueva parada haciendo minimizando su efecto en la ruta.
         * 
         * Parámetros:
         * - nuevaParada: Parada.
         * Parada a añadir
         */

        // Viendo las distancias mínimas
        int[][] distancias = Empresa.distanciaMinima;

        // Encontrando dónde se minimiza la distancia
        int distanciaMinima = distancias[nuevaParada.ordinal()][paradas[0].ordinal()];
        int posicionDistanciaMinima = 0;
        for (int i = 1; i < paradas.length; i++) {
            if (distanciaMinima > distancias[nuevaParada.ordinal()][paradas[i].ordinal()]) {
                distanciaMinima = distancias[nuevaParada.ordinal()][paradas[i].ordinal()];
                posicionDistanciaMinima = i;
            }
        }

        // Reorganizando el array de paradas para que la nueva parada quede en la
        // posición indicada.
        Parada[] temp = new Parada[paradas.length + 1];
        temp[posicionDistanciaMinima] = nuevaParada;
        for (int i = 0; i < paradas.length; i++) {
            if (i < posicionDistanciaMinima) {
                temp[i] = paradas[i];
            } else {
                temp[i + 1] = paradas[i];
            }
        }

        // Garantizando el cambio.
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
            
            if (ruta.getLugarFinal() == this.lugarFinal && ruta.getLugarInicio() != this.lugarInicio) {
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