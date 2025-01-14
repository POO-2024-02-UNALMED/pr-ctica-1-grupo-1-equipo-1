package gestorAplicacion.administracion;

public class Factura{
    // Atributos
    private int idFacturas;
    private String usuarioNombre;
    private int valor;
    private int asientosAsignados;
    private int fecha;
    private int cantidadMaletas;
    private Ruta rutaElegida;
    private Parada origen;
    private Parada destino;

    //Getters y Setters//

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

    public int getFecha(){
        return fecha;
    }

    public void setFecha(int fecha){
        this.fecha = fecha;
    }

    public int getIdFacturas(){
        return idFacturas;
    }

    public void setIdFacturas(int idFacturas){
        this.idFacturas = idFacturas;
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