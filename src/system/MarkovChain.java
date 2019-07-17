/** Author: Helen Lily Hu */
package system;

import java.util.HashMap;
import java.util.LinkedList;

/** An instance represents a Markov chain where the states are strings */
public class MarkovChain {

	/** Contains all the strings which are states in the chain, <br>
	 * Each key represents a state, with its value being a list of all the<br>
	 * states reachable from that state */
	private HashMap<String, LinkedList<String>> chain;

	/** Constructor: initializes an empty Markov chain for string states */
	public MarkovChain() {
		chain= new HashMap<>();
	}

	/** Adds a transition from the current string/state to the next string/state<br>
	 * If the transition current -> next already exists, then its weight is increased<br>
	 * If there is no state corresponding to current in the chain already,<br>
	 * then the current state is added to the chain with the transition */
	public void addTransition(String current, String next) {
		LinkedList<String> transitionsFromCurrent= chain.get(current);
		if (transitionsFromCurrent == null) {
			// create transitions list for current state
			LinkedList<String> transitions= new LinkedList<>();
			transitions.add(next);
			// add state and transitions list to chain
			chain.put(current, transitions);
		} else {
			// add current -> next transition to existing transitions list
			transitionsFromCurrent.add(next);
		}
	}

	/** Transitions stochastically from current to next string; returns next string <br>
	 * Precondition: current is a state with transitions in the chain */
	public String transition(String current) {
		LinkedList<String> transitions= chain.get(current);
		// get a random index of the transitions list
		int randomIndex= (int) ((transitions.size() - 1) * Math.random());
		// get random next state
		return transitions.get(randomIndex);
	}

	/** Returns a random string/state in the chain <br>
	 * Precondition: chain is not empty */
	public String getRandomState() {
		int randomIndex= (int) ((chain.size() - 1) * Math.random());
		return (String) chain.keySet().toArray()[randomIndex];
	}

	/** Returns the number of states in the chain */
	public int getNumStates() {
		return chain.size();
	}

	/** Returns the number of states s has transitions to; */
	public int getTransitionsListSize(String s) {
		LinkedList<String> transitions= chain.get(s);
		if (transitions == null) { return 0; }
		return transitions.size();
	}

}
