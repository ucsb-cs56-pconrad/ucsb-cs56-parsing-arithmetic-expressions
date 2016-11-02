package edu.ucsb.cs56.pconrad.parsing.tokenizer;

/**
 * Exception thrown if tokenization fails for whatever reason (e.g.,
 * encountering an unexpected character).
 */
public class TokenizerException extends Exception {
    public TokenizerException(String message) {
        super(message);
    }
}
