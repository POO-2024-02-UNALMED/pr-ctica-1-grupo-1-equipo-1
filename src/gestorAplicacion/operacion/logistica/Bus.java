package gestorAplicacion.operacion.logistica;

import gestorAplicacion.administracion.Ruta;
import gestorAplicacion.administracion.Empresa;
import gestorAplicacion.operacion.individuos.*;
import java.util.ArrayList;

public class Bus {
    private String placa;
    private int cantidadAsientos;
    private ArrayList<Asiento> asientos;
    private int capacidadMaletas;
    private int kilometrosRecorridos = 0;
    private ArrayList<Ruta> rutasFuturas = new ArrayList<Ruta>();
    private Empresa empresa;

    // Constructores
    public Bus(String placa, int cantidadAsientos, int capacidadMaletas) {
        this(placa, cantidadAsientos, capacidadMaletas, null);
    }

    public Bus(String placa, int cantidadAsientos, int capacidadMaletas, ArrayList<Ruta> rutasFuturas) {
        this.placa = placa;
        this.cantidadAsientos = cantidadAsientos;
        this.capacidadMaletas = capacidadMaletas;
        if (rutasFuturas != null) {
            this.setRutasFuturas(rutasFuturas);
        }
    }

    // Métodos get-set
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

    public int getCapacidadMaletas() {
        return capacidadMaletas;
    }

    public void setCapacidadMaletas(int nuevaCapacidadMaletas) {
        capacidadMaletas = nuevaCapacidadMaletas;
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

    public void setRutasFuturas(ArrayList<Ruta> nuevasRutasFuturas) {
        // Se añadirán todas las rutas en forma ascendente sin repeticiones,
        // mostrando error si existen cruces de horarios.
        rutasFuturas = new ArrayList<Ruta>();
        for (Ruta nuevaRuta : nuevasRutasFuturas) {
            this.anadirRuta(nuevaRuta);
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa nuevaEmpresa) {
        empresa = nuevaEmpresa;
    }

    // Métodos de instancia
    public Boolean isDisponible(int fechaInicial, int fechaFinal) {// Cambiar a objeto de tiempo
        /*
         * Determina si el rango [fecha inicial, fecha final] se cruza con los horarios
         * de las rutas que el bus debe cumplir.
         * 
         * Parámetros:
         * - fechaInicial: int,
         * Comienzo del rango horario
         * - fechaFinal: int,
         * Conclusión del rango horario
         * 
         * Retorna:
         * - disponibilidad: Boolean,
         * Valor que especifica si el bus está disponible en ese rango horario.
         */

        // Verificación de errores.
        if (fechaInicial > fechaFinal) {
            return false;
        }

        // Se busca si alguna de las rutas futuras
        Boolean disponibilidad = true;
        int numeroRutas = rutasFuturas.size();
        int contador = 0; // (#Rutas)-contador representa la cantidad de intervalos entre [fecha Inicial,
                          // fecha Final]
        for (int i = 0; i < numeroRutas; i++) {
            // Cambiar +- 1 por +- 1 hora.
            // Viendo si el final del intervalo [fecha Inicial, fecha Final] está a la
            // izqueirda o derecha.
            if ((fechaFinal < rutasFuturas.get(numeroRutas - i - 1).getFechaSalida() - 1) ||
                    (fechaInicial > rutasFuturas.get(i).getFechaLlegada() + 1)) {
                contador++;
            }
        }

        // Verificando que no se interseque con margen apropiado con alguna ruta.
        if (contador < numeroRutas) {
            disponibilidad = false;
        }

        return disponibilidad;
    }

    public void anadirRuta(Ruta nuevaRuta) {
        /*
         * Busca si se puede agregar la ruta en las ya establecidas para el bus,
         * mostrando una advertencia si la ruta no puede ser añadida.
         * 
         * Parámetros:
         * - nuevaRuta: Ruta,
         * Ruta a ser añadida.
         */

        // Se busca dónde debería ir la nueva ruta.
        int posicion = 0;
        for (Ruta ruta : rutasFuturas) {
            // Primero se mira si el intervalo de la nueva ruta se encuentra
            // a la derecha de los intervalos las rutas existentes.
            if (nuevaRuta.getFechaSalida() > ruta.getFechaSalida()) {
                // Cambiar +1 por +1 hora
                // Se encuentra en un margen aceptable.
                if (nuevaRuta.getFechaSalida() > ruta.getFechaLlegada() + 1) {
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
                // Cambiar -1 por -1 hora.
                // Se intersecan o no dejan el margen aceptable.
                if (nuevaRuta.getFechaLlegada() < ruta.getFechaSalida() - 1) {
                    posicion = -1;
                    break;
                }
            }
        }

        // Si no se intersecta con ningún horario, se añade correctamente.
        if (posicion != -1) {
            rutasFuturas.add(posicion, nuevaRuta);
            nuevaRuta.setBusAsociado(this);
        }

        // Añadir advertencia por si no se puede añadir.
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