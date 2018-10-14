package main.homework;

import java.util.EnumSet;

public class Operand {
    public enum Type {
        add, sub, div, mul, let
    }

    public static EnumSet<Operand.Type> binaryOperators() {
        return EnumSet.of(Type.add, Type.sub, Type.mul, Type.div);
    }

    public static EnumSet<Operand.Type> ternaryOperators() {
        return EnumSet.of(Type.let);
    }
}