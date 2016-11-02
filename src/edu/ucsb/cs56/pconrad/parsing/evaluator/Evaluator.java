package edu.ucsb.cs56.pconrad.parsing.evaluator;

import edu.ucsb.cs56.pconrad.parsing.syntax.*;

public class Evaluator {
    public static int evaluate(final int left,
			       final Operator operator,
			       final int right)
	throws EvaluatorException {
	if (operator instanceof Plus) {
	    return left + right;
	} else if (operator instanceof Minus) {
	    return left - right;
	} else if (operator instanceof Times) {
	    return left * right;
	} else if (operator instanceof Div) {
	    if (right == 0) {
		throw new EvaluatorException("Division by zero");
	    } else {
		return left / right;
	    }
	} else {
	    assert(false); // no other operations
	    return 0; // unreachable
	}
    } // evaluate

    public static int evaluate(final AST expression)
	throws EvaluatorException {
	if (expression instanceof Literal) {
	    return ((Literal)expression).getValue();
	} else if (expression instanceof Binop) {
	    final Binop binop = (Binop)expression;
	    return evaluate(evaluate(binop.getLeft()),
			    binop.getOperator(),
			    evaluate(binop.getRight()));
	} else if (expression instanceof UnaryMinus) {
	    return -evaluate(((UnaryMinus)expression).getNested());
	} else {
	    assert(false); // no other kinds of expressions
	    return 0; // unreachable
	}
    } // evaluate
}
