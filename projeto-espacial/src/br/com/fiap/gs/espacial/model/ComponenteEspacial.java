package br.com.fiap.gs.espacial.model;

import br.com.fiap.gs.espacial.enums.StatusSistema;
import br.com.fiap.gs.espacial.exception.ValorInvalidoException;

public abstract class ComponenteEspacial {
    private final String id;
    private String nome;
    private StatusSistema status;
    private double temperatura;

    protected ComponenteEspacial(String id, String nome, double temperatura) {
        if (id == null || id.isBlank()) {
            throw new ValorInvalidoException("O ID do componente é obrigatório.");
        }
        this.id = id;
        setNome(nome);
        setTemperatura(temperatura);
        this.status = StatusSistema.DESLIGADO;
    }

    public void ligar() {
        this.status = StatusSistema.LIGADO;
        System.out.println(nome + " ligado com sucesso.");
    }

    public void desligar() {
        this.status = StatusSistema.DESLIGADO;
        System.out.println(nome + " desligado com sucesso.");
    }

    public abstract String diagnosticar();

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new ValorInvalidoException("O nome do componente é obrigatório.");
        }
        this.nome = nome;
    }

    public StatusSistema getStatus() {
        return status;
    }

    protected void setStatus(StatusSistema status) {
        this.status = status;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        if (temperatura < -273.15) {
            throw new ValorInvalidoException("Temperatura não pode ser menor que o zero absoluto.");
        }
        this.temperatura = temperatura;
    }

    public String resumo() {
        return String.format("ID: %s | Nome: %s | Status: %s | Temperatura: %.2f °C", id, nome, status, temperatura);
    }
}
