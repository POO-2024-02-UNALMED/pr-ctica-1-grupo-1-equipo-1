package gestorAplicacion.operacion.logistica;
import java.io.Serializable;

import gestorAplicacion.operacion.individuos.Pasajero;

public class Asiento implements Serializable{

    //Atributos//

    private int idAsiento;
    private Bus bus;
    private boolean estado = true;
    private Pasajero Usuario;
    private String integridad;

    public Asiento(Pasajero pasajero){
        this.Usuario = pasajero;
        this.estado = true;
    }
    public Asiento(){
        this.estado = true;
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

