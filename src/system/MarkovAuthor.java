/** Author: Helen Lily Hu */
package system;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.scene.text.Text;

/** An instance reads a website's text, assembles a Markov chain from the text,<br>
 * and writes its own nonsensical text based off that Markov chain <br>
 * Uses jsoup to parse HTML */
public class MarkovAuthor {

	/** The Markov chain most recently created */
	private MarkovChain chain;

	/** Text to write */
	private String text;

	/** Parses the web address and creates a Markov chain from its text <br>
	 * Stores that chain in the chain field */
	public void read(String address) {
		// create new chain so read doesn't add to previous chain data
		chain= new MarkovChain();

		// fetch and parse html to get readable web site content
		Document d= null;
		try {
			d= Jsoup.connect(address).get();
		} catch (IllegalArgumentException | IOException e) {
			// web site link is wack, so don't read
			return;
		}
		String text= d.text();

		// demarcate words by spaces
		String[] textSpaceStripped= text.split(" ");
		int lastIndex= textSpaceStripped.length - 1;
		// add words to chain one by one
		for (int k= 0; k < lastIndex; k++ ) {
			String current= textSpaceStripped[k];
			// do not add empty strings to chain -> empty strings are rare, but may appear
			if (!current.equals("")) {
				// check for common end punctuation
				int i= current.length() - 1;
				char lastChar= current.charAt(i);
				if (lastChar == '.' || lastChar == '?' || lastChar == '!') {
					// treat end punctuation as a separate word/state
					String woEnd= current.substring(0, i);
					current= Character.toString(lastChar);
					chain.addTransition(woEnd, current);
				}
				// add transition from one word to the word following it
				chain.addTransition(current, textSpaceStripped[k + 1]);
			}
		}
	}

	/** Writes 10 sentences of text based off the Markov chain in the chain field<br>
	 * and sets that text to Text t */
	public void write(Text t) {
		// refresh text
		text= "";

		// check for empty chain --> usually means bad url
		if (chain.getNumStates() == 0) {
			// display error message and don't process chain
			t.setText("Oops! Something went wrong. Check your link.");
			return;
		}

		// write 10 sentences to text field
		for (int k= 0; k < 10; k++ ) {
			writeSentence();
		}

		// set text field to GUI component t
		t.setText(text);
	}

	/** Writes a sentence of text based off the Markov chain in the chain field<br>
	 * Helper for write */
	private void writeSentence() {
		// get first word in sentence & capitalize first letter
		String current= chain.getRandomState();
		current= Character.toString(Character.toUpperCase(current.charAt(0))) +
			current.substring(1);
		// add words and spaces to sentence until reach end punctuation
		while (!current.equals(".") && !current.equals("?") && !current.equals("!")) {
			// add word to sentence
			text+= current + " ";
			// if the current word has no transitions,
			if (chain.getTransitionsListSize(current) == 0) {
				// choose a random word and continue
				current= chain.getRandomState();
			} else {
				// else choose next word to process
				current= chain.transition(current);
			}
		}
		// add end punctuation to sentence
		text= text.substring(0, text.length() - 1);
		text+= current + " ";
	}
}
