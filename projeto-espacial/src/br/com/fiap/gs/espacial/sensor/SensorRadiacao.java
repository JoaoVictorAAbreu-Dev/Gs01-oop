package br.com.fiap.gs.espacial.sensor;

public class SensorRadiacao extends SensorBase {
    public SensorRadiacao(String codigo) {
        super(codigo, 7.0, 0.1, 10.0);
    }

    @Override
    public String retornarTipo() {
        return "Sensor de Radiação (mSv/h)";
    }
}
