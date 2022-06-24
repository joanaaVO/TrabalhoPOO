package Controller;

import Exceptions.EquipaNaoExistente;
import Exceptions.JogadorNaoExistente;
import Exceptions.JogadorSemEquipaNaoExistente;
import Exceptions.NumeroJaExistente;
import Model.Desporto;
import Model.Equipa;
import Model.Jogador;
import View.Menu;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ControllerEquipa {
    Desporto desporto;
    Equipa equipa;
    private static Scanner sc = new Scanner(System.in);
    private static Menu equipaMenu = new Menu("Menu Equipa");

    public ControllerEquipa(Desporto desporto) {
        this.desporto = desporto;
        this.equipa = null;
    }

    public void run () {
        List<String> opts = Arrays.asList(
                "Informação geral",           //1
                "Adicionar Jogador",          //2
                "Consultar Jogador",          //3
                "Escolher titulares",         //4
                "Escolher suplentes"          //5
        );

        equipaMenu.setOptions(opts);

        equipaMenu.setPreConditions(2, () -> !this.desporto.getJogSemEquipa().isEmpty());
        equipaMenu.setPreConditions(3, () -> !this.equipa.getJogadores().isEmpty());
        equipaMenu.setPreConditions(4, () -> !this.equipa.getJogadores().isEmpty());
        equipaMenu.setPreConditions(5, () -> !this.equipa.getJogadores().isEmpty());

        equipaMenu.setHandlers(1, () -> equipaShow());
        equipaMenu.setHandlers(2, () -> addJogador());
        equipaMenu.setHandlers(3, () -> gestaoJogador());
        equipaMenu.setHandlers(4, () -> escolherTitulares());
        equipaMenu.setHandlers(5, () -> escolherSuplentes());

        equipaMenu.run();
        this.desporto.setEquipa(equipa);
    }

    public Jogador seleciona() throws EquipaNaoExistente, JogadorNaoExistente {
        Integer scJogador;
        equipaMenu.message("Insira o numero do jogador a consultar: ");
        scJogador = sc.nextInt();
        if (jogadorExisteNaEquipa(this.equipa.getNome(),scJogador)) return this.desporto.getJogadorByNum(equipa.getNome(), scJogador);
        else {
            equipaMenu.message("Jogador não existente! Tente outra vez.");
            this.seleciona();
        }
        return null;
   }

   public void addJogador() {
       String scJogador;
       equipaMenu.message("Insira o nome do jogador a adicionar: ");
       scJogador = sc.nextLine();
       try {
           Jogador jog = this.desporto.getJogadorSemEquipa(scJogador);
           this.equipa.addJogador(jog);
       }
       catch(JogadorSemEquipaNaoExistente | NumeroJaExistente e) {
            equipaMenu.message("Não existe o jogador para adicionar");
       }
   }

    public void gestaoJogador() {
        try {
            Jogador selec = seleciona();
            ControllerJogador cJogador = new ControllerJogador(this.desporto);
            cJogador.setJogador(selec);
            cJogador.run();
        }
        catch(EquipaNaoExistente | JogadorNaoExistente e) {
            equipaMenu.message(e.getMessage());
        }
    }

    public void equipaShow() {
        equipaMenu.message(this.equipa.toString());
    }

    public void escolherTitulares() {
        int scTitular;
        for(int i=0; i< this.desporto.getNumjogequipa(); i++) {
            equipaMenu.message("Numero: ");
            scTitular = sc.nextInt();
            try{
                this.equipa.addTitular(scTitular);
            }
            catch (JogadorNaoExistente e) {
                equipaMenu.message("Jogador não existente.");
            }
        }
    }

    public void escolherSuplentes() {
        int scSuplente;
        for(int i=0; i<this.desporto.getNumsuplentes(); i++) {
            equipaMenu.message("Numero: ");
            scSuplente = sc.nextInt();
            try{
                this.equipa.addSuplente(scSuplente);
            }
            catch (JogadorNaoExistente e) {
                equipaMenu.message("Jogador não existente.");
            }
        }
    }

    public void setEquipa(Equipa selecionada) {
        this.equipa = selecionada;
    }

    public boolean jogadorExisteNaEquipa(String equipa, int num) throws EquipaNaoExistente {
        return this.desporto.jogadorExisteNaEquipa(equipa,num);
    }
}
