package edu.ucsb.cs56.pconrad.parsing.tokenizer;

/**
   An object implements TokenMaker if it has a method makeToken
   that takes an object of type String and returns an instance of the
   class Token
*/

public interface TokenMaker {
    public Token makeToken(String s);
}
