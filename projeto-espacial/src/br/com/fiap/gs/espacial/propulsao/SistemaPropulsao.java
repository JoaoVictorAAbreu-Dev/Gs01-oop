package br.com.fiap.gs.espacial.propulsao;

import br.com.fiap.gs.espacial.enums.StatusSistema;
import br.com.fiap.gs.espacial.exception.ValorInvalidoException;

public abstract class SistemaPropulsao {
    private final String id;
    private final String modelo;
    private StatusSistema status;
    private double potenciaAtual;
    private double empuxoBase;

    protected SistemaPropulsao(String id, String modelo, double empuxoBase) {
        if (id == null || id.isBlank()) {
            throw new ValorInvalidoException("ID da propulsão é obrigatório.");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new ValorInvalidoException("Modelo da propulsão é obrigatório.");
        }
        if (empuxoBase <= 0) {
            throw new ValorInvalidoException("Empuxo base deve ser positivo.");
        }
        this.id = id;
        this.modelo = modelo;
        this.empuxoBase = empuxoBase;
        this.status = StatusSistema.DESLIGADO;
    }

    public void ligar() {
        this.status = StatusSistema.LIGADO;
        System.out.println(modelo + " ligado.");
    }

    public void desligar() {
        this.status = StatusSistema.DESLIGADO;
        this.potenciaAtual = 0;
        System.out.println(modelo + " desligado.");
    }

    public abstract double acelerar(double percentualPotencia);

    protected void validarPotencia(double percentualPotencia) {
        if (percentualPotencia < 0 || percentualPotencia > 100) {
            throw new ValorInvalidoException("Potência deve estar entre 0 e 100%.");
        }
        if (status != StatusSistema.LIGADO) {
            throw new ValorInvalidoException("Não é possível acelerar com o sistema de propulsão desligado.");
        }
    }

    protected double calcularEmpuxoPadrao(double percentualPotencia) {
        this.potenciaAtual = percentualPotencia;
        return empuxoBase * (percentualPotencia / 100.0);
    }

    public String resumo() {
        return String.format("ID: %s | Modelo: %s | Status: %s | Potência: %.2f%% | Empuxo base: %.2f kN",
                id, modelo, status, potenciaAtual, empuxoBase);
    }

    public String getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public StatusSistema getStatus() {
        return status;
    }

    public double getPotenciaAtual() {
        return potenciaAtual;
    }

    public double getEmpuxoBase() {
        return empuxoBase;
    }
}
