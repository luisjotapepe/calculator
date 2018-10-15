package main.homework;


public class Token {

    private Operator.Type operator;
    private String value;
    private int index;

    public Token(Operator.Type operator, int index) {
        this.operator = operator;
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

    public int getIndex() {
        return index;
    }

    public boolean isOperator() {
        return operator != null;
    }

    public Operator.Type getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        if (operator != null) {
            return operator.toString();
        } else {
            return value;
        }
    }

}
