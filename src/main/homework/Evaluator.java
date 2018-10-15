package main.homework;

import java.util.Map;

public class Evaluator {

    protected static String evaluator(Node node) {
        String stringResult = null;
        if (node.isOperatorType()) {
            String leftSideStr = evaluator(node.getLeftNode());

            Node centerNode = node.getCenterNode();
            centerNode.copyIntoCache(node.getCache());
            String centerSideStr = evaluator(node.getCenterNode());

            if (node.isBinaryOperator()) {
                Integer dLeft = getValueFromString(leftSideStr, node.getCache());
                Integer dCenter = getValueFromString(centerSideStr, node.getCache());

                Integer result = calculateBinary(node, dLeft, dCenter);
                stringResult = String.valueOf(result);
            }
            else if (node.isTernaryOperator()) {
                validateNode(node);
                node.addToCache(leftSideStr, centerSideStr);

                Node rightNode = node.getRightNode();
                rightNode.copyIntoCache(node.getCache());
                stringResult = evaluator(rightNode);
            }
        }
        else {
            stringResult = node.getValue();
        }

        if (stringResult == null) {
            throw new IllegalStateException("Invalid state!");
        }

        return stringResult;
    }

    private static Integer getValueFromString(String stringValue, Map<String,Integer> cache) {
        Integer value;
        if (isNumeric(stringValue)) {
            value = Integer.parseInt(stringValue);
        }
        else {
            value = cache.get(stringValue);
            if (value == null) {
                throw new IllegalArgumentException("Variable name not found in expression: " + stringValue);
            }
        }
        return value;
    }

    private static Integer calculateBinary(Node node, Integer iLeft, Integer iCenter) {
        Integer result;
        switch(node.getOperator()) {
            case add:
                result = iLeft + iCenter;
                break;
            case sub:
                result = iLeft - iCenter;
                break;
            case mul:
                result = iLeft * iCenter;
                break;
            case div:
                result = calculateDiv(iLeft, iCenter);
                break;
            default:
                throw new IllegalArgumentException("Invalid data to calculate value. Please check expression.");
        }
        return result;
    }

    private static void validateNode(Node node) {
        String lParam = node.getLeftNode().getValue();
        if (lParam != null && isInvalidVariableName(lParam)) {
            throw new IllegalStateException("'let' operation has invalid parameter: " + lParam);
        }

        String rParam = node.getCenterNode().getValue();
        if (rParam != null && !isNumeric(rParam)) {
            throw new IllegalStateException("'let' operation has invalid parameter: " + rParam);
        }
    }

    private static Integer calculateDiv(Integer iLeft, Integer iCenter) {
        if (iCenter == 0) {
            throw new IllegalArgumentException("Divisor can't be zero");
        }
        double dLeft = new Double(iLeft);
        double dCenter = new Double(iCenter);
        Double dResult = dLeft / dCenter;
        if (dResult < 0 ) {
            dResult = Math.ceil(dResult);
        } else {
            dResult = Math.floor(dResult);
        }
        return dResult.intValue();
    }

    private static boolean isInvalidVariableName(String name) {
        boolean isValid = false;
        if (name == null
                || name == ""
                || !(name.matches("^[a-zA-Z\\_][a-zA-Z0-9\\_]*$"))
                || Operator.getType(name) != null) {

            isValid = true;
        }
        return isValid;
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
