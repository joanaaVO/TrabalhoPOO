package Controller;

import Exceptions.EquipaNaoExistente;
import Exceptions.JogadorNaoExistente;
import Exceptions.NumeroJaExistente;
import Model.Desporto;
import Exceptions.InsercaoLateralImpossivel;
import View.Menu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Model.SavedDesporto;

import java.util.Arrays;

import java.util.Scanner;


public class MainController {
    private Desporto desporto;
    private static Scanner sc = new Scanner(System.in);
    private static Menu mainMenu = new Menu("Main menu");
    private static final String fileDefault = "logsV2.txt";

    public MainController() {
        this.desporto = new Desporto();
        sc = new Scanner(System.in);
    }

    // Não sei se vale a pena manter encapsulamento
    public MainController(Desporto desp) {
        this.desporto = desp.clone();
    }

    public void run() {
        List<String> opts = Arrays.asList(
                "Novo Jogo",          //1
                "Menu Equipas",       //2
                "Menu Jogadores",     //3
                "Ler Ficheiros",      //4
                "Gravar Dados",       //5
                "Carregar Dados");    //6
        mainMenu.setOptions(opts);

        mainMenu.setHandlers(1, () -> novoJogo());
        mainMenu.setHandlers(2, () -> gestaoEquipas());
        mainMenu.setHandlers(3, () -> gestaoJogadores());
        mainMenu.setHandlers(4, () -> readFiles());
        mainMenu.setHandlers(5, () -> saveData());
        mainMenu.setHandlers(6, () -> loadData());
        mainMenu.run();
    }

    public void novoJogo() {
        ControllerPartidas cPartidas = new ControllerPartidas(this.desporto);
        cPartidas.run();

    }

    public void gestaoEquipas() {
        ControllerEquipas cEquipas = new ControllerEquipas(this.desporto);
        cEquipas.run();
    }

    public void gestaoJogadores() {
        ControllerJogadores cJogadores = new ControllerJogadores(this.desporto);
        cJogadores.run();
    }

    public void readFiles() {
        SavedDesporto saved = new SavedDesporto();
        String file;
        mainMenu.message("Insira um ficheiro (ENTER para usar o default): ");
        try {
            file = sc.nextLine();
            if (file.isEmpty()) file = fileDefault;
            this.desporto = saved.parser(file);
        }
        catch (IOException | NumeroJaExistente | JogadorNaoExistente | EquipaNaoExistente | InsercaoLateralImpossivel e) {
            mainMenu.message(e.getMessage());
        }
    }


    public void saveData() {
        SavedDesporto saved = new SavedDesporto();
        String file;
        mainMenu.message("Insira um ficheiro (ENTER para usar o default): ");
        try{
            file = sc.nextLine();
            if (file.isEmpty())
                saved.saveData(fileDefault, this.desporto);
            else
                saved.saveData(file, this.desporto);
        }
        catch (IOException e) {
            mainMenu.message("A gravação falhou. " + e.getMessage());
        }

    }

    public void loadData() {
        SavedDesporto saved = new SavedDesporto();
        String file;
        mainMenu.message("Insira um ficheiro (ENTER para usar o default): ");
        try {
            file = sc.nextLine();
            if(file.isEmpty())
                file = fileDefault;
            this.desporto = saved.loadData(file);

        }
        catch (IOException | ClassNotFoundException e) {
            mainMenu.message(e.getMessage());
        }
    }
}
