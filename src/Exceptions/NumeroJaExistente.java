package Exceptions;

public class NumeroJaExistente extends Exception {
    public NumeroJaExistente() {
        super();
    }

    public NumeroJaExistente(String msg) {
        super(msg);
    }
}
