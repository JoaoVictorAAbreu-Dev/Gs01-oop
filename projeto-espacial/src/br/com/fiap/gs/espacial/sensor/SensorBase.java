package br.com.fiap.gs.espacial.sensor;

import java.util.Random;

public abstract class SensorBase implements Sensor {
    private final String codigo;
    private final double limiteAlerta;
    private final double valorMinimo;
    private final double valorMaximo;
    private final Random random;
    private double ultimaLeitura;
    private boolean ativo;

    protected SensorBase(String codigo, double limiteAlerta, double valorMinimo, double valorMaximo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Código do sensor é obrigatório.");
        }
        if (valorMinimo >= valorMaximo) {
            throw new IllegalArgumentException("Faixa de leitura inválida.");
        }
        this.codigo = codigo;
        this.limiteAlerta = limiteAlerta;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.random = new Random();
        this.ativo = true;
    }

    @Override
    public double lerValor() {
        ultimaLeitura = valorMinimo + (valorMaximo - valorMinimo) * random.nextDouble();
        return ultimaLeitura;
    }

    @Override
    public boolean verificarFuncionamento() {
        return ativo;
    }

    @Override
    public double getLimiteAlerta() {
        return limiteAlerta;
    }

    @Override
    public boolean estaAcimaDoLimite() {
        return ultimaLeitura > limiteAlerta;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getUltimaLeitura() {
        return ultimaLeitura;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String resumoLeitura() {
        return String.format("%s [%s] | Valor: %.2f | Limite: %.2f | Funcionando: %s",
                retornarTipo(), codigo, ultimaLeitura, limiteAlerta, verificarFuncionamento() ? "Sim" : "Não");
    }
}
