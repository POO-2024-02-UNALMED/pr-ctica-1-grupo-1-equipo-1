package gestorAplicacion.administracion;
import java.time.LocalDateTime;
public class Factura{
    // Atributos
    private enum MetodoPago {Efectivo, TarjetadeCredito,TarjetadeDebito,Transferencia}

    private int idFactura;
    private String usuarioNombre;
    private int idUsuario;
    private int valor;
    private int asientosAsignados;
    private LocalDateTime fecha;
    private int cantidadMaletas;
    private Ruta rutaElegida;
    private Parada origen;
    private Parada destino;
    private MetodoPago metodoPago;
    // Constructores


    //Getters y Setters//


    public gestorAplicacion.administracion.Factura.MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(gestorAplicacion.administracion.Factura.MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getAsientosAsignados(){
        return asientosAsignados;
    }

    public void setAsientosAsignados(int asientosAsignados){
        this.asientosAsignados = asientosAsignados;
    }

    public int getCantidadMaletas(){
        return cantidadMaletas;
    }

    public void setCantidadMaletas(int cantidadMaletas){
        this.cantidadMaletas = cantidadMaletas;
    }

    public LocalDateTime getFecha(){
        return fecha;
    }

    public void setFecha(LocalDateTime fecha){
        this.fecha = fecha;
    }

    public int getIdFactura(){
        return idFactura;
    }

    public void setIdFactura(int idFactura){
        this.idFactura = idFactura;
    }

    public Ruta getRutaElegida(){
        return rutaElegida;
    }

    public void setRutaElegida(Ruta rutaElegida){
        this.rutaElegida = rutaElegida;
    }

    public String getUsuarioNombre(){
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre){
        this.usuarioNombre = usuarioNombre;
    }

    public int getValor(){
        return valor;
    }

    public void setValor(int valor){
        this.valor = valor;
    }

    public Parada getOrigen(){
        return origen;
    }

    public void setOrigen(Parada origen){
        this.origen = origen;
    }

    public Parada getDestino(){
        return destino;
    }

    public void setDestino(Parada destino){
        this.destino = destino;
    }

    //Metodos de clase//

    //Metodos de Instancia//

    public void ImprimirFactura(){

    }
    public void AplicarDescuento(){

    }
    public void asientoAsignado(){

    }
    public void cantidadMaletas(){

    }
    public void verificarEdad(){

    }
}