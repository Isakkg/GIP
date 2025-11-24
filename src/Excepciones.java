import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class ErrorInventario extends Exception {
    public ErrorInventario(String mensaje) {
        super(mensaje);
    }
}


class DatosInvalidosError extends ErrorInventario {
    public DatosInvalidosError(String mensaje) {
        super(mensaje);
    }
}

class StockInsuficienteError extends ErrorInventario {
    public StockInsuficienteError(String mensaje) {
        super(mensaje);
    }
}

class NombreInvalidoError extends ErrorInventario {
    public NombreInvalidoError(String mensaje) {
        super(mensaje);
    }
}

class FormatoNumericoError extends ErrorInventario {
    public FormatoNumericoError(String mensaje) {
        super(mensaje);
    }
}

