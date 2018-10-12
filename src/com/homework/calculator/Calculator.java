package com.homework.calculator;

public class Calculator {

    private static int index = 0;
    private static final char DELIMITER = ',';
    private static final char LPARENT = '(';
    private static final char RPARENT = ')';

    public int process(String equation) {
        reset();
        return calculate(equation);
    }

    private int calculate (String equation) {
        equation = "mul(2,div(sub(100,-100),0))";
        int result = 0;
        String operand = "";
        boolean delimiterFound = false;
        String param1 = "";
        String param2 = "";

        Token token = null;

        while (index < equation.length()) {
            char symbol = equation.charAt(index);

            if (LPARENT == symbol) {
                token = new Token(operand);
            }
            else if (RPARENT == symbol) {
                //compute
                token.setScndOp(Integer.parseInt(param2));
                return token.calculate();
            }
            else if (DELIMITER == symbol) {
                if (delimiterFound) {
                    throw new IllegalArgumentException("Found multiple delimiters");
                }
                else if (token == null) {
                    throw new IllegalArgumentException("Delimiter found with no operand set");
                }
                else {
                    //set first param
                    token.setFirstOp(Integer.parseInt(param1));
                    delimiterFound = true;
                }
            }
            else if (isCharacter(symbol)) {
                if (token == null) {
                    operand += symbol;
                }
                else {
                    int result2 = calculate(equation);

                    if (!delimiterFound) {
                        param1 = Integer.toString(result2);
                    }
                    else {
                        param2 = Integer.toString(result2);
                    }
                }
            }
            else if (isNumeric(symbol)) {
                if (!delimiterFound) {
                    param1 += String.valueOf(symbol);
                }
                else {
                    param2 += String.valueOf(symbol);
                }
            }
             else if (isAnythingOtherThanWhiteSpace(symbol)) {
                throw new IllegalArgumentException("Invalid character found in request");
            }

            index++;
        }


        return result;
    }

    private boolean isNumeric(char symbol) {
        return Character.isDigit(symbol);
    }

    private boolean isCharacter(char symbol) {
        return Character.isLetter(symbol);
    }

    private boolean isAnythingOtherThanWhiteSpace(char symbol) {
        return !Character.isWhitespace(symbol);
    }

    private void reset() {
        index = 0;
    }

}
