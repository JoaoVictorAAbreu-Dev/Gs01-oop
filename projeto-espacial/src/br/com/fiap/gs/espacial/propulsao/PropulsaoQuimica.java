package br.com.fiap.gs.espacial.propulsao;

public class PropulsaoQuimica extends SistemaPropulsao {
    private final String tipoCombustivel;
    private final double eficienciaCombustao;

    public PropulsaoQuimica(String id, String modelo, double empuxoBase, String tipoCombustivel, double eficienciaCombustao) {
        super(id, modelo, empuxoBase);
        this.tipoCombustivel = tipoCombustivel;
        this.eficienciaCombustao = eficienciaCombustao;
    }

    @Override
    public double acelerar(double percentualPotencia) {
        validarPotencia(percentualPotencia);
        double empuxoPadrao = calcularEmpuxoPadrao(percentualPotencia);
        return empuxoPadrao * eficienciaCombustao;
    }

    @Override
    public String resumo() {
        return super.resumo() + String.format(" | Tipo: Química | Combustível: %s | Eficiência: %.2f", tipoCombustivel, eficienciaCombustao);
    }
}
