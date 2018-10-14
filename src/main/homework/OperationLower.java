package main.homework;

import java.util.EnumSet;

public class OperationLower {
    public enum Type {
        add, sub, div, mul, let
    }

    public static EnumSet<OperationLower.Type> binaryOperators() {
        return EnumSet.of(Type.add, Type.sub, Type.mul, Type.div);
    }

    public static EnumSet<OperationLower.Type> ternaryOperators() {
        return EnumSet.of(Type.let);
    }
}