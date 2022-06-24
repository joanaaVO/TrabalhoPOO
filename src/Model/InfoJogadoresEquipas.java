package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InfoJogadoresEquipas implements Serializable {
    private Map<String, Jogador> jogadores;
    private Map<String, Equipa> equipas;

    public InfoJogadoresEquipas() {
        this.jogadores = new HashMap<>();
        this.equipas = new HashMap<>();
    }

    public InfoJogadoresEquipas(Map<String, Jogador> jogadores, Map<String, Equipa> equipas) {
        setJogadores(jogadores);
        setEquipas(equipas);
    }

    public InfoJogadoresEquipas(InfoJogadoresEquipas info) {
        this.jogadores = info.getJogadores();
        this.equipas = info.getEquipas();
    }

    public Map<String, Jogador> getJogadores() {
        return jogadores.values().stream().collect(Collectors.toMap(Jogador::getNome, Jogador::clone));
    }

    public Map<String, Equipa> getEquipas() {
        return equipas.values().stream().collect(Collectors.toMap(Equipa::getNome, Equipa::clone));
    }

    public void setJogadores(Map<String, Jogador> jogadores) {
        this.jogadores = jogadores.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void setEquipas(Map<String, Equipa> equipas) {
        this.equipas = equipas.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public Jogador getJogador(String nome) {
        return this.jogadores.get(nome);
    }

    public boolean existsJogador(String nome) {
        return this.jogadores.containsKey(nome);
    }

    public Equipa getEquipa(String nome) {
        return this.equipas.get(nome);
    }

    public boolean existsEquipa(String nome) {
        return this.equipas.containsKey(nome);
    }
}
