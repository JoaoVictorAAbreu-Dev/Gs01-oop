package br.com.fiap.gs.espacial.propulsao;

public class PropulsaoEletrica extends SistemaPropulsao {
    private final double consumoEnergiaKw;
    private final double fatorIonizacao;

    public PropulsaoEletrica(String id, String modelo, double empuxoBase, double consumoEnergiaKw, double fatorIonizacao) {
        super(id, modelo, empuxoBase);
        this.consumoEnergiaKw = consumoEnergiaKw;
        this.fatorIonizacao = fatorIonizacao;
    }

    @Override
    public double acelerar(double percentualPotencia) {
        validarPotencia(percentualPotencia);
        double empuxoPadrao = calcularEmpuxoPadrao(percentualPotencia);
        return empuxoPadrao * fatorIonizacao;
    }

    @Override
    public String resumo() {
        return super.resumo() + String.format(" | Tipo: Elétrica | Consumo: %.2f kW | Fator ionização: %.2f", consumoEnergiaKw, fatorIonizacao);
    }
}
