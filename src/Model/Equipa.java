package Model;

import Exceptions.JogadorNaoExistente;
import Exceptions.NumeroJaExistente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Equipa implements Serializable {
private String nome;
private Map<Integer, Jogador> jogadores;
private List<Integer> titulares;
private List<Integer> suplentes;
private double habilidadeTotal;

public Equipa() {
    this.nome = "n/a";
    this.jogadores = new HashMap<>();
    this.titulares = new ArrayList<>();
    this.suplentes = new ArrayList<>();
    this.habilidadeTotal = 0;
}

public Equipa(String nome) {
    this.nome = nome;
    this.jogadores = new HashMap<>();
    this.titulares = new ArrayList<>();
    this.suplentes = new ArrayList<>();
    this.habilidadeTotal = 0;
}
public static Equipa parse(String input){
    String[] campos = input.split(",");
    return new Equipa(campos[0]);
}

public Equipa(String nome, Map<Integer,Jogador> jogadores, List<Integer> titulares, List<Integer> suplentes) {
    this.nome = nome;
    setJogadores(jogadores);
    setTitulares(titulares);
    setSuplentes(suplentes);
    setHabilidadeTotal(getHabilidadeTotal());
}

public Equipa(Equipa eq) {
    this.nome = eq.getNome();
    setJogadores(eq.getJogadores());
    setTitulares(eq.getTitulares());
    setSuplentes(eq.getSuplentes());
    setHabilidadeTotal(eq.getHabilidadeTotal());
}

public String getNome() {
                      return nome;
                                  }

public void setNome(String nome) {
                               this.nome = nome;
                                                }

public Map<Integer, Jogador> getJogadores() {
    return this.jogadores.values().stream().collect(Collectors.toMap(Jogador::getNumeroJ, Jogador::clone));
}

public void setJogadores(Map<Integer, Jogador> jogadores) {
    this.jogadores = jogadores.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
}

public List<Integer> getTitulares() {
                                  return new ArrayList<>(this.titulares);
                                                                         }

public void setTitulares(List<Integer> titulares) {
                                                this.titulares = new ArrayList<>(titulares);
                                                                                            }

public List<Integer> getSuplentes() {
                                  return new ArrayList<>(this.suplentes);
                                                                         }

public void setSuplentes(List<Integer> suplentes) {
                                                this.suplentes = new ArrayList<>(suplentes);
                                                                                            }

public double getHabilidadeTotal() {
    return this.jogadores.values().stream().mapToDouble(Jogador::getHabilidade).average().orElse(Double.NaN);
}

public void setHabilidadeTotal(double habilidade) {
                                                this.habilidadeTotal = habilidade;
                                                                                  }

public Equipa clone() {
                    return new Equipa(this);
                                            }

public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append("Nome: ");
    sb.append(this.nome + "\n");
    sb.append("Jogadores: ");
    sb.append(this.jogadores.toString() + "\n");
    sb.append("Titulares: ");
    sb.append(this.titulares.toString() + "\n");
    sb.append("Suplentes: ");
    sb.append(this.suplentes.toString() + "\n");
    sb.append("Habilidade: ");
    sb.append(this.habilidadeTotal + "\n");

    return sb.toString();
}

public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Equipa equipa = (Equipa) o;
    return equipa.habilidadeTotal == this.habilidadeTotal
            && this.nome.equals(equipa.nome)
            && this.jogadores.equals(equipa.jogadores)
            && this.titulares.equals(equipa.titulares)
            && this.suplentes.equals(equipa.suplentes);
}

public void addJogador(Jogador j) throws NumeroJaExistente {
    if(this.jogadores.containsKey(j.getNumeroJ())) throw new NumeroJaExistente(""+j.getNumeroJ());
    this.jogadores.put(j.getNumeroJ(),j);
}

public void addTitular(Integer num) throws JogadorNaoExistente {
    if (this.getJogador(num) != null)
        this.titulares.add(num);
}

public void addSuplente(Integer num) throws JogadorNaoExistente {
    if (this.getJogador(num) != null)
        this.suplentes.add(num);
}

public Jogador getJogador(Integer num) throws JogadorNaoExistente {
    if(!this.jogadores.containsKey(num)) throw new JogadorNaoExistente("Jogador não existente");
    return this.jogadores.get(num);
}

public void removeJogador(Jogador j) {
                                   this.jogadores.remove(j);
                                                            }

public boolean existeJogadorComNumero(int num) {
                                             return this.jogadores.containsKey(num);
                                                                                    }

public Jogador getGuardaRedesTitular() throws JogadorNaoExistente {
    for(int num : this.titulares) {
        Jogador jog = this.getJogador(num);
        if(jog instanceof GuardaRedes) return jog;
    }
    return null;
}

public List<Integer> getLateraisTitulares() throws JogadorNaoExistente {
    List<Integer> res = new ArrayList<>();
    List<Integer> titulares = getTitulares();

    for(Integer num : this.titulares) {
        Jogador jog = getJogador(num);
        if(jog instanceof Lateral) res.add(num);
    }
    return res;
}

public List<Integer> getNaoLateraisTitulares() throws JogadorNaoExistente {
        List<Integer> res = new ArrayList<>();
        List<Integer> titulares = getTitulares();

        for(Integer num : this.titulares) {
            Jogador jog = getJogador(num);
            if(!(jog instanceof Lateral) && !(jog instanceof GuardaRedes)) res.add(num);
        }
        return res;
    }
}
