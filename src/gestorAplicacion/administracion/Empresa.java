package gestorAplicacion.administracion;
import gestorAplicacion.operacion.individuos.Chofer;
import gestorAplicacion.operacion.logistica.Bus;

public class Empresa{
    private String nombre;
    private Chofer[] empleados;
    private Bus[] busesTotales;
    private Bus[] busesDisponibles;
    private Ruta[] rutas;
    private int caja;

    // Métodos get-set
    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nuevoNombre){
        nombre = nuevoNombre;
    }

    public Chofer[] getEmpleados(){
        return empleados;
    }

    public void setEmpleados(Chofer[] nuevosEmpleados){
        empleados = nuevosEmpleados;
    }

    public Bus[] getBusesTotales(){
        return busesTotales;
    }

    public void setBusesTotales(Bus[] nuevosBusesTotales){
        busesTotales = nuevosBusesTotales;
    }

    public Bus[] getBusesDisponibles(){
        return busesDisponibles;
    }

    public void setBusesDisponibles(Bus[] nuevosBusesDisponibles){
        busesDisponibles = nuevosBusesDisponibles;
    }

    public Ruta[] getRutas(){
        return rutas;
    }

    public void setRutas(Ruta[] nuevasRutas){
        rutas = nuevasRutas;
    }

    public int getCaja(){
        return caja;
    }

    public void setCaja(int nuevaCaja){
        caja = nuevaCaja;
    }

    // Métodos de la clase
    public void asignarRuta(Bus bus){

    }

    public void reportarFinanzas(){

    }

    public void contratarEmpleado(){
        
    }
}
