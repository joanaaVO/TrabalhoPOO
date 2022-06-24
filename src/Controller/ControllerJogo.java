package Controller;

import Exceptions.EquipaNaoExistente;
import Exceptions.JogadorNaoExistente;
import Exceptions.SubstituicaoImpossivel;
import Model.*;
import View.Menu;

import java.util.*;

public class ControllerJogo {
    Desporto desporto;
    Partida partida;
    private int time;
    private List<Acontecimento> acontecimentos; //ordenados por data de acontecimento
    private static Menu equipaMenu = new Menu("Menu Jogo");
    private static Scanner sc = new Scanner(System.in);
    int resultadoVisitado;
    int resultadoVisitante;
    public ControllerJogo(Desporto desporto) {
        this.desporto = desporto;
        this.time = 0;
        this.acontecimentos = new ArrayList<>();
        this.resultadoVisitado = 0;
        this.resultadoVisitante = 0;
    }

    public void calculaAcontecimentos(int inicio1, int inicio2, String eqVisitado, String eqVisitante) throws JogadorNaoExistente { //acho q secalhar aqui fazemos só a gestão da posse de bola e golo
        int val;
        Acontecimento acontecimento;
        double habAtaqueVisitado = this.partida.getModeloVisitado().getHabilidadeEquipaAtaque();
        double habDefesVisitado = this.partida.getModeloVisitado().getHabilidadeEquipaDefesa();
        double habAtaqueVisitante = this.partida.getModeloVisitante().getHabilidadeEquipaAtaque();
        double habDefesVisitante = this.partida.getModeloVisitante().getHabilidadeEquipaDefesa();

        if(habAtaqueVisitado >= habDefesVisitante) {
            val = new Random().nextInt((int) (habAtaqueVisitado + habDefesVisitado)); //calcula um número aleatório entre 0 e a soma das habilidades
            if(val < habAtaqueVisitado) { //se calhar no intervalo correspondente ao visitado
                acontecimento = new PosseDeBola(inicio1, eqVisitado,true); //ganha posse de bola
                this.acontecimentos.add(acontecimento);
                val = new Random().nextInt((int) (habAtaqueVisitado + habAtaqueVisitante)); //nªaleatório de novo
                if(val < habAtaqueVisitado) { //voltar a calhar no visitado
                    acontecimento = new Golo(inicio2,eqVisitado,true);
                    this.acontecimentos.add(acontecimento);
                    this.resultadoVisitado++;
                }
                else {
                    acontecimento = new Golo(inicio1,eqVisitado,false);
                    this.acontecimentos.add(acontecimento);
                }
            }
            else { //caso a outra equipa ganhe a posse de bola
                acontecimento = new PosseDeBola(inicio1,eqVisitante,true);
                this.acontecimentos.add(acontecimento);
                val = new Random().nextInt((int) (habAtaqueVisitado + habAtaqueVisitante));
                if(val < habAtaqueVisitado) { //se não calhar nesta equipa
                    acontecimento = new Golo(inicio2,eqVisitante,false); //não há golo
                    this.acontecimentos.add(acontecimento);
                }
                else {
                    acontecimento = new Golo(inicio2,eqVisitado,true); //faz golo
                    this.acontecimentos.add(acontecimento);
                    this.resultadoVisitado++;
                }
            }
        }
        else if(habAtaqueVisitante > habDefesVisitado) {
            val = new Random().nextInt((int) (habAtaqueVisitado + habDefesVisitado)); //calcula um número aleatório entre 0 e a soma das habilidades
            if(val < habAtaqueVisitante) { //se calhar no intervalo correspondente ao visitante
                acontecimento = new PosseDeBola(inicio1, eqVisitante,true); //ganha posse de bola
                this.acontecimentos.add(acontecimento);
                val = new Random().nextInt((int) (habAtaqueVisitado + habAtaqueVisitante)); //nªaleatório de novo
                if(val < habAtaqueVisitante) { //voltar a calhar no visitante
                    acontecimento = new Golo(inicio2,eqVisitante,true);
                    this.acontecimentos.add(acontecimento);
                    this.resultadoVisitante++;
                }
                else {
                    acontecimento = new Golo(inicio1,eqVisitante,false);
                    this.acontecimentos.add(acontecimento);
                }
            }
            else { //caso a outra equipa ganhe a posse de bola
                acontecimento = new PosseDeBola(inicio1,eqVisitado,true);
                this.acontecimentos.add(acontecimento);
                val = new Random().nextInt((int) (habAtaqueVisitado + habAtaqueVisitante));
                if(val < habAtaqueVisitante) { //se não calhar nesta equipa
                    acontecimento = new Golo(inicio2,eqVisitado,false); //não há golo
                    this.acontecimentos.add(acontecimento);
                }
                else {
                    acontecimento = new Golo(inicio2,eqVisitante,true); //faz golo
                    this.acontecimentos.add(acontecimento);
                    this.resultadoVisitante++;
                }
            }
        }
    }

    public void run() {
        definirAcontecimentos();
        printAcontecimentos();
    }

    public void setPartida(Partida partida) {
        this.partida = partida.clone();
    }

    public void calculaSubstituicoes(int inicio, int numJogSai, int numJogEntra, boolean visitante) {
        ModeloTatico modTatico = visitante? this.partida.getModeloVisitante() : this.partida.getModeloVisitado();
        String equipa = this.partida.getVisitado().getNome();
        boolean subsituido;
        try{
            modTatico.substituicoes(numJogSai,numJogEntra);
            subsituido = true;
        }
        catch (SubstituicaoImpossivel | JogadorNaoExistente e) {
            equipaMenu.message(e.getMessage());
            subsituido = false;
        }
        Acontecimento acontecimento = new Substituicao(inicio,equipa, numJogSai,numJogEntra,subsituido);
        this.acontecimentos.add(acontecimento);
        if (visitante) this.partida.setModeloVisitante(modTatico);
        else this.partida.setModeloVisitado(modTatico);
    }

    public void definirAcontecimentos() {
        int scUtilizador;
        List <Integer> subsVisitado = this.partida.subsToListVisitado();
        int ivisitado = 0;
        List<Integer> subsVisitante = this.partida.subsToListVisitante();
        int ivisitante = 0;
        equipaMenu.message("Número de partes do jogo (mínimo 7): ");
        scUtilizador = sc.nextInt();
        int duracaoParte = this.desporto.getDuracao()/scUtilizador;
        ArrayList<Integer> partes = new ArrayList<>();
        for(int i=1; i<scUtilizador; i++) partes.add(i);
        Collections.shuffle(partes);
        for(int i=0; i<scUtilizador-1; i++) {
            try {
                int inicio = (partes.get(i)) * duracaoParte;
                if (partes.get(i) > 0 && partes.get(i) < 7) {
                    if(partes.get(i)%2 == 0) calculaSubstituicoes(inicio,subsVisitado.get(ivisitado++),subsVisitado.get(ivisitado++),false);
                    else calculaSubstituicoes(inicio,subsVisitante.get(ivisitante++),subsVisitante.get(ivisitante++),true);
                }
                else calculaAcontecimentos(inicio, inicio + duracaoParte / 2,this.partida.getVisitado().getNome(),this.partida.getVisitante().getNome());
            }
            catch (JogadorNaoExistente e) {
            }
        }
    }

    public void printAcontecimentos() {
        for(Acontecimento a : this.acontecimentos) {
            if(a instanceof Golo) {
                if (((Golo) a).isGolo()) equipaMenu.message("Golo da equipa " + a.getEquipa());
                else equipaMenu.message("Possibilidade de Golo da Equipa " + a.getEquipa() + "falhada!");
            }
            if(a instanceof PosseDeBola) {
                if(((PosseDeBola) a).isPosseDeBola()) equipaMenu.message(a.getEquipa() + "conseguiu posse de bola.");
                else equipaMenu.message(a.getEquipa() + "perdeu posse de bola.");
            }
            if(a instanceof Substituicao) {
                try {
                    if(((Substituicao) a).isAconteceu()) {
                        String equipa = a.getEquipa();
                        Jogador jogSai = this.desporto.getJogadorByNum(equipa, ((Substituicao) a).getJogSai());
                        Jogador jogEntra = this.desporto.getJogadorByNum(equipa, ((Substituicao) a).getJogEntra());
                        equipaMenu.message(jogSai + "->" + jogEntra);
                    }
                    else equipaMenu.message("Não foi possível fazer a substituição");
                }
                catch (EquipaNaoExistente | JogadorNaoExistente e) {
                    equipaMenu.message(e.getMessage());
                }
            }
            equipaMenu.message("Pressione qualquer tecla para continuar.");
            sc.next();
        }
        equipaMenu.message(this.partida.getVisitado().getNome() + ": " + this.resultadoVisitado + " - " +
                this.resultadoVisitante + " : " + this.partida.getVisitante().getNome() );
    }


}
