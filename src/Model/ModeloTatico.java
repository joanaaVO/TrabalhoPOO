package Model;

import Exceptions.JogadorNaoExistente;
import Exceptions.SubstituicaoImpossivel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class ModeloTatico implements Serializable {
   protected Map<String, List<Integer>> posicoes;
   private Equipa equipa;

   public ModeloTatico(Equipa equipa) {
        this.equipa = equipa;
   }

    public Equipa getEquipa() {
        return equipa.clone();
    }

    public abstract void setPosicoes();

   public double getHabilidadeEquipaDefesa() throws JogadorNaoExistente {
       List<Integer> guardaRedes = this.posicoes.get("GuardaRedes");
       List<Integer> defesa = this.posicoes.get("Defesa");
       List<Integer> medio = this.posicoes.get("Medio");
       List<Integer> avancado = this.posicoes.get("Avancado");

       return getHabilidadeDefesas(defesa) * 0.4
               + getHabilidadesGuardaRedes(guardaRedes) * 0.4
               + getHabilidadeAvancados(avancado) * 0.1
               + getHabilidadeMedios(medio) * 0.1;
   }

   public double getHabilidadeEquipaAtaque() throws JogadorNaoExistente {
       List<Integer> guardaRedes = this.posicoes.get("GuardaRedes");
       List<Integer> defesa = this.posicoes.get("Defesa");
       List<Integer> medio = this.posicoes.get("Medio");
       List<Integer> avancado = this.posicoes.get("Avancado");

       return getHabilidadeDefesas(defesa) * 0.05
               + getHabilidadesGuardaRedes(guardaRedes) * 0.05
               + getHabilidadeAvancados(avancado) * 0.6
               + getHabilidadeMedios(medio) * 0.3;
   }

   public double getHabilidadeDefesa(Jogador jog) {
       if(jog instanceof Defesa) return jog.getHabilidade();
       else return jog.getVelocidadeJ() * 0.05
                   + jog.getResistenciaJ()*0.1
                   + jog.getDestrezaJ()*0.15
                   + jog.getImpulsao()*0.15
                   + jog.getJogoCabeca()*0.15
                   + jog.getRemate()*0.05
                   + jog.getCapacidadePasse()*0.1
                   + 30 * 0.25;
   }

   public double getHabilidadeAvancado(Jogador jog) {
       if(jog instanceof Avancado) return jog.getHabilidade();
       else return jog.getVelocidadeJ() * 0.25
               + jog.getResistenciaJ() * 0.05
               + jog.getDestrezaJ() * 0.1
               + jog.getImpulsao() * 0.05
               + jog.getJogoCabeca() * 0.15
               + jog.getRemate() * 0.15
               + jog.getCapacidadePasse() * 0.05
               + 30 * 0.2;
   }

   public double getHabilidadeMedio(Jogador jog) {
       if(jog instanceof Medio) return jog.getHabilidade();
       else return jog.getVelocidadeJ()*0.2
               + jog.getResistenciaJ()*0.2
               + jog.getDestrezaJ()*0.05
               + jog.getImpulsao()*0.05
               + jog.getJogoCabeca()*0.05
               + jog.getRemate()*0.05
               + jog.getCapacidadePasse()*0.2
               + 30 * 0.2;
   }

   public double getHabilidadeGuardaRedes(Jogador jog) {
       return jog.getHabilidade();
   }

   public double getHabilidadeDefesas(List<Integer> defesas) throws JogadorNaoExistente {
      double r = 0;
      for(int num : defesas) {
            Jogador tmp = this.equipa.getJogador(num);
            r += getHabilidadeDefesa(tmp);
      }
      return r;
   }

   public double getHabilidadesGuardaRedes(List<Integer> guardaredes) throws JogadorNaoExistente {
      return getHabilidadeGuardaRedes(this.equipa.getJogador(guardaredes.get(0)));
   }

   public double getHabilidadeAvancados(List<Integer> avancados) throws JogadorNaoExistente {
       double r = 0;
       for(int num : avancados) {
           Jogador tmp = this.equipa.getJogador(num);
           r += getHabilidadeAvancado(tmp);
       }
       return r;
   }

   public double getHabilidadeMedios(List<Integer> medios) throws JogadorNaoExistente {
       double r = 0;
       for(int num : medios) {
           Jogador tmp = this.equipa.getJogador(num);
           r += this.getHabilidadeMedio(tmp);
       }
       return r;
   }

   public abstract ModeloTatico clone();

   public boolean jogaLateral(int num) {

       for(List<Integer> jogs : this.posicoes.values()) {
           int i = 0;
           for(Integer numero : jogs) {
               if(numero == num) return i == 0 || i==jogs.size()-1;
               i++;
           }
       }
       return false;
   }

   public void efetuaTroca(int numSai, int numEntra) {
       for(List<Integer> jogs : this.posicoes.values()) {
          int tmp = jogs.indexOf(numSai);
          if(tmp != -1) jogs.set(tmp,numEntra);
       }
   }

   public void substituicoes(int numJogSai, int numJogEntra) throws SubstituicaoImpossivel, JogadorNaoExistente {
       Jogador jogSai = this.equipa.getJogador(numJogSai);
       Jogador jogEntra = this.equipa.getJogador(numJogEntra);

       if(jogEntra instanceof Lateral) {
           if(!(jogSai instanceof Lateral) && !jogaLateral(numJogSai)) throw new SubstituicaoImpossivel("Não é possivel fazer a substituição.");
       }
       else efetuaTroca(numJogSai,numJogEntra);
   }
}
