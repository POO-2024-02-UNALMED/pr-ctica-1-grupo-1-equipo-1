package gestorAplicacion.operacion.individuos;
import gestorAplicacion.administracion.Empresa;
import java.util.ArrayList;
import java.io.Serializable;

public class Chofer extends Persona implements Serializable{
    private int sueldo;
    private int cantidadHorasConducidas = 0;
    private Empresa empresa;
    // El horario va a ser un ArrayList de arrays con 2 elementos {Fecha inicio, Fecha fin}.
    private ArrayList<int[]> horario = new ArrayList<int[]>(); // Cambiar int por fechas

    // Constructores
    public Chofer(int sueldo){
        this(sueldo, null);
    }

    public Chofer(int sueldo, ArrayList<int[]> horario){
        this.sueldo = sueldo;
        if(horario != null){this.setHorario(horario);}
    }
    
    // Métodos get-set

    public int getSueldo(){
        return sueldo;
    }

    public void setSueldo(int nuevoSueldo){
        sueldo = nuevoSueldo;
    }

    public int getCantidadHorasConducidas(){
        return cantidadHorasConducidas;
    }

    public void setCantidadHorasConducidas(int nuevaCantidadHorasConducidas){
        cantidadHorasConducidas = nuevaCantidadHorasConducidas;
    }

    public Empresa getEmpresa(){
        return empresa;
    }

    public void setEmpresa(Empresa nuevaEmpresa){
        empresa = nuevaEmpresa;
    }

    public ArrayList<int[]> getHorario(){
        return horario;
    }

    public void setHorario(ArrayList<int[]> nuevoHorario){
        // Se añadirán todas los lapsos en forma ascendente sin repeticiones,
        // mostrando error si existen cruces de horarios.
        horario = new ArrayList<int[]>();
        for(int[] lapso: nuevoHorario){
            this.anadirRuta(lapso);
        }
    }

    // Método abstracto
    public String mostrarDatos(){
        return this.nombre + this.edad + this.id;
    }

    // Métodos de instancia
    public Boolean isDisponible(int fechaInicial, int fechaFinal){// Cambiar a objeto de tiempo
        /*
         * Determina si el rango [fecha inicial, fecha final] se cruza con los horarios
         * de los lapsos en que el chofer estpa comprometido.
         * 
         * Parámetros:
         *      - fechaInicial: int,
         *          Comienzo del rango horario
         *      - fechaFinal: int,
         *          Conclusión del rango horario
         * 
         * Retorna:
         *      - disponibilidad: Boolean,
         *          Valor que especifica si el chofer está disponible en ese rango horario.
         */

        // Verificación de errores.
        if(fechaInicial > fechaFinal){
            return false;
        }

        // Se busca si alguna de las rutas futuras 
        Boolean disponibilidad = true;
        int numeroLapsos = horario.size();
        int contador = 0; // (#Lapsos)-contador representa la cantidad de intervalos entre [fecha Inicial, fecha Final]
        for(int i = 0; i < numeroLapsos; i++){
            // Cambiar +- 1 por +- 1 hora.
            // Viendo si el final del intervalo [fecha Inicial, fecha Final] está a la izqueirda o derecha.
            if((fechaFinal < horario.get(numeroLapsos - i - 1)[0] - 1) ||
               (fechaInicial > horario.get(i)[1] + 1)){
                contador++;
            }
        }

        // Verificando que no se interseque con margen apropiado con alguna ruta.
        if(contador < numeroLapsos){disponibilidad = false;}

        return disponibilidad;
    }

    public void anadirRuta(int[] nuevoLapso){
        /*
         * Busca si se puede agregar el lapso en las ya establecidas para el chofer,
         * mostrando una advertencia si no puede ser añadido.
         * 
         * Parámetros:
         *      - nuevoLapso: int[],
         *          {Fecha inicio, Fecha fin} a ser agregado.
         */

        // Verificación de errores
        if(nuevoLapso.length != 2){
            // Hacer un error.
        }

        // Se busca dónde debería ir el nuevo lapso.
        int posicion = 0;
        for(int[] lapso: horario){
            // Primero se mira si el intervalo del nuevo lapso se encuentra
            // a la derecha de los intervalos las rutas existentes.
            if(nuevoLapso[0] > lapso[0]){
                // Cambiar +1 por +1 hora
                // Se encuentra en un margen aceptable.
                if(nuevoLapso[0] > lapso[1] + 1){posicion++;}
                // Ambos intervalos se intersectan o no dejan el margen aceptable.
                else{posicion = -1; break;}
            }

            // Ahora se mira si se encuentra a la izquierda
            else{
                // Cambiar -1 por -1 hora.
                // Se intersecan o no dejan el margen aceptable.
                if(nuevoLapso[1] < lapso[0] - 1){posicion = -1; break;}
            }
        }

        // Si no se intersecta con ningún horario, se añade correctamente.
        if(posicion != -1){horario.add(posicion, nuevoLapso);}

        // Añadir advertencia por si no se puede añadir.
    }

    public void quitarRuta(int[] lapso){
        /*
         * Remueve el lapso especificada en caso de estar presente en el horario del chofer.
         * 
         * Parámetros:
         *      - lapso: int[],
         *          Lapso a ser añadido.
         */

        horario.remove(lapso);
    }

    public void calcularSalario(){

    }

    public void registrarTurno(){

    }

    public void registrarViaje(){
        
    }
    
//Interacción 1 de 5: Evaluación de Rendimiento de los Conductores esta 
//falta por relacionar a la demas clases solo ta el codigo esta 
//semana hago la interaccion de las clases correspondiente 

// Declaración de variables de instancia para el tiempo estimado y real del viaje, distancia recorrida, consumo de combustible reportado y puntajes.
private int tiempoEstimadoViaje; 
// Tiempo estimado para completar el viaje.
private int tiempoRealViaje; 
// Tiempo real que tomó completar el viaje.
private double distanciaRecorrida; 
// Distancia total recorrida durante el viaje.
private double consumoReportado; 
// Cantidad de combustible reportado como consumido durante el viaje.
private int puntajeSatisfaccionPasajeros; 
// Puntaje de satisfacción de los pasajeros.
private int puntajeEficienciaTiempos; 
// Puntaje de eficiencia en tiempos.
private int puntajeConsumoCombustible; 
// Puntaje de eficiencia en el consumo de combustible.
private double puntajeDefinitivo; 
// Puntaje final basado en varios factores.

// Constructor de la clase Chofer
public Chofer(int tiempoEstimadoViaje, int tiempoRealViaje, double distanciaRecorrida, double consumoReportado) {
    this.tiempoEstimadoViaje = tiempoEstimadoViaje; 
    // Asigna el tiempo estimado de viaje.
    this.tiempoRealViaje = tiempoRealViaje; 
    // Asigna el tiempo real de viaje.
    this.distanciaRecorrida = distanciaRecorrida; 
    // Asigna la distancia recorrida.
    this.consumoReportado = consumoReportado; 
    // Asigna el consumo de combustible reportado.
    this.puntajeSatisfaccionPasajeros = generarNumeroAleatorio(); 
    // Genera un puntaje aleatorio para la satisfacción de los pasajeros.
}

// Método estático para generar un número aleatorio entre 1 y 5
public static int generarNumeroAleatorio() {
    Random random = new Random(); 
    // Crea una instancia de la clase Random para generar números aleatorios.
    return random.nextInt(5) + 1; 
    
    // Devuelve un número aleatorio entre 1 y 5.
}

// Método para calcular la eficiencia en tiempos
public void calcularEficienciaTiempos() {
    int diferenciaTiempo = tiempoRealViaje - tiempoEstimadoViaje; 
    // Calcula la diferencia entre el tiempo real y el estimado.
    if (diferenciaTiempo < 10) { 
        // Si la diferencia es menor a 10 minutos...
        puntajeEficienciaTiempos = 5; 
        // Asigna puntaje de eficiencia en tiempos de 5.
    } else if (diferenciaTiempo < 20) { 
        // Si la diferencia es menor a 20 minutos...
        puntajeEficienciaTiempos = 4; 
        // Asigna puntaje de eficiencia en tiempos de 4.
    } else if (diferenciaTiempo < 30) { 
        // Si la diferencia es menor a 30 minutos...
        puntajeEficienciaTiempos = 3; 
        // Asigna puntaje de eficiencia en tiempos de 3.
    } else if (diferenciaTiempo < 40) { 
        // Si la diferencia es menor a 40 minutos...
        puntajeEficienciaTiempos = 2; 
        // Asigna puntaje de eficiencia en tiempos de 2.
    } else { // Si la diferencia es mayor o igual a 40 minutos...
        puntajeEficienciaTiempos = 1; 
        // Asigna puntaje de eficiencia en tiempos de 1.
    }
}

// Método para calcular el consumo de combustible
public void calcularConsumoCombustible() {
    double eficienciaCombustible = distanciaRecorrida / consumoReportado; 
    // Calcula la eficiencia de combustible.
    if (eficienciaCombustible >= 5) { 
        // Si la eficiencia es mayor o igual a 5...
        puntajeConsumoCombustible = 5; 
        // Asigna puntaje de consumo de combustible de 5.
    } else if (eficienciaCombustible >= 4) { 
        // Si la eficiencia es mayor o igual a 4...
        puntajeConsumoCombustible = 4; 
        // Asigna puntaje de consumo de combustible de 4.
    } else if (eficienciaCombustible >= 3) {
         // Si la eficiencia es mayor o igual a 3...
        puntajeConsumoCombustible = 3; 
        // Asigna puntaje de consumo de combustible de 3.
    } else if (eficienciaCombustible >= 2) { 
        // Si la eficiencia es mayor o igual a 2...
        puntajeConsumoCombustible = 2; 
        // Asigna puntaje de consumo de combustible de 2.
    } else { // Si la eficiencia es menor a 2...
        puntajeConsumoCombustible = 1; 
        // Asigna puntaje de consumo de combustible de 1.
    }
}

// Método para calcular el puntaje definitivo
public void calcularPuntajeDefinitivo() {
    calcularEficienciaTiempos(); 
    // Calcula la eficiencia en tiempos.
    calcularConsumoCombustible();
     // Calcula el consumo de combustible.
    // Calcula el puntaje definitivo ponderando los puntajes según su importancia (40% tiempos, 40% combustible, 20% satisfacción).
    puntajeDefinitivo = (puntajeEficienciaTiempos * 0.4) + (puntajeConsumoCombustible * 0.4) + (puntajeSatisfaccionPasajeros * 0.2); 
    puntajeDefinitivo = (puntajeDefinitivo / 5) * 100; 
    // Convierte el puntaje a base 100.
}

// Método para generar el informe de rendimiento
public void generarInformeRendimiento() {
    // Imprime el informe de rendimiento del conductor.
    System.out.println("Informe de Rendimiento del Conductor");
    System.out.println("====================================");
    System.out.println("Eficiencia en tiempos: " + puntajeEficienciaTiempos + "/5");
    System.out.println("Consumo de combustible: " + puntajeConsumoCombustible + "/5");
    System.out.println("Satisfacción de pasajeros: " + puntajeSatisfaccionPasajeros + "/5");
    System.out.println("Puntaje definitivo: " + puntajeDefinitivo + "%"); 
    // Representado en porcentaje

    if (puntajeDefinitivo < 70) { 
        // Si el puntaje definitivo es menor a 70...
        System.out.println("\n¡Atención! El conductor ha obtenido un puntaje por debajo del umbral establecido (70%).");
        System.out.println("Se recomienda una revisión más detallada de su desempeño.");
    }

    // Imprime las fortalezas del conductor.
    System.out.println("\nFortalezas:");
    if (puntajeEficienciaTiempos >= 4) {
        System.out.println("- Buen desempeño en la eficiencia de tiempos");
    }
    if (puntajeConsumoCombustible >= 4) {
        System.out.println("- Eficiencia destacada en el consumo de combustible");
    }
    if (puntajeSatisfaccionPasajeros >= 4) {
        System.out.println("- Alta satisfacción de los pasajeros");
    }

    // Imprime las áreas de mejora del conductor.
    System.out.println("\nÁreas de mejora:");
    if (puntajeEficienciaTiempos < 4) {
        System.out.println("- Mejorar la eficiencia de tiempos");
    }
    if (puntajeConsumoCombustible < 4) {
        System.out.println("- Reducir el consumo de combustible");
    }
    if (puntajeSatisfaccionPasajeros < 4) {
        System.out.println("- Aumentar la satisfacción de los pasajeros");
    }
}

// Método para obtener el puntaje definitivo.
public double getPuntajeDefinitivo() {
    return puntajeDefinitivo; // Devuelve el puntaje definitivo.
}
}
