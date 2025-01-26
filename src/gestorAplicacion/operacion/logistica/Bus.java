package gestorAplicacion.operacion.logistica;
import gestorAplicacion.administracion.Ruta;
import gestorAplicacion.administracion.Empresa;
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
    private ArrayList<Asiento> asientos;
    private int kilometrosRecorridos = 0;
    private ArrayList<Ruta> rutasFuturas = new ArrayList<Ruta>();
    private Empresa empresa;
    private ArrayList<Maleta> equipaje;// Equipaje de los pasajeros
    private double consumoReportado;

    // Constructores
    public Bus(String placa, int cantidadAsientos,  PesoMaxEquipaje pesoMaxEquipaje) {
        this(placa, cantidadAsientos, pesoMaxEquipaje, null);
    }

    public Bus(String placa, int cantidadAsientos,  PesoMaxEquipaje pesoMaxEquipaje, ArrayList<Ruta> rutasFuturas) {
        this.placa = placa;
        this.cantidadAsientos = cantidadAsientos;
        this.cantidadAsientos = cantidadAsientos;
        if (rutasFuturas != null) {
            this.setRutasFuturas(rutasFuturas);
        }
    }

    // Métodos get-set
    public  ArrayList<Maleta> getEquipaje(){
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

    public ArrayList<Ruta> setRutasFuturas(ArrayList<Ruta> nuevasRutasFuturas){
        /*
         * Se añadirán todas las rutas en forma ascendente sin repeticiones,
         * y se devuelven las rutas que no pudieron incluirse.
         */

        // Incluyendo las rutas que se puedan incorporar.
        ArrayList<Ruta> rutasNoIncluidas = new ArrayList<Ruta>();
        rutasFuturas = new ArrayList<Ruta>();
        for(Ruta nuevaRuta: nuevasRutasFuturas){
            if(!this.anadirRuta(nuevaRuta)){
                rutasNoIncluidas.add(nuevaRuta);
            }
        }

        // Muestra las rutas que no se pueden incluir.
        return rutasNoIncluidas;
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
        if (Duration.between(fechaInicial, fechaFinal).toHours() > 0) {
            return false;
        }

        // Se busca si alguna de las rutas futuras
        Boolean disponibilidad = true;
        int numeroRutas = rutasFuturas.size();
        int contador = 0; // (#Rutas)-contador representa la cantidad de intervalos entre [fecha Inicial,
                          // fecha Final]
        for (int i = 0; i < numeroRutas; i++) {
            // Viendo si el final del intervalo [fecha Inicial, fecha Final] está a la
            // izqueirda o derecha.
            if((Duration.between(fechaFinal, rutasFuturas.get(numeroRutas - i - 1).getFechaSalida()).toHours() > 1) ||
               (Duration.between(rutasFuturas.get(i).getFechaLlegada(), fechaInicial).toHours() > 1)){
                contador++;
            }
        }

        // Verificando que no se interseque con margen apropiado con alguna ruta.
        if (contador < numeroRutas) {
            disponibilidad = false;
        }

        return disponibilidad;
    }

    public Boolean anadirRuta(Ruta nuevaRuta){
        /*
         * Busca si se puede agregar la ruta en las ya establecidas para el bus,
         * mostrando una advertencia si la ruta no puede ser añadida.
         * 
         * Parámetros:
         *      - nuevaRuta: Ruta,
         *          Ruta a ser añadida.
         * 
         * Retorna:
         *      - asignado: Boolean,
         *          Indica si se pudo añadir la ruta.
         */

        // Se busca dónde debería ir la nueva ruta.
        int posicion = 0;
        for (Ruta ruta : rutasFuturas) {
            // Primero se mira si el intervalo de la nueva ruta se encuentra
            // a la derecha de los intervalos las rutas existentes.
            if (Duration.between(ruta.getFechaSalida(), nuevaRuta.getFechaSalida()).toHours() > 0) {
                // Se encuentra en un margen aceptable.
                if (Duration.between(ruta.getFechaLlegada(), nuevaRuta.getFechaSalida()).toHours() > 1) {
                    posicion++;
                }
                // Ambos intervalos se intersectan o no dejan el margen aceptable.
                else {
                    posicion = -1;
                    break;
                }
            }

            // Ahora se mira si se encuentra a la izquierda
            else {
                // Se intersecan o no dejan el margen aceptable.
                if (Duration.between(nuevaRuta.getFechaLlegada(), ruta.getFechaSalida()).toHours() > 1) {
                    posicion = -1;
                    break;
                }
            }
        }

        // Si no se intersecta con ningún horario, se añade correctamente.
        if (posicion != -1) {
            rutasFuturas.add(posicion, nuevaRuta);
            nuevaRuta.setBusAsociado(this);
            return true;
        }
        else{
            return false;
        }
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
        ArrayList<Asiento> asientosActuales = this.getAsientos();
        if (cantidadAsientos - asientosActuales.size() > 0) {
            Asiento asiento = new Asiento(pasajero);
            asientos.add(asiento);
            String output = "Asiento asignado a " + pasajero.getNombre() + " en el bus " + this.placa;
            return output;
        } else {
            return "No hay asientos disponibles en el bus " + this.placa;
        }
    }

    public String eliminarPasajero(Pasajero pasajero) {
        ArrayList<Asiento> asientosActuales = getAsientos();
        if (cantidadAsientos - asientosActuales.size() > 0) {
            for (Asiento asiento : asientosActuales) {
                if (asiento.getUsuario().getNombre() == pasajero.getNombre()) {
                    asientosActuales.remove(asiento);
                    return "Asiento quitado a " + pasajero.getNombre() + " en el bus " + this.placa;
                }
            }

        }
        return "No se ha encontrad al pasajero " + pasajero.getNombre() + " en el bus " + this.placa;
    }

    public LocalDateTime hallarHueco(int lapso){
        /*
         * Mira en el horario del bus a ver si encuentra un hueco de la duración especificada.
         * 
         * Parámetros:
         *      - lapso: int,
         *          Tiempo que se necesita al bus.
         * 
         * Retorna:
         *      - fecha: LocalDateTime,
         *          Hora en la cual puede iniciar la actividad a reclutarlo.
         */

        // Viendo si está disponible en una hora con ese lapso.
        if(Duration.between(LocalDateTime.now(), rutasFuturas.getFirst().getFechaSalida()).toHours() > lapso + 2){
                return rutasFuturas.getFirst().getFechaSalida().minusHours(lapso + 2);
            }

        // Viendo si existe una franja entre los horarios de las rutas que tiene el bus.
        for(int i = 0; i < rutasFuturas.size() - 1; i++){
            if(Duration.between(rutasFuturas.get(i).getFechaLlegada(),
               rutasFuturas.get(i).getFechaSalida()).toHours() > lapso + 2){
                return rutasFuturas.get(i).getFechaLlegada().plusHours(1);
            }
        }
        
        return rutasFuturas.getLast().getFechaLlegada().plusHours(1);
    }

    public String toString(){
        return "Soy el bus con placa " + placa;
    }

    public void reparar() {

    }

    public void verificarIntegridad() {

    }

    public void calcularConsumoCombustible() {

    }

    public float danoAleatorio() {
        return 0;
    }
}