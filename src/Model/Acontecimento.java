package Model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

public abstract class Acontecimento implements Comparator<Acontecimento>, Serializable {
    private int inicio;
    private String equipa; //Equipa equipa?

    public Acontecimento() {
        this.inicio = 0;
        this.equipa = "n/a";
    }

    public Acontecimento(int inicio, String equipa) {
        this.inicio = 0;
        this.equipa = equipa;
    }

    public int getInicio() {
        return inicio;
    }

    public String getEquipa() {
        return equipa;
    }

    public int compare(Acontecimento o1, Acontecimento o2) {
        return o1.getInicio() - o2.getInicio();
    }
}
