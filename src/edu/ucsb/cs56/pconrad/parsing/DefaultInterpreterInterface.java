package edu.ucsb.cs56.pconrad.parsing;

import edu.ucsb.cs56.pconrad.parsing.syntax.*;
import edu.ucsb.cs56.pconrad.parsing.tokenizer.*;
import edu.ucsb.cs56.pconrad.parsing.parser.*;
import edu.ucsb.cs56.pconrad.parsing.evaluator.*;

import java.util.ArrayList;

/* 
   The default implementation of <code>InterpreterInterface</code>

   @see InterpreterInterface
   @author Kyle Dewey, Phill Conrad
   
*/

public class DefaultInterpreterInterface extends InterpreterInterface {
    
    public static final DefaultInterpreterInterface DEFAULT =
	new DefaultInterpreterInterface();

    /** 
	Convert input to an list of tokens
	@param input the string to be tokenized
     */
    
    public ArrayList<Token> tokenize(final String input) throws TokenizerException {
	return new Tokenizer(input).tokenize();
    }
    
    /** 
	Convert sequence of tokens into an abstract syntax tree
	@param tokens the string to be parsed
     */

    public AST parse(final ArrayList<Token> tokens) throws ParserException {
	return new Parser(tokens).parse();
    }

    /** 
        Evaluate an abstract syntax tree representing an integer expression and return the final result
	@param expression the abstract syntax tree to be evaluated
     */

    public int evaluate(final AST expression) throws EvaluatorException {
	return Evaluator.evaluate(expression);
    }
    
} // DefaultInterpreterInterface

