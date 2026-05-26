package br.com.fiap.gs.espacial.model;

public class ModuloComunicacao extends ComponenteEspacial {
    private double intensidadeSinal;

    public ModuloComunicacao(String id, String nome, double temperatura, double intensidadeSinal) {
        super(id, nome, temperatura);
        setIntensidadeSinal(intensidadeSinal);
    }

    @Override
    public String diagnosticar() {
        if (intensidadeSinal < 30) {
            return "ALERTA: sinal de comunicação fraco.";
        }
        if (getTemperatura() > 85) {
            return "CRÍTICO: módulo de comunicação superaquecido.";
        }
        return "Comunicação estável e operacional.";
    }

    public double getIntensidadeSinal() {
        return intensidadeSinal;
    }

    public void setIntensidadeSinal(double intensidadeSinal) {
        if (intensidadeSinal < 0 || intensidadeSinal > 100) {
            throw new IllegalArgumentException("Intensidade do sinal deve estar entre 0 e 100.");
        }
        this.intensidadeSinal = intensidadeSinal;
    }
}
