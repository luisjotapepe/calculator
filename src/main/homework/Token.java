package main.homework;


public class Token {

    private Operation.Type operation;
    private String param;

    public Token(String param) {
        this.param = param;
    }

    public Token(Operation.Type op) {
        this.operation = op;
    }

    public boolean isBinaryOperand() {
        if (operation == null) {
            throw new IllegalStateException("Token is not operand type");
        }
        return operation != Operation.Type.LET;
    }

    public Operation.Type getOperation() {
        return operation;
    }

    public String getParam() {
        return param;
    }

}
