package edu.ucsb.cs56.pconrad.parsing.tokenizer;

public class Token {

    @Override public String toString() {
	return "Token";
    }
    
    @Override
    public int hashCode() {
	String s = this.toString();
	return s.hashCode();
    }

    @Override
    public boolean equals(Object obj)    {
	if (obj == null) return false;
	if (getClass() != obj.getClass()) return false;
	return true;
    }

}
