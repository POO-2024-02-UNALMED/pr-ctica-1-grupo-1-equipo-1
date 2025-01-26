package gestorAplicacion.operacion.logistica;

import gestorAplicacion.administracion.Empresa;
import gestorAplicacion.administracion.Ruta;
import gestorAplicacion.operacion.individuos.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bus {
    public enum PesoMaxEquipaje { // Esto nos dira cual es el maximo que soporta el bus
        LIGERO(500), // Peso máximo en kilogramos
        MEDIO(750),
        PESADO(1000),
        EXTRA_PESADO(1250);

        private final int PESOMAXIMO;

        // Constructor del enum
        PesoMaxEquipaje(int PESOMAXIMO) {
            this.PESOMAXIMO = PESOMAXIMO;
        }

        // Método para obtener el peso máximo
        public int getPESOMAXIMO() {
            return PESOMAXIMO;
        }
    }

    private String placa;
    private int cantidadAsientos;
    private ArrayList<Asiento> asientos = new ArrayList<>(); // Initialize asientos here
    private int kilometrosRecorridos = 0;
    private ArrayList<Ruta> rutasFuturas = new ArrayList<>();
    private Empresa empresa;
    private ArrayList<Maleta> equipaje = new ArrayList<>(); // Initialize equipaje here
    private double consumoReportado;

    // Constructores
    public Bus(String placa, int cantidadAsientos, PesoMaxEquipaje pesoMaxEquipaje) {
        this(placa, cantidadAsientos, pesoMaxEquipaje, null);
    }

    public Bus(String placa, int cantidadAsientos, PesoMaxEquipaje pesoMaxEquipaje, ArrayList<Ruta> rutasFuturas) {
        this.placa = placa;
        this.cantidadAsientos = cantidadAsientos;
        // Removed duplicate line: this.cantidadAsientos = cantidadAsientos;
        if (rutasFuturas != null) {
            this.setRutasFuturas(rutasFuturas);
        }
    }

    // Métodos get-set
    public ArrayList<Maleta> getEquipaje() {
        return equipaje;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String nuevaPlaca) {
        placa = nuevaPlaca;
    }

    public int getCantidadAsientos() {
        return cantidadAsientos;
    }

    public void setCantidadAsientos(int nuevaCantidadAsientos) {
        cantidadAsientos = nuevaCantidadAsientos;
    }

    public ArrayList<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(ArrayList<Asiento> nuevosAsientos) {
        asientos = nuevosAsientos;
    }

    public int getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(int nuevosKilometrosRecorridos) {
        kilometrosRecorridos = nuevosKilometrosRecorridos;
    }

    public ArrayList<Ruta> getRutasFuturas() {
        return rutasFuturas;
    }

    // Changed return type to void as it doesn't seem to be used
    public void setRutasFuturas(ArrayList<Ruta> nuevasRutasFuturas) {
        /*
         * Se añadirán todas las rutas en forma ascendente sin repeticiones,
         * y se devuelven las rutas que no pudieron incluirse.
         */

        // Incluyendo las rutas que se puedan incorporar.
        rutasFuturas = new ArrayList<>(); // Initialize rutasFuturas here
        for (Ruta nuevaRuta : nuevasRutasFuturas) {
            this.anadirRuta(nuevaRuta); // No need to check return value here
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa nuevaEmpresa) {
        empresa = nuevaEmpresa;
    }

    public double getConsumoReportado() {
        return consumoReportado;
    }

    public void setConsumoReportado(double nuevoConsumoreportado) {
        consumoReportado = nuevoConsumoreportado;
    }

    // Métodos de instancia
    public Boolean isDisponible(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        /*
         * Determina si el rango [fecha inicial, fecha final] se cruza con los horarios
         * de las rutas que el bus debe cumplir.
         * 
         * Parámetros:
         * - fechaInicial: LocalDateTime,
         * Comienzo del rango horario
         * - fechaFinal: LocalDateTime,
         * Conclusión del rango horario
         * 
         * Retorna:
         * - disponibilidad: Boolean,
         * Valor que especifica si el bus está disponible en ese rango horario.
         */

        // Verificación de errores.
        // Changed condition to check if fechaFinal is after fechaInicial
        if (fechaFinal.isAfter(fechaInicial)) {
            return false;
        }

        // Se busca si alguna de las rutas futuras
        for (Ruta ruta : rutasFuturas) {
            // Check if the given time range overlaps with the route's time range
            if (!(fechaFinal.isBefore(ruta.getFechaSalida()) || fechaInicial.isAfter(ruta.getFechaLlegada()))) {
                return false; // Overlap found, bus is not available
            }
        }

        return true; // No overlaps found, bus is available
    }

    public Boolean anadirRuta(Ruta nuevaRuta) {
        /*
         * Busca si se puede agregar la ruta en las ya establecidas para el bus,
         * mostrando una advertencia si la ruta no puede ser añadida.
         * 
         * Parámetros:
         * - nuevaRuta: Ruta,
         * Ruta a ser añadida.
         * 
         * Retorna:
         * - asignado: Boolean,
         * Indica si se pudo añadir la ruta.
         */
            // Verificar si la ruta ya existe en la lista
        if (rutasFuturas.contains(nuevaRuta)) {
            System.out.println("La ruta ya está asignada a este bus.");
            return false;
    }

        for (Ruta ruta : rutasFuturas) {
            // Check for overlaps with existing routes, considering a 1-hour buffer
            if (!(nuevaRuta.getFechaLlegada().plusHours(1).isBefore(ruta.getFechaSalida())
                    || nuevaRuta.getFechaSalida().minusHours(1).isAfter(ruta.getFechaLlegada()))) {
                return false; // Overlap found, cannot add the route
            }
        }

        // No overlaps found, add the route
        rutasFuturas.add(nuevaRuta);
        nuevaRuta.setBusAsociado(this);
        return true;
    }

    public void quitarRuta(Ruta ruta) {
        /*
         * Remueve la ruta especificada en caso de estar presente en una ruta del bus.
         * 
         * Parámetros:
         * - ruta: Ruta,
         * Ruta a ser añadida.
         */

        if (rutasFuturas.contains(ruta)) {
            rutasFuturas.remove(ruta);
            ruta.setBusAsociado(null);
        }
    }

    public String asignarPasajero(Pasajero pasajero) {
        if (asientos.size() < cantidadAsientos) {
            Asiento asiento = new Asiento(pasajero);
            asientos.add(asiento);
            return "Asiento asignado a " + pasajero.getNombre() + " en el bus " + this.placa;
        } else {
            return "No hay asientos disponibles en el bus " + this.placa;
        }
    }

    public String eliminarPasajero(Pasajero pasajero) {
        for (Asiento asiento : asientos) {
            if (asiento.getUsuario() == pasajero) { // Use direct object comparison
                asientos.remove(asiento);
                return "Asiento quitado a " + pasajero.getNombre() + " en el bus " + this.placa;
            }
        }
        return "No se ha encontrado al pasajero " + pasajero.getNombre() + " en el bus " + this.placa;
    }

    public LocalDateTime hallarHueco(int lapso) {
        /*
         * Mira en el horario del bus a ver si encuentra un hueco de la duración
         * especificada.
         * 
         * Parámetros:
         * - lapso: int,
         * Tiempo que se necesita al bus.
         * 
         * Retorna:
         * - fecha: LocalDateTime,
         * Hora en la cual puede iniciar la actividad a reclutarlo.
         */

        if (rutasFuturas.isEmpty()) {
            return LocalDateTime.now(); // If no routes, bus is always available
        }

        // Check for a gap before the first route
        if (Duration.between(LocalDateTime.now(), rutasFuturas.get(0).getFechaSalida()).toHours() > lapso + 2) {
            return rutasFuturas.get(0).getFechaSalida().minusHours(lapso + 2);
        }

        // Check for gaps between existing routes
        for (int i = 0; i < rutasFuturas.size() - 1; i++) {
            if (Duration.between(rutasFuturas.get(i).getFechaLlegada(), rutasFuturas.get(i + 1).getFechaSalida())
                    .toHours() > lapso + 2) {
                return rutasFuturas.get(i).getFechaLlegada().plusHours(1);
            }
        }

        // If no gaps found, return the time after the last route
        return rutasFuturas.get(rutasFuturas.size() - 1).getFechaLlegada().plusHours(1);
    }

    public String toString() {
        return "Soy el bus con placa " + placa;
    }

    // You can implement these methods later if needed
    public void reparar() {
        // Implementation for repairing the bus
    }

    public void verificarIntegridad() {
        // Implementation for checking the bus integrity
    }

    public void calcularConsumoCombustible() {
        // Implementation for calculating fuel consumption
    }

    public float danoAleatorio() {
        // Implementation for generating random damage
        return 0;
    }
}