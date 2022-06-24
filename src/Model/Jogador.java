package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Jogador implements Serializable {
    private String nome;
    private int numeroJ;
    private int velocidadeJ;
    private int resistenciaJ;
    private int destrezaJ;
    private int impulsao;
    private int jogoCabeca;
    private int remate;
    private int capacidadePasse;
    private double habilidade;
    private int numgolos;
    private List<String> historico;

    public Jogador () {
        this.nome = "";
        this.numeroJ=0;
        this.velocidadeJ = 0;
        this.resistenciaJ = 0;
        this.destrezaJ = 0;
        this.impulsao = 0;
        this.jogoCabeca = 0;
        this.capacidadePasse = 0;
        this.remate = 0;
        this.habilidade = 0;
        this.numgolos = 0;
        this.historico = new ArrayList<>();
    }

    public Jogador(String nome,int numeroJ,int velocidadeJ,int resistenciaJ,int destrezaJ,int impulsao,int jogocabeca,int capacidadePasse,int remate,int numgolos, List<String> historico){
        this.nome=nome;
        this.numeroJ=numeroJ;
        this.velocidadeJ=velocidadeJ;
        this.resistenciaJ=resistenciaJ;
        this.destrezaJ=destrezaJ;
        this.impulsao=impulsao;
        this.jogoCabeca=jogocabeca;
        this.capacidadePasse=capacidadePasse;
        this.remate=remate;
        this.numgolos=numgolos;
        setHistorico(historico);
    }

    public Jogador(Jogador j){
        this.nome=j.getNome();
        this.numeroJ=j.getNumeroJ();
        this.velocidadeJ=j.getVelocidadeJ();
        this.resistenciaJ=j.getResistenciaJ();
        this.destrezaJ=j.getDestrezaJ();
        this.impulsao=j.getImpulsao();
        this.jogoCabeca=j.getJogoCabeca();
        this.capacidadePasse=j.getCapacidadePasse();
        this.remate=j.getRemate();
        this.numgolos=j.getNumGolos();
        setHistorico(j.getHistorico());
    }
    public String getNome(){
    return this.nome;
}
    public int getNumeroJ(){ return this.numeroJ;}
    public int getVelocidadeJ(){
    return this.velocidadeJ;
}
    public int getResistenciaJ(){
        return this.resistenciaJ;
    }
    public int getDestrezaJ(){
        return this.destrezaJ;
    }
    public int getImpulsao(){
        return this.impulsao;
    }
    public int getJogoCabeca(){
        return this.jogoCabeca;
    }
    public int getRemate(){
        return this.remate;
    }
    public int getCapacidadePasse(){
        return this.capacidadePasse;
    }

    public abstract double getHabilidade();

    public void setNumeroJ(int numeroJ) {
        this.numeroJ = numeroJ;
    }

    public void setVelocidadeJ(int velocidadeJ) {
        this.velocidadeJ = velocidadeJ;
    }

    public void setResistenciaJ(int resistenciaJ) {
        this.resistenciaJ = resistenciaJ;
    }

    public void setDestrezaJ(int destrezaJ) {
        this.destrezaJ = destrezaJ;
    }

    public void setImpulsao(int impulsao) {
        this.impulsao = impulsao;
    }

    public void setJogoCabeca(int jogoCabeca) {
        this.jogoCabeca = jogoCabeca;
    }

    public void setRemate(int remate) {
        this.remate = remate;
    }

    public void setCapacidadePasse(int capacidadePasse) {
        this.capacidadePasse = capacidadePasse;
    }

    public void setHabilidade(double habilidade) {
        this.habilidade = habilidade;
    }

    public void setHistorico(List<String> historico) {
        this.historico = new ArrayList<>(historico);
    }

    public List<String> getHistorico() {
        return new ArrayList<>(this.historico);
    }

    public int getNumGolos(){
        return this.numgolos;
    }

    public void setNumGolos(){
        this.numgolos=numgolos;
    }

    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("Nome:");
        sb.append(this.getNome());
        sb.append("\nVelocidade:");
        sb.append(this.getVelocidadeJ());
        sb.append("\nResistencia:");
        sb.append(this.getResistenciaJ());
        sb.append("\nDestreza:");
        sb.append(this.getDestrezaJ());
        sb.append("\nImpulsao:");
        sb.append(this.getImpulsao());
        sb.append("\nJogocabeca:");
        sb.append(this.getJogoCabeca());
        sb.append("\nRemate:");
        sb.append(this.getRemate());
        sb.append("\nCapacidade de Passe");
        sb.append(this.getCapacidadePasse());
        sb.append("\nHabilidade:");
        sb.append(this.getHabilidade());
        sb.append("\nNumero de golos");
        sb.append(this.getNumGolos());
        return sb.toString();
    }

    public abstract Jogador clone();

    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null || o.getClass() != this.getClass())return false;
        Jogador jog=(Jogador) o;
        return this.nome.equals(jog.getNome())
                && this.velocidadeJ==jog.getVelocidadeJ()
                && this.resistenciaJ==jog.getResistenciaJ()
                && this.destrezaJ==jog.getDestrezaJ()
                && this.impulsao==jog.getImpulsao()
                && this.jogoCabeca==jog.getJogoCabeca()
                && this.remate==jog.getRemate()
                && this.capacidadePasse==jog.getCapacidadePasse()
                && this.habilidade==jog.getHabilidade()
                && this.numgolos==jog.getNumGolos();
    }

    public boolean existeCaracteristica(String caracteristica) {
        if(caracteristica.equals("Velocidade") || caracteristica.equals("Resistencia") || caracteristica.equals("Destreza")
        || caracteristica.equals("Impulsao") || caracteristica.equals("Jogo de Cabeca") || caracteristica.equals("Remate")
        || caracteristica.equals("Capacidade de Passe")) return true;
        else return this instanceof Avancado && caracteristica.equals("Colocacao de Bola")
                || this instanceof Defesa && caracteristica.equals("Capacidade de Defesa")
                || this instanceof GuardaRedes && (caracteristica.equals("Altura") || caracteristica.equals("Elasticidade"))
                || this instanceof Lateral && caracteristica.equals("Cruzamento")
                || this instanceof Medio && caracteristica.equals("Recuperacao de Bola");
    }

    public void setCaracteristica(String caracteristica, int valor) {
        if(caracteristica.equals("Velocidade")) setVelocidadeJ(valor);
        else if(caracteristica.equals("Resistencia")) setResistenciaJ(valor);
        else if(caracteristica.equals("Destreza")) setDestrezaJ(valor);
        else if(caracteristica.equals("Impulsao")) setImpulsao(valor);
        else if(caracteristica.equals("Jogo de Cabeca")) setJogoCabeca(valor);
        else if(caracteristica.equals("Remate")) setRemate(valor);
        else if(caracteristica.equals("Capacidade de Passe")) setCapacidadePasse(valor);
        else if(this instanceof Avancado && caracteristica.equals("Colocacao de Bola")) ((Avancado) this).setColocacaoBola(valor);
        else if(this instanceof Defesa && caracteristica.equals("Capacidade de Defesa")) ((Defesa) this).setCapacidadeDefesa(valor);
        else if(this instanceof GuardaRedes) {
            if(caracteristica.equals("Altura")) ((GuardaRedes) this).setAltura(valor);
            else if(caracteristica.equals("Elasticidade")) ((GuardaRedes) this).setElasticidade(valor);
        }
        else if(this instanceof Lateral && caracteristica.equals("Cruzamento")) ((Lateral) this).setCruzamento(valor);
        else if(this instanceof Medio && caracteristica.equals("Recuperacao de Bola")) ((Medio) this).setRecuperacaoBola(valor);
    }

    public void addToHistorico(String equipa) {
        this.historico.add(equipa);
    }
}