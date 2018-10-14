package test.homework;

import main.homework.Calculator;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    /**
     * Testing Add operation
     */

    @Test
    public void add() {
        int result = calculator.calculate("add(2,2)");
        Assert.assertEquals(4, result);
    }

    @Test
    public void add_withNegativeAugend() {
        int result = calculator.calculate("add(-20,3)");
        Assert.assertEquals(-17, result);
    }

    @Test
    public void add_withNegativeAddend() {
        int result = calculator.calculate("add(2,-5)");
        Assert.assertEquals(-3, result);
    }

    @Test
    public void add_withNegativeAugendAndAddend() {
        int result = calculator.calculate("add(-12,-2)");
        Assert.assertEquals(-14, result);
    }

    @Test
    public void add_withLargestInputs() {
        int result = calculator.calculate("add(32768,32768)");
        Assert.assertEquals(65536, result);
    }

    @Test
    public void add_withLargestNegaviteInputs() {
        int result = calculator.calculate("add(-32768,-32768)");
        Assert.assertEquals(-65536, result);
    }

    @Test
    public void add_singleNestedOperand() {
        int result = calculator.calculate("add(add(40,30),400)");
        Assert.assertEquals(470, result);
    }

    @Test
    public void add_doubleNestedOperands() {
        int result = calculator.calculate("add(add(40,30),add(500,10))");
        Assert.assertEquals(580, result);
    }

    @Test
    public void add_multipleNestedOperands() {
        int result = calculator.calculate("add(add(40,add(30,55)),add(500,10))");
        Assert.assertEquals(635, result);
    }

    /**
     * Testing Sub operation
     */

    @Test
    public void sub() {
        int result = calculator.calculate("sub(20,10)");
        Assert.assertEquals(10, result);
    }

    @Test
    public void sub_withNegativeMinuend() {
        int result = calculator.calculate("sub(-240,20)");
        Assert.assertEquals(-260, result);
    }

    @Test
    public void sub_withNegativeSubtrahend() {
        int result = calculator.calculate("sub(111,-30000)");
        Assert.assertEquals(30111, result);
    }

    @Test
    public void sub_withNegativeMinuendSubtrahend() {
        int result = calculator.calculate("sub(-240,-10)");
        Assert.assertEquals(-230, result);
    }

    @Test
    public void sub_withLargestInputs() {
        int result = calculator.calculate("sub(32768,32768)");
        Assert.assertEquals(0, result);
    }

    @Test
    public void sub_withLargestNegaviteInputs() {
        int result = calculator.calculate("sub(-32768,-32768)");
        Assert.assertEquals(0, result);
    }

    @Test
    public void sub_singleNestedOperand() {
        int result = calculator.calculate("sub(sub(40,30),400)");
        Assert.assertEquals(390, result);
    }

    @Test
    public void sub_doubleNestedOperands() {
        int result = calculator.calculate("sub(sub(40,30),sub(500,10))");
        Assert.assertEquals(-480, result);
    }

    @Test
    public void sub_multipleNestedOperands() {
        int result = calculator.calculate("sub(sub(40,sub(30,55)),sub(500,10))");
        Assert.assertEquals(-425, result);
    }

    /**
     * Testing Mul operation
     */

    @Test
    public void mul() {
        int result = calculator.calculate("mul(234,76)");
        Assert.assertEquals(17784, result);
    }

    @Test
    public void mul_withNegativeMultiplicand() {
        int result = calculator.calculate("mul(-123,123)");
        Assert.assertEquals(-15129, result);
    }

    @Test
    public void mul_withNegativeMultiplier() {
        int result = calculator.calculate("mul(123,-123)");
        Assert.assertEquals(-15129, result);
    }

    @Test
    public void mul_withLargestInputs() {
        int result = calculator.calculate("mul(32768,32768)");
        Assert.assertEquals(1073741824, result);
    }

    @Test
    public void mul_withLargestNegaviteInputs() {
        int result = calculator.calculate("mul(-32768,-32768)");
        Assert.assertEquals(1073741824, result);
    }

    @Test
    public void mul_singleNestedOperand() {
        int result = calculator.calculate("mul(mul(40,30),400)");
        Assert.assertEquals(480000, result);
    }

    @Test
    public void mul_doubleNestedOperands() {
        int result = calculator.calculate("mul(mul(40,30),mul(500,10))");
        Assert.assertEquals(6000000, result);
    }

    @Test
    public void mul_multipleNestedOperands() {
        int result = calculator.calculate("mul(mul(40,mul(30,55)),mul(500,10))");
        Assert.assertEquals(330000000, result);
    }

    /**
     * Testing Div operation
     */

    @Test
    public void div() {
        int result = calculator.calculate("div(12321,434)");
        Assert.assertEquals(28, result);
    }

    @Test
    public void div_withNegativeDividend() {
        int result = calculator.calculate("div(-12321,434)");
        Assert.assertEquals(-28, result);
    }

    @Test
    public void div_withNegativeDivisor() {
        int result = calculator.calculate("div(12321,-434)");
        Assert.assertEquals(-28, result);
    }

    @Test
    public void div_withLargestInputs() {
        int result = calculator.calculate("div(32768,32768)");
        Assert.assertEquals(1, result);
    }

    @Test
    public void div_withLargestNegativeInputs() {
        int result = calculator.calculate("div(-32768,-32768)");
        Assert.assertEquals(1, result);
    }

    @Test
    public void div_withZeroDividend() {
        int result = calculator.calculate("div(0,-32768)");
        Assert.assertEquals(0, result);
    }

    @Test
    public void div_singleNestedOperand() {
        int result = calculator.calculate("div(div(50000,30),400)");
        Assert.assertEquals(4, result);
    }

    @Test
    public void div_doubleNestedOperands() {
        int result = calculator.calculate("div(div(50000,30),div(500,10))");
        Assert.assertEquals(33, result);
    }

    @Test
    public void div_multipleNestedOperands() {
        int result = calculator.calculate("div(div(40,div(50000,55)),div(500,10))");
        Assert.assertEquals(0, result);
    }

    /**
     * Testing Let operation
     */

    @Test
    public void let() {
        int result = calculator.calculate("let(a,32768,add(32768,a))");
        Assert.assertEquals(65536, result);
    }

    @Test
    public void let_nestedResultExpression() {
        int result = calculator.calculate("let(a,5,add(a,a))");
        Assert.assertEquals(10, result);
    }

    @Test
    public void let_nestedValueExpression() {
        int result = calculator.calculate("let(a,5,let(b,mul(a,10),add(b,a)))");
        Assert.assertEquals(55, result);
    }

    @Test
    public void let_nestedDoubleValueExpression() {
        int result = calculator.calculate("let(a,let(b,10,add(b,b)),let(b,20,add(a,b)))");
        Assert.assertEquals(40, result);
    }



}
