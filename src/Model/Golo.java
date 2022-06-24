package Model;

import java.io.Serializable;
import java.util.Comparator;

public class Golo extends Acontecimento implements Serializable {
    private boolean golo; //se marca golo ou não
    //não sei se isto faz sentido...porque se este acontecimento acontece obviamente é true

    public Golo(int inicio, String equipa, boolean golo) {
        super(inicio,equipa);
        this.golo = golo;
    }

    public int compare(Golo o1, Golo o2) {
        return o1.getInicio() - o2.getInicio();
    }

    public boolean isGolo() {
        return golo;
    }
}
