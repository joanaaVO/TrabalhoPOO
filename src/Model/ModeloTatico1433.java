package Model;

import Exceptions.JogadorNaoExistente;

import java.io.Serializable;
import java.util.*;

public class ModeloTatico1433 extends ModeloTatico implements Serializable {
    public ModeloTatico1433(Equipa eq) {
        super(eq);
        setPosicoes();
    }

    public ModeloTatico1433(ModeloTatico1433 m) {
        super(m.getEquipa());
        setPosicoes();
    }

    public void setPosicoes() {
        try {
            Map<String, List<Integer>> pos = new HashMap<>();
            List<Integer> ls = this.getEquipa().getTitulares();
            List<Integer> naolats = this.getEquipa().getNaoLateraisTitulares();
            List<Integer> lats = this.getEquipa().getLateraisTitulares();
            Jogador gr = this.getEquipa().getGuardaRedesTitular();
            List<Integer> grs = new ArrayList<>();
            grs.add(gr.getNumeroJ());
            pos.put("GuardaRedes",grs);
            pos.put("Defesa",new ArrayList<Integer>(Arrays.asList(new Integer[] {-1,-1,-1,-1})));
            pos.put("Medio",new ArrayList<>(Arrays.asList(new Integer[] {-1,-1,-1})));
            pos.put("Avancado",new ArrayList<>(Arrays.asList(new Integer[] {-1,-1,-1})));

            for(List<Integer> li : pos.values()) {
                if(li.get(0) != gr.getNumeroJ()) {
                    if(!lats.isEmpty()) {
                        li.set(0, lats.get(0));
                        lats.remove(0);
                    }
                    if(!lats.isEmpty()) {
                        int size = li.size();
                        li.set(size - 1, lats.get(0));
                        lats.remove(0);
                    }

                }

            }
            for(List<Integer> li : pos.values()) {
                if (li.get(0) != gr.getNumeroJ()) {
                    int j = 0;
                    for(int n : li) {
                        if(n == -1) {
                            li.set(j, naolats.get(0));
                            naolats.remove(0);
                        }
                        j++;
                    }
                }
            }


            this.posicoes = pos;
        }
        catch (JogadorNaoExistente e) {
            //O jogador existe sempre...
        }

    }

    public ModeloTatico clone() {
            return new ModeloTatico1433(this);
    }
}
