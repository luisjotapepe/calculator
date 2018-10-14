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
        //lexer
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
                    OperationLower.Type type = getOperandType(value);
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

    private OperationLower.Type getOperandType(String arg) {
        OperationLower.Type type;
        switch(arg) {
            case "add":
                 type = OperationLower.Type.add;
                 break;
            case "sub":
                type = OperationLower.Type.sub;
                break;
            case "mul":
                type = OperationLower.Type.mul;
                break;
            case "div":
                type = OperationLower.Type.div;
                break;
            case "let":
                type = OperationLower.Type.let;
                break;
            default:
                type = null;
        }
        return type;
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private String evaluator(Node node) {
        String stringResult = null;
        if (node.isOperand()) {
            String leftSideStr = evaluator(node.getLeftNode());
            String centerSideStr = evaluator(node.getCenterNode());

            if (node.isBinary()) {
                Integer dLeft = getValueFromString(leftSideStr, node.getCache());
                Integer dCenter = getValueFromString(centerSideStr, node.getCache());

                Integer result = calculateBinary(node, dLeft, dCenter);
                stringResult = String.valueOf(result);
            }
            else if (node.isTernary()) {
                node.addToCache(node.getLeftNode().getValue(), centerSideStr);
                Node rightNode = node.getRightNode();
                rightNode.copyIntoCache(node.getCache());
                stringResult = evaluator(node.getRightNode());
            }
        }
        else {
            stringResult = node.getValue();
        }

        if (stringResult == null) {
            //todo: throw exception
        }

        return stringResult;
    }

    private Integer calculateBinary(Node node, Integer dLeft, Integer dCenter) {
        Integer result;
        switch(node.getOperand()) {
            case add:
                result = dLeft + dCenter;
                break;
            case sub:
                result = dLeft - dCenter;
                break;
            case mul:
                result = dLeft * dCenter;
                break;
            case div:
                if (dCenter == 0) {
                    throw new IllegalArgumentException();
                }
                result = dLeft / dCenter;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return result;
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
