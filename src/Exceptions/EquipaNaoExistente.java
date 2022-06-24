package Exceptions;


public class EquipaNaoExistente extends Exception{
    public EquipaNaoExistente() {
        super();
    }
    public EquipaNaoExistente(String msg) {
        super(msg);
    }
}
