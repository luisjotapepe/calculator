package main.homework;


public class Token {

    private OperationLower.Type operation;
    private String value;

    public Token(OperationLower.Type operand) {
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

    public boolean isBinaryOperand() {
        return operation == OperationLower.Type.add
                || operation == OperationLower.Type.sub
                || operation == OperationLower.Type.mul
                || operation == OperationLower.Type.div;
    }

    public boolean isTernaryOperand() {
        return operation == OperationLower.Type.let;
    }

    public OperationLower.Type getOperation() {
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
