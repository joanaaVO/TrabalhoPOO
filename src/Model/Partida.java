package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Partida implements Serializable {
    private Equipa visitado;
    private Equipa visitante;

    private double habilidadeVisitado;
    private double habilidadeVisitante;

    private ModeloTatico modeloVisitado;
    private ModeloTatico modeloVisitante;

    private int resultadoVisitado;
    private int resultadoVisitante;

    private int duracao;
    private int partes;
    private LocalDate data;

    Map<Integer,Integer> subVisitado;
    Map<Integer, Integer> subVisitante;

    public Partida()  {
        this.visitado = new Equipa();
        this.visitante = new Equipa();
        this.duracao = 0;
        this.partes = 0;
        this.data = LocalDate.now();
        this.habilidadeVisitante = 0;
        this.habilidadeVisitado = 0;
        this.modeloVisitado = new ModeloTatico1442(this.visitado);
        this.modeloVisitante = new ModeloTatico1433(this.visitante);
        this.subVisitado = new HashMap<>();
        this.subVisitante = new HashMap<>();
    }

   public Partida(Equipa visitado, Equipa visitante, double habilidadeVisitado
           , double habilidadeVisitante, int duracao, int partes, LocalDate data
           , ModeloTatico modeloVisitante, ModeloTatico modeloVisitado
           , Map<Integer,Integer> subVisitado, Map<Integer,Integer> subVisitante) {
        // O clone é para ter a certeza que qualquer mudança ao estado anterior das equipas não é alterado.
        this.visitado = visitado;
        this.visitante = visitante;
        this.duracao = duracao;
        this.partes = partes;
        this.data = data;
        this.habilidadeVisitado = habilidadeVisitado;
        this.habilidadeVisitante = habilidadeVisitante;
        this.modeloVisitante = modeloVisitante.clone();
        this.modeloVisitado = modeloVisitado.clone();
        setSubVisitado(subVisitado);
        setSubVisitante(subVisitante);
        this.resultadoVisitado = 0;
        this.resultadoVisitante = 0;
    }

    //Construtor específico para o parser do ficheiro log. Uma vez que já contém o conteúdo do resultado.
    public Partida(Equipa visitado, Equipa visitante, double habilidadeVisitado
            , double habilidadeVisitante, int duracao, int partes, LocalDate data
            , ModeloTatico modeloVisitante, ModeloTatico modeloVisitado
            , Map<Integer,Integer> subVisitado, Map<Integer,Integer> subVisitante
            , int resultadoVisitado, int resultadoVisitante) {
        // O clone é para ter a certeza que qualquer mudança ao estado anterior das equipas não é alterado.
        this.visitado = visitado;
        this.visitante = visitante;
        this.duracao = duracao;
        this.partes = partes;
        this.data = data;
        this.habilidadeVisitado = habilidadeVisitado;
        this.habilidadeVisitante = habilidadeVisitante;
        this.modeloVisitante = modeloVisitante.clone();
        this.modeloVisitado = modeloVisitado.clone();
        setSubVisitado(subVisitado);
        setSubVisitante(subVisitante);
        this.resultadoVisitado = resultadoVisitado;
        this.resultadoVisitante = resultadoVisitante;
    }





    public Partida(Partida prt) {
        this.visitado = prt.getVisitado();
        this.visitante = prt.getVisitante();
        this.duracao = prt.getDuracao();
        this.partes = prt.getPartes();
        this.data = prt.getData();
        this.modeloVisitado = prt.getModeloVisitado();
        this.modeloVisitante = prt.getModeloVisitante();
        this.resultadoVisitado = prt.getResultadoVisitado();
        this.resultadoVisitante = prt.getResultadoVisitante();
        this.subVisitante = prt.getSubVisitante();
        this.subVisitado = prt.getSubVisitado();

    }

    public void setResultadoVisitado(int res) {
        this.resultadoVisitado = res;
    }

    public void setResultadoVisitante(int res) {
        this.resultadoVisitante = res;
    }
    public ModeloTatico getModeloVisitado() {
        return this.modeloVisitado.clone();
    }

    public ModeloTatico getModeloVisitante() {
        return this.modeloVisitante.clone();
    }

    public Equipa getVisitado() {
        return this.visitado;
    }

    public Equipa getVisitante() {
        return this.visitante;
    }

    public void setSubVisitado(Map<Integer, Integer> sub) {
        this.subVisitado = new HashMap<>(sub);
    }

    public void setSubVisitante(Map<Integer, Integer> sub) {
        this.subVisitante = new HashMap<>(sub);
    }

    public int getDuracao() {
        return this.duracao;
    }

    public int getPartes() {
        return this.partes;
    }

    public LocalDate getData() {
        return this.data;
    }

    public int getResultadoVisitado() {
        return this.resultadoVisitado;
    }

    public int getResultadoVisitante() {
        return this.resultadoVisitante;
    }

    public Map<Integer, Integer> getSubVisitado() {
        return this.subVisitado.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
    }

    public Map<Integer, Integer> getSubVisitante() {
        return this.subVisitante.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
    }

    public void setVisitado(Equipa visitado) {
        this.visitado = visitado;
    }

    public void setVisitante(Equipa visitante) {
        this.visitante = visitante;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }


    public boolean vencedorVisitado() {
        return this.resultadoVisitado > this.resultadoVisitante;
    }

    public boolean vencedorVisitante() {
        return this.resultadoVisitante > this.resultadoVisitado;
    }

    /*public void calculaResultados() {
        // Algoritmo ainda muito rudimentar
        int i;
        for(i = 0; i < this.partes; i++) {
            if (this.habilidadeVisitado > this.habilidadeVisitante) this.resultadoVisitado++;
            else if(habilidadeVisitante > habilidadeVisitado) this.resultadoVisitante++;
        }
    }
     */

    public String toString() {
        StringBuilder sb = new StringBuilder("Partida: {");
        sb.append(this.visitado.getNome()); sb.append("; ");
        sb.append(this.visitante.getNome()); sb.append("; ");
        sb.append("Resultado Visitado - Visitante: "); sb.append(this.resultadoVisitado); sb.append(" - ");
        sb.append(this.resultadoVisitante); sb.append(" }");

        return sb.toString();
    }

    public Partida clone() {
        return new Partida(this);
    }

    //public void addSubstituicao(int inicio, int fim) {
    //    this.substituicoes.put(inicio,fim);
    //}

    public void setModeloVisitado(ModeloTatico mT) {
        this.modeloVisitado = mT.clone();
    }

    public void setModeloVisitante(ModeloTatico mT) {
        this.modeloVisitante = mT.clone();
    }

    public List<Integer> subsToListVisitante() {
        List<Integer> res = new ArrayList<>();
        for(int num : this.subVisitante.keySet()) {
            res.add(num);
            res.add(this.subVisitante.get(num));
        }
        return res;
    }

    public List<Integer> subsToListVisitado() {
        List<Integer> res = new ArrayList<>();
        for(int num : this.subVisitado.keySet()) {
            res.add(num);
            res.add(this.subVisitado.get(num));
        }
        return res;
    }


}
