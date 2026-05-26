package br.com.fiap.gs.espacial.sensor;

public class SensorTemperatura extends SensorBase {
    public SensorTemperatura(String codigo) {
        super(codigo, 80.0, -20.0, 120.0);
    }

    @Override
    public String retornarTipo() {
        return "Sensor de Temperatura (°C)";
    }
}
