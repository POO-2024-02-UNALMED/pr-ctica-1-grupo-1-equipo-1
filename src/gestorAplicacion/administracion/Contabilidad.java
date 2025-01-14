package gestorAplicacion.administracion;
import java.util.ArrayList;

public  class Contabilidad{
    //Atributos//

    private double ingresos;
    private static ArrayList<Factura> ventas;

    //Getters y Setters//

    public double getIngresos(){
        return ingresos;
    }

    public void setIngresos(double ingresos){
        this.ingresos = ingresos;
    }

    public static ArrayList<Factura> getVentas(){
        return ventas;
    }

    public static void setVentas(ArrayList<Factura> ventas){
        Contabilidad.ventas = ventas;
    }

    //Metodos de clase//

    //Metodos de Instancia//

    public void pagarMantenimiento(){

    }
    public void pagarReparacion(){

    }
    public void pagarEmpleado(){
        
    }


}