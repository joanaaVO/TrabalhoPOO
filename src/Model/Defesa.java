package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Defesa extends Jogador implements Serializable {
    private int capacidadeDefesa;

    public Defesa(Defesa def){
        super(def);
        setHabilidade(def.getHabilidade());
        this.capacidadeDefesa = 0;
    }

    public Defesa(String nome, int numeroJ,int velocidade, int resistencia, int destreza,int impulsao, int jogodecabeca, int remate, int passe, int numgolos, int capacidadeDefesa, List<String> hist) {
        super(nome,numeroJ,velocidade,resistencia,destreza,impulsao,jogodecabeca,passe,remate,numgolos, new ArrayList<>(hist));
        setHabilidade(getHabilidade());
        this.capacidadeDefesa = capacidadeDefesa;
    }

    public int getCapacidadeDefesa() {
        return capacidadeDefesa;
    }

    public void setCapacidadeDefesa(int capacidadeDefesa) {
        this.capacidadeDefesa = capacidadeDefesa;
    }

    public double getHabilidade() {
        return  this.getVelocidadeJ() * 0.05
                +this.getResistenciaJ()*0.1
                +this.getDestrezaJ()*0.15
                +this.getImpulsao()*0.15
                +this.getJogoCabeca()*0.15
                +this.getRemate()*0.05
                +this.getCapacidadePasse()*0.1
                + this.capacidadeDefesa * 0.25;
    }
    public static Defesa parse(String input){
        String[] campos = input.split(",");
        return new Defesa(campos[0], Integer.parseInt(campos[1]),
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

    public Defesa clone(){
        return new Defesa(this);
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
        sb.append("\nCspacidade de Defesa: ");
        sb.append(this.capacidadeDefesa);

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Defesa def = (Defesa) o;
        return super.equals(def) && this.capacidadeDefesa == def.getCapacidadePasse();
    }
}
