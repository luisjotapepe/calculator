package main.homework;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private Map<String,Integer> cache = new HashMap<>();
    private Operator.Type operator;
    private String value;
    private Node left;
    private Node center;
    private Node right;

    public Node(Operator.Type operator) {
        this.operator = operator;
    }

    public Node(String value) {
        this.value = value;
    }

    public void addChild(Node child) {
        if (left == null) {
            left = child;
        }
        else if (center == null) {
            center = child;
        }
        else if (right == null) {
            right = child;
        }
        else {
            throw new IllegalStateException("Failed to add child node");
        }
    }

    public Node getLeftNode() {
        return this.left;
    }

    public Node getCenterNode() {
        return this.center;
    }

    public Node getRightNode() {
        return this.right;
    }

    public boolean isBinaryOperator() {
        return Operator.binary.contains(operator);
    }

    public boolean isTernaryOperator() {
        return Operator.ternary.contains(operator);
    }

    public boolean isOperatorType() {
        return operator != null;
    }

    public Operator.Type getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public Map<String,Integer> getCache() {
        return cache;
    }

    public void addToCache(String key, String value) {
        Integer intValue = Integer.parseInt(value);
        cache.put(key, intValue);
    }
    public void copyIntoCache(Map<String, Integer> cache) {
        this.cache.putAll(cache);
    }

}
