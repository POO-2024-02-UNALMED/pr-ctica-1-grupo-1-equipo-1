package gestorAplicacion.operacion.logistica;

import gestorAplicacion.operacion.individuos.Pasajero;
import java.io.Serializable;
public class Asiento implements Serializable{

    //Atributos//

    private int idAsiento;
    private Bus bus;
    private boolean estado;
    private Pasajero Usuario;
    private String integridad;

    public Asiento(Pasajero pasajero){
        this.Usuario = pasajero;
    }
    // Getters y Setters//

    public Bus getBus(){
        return bus;
    }
    
    public void setBus(Bus bus){
        this.bus = bus;
    }

    public boolean isEstado(){
        return estado;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }

    public int getIdAsiento(){
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento){
        this.idAsiento = idAsiento;
    }

    public String getIntegridad(){
        return integridad;
    }

    public void setIntegridad(String integridad){
        this.integridad = integridad;
    }

    public Pasajero getUsuario(){
        return Usuario;
    }

    public void setUsuario(Pasajero usuario){
        Usuario = usuario;
    }

    //Metodos de Clase//

    //Metodos de Instancia//

    public void danoAleatorio(){

    }
    public void consultarReserva(){

    }
}

