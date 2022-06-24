package Controller;

import Exceptions.JogadorNaoExistente;
import Model.*;
import View.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ControllerJogadores {
    private Desporto desporto;
    private static Scanner sc = new Scanner(System.in);
    public static Menu playersMenu = new Menu("Menu Jogadores");

    public ControllerJogadores (Desporto desporto) {
        this.desporto = desporto;
    }

    public void run () {
        List<String> opts = Arrays.asList(
                "Criar Jogador",                 //1
                "Consultar Jogador",             //2
                "Listar Jogadores",              //3
                "Listar Jogadores sem Equipa"    //4
        );

        playersMenu.setOptions(opts);

        playersMenu.setPreConditions(2, () -> !this.desporto.getJogComEquipa().isEmpty() || !this.desporto.getJogSemEquipa().isEmpty());
        playersMenu.setPreConditions(3, () -> !this.desporto.getJogComEquipa().isEmpty() || !this.desporto.getJogSemEquipa().isEmpty());
        playersMenu.setPreConditions(4, () -> !this.desporto.getJogSemEquipa().isEmpty());

        playersMenu.setHandlers(1, () -> criarJogador());
        playersMenu.setHandlers(2, () -> gestaoJogador());
        playersMenu.setHandlers(3, () -> showJogadores());
        playersMenu.setHandlers(4, () -> showJogadoresSemEquipa());

        playersMenu.run();
    }

    public void criarJogador() {
        String nome;
        playersMenu.message("Nome: ");
        nome = sc.nextLine();
        if(jogadorExiste(nome)) {
            playersMenu.message("Jogador já existente!");
            this.criarJogador();
        }
        else {
            playersMenu.message("Posicao: ");
            String posicao = sc.nextLine();
            while(posicao.compareTo("Avancado") != 0 && posicao.compareTo("Defesa") != 0 && posicao.compareTo("Guarda-Redes") != 0
                    && posicao.compareTo("Lateral") != 0 && posicao.compareTo("Medio") != 0) {
                System.out.println("Posicao Invalida! Tente novamente.");
                posicao = sc.nextLine();
            }
            playersMenu.message("Numero: ");
            int numero = sc.nextInt();
            playersMenu.message("Velocidade: ");
            int velocidade = sc.nextInt();
            playersMenu.message("Resistencia: ");
            int resistencia = sc.nextInt();
            playersMenu.message("Destreza: ");
            int destreza = sc.nextInt();
            playersMenu.message("Impulsao: ");
            int impulsao = sc.nextInt();
            playersMenu.message("Jogo de Cabeca: ");
            int jogodecabeca = sc.nextInt();
            playersMenu.message("Remate: ");
            int remate = sc.nextInt();
            playersMenu.message("Capacidade de Passe: ");
            int capacidadepasse = sc.nextInt();
            if(posicao.compareTo("Avancado") == 0) {
                playersMenu.message("Colocacao Bola: ");
                int colocacao = sc.nextInt();
                Jogador jogador = new Avancado(nome,numero,velocidade,resistencia,destreza,impulsao,jogodecabeca,capacidadepasse,remate,0,colocacao, new ArrayList<>());
                this.desporto.addJogador(nome,jogador);
            }
            else if(posicao.compareTo("Defesa") == 0) {
                playersMenu.message("Capacidade de Defesa: ");
                int defesa = sc.nextInt();
                Jogador jogador = new Defesa(nome,numero,velocidade,resistencia,destreza,impulsao,jogodecabeca,remate,capacidadepasse,0,defesa, new ArrayList<>());
                this.desporto.addJogador(nome,jogador);
            }
            else if(posicao.compareTo("Guarda-Redes") == 0) {
                playersMenu.message("Altura: ");
                int altura = sc.nextInt();
                playersMenu.message("Elasticidade: ");
                int elasticidade = sc.nextInt();
                Jogador jogador = new GuardaRedes(nome,numero,velocidade,resistencia,destreza,impulsao,jogodecabeca,capacidadepasse,remate,0,altura,elasticidade, new ArrayList<>());
                this.desporto.addJogador(nome,jogador);
            }
            else if(posicao.compareTo("Lateral") == 0) {
                //não sei se faz sentido criar um lateral sem mais nenhuma posicao
            }
            else if(posicao.compareTo("Medio") == 0) {
                playersMenu.message("Recuperacao de Bola: ");
                int recuperacao = sc.nextInt();
                Jogador jogador = new Medio(nome,numero,velocidade,resistencia,destreza,impulsao,jogodecabeca,remate,capacidadepasse,0,recuperacao, new ArrayList<>());
                this.desporto.addJogador(nome,jogador);
            }
        }
    }

    public Jogador seleciona() throws JogadorNaoExistente {
        String scJogador;
        scJogador = sc.next();
        if (jogadorExiste(scJogador)) return this.desporto.getJogadorbyName(scJogador);
        else {
            playersMenu.message("Equipa não existente! Tente outra vez.");
            this.seleciona();
        }
        return null;
    }

    public void gestaoJogador() {
        try {
            Jogador selec = seleciona();
            ControllerJogador cJogador = new ControllerJogador(this.desporto);
            cJogador.setJogador(selec);
            cJogador.run();
        }
        catch(JogadorNaoExistente e) {
            playersMenu.message("Jogador não existente.");
        }

    }

    public void showJogadores() {
        List<String> ls = new ArrayList<>();
        for (Jogador j : this.desporto.getJogComEquipa().values())
            ls.add(j.toString());
        for(Jogador j : this.desporto.getJogSemEquipa().values())
            ls.add(j.toString());

        playersMenu.printList(ls);
    }

    public void showJogadoresSemEquipa() {
        List<String> ls = new ArrayList<>();
        for(Jogador j : this.desporto.getJogSemEquipa().values())
            ls.add(j.toString());

        playersMenu.printList(ls);
    }

    private boolean jogadorExiste(String nome) {
        return this.desporto.jogadorExiste(nome);
    }

}
