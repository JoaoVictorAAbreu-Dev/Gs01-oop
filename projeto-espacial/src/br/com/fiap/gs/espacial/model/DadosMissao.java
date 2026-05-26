package br.com.fiap.gs.espacial.model;

import br.com.fiap.gs.espacial.exception.ValorInvalidoException;

public class DadosMissao {
    private static final double LIMITE_COMBUSTIVEL_BAIXO = 20.0;

    private String nomeMissao;
    private String coordenadas;
    private String codigoAcesso;
    private double nivelCombustivel;
    private String trajetoria;
    private int numeroTripulantes;

    public DadosMissao(String nomeMissao, String coordenadas, String codigoAcesso,
                       double nivelCombustivel, String trajetoria, int numeroTripulantes) {
        setNomeMissao(nomeMissao);
        setCoordenadas(coordenadas, codigoAcesso);
        setCodigoAcesso(codigoAcesso);
        setNivelCombustivel(nivelCombustivel);
        setTrajetoria(trajetoria);
        setNumeroTripulantes(numeroTripulantes);
    }

    public String getNomeMissao() {
        return nomeMissao;
    }

    public void setNomeMissao(String nomeMissao) {
        validarTextoObrigatorio(nomeMissao, "Nome da missão");
        this.nomeMissao = nomeMissao;
    }

    public String acessarCoordenadas(String codigoInformado) {
        validarCodigo(codigoInformado);
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas, String codigoInformado) {
        validarTextoObrigatorio(coordenadas, "Coordenadas");
        if (this.codigoAcesso != null) {
            validarCodigo(codigoInformado);
        }
        this.coordenadas = coordenadas;
    }

    public void setCodigoAcesso(String codigoAcesso) {
        validarTextoObrigatorio(codigoAcesso, "Código de acesso");
        if (codigoAcesso.length() < 6) {
            throw new ValorInvalidoException("Código de acesso deve ter pelo menos 6 caracteres.");
        }
        this.codigoAcesso = codigoAcesso;
    }

    public double getNivelCombustivel() {
        return nivelCombustivel;
    }

    public void setNivelCombustivel(double nivelCombustivel) {
        if (nivelCombustivel < 0 || nivelCombustivel > 100) {
            throw new ValorInvalidoException("Nível de combustível deve estar entre 0 e 100%.");
        }
        this.nivelCombustivel = nivelCombustivel;
    }

    public boolean isCombustivelBaixo() {
        return nivelCombustivel < LIMITE_COMBUSTIVEL_BAIXO;
    }

    public String getTrajetoria() {
        return trajetoria;
    }

    public void setTrajetoria(String trajetoria) {
        validarTextoObrigatorio(trajetoria, "Trajetória");
        this.trajetoria = trajetoria;
    }

    public int getNumeroTripulantes() {
        return numeroTripulantes;
    }

    public void setNumeroTripulantes(int numeroTripulantes) {
        if (numeroTripulantes < 0) {
            throw new ValorInvalidoException("Número de tripulantes não pode ser negativo.");
        }
        this.numeroTripulantes = numeroTripulantes;
    }

    public String resumoPublico() {
        String alertaCombustivel = isCombustivelBaixo() ? " | ATENÇÃO: combustível abaixo de 20%" : "";
        return String.format("Missão: %s | Combustível: %.2f%% | Trajetória: %s | Tripulantes: %d%s",
                nomeMissao, nivelCombustivel, trajetoria, numeroTripulantes, alertaCombustivel);
    }

    private void validarCodigo(String codigoInformado) {
        if (!codigoAcesso.equals(codigoInformado)) {
            throw new SecurityException("Código de acesso inválido. Coordenadas protegidas.");
        }
    }

    private void validarTextoObrigatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new ValorInvalidoException(campo + " é obrigatório.");
        }
    }
}
