package Exceptions;

public class JogadorNaoExistente extends Exception {
    public JogadorNaoExistente() {
        super();
    }
    public JogadorNaoExistente(String msg) {
        super(msg);
    }
}
