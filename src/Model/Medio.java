package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Medio extends Jogador implements Serializable {
    private int  recuperacaoBola;
    
    public Medio() {
        super();
    }

    public Medio(String nome, int numeroJ,int  velocidade, int  resistencia, int  destreza, int  impulsao, int  jogodecabeca, int  remate, int  passe, int numgolos, int  recuperacaoBola, List<String> hist) {
        super(nome,numeroJ,velocidade,resistencia,destreza,impulsao,jogodecabeca,passe,remate,numgolos, new ArrayList<>(hist));
        setHabilidade(getHabilidade());
        this.recuperacaoBola = recuperacaoBola;
    }

    public Medio(Medio m) {
        super(m);
        setHabilidade(getHabilidade());
        this.recuperacaoBola = m.getRecuperacaoBola();
       
    }
    public int  getRecuperacaoBola() {
        return this.recuperacaoBola;
    }

    public void setRecuperacaoBola(int recuperacaoBola) {
        this.recuperacaoBola = recuperacaoBola;
    }

    public Medio clone() {
        return new Medio(this);
    }

    public double getHabilidade() {
        return this.getVelocidadeJ()*0.2
                +this.getResistenciaJ()*0.2
                +this.getDestrezaJ()*0.05
                +this.getImpulsao()*0.05
                +this.getJogoCabeca()*0.05
                +this.getRemate()*0.05
                +this.getCapacidadePasse()*0.2
                +this.recuperacaoBola*0.2;
    }
    public static Medio parse(String input){
        String[] campos = input.split(",");
        return new Medio(campos[0], Integer.parseInt(campos[1]),
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
        sb.append("\nRecuperacao de Bola: ");
        sb.append(this.recuperacaoBola);

        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medio m = (Medio) o;
        return super.equals(m) && this.recuperacaoBola == m.getRecuperacaoBola();
    }
}
