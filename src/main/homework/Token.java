package main.homework;


public class Token {

    private Operand.Type operation;
    private String value;

    public Token(Operand.Type operand) {
        operation = operand;
    }

    public Token(String arg) {
        value = arg;
    }

    public Token (char arg) {
        value = String.valueOf(arg);
    }

    public int getSize() {
        return getParam().length();
    }

    public boolean isOperand() {
        return operation != null;
    }

    public Operand.Type getOperation() {
        return operation;
    }

    public String getParam() {
        if (operation != null) {
            return operation.toString();
        } else {
            return value;
        }
    }

}
