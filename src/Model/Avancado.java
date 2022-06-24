package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Avancado extends Jogador implements Serializable {
    private int colocacaoBola;

    public Avancado() {
        super();
        this.colocacaoBola = 0;
    }

    public Avancado(Avancado avc) {
        super(avc);
        setHabilidade(getHabilidade());
        this.colocacaoBola = avc.getColocacaoBola();
    }
    public Avancado(String nomeJ,int numeroJ, int velocidadeJ, int resistenciaJ, int destrezaJ, int impulsao, int jogocabeca, int capacidadePasse, int remate, int numgolos, int colocacaoBola, List<String> hist) {
        super(nomeJ,numeroJ, velocidadeJ, resistenciaJ, destrezaJ, impulsao, jogocabeca, capacidadePasse, remate, numgolos, new ArrayList<>(hist));
        setHabilidade(getHabilidade());
        this.colocacaoBola = colocacaoBola;
    }

    public int getColocacaoBola() {
        return this.colocacaoBola;
    }

    public void setColocacaoBola(int colocacaoBola) {
        this.colocacaoBola = colocacaoBola;
    }

    public double getHabilidade() {
        return this.getVelocidadeJ() * 0.25 +
                this.colocacaoBola * 0.2 +
                this.getResistenciaJ() * 0.05 +
                this.getDestrezaJ() * 0.1 +
                this.getImpulsao() * 0.05 +
                this.getJogoCabeca() * 0.15 +
                this.getRemate() * 0.15 +
                this.getCapacidadePasse() * 0.05;
    }
    public Avancado clone() {
        return new Avancado(this);
    }
    public static Avancado parse(String input){
        String[] campos = input.split(",");
        return new Avancado(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                50,
                0,
                Integer.parseInt(campos[8]),
                new ArrayList<>());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Nome: ");
        sb.append(this.getNome());
        sb.append("\nVelocidade: ");
        sb.append(this.getVelocidadeJ());
        sb.append("\nResistencia: ");
        sb.append(this.getResistenciaJ());
        sb.append("\nDestreza: ");
        sb.append(this.getDestrezaJ());
        sb.append("\nImpulsao: ");
        sb.append(this.getImpulsao());
        sb.append("\nJogocabeca: ");
        sb.append(this.getJogoCabeca());
        sb.append("\nRemate: ");
        sb.append(this.getRemate());
        sb.append("\nCapacidade de Passe: ");
        sb.append(this.getCapacidadePasse());
        sb.append("\nHabilidade: ");
        sb.append(this.getHabilidade());
        sb.append("\nNumero de golos: ");
        sb.append(this.getNumGolos());
        sb.append("\nColocacao de Bola: ");
        sb.append(this.colocacaoBola);

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Avancado avc = (Avancado) o;
        return super.equals(avc) && this.colocacaoBola == avc.getColocacaoBola();
    }

    public void setCaracteristica(String caracteristica, int valor) {

    }
}