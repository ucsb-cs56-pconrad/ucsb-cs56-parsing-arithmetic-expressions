package edu.ucsb.cs56.pconrad.parsing;

import edu.ucsb.cs56.pconrad.parsing.tokenizer.TokenizerException;
import edu.ucsb.cs56.pconrad.parsing.parser.ParserException;
import edu.ucsb.cs56.pconrad.parsing.evaluator.EvaluatorException;

import static edu.ucsb.cs56.pconrad.parsing.DefaultInterpreterInterface.DEFAULT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**

 Tests going from a string to an final result,
 i.e. an end-to-end test of the entire evaluator

 @author P. Conrad
 */ 

public class EndToEndTest {

    public static final DefaultInterpreterInterface
	intepreter = DefaultInterpreterInterface.DEFAULT;

    public static int evaluateNoException(final String input) {
	int retval = 0;
        try {
	    retval = intepreter.tokenizeParseAndEvaluate(input);
	} catch (TokenizerException e) {
	    fail("Unexpected tokenizer exception: " + e.toString());
        } catch (ParserException e) {
            fail("Unexpected parse exception: " + e.toString());
	} catch (EvaluatorException e) {
            fail("Unexpected evaluator exception: " + e.toString());
        }
	return retval;
    }
    
    @Test public void testNum() { assertEquals(42, evaluateNoException("42")); }

    @Test public void testAdd() { assertEquals(3, evaluateNoException("1 + 2")); }
    
    @Test public void testPrecedence() { assertEquals(23, evaluateNoException("3 + 4 * 5")); }

    @Test public void testParens() { assertEquals(17, evaluateNoException("3 * 4 + 5")); }

    @Test public void testIntegerDiv() { assertEquals(5, evaluateNoException("16/3")); }

} // EndToEndTest.java

