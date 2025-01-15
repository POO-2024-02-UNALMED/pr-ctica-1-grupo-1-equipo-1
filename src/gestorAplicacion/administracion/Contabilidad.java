package gestorAplicacion.administracion;

import java.util.ArrayList;
import java.util.List;

public class Contabilidad {
    // Atributos//
    static double costoCompensacion = 10.0;
    private double ingresos;
    private static ArrayList<Factura> ventas;

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

    // Metodos de clase//
    static public double calcularCompensacion(int numeroPasajeros) {
        double costoPorPasajero = costoCompensacion; // Definir el costo de compensación por pasajero, definirlo mas
                                                     // adelente
        return numeroPasajeros * costoPorPasajero;
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

    public void pagarMantenimiento() {

    }

    public void pagarReparacion() {

    }

    public void pagarEmpleado() {

    }

}