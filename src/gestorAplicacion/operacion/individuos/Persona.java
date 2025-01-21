package gestorAplicacion.operacion.individuos;

import java.io.Serializable;

public abstract class Persona implements Serializable{
    protected String nombre;
    protected int edad;
    protected int id;

    abstract String mostrarDatos();
}