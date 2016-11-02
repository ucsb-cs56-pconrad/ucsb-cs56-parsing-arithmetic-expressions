package edu.ucsb.cs56.pconrad.parsing.parser;

/**
 Encapsulates the result of parsing something in, where "something"
 is represented by the type variable <code>A</code>.
 Whenever we parse something, there are two pieces of information
 which are immediately of interest:
 
 <ol>
  <li>The value which was parsed in, whatever it was (e.g., an AST, an operator, etc.)</li>
  <li>The next position where we are in the input; that is, where we need to start parsing from next</li>
 </ol>

 <code>ParseResult</code> combines the above two pieces of information into a single class.
 */
public class ParseResult<A> {
    // begin instance variables
    private final A result;
    private final int nextPos;
    // end instance variables

    /**
     * @param result The value which was parsed in (e.g., an AST, an operator, etc.)
     * @param nextPos The next position in the input where to start parsing
     */
    public ParseResult(final A result, final int nextPos) {
	this.result = result;
	this.nextPos = nextPos;
    }

    public A getResult() { return result; }
    public int getNextPos() { return nextPos; }
} // ParseResult

