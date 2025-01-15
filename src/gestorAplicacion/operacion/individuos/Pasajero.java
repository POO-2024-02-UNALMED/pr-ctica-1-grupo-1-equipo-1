package gestorAplicacion.operacion.individuos;
import gestorAplicacion.operacion.logistica.Maleta;
import gestorAplicacion.administracion.Factura;

public class Pasajero extends Persona{

    // Atributos //

    private Maleta[] maletas;
    private double wallet;
    private Factura factura;


    //Getters y Setters//

    public Factura getFactura(){
        return factura;
    }

    public void setFactura(Factura factura){
        this.factura = factura;
    }

    public Maleta[] getMaletas(){
        return maletas;
    }

    public void setMaletas(Maleta[] maletas){
        this.maletas = maletas;
    }

    public double getWallet(){
        return wallet;
    }

    public void setWallet(double wallet){
        this.wallet = wallet;
    }

    // Método abstracto
    public String mostrarDatos(){
        return this.nombre + this.edad + this.id;
    }

    //Metodos de Clase//


    //Metodos de Instancia//

    public void comprarTiquete(){

    }
    public void  pedirFactura(){

    }
    public void agregarMaleta(){

    }
    public void validarDestino(){

    }
    public void validarHora(){

    }
    public void proveerInformacion(){

    }

}