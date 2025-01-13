package gestorAplicacion.operacion.logistica;
import gestorAplicacion.operacion.logistica.Asiento;

public class Bus{
    private String placa;
    private int cantidadAsientos;
    private Asiento[] asientos;
    private Boolean disponible;
    private int capacidadMaletas;
    private int kilometrosRecorridos;

    // Métodos get-set
    public String getPlaca(){
        return placa;
    }

    public void setPlaca(String nuevaPlaca){
        placa = nuevaPlaca;
    }

    public int getCantidadAsientos(){
        return cantidadAsientos;
    }

    public void setCantidadAsientos(int nuevaCantidadAsientos){
        cantidadAsientos = nuevaCantidadAsientos;
    }

    public Asiento[] getAsientos(){
        return asientos;
    }

    public void setAsientos(Asiento[] nuevosAsientos){
        asientos = nuevosAsientos;
    }

    public Boolean isDisponible(){
        return disponible;
    }

    public void setDisponible(Boolean nuevaDisponible){
        disponible = nuevaDisponible;
    }

    public int getCapacidadMaletas(){
        return capacidadMaletas;
    }

    public void setCapacidadMaletas(int nuevaCapacidadMaletas){
        capacidadMaletas = nuevaCapacidadMaletas;
    }

    public int getKilometrosRecorridos(){
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(int nuevosKilometrosRecorridos){
        kilometrosRecorridos = nuevosKilometrosRecorridos;
    }

    //Métodos de la clase
    public void reparar(){

    }

    public void verificarIntegridad(){

    }

    public void calcularConsumoCombustible(){

    }

    public float danoAleatorio(){
        return 0;
    }
}