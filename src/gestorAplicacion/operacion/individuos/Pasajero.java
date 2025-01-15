package gestorAplicacion.operacion.individuos;
import gestorAplicacion.operacion.logistica.Maleta;
import gestorAplicacion.administracion.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;
public class Pasajero extends Persona{

    // Atributos //

    private Maleta[] maletas;
    private double wallet;
    private Factura factura;
    private int numReembolsoDisp;

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

    public int getNumReembolsoDisp() {
        return numReembolsoDisp;
    }

    public void setNumReembolsoDisp(int numReembolsoDisp) {
        this.numReembolsoDisp = numReembolsoDisp;
    }

    // Método abstracto
    public String mostrarDatos(){
        return this.nombre + this.edad + this.id;
    }

    //Metodos de Clase//

    // Metodos Funcionalidad 3
    public String solicitarReembolso( int idPasajeroUser, int idFacturaUser,LocalDateTime horaZero){
        //Calculo la diferencia con la fecha de inicio del programa
        Duration diferenciaZero = Duration.between(horaZero, nowTime);

        int numReembolsoDispUser = this.getNumReembolsoDisp();
        // Verificar si la diferencia es mayor a un año (en segundos)
        long secondsInOneYear = 365L * 24 * 60 * 60; // 365 días en segundos
        if (diferenciaZero.getSeconds() > secondsInOneYear) {
            numReembolsoDispUser = 2;
        } else if (numReembolsoDispUser == 0) {
            return "El Pasajero no tiene mas reembolsos por este ano, segun los terminos y condiciones";}


        ArrayList<Factura> facturas = Contabilidad.getVentas();
        for (Factura factura : facturas) {// Se obtiene el array de las facturas y se itera hasta encontrar una que coincida con la recibida
            int idFactura= factura.getIdFactura();

            if (idFactura == idFacturaUser){
                int idPasajero = factura.getIdUsuario();

                if (idPasajeroUser == idPasajero){

                    LocalDateTime nowTime = LocalDateTime.now(); //Obtengo el tiempo exacto de solicitud
                    LocalDateTime timecreation = factura.getFecha();
                    // Calcular la diferencia entre las fechas
                    Duration diferencia = Duration.between(timecreation, nowTime);

                    if (diferencia.toHours() < 24) {
                        return "El reembolso no puede hacerse efectivo, La diferencia no es mayor a 24 horas segun lo establecido por terminos y condiciones.";
                    } else if(factura.getMetodoPago() == "Efectivo") {
                        return "El reembolso no es posible, el metodo de pago utilizado fue en efectivo, un metodo de pago invalido para un reembolso"
                        else{
                            //Seguir Aca No terminado
                        }
                    }
                }
                else { return "El documento no coincide con el del pasajero";}
            }else{ return "No existe Factura asociada al numero de la factura";}
        }
    }


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