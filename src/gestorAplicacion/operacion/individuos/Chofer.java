package gestorAplicacion.operacion.individuos;

import gestorAplicacion.administracion.Empresa;
import gestorAplicacion.administracion.Ruta;
import gestorAplicacion.operacion.logistica.Bus;

import java.util.ArrayList;
import java.util.Random;
import java.time.Duration;
import java.time.LocalDateTime;

public class Chofer extends Persona {
    private int sueldo;
    private int cantidadHorasConducidas = 0;
    private Empresa empresa;
    private int puntajeEficienciaTiempos;
    private int puntajeConsumoCombustible;
    private double puntajeDefinitivo;
    private Ruta ruta;
    private Bus bus;
    private Pasajero pasajero;
    private int tiempoReal;

    // El horario va a ser un ArrayList de arrays con 2 elementos {Fecha inicio,
    // Fecha fin}.
    private ArrayList<LocalDateTime[]> horario = new ArrayList<LocalDateTime[]>(); // Cambiar int por fechas

    // Constructores
    public Chofer(String nombre, int edad, int id, int sueldo) {
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
        this.sueldo = sueldo;
    }
    public Chofer(int sueldo){

    }
    public Chofer(int sueldo, ArrayList<LocalDateTime[]> horario) {
        this.sueldo = sueldo;
        if (horario != null) {
            this.setHorario(horario);
        }
    }

    // Métodos get-set

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int nuevoSueldo) {
        sueldo = nuevoSueldo;
    }
     public void aumentarSueldo(int sueldo) {
        this.sueldo += sueldo;
     }
    public int getCantidadHorasConducidas() {
        return cantidadHorasConducidas;
    }

    public void setCantidadHorasConducidas(int nuevaCantidadHorasConducidas) {
        cantidadHorasConducidas = nuevaCantidadHorasConducidas;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa nuevaEmpresa) {
        empresa = nuevaEmpresa;
    }

    public ArrayList<LocalDateTime[]> getHorario() {
        return horario;
    }

    public void setHorario(ArrayList<LocalDateTime[]> nuevoHorario) {
        // Se añadirán todas los lapsos en forma ascendente sin repeticiones,
        // mostrando error si existen cruces de horarios.
        horario = new ArrayList<LocalDateTime[]>();
        for (LocalDateTime[] lapso : nuevoHorario) {
            this.anadirRuta(lapso);
        }
    }

    public int getTiempoReal() {
        return tiempoReal;
    }

    public void setTiempoReal(int nuevoTiempoReal) {
        tiempoReal = nuevoTiempoReal;
    }

    // Método abstracto
    public String mostrarDatos() {
        return "Soy el chofer " + this.nombre + " con edad " + this.edad + " e identificación " + this.id;
    }

    // Métodos de instancia
    public Boolean isDisponible(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {// Cambiar a objeto de tiempo
        /*
         * Determina si el rango [fecha inicial, fecha final] se cruza con los horarios
         * de los lapsos en que el chofer estpa comprometido.
         * 
         * Parámetros:
         * - fechaInicial: LocalDateTime,
         * Comienzo del rango horario
         * - fechaFinal: LocalDateTime,
         * Conclusión del rango horario
         * 
         * Retorna:
         * - disponibilidad: Boolean,
         * Valor que especifica si el chofer está disponible en ese rango horario.
         */

        // Verificación de errores.
        if (Duration.between(fechaInicial, fechaFinal).toHours() > 0) {
            return false;
        }

        // Se busca si alguna de las rutas futuras
        Boolean disponibilidad = true;
        int numeroLapsos = horario.size();
        int contador = 0; // (#Lapsos)-contador representa la cantidad de intervalos entre [fecha Inicial,
                          // fecha Final]
        for (int i = 0; i < numeroLapsos; i++) {
            // Viendo si el final del intervalo [fecha Inicial, fecha Final] está a la
            // izqueirda o derecha.
            if (Duration.between(fechaFinal, horario.get(numeroLapsos - i - 1)[0]).toHours() > 1 ||
                    Duration.between(horario.get(i)[1], fechaInicial).toHours() > 1) {
                contador++;
            }
        }

        // Verificando que no se interseque con margen apropiado con alguna ruta.
        if (contador < numeroLapsos) {
            disponibilidad = false;
        }

        return disponibilidad;
    }

    public LocalDateTime[] anadirRuta(LocalDateTime[] nuevoLapso) {
        /*
         * Busca si se puede agregar el lapso en las ya establecidas para el chofer,
         * mostrando una advertencia si no puede ser añadido.
         * 
         * Parámetros:
         * - nuevoLapso: LocalDateTime[],
         * {Fecha inicio, Fecha fin} a ser agregado.
         * 
         * Retorna:
         * - lapsoNoAsignado.
         * Devuelve el nuevoLapso si este no puede ser asignado.
         */

        // Verificación de errores
        if (nuevoLapso.length != 2) {
            return nuevoLapso;
        }

        // Se busca dónde debería ir el nuevo lapso.
        int posicion = 0;
        for (LocalDateTime[] lapso : horario) {
            // Primero se mira si el intervalo del nuevo lapso se encuentra
            // a la derecha de los intervalos las rutas existentes.
            if (Duration.between(lapso[0], nuevoLapso[0]).toHours() > 0) {
                // Se encuentra en un margen aceptable.
                if (Duration.between(lapso[1], nuevoLapso[0]).toHours() > 1) {
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
                if (Duration.between(nuevoLapso[1], lapso[0]).toHours() > 1) {
                    posicion = -1;
                    break;
                }
            }
        }

        // Si no se intersecta con ningún horario, se añade correctamente.
        if (posicion != -1) {
            horario.add(posicion, nuevoLapso);
            return null;
        } else {
            return nuevoLapso;
        }
    }

    public void quitarRuta(LocalDateTime[] lapso) {
        /*
         * Remueve el lapso especificada en caso de estar presente en el horario del
         * chofer.
         * 
         * Parámetros:
         * - lapso: LocalDateTime[],
         * Lapso a ser removido.
         */

        horario.remove(lapso);
    }

    // Método estático para generar un número aleatorio entre 1 y 5
    public static int generarPuntaje() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    // Métodos para calcular la eficiencia de condutor en tiempos
    public void calcularEficienciaTiempos() {
        /*
         * Da una calificación al chofer sabiendo el tiempo que le tomó hacer una ruta.
         */

        //
        long diferenciaTiempo = Math.abs(tiempoReal - ruta.duracion());

        if (diferenciaTiempo < 10) {
            puntajeEficienciaTiempos = 5;
        } else if (diferenciaTiempo < 20) {
            puntajeEficienciaTiempos = 4;
        } else if (diferenciaTiempo < 30) {
            puntajeEficienciaTiempos = 3;
        } else if (diferenciaTiempo < 40) {
            puntajeEficienciaTiempos = 2;
        } else {
            puntajeEficienciaTiempos = 1;
        }
    }

    // Métodos para calcular el consumo de combustible del conductor
    public void calcularConsumoCombustible() {
        /*
         * Da una calificación según la cantidad de porcentaje de combustible consumido.
         */

        //
        double eficienciaCombustible = ruta.duracion() / bus.getConsumoReportado();
        if (eficienciaCombustible >= 5) {
            puntajeConsumoCombustible = 5;
        } else if (eficienciaCombustible >= 4) {
            puntajeConsumoCombustible = 4;
        } else if (eficienciaCombustible >= 3) {
            puntajeConsumoCombustible = 3;
        } else if (eficienciaCombustible >= 2) {
            puntajeConsumoCombustible = 2;
        } else {
            puntajeConsumoCombustible = 1;
        }

        /*
         * Sugeriría usar la función:
         * puntajeConsumoCombustible = Math.max((int) eficienciaCombustible, 1)
         */
    }

    // Método para calcular el puntaje definitivo
    public void calcularPuntajeDefinitivo() {
        calcularEficienciaTiempos();
        calcularConsumoCombustible();
        // Se ponderan los puntajes según su importancia (40% tiempos, 40% combustible,
        // 20% satisfacción)
        puntajeDefinitivo = (puntajeEficienciaTiempos * 0.4) + (puntajeConsumoCombustible * 0.4)
                + (pasajero.getPuntajeSatisfaccionPasajeros() * 0.2);
        // Convertir puntaje a base 100
        puntajeDefinitivo = (puntajeDefinitivo / 5) * 100;
    }

    // Método para generar el informe de rendimiento
    public String generarInformeRendimiento() {
        /*
         * Regresa el nivel de satisfacción que se tiene con el chofer.
         */

        calcularPuntajeDefinitivo();

        String dofa = "";
        if (puntajeDefinitivo < 70) {
            dofa += "¡Atención! El conductor ha obtenido un puntaje por debajo del umbral establecido (70%)." +
                    "\nSe recomienda una revisión más detallada de su desempeño.";
        }

        dofa += "\nFortalezas:";
        if (puntajeEficienciaTiempos >= 4) {
            dofa += "- Buen desempeño en la eficiencia de tiempos";
        }
        if (puntajeConsumoCombustible >= 4) {
            dofa += "- Eficiencia destacada en el consumo de combustible";
        }
        if (pasajero.getPuntajeSatisfaccionPasajeros() >= 4) {
            dofa += "- Alta satisfacción de los pasajeros";
        }

        dofa += "\nÁreas de mejora:";
        if (puntajeEficienciaTiempos < 4) {
            dofa += "- Mejorar la eficiencia de tiempos";
        }
        if (puntajeConsumoCombustible < 4) {
            dofa += "- Reducir el consumo de combustible";
        }
        if (pasajero.getPuntajeSatisfaccionPasajeros() < 4) {
            dofa += "- Aumentar la satisfacción de los pasajeros";
        }

        return dofa;
    }

    public double getPuntajeDefinitivo() {
        return puntajeDefinitivo;
    }

    public String toString() {
        return "Soy el chofer " + nombre;
    }

    public void calcularSalario() {

    }

    public void registrarTurno() {

    }

    public void registrarViaje() {

    }
}
