package edu.ucsb.cs56.pconrad.parsing.tokenizer;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.ArrayList;

public class FiniteStateAutomaton {

    public static boolean debug = false;

    private String input = null;
    private int pos = 0;
    private State currState = null;
    private State nextState = null;
    private State startState = null;
    private String accumulatedToken = "";

    private TreeSet<Character> legalChars = new TreeSet<Character>();
    
    public void setInput(String input) {
	this.input = input;
	this.pos = 0;
	resetToStart();
    }

    public String getRemainingInput() {
	if (this.pos >= input.length() || this.pos < 0) {
	    return "";
	}
	return input.substring(pos);	
    }
    
    private class State {
	private int num;
	private TokenMaker tm=null;
	private TreeMap<Character,State> nextState =
	    new TreeMap<Character,State>();

	public State(int num) {
	    this.num = num;
	}

	public State(int num, TokenMaker tm) {
	    this(num);
	}
	
	@Override
	public String toString() {
	    String retVal = "";
	    for(Map.Entry<Character,State> entry : nextState.entrySet()) {
		if (!retVal.equals(""))
		    retVal += ", "; 
		char c = entry.getKey();
		State s = entry.getValue();
		retVal += "'" + c + "'->" + s.num;
	    }
	    String tokenString = "";
	    if ( this.tm != null) {
		Token t = tm.makeToken("0");
		tokenString = " (" + t.toString() + ") ";
	    }
	    return "" + this.num + tokenString  + retVal;
	}
	
	@Override public boolean equals(Object obj) {	
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    State other = (State) obj;
	    return other.toString().equals(obj.toString());
	}
	
	@Override public int hashCode(){ return this.toString().hashCode(); }
	
    } // inner class State

    private TreeMap<Integer,State> states =
	new TreeMap<Integer,State>();
    
    private State getState(int num) {
	State s = states.get(num);
	if (s==null) {
	    s = new State(num);
	    states.put(num,s);
	}
	return s;
    }

    
    public void addState(int num) {
	State s = getState(num);
    }


    public void addState(int num, TokenMaker tm) {
	State s = getState(num);
	s.tm = tm;
    }

    public void addTransition(char c, int from, int to) {
	State fromS = getState(from);
	State toS = getState(to);
	fromS.nextState.put(c,toS);
	legalChars.add(c);
    }

    @Override
    public String toString() {
	String retVal = "";
	retVal += "FSA: \n";	
	for(Map.Entry<Integer,State> entry : states.entrySet()) {
	    int num = entry.getKey();
	    State s = entry.getValue();
	    retVal += "\t " + num + ": " + s.toString() + "\n"; 
	}
	return retVal;
    }

    @Override public boolean equals(Object obj) {	
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    FiniteStateAutomaton other = (FiniteStateAutomaton) obj;
	    return other.toString().equals(obj.toString());
    }
	
    @Override public int hashCode() {
	return this.toString().hashCode();
    }

    public void debuggingOutput(String prefix) {
	if (debug) {
	    String s = prefix;
	    if (input!=null && pos >= 0 && pos < input.length()) {
		s += "c='" + input.charAt(pos) + "'";
	    }
	    s +=  " currState=" + (currState==null?"null":currState.num);
	    s +=  " nextState=" + (nextState==null?"null":nextState.num);
	    s +=  " input=" + (input==null?"null":"\"" + input+ "\"");
	    s +=  " pos=" + pos + " accumulatedToken=" + accumulatedToken;
	    System.out.println(s);
	}
    }

    public void resetToStart() {
	this.startState = states.get(0);
	this.currState = startState;
	this.accumulatedToken = "";
	if (this.currState==null) {
	    throw new IllegalStateException("FSA instance does not have a start state (state 0) defined.");
	}
    }

    private char currChar() {
	if (this.input == null || pos >= this.input.length() ) {
	    throw new IllegalStateException("current character is undefined");
	}
	return this.input.charAt(pos);
    }

    public Token nextToken() {

	debuggingOutput("top of nextToken: ");

	if (this.input == null) {
	    throw new IllegalStateException("must call setInput before calling nextToken");
	}
		
	if (this.getRemainingInput().equals("")) {
	    return null;
	}

	if (this.currState==null) {
	    throw new IllegalStateException("No current state; add a state 0, and call setInput");
	}

	this.nextState = this.currState.nextState.get(currChar());

	if (this.nextState == null) {
	    // No transition for current character.
	    // Emit error token, consuming current character
	    String s = "" + currChar();
	    Token t = new ErrorToken(s);
	    this.pos++;
	    resetToStart();	    
	    return t;			    
	}

	// Otherwise, we have a transition for this character.
	// As long as we continue to have a next state,
	// add this character to the token, and go to that state

	debuggingOutput("nextToken, before while loop: ");
	
	while (this.nextState != null) {	    
	    this.accumulatedToken += currChar();
	    this.currState = this.nextState; // advance the state
	    this.pos++;                      // consume the character
	    debuggingOutput("nextToken, top of while loop: ");
	    
	    if ( this.pos >= this.input.length() ) {
		// If we've reached the end, don't look at the character
		// There is no transition we can make
		this.nextState = null; 
	    } else {
		this.nextState = this.currState.nextState.get(currChar());
	    }
	}

	debuggingOutput("nextToken, after while loop: ");
	
	if (currState.tm != null) {
	    Token t = currState.tm.makeToken(accumulatedToken.trim());
	    resetToStart();
	    return t;
	}

	if (!accumulatedToken.trim().equals("")) {
	    Token t = new ErrorToken(accumulatedToken.trim());
	    resetToStart();
	    return t;
	} else if (this.pos < input.length() && !legalChars.contains(currChar()) ) {
	    Token t = new ErrorToken("" + currChar());
	    pos ++;
	    resetToStart();
	    return t;
	}

	resetToStart();	
	return null;
    }
}
