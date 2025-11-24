public class Excepciones {

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
}
