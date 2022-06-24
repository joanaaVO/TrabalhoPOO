package Exceptions;

public class JogadorSemEquipaNaoExistente extends Exception {
    public JogadorSemEquipaNaoExistente() {
        super();
    }

    public JogadorSemEquipaNaoExistente(String msg) {
        super(msg);
    }
}
