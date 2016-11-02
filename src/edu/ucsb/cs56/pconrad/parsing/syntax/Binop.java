package edu.ucsb.cs56.pconrad.parsing.syntax;

public class Binop implements AST {
    // begin instance variables
    private final AST left;
    private final Operator op;
    private final AST right;
    // end instance variables

    public Binop(final AST left,
		 final Operator op,
		 final AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    
    
    public boolean equals(final Object other) {
        if (other instanceof Binop) {
            final Binop otherOp = (Binop)other;
            return (otherOp.op.equals(op) &&
                    otherOp.left.equals(left) &&
                    otherOp.right.equals(right));
        } else {
            return false;
        }
    }

    public int hashCode() {
	return (left.hashCode() +
		op.hashCode() +
		right.hashCode());
    }

    public String toString() {
	return ("(" + left.toString() +
		" " + op.toString() +
		" " + right.toString() +
		")");
    }

    public AST getLeft() { return left; }
    public Operator getOperator() { return op; }
    public AST getRight() { return right; }

} // Binop
