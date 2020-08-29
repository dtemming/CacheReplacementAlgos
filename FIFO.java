
/*
 * David Temming
 * 
 * First in first out.  This scheme replaces the data
 * that has been in the cache the longest.
 */
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class FIFO extends CacheScheme {
	
	public int numCollisions(int cacheSize, String word) {
		int numCollisions = 0;
		//hits value is never returned, it is used to keep track of collisions for debugging purposes
		int hits = 0;
		HashMap<Character, Data> listPointer = new HashMap<Character,Data>();
		LinkedBlockingQueue<Data> ordering = new LinkedBlockingQueue<Data>();
		
		if(word.length() < cacheSize) {
			return 0;
		}
		//making the initial cache
		for(int i = 0; i < cacheSize; i++) {
			if(word.length() == 0) {
				return 0;
			}
			if(listPointer.containsKey(word.charAt(0))) {
				word = word.substring(1, word.length());
				hits++;
				i--;
			} else {
				Data newCharacter = new Data();
				newCharacter.letter = word.charAt(0);
				listPointer.put(word.charAt(0), newCharacter);
				ordering.add(newCharacter);
				word = word.substring(1, word.length());
			}
		}
		
		//determining hits versus collisions and replacing cache elements when collisions occur
		while(word.length() != 0) {
			if(listPointer.containsKey(word.charAt(0))) {
				word = word.substring(1, word.length());
				hits++;
			} else {
				numCollisions++;
				Data newCharacter = new Data();
				newCharacter.letter = word.charAt(0);
				char characterToRemove = ordering.poll().letter;
				ordering.add(newCharacter);
				listPointer.put(word.charAt(0), newCharacter);
				listPointer.remove(characterToRemove);
				word = word.substring(1, word.length());
			}
		}
		return numCollisions;
	}
}
