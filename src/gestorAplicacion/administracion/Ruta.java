package gestorAplicacion.administracion;
import gestorAplicacion.operacion.logistica.Bus;
import java.util.ArrayList;

public class Ruta{
    private static int totalRutas;
    private int idRuta;
    private Bus busAsociado;
    private int fechaSalida;  // Cambiar por objeto de tiempo
    private int fechaLlegada; // Cambiar por objeto de tiempo
    private int lugarInicio;
    private int lugarFinal;
    private ArrayList<Parada> paradas;

    // Constructores
    public Ruta(Bus busAsociado, int fechaSalida, int fechaLlegada, // Cambiar por objetos de tiempo
     int lugarInicio, int lugarFinal, ArrayList<Parada> paradas){
        totalRutas++;
        this.idRuta = totalRutas;
        this.busAsociado = busAsociado;
        this.fechaSalida = fechaSalida;     // Hay que verificar que la fecha de salida sea menor a la de llegada
        this.fechaLlegada = fechaLlegada;   // ""
        this.lugarInicio = lugarInicio;
        this.lugarFinal = lugarFinal;
    }

    public Ruta(int lugarInicio, int lugarFinal, ArrayList<Parada> paradas){
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

    public ArrayList<Parada> getParadas(){
        return paradas;
    }

    public void setParadas(ArrayList<Parada> nuevasParadas){
        paradas = nuevasParadas;
    }

    // Métodos de instancia
    public void calcularDistancia(){

    }

    public void agragarParada(){

    }

    public void finalizarRuta(){

    }
}