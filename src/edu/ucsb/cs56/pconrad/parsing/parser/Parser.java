package edu.ucsb.cs56.pconrad.parsing.parser;

import edu.ucsb.cs56.pconrad.parsing.syntax.*;
import edu.ucsb.cs56.pconrad.parsing.tokenizer.*;

import java.util.*;

/**
 * Parses a sequence of tokens into an AST.
 * This is specific to our arithmetic expression language.
 */
public class Parser {
    // BEGIN CONSTANTS


    public static final Token LEFT_PAREN_TOKEN = new LParenToken();
    public static final Token RIGHT_PAREN_TOKEN = new RParenToken();
    public static final Token PLUS_TOKEN = new PlusToken();
    public static final Token MINUS_TOKEN = new MinusToken();
    public static final Token TIMES_TOKEN = new TimesToken();
    public static final Token DIV_TOKEN = new DivideToken();
    // END CONSTANTS
    
    // BEGIN INSTANCE VARIABLES
    private final ArrayList<Token> input;
    // END INSTANCE VARIABLES

    /**
     * @param input The tokens to parse.  This is intentionally an <code>ArrayList</code>
     *              to guarantee constant-time random access, which is necessary
     *              for performance.
     */
    public Parser(final ArrayList<Token> input) {
	this.input = input;
    }



    /**
     * Parses a <code>primary</code> expression, as per our arithmetic expression grammar.
     *
     * @param pos The position where to start parsing from
     */
    

    private ParseResult<AST> parsePrimary(final int pos) throws ParserException {
	final Token firstToken = tokenAt(pos);
	
	if (firstToken.equals(LEFT_PAREN_TOKEN)) {
	    final ParseResult<AST> nestedExp = parseExpression(pos + 1);
	    final int nextPos = nestedExp.getNextPos();
	    if (tokenAt(nextPos).equals(RIGHT_PAREN_TOKEN)) {
		return new ParseResult<AST>(nestedExp.getResult(),
					    nextPos + 1);
	    } else {
		throw new ParserException("Expected ')'");
	    }
	} else if (firstToken.equals(MINUS_TOKEN)) {
	    final ParseResult<AST> nestedExp = parsePrimary(pos + 1);
	    return new ParseResult<AST>(new UnaryMinus(nestedExp.getResult()),
					nestedExp.getNextPos());
	} else if (firstToken instanceof IntToken) {
	    return new ParseResult<AST>(new Literal(((IntToken)firstToken).getValue()),
					pos + 1);
	} else {
	    throw new ParserException("Expected primary expression; got: " +
				      firstToken.toString());
	}
    }

    
    private ParseResult<Operator> parsePlusMinus(final int pos) throws ParserException {
	final Token tokenHere = tokenAt(pos);
	if (tokenHere.equals(PLUS_TOKEN)) {
	    return new ParseResult<Operator>(Plus.PLUS, pos + 1);
	} else if (tokenHere.equals(MINUS_TOKEN)) {
	    return new ParseResult<Operator>(Minus.MINUS, pos + 1);
	} else {
	    throw new ParserException("Expected + or - operator ");
	}
	
    }
    
    private ParseResult<Operator> parseTimesDiv(final int pos) throws ParserException {
	final Token tokenHere = tokenAt(pos);
	if (tokenHere.equals(TIMES_TOKEN)) {
	    return new ParseResult<Operator>(Times.TIMES, pos + 1);
	} else if (tokenHere.equals(DIV_TOKEN)) {
	    return new ParseResult<Operator>(Div.DIV, pos + 1);
	} else {
	    throw new ParserException("Expected * or / operator ");
	}
    }
    
    // BEGIN CODE FOR MULTIPLICATIVE AND ADDITIVE EXPRESSIONS
    /**
     * As with <code>PrimaryTokenVisitor</code>, this is defined as an inner class
     * to get access to all the methods on <code>Parser</code>, without (innapropriately)
     * making those methods <code>public</code>.
     */
    private class ParseAdditive extends ParseAdditiveOrMultiplicative {

	public ParseResult<AST> parseBase(final int pos) throws ParserException {
	    return parseMultiplicativeExpression(pos);
	}
	
	public ParseResult<Operator> parseOp(final int pos) throws ParserException {
	    return parsePlusMinus(pos);
	}
    }

    /**
     * As with <code>PrimaryTokenVisitor</code>, this is defined as an inner class
     * to get access to all the methods on <code>Parser</code>, without (innapropriately)
     * making those methods <code>public</code>.
     */
    private class ParseMultiplicative extends ParseAdditiveOrMultiplicative {
	public ParseResult<AST> parseBase(final int pos) throws ParserException {
	    return parsePrimary(pos);
	}
	public ParseResult<Operator> parseOp(final int pos) throws ParserException {
	    return parseTimesDiv(pos);
	}
    }

    // because the above two classes hold no state and don't have useful
    // constructors, we can simply allocate these ahead of time and use
    // them throughout
    
    private final ParseAdditive PARSE_ADDITIVE = new ParseAdditive();
    private final ParseMultiplicative PARSE_MULTIPLICATIVE = new ParseMultiplicative();

    private ParseResult<AST> parseMultiplicativeExpression(final int pos)
	throws ParserException {
	return PARSE_MULTIPLICATIVE.parseExp(pos);
    }

    private ParseResult<AST> parseAdditiveExpression(final int pos)
	throws ParserException {
	return PARSE_ADDITIVE.parseExp(pos);
    }
    // END CODE FOR MULIPLICATIVE AND ADDITIVE EXPRESSIONS
    
    private ParseResult<AST> parseExpression(final int pos) throws ParserException {
	return parseAdditiveExpression(pos);
    }

    /**
     * Used to get access to underlying tokens.
     * This should <b>always</b> be used instead of directly accessing the underlying
     * list of tokens.  While running off of the end of a list usually indicates a bug,
     * when it comes to parsing running off the end of the list simply means the input
     * wasn't valid (which isn't a bug, but an error in user input).
     *
     * @param pos The position where to get the token
     * @return The token at <code>pos</code>
     * @throws ParserException if <code>pos</code> is out of range; that is, there
     *         is no token at <code>pos</code>.
     */
    private Token tokenAt(final int pos) throws ParserException {
	if (pos < 0 || pos >= input.size()) {
	    throw new ParserException("Attempted to get token out of position");
	} else {
	    return input.get(pos);
	}
    }

    /**
     * Parses the list of tokens provided in the constructor.
     *
     * @return The AST resulting from parsing
     * @throws ParserException If the tokens could not be parsed, e.g., with the
     *         input <code>)3(</code>.
     */
    public AST parse() throws ParserException {
	final ParseResult<AST> rawResult = parseExpression(0);
	if (rawResult.getNextPos() != input.size()) {
	    throw new ParserException("Extra tokens at the end");
	} else {
	    return rawResult.getResult();
	}
    }
} // Parser
