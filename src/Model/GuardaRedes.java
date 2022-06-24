package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuardaRedes extends Jogador implements Serializable
{
    private int altura;
    private int  elasticidade;
    
    public GuardaRedes(GuardaRedes g){
        super(g);
        setHabilidade(getHabilidade());
        this.elasticidade=g.getElasticidade();
        this.altura=g.getAltura();
    }

    public GuardaRedes(String nome, int numeroJ,int velocidade, int resistencia, int destreza, int impulsao, int jogocabeca, int capacidadePasse, int remate, int numgolos, int altura, int elasticidade, List<String> hist) {
        super(nome,numeroJ,velocidade,resistencia,destreza,impulsao,jogocabeca,capacidadePasse,remate,numgolos, new ArrayList<>(hist));
        setHabilidade(getHabilidade());
        this.altura = altura;
        this.elasticidade = elasticidade;
    }
    public static GuardaRedes parse(String input){
        String[] campos = input.split(",");
        return new GuardaRedes(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                50,
                0,
                Integer.parseInt(campos[8]),
                Integer.parseInt(campos[9]),
                new ArrayList<>());
    }
    public int getElasticidade(){
        return this.elasticidade;
    }

    public int getAltura(){
    return this.altura;
}

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setElasticidade(int elasticidade) {
        this.elasticidade = elasticidade;
    }

    //NOTA: falta alterar esta e as outras habilidades
    //penso q a soma das percentagens tem q dar 1
    public double getHabilidade() {
        return this.getVelocidadeJ() * 0.05
                + this.getResistenciaJ() * 0.05
                + this.getDestrezaJ() * 0.15
                + this.getImpulsao() * 0.15
                + this.getJogoCabeca() * 0.05
                + this.getRemate() * 0.05
                + this.getCapacidadePasse() * 0.05
                + this.altura * 0.2
                + this.elasticidade * 0.25;
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
        sb.append("\nAltura: ");
        sb.append(this.altura);
        sb.append("\nElasticidade: ");
        sb.append(this.elasticidade);

        return sb.toString();
    }

    public GuardaRedes clone(){
        return new GuardaRedes(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuardaRedes gr = (GuardaRedes) o;
        return super.equals(gr) && this.altura == gr.getAltura() && this.elasticidade == gr.getElasticidade();
    }
}
