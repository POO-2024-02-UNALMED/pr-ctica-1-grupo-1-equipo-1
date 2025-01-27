package gestorAplicacion.administracion;

import java.time.LocalDateTime;
import java.time.Duration;
import gestorAplicacion.operacion.individuos.Chofer;
import gestorAplicacion.operacion.individuos.Pasajero;
import gestorAplicacion.operacion.logistica.Bus;

import java.util.ArrayList; // Para crear la red de carreteras que tiene el terminal.
import java.util.Arrays; // Para ordenar arrays.
import java.util.Comparator;

public class Empresa {
    private String nombre;
    private Chofer[] empleados;
    private Bus[] busesTotales;
    private ArrayList<Ruta> rutas;
    private int caja;

    // Constructores.
    public Empresa(String nombre) {
        this(nombre, new Chofer[0], new Bus[0], new ArrayList<Ruta>(), 0);
    }

    public Empresa(String nombre, Chofer[] empleados, Bus[] busestotales, ArrayList<Ruta> rutas, int caja) {
        this.nombre = nombre;
        setEmpleados(empleados);
        setBusesTotales(busestotales);
        setRutas(rutas);
        this.caja = caja;
    }

    // Métodos get-set.
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nuevoNombre) {
        nombre = nuevoNombre;
    }

    public Chofer[] getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Chofer[] nuevosEmpleados) {
        Chofer[] empleadosUnicos = new Chofer[nuevosEmpleados.length];
        Boolean esUnico;
        int contandoUnicos = 0;

        // Viendo cuántos buses existen y son únicos.
        for (Chofer empleado : nuevosEmpleados) {
            if (empleado != null) {
                // Viendo si el bus ya había sido incluido.
                esUnico = true;
                for (Chofer empleadoUnico : empleadosUnicos) {
                    if (empleadoUnico == empleado) {
                        esUnico = false;
                        break;
                    }
                }

                if (esUnico) {
                    empleadosUnicos[contandoUnicos] = empleado;
                    contandoUnicos++;
                }
            }
        }

        // Dejando solo los únicos
        empleados = Arrays.copyOfRange(empleadosUnicos, 0, contandoUnicos);
    }

    public Bus[] getBusesTotales() {
        return busesTotales;
    }

    public void setBusesTotales(Bus[] nuevosBusesTotales) {
        Bus[] busesUnicos = new Bus[nuevosBusesTotales.length];
        Boolean esUnico;
        int contandoUnicos = 0;

        // Viendo cuántos buses existen y son únicos.
        for (Bus bus : nuevosBusesTotales) {
            if (bus != null) {
                // Viendo si el bus ya había sido incluido.
                esUnico = true;
                for (Bus busUnico : busesUnicos) {
                    if (busUnico == bus) {
                        esUnico = false;
                        break;
                    }
                }

                if (esUnico) {
                    busesUnicos[contandoUnicos] = bus;
                    contandoUnicos++;
                }
            }
        }

        // Dejando solo los únicos
        busesTotales = Arrays.copyOfRange(busesUnicos, 0, contandoUnicos);
    }

    public ArrayList<Ruta> getRutas() {
        return this.rutas;
    }

    public void setRutas(ArrayList<Ruta> nuevasRutas) {
        ArrayList<Ruta> rutasUnicas = new ArrayList<Ruta>();
        Boolean esUnico;

        // Viendo cuántos buses existen y son únicos.
        for (Ruta ruta : nuevasRutas) {
            if (ruta != null) {
                // Viendo si el bus ya había sido incluido.
                esUnico = true;
                for (Ruta rutaUnica : rutasUnicas) {
                    if (rutaUnica == ruta) {
                        esUnico = false;
                        break;
                    }
                }

                if (esUnico) {
                    rutasUnicas.add(ruta);
                }
            }
        }

        // Dejando solo los únicos
        rutas = rutasUnicas;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int nuevaCaja) {
        caja = nuevaCaja;
    }

    // Métodos de instancia

    // Todas las formas de contratar un chofer.
    void contratar(String nombre, int edad, int sueldo) {
        contratar(new Chofer(sueldo), sueldo, null);
    }

    void contratar(int sueldo, ArrayList<LocalDateTime[]> horario) {
        contratar(new Chofer(sueldo), sueldo, horario);
    }

    void contratar(Chofer chofer, int sueldo) {
        contratar(chofer, sueldo, null);
    }

    void contratar(Chofer chofer, int sueldo, ArrayList<LocalDateTime[]> horario) {
        /*
         * Agrega un chofer a la nómina.
         * 
         * Parámetros:
         * - chofer: Chofer,
         * Persona a contratar.
         * - sueldo: int,
         * Sueldo ofrecido para contratar.
         * - horario: ArrayList<LocalDateTime[]>,
         * Horario con el que va a iniciar.
         */

        // Como los empleados se almacenan en un array, se busca si está disponible un
        // espacio para incluirlo.
        Boolean hayPuesto = false;
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] == null) {
                // Se mira si la persona acepta el empleo.
                if (chofer.getSueldo() >= sueldo) {
                    // En caso de estar vinculado a una empresa existente, se le pide la renuncia.
                    if (chofer.getEmpresa() != null) {
                        chofer.getEmpresa().despedir(chofer);
                    }

                    // Se incluye al nuevo empleado en la nómina.
                    empleados[i] = chofer;
                    chofer.setEmpresa(this);
                    hayPuesto = true;
                    chofer.setHorario(horario);
                }

                break;
            }
        }

        // En caso de no haber espacio en el array, se crea uno nuevo con un espacio
        // extra.
        if (!hayPuesto) {
            // Se crea un array temporal para almacenar a todos los empleados
            Chofer[] temp = new Chofer[empleados.length + 1];
            for (int i = 0; i < empleados.length; i++) {
                temp[i] = empleados[i];
            }

            // Se incluye el nuevo empleado.
            temp[empleados.length] = chofer;
            chofer.setEmpresa(this);

            // Se cambia el array anterior por el nuevo.
            empleados = temp;
        }
    }

    // Despedir un chofer.
    void despedir(Chofer chofer) {
        /*
         * Quita al chofer indicado de la nómina.
         * 
         * Parámetros:
         * - chofer: Chofer,
         * Empleado a despedir.
         */

        // Buscando si el empleado está vinculado a la empresa y limpiando toda relación
        // con el mismo.
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] == chofer) {
                chofer.setSueldo(0);
                chofer.setCantidadHorasConducidas(0);
                chofer.setHorario(null);
                chofer.setEmpresa(null);
                empleados[i] = null;
            }
        }

        setEmpleados(empleados);
    }

    // Todas las formas de comprar un bus.
    void comprarBus(int valor) {
        comprarBus(valor, null);
    }

    void comprarBus(int valor, ArrayList<Ruta> rutasFuturas) {
        // Generar un bus aleatorio.
        Bus busAleatorio = new Bus("A", 50, Bus.PesoMaxEquipaje.LIGERO);
        comprarBus(busAleatorio, valor, rutasFuturas);
    }

    void comprarBus(Bus bus, int valor) {
        ;
        comprarBus(bus, valor, null);
    }

    void comprarBus(Bus bus, int valor, ArrayList<Ruta> rutasFuturas) {
        /*
         * Agrega un bus a las utilidades.
         * 
         * Parámetros:
         * - bus: Bus,
         * Bus a comprar.
         * - valor: int,
         * Costo del bus.
         * - rutasFuturas: ArrayList<Ruta>,
         * Rutas con el que va a iniciar.
         */

        // Como los buses se almacenan en un array, se busca si está disponible un
        // espacio para incluirlo.
        Boolean hayPuesto = false;
        for (int i = 0; i < empleados.length; i++) {
            if (busesTotales[i] == null) {
                // En caso de estar vinculado a una empresa existente, se le pide la
                // desvinculaciín.
                if (bus.getEmpresa() != null) {
                    bus.getEmpresa().desvincularBus(bus);
                }

                // Se incluye al nuevo bus en las utilidades
                busesTotales[i] = bus;
                bus.setEmpresa(this);
                bus.setRutasFuturas(rutasFuturas);
                hayPuesto = true;
                break;
            }
        }

        // En caso de no haber espacio en el array, se crea uno nuevo con un espacio
        // extra.
        if (!hayPuesto) {
            // Se crea un array temporal para almacenar a todos los buses.
            Bus[] temp = new Bus[busesTotales.length + 1];
            for (int i = 0; i < busesTotales.length; i++) {
                temp[i] = busesTotales[i];
            }

            // Se incluye el nuevo empleado.
            temp[busesTotales.length] = bus;
            bus.setEmpresa(this);

            // Se cambia el array anterior por el nuevo.
            busesTotales = temp;
        }

        setBusesTotales(busesTotales);
    }

    // Desvincular un bus de la empresa.
    void desvincularBus(Bus bus) {
        /*
         * Quita al bus vinculado a la empresa.
         * 
         * Parámetros:
         * - bus: Bus,
         * Bus a desvincular.
         */

        // Buscando si el bus está vinculado a la empresa y limpiando toda relación
        // con el mismo.
        for (int i = 0; i < busesTotales.length; i++) {
            if (busesTotales[i] == bus) {
                // Haciendo que las rutas no tengan el bus que se eliminó.
                for (Ruta ruta : bus.getRutasFuturas()) {
                    ruta.setBusAsociado(null);
                }

                // Desvinculando el bus con la empresa.
                bus.setRutasFuturas(null);
                bus.setEmpresa(null);
                busesTotales[i] = null;
            }
        }
    }

    // Estaba muy básica anteriormente, se modificó para que
    // busque el bus con disponibilidad de horario con fecha más cercana.
    void asignarRuta(Ruta ruta) {
        /*
         * Dado una ruta establecida, se busca el bus que tiene un horario disponible
         * que cumpla las horas establecidas por la ruta. Dentro de esto, se escoge el
         * que
         * su horario de disponibilidad esté más cercano.
         * 
         * Parámetros:
         * - lapso: int,
         * Cantidad de tiempo que debe estar libre el bus.
         * 
         * Retorna:
         * - busEncontrado: Bus,
         * Bus que va a tener esa ruta.
         */

        // Viendo que haya buses.
        if (busesTotales.length == 0) {
            // Generar precio aleatorio para comprar bus.
            int precioAleatorio = (int) (Math.random() * 1000) + 17000;
            this.comprarBus(precioAleatorio);
        }

        // Viendo la duración de la ruta.
        int lapso = ruta.duracion();

        // Estableciendo una cota a la fecha.
        LocalDateTime horarioBusBase = busesTotales[0].hallarHueco(lapso);
        Bus busEncontrado = busesTotales[0];

        // Buscando la fecha más cercana entre los buses.
        LocalDateTime horarioBus = LocalDateTime.now();
        for (Bus bus : busesTotales) {
            horarioBus = bus.hallarHueco(lapso);
            if (Duration.between(horarioBusBase, horarioBus).toHours() < 0) {
                horarioBusBase = horarioBus;
                busEncontrado = bus;
            }
        }

        // Asignando la ruta al bus.
        ruta.setFechaSalida(horarioBus);
        ruta.setFechaLlegada(horarioBus.plusHours(lapso));
        busEncontrado.anadirRuta(ruta);

        // Buscando un chofer para que la conduzca
        if (ruta.getChoferAsociado() != null) {
            return;
        }
        Boolean existeChambeador = false;
        for (Chofer empleado : empleados) {
            if (empleado.isDisponible(ruta.getFechaSalida(), ruta.getFechaLlegada())) {
                existeChambeador = true;
                LocalDateTime[] retornada = empleado
                        .anadirRuta(new LocalDateTime[] { ruta.getFechaSalida(), ruta.getFechaLlegada() });
                if (retornada == null) {
                    ruta.setChoferAsociado(empleado);
                    break;
                }
            }
        }

        // Viendo qué pasa si no existe el chofer.
        if (!existeChambeador) {
            // Generar sueldo aleatorio.
            int sueldoAleatorio = (int) (Math.random() * 100) + 400;
            ArrayList<LocalDateTime[]> horarioChofer = new ArrayList<LocalDateTime[]>();
            horarioChofer.add(new LocalDateTime[] { ruta.getFechaSalida(), ruta.getFechaLlegada() });

            // Contratando al nuevo chofer.
            contratar(sueldoAleatorio, horarioChofer);
            ruta.setChoferAsociado(empleados[empleados.length - 1]);
        }
    }

    public void reportarFinanzas() {

    }

    // Implementación de la funcionalidad 4.
    // Creación de rutas con paradas muy concurridas.
    public float[] flujoPromedio(Boolean eje) {// Posiblemente no nos sirva a futuro
        /*
         * Calcula el promedio de personas por viaje desde una parada a otra.
         * 
         * Parámetros:
         * - eje: bool,
         * Determina si el promedio se calcula en las filas (False) o columnas (True).
         * 
         * Retorna:
         * - promedios: int[# Paradas][# Paradas],
         * Matriz de cantidad de personas que compran este viaje.
         * La entrada i, j representa el flujo desde la ciudad con ordinal i hasta la
         * ciudad con ordinal j.
         */

        // Definición de variables

        // Matriz de personas que realizan el viaje.
        int[][] flujoDemanda = new int[Red.totalParadas][Red.totalParadas];
        // Matriz de viajes totales realizados.
        int[][] flujoOferta = new int[Red.totalParadas][Red.totalParadas];
        // Contador de rutas ya analizadas.
        ArrayList<Ruta> rutasCopia = new ArrayList<Ruta>();

        // Generando la copia del atributo rutas.
        for (Ruta ruta : rutas) {
            rutasCopia.add(ruta);
        }

        // Contando el número de personas que viajan y viajes realizados (En la
        // empresa).
        ArrayList<Factura> facturas = Contabilidad.getVentas(); // Las facturas llevan registro de esto.
        for (Factura factura : facturas) {
            // Definiendo el origen y destino de la ruta.
            Ruta rutaAsociada = factura.getRutaElegida();
            int origen = factura.getOrigen().ordinal();
            int destino = factura.getDestino().ordinal();

            // Viendo si la ruta fue realizada por esta empresa.
            if (rutas.contains(rutaAsociada)) {
                flujoDemanda[origen][destino] += factura.getNumAsientosAsignados();

                // Viendo si ya se tomó en cuenta esta ruta.
                if (rutasCopia.contains(rutaAsociada)) {
                    flujoOferta[origen][destino]++;
                    flujoOferta[destino][origen]++;
                    rutasCopia.remove(rutaAsociada);
                }
            }
        }

        // Definiendo los promedios de flujo entre paradas.
        float[] promedios = new float[Red.totalParadas];
        if (eje) {
            for (int i = 0; i < Red.totalParadas; i++) {
                // Calculando el flujo de la columna i.
                int sumaOferta = 0;
                int sumaDemanda = 0;
                for (int j = 0; j < Red.totalParadas; j++) {
                    sumaOferta += flujoOferta[j][i];
                    sumaDemanda += flujoDemanda[j][i];
                }

                // Computando el promedio del flujo.
                if (sumaOferta != 0) {
                    promedios[i] = sumaDemanda / sumaOferta;
                }
            }
        } else {
            for (int i = 0; i < Red.totalParadas; i++) {
                // Calculando el flujo de la fila i.
                int sumaOferta = 0;
                int sumaDemanda = 0;
                for (int j = 0; j < Red.totalParadas; j++) {
                    sumaOferta += flujoOferta[i][j];
                    sumaDemanda += flujoDemanda[i][j];
                }

                // Computando el promedio del flujo.
                if (sumaOferta != 0) {
                    promedios[i] = sumaDemanda / sumaOferta;
                }
            }
        }

        return promedios;
    }

    public float[][] flujoPromedio() {
        /*
         * Calcula el promedio de personas por viaje desde una parada a otra.
         * 
         * Retorna:
         * - promedios: int[# Paradas][# Paradas],
         * Matriz de cantidad de personas que compran este viaje.
         * La entrada i, j representa el flujo desde la ciudad con ordinal i hasta la
         * ciudad con ordinal j.
         */

        // Definición de variables

        // Matriz de personas que realizan el viaje.
        int[][] flujoDemanda = new int[Red.totalParadas][Red.totalParadas];
        // Matriz de viajes totales realizados.
        int[][] flujoOferta = new int[Red.totalParadas][Red.totalParadas];
        // Contador de rutas ya analizadas.
        ArrayList<Ruta> rutasCopia = new ArrayList<Ruta>();

        // Generando la copia del atributo rutas.
        for (Ruta ruta : rutas) {
            rutasCopia.add(ruta);
        }

        // Contando el número de personas que viajan y viajes realizados (En la
        // empresa).
        ArrayList<Factura> facturas = Contabilidad.getVentas(); // Las facturas llevan registro de esto.
        for (Factura factura : facturas) {
            // Definiendo el origen y destino de la ruta.
            Ruta rutaAsociada = factura.getRutaElegida();
            int origen = factura.getOrigen().ordinal();
            int destino = factura.getDestino().ordinal();

            // Viendo si la ruta fue realizada por esta empresa.
            if (rutas.contains(rutaAsociada)) {
                flujoDemanda[origen][destino] += factura.getNumAsientosAsignados();

                // Viendo si ya se tomó en cuenta esta ruta.
                if (rutasCopia.contains(rutaAsociada)) {
                    flujoOferta[origen][destino]++;
                    flujoOferta[destino][origen]++;
                    rutasCopia.remove(rutaAsociada);
                }
            }
        }

        // Definiendo los promedios de flujo entre paradas.
        float[][] promedios = new float[Red.totalParadas][Red.totalParadas];
        for (int i = 0; i < Red.totalParadas; i++) {
            for (int j = 0; j < Red.totalParadas; j++) {
                if (flujoOferta[i][j] != 0) {
                    promedios[i][j] = flujoDemanda[i][j] / flujoOferta[i][j];
                }
            }
        }

        return promedios;
    }

    // Métodos para ordenar listas de manera adecuada.
    public static Integer[] ordenar(float[] valores, Boolean invertido) {
        /*
         * Se encuentra la posición en que los valores en el array deberían tener para
         * que
         * esté ordenado. (Ejemplo: [0.5, 0.1, 0] devolvería [2, 1, 0], porque [0, 0.1,
         * 0.5] está ordenado,
         * así, 0.5 debería ocupar la posición dos, 0.1 la posición uno y 0 la posición
         * cero).
         * 
         * Parámetros:
         * - valores: float[],
         * Array a encontrar el orden.
         * - invertido: Boolean,
         * Pregunta si se escribe en orden ascendente (false) o descendente (true).
         * 
         * Retorna:
         * - posiciones: Integer[],
         * Array de posiciones que se deberían ocupar.
         */

        // Creando el array de las posiciones que ocupan los valores.
        Integer[] posiciones = new Integer[valores.length];
        for (int i = 0; i < valores.length; i++) {
            posiciones[i] = i;
        }

        // Forma de ordenar el array para hallar las posiciones.
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer n, Integer m) {
                if (valores[n] - valores[m] > 0) {
                    return 1;
                } else if (valores[n] - valores[m] < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        // Hallando las posiciones que deben tener.
        if (invertido) {
            comparator = comparator.reversed();
        }
        Arrays.sort(posiciones, comparator);

        // Devolviendo las posiciones
        return posiciones;
    }

    public static Integer[] ordenar(float[][] matrizValores, Boolean invertido) {
        /*
         * Dada una matriz del estilo {{a, b}, {c, d}}, aplica el mismo método de
         * ordenar,
         * teniendo en cuenta que se va a ordenar como si se tratara del array {a, b, c,
         * d}.
         * Es decir, se concatenan las filas.
         * 
         * Parámetros:
         * - valores: float[][],
         * Matriz a hallar el orden.
         * - invertido: Boolean,
         * Pregunta si se escribe en orden ascendente (false) o descendente (true).
         * 
         * Retorna:
         * - posiciones: Integer[],
         * Array de posiciones que se deberían ocupar.
         */

        // Escribiendo la matriz como un único array concatenando las filas.
        int numeroFilas = matrizValores.length;
        int numeroColumnas = matrizValores[0].length;
        float[] valores = new float[numeroFilas * numeroColumnas];
        for (int i = 0; i < valores.length; i++) {
            /*
             * Definiendo la relación entre un número y la casilla de la matriz que
             * representa,
             * usando el algoritmo de la división, i = (#Columnas) * q + r con el algoritmo,
             * así, la casilla que representa es [Fila][Columna] = [q][r]
             */
            valores[i] = matrizValores[i / numeroColumnas][i % numeroColumnas];
        }

        return ordenar(valores, invertido);
    }

    public static Integer[] ordenar(int[] valores, Boolean invertido) {
        /*
         * Realiza lo mismo que el método ordenar, pero con valores en int[].
         * 
         * Parámetros:
         * - valores: int[],
         * Array a encontrar el orden.
         * - invertido: Boolean,
         * Pregunta si se escribe en orden ascendente (false) o descendente (true).
         * 
         * Retorna:
         * - posiciones: Integer[],
         * Array de posiciones que se deberían ocupar.
         */

        // Pasando los enteros a float.
        float[] valoresFloat = new float[valores.length];
        for (int i = 0; i < valores.length; i++) {
            valoresFloat[i] = (float) valores[i];
        }

        // Devolviendo las posiciones
        return ordenar(valoresFloat, invertido);
    }

    // Métodos de la funcionalidad 4.
    public float[][] promediosSesgados(float[][] promedios, int paradaOrigen, int paradaDestino) {
        /*
         * Dadas paradas de origen y destino (Que podrían estar no especificados,
         * en cuyo caso se tomará -1), se calculan los flujos promedios.
         * 
         * Parámetros:
         * - promedios: float[][],
         * Promedios a analizar.
         * - paradaOrigen: int,
         * Ordinal de la primera parada para la ruta.
         * - paradaDestino: int,
         * Ordinal de la última parada para la ruta.
         * 
         * Retorna:
         * - promediosSesgados: float[][],
         * Los promedios que se desean analizar.
         */

        // Viendo solo los trayectos que se solicitan.
        float[][] promediosSesgados = new float[Red.totalParadas][Red.totalParadas];
        for (int i = 0; i < Red.totalParadas; i++) {
            for (int j = 0; j < Red.totalParadas; j++) {
                // En caso de especificar una parada, solo se involucran los valores que tengan
                // esas paradas.
                // En caso de no especificar una parada, se tomará como -1 y verán todos los
                // posibles valores.
                if ((paradaOrigen == -1 || paradaOrigen == i) && (paradaDestino == -1 || paradaDestino == j)) {
                    promediosSesgados[i][j] = promedios[i][j];
                }
            }
        }

        return promediosSesgados;
    }

    public int[] ajustarParadas(int paradaOrigen, int paradaDestino, int numeroParadas, float factor) {
        /*
         * Calcula el trayecto con parada origen -> parada destino, tal que
         * se cumple (Si se puede) el número de paradas deseadas y un factor de
         * crecimiento
         * (Medido con base a la ruta óptima dada por el algoritmo de Bellman-Ford).
         * 
         * Parámetros:
         * - paradaOrigen: int,
         * Ordinal de la primera parada para la ruta.
         * - paradaDestino: int,
         * Ordinal de la última parada para la ruta.
         * - numeroParadas: int,
         * Cantidad de paradas que se quiere tenga la ruta.
         * - factor: float,
         * Indica la cantidad máxima de expansión permitida en la ruta.
         * 
         * Retorna:
         * - paradasReales: int[],
         * Ordinales de las paradas en el trayecto que cumplen
         * (En la medida de lo posible) los requisitos.
         */

        // Iniciando las variables necesarias.
        float[][] promedios = flujoPromedio();
        float[][] promediosSesgados = promediosSesgados(promedios, paradaOrigen, paradaDestino);

        /*
         * Contruyendo un array que involucre todos los trayectos posibles
         * Aquí, la entrada i = Red.totalParadas * q + r (Expresión con el algoritmo de
         * la
         * división), nos indica
         * que es el trayecto desde la parada con ordinal (q) hacia la ciudad con
         * ordinal (r).
         * Y a este se le busca cuáles tienen los mayores viajes según las
         * especificaciones.
         */
        Integer[] tuplasParadas = ordenar(promediosSesgados, true);

        // Tomando la ruta según el algoritmo de BellmanFord modificado.
        int ordinalOrigen = tuplasParadas[0] / Red.totalParadas; // Ciudad mayor flujo saliente.
        int ordinalDestino = tuplasParadas[0] % Red.totalParadas; // Ciudad mayor flujo entrante.
        int[] paradasOptimas = Red.algoritmoBellmanFord(ordinalOrigen, ordinalDestino);

        // Ajustando para que se tenga la cantidad de paradas deseada.
        int numeroParadasCreadas = paradasOptimas.length;
        int[] paradasReales = new int[numeroParadas];
        if (numeroParadasCreadas > numeroParadas) {
            /*
             * Se buscará una ruta que maximice la cantidad de personas que usarán la ruta.
             * Para esto se verá las paradas con mayor cantidad de personas que realizan el
             * viaje
             * parada inicial -> parada, para cada parada.
             */
            float[] SalidasParadasCreadas = new float[numeroParadasCreadas - 2];
            for (int i = 0; i < numeroParadasCreadas - 2; i++) {
                int ordinalActual = paradasOptimas[i + 1];
                SalidasParadasCreadas[i] = promedios[ordinalOrigen][ordinalActual];
            }

            // Hallando el orden de la cantidad de personas que se bajan en esa parada.
            Integer[] concurrencia = ordenar(SalidasParadasCreadas, true);

            // Creando el array con las paradas adecuadas.
            paradasReales[0] = paradasOptimas[0];
            paradasReales[numeroParadas - 1] = paradasOptimas[numeroParadasCreadas - 1];
            for (int i = 1; i < numeroParadas - 1; i++) {
                paradasReales[i] = paradasOptimas[concurrencia[i - 1]];
            }

            // Ordenando las paradas.
            paradasReales = Red.ordenarParadas(paradasReales);
        } else if (numeroParadasCreadas < numeroParadas) {
            // Viendo la distancia del trayecto.
            int recorridoTotal = Red.distancias[ordinalOrigen][ordinalDestino];

            // Corrección de errores.
            if (numeroParadas > Red.totalParadas) {
                numeroParadas = Red.totalParadas;
            }

            // Viendo las paradas que están y las que no en la ruta.
            int[] enRuta = new int[numeroParadasCreadas];
            int[] noEnRuta = new int[Red.totalParadas - numeroParadasCreadas];

            // Enlistando las que aparecen en la ruta,
            for (int i = 0; i < numeroParadasCreadas; i++) {
                enRuta[i] = paradasOptimas[i];
            }

            // Enlistando las que no aparecen en la ruta.
            Arrays.sort(enRuta);
            int desplazamiento = 0;
            for (int i = 0; i < Red.totalParadas; i++) {
                if (i < enRuta[desplazamiento]) {
                    noEnRuta[i - desplazamiento] = i;
                } else {
                    desplazamiento++;
                }
            }

            // Viendo los flujos entrantes desde paradas en la ruta hasta paradas que no
            // están en la ruta.
            float[] rutaANoRuta = new float[noEnRuta.length];
            int[] separacion;
            for (int i = 0; i < noEnRuta.length; i++) {
                separacion = Red.posicion(paradasOptimas, noEnRuta[i]);
                for (int j = 0; j < enRuta.length; j++) {
                    // Viendo si la parada en la ruta está antes o después de la parada que no está
                    // en la ruta.
                    if (enRuta[j] <= separacion[0]) {
                        // Si está antes, importa es cuántos se bajan en la parada que no está en la
                        // ruta.
                        rutaANoRuta[i] += promedios[enRuta[j]][noEnRuta[i]];
                    } else if (enRuta[j] > separacion[0]) {
                        // Si está después, importa es cuántos se suben en la parada que no está en la
                        // ruta.
                        rutaANoRuta[i] += promedios[noEnRuta[i]][enRuta[j]];
                    }
                }
            }

            // Visualizando la contribución individual en distancia de cada parada a añadir.
            int[] contribucionIndividual = new int[noEnRuta.length];
            int[] posicion; // Para ver la posición que debería ocupar.
            int contribucion; // Distancia que contribuye.
            int paradaAnterior, paradaPosterior; // Paradas donde se encuentra ensandwichado.
            for (int i = 0; i < noEnRuta.length; i++) {
                posicion = Red.posicion(paradasOptimas, noEnRuta[i]);
                paradaAnterior = paradasOptimas[posicion[0] + 1];
                paradaPosterior = paradasOptimas[posicion[1]];
                contribucion = Red.distancias[paradaAnterior][noEnRuta[i]] +
                        Red.distancias[paradaPosterior][noEnRuta[i]] -
                        Red.distancias[paradaAnterior][paradaPosterior];
                contribucionIndividual[i] = contribucion;
            }

            // Añadiendo las rutas faltates en orden de flujo y viendo su aporte de
            // distancia.
            int cantidadFaltante = numeroParadas - numeroParadasCreadas;
            Integer[] paradasEnOrden = ordenar(rutaANoRuta, true);
            int nuevaParada = 0;
            int[] nuevoTrayecto = new int[0];
            int[] aporte = new int[cantidadFaltante];
            for (int i = 0; i < cantidadFaltante; i++) {
                nuevaParada = noEnRuta[paradasEnOrden[i]];
                nuevoTrayecto = Red.agregarParada(paradasOptimas, nuevaParada);
                aporte[i] = contribucionIndividual[nuevaParada];
            }

            // Viendo si se cumple que no se sobrepasa la longitud máxima deseada.
            int nuevoRecorridoTotal = Red.longitud(nuevoTrayecto);
            int desfase = 0;
            Integer[] ordenDeAporte = new Integer[0];
            int paradaAEliminar; // Parada a eliminar en el siguiente paso.
            while ((nuevoRecorridoTotal > (recorridoTotal * factor)) &&
                    (desfase < paradasEnOrden.length - cantidadFaltante)) {
                // Eliminando la parada que más distancia individual contribuye.
                ordenDeAporte = ordenar(aporte, true);
                paradaAEliminar = paradasEnOrden[desfase + ordenDeAporte[0]];
                nuevoTrayecto = Red.eliminarParada(nuevoTrayecto, paradaAEliminar);

                // Añadiendo la siguiente parada a analizar.
                nuevaParada = noEnRuta[paradasEnOrden[cantidadFaltante + desfase]];
                nuevoTrayecto = Red.agregarParada(nuevoTrayecto, nuevaParada);

                // Viendo la siguiente iteración.
                aporte[ordenDeAporte[0]] = contribucionIndividual[nuevaParada];
                nuevoRecorridoTotal = Red.longitud(nuevoTrayecto);
                desfase++;
            }

            if (desfase >= paradasEnOrden.length - cantidadFaltante) {
                int cuentaRegresiva = 0;
                while ((nuevoRecorridoTotal > (recorridoTotal * factor)) &&
                        (cuentaRegresiva < cantidadFaltante)) {
                    // Eliminando progresivamente las paradas.
                    paradaAEliminar = paradasEnOrden[desfase + ordenDeAporte[cuentaRegresiva]];
                    nuevoTrayecto = Red.eliminarParada(nuevoTrayecto, paradaAEliminar);

                    // Viendo la siguiente iteración.
                    nuevoRecorridoTotal = Red.longitud(nuevoTrayecto);
                    cuentaRegresiva++;
                }
            }

            // Entregando el resultado final.
            paradasReales = nuevoTrayecto;
        } else {
            paradasReales = paradasOptimas;
        }

        return paradasReales;
    }

    public Ruta funcionalidad4(int paradaOrigen, int paradaDestino, int numeroParadas, float factor) {
        /*
         * Crea una nueva ruta y busca una posible asignación dentro de la empresa
         * (Escoger bus y chofer).
         * 
         * Parámetros:
         * - paradaOrigen: int,
         * Ordinal de la primera parada para la ruta.
         * - paradaDestino: int,
         * Ordinal de la última parada para la ruta.
         * - numeroParadas: int,
         * Cantidad de paradas que se quiere tenga la ruta.
         * - factor: float,
         * Indica la cantidad máxima de expansión permitida en la ruta
         * (En caso de tener que agregar paradas más allá de la ruta óptima).
         * 
         * Retorna:
         * - rutaOptima: Ruta,
         * Ruta que cumple con las especificaciones.
         */

        // Verificación de errores.
        if (numeroParadas < 2) {
            // Presentar un error.
        }

        // Calculando el trayecto con las especificaciones.
        int[] paradasReales = ajustarParadas(paradaOrigen, paradaDestino, numeroParadas, factor);

        int paradaInicial = paradasReales[0];
        int paradaFinal = paradasReales[paradasReales.length];
        Ruta rutaFinal = new Ruta(paradaInicial, paradaFinal, paradasReales);
        asignarRuta(rutaFinal);

        return rutaFinal;
    }

    public void optimizarEscalas(Pasajero pasajero) {
        for (Ruta ruta : rutas) {
            int pasajerosEnParada = ruta.contarPasajerosEnParada(ruta.getLugarFinal());
            if (pasajerosEnParada >= ruta.getBusAsociado().getCantidadAsientos() - 2) { // Umbral de minimo dos asientos
                                                                                        // libres
                ArrayList<Pasajero> listaEspera = new ArrayList<Pasajero>();
                for (Factura factura : Contabilidad.getVentas()) {
                    if (factura.getRutaElegida().equals(ruta)) {
                        Pasajero pasajeroEnEspera = pasajero;
                        if (pasajeroEnEspera.aceptarCambio()) {
                            listaEspera.add(pasajeroEnEspera);
                        }
                    }
                }
                double compensacion = Contabilidad.calcularCompensacion(listaEspera.size());
                // System.out.println("Costo de compensación: " + compensacion);
                // Llamada a el meteod reasignarPasajeros para darles una ruta diferente
                reasignarPasajeros(listaEspera, ruta);
            }
        }
    }

    public void reasignarPasajeros(ArrayList<Pasajero> listaEspera, Ruta rutaOrigen) {
        for (Pasajero pasajero : listaEspera) {
            ArrayList<Ruta> rutasAlternativas = rutaOrigen.obtenerRutasAlternativas();
            boolean reasingnado = false;

            for (Ruta nuevaRuta : rutasAlternativas) {
                if (nuevaRuta.getBusAsociado().isDisponible(rutaOrigen.getFechaLlegada(),
                        rutaOrigen.getFechaSalida())) {
                    // Reasignacion de el pasajero a una nueva ruta
                    nuevaRuta.getBusAsociado().asignarPasajero(pasajero);
                    rutaOrigen.getBusAsociado().eliminarPasajero(pasajero);
                    // System.out.println("Pasajero " + pasajero.getNombre() + " ha sido reasignado
                    // a la ruta "
                    // + nuevaRuta.getIdRuta());
                    reasingnado = true;
                    break;
                }
            }
            if (!reasingnado) {
                // System.out.println("No se encontró una ruta alternativa disponible para " +
                // pasajero.getNombre());
            }
        }
    }
}