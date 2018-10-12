package com.homework.calculator;


public class Token implements MainType {

    public enum Operation {
        ADD, SUB, DIV, MUL
    }

    private Operation operation;
    private int param1;
    private int param2;

    public Token(String operand) {
        switch(operand) {
            case "add":
                this.operation = Operation.ADD;
                break;
            case "sub":
                this.operation = Operation.SUB;
                break;
            case "mul":
                this.operation = Operation.MUL;
                break;
            case "div":
                this.operation = Operation.DIV;
                break;
            default:
                throw new IllegalArgumentException("Invalid operand found: " + operand);
        }
    }

    @Override
    public void setFirstOp(int op1) {
        param1 = op1;
    }

    @Override
    public void setScndOp(int op2) {
        param2 = op2;
    }

    @Override
    public int calculate() {
        int result = 0;
        switch(operation) {
            case ADD:
                result = param1 + param2;
                break;
            case SUB:
                result = param1 - param2;
                break;
            case MUL:
                result = param1 * param2;
                break;
            case DIV:
                if (param2 == 0) {
                    throw new IllegalStateException("Divisor cannot be 0");
                }
                result = param1 / param2;
                break;
            default:
                //throw excep?
        }
        return result;
    }
}
