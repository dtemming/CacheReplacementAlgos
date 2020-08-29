import java.util.ArrayList;
import java.util.HashMap;

/*
 * David Temming
 * 
 * Least recently used cache algorithm
 * 
 * This algorithm replaces the data in the cache 
 * with the oldest last request.
 */


public class LRU extends CacheScheme {
	
	public int numCollisions(int cacheSize, String word){
		int numCollisions = 0;
		
		//hits value is never returned, it is used to keep track of collisions for debugging purposes
		int hits = 0;
		String keyForLastElement = "";
		String keyForFirstElement = "";
		
		HashMap<Character, Data> listPointer = new HashMap<Character,Data>();
		
		Data prevCharacter = new Data();
		prevCharacter = null;
		
		if(word.length() < cacheSize) {
			return 0;
		}
		//making the initial cache
		for(int i = 0; i < cacheSize; i++) {
			if(word.length() == 0) {
				return 0;
			}
			if(listPointer.containsKey(word.charAt(0))) {
				listPointer.get(keyForLastElement.charAt(0)).after = listPointer.get(word.charAt(0));
				if(listPointer.get(word.charAt(0)).before != null) {
					listPointer.get(word.charAt(0)).before.after = listPointer.get(word.charAt(0)).after;
				} 
				if(listPointer.get(word.charAt(0)).after != null) {
					listPointer.get(word.charAt(0)).after.before = listPointer.get(word.charAt(0)).before;
				}
				listPointer.get(word.charAt(0)).before = listPointer.get(keyForLastElement.charAt(0));
				listPointer.get(word.charAt(0)).after = null;
				keyForLastElement = Character.toString(word.charAt(0));
				if(listPointer.get(keyForLastElement.charAt(0)).before.before == null) {
					keyForFirstElement = Character.toString(listPointer.get(keyForLastElement.charAt(0)).before.letter);
				}
				prevCharacter = listPointer.get(keyForLastElement.charAt(0));
				word = word.substring(1, word.length());
				hits++;
				i--;
			} else {
				Data newCharacter = new Data();
				newCharacter.letter = word.charAt(0);
				newCharacter.before = prevCharacter;
				if(i != 0) {
					prevCharacter.after = newCharacter;
				}
				if(i == 0) {
					keyForFirstElement = Character.toString(word.charAt(0));
				} 
				keyForLastElement = Character.toString(word.charAt(0));
				listPointer.put(word.charAt(0), newCharacter);
				word = word.substring(1, word.length());
				prevCharacter = newCharacter;
			}
		}
		
		//determining hits versus collisions and replacing cache elements when collisions occur
		while(word.length() != 0) {
			if(listPointer.containsKey(word.charAt(0))) {
				if(Character.toString(word.charAt(0)).equals(keyForLastElement)) {
					//do nothing
				} 
				else {
					if(Character.toString(word.charAt(0)).equals(keyForFirstElement)) {
						keyForFirstElement = Character.toString(listPointer.get(keyForFirstElement.charAt(0)).after.letter);
					}
					listPointer.get(keyForLastElement.charAt(0)).after = listPointer.get(word.charAt(0));
					if(listPointer.get(word.charAt(0)).before != null) {
						listPointer.get(word.charAt(0)).before.after = listPointer.get(word.charAt(0)).after;
					} 
					if(listPointer.get(word.charAt(0)).after != null) {
						listPointer.get(word.charAt(0)).after.before = listPointer.get(word.charAt(0)).before;
					}
					listPointer.get(word.charAt(0)).before = listPointer.get(keyForLastElement.charAt(0));
					listPointer.get(word.charAt(0)).after = null;
					keyForLastElement = Character.toString(word.charAt(0));
				}
				word = word.substring(1, word.length());
				hits++;

			} else {
				numCollisions++;
				Data newCharacter = new Data();
				newCharacter.letter = word.charAt(0);
				
				char keyForSecondElement = listPointer.get(keyForFirstElement.charAt(0)).after.letter;
				
				listPointer.get(keyForSecondElement).before = null;
				listPointer.remove(keyForFirstElement.charAt(0));
				keyForFirstElement = Character.toString(keyForSecondElement);
				
				
				listPointer.get(keyForLastElement.charAt(0)).after = newCharacter;
				newCharacter.before = listPointer.get(keyForLastElement.charAt(0));
				newCharacter.after = null;
				keyForLastElement = Character.toString(word.charAt(0));
				
				listPointer.put(word.charAt(0), newCharacter);
				word = word.substring(1, word.length());
			}
		}
		return numCollisions;
	}
}

