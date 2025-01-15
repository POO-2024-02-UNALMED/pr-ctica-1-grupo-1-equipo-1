package gestorAplicacion.operacion.individuos;
import gestorAplicacion.administracion.Empresa;
import java.util.ArrayList;

public class Chofer extends Persona{
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
}
