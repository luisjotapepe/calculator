package main.homework;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Calculator {

    public int calculate(String expression) {
        //tokenizer
        Queue<Token> tokens = Tokenizer.tokenize(expression);
        //parser
        Node root = Parser.extractTree(tokens);
        //evaluator
        String strResult = Evaluator.evaluator(root);

        return Integer.parseInt(strResult);
    }

}
