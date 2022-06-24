package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lateral extends Jogador implements Serializable {
    private int  cruzamento;

    public Lateral(String nomeJ, int numeroJ, int  velocidadeJ, int  resistenciaJ, int  destrezaJ, int  impulsao, int jogocabeca, int  capacidadePasse, int  remate, int numgolos, int  cruzamento, List<String> hist) {
        super(nomeJ,numeroJ,velocidadeJ, resistenciaJ, destrezaJ, impulsao, jogocabeca, capacidadePasse, remate, numgolos, new ArrayList<>(hist));
        setHabilidade(getHabilidade());
        this.cruzamento = cruzamento;
    }

    public Lateral(Lateral lt) {
        super(lt);
        setHabilidade(getHabilidade());
        this.cruzamento = lt.getCruzamento();
    }

    public int  getCruzamento() {
        return this.cruzamento;
    }

    public void setCruzamento(int cruzamento) {
        this.cruzamento = cruzamento;
    }

    public double getHabilidade() {
        return this.getVelocidadeJ() * 0.2 +
                this.cruzamento * 0.2 +
                this.getResistenciaJ() * 0.2 +
                this.getDestrezaJ() * 0.05 +
                this.getImpulsao() * 0.05 +
                this.getJogoCabeca() * 0.05 +
                this.getRemate() * 0.1 +
                this.getCapacidadePasse() * 0.15;
    }
    public static Lateral parse(String input){
        String[] campos = input.split(",");
        return new Lateral(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                Integer.parseInt(campos[8]),
                0,
                Integer.parseInt(campos[9]),
                new ArrayList<>());
    }
    public Lateral clone() {
        return new Lateral(this);
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
        sb.append("\nCruzamento: ");
        sb.append(this.cruzamento);

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Lateral lt = (Lateral) o;
        return super.equals(lt) && this.cruzamento == lt.getCruzamento();
    }


}
