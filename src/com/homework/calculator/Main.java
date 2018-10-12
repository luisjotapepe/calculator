package com.homework.calculator;


public class Main {


    private static Calculator calculator = new Calculator();

    public static void main(String[] args) {

        try {
            int result = calculator.process("add(100,add(add(100,100),100))");
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}

