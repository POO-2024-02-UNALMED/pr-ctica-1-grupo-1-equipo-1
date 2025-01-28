package gestorAplicacion.administracion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gestorAplicacion.administracion.Red.Parada;

import java.io.Serializable;

public class Contabilidad implements Serializable {
    // Atributos//
    static double costoCompensacion = 10.0;
    private double ingresos;
    private double costosOperativos;
    private static ArrayList<Factura> ventas;
    private static ArrayList<Factura> transaccionesReembolsadas;

    // Getters y Setters//
    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public static ArrayList<Factura> getVentas() {
        return ventas;
    }

    public static void setVentas(ArrayList<Factura> ventas) {
        Contabilidad.ventas = ventas;
    }

    public static ArrayList<Factura> getTransaccionesReembolsadas() {
        return transaccionesReembolsadas;
    }

    public static void setTransaccionesReembolsadas(ArrayList<Factura> transaccionesReembolsadas) {
        Contabilidad.transaccionesReembolsadas = transaccionesReembolsadas;
    }

    // Metodos de clase//
    static public double calcularCompensacion(int numeroPasajeros) {
        // Definir el costo de compensación por pasajero, definirlo más adelente.
        double costoPorPasajero = costoCompensacion;
        return numeroPasajeros * costoPorPasajero;
    }

    // Metodo Para actualizar la lista de Facturas
    public static void actualizarFacturas(Factura factura) {
        if (ventas == null) {
            ventas = new ArrayList<>();
        }
        ventas.add(factura);
    }

    public static double calcularTarifas(Factura factura) {
        double tarifaBase = 5.0; // Tarifa fija por procesamiento.
        double porcentajeReembolso = 0.02 * factura.getValor(); // 2% del valor del reembolso.
        double tarifaPorMetodo = factura.getMetodoPago() == Factura.MetodoPago.TarjetadeCredito ? 2.0 : 0.0; // operador
                                                                                                             // ternario

        return tarifaBase + porcentajeReembolso + tarifaPorMetodo;
    }

    public static double calcularDescuentos(Factura factura) {
        double descuento = 0.0;
        // Verificar si el usuario es frecuente
        int apariciones = 0;
        for (Factura f : Contabilidad.getVentas()) {
            if (f.getIdUsuario() == factura.getIdUsuario()) {
                apariciones++;
            }
        }

        // Aplicar descuento por fidelidad si el usuario aparece más de 10 veces
        if (apariciones > 10) {
            descuento += 0.1; // Descuento del 10%
        }

        // Verificar si el método de pago es transferencia y aplicar descuento
        if (factura.getMetodoPago() == Factura.MetodoPago.Transferencia) {
            descuento += 0.05; // Descuento adicional del 5%
        }

        return descuento;
    }

    public static double montoReembolso(Factura factura) {

        double tarifas = calcularTarifas(factura); // Método previamente implementado.
        double descuentos = calcularDescuentos(factura);
        double montoFinal = factura.getValor() - tarifas + descuentos;
        return montoFinal;
    }

    public static String generarDesglose(Factura factura) {
        double tarifas = calcularTarifas(factura); // Método previamente implementado.
        double descuentos = calcularDescuentos(factura);
        double montoFinal = factura.getValor() - tarifas + descuentos;
        return "Desglose de Reembolso:\n" +
                "Monto Base: $" + factura.getValor() + "\n" +
                "Tarifas Administrativas: $" + tarifas + "\n" +
                "Descuentos Aplicados: $" + descuentos + "\n" +
                "Monto Final a Transferir: $" + montoFinal;
    }

    // Metodos de Instancia//
    public ArrayList<String> reportarFinanzas(double costoCompensacion, List<Factura> facturasAjustadas) {
        ArrayList<String> mensajeCompleto = new ArrayList<String>();
        String msg1 = "===== Reporte Financiero =====";
        String msg2 = "Costo total de compensaciones: " + costoCompensacion;
        String msg3 = "Facturas ajustadas:";
        mensajeCompleto.add(msg1);
        mensajeCompleto.add(msg2);
        mensajeCompleto.add(msg3);
        for (Factura factura : facturasAjustadas) {
            String msg4 = "Factura ID: " + factura.getIdFactura() + ", Nuevo costo: " + factura.getValor();
            mensajeCompleto.add(msg4);
        }
        String msg5 = ("Fin del reporte.");
        mensajeCompleto.add(msg5);
        return mensajeCompleto;
    }

    // Método para calcular el total de ingresos
    public double calcularTotalIngresos() {
        double totalIngresos = 0.0;
        for (Factura factura : ventas) {
            totalIngresos += factura.getValor();
        }
        return totalIngresos;
    }

    public static void registrarVenta(Factura factura) {
        ventas.add(factura);
    }

    // Método para procesar reembolsos
    public void procesarReembolso(Factura factura) {
        // Verificar si la factura existe en las ventas registradas
        if (ventas.contains(factura)) {
            // Remover la factura de la lista de ventas
            ventas.remove(factura);

            // Agregar la factura a la lista de transacciones reembolsadas
            transaccionesReembolsadas.add(factura);

            // Actualizar los costos operativos
            costosOperativos += factura.getValor();

            // Actualizar los ingresos
            ingresos -= factura.getValor();

            System.out.println("Reembolso procesado para la factura ID: " + factura.getIdFactura());
        } else {
            System.out
                    .println("La factura ID: " + factura.getIdFactura() + " no se encuentra registrada en las ventas.");
        }
    }

    public static double calcularValorTiquete(Ruta ruta, String origen, String destino) {
        double valorBasePorKm = 0.05;
        Parada[] paradas = ruta.getParadas();
        double distancia = Red.longitud(paradas);
        double valorTiquete = distancia * valorBasePorKm;
        return valorTiquete;
    }

    public static <T> T[] concatenateArrays(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public void pagarMantenimiento() {

    }

    public void pagarReparacion() {

    }

    public void pagarEmpleado() {

    }

}