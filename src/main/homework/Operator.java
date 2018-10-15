package main.homework;

import java.util.EnumSet;

public class Operator {
    public enum Type {
        add, sub, div, mul, let
    }

    public static EnumSet<Operator.Type> binaryOperators() {
        return EnumSet.of(Type.add, Type.sub, Type.mul, Type.div);
    }

    public static EnumSet<Operator.Type> ternaryOperators() {
        return EnumSet.of(Type.let);
    }
}