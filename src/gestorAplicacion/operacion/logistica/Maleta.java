package gestorAplicacion.operacion.logistica;
import gestorAplicacion.operacion.individuos.Pasajero;

public class Maleta{

    //Atributos//

    private int idMaleta;
    private Pasajero Propietario;
    private int peso;

    //Getters y Setters//

    public int getIdMaleta(){
        return idMaleta;
    }

    public void setIdMaleta(int idMaleta){
        this.idMaleta = idMaleta;
    }

    public int getPeso(){
        return peso;
    }

    public void setPeso(int peso){
        this.peso = peso;
    }

    public Pasajero getPropietario(){
        return Propietario;
    }

    public void setPropietario(Pasajero propietario){
        Propietario = propietario;
    }

    //Metodos de clase//

    // Metodos de instancia //

    public void mostrarDetalles(){

    }
    public void asociarPropietario(){

    }
    public void comprobarLimitesPeso(){

    }
}