package Model;

import java.io.Serializable;

public class Substituicao extends Acontecimento implements Serializable {
    private int jogSai;
    private int jogEntra;
    private boolean aconteceu;

    public Substituicao(int inicio, String equipa, int jogSai, int jogEntra, boolean aconteceu) {
        super(inicio,equipa);
        this.jogSai = jogSai;
        this.jogEntra = jogEntra;
    }

    public int getJogSai() {
        return jogSai;
    }

    public int getJogEntra() {
        return jogEntra;
    }

    public boolean isAconteceu() {
        return aconteceu;
    }

    public int compare(Substituicao o1, Substituicao o2) {
        return o1.getInicio() - o2.getInicio();
    }
}
