package br.com.fiap.gs.espacial.sensor;

public class SensorPressao extends SensorBase {
    public SensorPressao(String codigo) {
        super(codigo, 2.5, 0.2, 4.0);
    }

    @Override
    public String retornarTipo() {
        return "Sensor de Pressão (atm)";
    }
}
