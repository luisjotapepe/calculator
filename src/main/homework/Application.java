package main.homework;


public class Application {

    private static final Calculator calculator = new Calculator();

    public static void main(String[] args) {

        String expression = args[0];
        if (expression == null || expression == "") {
            System.out.println("No expression to calculate");
        }
        else {
            runCalculation(expression);
        }

    }

    private static void runCalculation(String expression) {
        System.out.println("Expression to calculate: " + expression);
        String output;
        try {
            int result = calculator.calculate(expression);
            output = String.valueOf(result);
        } catch (IllegalArgumentException ex) {
            output = "****** " + ex.getMessage() + " ******";
        }

        System.out.println("Result: " + output);
    }

}

