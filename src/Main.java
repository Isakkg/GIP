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
                        System.out.println("❌ Opción inválida.");
                }

            } while (opcion != 5);
        }
