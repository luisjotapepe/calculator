package test.homework;

import main.homework.Calculator;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    //Test simple add
    @Test
    public void add() {
        int result = calculator.process("add(2,2)");
        Assert.assertEquals(4, result);
    }

    @Test
    public void add_withNegativeAugend() {
        int result = calculator.process("add(-20,3)");
        Assert.assertEquals(-17, result);
    }

    @Test
    public void add_withNegativeAddedend() {
        int result = calculator.process("add(2,-2)");
        Assert.assertEquals(0, result);
    }

    @Test
    public void add_withNegativeAugendAndAddedend() {
        int result = calculator.process("add(-12,-2)");
        Assert.assertEquals(-14, result);
    }

    //Test simple add
    @Test
    public void sub() {
        int result = calculator.process("sub(2,2)");
        Assert.assertEquals(0, result);
    }

    @Test
    public void sub_withNegativeMinuend() {
        int result = calculator.process("sub(-20,2)");
        Assert.assertEquals(-22, result);
    }

    @Test
    public void sub_withNegativeSubtrahend() {
        int result = calculator.process("sub(111,-30000)");
        Assert.assertEquals(30111, result);
    }

    @Test
    public void sub_withNegativeMinuendSubtrahend() {
        int result = calculator.process("sub(-20,-13)");
        Assert.assertEquals(-7, result);
    }

    //Test simple mul
    @Test
    public void mul() {
        int result = calculator.process("mul(2,2)");
        Assert.assertEquals(4, result);
    }

    @Test
    public void mul_withNegative() {
        int result = calculator.process("mul(2,-2)");
        Assert.assertEquals(-4, result);
    }

    @Test
    public void div() {
        int result = calculator.process("div(4,2)");
        Assert.assertEquals(2, result);
    }

    @Test
    public void div_withNegative() {
        int result = calculator.process("div(0,2)");
        Assert.assertEquals(0, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void div_withNegativeDivisor() {
        calculator.process("div(4,0)");
    }

    @Test
    public void multiple_addmulOne() {
        int result = calculator.process("add(mul(-2,3),1)");
        Assert.assertEquals(-5, result);
    }

    @Test
    public void multiple_addmulTwo() {
        int result = calculator.process("add(1,mul(2,3))");
        Assert.assertEquals(7, result);
    }

    @Test(expected = IllegalStateException.class)
    public void multiple_imbalancedParenthesisAtTheEnd() {
        calculator.process("add(1,mul(2,3))))))");
    }

    @Test(expected = IllegalStateException.class)
    public void multiple_imbalancedparenthesisAtTheBeginning() {
        calculator.process("add(((1,mul(2,3))");
    }

    @Test(expected = IllegalStateException.class)
    public void multiple_imbalancedparenthesisInTheMiddle() {
        calculator.process("add(1,mul(((2,3))");
    }
}
