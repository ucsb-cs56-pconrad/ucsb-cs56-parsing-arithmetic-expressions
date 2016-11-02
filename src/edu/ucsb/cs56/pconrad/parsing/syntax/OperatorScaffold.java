package edu.ucsb.cs56.pconrad.parsing.syntax;

public abstract class OperatorScaffold implements Operator {
    // begin instance variables
    protected final char repr;
    // end instance variables

    public OperatorScaffold(char repr) {
        this.repr = repr;
    }

    public char getRepr() { return repr; }
    
    public String toString() { return Character.toString(repr); }

    public boolean equals(Object other) {
	return (other instanceof OperatorScaffold &&
		((OperatorScaffold)other).getRepr() == repr);
    }

    public int hashCode() {
	return (int)repr;
    }
}
