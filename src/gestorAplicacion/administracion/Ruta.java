package gestorAplicacion.administracion;
import gestorAplicacion.operacion.logistica.Bus;

public class Ruta{
    private static int totalRutas;
    private int idRuta;
    private Bus busAsociado;
    private int fechaSalida;  // Cambiar por objeto de tiempo
    private int fechaLlegada; // Cambiar por objeto de tiempo
    private int lugarInicio;
    private int lugarFinal;
    private Parada[] paradas;

    // Constructores
    public Ruta(Bus busAsociado, int fechaSalida, int fechaLlegada, // Cambiar por objetos de tiempo
     int lugarInicio, int lugarFinal, Parada[] paradas){
        totalRutas++;
        this.idRuta = totalRutas;
        this.busAsociado = busAsociado;
        this.fechaSalida = fechaSalida;     // Hay que verificar que la fecha de salida sea menor a la de llegada
        this.fechaLlegada = fechaLlegada;   // ""
        this.lugarInicio = lugarInicio;
        this.lugarFinal = lugarFinal;
        this.setParadas(paradas);
    }

    public Ruta(int lugarInicio, int lugarFinal, Parada[] paradas){
        // Cambiar por objetos de tiempo
        this(null, 0, 0, lugarInicio, lugarFinal, paradas);
    }

    // Métodos get-set
    public static int getTotalRutas(){
        return totalRutas;
    }

    public static void setTotalRutas(int nuevoTotalRutas){
        totalRutas = nuevoTotalRutas;
    }

    public int getIdRuta(){
        return idRuta;
    }

    public void setIdRuta(int nuevoIdRuta){
        idRuta = nuevoIdRuta;
    }

    public Bus getBusAsociado(){
        return busAsociado;
    }

    public void setBusAsociado(Bus nuevoBusAsociado){
        busAsociado = nuevoBusAsociado;
    }

    public int getFechaSalida(){
        return fechaSalida;
    }

    public void setFechaSalida(int nuevafechaSalida){
        fechaSalida = nuevafechaSalida;
    }

    public int getFechaLlegada(){
        return fechaLlegada;
    }

    public void setFechaLlegada(int nuevafechaLlegada){
        fechaLlegada = nuevafechaLlegada;
    }

    public int getLugarInicio(){
        return lugarInicio;
    }

    public void setLugarInicio(int nuevoLugarInicio){
        lugarInicio = nuevoLugarInicio;
    }

    public int getLugarFinal(){
        return lugarFinal;
    }

    public void setLugarFinal(int nuevoLugarFinal){
        lugarFinal = nuevoLugarFinal;
    }

    public Parada[] getParadas(){
        return paradas;
    }

    public void setParadas(Parada[] nuevasParadas){
        this.paradas = new Parada[0];

        // Poniendo todas las paradas en su lugar sin contar null.
        for(Parada parada: nuevasParadas){
            this.agregarParada(parada);
        }
    }

    // Métodos de instancia
    public void calcularDistancia(){

    }

    public void finalizarRuta(){

    }

    public void agregarParada(Parada nuevaParada){
        /*
         * Añade la nueva parada haciendo minimizando su efecto en la ruta.
         * 
         * Parámetros:
         *      - nuevaParada: Parada.
         *          Parada a añadir
         */

        // Viendo las distancias mínimas
        int[][] distancias = Empresa.distanciaMinima;

        // Encontrando dónde se minimiza la distancia
        int distanciaMinima = distancias[nuevaParada.ordinal()][paradas[0].ordinal()];
        int posicionDistanciaMinima = 0;
        for(int i = 1; i < paradas.length; i++){
            if(distanciaMinima > distancias[nuevaParada.ordinal()][paradas[i].ordinal()]){
                distanciaMinima = distancias[nuevaParada.ordinal()][paradas[i].ordinal()];
                posicionDistanciaMinima = i;
            }
        }

        // Reorganizando el array de paradas para que la nueva parada quede en la posición indicada.
        Parada[] temp = new Parada[paradas.length + 1];
        temp[posicionDistanciaMinima] = nuevaParada;
        for(int i = 0; i < paradas.length; i++){
            if(i < posicionDistanciaMinima){
                temp[i] = paradas[i];
            }
            else{
                temp[i + 1] = paradas[i];
            }
        }

        // Garantizando el cambio.
        paradas = temp;
    }
}