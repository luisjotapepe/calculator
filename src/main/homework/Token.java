package main.homework;


public class Token {

    private Operand.Type operation;
    private String value;
    private int index;

    public Token(Operand.Type operand, int index) {
        operation = operand;
        this.index = index;
    }

    public Token(String arg, int index) {
        this.value = arg;
        this.index = index;
    }

    public Token (char arg, int index) {
        this.value = String.valueOf(arg);
        this.index = index;
    }

    public int getSize() {
        return toString().length();
    }

    public int getIndex() {
        return index;
    }

    public boolean isOperand() {
        return operation != null;
    }

    public Operand.Type getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        if (operation != null) {
            return operation.toString();
        } else {
            return value;
        }
    }

}
