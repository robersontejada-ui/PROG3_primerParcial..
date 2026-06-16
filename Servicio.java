import java.time.LocalDate;

public class Servicio {
    private String descripcion;
    private double costo;
    private LocalDate fecha;

    public Servicio(String descripcion, double costo) {
        this.descripcion = descripcion;
        this.costo = costo;
        this.fecha = LocalDate.now();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCosto() {
        return costo;

    }

    public LocalDate getfecha() {
        return fecha;

    }

    @Override
    public String toString() {
        return fecha + " - " + descripcion + " - RD$" + costo;
    }

}
