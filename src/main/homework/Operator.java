package main.homework;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Operator {
    public enum Type {
        add, sub, div, mul, let
    }

    protected static Operator.Type getType(String arg) {
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

    protected static Set<Type> binary = new HashSet<>(Arrays.asList(Type.add, Type.sub, Type.mul, Type.div));
    protected static Set<Type> ternary = new HashSet<>(Arrays.asList(Type.let));

}