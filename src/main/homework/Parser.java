package main.homework;

import java.util.Queue;

public class Parser {

    protected static Node extractTree(Queue<Token> allTokens)  {
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

    private static void populateTree(Queue<Token> tokens, Node parent) {
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
                Node newNode = new Node(token.getOperator());
                parent.addChild(newNode);
                populateTree(tokens, newNode);
            }
            else {
                //add next child
                Node newNode = new Node(token.toString());
                parent.addChild(newNode);
            }
        }
    }

}
