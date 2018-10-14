package test.homework;

import main.homework.Calculator;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    //Test simple add
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
    public void add_withNegativeAddedend() {
        int result = calculator.calculate("add(2,-2)");
        Assert.assertEquals(0, result);
    }

    @Test
    public void add_withNegativeAugendAndAddedend() {
        int result = calculator.calculate("add(-12,-2)");
        Assert.assertEquals(-14, result);
    }

    //Test simple add
    @Test
    public void sub() {
        int result = calculator.calculate("sub(2,2)");
        Assert.assertEquals(0, result);
    }

    @Test
    public void sub_withNegativeMinuend() {
        int result = calculator.calculate("sub(-20,2)");
        Assert.assertEquals(-22, result);
    }

    @Test
    public void sub_withNegativeSubtrahend() {
        int result = calculator.calculate("sub(111,-30000)");
        Assert.assertEquals(30111, result);
    }

    @Test
    public void sub_withNegativeMinuendSubtrahend() {
        int result = calculator.calculate("sub(-20,-13)");
        Assert.assertEquals(-7, result);
    }

    //Test simple mul
    @Test
    public void mul() {
        int result = calculator.calculate("mul(2,2)");
        Assert.assertEquals(4, result);
    }

    @Test
    public void mul_withNegative() {
        int result = calculator.calculate("mul(2,-2)");
        Assert.assertEquals(-4, result);
    }

    //Simple div
    @Test
    public void div() {
        int result = calculator.calculate("div(4,2)");
        Assert.assertEquals(2, result);
    }

    @Test
    public void div_withNegative() {
        int result = calculator.calculate("div(0,2)");
        Assert.assertEquals(0, result);
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void div_withNegativeDivisor() {
//        calculator.calculate("div(4,0)");
//    }

    //Simple let
    @Test
    public void let() {
        int result = calculator.calculate("let(a,5,add(a,a))");
        Assert.assertEquals(10, result);
    }

    @Test
    public void multiple_addmulOne() {
        int result = calculator.calculate("add(mul(-2,3),1)");
        Assert.assertEquals(-5, result);
    }

    @Test
    public void multiple_addmulTwo() {
        int result = calculator.calculate("add(1,mul(2,3))");
        Assert.assertEquals(7, result);
    }

    @Test
    public void multiple_addmulThree() {
        int result = calculator.calculate("mul(add(2,2),div(9,3))");
        Assert.assertEquals(12, result);
    }

    @Test
    public void test_let() {
        int result = calculator.calculate("let(a,5,add(a,a))");
        Assert.assertEquals(10, result);
    }

    @Test
    public void test_nestedLet1() {
        int result = calculator.calculate("let(a,add(5,4),add(a,10))");
        Assert.assertEquals(19, result);
    }

    @Test
    public void test_nestedLet2() {
        int result = calculator.calculate("let(a,let(b,5,add(b,5)),add(a,4))");
        Assert.assertEquals(14, result);
    }

    @Test
    public void test_nestedLet3() {
        int result = calculator.calculate("let(a,let(b,5,add(b,5)),let(b,20,add(a,b)))");
        Assert.assertEquals(30, result);
    }

}
