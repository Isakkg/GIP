import java.util.ArrayList;
import java.util.Scanner;

    public class Main {

        static ArrayList<Producto> productos = new ArrayList<>();
        static Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {

            int opcion = 0;

            do {
                System.out.println("\n====== SISTEMA DE INVENTARIO ======");
                System.out.println("1. Registrar producto");
                System.out.println("2. Entrada de stock");
                System.out.println("3. Salida de stock");
                System.out.println("4. Ver productos");
                System.out.println("5. Salir");
                System.out.print("Elige una opción: ");

                try {
                    opcion = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    opcion = 0;
                }

                switch (opcion) {

                    case 1:
                        registrarProducto();
                        break;

                    case 2:
                        entradaStock();
                        break;

                    case 3:
                        salidaStock();
                        break;

                    case 4:
                        verProductos();
                        break;

                    case 5:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println(" Opción inválida.");
                }

            } while (opcion != 5);
        }
        static void registrarProducto() {

            String nombre = "";
            boolean nombreValido = false;

            while (!nombreValido) {
                System.out.print("Nombre: ");
                nombre = sc.nextLine();

                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    System.out.println("Error: El nombre solo puede contener letras.");
                    continue;
                }

                if (buscarProducto(nombre) != null) {
                    System.out.println("Error: Ya existe un producto con ese nombre.");
                    continue;
                }

                nombreValido = true;
            }

            double precio = 0;
            boolean precioValido = false;

            while (!precioValido) {
                System.out.print("Precio: ");
                String input = sc.nextLine();

                try {
                    input = input.replace(".", "").replace(",", ".");
                    precio = Double.parseDouble(input);
                    precioValido = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese solo números.");
                    System.out.println("Ejemplos válidos: 120000000 | 120.000.000 | 1500,50 | 10.5\n");
                }
            }

            int stock = 0;
            boolean stockValido = false;

            while (!stockValido) {
                System.out.print("Stock inicial: ");
                try {
                    stock = Integer.parseInt(sc.nextLine());
                    if (stock < 0) {
                        System.out.println("El stock no puede ser negativo.");
                    } else {
                        stockValido = true;
                    }
                } catch (Exception e) {
                    System.out.println("Error: Ingrese solo números enteros.");
                }
            }

            productos.add(new Producto(nombre, precio, stock));

            Inventario.guardarProductos(productos);

            System.out.println("Producto registrado.");
        }


