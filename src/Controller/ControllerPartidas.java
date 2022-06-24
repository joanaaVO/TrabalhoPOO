package Controller;

import Exceptions.EquipaNaoExistente;
import Exceptions.PartidaNaoExiste;
import Model.*;
import View.Menu;
import View.MenuPartida;

import javax.naming.ldap.Control;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerPartidas {
    private Desporto desporto;
    private List<Partida> partidas;
    private static Scanner sc = new Scanner(System.in);
    private static MenuPartida partidasMenu = new MenuPartida("Nome Jogo");

    public ControllerPartidas(Desporto desp) {
        this.desporto = desp;
        this.partidas = new ArrayList<>();
    }

    public void run() {
        List<String> opts = Arrays.asList(
                "Menu Partida",          //1
                "Listar Partidas",       //2
                "Adicionar Partida");    //3

        partidasMenu.setOptions(opts);

        partidasMenu.setPreConditions(1, () -> !this.desporto.getPartidas().isEmpty());
        partidasMenu.setPreConditions(2, () -> !this.desporto.getPartidas().isEmpty());
        partidasMenu.setOptions(opts);

        partidasMenu.setHandlers(1, ()->gestaoPartida(partidasMenu));
        partidasMenu.setHandlers(2, ()->showPartidas(partidasMenu));
        partidasMenu.setHandlers(3, ()->criaPartida(partidasMenu));

        partidasMenu.run();
    }

    public void gestaoPartida(Menu partidasMenu) {
        ControllerPartida cPartida = new ControllerPartida(this.desporto);
        Partida partida;
        try {
            partida = seleciona(partidasMenu);
            cPartida.setPartida(partida);
            cPartida.run();
        }
        catch(InputMismatchException | PartidaNaoExiste e) {
            partidasMenu.message("Partida " + e.getMessage() + " não existe, tente outra vez.");
        }
    }

    /* Serve como auxiliar para selecionar a Partida a ser usada no Menu Partida */
    private Partida seleciona(Menu partidasMenu) throws InputMismatchException, PartidaNaoExiste {
        Partida partida;
        int scPartida;
        partidasMenu.message("Insira o número do Jogo. Se tiver dúvidas liste as Partidas.");
        scPartida = sc.nextInt();
        return this.desporto.getPartidaByNum(scPartida);
    }

    public void showPartidas(Menu partidasMenu) {
        List<String> ls = new ArrayList<>();
        for (Partida pt : this.desporto.getPartidas())
            ls.add(pt.toString());

        partidasMenu.printList(ls);
    }

    public void criaPartida(Menu partidasMenu) {
        Partida newPartida;
        try {
            String visitante;
            String visitado;
            int duracao = 90;
            int partes = 2;
            String unparsedDate;
            LocalDate date;
            partidasMenu.message("Insira o nome do clube visitante.");
            visitante = sc.nextLine();
            partidasMenu.message("Insira o nome do clube visitado.");
            visitado = sc.nextLine();

            Equipa eqVisitado = this.desporto.getEquipa(visitado);
            Equipa eqVisitante = this.desporto.getEquipa(visitante);

            partidasMenu.message("Insira a data no modo \"ano-mes-dia\".");
            unparsedDate = sc.nextLine();
            date = LocalDate.parse(unparsedDate);


            //TODO adicionar o modelo tático e substituições
            newPartida = new Partida(eqVisitado, eqVisitante, eqVisitado.getHabilidadeTotal(),
                    eqVisitante.getHabilidadeTotal(), duracao, partes, date,
                    new ModeloTatico1433(eqVisitado), new ModeloTatico1433(eqVisitado),
                    new HashMap<Integer,Integer>(), new HashMap<Integer,Integer>());
            this.desporto.addPartida(newPartida);
        }
        catch (DateTimeParseException | EquipaNaoExistente  e) {
            partidasMenu.message("Verifique que os laterais não estão ser inseridos como centrais!");
        }
    }

    public void setPartidas(List<Partida> partidas) {
        if(partidas == null) this.partidas = partidas;
        else
            this.partidas = partidas.stream().map(Partida::clone).collect(Collectors.toList());
    }

}
