package edu.ucsb.cs56.pconrad.parsing;

import edu.ucsb.cs56.pconrad.parsing.syntax.AST;
import edu.ucsb.cs56.pconrad.parsing.tokenizer.Token;
import edu.ucsb.cs56.pconrad.parsing.tokenizer.TokenizerException;
import edu.ucsb.cs56.pconrad.parsing.parser.ParserException;
import edu.ucsb.cs56.pconrad.parsing.evaluator.EvaluatorException;

import java.util.ArrayList;




public abstract class InterpreterInterface {
    public abstract ArrayList<Token> tokenize(String input) throws TokenizerException;
    public abstract AST parse(ArrayList<Token> tokens) throws ParserException;
    public abstract int evaluate(AST expression) throws EvaluatorException;
    
    public AST tokenizeAndParse(final String input)
	throws TokenizerException, ParserException {
	return parse(tokenize(input));
    }

    public int tokenizeParseAndEvaluate(final String input)
	throws TokenizerException, ParserException, EvaluatorException {
	return evaluate(parse(tokenize(input)));
    }
}
