public class VehiculoAutomovil extends Vehiculo {
    public VehiculoAutomovil(String placa, String marca) {
        super(placa, marca);
    }

    @Override
    public String getTipo() {
        return "Automovil";
    }

}
