import Controller.MainController;
import Exceptions.EquipaNaoExistente;
import Exceptions.JogadorNaoExistente;
import Exceptions.NumeroJaExistente;
import Model.Desporto;
import Model.SavedDesporto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private Desporto data;

    public static void main(String[] args) {
        SavedDesporto infoFutebol = new SavedDesporto();
        //try {
        //    Desporto futebol = infoFutebol.parser("logsV2.txt");
        //    MainController cMain = new MainController(futebol);
        //    cMain.run();
        //}
        //catch (EquipaNaoExistente | IOException | JogadorNaoExistente | NumeroJaExistente e) {
        //}
        Desporto futebol = new Desporto();
        MainController cMain = new MainController(futebol);
        cMain.run();



    }
}
