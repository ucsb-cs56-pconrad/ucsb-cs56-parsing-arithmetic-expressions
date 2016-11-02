package edu.ucsb.cs56.pconrad.parsing.parser;

/**
 * Exception thrown when parsing fails, e.g., <code>)3(</code>.
 */
public class ParserException extends Exception {
    public ParserException(final String message) {
	super(message);
    }
}
