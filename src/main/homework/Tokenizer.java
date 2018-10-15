package main.homework;

import java.util.LinkedList;
import java.util.Queue;

public class Tokenizer {

    private static final char DELIMITER = ',';
    private static final char LPARENT = '(';
    private static final char RPARENT = ')';

    protected static Queue<Token> tokenize(String expression) {
        if (expression == null || expression.trim() == "") {
            throw new IllegalArgumentException("No expression to calculate");
        }

        final int EXPRESSION_MIN_LENGTH = 8;
        if (expression.trim().length() < EXPRESSION_MIN_LENGTH) {
            throw new IllegalArgumentException("Expression length is less than expected (" + EXPRESSION_MIN_LENGTH + ")");
        }

        String trimmedExpression = expression.trim();

        int index = 0;
        Queue<Token> allTokens = new LinkedList<>();

        while (index < trimmedExpression.length()) {
            char symbol = trimmedExpression.charAt(index);
            Token token;

            switch (symbol) {
                case LPARENT:
                case RPARENT:
                case DELIMITER:
                    token = new Token(symbol, index);
                    allTokens.add(token);
                    index++;
                    break;
                default:
                    String value = getValue(trimmedExpression, index);
                    String trimmedValue = value.trim();
                    if (trimmedValue.length() != 0) {
                        validateValue(trimmedValue, index);
                        Operator.Type type = Operator.getType(trimmedValue);
                        if (type != null) {
                            token = new Token(type, index);
                        }
                        else {
                            token = new Token(trimmedValue, index);
                        }
                        allTokens.add(token);
                    }
                    index += value.length();
                    break;
            }
        }
        return allTokens;
    }

    private static void validateValue(String value, int index) {
        if (value == null) {
            throw new IllegalStateException("Error parsing value at index: " + index);
        }
        if (value.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalStateException("Invalid white space found at index " + index);
        }
    }

    private static String getValue(String expression, final int index) {
        int j = index;
        while (index < expression.length()) {
            if (expression.charAt(j) == LPARENT
                    || expression.charAt(j) == RPARENT
                    || expression.charAt(j) == DELIMITER) {
                return expression.substring(index, j);
            } else {
                j++;
            }
        }
        return expression.substring(index, j);
    }


}
