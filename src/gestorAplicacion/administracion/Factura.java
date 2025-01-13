package gestorAplicacion.administracion;

public class Factura{

    //Atributos//

    private int idFacturas;
    private String usuarioNombre;
    private int valor;
    private int asientosAsignados;
    private int Fecha;
    private int cantidadMaletas;
    private Ruta Ruta_elegida;

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
        return Fecha;
    }

    public void setFecha(int fecha){
        Fecha = fecha;
    }

    public int getIdFacturas(){
        return idFacturas;
    }

    public void setIdFacturas(int idFacturas){
        this.idFacturas = idFacturas;
    }

    public Ruta getRuta_elegida(){
        return Ruta_elegida;
    }

    public void setRuta_elegida(Ruta ruta_elegida){
        Ruta_elegida = ruta_elegida;
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