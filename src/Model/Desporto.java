package Model;

import Exceptions.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Desporto implements Serializable
{
    private int numjogequipa; //número de jogadores por equipa
    private int numsuplentes;
    private int duracaoP; 
    private String tipoJogador;
    private List<Partida> partidas;
    private Map<String,Equipa> equipas;
    private Map<String,Jogador> jogSemEquipa;
    private Map<String, Jogador> jogComEquipa;

    public Desporto(int numjogequipa,int numsuplentes,int duracaoP,String tipoJogador,List<Partida> partidas, Map<String,Equipa> equipas,
                    Map<String,Jogador> jogSemEquipa){
        this.numjogequipa=numjogequipa;
        this.numsuplentes=numsuplentes;
        this.duracaoP=duracaoP;
        this.tipoJogador=tipoJogador;
        setPartidas(partidas);
        setEquipas(equipas);
        setJogSemEquipa(jogSemEquipa);
        setJogComEquipa(new HashMap<>());
    }
    
    public Desporto(Desporto d){
        this.numjogequipa=d.getNumjogequipa();
        this.numsuplentes=d.getNumsuplentes();
        this.duracaoP=d.getDuracao();
        this.tipoJogador=d.getTipoJogador();
        setPartidas(d.getPartidas());
        setEquipas(d.getEquipas());
        setJogSemEquipa(d.getJogSemEquipa());
        setJogComEquipa(d.getJogComEquipa());
    }

    public Desporto() {
        this.numjogequipa = 0;
        this.numsuplentes = 0;
        this.duracaoP = 0;
        this.tipoJogador = "";
        this.partidas = new ArrayList<>();
        this.equipas = new HashMap<>();
        this.jogSemEquipa = new HashMap<>();
        this.jogComEquipa = new HashMap<>();
    }
    public int getNumjogequipa(){
      return  this.numjogequipa;
    }

    public void setNumjogequipa(int numjogequipa){
        this.numjogequipa=numjogequipa;
}

    public int getNumsuplentes(){
    return this.numsuplentes;
}

    public void setNumsuplentes(int numsuplentes){
    this.numsuplentes=numsuplentes;
}

    public int getDuracao(){
    return this.duracaoP;
}

    public String getTipoJogador(){
   return this.tipoJogador;
}

    public List<Partida> getPartidas() {
        return this.partidas.stream().map(Partida::clone).collect(Collectors.toList());
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas.stream().map(Partida::clone).collect(Collectors.toCollection(ArrayList::new));
    }

    public Map<String,Equipa> getEquipas() {
        return this.equipas.values().stream().collect(Collectors.toMap(Equipa::getNome, Equipa::clone));
    }

    public void setEquipas(Map<String,Equipa> equipas) {
        this.equipas = equipas.values().stream().collect(Collectors.toMap(Equipa::getNome, Equipa::clone));
    }

    public Map<String,Jogador> getJogSemEquipa() {
        return this.jogSemEquipa.values().stream().collect(Collectors.toMap(Jogador::getNome, Jogador::clone));
    }

    public void setJogSemEquipa(Map<String,Jogador> jogadores) {
        this.jogSemEquipa = jogadores.values().stream().collect(Collectors.toMap(Jogador::getNome, Jogador::clone));
    }

    public Map<String,Jogador> getJogComEquipa() {
        return this.jogComEquipa.values().stream().collect(Collectors.toMap(Jogador::getNome, Jogador::clone));
    }

    public void setJogComEquipa(Map<String,Jogador> jogadores) {
        this.jogComEquipa = jogadores.values().stream().collect(Collectors.toMap(Jogador::getNome, Jogador::clone));
    }

    public Desporto clone(){
    return new Desporto(this);
}

    public String toString(){
    StringBuilder sb= new StringBuilder();
    sb.append("Numero de jogadores por equipa:");
    sb.append(this.getNumjogequipa());
    sb.append("\nNumero de suplentes:");
    sb.append(this.getNumsuplentes());
    sb.append("\nDuracao:");
    sb.append(this.getDuracao());
    sb.append("\nTipo de Jogador:");
    sb.append(this.getTipoJogador());
    sb.append("\nPartidas:");
    sb.append(this.getPartidas().toString());
    return sb.toString();
    }

    public boolean equals(Object o){
        int i,j;
        if(this == o)return true;
        if(o == null || o.getClass() != this.getClass())return false;
        Desporto d=(Desporto) o;
        return this.numjogequipa==d.getNumjogequipa() && this.numsuplentes==d.getNumsuplentes() && this.duracaoP==d.getDuracao()
        && this.tipoJogador==d.getTipoJogador();
    }

    public Equipa getEquipa(String nome) throws EquipaNaoExistente {
        if (!this.equipas.containsKey(nome)) throw new EquipaNaoExistente(nome);
        return this.equipas.get(nome);
    }

    public void addPartida(Partida partida) {
        this.partidas.add(partida.clone());
    }

    public void addEquipa(String nome, Equipa eq) {
        this.equipas.put(nome, eq);
    }

    public void addJogador(String nome, Jogador jog) {
        this.jogSemEquipa.put(nome,jog);
    }

    public void addJogadorToEquipa(String equipa, Jogador jog) throws EquipaNaoExistente, NumeroJaExistente {
        this.getEquipa(equipa).addJogador(jog.clone());
        setEquipa(getEquipas().get(equipa));
    }

    public Map<Integer,Jogador> getJogadoresFrom(String equipa) throws EquipaNaoExistente {
        if (!this.equipas.containsKey(equipa)) throw new EquipaNaoExistente(equipa);
        return this.equipas.get(equipa).getJogadores();
    }

    public Jogador getJogadorByNum(String equipa, int num) throws EquipaNaoExistente, JogadorNaoExistente {
        Map<Integer, Jogador> ls = getJogadoresFrom(equipa);
        if(!ls.containsKey(num)) throw new JogadorNaoExistente("Jogador nao existente nesta equipa!");
        if(!this.equipas.containsKey(equipa)) throw new EquipaNaoExistente("Equipa nao existente.");
        return ls.get(num).clone();
    }

    /*public Jogador getJogadorFromEquipaByName(String equipa, String nome) throws EquipaNaoExistente, JogadorNaoExistente {
        Map<String, Jogador> ls = getJogadoresFrom(equipa);
        if(!ls.containsKey(nome)) throw new JogadorNaoExistente("Jogador não existente nesta equipa!");
        if(!this.equipas.containsKey(equipa)) throw new EquipaNaoExistente(equipa);
        return ls.get(nome);
    }
     */

    public Jogador getJogadorbyName(String nome) throws JogadorNaoExistente {
        if(!jogSemEquipa.containsKey(nome) && !jogComEquipa.containsKey(nome)) throw new JogadorNaoExistente(nome);
        if(!jogSemEquipa.containsKey(nome)) return this.jogComEquipa.get(nome);
        if(!jogComEquipa.containsKey(nome)) return this.jogSemEquipa.get(nome);
        return null;
    }

    public boolean jogadorExisteNaEquipa(String equipa, int num) throws EquipaNaoExistente {
        return this.getJogadoresFrom(equipa).containsKey(num);
    }

    public boolean jogadorExiste(String nome) {
        return (jogSemEquipa.containsKey(nome) || jogComEquipa.containsKey(nome));
    }

    public boolean equipaExiste(String nome) {
        return this.equipas.containsKey(nome);
    }

    public Partida getPartidaByNum(int num) throws PartidaNaoExiste {
        if(num > this.partidas.size() || num <= 0 ) throw new PartidaNaoExiste(Integer.toString(num));
        else return (this.partidas.get(num - 1));
    }

    public Jogador getJogadorSemEquipa(String nome) throws JogadorSemEquipaNaoExistente {
        Jogador jog = null;

        jog = this.jogSemEquipa.get(nome);
        if (jog == null) throw new JogadorSemEquipaNaoExistente(nome);
        return jog;
    }

    public void setEquipa(Equipa equipa) {
        this.equipas.replace(equipa.getNome(),equipa.clone());
    }

    public void setJogador(Jogador jogador) {
        if(this.jogSemEquipa.containsKey(jogador.getNome())) this.jogSemEquipa.replace(jogador.getNome(),jogador);
        else this.jogComEquipa.replace(jogador.getNome(),jogador);
    }

    public void substituicaoEquipas(Jogador jog, String eq1, String eq2) throws NumeroJaExistente {
        this.equipas.get(eq1).removeJogador(jog);
        this.equipas.get(eq2).addJogador(jog);
        jog.addToHistorico(eq1);
        setEquipa(getEquipas().get(eq1));
        setEquipa(getEquipas().get(eq2));
    }

    public void addTitularToEquipa(String equipa, int num) throws EquipaNaoExistente, JogadorNaoExistente {
        Equipa tmp = this.getEquipa(equipa);
        tmp.addTitular(num);
        setEquipa(tmp);
    }

    public void guardaDesporto(String file) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
}
