package br.com.fiap.gs.espacial.sensor;

public interface Sensor {
    double lerValor();

    boolean verificarFuncionamento();

    String retornarTipo();

    double getLimiteAlerta();

    boolean estaAcimaDoLimite();
}
