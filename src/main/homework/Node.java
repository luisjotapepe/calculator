package main.homework;

import java.util.HashMap;
import java.util.Map;

public class Node<T> {
    private Node<T> parent;

    Map<String,Integer> cache;
    String value;
    Node left;
    Node center;
    Node right;
    private OperationLower.Type operand;

    public Node(String value, Node<T> parent, OperationLower.Type operand) {
        this.value = value;
        this.parent = parent;
        this.operand = operand;
        this.cache = new HashMap<>();
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
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
            //bad satate
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
        return OperationLower.binaryOperators(). contains(operand);
    }

    public boolean isTernary() {
        return OperationLower.ternaryOperators().contains(operand);
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return (this.left == null) && (this.center == null) && (this.right == null);
    }

    public boolean isOperand() {
        return operand != null;
    }

    public OperationLower.Type getOperand() {
        return operand;
    }

    public String getValue() {
        return value;
    }

    public void addToCache(String key, String value) {
        Integer intValue = Integer.parseInt(value);
        cache.put(key, intValue);
    }
    public void copyIntoCache(Map<String, Integer> cache) {
        this.cache.putAll(cache);
    }

    public Map<String,Integer> getCache() {
        return cache;
    }

}
