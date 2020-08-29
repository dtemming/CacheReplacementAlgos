import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * David Temming
 * 
 * Least frequently used Cache algorithm.
 * This algorithm replaces the piece of data that has the least 
 * number of requests.
 */


public class LFU extends CacheScheme {

	public int numCollisions(int cacheSize, String word){
		
		int numCollisions = 0;
		//hits value is never returned, it is used to keep track of collisions for debugging purposes
		int hits = 0;
		HashMap<Character, Data> listPointer = new HashMap<Character,Data>();
		PriorityQueue<Data> ordering = new PriorityQueue<Data>();
		int time = 0;

		if(word.length() < cacheSize) {
			return 0;
		}
		//making the initial cache
		for(int i = 0; i < cacheSize; i++) {
			if(word.length() == 0) {
				return 0;
			}
			if(listPointer.containsKey(word.charAt(0))) {
				listPointer.get(word.charAt(0)).frequency++;
				listPointer.get(word.charAt(0)).time = time;
				ordering.remove(listPointer.get(word.charAt(0)));
				ordering.add(listPointer.get(word.charAt(0)));
				time++;
				
				word = word.substring(1, word.length());
				hits++;
				i--;
			}else {
				Data newCharacter = new Data();
				newCharacter.cached = true;
				newCharacter.frequency++;	
				newCharacter.letter = word.charAt(0);
				newCharacter.time = time;
				listPointer.put(word.charAt(0), newCharacter);
				ordering.add(newCharacter);
				time++;
				word = word.substring(1, word.length());
			}
		}
		
		//determining hits versus collisions and replacing cache elements when collisions occur
		while(word.length() != 0) {
			if(listPointer.containsKey(word.charAt(0)) && listPointer.get(word.charAt(0)).cached == true) {
				listPointer.get(word.charAt(0)).frequency++;				
				ordering.remove(listPointer.get(word.charAt(0)));
				ordering.add(listPointer.get(word.charAt(0)));
				word = word.substring(1, word.length());
				hits++;
			} else {
				Data newCharacter;
				numCollisions++;
				if(listPointer.containsKey(word.charAt(0)) == false) {
					newCharacter = new Data();
				} else {
					newCharacter = listPointer.get(word.charAt(0));
				}
				
				newCharacter.cached = true;
				newCharacter.frequency++;	
				newCharacter.letter = word.charAt(0);
				newCharacter.time = time;
				listPointer.get(ordering.poll().letter).cached = false;
				listPointer.put(word.charAt(0), newCharacter);
				ordering.add(newCharacter);
				time++;
				word = word.substring(1, word.length());
				
			}
		}
		return numCollisions;
	}
}
