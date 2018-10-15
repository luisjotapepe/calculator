package main.homework;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private Map<String,Integer> cache = new HashMap<>();
    private Operand.Type operand;
    private String value;
    private Node parent;
    private Node left;
    private Node center;
    private Node right;
    private int index;

    public Node(Operand.Type operand, Node parent, int index) {
        this.parent = parent;
        this.operand = operand;
        this.index = index;
    }

    public Node(String value, Node parent, int index) {
        this.value = value;
        this.parent = parent;
        this.index = index;
    }

    public Node(Operand.Type operand, int index) {
        this.operand = operand;
        this.index = index;
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

    public boolean isBinary() {
        return Operand.binaryOperators(). contains(operand);
    }

    public boolean isTernary() {
        return Operand.ternaryOperators().contains(operand);
    }

    public boolean isOperand() {
        return operand != null;
    }

    public Operand.Type getOperand() {
        return operand;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
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
