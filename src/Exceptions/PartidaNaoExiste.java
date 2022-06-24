package Exceptions;

import Model.Partida;

public class PartidaNaoExiste extends Exception {
    public PartidaNaoExiste() {
        super();
    }

    public PartidaNaoExiste(String msg) {
        super(msg);
    }
}
