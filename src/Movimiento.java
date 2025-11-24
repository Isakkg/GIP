class Movimiento {
    private String tipo;
    private Producto producto;
    private int cantidad;

    public Movimiento(String tipo, Producto producto, int cantidad) throws DatosInvalidosError {
        this.tipo = tipo;
        this.producto = producto;
        this.cantidad = cantidad;
        validarDatos();
    }
}
