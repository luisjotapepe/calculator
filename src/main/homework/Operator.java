package main.homework;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Operator {
    public enum Type {
        add, sub, div, mul, let
    }

    protected static Set<Type> binary = new HashSet<>(Arrays.asList(Type.add, Type.sub, Type.mul, Type.div));
    protected static Set<Type> ternary = new HashSet<>(Arrays.asList(Type.let));

}