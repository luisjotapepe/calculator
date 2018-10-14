package main.homework;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Calculator {

    private static final char DELIMITER = ',';
    private static final char LPARENT = '(';
    private static final char RPARENT = ')';

    private static final Stack<String> parenthesis = new Stack<>();
    private static boolean balanced = true;

    public int calculate(String equation) {
        //tokenizer
        Queue<Token> tokens = tokenizer(equation);
        //parser
        Node root = parser(tokens);
        //evaluator
        String strResult = evaluator(root);

        return Integer.parseInt(strResult);
    }

    private Queue<Token> tokenizer(String equation) {
        int index = 0;
        Queue<Token> allTokens = new LinkedList<>();

        while (index < equation.length()) {
            char symbol = equation.charAt(index);
            Token token;

            switch (symbol) {
                case LPARENT:
                case RPARENT:
                case DELIMITER:
                    token = new Token(symbol);
                    allTokens.add(token);
                    index++;
                    break;
                default:
                    String value = getValue(equation, index);
                    Operand.Type type = getOperandType(value);
                    if (type != null) {
                        token = new Token(type);
                    }
                    else {
                        token = new Token(value);
                    }
                    allTokens.add(token);
                    index += token.getSize();
                    break;
            }
        }
        return allTokens;
    }

    private Node parser(Queue<Token> allTokens) {
        Node root;
        Token token = allTokens.remove();
        if (token.isOperand()) {
            root = new Node(token.getParam(), null, token.getOperation());
        } else {
            throw new IllegalArgumentException("Illegal state!");
        }

        populateTree(allTokens, root);
        return root;
    }

    private String evaluator(Node node) {
        String stringResult = null;
        if (node.isOperand()) {
            String leftSideStr = evaluator(node.getLeftNode());

            Node centerNode = node.getCenterNode();
            centerNode.copyIntoCache(node.getCache());
            String centerSideStr = evaluator(node.getCenterNode());

            if (node.isBinary()) {
                Integer dLeft = getValueFromString(leftSideStr, node.getCache());
                Integer dCenter = getValueFromString(centerSideStr, node.getCache());

                Integer result = calculateBinary(node, dLeft, dCenter);
                stringResult = String.valueOf(result);
            }
            else if (node.isTernary()) {
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

    private Operand.Type getOperandType(String arg) {
        Operand.Type type;
        switch(arg) {
            case "add":
                 type = Operand.Type.add;
                 break;
            case "sub":
                type = Operand.Type.sub;
                break;
            case "mul":
                type = Operand.Type.mul;
                break;
            case "div":
                type = Operand.Type.div;
                break;
            case "let":
                type = Operand.Type.let;
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
        switch(node.getOperand()) {
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
                throw new IllegalArgumentException();
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
            //todo: get value from cache
            value = cache.get(stringValue);
            if (value == null) {
                throw new IllegalArgumentException();
            }
        }
        return value;
    }

    private String getValue(String eq, final int i) {
        int j = i;
        for (; i < eq.length();) {
            if (eq.charAt(j) == LPARENT
                    || eq.charAt(j) == RPARENT
                    || eq.charAt(j) == DELIMITER) {
                return eq.substring(i, j);
            } else {
                j++;
            }
        }
        return eq.substring(i, j);
    }

    private void populateTree(Queue<Token> tokens, Node parent) {
        while (!tokens.isEmpty()) {
            Token token = tokens.remove();
            String param = token.getParam();
            if ("(".equals(param)) {
                //skip
            }
            else if (")".equals(param)) {
                break;
            }
            else if (",".equals(param)) {
                //skip
            }
            else if (token.isOperand()) {
                //add node
                Node newNode = new Node(token.getParam(), parent, token.getOperation());
                parent.addChild(newNode);
                //go deeper
                populateTree(tokens, newNode);
            }
            else {
                //add node
                Node newNode = new Node(token.getParam(), parent, null);
                parent.addChild(newNode);
            }
        }
    }

}
