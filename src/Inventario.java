import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Inventario {

    public static void guardarProductos(ArrayList<Producto> productos) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productos.dat"));
            oos.writeObject(productos);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error al guardar productos.");
        }
    }

    public static ArrayList<Producto> cargarProductos() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("productos.dat"));
            ArrayList<Producto> productos = (ArrayList<Producto>) ois.readObject();
            ois.close();
            return productos;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
