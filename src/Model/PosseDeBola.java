package Model;

import java.io.Serializable;

public class PosseDeBola extends Acontecimento implements Serializable {
    private boolean posseDeBola; //indica se a equipa passa a ter posse de bola (com base em habilidade/probabilidade)

    public PosseDeBola(int inicio, String equipa, boolean posseDeBola) {
        super(inicio,equipa);
        this.posseDeBola = posseDeBola;
    }

    public int compare(PosseDeBola o1,PosseDeBola o2) {
        return o1.getInicio() - o2.getInicio();
    }

    public boolean isPosseDeBola() {
        return posseDeBola;
    }
}
