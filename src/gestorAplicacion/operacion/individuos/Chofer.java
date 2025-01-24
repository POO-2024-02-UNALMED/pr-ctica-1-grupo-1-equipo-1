package gestorAplicacion.operacion.individuos;
import gestorAplicacion.administracion.Empresa;
import java.util.ArrayList;
import java.time.Duration;
import java.time.LocalDateTime;

public class Chofer extends Persona{
    private int sueldo;
    private int cantidadHorasConducidas = 0;
    private Empresa empresa;
    // El horario va a ser un ArrayList de arrays con 2 elementos {Fecha inicio, Fecha fin}.
    private ArrayList<LocalDateTime[]> horario = new ArrayList<LocalDateTime[]>(); // Cambiar int por fechas

    // Constructores
    public Chofer(int sueldo){
        this(sueldo, null);
    }

    public Chofer(int sueldo, ArrayList<LocalDateTime[]> horario){
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

    public ArrayList<LocalDateTime[]> getHorario(){
        return horario;
    }

    public void setHorario(ArrayList<LocalDateTime[]> nuevoHorario){
        // Se añadirán todas los lapsos en forma ascendente sin repeticiones,
        // mostrando error si existen cruces de horarios.
        horario = new ArrayList<LocalDateTime[]>();
        for(LocalDateTime[] lapso: nuevoHorario){
            this.anadirRuta(lapso);
        }
    }

    // Método abstracto
    public String mostrarDatos(){
        return this.nombre + this.edad + this.id;
    }

    // Métodos de instancia
    public Boolean isDisponible(LocalDateTime fechaInicial, LocalDateTime fechaFinal){// Cambiar a objeto de tiempo
        /*
         * Determina si el rango [fecha inicial, fecha final] se cruza con los horarios
         * de los lapsos en que el chofer estpa comprometido.
         * 
         * Parámetros:
         *      - fechaInicial: LocalDateTime,
         *          Comienzo del rango horario
         *      - fechaFinal: LocalDateTime,
         *          Conclusión del rango horario
         * 
         * Retorna:
         *      - disponibilidad: Boolean,
         *          Valor que especifica si el chofer está disponible en ese rango horario.
         */

        // Verificación de errores.
        if(Duration.between(fechaInicial, fechaFinal).toHours() > 0){
            return false;
        }

        // Se busca si alguna de las rutas futuras 
        Boolean disponibilidad = true;
        int numeroLapsos = horario.size();
        int contador = 0; // (#Lapsos)-contador representa la cantidad de intervalos entre [fecha Inicial, fecha Final]
        for(int i = 0; i < numeroLapsos; i++){
            // Viendo si el final del intervalo [fecha Inicial, fecha Final] está a la izqueirda o derecha.
            if(Duration.between(fechaFinal, horario.get(numeroLapsos - i - 1)[0]).toHours() > 1 ||
               Duration.between(horario.get(i)[1], fechaInicial).toHours() > 1){
                contador++;
            }
        }

        // Verificando que no se interseque con margen apropiado con alguna ruta.
        if(contador < numeroLapsos){disponibilidad = false;}

        return disponibilidad;
    }

    public LocalDateTime[] anadirRuta(LocalDateTime[] nuevoLapso){
        /*
         * Busca si se puede agregar el lapso en las ya establecidas para el chofer,
         * mostrando una advertencia si no puede ser añadido.
         * 
         * Parámetros:
         *      - nuevoLapso: LocalDateTime[],
         *          {Fecha inicio, Fecha fin} a ser agregado.
         * 
         * Retorna:
         *      - lapsoNoAsignado.
         *      Devuelve el nuevoLapso si este no puede ser asignado.
         */

        // Verificación de errores
        if(nuevoLapso.length != 2){
            return nuevoLapso;
        }

        // Se busca dónde debería ir el nuevo lapso.
        int posicion = 0;
        for(LocalDateTime[] lapso: horario){
            // Primero se mira si el intervalo del nuevo lapso se encuentra
            // a la derecha de los intervalos las rutas existentes.
            if(Duration.between(lapso[0], nuevoLapso[0]).toHours() > 0){
                // Se encuentra en un margen aceptable.
                if(Duration.between(lapso[1], nuevoLapso[0]).toHours() > 1){posicion++;}
                // Ambos intervalos se intersectan o no dejan el margen aceptable.
                else{posicion = -1; break;}
            }

            // Ahora se mira si se encuentra a la izquierda
            else{
                // Se intersecan o no dejan el margen aceptable.
                if(Duration.between(nuevoLapso[1], lapso[0]).toHours() > 1){
                    posicion = -1;
                    break;
                }
            }
        }

        // Si no se intersecta con ningún horario, se añade correctamente.
        if(posicion != -1){horario.add(posicion, nuevoLapso); return null;}
        else{return nuevoLapso;}
    }

    public void quitarRuta(LocalDateTime[] lapso){
        /*
         * Remueve el lapso especificada en caso de estar presente en el horario del chofer.
         * 
         * Parámetros:
         *      - lapso: LocalDateTime[],
         *          Lapso a ser removido.
         */

        horario.remove(lapso);
    }

    public String toString(){
        return "Soy el chofer " + nombre;
    }

    public void calcularSalario(){

    }

    public void registrarTurno(){

    }

    public void registrarViaje(){
        
    }
}
