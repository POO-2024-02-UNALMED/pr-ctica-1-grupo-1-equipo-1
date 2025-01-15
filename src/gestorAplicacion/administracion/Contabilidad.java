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
    public void reportarFinanzas(double costoCompensacion, List<Factura> facturasAjustadas) {
        System.out.println("===== Reporte Financiero =====");
        System.out.println("Costo total de compensaciones: " + costoCompensacion);
        System.out.println("Facturas ajustadas:");
        for (Factura factura : facturasAjustadas) {
            System.out.println("Factura ID: " + factura.getIdFactura() + ", Nuevo costo: " + factura.getValor());
        }
        System.out.println("Fin del reporte.");
    }

    public void pagarMantenimiento() {

    }

    public void pagarReparacion() {

    }

    public void pagarEmpleado() {

    }

}