package Controller;

import Model.*;
import View.Menu;

import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ControllerJogador {
    private Desporto desporto;
    Jogador jogador;
    private static Scanner sc = new Scanner(System.in);
    private static Menu jogadorMenu = new Menu("Menu Jogador");


    public ControllerJogador(Desporto desporto) {
        this.desporto = desporto;
        this.jogador = null;
    }

    public void run () {
        List<String> opts = Arrays.asList(
                "Informações gerais",       //1
                "Alterar Características"   //2
        );

        jogadorMenu.setOptions(opts);

        jogadorMenu.setHandlers(1, ()-> showJogador());
        jogadorMenu.setHandlers(2, () -> alterarCaracteristicas());

        jogadorMenu.run();
        this.desporto.setJogador(jogador);
    }

    public void alterarCaracteristicas() {
        String caracteristica;
        int valor;
        System.out.println("Insira a caracteristica a alterar: ");
        caracteristica = sc.nextLine();
        if(!jogador.existeCaracteristica(caracteristica)) {
            System.out.println("Característica não existente!");
            this.alterarCaracteristicas();
        }
        else {
            System.out.println("Insira o novo valor: ");
            valor = sc.nextInt();
            this.jogador.setCaracteristica(caracteristica,valor);
        }
    }

    public void showJogador() {
        jogadorMenu.message(this.jogador.toString());
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

}
