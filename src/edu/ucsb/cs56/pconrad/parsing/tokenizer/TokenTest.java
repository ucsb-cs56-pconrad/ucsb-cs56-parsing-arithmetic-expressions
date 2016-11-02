package edu.ucsb.cs56.pconrad.parsing.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class TokenTest {

    @Test
    public void testAllDivideTokensAreEqual() {
	assertEquals(new DivideToken(), new DivideToken());
    }


    @Test
    public void testAllLParenTokensAreEqual() {
	assertEquals(new LParenToken(), new LParenToken());
    }

    @Test
    public void testAllLParenTokensArentEqualToDivideTokens() {
	assertFalse(new LParenToken().equals(new DivideToken()));
    }

}
