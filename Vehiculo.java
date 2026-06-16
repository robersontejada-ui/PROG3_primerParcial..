
import java.util.ArrayList;

public abstract class Vehiculo {
    private String placa;
    private String marca;
    private ArrayList<Servicio> servicios;

    public Vehiculo(String placa, String marca) {
        this.placa = placa;
        this.marca = marca;
        this.servicios = new ArrayList<>();
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    public double calcularCostoTotal() {
        double total = 0;
        for (Servicio s : servicios) {
            total += s.getCosto();
        }

        return total;
    }

    public abstract String getTipo();
}
