package Controller;

import Exceptions.EquipaNaoExistente;
import Model.Desporto;
import Model.Partida;
import View.Menu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ControllerPartida {
    private Desporto desporto;
    private Partida partida;
    private static Scanner sc = new Scanner(System.in);
    private static Menu menuPartida = new Menu("Menu Partida");

    public ControllerPartida(Desporto desp) {
        this.desporto = desp;
        this.partida = null;
    }

    public void run() {
        List<String> opts = Arrays.asList(
                "Jogar partida",           //1
                "Editar partida"           //2
        );
        menuPartida.setOptions(opts);

        menuPartida.setHandlers(1, () -> iniciarPartida());
        menuPartida.setHandlers( 2, ()-> editarPartida());

        menuPartida.run();
    }
    //public void escolheEquipas(){
    //    view.Menu();
    //   Scanner s = new Scanner(System.in);
    //   int opcao = Scanner.nextInt();
    //     switch (opcao) {
    //         case 1:
    //             System.out.println("Escolha equipa visitante");
    //             this.run();
    //             break;

    //         case 2:
    //             System.out.println("Escolha equipa visitada");
    //             this.run();
    //             break;
    //         case 0:
    //             break;
    //         default:
    //             System.out.println("Opcao invalida");
    //             this.run();
    //             break;
    //     }

    //}

    public void iniciarPartida() {
        ControllerJogo cJogo = new ControllerJogo(this.desporto);
        cJogo.setPartida(this.partida);
        cJogo.run();
    }

    public void editarPartida() {
        Menu menuSetters = new Menu("Editar Partida");
        List<String> opts = Arrays.asList(
                "Alterar equipa visitada",     //1
                "Alterar equipa visitante",    //2
                "Alterar data");               //3
        menuSetters.setOptions(opts);
        menuSetters.setHandlers(1, ()->setVisitado());
        menuSetters.setHandlers(2, ()->setVisitante());
        menuSetters.setHandlers(3, ()->setData());
        menuSetters.run();
    }

    public void setPartida(Partida partida) {
        this.partida = partida.clone();
    }

    public void setVisitado() {
        String scVisitado;
        scVisitado = sc.nextLine();

        try {
            this.partida.setVisitado(this.desporto.getEquipa(scVisitado));
        }
        catch (EquipaNaoExistente e) {
            menuPartida.message("Equipa não existe.");
        }
    }

    public void setVisitante() {
        String scVisitante;
        scVisitante = sc.nextLine();

        try {
            this.partida.setVisitante(this.desporto.getEquipa(scVisitante));
        }
        catch (EquipaNaoExistente e) {
            menuPartida.message("Equipa não existe.");
        }
    }

    public void setData() {
        String scData;
        LocalDate data;
        scData = sc.nextLine();
        menuPartida.message("Indique a data no formato \"aa-mm-dd\".");
        try {
            data = LocalDate.parse(scData);
            this.partida.setData(data);
        }
        catch(DateTimeParseException e) {
            menuPartida.message("Input inválido.");
        }
    }
}
