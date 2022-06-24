package View;

import java.util.List;

public class MenuPartida extends Menu{
    public MenuPartida(List<String> opts, String menuName) {
        super(opts, menuName);
    }

    public MenuPartida(String menuName) {
        super(menuName);
    }
    @Override
    public void printList(List<String> list) {
        int i = 1;
        for(String partida : list) {
            System.out.println(i + "-> " + partida + ";" );
            i++;
        }
    }
}
