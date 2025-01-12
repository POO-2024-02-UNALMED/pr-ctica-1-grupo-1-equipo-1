package individuos;

import src.gestorAplicacion.administracion.Factura;

public class Pasajero{

    // Atributos //

    private Maleta[] maletas;
    private double wallet;
    private Factura factura;


    //Getters y Setters//

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Maleta[] getMaletas() {
        return maletas;
    }

    public void setMaletas(Maleta[] maletas) {
        this.maletas = maletas;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
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