package gestorAplicacion.operacion.individuos;

public class Chofer{
    private static int cantidadChoferes;
    private int sueldo;
    private int cantidadHorasConducidas;
    private Boolean disponible;

    // Métodos get-set
    public static int getCantidadChoferes(){
        return cantidadChoferes;
    }

    public static void setCantidadChoferes(int nuevaCantidadChoferes){
        cantidadChoferes = nuevaCantidadChoferes;
    }

    public int getSueldo(){
        return sueldo;
    }

    public void setSueldo(int nuevoSueldo){
        sueldo = nuevoSueldo;
    }

    public int getCantidadHorasConducidas(){
        return cantidadHorasConducidas;
    }

    public void setCantidadHorasConducidas(int nuevaCantidadHorasConducidas){
        cantidadHorasConducidas = nuevaCantidadHorasConducidas;
    }

    public Boolean getDisponible(){
        return disponible;
    }

    public void setDisponible(Boolean nuevaDisponible){
        disponible = nuevaDisponible;
    }

    // Métodos de la clase
    public void calcularSalario(){

    }

    public void registrarTurno(){

    }

    public void registrarViaje(){
        
    }
}
