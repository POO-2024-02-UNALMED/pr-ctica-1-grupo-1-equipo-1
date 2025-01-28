package gestorAplicacion.operacion.logistica;

import gestorAplicacion.administracion.Empresa;
import gestorAplicacion.administracion.Red.Parada;
import gestorAplicacion.administracion.Ruta;
import gestorAplicacion.operacion.individuos.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;
public class Bus implements Serializable{
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
    private ArrayList<Asiento> asientos = new ArrayList<>(cantidadAsientos); // Initialize asientos here
    private int kilometrosRecorridos = 0;
    private ArrayList<Ruta> rutasFuturas = new ArrayList<>();
    private Empresa empresa;
    private ArrayList<Maleta> equipaje = new ArrayList<>(); // Initialize equipaje here
    private double consumoReportado;
    private Bus.PesoMaxEquipaje pesoMaxEquipaje;
    private static ArrayList<Bus> buses = new ArrayList<>(); // Initialize

    // Constructores
    public Bus(String placa, int cantidadAsientos, Bus.PesoMaxEquipaje pesoMaxEquipaje) {
        this.placa = placa;
        this.cantidadAsientos = cantidadAsientos;
        this.pesoMaxEquipaje = pesoMaxEquipaje;
        for (int i = 0; i < cantidadAsientos; i++) {
            asientos.add(new Asiento());
        }
    }

    public Bus(String placa, int cantidadAsientos, Bus.PesoMaxEquipaje pesoMaxEquipaje, ArrayList<Ruta> rutasFuturas) {
        this.placa = placa;
        this.cantidadAsientos = cantidadAsientos;

        if (rutasFuturas != null) {
            this.setRutasFuturas(rutasFuturas);
        }
        for (int i = 0; i < cantidadAsientos; i++) {
            asientos.add(new Asiento());
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

    public static void eliminarBus(Scanner scanner) {
        System.out.println("Eliminar Bus");
        System.out.print("Placa del Bus a eliminar: ");
        String idRuta = scanner.nextLine().trim().toUpperCase();
        scanner.nextLine(); // Consumir la nueva línea

        // Eliminar la ruta de la empresa
        for (Bus bus : buses) {
            if (bus.getPlaca() == idRuta) { 
                System.out.println("Bus eliminado con éxito.");
                buses.remove(bus);
                break;
            } else {
                System.out.println("No se encontró ninguna bus con esa placa.");
            }
        }
    }

    public static void anadirBus(Scanner scanner) {
        System.out.println("Añadir Bus");
        System.out.print("Placa del Bus: ");
        String idBus = scanner.nextLine();
        System.out.print("Cantidad de Asientos: ");
        int asientos = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Peso Máximo: ");
        for (int i = 0; i < PesoMaxEquipaje.values().length; i++) {
            System.out.println((i + 1) + ") " + PesoMaxEquipaje.values()[i].name());
        }
        int pesoMax = scanner.nextInt();
        scanner.nextLine();
        PesoMaxEquipaje pesoMaximo = PesoMaxEquipaje.values()[pesoMax - 1];
        Bus newBus = new Bus(idBus, asientos, pesoMaximo);
        buses.add(newBus);

        System.out.println("Bus añadido con exito.");
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