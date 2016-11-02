package edu.ucsb.cs56.pconrad.parsing.tokenizer;

public class IntToken extends Token {

    private int value;
    
    public IntToken(String s) {
	try {
	    this.value = Integer.parseInt(s);
	} catch (NumberFormatException nfe) {
	    throw new IllegalArgumentException("String passed into IntToken could not be parsed into integer value");
	}
    }

    @Override
    public String toString() { return "IntToken(" + value + ")"; }

    @Override
    public boolean equals(Object obj)    {
	if (obj == null) return false;
	if (getClass() != obj.getClass()) return false;
	IntToken other = (IntToken) obj;
	return other.value==this.value;
    }

    public int getValue() { return this.value; }
}
