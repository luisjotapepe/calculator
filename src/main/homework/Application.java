package main.homework;


import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private static final Calculator calculator = new Calculator();
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {

        String expression = args[0];
        if (expression == null || expression == "") {
            LOGGER.log(Level.SEVERE, "No expression to calculate");
        }
        else {
            runCalculation(expression);
        }

    }

    private static void runCalculation(String expression) {

        LOGGER.log(Level.INFO, "Expression to calculate: " + expression);

        String output;
        try {
            int result = calculator.calculate(expression);
            output = String.valueOf(result);
        } catch (IllegalArgumentException ex) {
            output = "****** " + ex.getMessage() + " ******";
        }

        LOGGER.log(Level.INFO, "Result: " + output);
    }

}

