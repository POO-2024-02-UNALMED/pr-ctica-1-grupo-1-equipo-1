package gestorAplicacion.administracion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import gestorAplicacion.operacion.logistica.Asiento;
import gestorAplicacion.administracion.Red.Parada;

import gestorAplicacion.operacion.logistica.Bus;
import gestorAplicacion.operacion.logistica.Maleta;

import java.io.Serializable;

public class Factura implements Serializable {
    // Atributos
    public enum MetodoPago {
        Efectivo, TarjetadeCredito, TarjetadeDebito, Transferencia
    }

    private int idFactura;
    private String usuarioNombre;
    private int idUsuario;
    private double valor;
    private int numAsientosAsignados;
    private ArrayList<Asiento> asientosAsignados;
    private LocalDateTime fecha;
    private int cantidadMaletas;
    private Ruta rutaElegida;
    private Parada origen;
    private Parada destino;
    private MetodoPago metodoPago;

    // Constructores
    public Factura(String usuarioNombre, int idFactura, double valorTiquete, int numAsientosAsignados,
            Asiento asientosAsignados, LocalDateTime fecha, int cantidadMaletas, Ruta rutaElegida, String origen,
            String destino, String met) {
        this.usuarioNombre = usuarioNombre;
        this.idFactura = idFactura;
        this.valor = valorTiquete;
        this.numAsientosAsignados = numAsientosAsignados;
        this.asientosAsignados.add(asientosAsignados);
        this.fecha = fecha;
        this.cantidadMaletas = cantidadMaletas;
        this.rutaElegida = rutaElegida;
        this.origen = Parada.valueOf(origen);
        this.destino = Parada.valueOf(destino);
        this.metodoPago = MetodoPago.valueOf(met);

    }

    public Factura(String usuarioNombre, int idFactura, double valorTiquete, int numAsientosAsignados,
            ArrayList<Asiento> asientosAsignados, LocalDateTime fecha, int cantidadMaletas, Ruta rutaElegida,
            Parada origen,
            Parada destino, String met) {
        this.usuarioNombre = usuarioNombre;
        this.idFactura = idFactura;
        this.valor = valorTiquete;
        this.numAsientosAsignados = numAsientosAsignados;
        this.asientosAsignados = asientosAsignados;
        this.fecha = fecha;
        this.cantidadMaletas = cantidadMaletas;
        this.rutaElegida = rutaElegida;
        this.origen = origen;
        this.destino = destino;
        this.metodoPago = MetodoPago.valueOf(met);

    }
    // Getters y Setters//

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNumAsientosAsignados() {
        return numAsientosAsignados;
    }

    public void setnumAsientosAsignados(int numAsientosAsignados) {
        this.numAsientosAsignados = numAsientosAsignados;
    }

    public int getCantidadMaletas() {
        return cantidadMaletas;
    }

    public void setCantidadMaletas(int cantidadMaletas) {
        this.cantidadMaletas = cantidadMaletas;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Ruta getRutaElegida() {
        return rutaElegida;
    }

    public void setRutaElegida(Ruta rutaElegida) {
        this.rutaElegida = rutaElegida;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Parada getOrigen() {
        return origen;
    }

    public void setOrigen(Parada origen) {
        this.origen = origen;
    }

    public Parada getDestino() {
        return destino;
    }

    public void setDestino(Parada destino) {
        this.destino = destino;
    }

    // Metodos de clase//

    // Funcionalidad 3
    public String verificarBusAsociado() {
        Bus bus = this.rutaElegida.getBusAsociado();
        String mensaje;
        if (bus != null) {
            mensaje = "Existe un Bus Asociado a la ruta de la factura, Su solicitud seguira en proceso";
        } else {
            mensaje = "Lo sentimos pero no existe bus Asociado a la ruta de dicha factura, por lo cual el reembolso no puede ser efectivo";
        }

        return mensaje;
    }

    public String verificarRutaAsociada() {
        // Obtener el bus asociado a la ruta
        Bus bus = this.rutaElegida.getBusAsociado();
        String mensaje = "";

        ArrayList<Asiento> asientosBus = bus.getAsientos();
        for (Asiento asiento : asientosBus) {
            if (asiento.getUsuario().getNombre().equals(this.usuarioNombre)) {
                mensaje = "El usuario ya tiene una reserva asociada a esta ruta";
                Ruta rutaElegida = getRutaElegida();
                LocalDateTime fechaSalida = rutaElegida.getFechaSalida();
                if (LocalDateTime.now().isBefore(fechaSalida)) {
                    System.out.println(
                            "El asiento liberado puede ser reservado nuevamente, Su reembolso sigue en proceso");
                } else {
                    System.out.println(
                            "Es demasiado tarde Para Hacer la reservacion, Proximamente el Bus saldra a su debida Ruta.");
                }
            } else {
                mensaje = "Lo sentimo El usuario no tiene una reserva asociada a esta ruta";
            }
        }
        return mensaje;
    }

    public boolean verificarMaletaBusAsociado(Integer numsMaleta) {
        Bus bus = this.rutaElegida.getBusAsociado();
        boolean verificacion = false;
        for (Maleta maleta : bus.getEquipaje()) {
            if (maleta.getIdMaleta() == numsMaleta) {
                verificacion = true;
                break;
            }
        }
        if (!verificacion) {
            return false;
        }
        return true;

    }

    public String EliminarMaletaBusAsociado(ArrayList<Integer> numsMaletas) {
        Bus bus = this.rutaElegida.getBusAsociado();
        String mensaje = "";
        // Vamos a recorrer la Array de el equipaje y si el id de la maleta coincide con
        // alguna del Array entonces sera eliminada
        for (Integer integer : numsMaletas) {
            for (Maleta maleta : bus.getEquipaje()) {
                if (maleta.getIdMaleta() == integer) {
                    bus.getEquipaje().remove(maleta);
                    mensaje = "La maleta con el numero de identificacion " + integer
                            + " ha sido eliminada del equipaje del bus";
                } else {
                    // Esta es una segunda verificacion pero realmente es solo para verificar
                    mensaje = "No se pudo hacer el reembolso, La maleta con el numero de identificacion " + integer
                            + " no existe en el bus asociado a la factura";
                }
            }
        }
        return mensaje;
    }

    // Metodos de Instancia//

    public void ImprimirFactura() {

    }

    public void AplicarDescuento() {

    }

    public void asientoAsignado() {

    }

    public void cantidadMaletas() {

    }

    public void verificarEdad() {

    }
}