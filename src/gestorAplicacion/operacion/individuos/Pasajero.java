package gestorAplicacion.operacion.individuos;

import gestorAplicacion.operacion.logistica.Asiento;
import gestorAplicacion.operacion.logistica.Bus;
import gestorAplicacion.operacion.logistica.Maleta;
import gestorAplicacion.administracion.*;
import gestorAplicacion.administracion.Red.Parada;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.lang.reflect.Array;
import java.time.Duration;

public class Pasajero extends Persona {

    // Atributos //
    private static ArrayList<Pasajero> pasajerosEnSistema;
    private Maleta[] maletas;
    private double wallet;
    private Factura factura;
    private int numReembolsoDisp;
    private Pasajero acompanante;

    // Getters y Setters//

    public Pasajero getacompanamte() {
        return acompanante;
    }

    public void setPasajero(Pasajero acompanante) {
        this.acompanante = acompanante;
    }
    static public ArrayList<Pasajero> getPasajerosEnSistema() {
        return pasajerosEnSistema;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setPasajerosEnSistema(ArrayList<Pasajero> pasajerosEnSistema) {
        this.pasajerosEnSistema = pasajerosEnSistema;
    }
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

    // Generar puntaje aleatorio.
    public int getPuntajeSatisfaccionPasajeros() {
        return (int) (Math.random() * 5);
    }

    // Ingresar y Sacar dinero
    public void AgregarWallet(double monto){
        this.wallet += monto;
    }
    public void SacarWallet(double monto){
        if (this.wallet >= monto) {
            this.wallet -= monto;
            }else {
                System.out.println("No tienes suficiente dinero en tu billetera");
            }
        }

    public int getNumReembolsoDisp() {
        return numReembolsoDisp;
    }

    public void setNumReembolsoDisp(int numReembolsoDisp) {
        this.numReembolsoDisp = numReembolsoDisp;
    }

    // Método abstracto
    public String mostrarDatos() {
        return this.nombre + this.edad + this.id;
    }

    public String getNombre() {
        return this.nombre;
    }
    // Metodos de Clase//

    // Metodos Funcionalidad 3

    //Este Metodo Hace conexion con metodo eliminarPasajero de bus
    public String  EliminarPasajes(){
        Bus bus = this.getFactura().getRutaElegida().getBusAsociado();
        String mensaje = bus.eliminarPasajero(this);
        return mensaje;
    }
    public void  RevertirPasajes(){
        Bus bus = this.getFactura().getRutaElegida().getBusAsociado();
        bus.asignarPasajero(this);
    }

    public ArrayList<Object> solicitarReembolso( int idPasajeroUser, int idFacturaUser,LocalDateTime horaZero){
        ArrayList<Object> respuesta = new ArrayList<>();
        //Calculo la diferencia con la fecha de inicio del programa
        LocalDateTime nowTime = LocalDateTime.now(); //Obtengo el tiempo exacto de solicitud
        Duration diferenciaZero = Duration.between(horaZero, nowTime);
        String mensaje="";
        int numReembolsoDispUser = this.getNumReembolsoDisp();
        // Verificar si la diferencia es mayor a un año (en segundos)
        long secondsInOneYear = 365L * 24 * 60 * 60; // 365 días en segundos
        if (diferenciaZero.getSeconds() > secondsInOneYear) {
            numReembolsoDispUser = 2;
        } else if (numReembolsoDispUser == 0) {
            mensaje= "El Pasajero no tiene mas reembolsos por este ano, segun los terminos y condiciones";
            respuesta.add(mensaje);
        }


        ArrayList<Factura> facturas = Contabilidad.getVentas();
        for (Factura factura : facturas) {// Se obtiene el array de las facturas y se itera hasta encontrar una que coincida con la recibida
            int idFactura = factura.getIdFactura();

            if (idFactura == idFacturaUser){
                int idPasajero = factura.getIdUsuario();

                if (idPasajeroUser == idPasajero){

                    
                    LocalDateTime timecreation = factura.getFecha();
                    
                    // Calcular la diferencia entre las fechas
                    Duration diferencia = Duration.between(timecreation, nowTime);

                    if (diferencia.toHours() < 24) {
                        mensaje= "El reembolso no puede hacerse efectivo, La diferencia no es mayor a 24 horas segun lo establecido por terminos y condiciones.";
                        respuesta.add(mensaje);
                    } else if (factura.getMetodoPago().toString().equals("Efectivo")) {
                        mensaje= "El reembolso no es posible, el metodo de pago utilizado fue en efectivo, un metodo de pago invalido para un reembolso";
                        respuesta.add(mensaje);
                    } else{
                            mensaje= "Su solicitud sigue en proceso, valoramos su paciencia y gracias por escojernos";
                            // Actualizar el número de reembolsos disponibles
                            // this.setNumReembolsoDisp(numReembolsoDispUser - 1);
                            respuesta.add(mensaje);
                            respuesta.add(factura);
                    }
                    }else { mensaje= "El documento no coincide con el del pasajero";}
                }else{ mensaje = "No existe Factura asociada al numero de la factura";}
        }

        return respuesta;
    }
        // Metodo de clase
        public static Pasajero BuscarPasajero(String nombre, int id){
            // Buscar el pasajero 
            ArrayList<Pasajero> pasajeros = Pasajero.getPasajerosEnSistema();
            for (Pasajero pasajero : pasajeros) {
                if (pasajero.getNombre().equals(nombre) && pasajero.getId() == id
                || pasajero.getNombre().equals(nombre) && pasajero.getId() == id)
                return pasajero;
                }
                return null;

        }   
    // Metodos de Instancia//

    public void registrarAcompanante(String nombre, String identificacion, int edad) {
        if (edad >= 18) {
            this.acompanante = new Pasajero(nombre, identificacion, edad);
        } else {
            throw new IllegalArgumentException("El acompañante debe ser mayor de edad.");
        }
    }

    public void comprarTiquete(String lugarInicio, String lugarFinal) {
        List<Parada> rutasDisponibles = Ruta.filtrarRutas(lugarInicio, lugarFinal);

        if (rutasDisponibles.isEmpty()) {
            return;
        }

        Ruta rutaSeleccionada = rutasDisponibles.get(0);
        Bus busAsignado = rutaSeleccionada.getBusAsociado();

        Parada[] paradas = rutaSeleccionada.getParadas();
        Asiento[] asientosDisponibles = busAsignado.getAsientos().toArray(new Asiento[0]);

        Asiento asientoSeleccionado = asientosDisponibles[0];
        this.setAsiento(asientoSeleccionado);
        asientoSeleccionado.setUsuario(this);
        asientoSeleccionado.setEstado(false);
    }

    public boolean aceptarCambio() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Estimado " + nombre + ", se ha detectado un cambio de horario disponible.");
        System.out.println("¿Acepta cambiar su horario por una compensacion? (Si/No)");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        while (!respuesta.equals("si") && !respuesta.equals("no")) {
            System.out.println("Respuesta no valida. Por favor, escriba 'Si' o 'No'.");
            respuesta = scanner.nextLine().trim().toLowerCase();
        }
        scanner.close();
        return respuesta.equals("si") ? true : false;
    }

    public void pedirFactura() {

    }

    public void agregarMaleta() {

    }

    public void validarDestino() {

    }

    public void validarHora() {

    }

    public void proveerInformacion() {

    }

}