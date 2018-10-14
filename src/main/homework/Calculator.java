package main.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculator {

    private static int index = 0;
    private static final char DELIMITER = ',';
    private static final char LPARENT = '(';
    private static final char RPARENT = ')';

    private static final String ADD = "add";
    private static final String SUB = "sub";
    private static final String MUL = "mul";
    private static final String DIV = "div";
    private static final String LET = "let";

    private static final Stack<String> parenthesis = new Stack<>();
    private static boolean balanced = true;

    private static final Stack<Token> allTokens = new Stack<>();

    public int process(String equation) {
        reset();
        
        int result = calculate(equation);

        if (index != equation.length()-1) {
            throw new IllegalStateException("Invalid equation string");
        }

        return result;
    }

    private int calculate (String equation) {
        Integer result = null;
        String operand = "";
        String param = "";
        boolean frstDelimiterFound = false;
        boolean scndDelimiterFound = false;

        List<Token> localTokens = new ArrayList<>();

        if (equation == null || equation.isEmpty()) {
            throw new IllegalArgumentException("No equation");
        }

        while (index < equation.length()) {
            char symbol = equation.charAt(index);

            if (LPARENT == symbol) {

                parenthesis.push(String.valueOf(symbol));

                if (!localTokens.isEmpty()) {
                    //tokens must be empty by here
                }
                localTokens.add(new Token(getValidOperandType(operand)));
            }
            else if (RPARENT == symbol) {
                if (parenthesis.empty()) {
                    balanced = false;
                } else {
                    parenthesis.pop();
                }

                if (localTokens.isEmpty()) {
                    //todo: invalid format
                }

                if (isBinaryOperand(localTokens)) {
                    if (localTokens.size() != 2) {//could be three if we look ahead
                        //todo: invalid format
                    }
                    else {
                        localTokens.add(new Token(param));
                        allTokens.addAll(localTokens);
                        //todo: calculate
                        return calculateResult(localTokens);
                    }
                }
                else if (!isBinaryOperand(localTokens)) {
                    if (localTokens.size() != 3) {
                        //todo: invalid format
                    }
                    else {
                        localTokens.add(new Token(param));
                        //todo: calculate
                    }
                }
            }
            else if (DELIMITER == symbol) {
                if (localTokens.isEmpty()) {
                    //todo: invalid format
                }

                if (isBinaryOperand(localTokens)) {
                    if (localTokens.size() != 1) {
                        //todo: invalid format
                    }
                    else {
                        localTokens.add(new Token(param));
                        param = "";
                    }
                }
                else {
                    if (!frstDelimiterFound) {
                        frstDelimiterFound = true;
                        //validate param syntax
                        localTokens.add(new Token(param));
                    }
                    else if (!scndDelimiterFound) {
                        if (localTokens.size() != 2) {
                            //todo: invalid format
                        }
                        scndDelimiterFound = true;
                        localTokens.add(new Token(param));
                    }
                    else {
                        //todo: throw too many delimiters
                    }
                }
            }
            else if (isNumeric(symbol)) {
                if (localTokens.isEmpty()) {
                    //todo: invalid format
                }

                if (isBinaryOperand(localTokens)) {
                    param += String.valueOf(symbol);
                }
                else {
                    if (param == "" && isNumeric(symbol)) {
                        //todo: invalid data
                    }
                    //todo: complete this part
                }
            }
            else if (isValidNonNumeric(symbol)) {
                if (localTokens.isEmpty()) {
                    if (isOperandKeyword(equation)) {
                        operand = equation.substring(index, index+3);
                        index += 2;
                    }
                    else {
                        //todo: invalid data
                    }
                }
                else if (isBinaryOperand(localTokens) && isOperandKeyword(equation)) {
                    //todo: ************* calculate!!!! *************
                    param = String.valueOf(calculate(equation));
                } else {
                    param += symbol;
                }
            }
            else if (isWhiteSpace(symbol)) {
                //skip
            } else {
                throw new IllegalArgumentException("Invalid character found in request");
            }

            index++;
        }

        if (result == null) {
            throw new IllegalStateException("Something went wrong!");
        }

        if (!balanced || !parenthesis.isEmpty()) {
            throw new IllegalStateException("Imbalanced parenthesis in equation");
        }

        return result;
    }

    private boolean isNumeric(char symbol) {
        return Character.isDigit(symbol);
    }

    private boolean isValidNonNumeric(char symbol) {
        return Character.isLetter(symbol) || '_' == symbol || '-' == symbol;
    }

    private boolean isWhiteSpace(char symbol) {
        return Character.isWhitespace(symbol);
    }

    private void reset() {
        index = 0;
    }

    private Operation.Type getValidOperandType(String operand) {
        Operation.Type op;
        switch(operand) {
            case ADD:
                op = Operation.Type.ADD;
                break;
            case SUB:
                op = Operation.Type.SUB;
                break;
            case MUL:
                op = Operation.Type.MUL;
                break;
            case DIV:
                op = Operation.Type.DIV;
                break;
            case LET:
                op = Operation.Type.LET;
                break;
            default:
                throw new IllegalArgumentException("Invalid operand type found: " + operand);
        }
        return op;
    }

    private boolean isOperandKeyword(String equation) {
        String tempOperand;

        try {
            tempOperand = equation.substring(index, index + 4);
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }

        return tempOperand.matches(ADD + "\\(")
                || tempOperand.matches(SUB + "\\(")
                || tempOperand.matches(MUL + "\\(")
                || tempOperand.matches(DIV + "\\(")
                || tempOperand.matches(LET + "\\(");
    }

    private boolean isBinaryOperand(List<Token> tokens) {
        return tokens.get(0).isBinaryOperand();
    }

    private int calculateResult(List<Token> tokens) {
        int result = 0;
        if (isBinaryOperand(tokens)) {
            Operation.Type operand = tokens.get(0).getOperation();

            String firstOperand = tokens.get(1).getParam();
            String secondOperand = tokens.get(2).getParam();

            switch (operand) {
                case ADD:
                    result = Integer.parseInt(firstOperand) + Integer.parseInt(secondOperand);
                    break;
                case SUB:
                    result = Integer.parseInt(firstOperand) - Integer.parseInt(secondOperand);
                    break;
                case MUL:
                    result = Integer.parseInt(firstOperand) * Integer.parseInt(secondOperand);
                    break;
                case DIV:
                    if (Integer.parseInt(secondOperand) == 0) {
                        throw new IllegalArgumentException("Division by zero is invalid");
                    }
                    result = Integer.parseInt(firstOperand) / Integer.parseInt(secondOperand);
                    break;
                default:
                    throw new IllegalStateException("Invalid data for equation");

            }
        } else {

        }
        return result;
    }

}
