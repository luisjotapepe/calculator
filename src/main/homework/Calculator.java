package main.homework;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Calculator {

    private static final char DELIMITER = ',';
    private static final char LPARENT = '(';
    private static final char RPARENT = ')';

    public int calculate(String expression) {

        //tokenizer
        Queue<Token> tokens = tokenize(expression);
        //parser
        Node root = extractTree(tokens);
        //evaluator
        String strResult = evaluator(root);

        return Integer.parseInt(strResult);
    }

    private Queue<Token> tokenize(String expression) {
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
                        Operator.Type type = getOperatorType(trimmedValue);
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

    private void validateValue(String value, int index) {
        if (value == null) {
            throw new IllegalStateException("Error parsing value at index: " + index);
        }
        if (value.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalStateException("Invalid white space found at index " + index);
        }
    }

    private Node extractTree(Queue<Token> allTokens)  {
        Node root;
        Token token = allTokens.remove();
        if (token.isOperator()) {
            root = new Node(token.getOperator());
        } else {
            throw new IllegalStateException("First token is not operator type. Invalid syntax.!" + token.getIndex());
        }

        populateTree(allTokens, root);
        return root;
    }

    private String evaluator(Node node) {
        String stringResult = null;
        if (node.isOperatorType()) {
            String leftSideStr = evaluator(node.getLeftNode());

            Node centerNode = node.getCenterNode();
            centerNode.copyIntoCache(node.getCache());
            String centerSideStr = evaluator(node.getCenterNode());

            if (node.isBinaryOperator()) {
                Integer dLeft = getValueFromString(leftSideStr, node.getCache());
                Integer dCenter = getValueFromString(centerSideStr, node.getCache());

                Integer result = calculateBinary(node, dLeft, dCenter);
                stringResult = String.valueOf(result);
            }
            else if (node.isTernaryOperator()) {
                validateNode(node);
                node.addToCache(leftSideStr, centerSideStr);

                Node rightNode = node.getRightNode();
                rightNode.copyIntoCache(node.getCache());
                stringResult = evaluator(rightNode);
            }
        }
        else {
            stringResult = node.getValue();
        }

        if (stringResult == null) {
            throw new IllegalStateException("Invalid state!");
        }

        return stringResult;
    }

    private void populateTree(Queue<Token> tokens, Node parent) {
        while (!tokens.isEmpty()) {
            Token token = tokens.remove();
            String param = token.toString();

            if ("(".equals(param) || ",".equals(param)) {
                //skip
            }
            else if (")".equals(param)) {
                break;
            }
            else if (token.isOperator()) {
                //add next child
                Node newNode = new Node(token.getOperator(), parent);
                parent.addChild(newNode);
                populateTree(tokens, newNode);
            }
            else {
                //add next child
                Node newNode = new Node(token.toString(), parent);
                parent.addChild(newNode);
            }
        }
    }

    private void validateNode(Node node) {
        String lParam = node.getLeftNode().getValue();
        if (lParam != null && isInvalidVariableName(lParam)) {
            throw new IllegalStateException("'let' operation has invalid parameter: " + lParam);
        }

        String rParam = node.getCenterNode().getValue();
        if (rParam != null && !isNumeric(rParam)) {
            throw new IllegalStateException("'let' operation has invalid parameter: " + rParam);
        }
    }

    private boolean isInvalidVariableName(String name) {
        boolean isValid = false;
        if (name == null
                || name == ""
                || !(name.matches("^[a-zA-Z\\_][a-zA-Z0-9\\_]*$"))
                || getOperatorType(name) != null) {

            isValid = true;
        }
        return isValid;
    }

    private Operator.Type getOperatorType(String arg) {
        Operator.Type type;
        switch(arg) {
            case "add":
                 type = Operator.Type.add;
                 break;
            case "sub":
                type = Operator.Type.sub;
                break;
            case "mul":
                type = Operator.Type.mul;
                break;
            case "div":
                type = Operator.Type.div;
                break;
            case "let":
                type = Operator.Type.let;
                break;
            default:
                type = null;
        }
        return type;
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private Integer calculateBinary(Node node, Integer iLeft, Integer iCenter) {
        Integer result;
        switch(node.getOperator()) {
            case add:
                result = iLeft + iCenter;
                break;
            case sub:
                result = iLeft - iCenter;
                break;
            case mul:
                result = iLeft * iCenter;
                break;
            case div:
                result = calculateDiv(iLeft, iCenter);
                break;
            default:
                throw new IllegalArgumentException("Invalid data to calculate value. Please check expression.");
        }
        return result;
    }

    private Integer calculateDiv(Integer iLeft, Integer iCenter) {
        if (iCenter == 0) {
            throw new IllegalArgumentException("Divisor can't be zero");
        }
        double dLeft = new Double(iLeft);
        double dCenter = new Double(iCenter);
        Double dResult = dLeft / dCenter;
        if (dResult < 0 ) {
            dResult = Math.ceil(dResult);
        } else {
            dResult = Math.floor(dResult);
        }
        return dResult.intValue();
    }

    private Integer getValueFromString(String stringValue, Map<String,Integer> cache) {
        Integer value;
        if (isNumeric(stringValue)) {
            value = Integer.parseInt(stringValue);
        }
        else {
            value = cache.get(stringValue);
            if (value == null) {
                throw new IllegalArgumentException("Variable name not found in expression: " + stringValue);
            }
        }
        return value;
    }

    private String getValue(String expression, final int index) {
        int j = index;
        for (; index < expression.length() ;) {
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
