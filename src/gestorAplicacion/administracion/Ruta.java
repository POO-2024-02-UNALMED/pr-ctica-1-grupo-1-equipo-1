package gestorAplicacion.administracion;
import gestorAplicacion.operacion.logistica.Bus;

public class Ruta{
    private int idRuta;
    private Bus busAsociado;
    private int horaSalida;  // Cambiar por objeto de tiempo
    private int horaLlegada; // Cambiar por objeto de tiempo
    private String lugarInicio;
    private String lugarFinal;
    private String[] paradas;

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

    public String getLugarInicio(){
        return lugarInicio;
    }

    public void setLugarInicio(String nuevoLugarInicio){
        lugarInicio = nuevoLugarInicio;
    }

    public String getLugarFinal(){
        return lugarFinal;
    }

    public void setLugarFinal(String nuevoLugarFinal){
        lugarFinal = nuevoLugarFinal;
    }

    public String[] getParadas(){
        return paradas;
    }

    public void setParadas(String[] nuevasParadas){
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
