package edu.ucsb.cs56.pconrad.parsing.tokenizer;

/**
   <p>
   The methods of TokenFactory exist for one reason, and one reason only; to decouple the
   TokenizerTest class from the specific classes used that all inherit from Token.
   </p>

   <p>
   That way, the instructor can pin down the exact contents of the TokenizerTest.java file,
   in a way that does not change.  But, the TokenizerTest.java file does NOT depend on the names
   that you give to the classes that implement various kinds of tokens.     
   </p>

   <p>
   That way, you are free, for example, if you are extending the code to add == and != as operators,
   to either have separate EqualsOpToken and NotEqualsOpToken, or instead to just have an ComparisonOpToken
   into which you pass either "==" or "!=" as a value (much the same as is done for the IntToken and
   ErrorToken classes.)   
   </p>

   @author Kyle Dewey and Phill Conrad

*/

public class DefaultTokenFactory implements TokenFactory {

    /** make the type of token that represents an integer
	@param value value of the integer, as a string.
	@return an appropriate token
    */

    public Token makeIntToken(String value) { return new IntToken(value);  }

    /** make a token that indicates there were one or more illegal characters in the input
	@param value the sequence of illegal characters 
	@return an appropriate token
    */

    public Token makeErrorToken(String value) { return new ErrorToken(value); }

    /** make a token that represents a left parentheses <code>(</code> 
	@return an appropriate token
    */
    
    public Token makeLParenToken() { return new LParenToken(); }

    /** make a token that represents a right parentheses <code>)</code> 
	@return an appropriate token
    */


    public Token makeRParenToken() { return new RParenToken(); }

    /** make a token that represents a plus sign <code>+</code> 
	@return an appropriate token
     */

    public Token makePlusToken() { return new PlusToken(); }

    /** make a token that represents a minus sign <code>-</code> 
	@return an appropriate token
     */

    public Token makeMinusToken() { return new MinusToken(); }

    /** make a token that represents a multiplication operator <code>*</code> 
	@return an appropriate token
     */
    
    public Token makeTimesToken() { return new TimesToken(); }

    /** make a token that represents a division operator <code>/</code> 
	@return an appropriate token
    */
    
    public Token makeDivideToken() { return new DivideToken(); }
}
