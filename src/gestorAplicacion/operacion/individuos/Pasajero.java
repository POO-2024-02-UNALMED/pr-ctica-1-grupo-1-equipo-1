package gestorAplicacion.operacion.individuos;

import gestorAplicacion.operacion.logistica.Asiento;
import gestorAplicacion.operacion.logistica.Bus;
import gestorAplicacion.operacion.logistica.Maleta;
import gestorAplicacion.administracion.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.lang.reflect.Array;
import java.time.Duration;

public class Pasajero extends Persona {

    // Atributos //

    private Maleta[] maletas;
    private double wallet;
    private Factura factura;
    private int numReembolsoDisp;

    // Getters y Setters//

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
            int idFactura= factura.getIdFactura();

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
                        respuesta.add(mensaje);}
                        else{
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
        
    // Metodos de Instancia//

    public void comprarTiquete(String origen, String destino) {
        // Paso 1: Buscar las rutas disponibles para el origen y destino seleccionados
        List<Ruta> rutasDisponibles = Ruta.filtrarRutas(origen, destino);

        // Paso 2: Si no hay rutas disponibles, informamos al usuario
        if (rutasDisponibles.isEmpty()) {
            System.out.println("No hay rutas disponibles entre " + origen + " y " + destino);
            return;
        }

        // Paso 3: Mostrar las rutas disponibles al usuario
        System.out.println("Rutas disponibles entre " + origen + " y " + destino + ":");
        int i = 1;
        for (Ruta ruta : rutasDisponibles) {
            System.out.println(i + ". " + "Empresa: " + ruta.getBusAsociado().getEmpresa().getNombre() +
                    ", Tipo de asiento: " + (ruta.getBusAsociado().getAsientos().equals("VIP") ? "VIP" : "Estándar") +
                    ", Escalas: " + ruta.getParadas());
            i++;
        }

        // Paso 4: Solicitar al pasajero elegir una ruta
        System.out.print("Seleccione la opción de ruta (1-" + rutasDisponibles.size() + "): ");
        int opcionRuta = new Scanner(System.in).nextInt();

        // Validación de la opción seleccionada
        if (opcionRuta < 1 || opcionRuta > rutasDisponibles.size()) {
            System.out.println("Opción inválida");
            return;
        }

        // Paso 5: Obtener la ruta seleccionada
        Ruta rutaSeleccionada = rutasDisponibles.get(opcionRuta - 1);

        // Paso 6: Mostrar detalles del bus asignado a la ruta seleccionada
        Bus busAsignado = rutaSeleccionada.getBusAsociado();
        System.out.println("Bus asignado para la ruta: " + busAsignado.getPlaca() +
                " con capacidad de " + busAsignado.getCantidadAsientos() + " asientos.");

        // Paso 7: Selección del tipo de asiento (Estándar o VIP)
        System.out.print("Seleccione el tipo de asiento (1 para Estándar, 2 para VIP): ");
        int tipoAsiento = new Scanner(System.in).nextInt();

        if (tipoAsiento != 1 && tipoAsiento != 2) {
            System.out.println("Selección de tipo de asiento inválido.");
            return;
        }

        // Paso 8: Mostrar los asientos disponibles en el bus
        ArrayList<Asiento> asientosDisponiblesList = busAsignado.getAsientos(); // Obtener el ArrayList
        Asiento[] asientosDisponibles = asientosDisponiblesList.toArray(new Asiento[0]); // Convertir a un arreglo

        System.out.println("Asientos disponibles:");
        i = 1;
        for (Asiento asiento : asientosDisponibles) {
            if (asiento.isEstado()) { // Verificamos si el asiento está disponible
                System.out.println(i + ". Asiento ID: " + asiento.getIdAsiento());
            }
            i++;
        }

        // Paso 9: Solicitar al pasajero seleccionar un asiento
        System.out.print("Seleccione el número de asiento: ");
        int opcionAsiento = new Scanner(System.in).nextInt();

        // Validación de la opción seleccionada
        if (opcionAsiento < 1 || opcionAsiento > asientosDisponibles.length
                || !asientosDisponibles[opcionAsiento - 1].isEstado()) {
            System.out.println("Opción de asiento inválida o asiento no disponible.");
            return;
        }

        // Paso 10: Asignar el asiento seleccionado al pasajero
        Asiento asientoSeleccionado = asientosDisponibles[opcionAsiento - 1];
        this.setAsiento(asientoSeleccionado);
        asientoSeleccionado.setUsuario(this);
        asientoSeleccionado.setEstado(false); // El asiento ya no está disponible

        // Paso 11: Confirmación de la compra
        System.out.println("Tiquete comprado exitosamente!");
        System.out.println("Ruta: " + rutaSeleccionada.getLugarInicio() + " - " + rutaSeleccionada.getLugarFinal());
        System.out.println("Asiento asignado: " + asientoSeleccionado.getIdAsiento());
        System.out.println("Bus: " + busAsignado.getPlaca() + " | Tipo de asiento: " +
                (tipoAsiento == 1 ? "Estándar" : "VIP"));

        // Paso 12: Proseguir con la gestión del equipaje y la generación de la factura
        // (esto lo continuamos después)
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