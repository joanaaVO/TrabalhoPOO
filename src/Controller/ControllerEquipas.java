package Controller;

import Exceptions.EquipaNaoExistente;
import Exceptions.JogadorNaoExistente;
import Exceptions.NumeroJaExistente;
import Model.Desporto;
import Model.Equipa;
import Model.Jogador;
import View.Menu;

import java.util.*;

public class ControllerEquipas {
    private Desporto desporto;
    private static Scanner sc = new Scanner(System.in);
    private static Menu equipasMenu = new Menu("Menu Equipas");

    public ControllerEquipas (Desporto desp) {
        // Não posso fazer clone porque as mudanças devem ser mantidas durante a execução
        this.desporto = desp;
    }

    public void run () {
        List<String> opts = Arrays.asList(
                "Criar equipa",                      //1
                "Consultar equipa",                  //2
                "Listar Equipas",                    //3
                "Adicionar Jogador a uma Equipa",    //4
                "Transferência"                      //5
        );

        equipasMenu.setOptions(opts);

        equipasMenu.setPreConditions(2, () -> !this.desporto.getEquipas().isEmpty());
        equipasMenu.setPreConditions(3, () -> !this.desporto.getEquipas().isEmpty());
        equipasMenu.setPreConditions(4, () -> !this.desporto.getEquipas().isEmpty() && !this.desporto.getJogSemEquipa().isEmpty());
        equipasMenu.setPreConditions(5, () -> !this.desporto.getEquipas().isEmpty());// && !this.desporto.getJogComEquipa().isEmpty());

        equipasMenu.setHandlers(1, () -> criaEquipa());
        equipasMenu.setHandlers(2, () -> gestaoEquipa());
        equipasMenu.setHandlers(3, () -> showEquipas());
        equipasMenu.setHandlers(4, () -> addJogadorToEquipa());
        equipasMenu.setHandlers(5, () -> transferencias());

        equipasMenu.run();

        //pré-condições
    }

    public void criaEquipa() {
        String nome;
        nome = sc.nextLine();
        if(equipaExiste(nome)) equipasMenu.message("Equipa já existente!");
        else {
            Equipa equipa = new Equipa(nome);
            //provavelmente dar a opção de já adicionar jogadores,definir titulares e suplentes
            this.desporto.addEquipa(nome,equipa);
        }
    }

    public Equipa seleciona() throws EquipaNaoExistente {
        String scEquipa;
        equipasMenu.message("Insira o nome da equipa:");
        scEquipa = sc.nextLine();
        if (equipaExiste(scEquipa)) return this.desporto.getEquipa(scEquipa);
        else {
            throw new EquipaNaoExistente(scEquipa);
        }
    }

    public void gestaoEquipa() {
        try {
            Equipa selec = seleciona();
            ControllerEquipa cEquipa = new ControllerEquipa(this.desporto);
            cEquipa.setEquipa(selec);
            cEquipa.run();
        }
        catch (EquipaNaoExistente e) {
            equipasMenu.message("Equipa " + e.getMessage() + "não existente.");
            equipasMenu.message("Tente outra vez.");
        }
    }

    public void showEquipas() {
        List<String> ls = new ArrayList<>();
        for (Equipa eq : desporto.getEquipas().values())
            ls.add(eq.toString());

        equipasMenu.printList(ls);
    }

    public void addJogadorToEquipa() {
        String scJogador;
        String equipa;

        try {
            equipasMenu.message("Nome do Jogador: ");
            scJogador = sc.nextLine();
            equipasMenu.message("Nome da Equipa: ");
            equipa = sc.nextLine();

            this.desporto.addJogadorToEquipa(equipa, desporto.getJogSemEquipa().get(scJogador));
        }
        catch (EquipaNaoExistente | NumeroJaExistente e) {
            equipasMenu.message(e.getMessage());
        }
    }

    public void transferencias() {
        String equipa1;
        String equipa2;
        int num;

        try {
            equipasMenu.message("Equipa de onde transferir: ");
            equipa1 = sc.nextLine();
            equipasMenu.message("Equipa para onde transferir: ");
            equipa2 = sc.nextLine();
            equipasMenu.message("Numero do jogador a transferir: ");
            num = sc.nextInt();
            Jogador jogador = this.desporto.getJogadorByNum(equipa1, num);
            this.desporto.substituicaoEquipas(jogador, equipa1, equipa2);
        }
        catch (EquipaNaoExistente | JogadorNaoExistente | NumeroJaExistente e) {
            equipasMenu.message(e.getMessage());
        }
    }

    private boolean equipaExiste(String nome) {
        return this.desporto.equipaExiste(nome);
    }
}
