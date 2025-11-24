

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

    public void validarDatos() throws DatosInvalidosError {
        if (!tipo.equals("ingreso") && !tipo.equals("salida")) {
            throw new DatosInvalidosError("Tipo inválido.");
        }
        if (cantidad <= 0) {
            throw new DatosInvalidosError("Cantidad inválida.");
        }
    }

    public void aplicar() throws StockInsuficienteError {
        if (tipo.equals("ingreso")) {
            producto.ajustarStock(cantidad);
        } else {
            producto.ajustarStock(-cantidad);
        }
    }
}


