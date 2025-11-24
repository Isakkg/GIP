import java.io.Serializable;

    public class Proveedor implements Serializable {
        private String nombre;
        private String contacto;

        public Proveedor(String nombre, String contacto) {
            this.nombre = nombre;
            this.contacto = contacto;
        }

        public String getNombre() {
            return nombre;



        }
    }

