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

        static void entradaStock() {
            System.out.print("Nombre del producto: ");
            String nombre = sc.nextLine();

            Producto encontrado = buscarProducto(nombre);

            if (encontrado == null) {
                System.out.println("Error: Producto no encontrado.");
                return;
            }

            System.out.print("Cantidad a ingresar: ");
            try {
                int cantidad = Integer.parseInt(sc.nextLine());

                if (cantidad <= 0) {
                    System.out.println("La cantidad debe ser mayor a 0.");
                } else {
                    encontrado.setStock(encontrado.getStock() + cantidad);


                    Inventario.guardarProductos(productos);

                    System.out.println("Stock agregado.");
                }

            } catch (Exception e) {
                System.out.println("Error: Ingrese solo números.");
            }
        }

        static void salidaStock() {

            System.out.println("\n=== PRODUCTOS DISPONIBLES ===");
            for (Producto p : productos) {
                if (p.getStock() > 0) {
                    System.out.println("- " + p.getNombre() + " | Stock: " + p.getStock());
                }
            }

            System.out.print("\nNombre del producto: ");
            String nombre = sc.nextLine();

            Producto encontrado = buscarProducto(nombre);

            if (encontrado == null) {
                System.out.println("Error: Producto no encontrado.");
                return;
            }

            System.out.print("Cantidad a retirar: ");

            try {
                int cantidad = Integer.parseInt(sc.nextLine());

                if (cantidad <= 0) {
                    System.out.println("La cantidad debe ser mayor a 0.");
                } else if (cantidad > encontrado.getStock()) {
                    System.out.println("Error: Stock insuficiente.");
                } else {
                    encontrado.setStock(encontrado.getStock() - cantidad);


                    Inventario.guardarProductos(productos);

                    System.out.println("Venta registrada.");
                }

            } catch (Exception e) {
                System.out.println("Error: Ingrese solo números.");
            }
        }


        static void verProductos() {

            if (productos.isEmpty()) {
                System.out.println("No hay productos registrados.");
                return;
            }

            System.out.println("\n=== INVENTARIO ===");
            for (Producto p : productos) {
                System.out.println(p.getNombre() + " | Precio: " + p.getPrecio() + " | Stock: " + p.getStock());

                if (p.getStock() <= 2) {
                    System.out.println("ALERTA: Stock bajo");
                }
            }
        }

        static Producto buscarProducto(String nombre) {
            for (Producto p : productos) {
                if (p.getNombre().equalsIgnoreCase(nombre)) {
                    return p;
                }
            }
            return null;
        }
    }



