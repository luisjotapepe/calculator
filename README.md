# Simple Calculator Program

This implementation of the calculator has been created following a methodology where the expression calculation happens in three steps: **tokenizing, parsing and evaluating**. 
The stream of characters is fed into the **tokenizer** which iterates character by character creating a collection of tokens that represent a meaningful part of the expression. 
This collection is then **parsed** and logically mapped out on to a tree. 
Lastly, the **evaluator** traverses the tree evaluating each of the logical operator which subsequently returns the value of the calculated expression.

* Requirements:
 
-- Java 1.6+

-- Linux/Mac Os

* Build:

From the home folder of the src code run the following scripts

``$ javac src/main/homework/*.java -d out/``

``$ jar cvfm Calculator.jar src/META-INF/MANIFEST.MF -C out/ .``

* Run Application:

To run the application

``$ java -jar Calculator.jar "<EXPRESSIOM>"`` where ``<EPRESSION>`` is an arithmetic expression desired to be calculated.

Example:

_Successful calculation_:

``$ java -jar Calculator.jar "let(a,let(x,10,add(x,x)),let(b,30,add(a,b)))"``

``Oct 13, 2018 02:24:09 PM main.homework.Application runCalculation``

``INFO: Expression to calculate: let(a,let(x,10,add(x,x)),let(b,30,add(a,b)))``

``Oct 13, 2018 02:24:09 PM main.homework.Application runCalculation``

``INFO: Result: 50``

_Failed calculation_:

``$ java -jar Calculator.jar "let(a,let(c,10,add(x,x)),let(b,30,add(a,b)))"``

``Oct 13, 2018 02:29:47 PM main.homework.Application runCalculation``

``INFO: Expression to calculate: let(a,let(c,10,add(x,x)),let(b,30,add(a,b)))``

``Oct 13, 2018 02:29:47 PM main.homework.Application runCalculation``

``INFO: Result: ****** Variable name not found in expression: x ******``

* Run Test Cases:

To run test cases from CLI

``$ javac -cp libs/junit-4.12.jar:Calculator.jar:.  src/test/homework/CalculatorTest.java``