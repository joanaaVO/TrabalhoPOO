package Model;

import Exceptions.EquipaNaoExistente;
import Exceptions.InsercaoLateralImpossivel;
import Exceptions.JogadorNaoExistente;
import Exceptions.NumeroJaExistente;
import Model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedDesporto {
    private String file;
    public SavedDesporto() {
        this.file = "";
    }

    public Desporto parser(String file) throws IOException, NumeroJaExistente, EquipaNaoExistente, JogadorNaoExistente, InsercaoLateralImpossivel {
        Desporto desporto = new Desporto();
        String equipaRecente;
        List<String> linhas = lerFicheiro(file);
        Map<String, Equipa> equipas = new HashMap<>(); //nome, equipa
        Map<Integer, Jogador> jogadores = new HashMap<>(); //numero, jogador
        List<Partida> jogos = new ArrayList<>();
        String ultima = null;
        Jogador j = null;
        String[] linhaPartida;
        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch (linhaPartida[0]) {
                case "Equipa":
                    Equipa e = Equipa.parse(linhaPartida[1]);
                    desporto.addEquipa(e.getNome(), e);
                    ultima = e.getNome();
                    break;
                case "Guarda-Redes":
                    j = GuardaRedes.parse(linhaPartida[1]);
                    if (ultima != null) {          //we need to insert the player into the team
                        desporto.addJogadorToEquipa(ultima, j);
                    }
                    break;
                case "Defesa":
                    j = Defesa.parse(linhaPartida[1]);
                    if (ultima != null) {  //we need to insert the player into the team
                        desporto.addJogadorToEquipa(ultima, j);
                    }
                    break;
                case "Medio":
                    j = Medio.parse(linhaPartida[1]);
                    if (ultima != null) {      //we need to insert the player into the team
                        desporto.addJogadorToEquipa(ultima, j);
                    }
                    break;
                case "Lateral":
                    j = Lateral.parse(linhaPartida[1]);
                    if (ultima != null) {  //we need to insert the player into the team
                        desporto.addJogadorToEquipa(ultima, j);
                    }
                    break;
                case "Avancado":
                    j = Avancado.parse(linhaPartida[1]);
                    if (ultima != null) {  //we need to insert the player into the team
                        desporto.addJogadorToEquipa(ultima, j);
                    }
                    break;
                case "Jogo":
                    Partida jo = parsePartida(linhaPartida[1], desporto);
                    desporto.addPartida(jo);
                    break;
                default:
            }

        }

        //debug
        for (Equipa e: equipas.values()){
            System.out.println(e.toString());
        }
        for (Partida jog: jogos){
            System.out.println(jog.toString());
        }

        return desporto;
    }

    public Desporto loadData(String file) throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(file);

        ObjectInputStream o = new ObjectInputStream(f);
        Desporto d = (Desporto) o.readObject();
        o.close();
        return d;
    }

    public void saveData(String file, Desporto desp) throws IOException {
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(desp);
        o.flush(); // para ter a certeza que todos os dados foram gravados
        o.close();
    }


    public static List<String> lerFicheiro(String nomeFich) throws IOException {
        List<String> lines;
        lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);
        return lines;
    }

    public static Partida parsePartida(String input, Desporto desp) throws EquipaNaoExistente, JogadorNaoExistente {
        String[] campos = input.split(",");
        String[] data = campos[4].split("-");
        List<Integer> jc = new ArrayList<>();
        List<Integer> jf = new ArrayList<>();
        Map<Integer, Integer> subsC = new HashMap<>();
        Map<Integer, Integer> subsF = new HashMap<>();
        Equipa casa = desp.getEquipa(campos[0]);
        Equipa fora = desp.getEquipa(campos[1]);



        for (int i = 5; i < 16; i++){
            casa.addTitular(Integer.parseInt(campos[i]));
        }

        for (int i = 16; i < 19; i++){
            String[] sub = campos[i].split("->");
            subsC.put(Integer.parseInt(sub[0]), Integer.parseInt(sub[1]));
        }

        for (int i = 19; i < 30; i++){
            fora.addTitular(Integer.parseInt(campos[i]));
        }
        for (int i = 30; i < 33; i++){
            String[] sub = campos[i].split("->");
            subsF.put(Integer.parseInt(sub[0]), Integer.parseInt(sub[1]));
        }

        desp.setEquipa(casa);
        desp.setEquipa(fora);

        Partida partida = new Partida(casa, fora, casa.getHabilidadeTotal(),
                fora.getHabilidadeTotal(),
                90,
                2,
                LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])),
                new ModeloTatico1442(casa),
                new ModeloTatico1442(fora),
                subsC,
                subsF,
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]));

        return partida;
    }



}
