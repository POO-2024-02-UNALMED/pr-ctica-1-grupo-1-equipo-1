package gestorAplicacion.administracion;

import gestorAplicacion.operacion.logistica.Bus;
import gestorAplicacion.operacion.individuos.Chofer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.io.Serializable;
public class Ruta extends Red implements Serializable{
    private static int totalRutas;
    private static ArrayList<Ruta> rutas = new ArrayList<Ruta>();
    private int idRuta;
    private Bus busAsociado;
    private Chofer choferAsociado;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private Parada lugarInicio;
    private Parada lugarFinal;
    private Parada[] paradas;
    private float distanciaRuta;
    private int minutos;

    static Bus bus1 = new Bus("XYZ123", 40, Bus.PesoMaxEquipaje.MEDIO);
    static Bus bus2 = new Bus("ABC789", 50, Bus.PesoMaxEquipaje.PESADO);
    static Bus bus3 = new Bus("DEF456", 60, Bus.PesoMaxEquipaje.LIGERO);
    static Bus bus4 = new Bus("GHI987", 30, Bus.PesoMaxEquipaje.MEDIO);
    static Bus bus5 = new Bus("JKL098", 40, Bus.PesoMaxEquipaje.PESADO);
    static Bus bus6 = new Bus("MNO654", 50, Bus.PesoMaxEquipaje.LIGERO);
    static Bus bus7 = new Bus("PQR321", 40, Bus.PesoMaxEquipaje.PESADO);
    static Bus bus8 = new Bus("STU210", 30, Bus.PesoMaxEquipaje.MEDIO);
    static Bus bus9 = new Bus("VWX654", 40, Bus.PesoMaxEquipaje.MEDIO);
    public static Bus bus10 = new Bus("YZA123", 40, Bus.PesoMaxEquipaje.PESADO);
    static Chofer chofer1 = new Chofer("Juan Perez", 30, 12345, 1000000, bus1);
    static Chofer chofer2 = new Chofer("Maria Rodriguez", 35, 67890, 1200000, bus2);
    static Chofer chofer3 = new Chofer("Pedro Garcia", 40, 98765, 1500000, bus3);
    static Chofer chofer4 = new Chofer("Luis Martinez", 45, 34567, 1800000, bus4);
    static Chofer chofer5 = new Chofer("Ana Gonzalez", 50, 23456, 2000000, bus5);
    static Chofer chofer6 = new Chofer("Jose Maria", 55, 123456, 2200000, bus6);
    static Chofer chofer7 = new Chofer("Marco Fernandez", 60, 45678, 2500000, bus7);
    static Chofer chofer8 = new Chofer("Sofia Lopez", 65, 78901, 2800000, bus8);
    static Chofer chofer9 = new Chofer("Jose Antonio", 70, 89012, 3000000, bus9);
    static Chofer chofer10 = new Chofer("Natalia Fernandez", 75, 56789, 3200000, bus10);
    static ArrayList<Chofer> choferes = new ArrayList<>(Arrays.asList(chofer1, chofer2, chofer3, chofer4, chofer5,
            chofer6, chofer7, chofer8, chofer9, chofer10));
    static ArrayList<Bus> buses = new ArrayList<>(
            Arrays.asList(bus1, bus2, bus3, bus4, bus5, bus6, bus7, bus8, bus9, bus10));

    // Constantes de dificultad
    public static final float DIFICULTAD_BAJA = 20; // km
    public static final float DIFICULTAD_MEDIA = 40; // km
    public static final float DIFICULTAD_ALTA = 60; // km

    // Constructores
    public Ruta(Bus busAsociado, LocalDateTime fechaSalida, LocalDateTime fechaLlegada, // Cambiar por objetos de tiempo
            Parada lugarInicio, Parada lugarFinal) {
        totalRutas++;
        this.idRuta = totalRutas;
        this.busAsociado = busAsociado;
        this.fechaSalida = fechaSalida; // Hay que verificar que la fecha de salida sea menor a la de llegada
        this.fechaLlegada = fechaLlegada; // ""
        this.lugarInicio = lugarInicio;
        this.lugarFinal = lugarFinal;
    }

    public Ruta(Parada lugarInicio, Parada lugarFinal, Parada[] paradas) {
        // Cambiar por objetos de tiempo
        this(null, LocalDateTime.now(), LocalDateTime.now(), lugarInicio, lugarFinal);
    }

    public Ruta(Parada lugarInicio, Parada lugarFinal, int kilometros, int minutos, Bus bus, Chofer chofer) {
        this.lugarInicio = lugarInicio;
        this.lugarFinal = lugarFinal;
        this.distanciaRuta = (float) kilometros;
        this.minutos = minutos;
        this.busAsociado = bus;
        this.choferAsociado = chofer;
    }

    public Ruta(int idRuta,Parada lugarInicio, Parada lugarFinal, int kilometros, int minutos) {
        this.idRuta = idRuta;
        this.lugarInicio = lugarInicio;
        this.lugarFinal = lugarFinal;
        this.distanciaRuta = (float) kilometros;
        this.minutos = minutos;
        
    }

    public Ruta(int ordinalLugarInicio, int ordinalLugarFinal, int[] ordinalesParadas) {
        // Cambiar por objetos de tiempo
        this(null, LocalDateTime.now(), LocalDateTime.now(),
                Parada(ordinalLugarInicio),
                Parada(ordinalLugarFinal));
    }

    // Métodos get-set
    public static int getTotalRutas() {
        return totalRutas;
    }

    public int getTiempo() {
        return this.minutos;
    }

    static public void addRutas(Ruta ruta) {
        rutas.add(ruta);
    }

    public static void setTotalRutas(int nuevoTotalRutas) {
        totalRutas = nuevoTotalRutas;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public float getDistancia() {
        return distanciaRuta;
    }

    public void setIdRuta(int nuevoIdRuta) {
        idRuta = nuevoIdRuta;
    }

    public Bus getBusAsociado() {
        return busAsociado;
    }

    public void setBusAsociado(Bus nuevoBusAsociado) {
        busAsociado = nuevoBusAsociado;
    }

    public Chofer getChoferAsociado() {
        return choferAsociado;
    }

    public void setChoferAsociado(Chofer nuevoChoferAsociado) {
        choferAsociado = nuevoChoferAsociado;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime nuevafechaSalida) {
        fechaSalida = nuevafechaSalida;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDateTime nuevafechaLlegada) {
        fechaLlegada = nuevafechaLlegada;
    }

    public Parada getLugarInicio() {
        return lugarInicio;
    }

    public void setLugarInicio(Parada nuevoLugarInicio) {

        lugarInicio = nuevoLugarInicio;
    }

    public Parada getLugarFinal() {
        return lugarFinal;
    }

    public void setLugarFinal(Parada nuevoLugarFinal) {
        lugarFinal = nuevoLugarFinal;
    }

    public Parada[] getParadas() {
        return paradas;
    }

    public void setParadas(Parada[] nuevasParadas) {
        paradas = new Parada[2];
        paradas[0] = lugarInicio;
        paradas[1] = lugarFinal;

        // Poniendo todas las paradas en su lugar sin contar null.
        for (Parada parada : nuevasParadas) {
            this.agregarParada(parada);
        }
    }

    // Métodos de instancia
    public void calcularDistancia() {

    }

    public void finalizarRutas() {

    }

    public static ArrayList<Ruta> filtrarRutas(String origen, String destino) {
        ArrayList<Ruta> rutasFiltradas = new ArrayList<>();

        for (int i = 0; i < Red.carreteras.size(); i++) {
            int[][] casoCarretera = Red.carreteras.get(i); // Accede al primer elemento del primer array
            int origenCarretera = casoCarretera[0][0];
            int destinoCarretera = casoCarretera[0][1];
            int kilometros = casoCarretera[1][0];
            int minutos = casoCarretera[1][1];
            Chofer chofer = Ruta.choferes.get(i);
            Bus bus = Ruta.buses.get(i);
            if (Red.Parada(origenCarretera - 1) == Parada.valueOf(origen)
                    && Red.Parada(destinoCarretera - 1) == Parada.valueOf(destino)) {

                Ruta nuevaRuta = new Ruta(Parada.valueOf(origen), Parada.valueOf(destino), kilometros, minutos, bus,
                        chofer); // Asumiendo
                // este
                // constructor
                rutasFiltradas.add(nuevaRuta);
            }
        }

        return rutasFiltradas;
    }

    public int duracion() {
        /*
         * Calcula la duración de la ruta.
         * 
         * Retorna:
         * - duracion: int,
         * duracion de la ruta.
         */

        return Ruta.duracion(this.paradas);
    }

    public void agregarParada(Parada nuevaParada) {
        /*
         * Añade la nueva parada haciendo minimizando su efecto en la ruta.
         * 
         * Parámetros:
         * - nuevaParada: Parada.
         * Parada a añadir
         */

        // Tomando el número de paradas
        int nParadas = paradas.length;

        // Caso donde solo exista una parada.
        if (nParadas < 2) {
            // Debe generar error.
        }

        int[] posicion = Red.posicion(paradas, nuevaParada);
        if (posicion == null) {
            return;
        }

        // En caso de poderse agregar, se ve el puesto donde debe agregarse.
        int posicionDistanciaMinima = posicion[0] + 1;

        // Reorganizando el array de paradas para que la nueva parada quede en la
        // posición indicada.
        Parada[] temp = new Parada[nParadas + 1];
        temp[posicionDistanciaMinima] = nuevaParada;
        for (int i = 0; i < nParadas; i++) {
            if (i < posicionDistanciaMinima) {
                temp[i] = paradas[i];
            } else {
                temp[i + 1] = paradas[i];
            }
        }

        // Cambiando los lugares de origen y destino.
        lugarInicio = temp[0];
        lugarFinal = temp[temp.length - 1];

        // Garantizando el cambio.
        paradas = temp;
    }

    public void eliminarParada(Parada parada) {
        /*
         * Elimina una parada existente.
         * 
         * Parámetros:
         * - parada: Parada,
         * Parada a eliminar.
         */

        // Caso donde no existan paradas.
        if (paradas == null) {
            paradas = new Parada[] { lugarInicio, lugarFinal };
            return;
        }

        // Tomando el número de paradas.
        int nParadas = paradas.length;

        // Creando un array donde se quitó la parada.
        Parada[] temp = new Parada[nParadas - 1];
        Boolean estaEnElArray = false;
        for (int i = 0; i < nParadas - 1; i++) {
            if (paradas[i] == parada) {
                estaEnElArray = true;
            } // Viendo si está la parada.
            else {
                temp[i] = paradas[i - (estaEnElArray ? 1 : 0)];
            } // Desplazando lo adecuado.
        }

        // Preguntando si la parada se encontraba en el array.
        if (!estaEnElArray && paradas[nParadas - 1] != parada) {
            return;
        } else if (!estaEnElArray && paradas[nParadas - 1] == parada) {
            temp[nParadas - 2] = paradas[nParadas - 1];
        }

        // Viendo que siempre existan al menos 2 paradas en la ruta.
        if (temp.length >= 2) {
            // Guardando el cambio.
            lugarInicio = temp[0];
            lugarFinal = temp[temp.length - 1];
            paradas = temp;
        }
    }

    public int contarPasajerosEnParada(Parada parada) {
        int contador = 0;
        for (Factura factura : Contabilidad.getVentas()) {
            if (factura.getRutaElegida().equals(this) && factura.getDestino().equals(parada)) {
                contador += factura.getNumAsientosAsignados();
            }
        }
        return contador;
    }

    // Este metodo obtiene todas las rutas alternativas que el usuario puede
    // obtener,
    // Tener en cuenta que se debe ejecutar por cada empresa que se quiera ver las
    // rutas alternativas
    public ArrayList<Ruta> obtenerRutasAlternativas() {
        ArrayList<Ruta> rutasAlternativas = new ArrayList<Ruta>();
        Empresa empresa = this.getBusAsociado().getEmpresa();
        // Iterar sobre las rutas de la empresa
        for (Ruta ruta : empresa.getRutas()) {

            if (ruta.getLugarFinal().equals(this.lugarFinal) && ruta.getLugarInicio() != this.lugarInicio) {
                // Verificar si la ruta esta activa y si el bus esta disponible tambien
                if (ruta.getBusAsociado() != null
                        && ruta.getBusAsociado().isDisponible(this.fechaSalida, this.fechaLlegada)) {

                    // Convertir los arreglos a ArrayList para poder trabajar con
                    ArrayList<Parada> paradasRutaActual = new ArrayList<>(Arrays.asList(this.getParadas()));
                    ArrayList<Parada> paradasRutasAlternativas = new ArrayList<>(Arrays.asList(ruta.getParadas()));

                    // Verificar si alguna parada de la ruta actual está en las paradas alternativas
                    for (Parada paradaActual : paradasRutaActual) {
                        if (paradasRutasAlternativas.contains(paradaActual)) {
                            rutasAlternativas.add(ruta);
                            break; // Salir del bucle si se encuentra una coincidencia
                        }
                    }
                }
            }
        }
        return rutasAlternativas;
    }

    public String evaluarComplejidad(float distanciaRuta) {

        if (distanciaRuta <= DIFICULTAD_BAJA) {
            return "La ruta" + lugarInicio + "-" + lugarFinal +
                    "cuenta con una dificultad BAJA";

        } else if ((DIFICULTAD_BAJA < distanciaRuta) &&
                (distanciaRuta <= DIFICULTAD_MEDIA) && DIFICULTAD_ALTA > distanciaRuta) {
            return "La ruta" + lugarInicio + "-" + lugarFinal +
                    "cuenta con una dificultad Media";
        }
        return "La ruta" + lugarInicio + "-" + lugarFinal +
                "cuenta con una dificultad ALTA";

    }

    public void evaluarBonificacion(Ruta ruta) {
        if (ruta.distanciaRuta >= 40) {
            float bonificacion = ruta.distanciaRuta * 4;
            ruta.choferAsociado.aumentarSueldo((int) bonificacion);
        }
    }

    public static void eliminarRuta(Scanner scanner) {
        System.out.println("Eliminar Ruta");
        System.out.print("ID de la ruta a eliminar: ");
        int idRuta = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea

        // Eliminar la ruta de la empresa
        for (Ruta ruta : rutas) {
            if (ruta.getIdRuta() == idRuta) { // Asegúrate de que la clase Empresa tenga el método eliminarRuta()
                System.out.println("Ruta eliminada con éxito.");
                rutas.remove(ruta);
                break;
            } else {
                System.out.println("No se encontró ninguna ruta con ese ID.");
            }
        }
    }

    public static void anadirRuta(Scanner scanner) {
        System.out.println("Añadir Ruta");
        System.out.print("ID de la ruta: ");
        int idRuta = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Origen: ");
        Parada origen = Parada.valueOf(scanner.nextLine().toUpperCase().trim());
        System.out.print("Destino: ");
        Parada destino = Parada.valueOf(scanner.nextLine().toUpperCase().trim());
        System.out.print("Kilometros: ");
        int kilometros = scanner.nextInt();
        System.out.print("Tiempo: ");
        int minutos = scanner.nextInt();
        scanner.nextLine(); 

        Ruta nuevaRuta = new Ruta(idRuta,origen, destino, kilometros,minutos); 
        rutas.add(nuevaRuta); 
    
        System.out.println("Ruta añadida con éxito.");
    }

    public String imprimirRuta() {

        return (

        "Origen: " + this.getLugarInicio().toString() +
                " Destino: " + this.getLugarFinal().toString() +
                " Duracion: " + this.getTiempo() + " horas" +
                " Distancia: " + this.getDistancia() + " km" + "\n"

        );
    }

}
