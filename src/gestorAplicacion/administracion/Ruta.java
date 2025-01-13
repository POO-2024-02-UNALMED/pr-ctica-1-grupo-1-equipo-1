package gestorAplicacion.administracion;
import gestorAplicacion.operacion.logistica.Bus;
import java.util.ArrayList;

public class Ruta{
    private static int totalRutas;
    private int idRuta;
    private Bus busAsociado;
    private int horaSalida;  // Cambiar por objeto de tiempo
    private int horaLlegada; // Cambiar por objeto de tiempo
    private int lugarInicio;
    private int lugarFinal;
    private ArrayList<Parada> paradas;

    public Ruta(Bus busAsociado, int horaSalida, int horaLlegada, // Cambiar por objetos de tiempo
     int lugarInicio, int lugarFinal, ArrayList<Parada> paradas){
        totalRutas++;
        this.idRuta = totalRutas;
        this.busAsociado = busAsociado;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.lugarInicio = lugarInicio;
        this.lugarFinal = lugarFinal;
    }

    public Ruta(int lugarInicio, int lugarFinal, ArrayList<Parada> paradas){
        this(null, 0, 0, lugarInicio, lugarFinal, paradas);
    }

    // Métodos get-set
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

    public int getHoraSalida(){
        return horaSalida;
    }

    public void setHoraSalida(int nuevaHoraSalida){
        horaSalida = nuevaHoraSalida;
    }

    public int getHoraLlegada(){
        return horaLlegada;
    }

    public void setHoraLlegada(int nuevaHoraLlegada){
        horaLlegada = nuevaHoraLlegada;
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

    // Métodos de la clase
    public void calcularDistancia(){

    }

    public void agragarParada(){

    }

    public void finalizarRuta(){

    }
}
