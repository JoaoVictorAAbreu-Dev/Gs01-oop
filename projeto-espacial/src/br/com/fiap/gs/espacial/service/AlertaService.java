package br.com.fiap.gs.espacial.service;

import br.com.fiap.gs.espacial.enums.NivelAlerta;
import br.com.fiap.gs.espacial.model.DadosMissao;
import br.com.fiap.gs.espacial.sensor.SensorBase;

import java.util.List;

public class AlertaService {
    public NivelAlerta classificarSensor(SensorBase sensor) {
        if (!sensor.verificarFuncionamento()) {
            return NivelAlerta.CRITICO;
        }
        double valor = sensor.getUltimaLeitura();
        double limite = sensor.getLimiteAlerta();

        if (valor >= limite * 1.25) {
            return NivelAlerta.CRITICO;
        }
        if (valor > limite) {
            return NivelAlerta.ALERTA;
        }
        if (valor >= limite * 0.85) {
            return NivelAlerta.ATENCAO;
        }
        return NivelAlerta.NORMAL;
    }

    public void verificarSensores(List<SensorBase> sensores) {
        for (SensorBase sensor : sensores) {
            sensor.lerValor();
            NivelAlerta nivel = classificarSensor(sensor);
            System.out.println(formatarMensagemSensor(sensor, nivel));
        }
    }

    public void verificarMissao(DadosMissao dadosMissao) {
        if (dadosMissao.isCombustivelBaixo()) {
            System.out.println("[ALERTA] Combustível abaixo de 20%. Planeje reabastecimento ou rota de economia.");
        } else {
            System.out.println("[NORMAL] Nível de combustível adequado.");
        }
    }

    private String formatarMensagemSensor(SensorBase sensor, NivelAlerta nivel) {
        return switch (nivel) {
            case CRITICO -> "[CRÍTICO] " + sensor.resumoLeitura() + " | Ação imediata necessária.";
            case ALERTA -> "[ALERTA] " + sensor.resumoLeitura() + " | Valor acima do limite.";
            case ATENCAO -> "[ATENÇÃO] " + sensor.resumoLeitura() + " | Valor próximo ao limite.";
            case NORMAL -> "[NORMAL] " + sensor.resumoLeitura();
        };
    }
}
