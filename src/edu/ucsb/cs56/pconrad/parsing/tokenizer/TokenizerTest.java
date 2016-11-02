package edu.ucsb.cs56.pconrad.parsing.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TokenizerTest {

    private TokenFactory tf = new DefaultTokenFactory();
    
    @Test
    public void testOneErrorToken() {
        assertArrayEquals(new Token[] { tf.makeErrorToken("$") },
                          Tokenizer.tokenizeToArray("$"));
    }


    @Test
    public void testOneValidFollowedByOneErrorToken() {
        assertArrayEquals(new Token[] {
		tf.makeIntToken("2"),
		tf.makeErrorToken("$")
	    },
                          Tokenizer.tokenizeToArray("2$"));
    }

    @Test
    public void testTwoPlusTwoEquals() {
        assertArrayEquals(new Token[] {
		tf.makeIntToken("2"),
		tf.makePlusToken(),
		tf.makeIntToken("2"),
		tf.makeErrorToken("=")
	    },
	    Tokenizer.tokenizeToArray("2+2="));
    }

    @Test
    public void testTwoPlusTwoEqualsWithSpace() {
        assertArrayEquals(new Token[] {
		tf.makeIntToken("2"),
		tf.makePlusToken(),
		tf.makeIntToken("2"),
		tf.makeErrorToken("=")
	    },
	    Tokenizer.tokenizeToArray(" 2 + 2 = "));
    }

    @Test
    public void testInterleavedIllegalCharsAndWhiteSpace() {
        assertArrayEquals(new Token[] {
		tf.makeErrorToken("a"),
		tf.makeErrorToken("b"),
		tf.makeErrorToken("c"),
		tf.makeErrorToken("d"),
		tf.makeErrorToken("e"),
		tf.makeErrorToken("f"),
		tf.makeErrorToken("g"),
		tf.makeErrorToken("h"),
		tf.makeErrorToken("i"),
	    },
	    Tokenizer.tokenizeToArray(" ab c d ef ghi "));
    }


    
    @Test
    public void testSingleDigitIntToken() {
        assertArrayEquals(new Token[] { tf.makeIntToken("0") },
                          Tokenizer.tokenizeToArray("0"));
    }


    @Test
    public void testTwoDigitIntToken() {
        assertArrayEquals(new Token[] { tf.makeIntToken("12") },
                          Tokenizer.tokenizeToArray("12"));
    }

    @Test
    public void testTwoPlusTwo() {
        assertArrayEquals(new Token[] {
		tf.makeIntToken("2"),
		tf.makePlusToken(),
		tf.makeIntToken("2")
	    },
	    Tokenizer.tokenizeToArray("2+2"));
    }


    @Test
    public void testTwoPlusTwoWithWhiteSpace() {
        assertArrayEquals(new Token[] {
		tf.makeIntToken("2"),
		tf.makePlusToken(),
		tf.makeIntToken("2")
	    },
	    Tokenizer.tokenizeToArray(" 2 + 2 "));
    }

    @Test
    public void twoDigitArithmeticMixed() {
        assertArrayEquals(new Token[] {
		tf.makeIntToken("12"),
		tf.makePlusToken(),
		tf.makeIntToken("34"),
		tf.makeTimesToken(),
		tf.makeIntToken("56"),
		tf.makeMinusToken(),
		tf.makeIntToken("78"),
		tf.makeDivideToken(),
		tf.makeIntToken("90"),
	    },
	    Tokenizer.tokenizeToArray(" 12  + 34*56 -78/90 "));
    }
    
    @Test
    public void fullSetNoWhiteSpace() {
        assertArrayEquals(new Token[] {
		tf.makeIntToken("1234"),
		tf.makePlusToken(),
		tf.makeTimesToken(),
		tf.makeMinusToken(),
		tf.makeIntToken("5678"),		
		tf.makeDivideToken(),
		tf.makeLParenToken(),
		tf.makeRParenToken(),
		tf.makeIntToken("3333"),		
	    },
	    Tokenizer.tokenizeToArray("1234+*-5678/()3333"));
    }

    @Test
    public void fullSetLotsOfWhiteSpace() {
        assertArrayEquals(new Token[] {
		tf.makeIntToken("12"),
		tf.makeIntToken("34"),
		tf.makePlusToken(),
		tf.makeTimesToken(),
		tf.makeMinusToken(),
		tf.makeIntToken("5678"),		
		tf.makeDivideToken(),
		tf.makeLParenToken(),
		tf.makeRParenToken(),
		tf.makeIntToken("3333"),		
	    },
	    Tokenizer.tokenizeToArray(" 12 34 + *  -   5678  / ( )  3333  "));
    }

    @Test
    public void testOneToken() {
	assertArrayEquals(new Token[] { tf.makePlusToken() },
			  Tokenizer.tokenizeToArray("+"));
    }

        @Test
	public void testTwoSameTokens() {
	    assertArrayEquals(new Token[] { tf.makePlusToken(),
					    tf.makePlusToken() },
		Tokenizer.tokenizeToArray("++"));
	}

        @Test
	public void testSingleWhitespace() {
	    assertArrayEquals(new Token[] { },
			      Tokenizer.tokenizeToArray(" "));
	}

        @Test
	public void testSingleDigit() {
	    assertArrayEquals(new Token[] { tf.makeIntToken("1") },
			      Tokenizer.tokenizeToArray("1"));
	}

        @Test
	public void testTwoDigit() {
	    assertArrayEquals(new Token[] { tf.makeIntToken("12") },
			      Tokenizer.tokenizeToArray("12"));
	}

        @Test
	public void testBigFatUglyTestFromLecture() {
	    assertArrayEquals(new Token[] {
		    tf.makePlusToken(),
		    tf.makeMinusToken(),
		    tf.makePlusToken(),
		    tf.makeIntToken("12"),
		    tf.makePlusToken()
		},
		Tokenizer.tokenizeToArray("+-+12+"));
	}

        @Test
	public void testInvalidSingleToken() {
	    assertArrayEquals(new Token[] {
		    tf.makeErrorToken("c"),
		},
		Tokenizer.tokenizeToArray("c"));
	}
    
}
