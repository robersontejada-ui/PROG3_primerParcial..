public class VehiculoMotocicleta extends Vehiculo {

    public VehiculoMotocicleta(String placa, String marca) {
        super(placa, marca);
    }

    @Override
    public String getTipo() {
        return "Motocicleta";
    }

}