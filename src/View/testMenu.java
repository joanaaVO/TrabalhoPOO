package View;

import Controller.MainController;
import Model.Desporto;
import Model.SavedDesporto;

public class testMenu {
    public static void main(String[] args) {
        MainController mc = new MainController(new Desporto());
        mc.run();
    }
}
