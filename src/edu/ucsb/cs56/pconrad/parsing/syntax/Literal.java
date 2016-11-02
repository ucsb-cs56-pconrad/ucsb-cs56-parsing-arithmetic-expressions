package edu.ucsb.cs56.pconrad.parsing.syntax;

public class Literal implements AST {
    // begin instance variables
    private final int value;
    // end instance variables


    public int getValue() {
	return value;
    }
    
    public Literal(final int value) {
        this.value = value;
    }

    public boolean equals(final Object other) {
        return (other instanceof Literal &&
                ((Literal)other).value == value);
    }

    public int hashCode() {
	return value;
    }

    public String toString() {
        return Integer.toString(value);
    }
} // Literal
