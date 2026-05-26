package br.com.fiap.gs.espacial.app;

import br.com.fiap.gs.espacial.exception.ValorInvalidoException;
import br.com.fiap.gs.espacial.model.DadosMissao;
import br.com.fiap.gs.espacial.model.ModuloComunicacao;
import br.com.fiap.gs.espacial.propulsao.PropulsaoEletrica;
import br.com.fiap.gs.espacial.propulsao.PropulsaoQuimica;
import br.com.fiap.gs.espacial.propulsao.SistemaPropulsao;
import br.com.fiap.gs.espacial.sensor.SensorBase;
import br.com.fiap.gs.espacial.sensor.SensorPressao;
import br.com.fiap.gs.espacial.sensor.SensorRadiacao;
import br.com.fiap.gs.espacial.sensor.SensorTemperatura;
import br.com.fiap.gs.espacial.service.AlertaService;
import br.com.fiap.gs.espacial.util.EntradaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaMonitoramento {
    private static final String CODIGO_ACESSO_PADRAO = "ORBITA-2026";

    private final Scanner scanner;
    private final List<SensorBase> sensores;
    private final List<SistemaPropulsao> propulsores;
    private final AlertaService alertaService;
    private final DadosMissao dadosMissao;
    private final ModuloComunicacao moduloComunicacao;

    public SistemaMonitoramento() {
        this.scanner = new Scanner(System.in);
        this.sensores = criarSensores();
        this.propulsores = criarPropulsores();
        this.alertaService = new AlertaService();
        this.dadosMissao = new DadosMissao(
                "Artemis Sentinel BR",
                "Órbita baixa terrestre - LAT -23.55 / LONG -46.63",
                CODIGO_ACESSO_PADRAO,
                76.5,
                "LEO -> Transferência lunar -> Órbita de monitoramento",
                4
        );
        this.moduloComunicacao = new ModuloComunicacao("COM-01", "Módulo de Comunicação Orbital", 42.5, 91.0);
    }

    public static void main(String[] args) {
        new SistemaMonitoramento().executar();
    }

    private void executar() {
        int opcao;
        do {
            exibirMenu();
            opcao = EntradaUtil.lerInteiro(scanner, "Escolha uma opção: ");
            try {
                processarOpcao(opcao);
            } catch (ValorInvalidoException | SecurityException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);

        System.out.println("Sistema de monitoramento encerrado.");
        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("\n===== PLATAFORMA DE MONITORAMENTO ESPACIAL =====");
        System.out.println("1 - Verificar sensores");
        System.out.println("2 - Controlar propulsão");
        System.out.println("3 - Gerenciar dados da missão");
        System.out.println("4 - Simular alertas");
        System.out.println("5 - Exibir status completo");
        System.out.println("6 - Diagnosticar componente espacial");
        System.out.println("0 - Sair");
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> verificarSensores();
            case 2 -> controlarPropulsao();
            case 3 -> gerenciarDadosMissao();
            case 4 -> simularAlertas();
            case 5 -> exibirStatusCompleto();
            case 6 -> diagnosticarComponente();
            case 0 -> { }
            default -> System.out.println("Opção inválida.");
        }
    }

    private void verificarSensores() {
        System.out.println("\n--- Verificação de Sensores ---");
        alertaService.verificarSensores(sensores);
    }

    private void controlarPropulsao() {
        System.out.println("\n--- Controle de Propulsão ---");
        listarPropulsores();
        int indice = EntradaUtil.lerInteiro(scanner, "Selecione o propulsor: ") - 1;

        if (indice < 0 || indice >= propulsores.size()) {
            System.out.println("Propulsor inexistente.");
            return;
        }

        SistemaPropulsao propulsor = propulsores.get(indice);
        System.out.println("1 - Ligar");
        System.out.println("2 - Desligar");
        System.out.println("3 - Acelerar");
        int acao = EntradaUtil.lerInteiro(scanner, "Ação: ");

        switch (acao) {
            case 1 -> propulsor.ligar();
            case 2 -> propulsor.desligar();
            case 3 -> {
                double potencia = EntradaUtil.lerDouble(scanner, "Potência desejada (0-100): ");
                double empuxo = propulsor.acelerar(potencia);
                System.out.printf("Empuxo gerado: %.2f kN%n", empuxo);
            }
            default -> System.out.println("Ação inválida.");
        }
    }

    private void gerenciarDadosMissao() {
        System.out.println("\n--- Dados da Missão ---");
        System.out.println("1 - Exibir resumo público");
        System.out.println("2 - Acessar coordenadas protegidas");
        System.out.println("3 - Atualizar combustível");
        System.out.println("4 - Atualizar trajetória");
        System.out.println("5 - Atualizar número de tripulantes");
        int opcao = EntradaUtil.lerInteiro(scanner, "Opção: ");

        switch (opcao) {
            case 1 -> System.out.println(dadosMissao.resumoPublico());
            case 2 -> acessarCoordenadas();
            case 3 -> atualizarCombustivel();
            case 4 -> atualizarTrajetoria();
            case 5 -> atualizarTripulantes();
            default -> System.out.println("Opção inválida.");
        }
    }

    private void acessarCoordenadas() {
        String codigo = EntradaUtil.lerTexto(scanner, "Digite o código de acesso: ");
        System.out.println("Coordenadas: " + dadosMissao.acessarCoordenadas(codigo));
    }

    private void atualizarCombustivel() {
        double combustivel = EntradaUtil.lerDouble(scanner, "Novo nível de combustível (%): ");
        dadosMissao.setNivelCombustivel(combustivel);
        alertaService.verificarMissao(dadosMissao);
    }

    private void atualizarTrajetoria() {
        String trajetoria = EntradaUtil.lerTexto(scanner, "Nova trajetória: ");
        dadosMissao.setTrajetoria(trajetoria);
        System.out.println("Trajetória atualizada.");
    }

    private void atualizarTripulantes() {
        int tripulantes = EntradaUtil.lerInteiro(scanner, "Número de tripulantes: ");
        dadosMissao.setNumeroTripulantes(tripulantes);
        System.out.println("Número de tripulantes atualizado.");
    }

    private void simularAlertas() {
        System.out.println("\n--- Simulação de Alertas ---");
        verificarSensores();
        alertaService.verificarMissao(dadosMissao);
        moduloComunicacao.setTemperatura(92.0);
        System.out.println("[COMPONENTE] " + moduloComunicacao.diagnosticar());
    }

    private void exibirStatusCompleto() {
        System.out.println("\n--- Status Completo ---");
        System.out.println(dadosMissao.resumoPublico());
        System.out.println("\nSensores:");
        sensores.forEach(sensor -> System.out.println("- " + sensor.resumoLeitura()));
        System.out.println("\nPropulsores:");
        propulsores.forEach(propulsor -> System.out.println("- " + propulsor.resumo()));
        System.out.println("\nComponente espacial:");
        System.out.println("- " + moduloComunicacao.resumo());
        System.out.println("- Diagnóstico: " + moduloComunicacao.diagnosticar());
    }

    private void diagnosticarComponente() {
        System.out.println("\n--- Diagnóstico de Componente Espacial ---");
        moduloComunicacao.ligar();
        System.out.println(moduloComunicacao.resumo());
        System.out.println(moduloComunicacao.diagnosticar());
    }

    private void listarPropulsores() {
        for (int i = 0; i < propulsores.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, propulsores.get(i).resumo());
        }
    }

    private List<SensorBase> criarSensores() {
        List<SensorBase> lista = new ArrayList<>();
        lista.add(new SensorTemperatura("TEMP-01"));
        lista.add(new SensorPressao("PRESS-01"));
        lista.add(new SensorRadiacao("RAD-01"));
        return lista;
    }

    private List<SistemaPropulsao> criarPropulsores() {
        List<SistemaPropulsao> lista = new ArrayList<>();
        lista.add(new PropulsaoQuimica("PQ-01", "Motor Químico Vulcan", 950.0, "Hidrogênio/Oxigênio", 0.92));
        lista.add(new PropulsaoEletrica("PE-01", "Propulsor Iônico Aurora", 140.0, 85.0, 1.35));
        return lista;
    }
}
