package gestorAplicacion.operacion.individuos;

public abstract class Persona{
    protected String nombre;
    protected int edad;
    protected int id;

    abstract String mostrarDatos();
}