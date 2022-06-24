package View;

import java.io.IOException;
import java.util.*;

public class Menu {
    public interface Handler {
        public void execute();
    }

    public interface PreCondition {
        // Se fizesse com predicado não sei com que tipo seria, mas supostamente era melhor
        public boolean validate();
    }

    private final static  Scanner sc = new Scanner(System.in);
    private List<String> options;
    private List<PreCondition> pre;
    private List<Handler> handlers;
    private String menuName;

    public Menu(List<String> options, String menuName) {
        this.options = new ArrayList<>(options);
        this.pre = new ArrayList<>();
        this.handlers = new ArrayList<>();
        for(String opt : this.options) {
            this.pre.add(() -> false);
            this.handlers.add (() -> System.out.println("\nOpção não implementada!"));
        }
        this.menuName = menuName;
    }

    public Menu(String menuName) {
        this.options = new ArrayList<>();
        this.pre = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.menuName = menuName;

    }

    public void setHandlers(int opt, Handler handler) {
        // Interfaces funcionais são imutáveis
        this.handlers.set(opt-1, handler);
    }

    public void addHandler(int opt, Handler handler) {
        this.handlers.add(opt-1, handler);
    }

    public void setPreConditions(int opt, PreCondition pre) {
        this.pre.set(opt-1, pre);
    }

    public void setOptions(List<String> options) {
        this.options = new ArrayList<>(options);
        for(String opt : this.options) {
            this.pre.add(() -> true);
            this.handlers.add (() -> System.out.println("\nOpção não implementada!"));
        }
    }

    public void run() {
        int opt;
        do {
            show();
            opt = readOpt();
            if (opt > 0 && !this.pre.get(opt - 1).validate())
                System.out.println("Opção não disponível!");
            else if (opt > 0)
                this.handlers.get(opt - 1).execute();

        } while (opt != 0);
    }

    public void show() {
        System.out.println("---------" + this.menuName + "---------");
        for (int i = 0; i < this.options.size(); i++) {
            System.out.print(i + 1 + ": ");
            System.out.println(this.pre.get(i).validate() ? this.options.get(i) : "----");
        }
    }

    public int readOpt()  {
        int r = 0;
        while(true) {
            try {
                r = sc.nextInt();
                break;
            }
            catch (InputMismatchException e) {
                System.out.println("Por favor insira outra vez a opção.");
                // Para ignorar o que foi lido
                sc.nextLine();
            }
        }

        return r;
    }

    public void message(String msg) {
        System.out.println(msg);
    }

    public void printList(List<String> list) {
        for (String st : list)
            System.out.println("* " + st + ";");

    }
}
