package operator;

// Name: Emin Salih
// Surname: AÇIKGÖZ
// Student ID: 22050111032
enum OperatorType {
    Add, Subtract, Multiply, Divide, Value;

    String symbol() {
        return switch (this) {
            case Add ->
                " + ";
            case Subtract ->
                " - ";
            case Multiply ->
                " * ";
            case Divide ->
                " / ";
            case Value ->
                " v "; //In place in case of an error where value is printed as a sign
            default ->
                "error";
        };
    }
}

public class Operator {

    public static void main(String[] args) {

        Operator a = new Operator(-1);
        Operator b = new Operator(2);
        Operator c = new Operator(OperatorType.Add, a, b);
        Operator d = new Operator(3);
        Operator e = new Operator(4);
        Operator f = new Operator(OperatorType.Multiply, d, e);
        Operator g = new Operator(OperatorType.Subtract, c, f);
        Operator h = new Operator(12.);
        Operator i = new Operator(OperatorType.Divide, g, h);

        System.out.println("test 0");
        System.out.println(i.toString() + " = " + i.getValue() + "\n");
        // = -0.9166666666666666 (Truncation point appears to vary depending on calculator)

        Operator test1 = new Operator(-1.0);
        System.out.println("test 1");
        System.out.println(test1);
        // =-1.0

        System.out.println("test 2");
        Operator test2a = new Operator(0);
        Operator test2b = new Operator(-0.5);
        Operator test2c = new Operator(OperatorType.Add, i, test2b);
        Operator test2d = new Operator(OperatorType.Subtract, test2a, test2c);
        System.out.println(test2d.toString() + " = " + test2d.getValue());
        // = 1.4166666666666665 (Truncation point appears to vary depending on calculator)

        System.out.println("test 3");
        Operator test3a = new Operator(OperatorType.Add, new Operator(-4.5), new Operator(1.0));
        Operator test3b = new Operator(OperatorType.Add, new Operator(3.5), new Operator(1.0));
        Operator test3c = new Operator(OperatorType.Multiply, test3a, test3b);
        System.out.println(test3c.toString() + " = " + test3c.getValue());
        // = -15.75

        System.out.println("test 4");
        Operator test4 = new Operator(OperatorType.Add, new Operator(-5.0), new Operator(Double.POSITIVE_INFINITY));
        System.out.println(test4 + " = " + test4.getValue());
        // Positive infinity

        System.out.println("test 5");
        Operator test5 = new Operator(OperatorType.Divide, new Operator(Double.POSITIVE_INFINITY), new Operator(Double.NEGATIVE_INFINITY));
        System.out.println(test5 + " = " + test5.getValue());
        // NaN as inf/inf is not a real number

        System.out.println("test 6");
        Operator test6 = new Operator(OperatorType.Subtract, i, null);
        System.out.println(test6 + " = " + test6.getValue());
        //NaN due to right child being null

        System.out.println("test 7");
        Operator test7 = new Operator(OperatorType.Subtract, i, i);
        System.out.println(test7 + " = " + test7.getValue());
        // approximately 0 due to left having the same value as right (depending on operation there may be small round off errors)

        System.out.println("test 8");
        Operator test8 = new Operator(OperatorType.Value, new Operator(-7.0), new Operator(-5.0));
        System.out.println(test8 + " = " + test8.getValue());
        // NaN due to invalid operator type

        System.out.println("test 9");
        Operator test9 = new Operator(OperatorType.Value, null, new Operator(-5.0));
        System.out.println(test9 + " = " + test9.getValue());
        //NaN due to invalid input

        System.out.println("test 10");
        Operator test10 = new Operator(OperatorType.Value, null, null);
        System.out.println(test10 + " = " + test10.getValue());
        //NaN due to invalid input

        System.out.println("test 11");
        Operator test11 = new Operator();
        System.out.println(test11 + " = " + test11.getValue());
        //0 as default value is zero

    }

    final double value;
    final OperatorType operatorType;
    final Operator leftChild;
    final Operator rightChild;

    Operator(double value) {
        //Creates a leaf with the specified value
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
        operatorType = OperatorType.Value;

    }

    Operator() {
        this(0); // empty constructors have a default value of zero
    }

    Operator(OperatorType operatorType, Operator leftChild, Operator rightChild) {
        // creates an Operator node with two children and the specified operator
        // if the entered values are improper it creates a leaf with a value of NaN
        this.value = Double.NaN;
        if (leftChild != null && rightChild != null && operatorType != OperatorType.Value) {
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.operatorType = operatorType;
        } else {
            this.leftChild = null;
            this.rightChild = null;
            this.operatorType = OperatorType.Value;
        }
    }

    boolean isLeaf() {
        // returns true if the node has no children
        return ((leftChild == null) && (rightChild == null));
    }

    double getValue() {
        //returns the value of the current node if it is a value node 
        //else it returns the appropiate operation of its children
        switch (this.operatorType) {
            case Value:
                return this.value;
            case Add:
                return (leftChild.getValue() + rightChild.getValue());
            case Subtract:
                return (leftChild.getValue() - rightChild.getValue());
            case Multiply:
                return (leftChild.getValue() * rightChild.getValue());
            case Divide:
                return (leftChild.getValue() / rightChild.getValue());
            default:
                return Double.NaN;
        }
    }

    @Override
    public String toString() {
        if (this.isLeaf() && this.operatorType == OperatorType.Value) { // checks twice for a valid entry, as both conditions must be valid.
            return Double.toString(this.value);
        } else {
            if (rightChild.operatorType == OperatorType.Value && rightChild.value < 0) {
                return "(" + leftChild.toString() + this.operatorType.symbol() + "(" + rightChild.toString() + "))";
                // negative values on the right side of an operation must be surrounded by parentheses
            } else {
                return "(" + leftChild.toString() + this.operatorType.symbol() + rightChild.toString() + ")";
            }

        }
    }

}
