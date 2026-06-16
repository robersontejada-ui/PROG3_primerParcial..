import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Taller {
    private static ArrayList<Vehiculo> vehiculos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isFlujoActivo_ControlGeneral = true;
        Thread hiloSecundario_ProcesoAsincrono = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000);
                    System.out.println("\n[Respaldo automático activo]");
                } catch (InterruptedException e) {
                    break;
                }
            }

        });
        hiloSecundario_ProcesoAsincrono.setDaemon(true);
        hiloSecundario_ProcesoAsincrono.start();

        while (isFlujoActivo_ControlGeneral) {
            System.out.println("\n===== TALLER MECANICO =====");
            System.out.println("1. Registrar vehículo");
            System.out.println("2. Listar vehículos");
            System.out.println("3. Buscar vehículo");
            System.out.println("4. Registrar servicio");
            System.out.println("5. Mostrar historial");
            System.out.println("6. Mostrar costo total");
            System.out.println("7. Guardar información");
            System.out.println("8. Cargar información");
            System.out.println("9. Salir");
            System.out.print("Opción: ");
            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Opción inválida.");
                continue;
            }
            switch (opcion) {
                case 1:
                    System.out.print("Placa: ");
                    String placa = scanner.nextLine();
                    boolean existe = false;
                    for (Vehiculo v : vehiculos) {
                        if (v.getPlaca().equalsIgnoreCase(placa)) {
                            existe = true;
                            break;
                        }
                    }
                    if (existe) {
                        System.out.println("La placa ya existe.");
                        break;
                    }
                    System.out.print("Marca: ");
                    String marca = scanner.nextLine();
                    System.out.println("1. Automóvil");
                    System.out.println("2. Motocicleta");
                    System.out.print("Tipo: ");
                    int tipo = Integer.parseInt(scanner.nextLine());
                    if (tipo == 1) {
                        vehiculos.add(new VehiculoAutomovil(placa, marca));
                    } else {
                        vehiculos.add(new VehiculoMotocicleta(placa, marca));
                    }
                    System.out.println("Vehículo registrado.");
                    break;
                case 2:
                    if (vehiculos.isEmpty()) {
                        System.out.println("No hay vehículos.");
                    } else {
                        for (Vehiculo v : vehiculos) {
                            System.out.println(
                                    v.getTipo()
                                            + " | "
                                            + v.getPlaca()
                                            + " | "
                                            + v.getMarca());
                        }
                    }

                    break;

                case 3:
                    try {
                        System.out.print("Placa: ");
                        String buscar = scanner.nextLine();

                        Vehiculo v = buscarVehiculo(buscar);

                        System.out.println(
                                v.getTipo()
                                        + " - "
                                        + v.getPlaca()
                                        + " - "
                                        + v.getMarca());

                    } catch (VehiculoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 4:
                    try {
                        System.out.print("Placa: ");
                        String placaServicio = scanner.nextLine();
                        Vehiculo v = buscarVehiculo(placaServicio);
                        System.out.print("Descripción: ");
                        String descripcion = scanner.nextLine();
                        System.out.print("Costo: ");
                        double costo = Double.parseDouble(scanner.nextLine());

                        if (costo < 0) {
                            System.out.println("El costo no puede ser negativo.");
                            break;
                        }

                        Servicio servicio = new Servicio(descripcion, costo);

                        v.agregarServicio(servicio);

                        System.out.println("Servicio registrado.");

                    } catch (VehiculoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 5:

                    try {

                        System.out.print("Placa: ");
                        String placaHistorial = scanner.nextLine();

                        Vehiculo v = buscarVehiculo(placaHistorial);

                        if (v.getServicios().isEmpty()) {
                            System.out.println("Sin servicios.");
                        } else {

                            for (Servicio s : v.getServicios()) {
                                System.out.println(s);
                            }
                        }

                    } catch (VehiculoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 6:

                    try {

                        System.out.print("Placa: ");
                        String placaCosto = scanner.nextLine();

                        Vehiculo v = buscarVehiculo(placaCosto);

                        System.out.println(
                                "Costo acumulado: RD$"
                                        + v.calcularCostoTotal());

                    } catch (VehiculoNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                case 7:

                    try {

                        FileWriter manejadorDestino_FileI_O = new FileWriter("vehiculos.txt");

                        for (Vehiculo v : vehiculos) {

                            manejadorDestino_FileI_O.write(
                                    v.getTipo() + ";"
                                            + v.getPlaca() + ";"
                                            + v.getMarca() + "\n");
                        }

                        manejadorDestino_FileI_O.close();

                        System.out.println("Datos guardados.");

                    } catch (Exception e) {

                        System.out.println("Error al guardar.");
                    }

                    break;

                case 8:

                    try {

                        BufferedReader lector = new BufferedReader(
                                new FileReader("vehiculos.txt"));

                        vehiculos.clear();

                        String linea;

                        while ((linea = lector.readLine()) != null) {

                            String[] datos = linea.split(";");

                            if (datos[0].equals("Automovil")) {

                                vehiculos.add(
                                        new VehiculoAutomovil(
                                                datos[1],
                                                datos[2]));
                            } else {

                                vehiculos.add(
                                        new VehiculoMotocicleta(
                                                datos[1],
                                                datos[2]));
                            }
                        }

                        lector.close();

                        System.out.println("Datos cargados.");

                    } catch (Exception e) {

                        System.out.println("Error al cargar.");
                    }

                    break;

                case 9:

                    isFlujoActivo_ControlGeneral = false;
                    System.out.println("Saliendo...");
                    break;

                default:

                    System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }

    public static Vehiculo buscarVehiculo(String placa)
            throws VehiculoNoEncontradoException {

        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        throw new VehiculoNoEncontradoException(
                "Vehículo no encontrado.");
    }
}
