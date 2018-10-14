package main.homework;


import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Calculator calculator = new Calculator();
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        String output;
        try {
            output = String.valueOf(calculator.calculate(String.valueOf(args)));
        } catch (IllegalStateException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            output = ex.getMessage();
        }

        System.out.println(output);

    }

}

