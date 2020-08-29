import java.util.ArrayList;
import java.util.HashMap;

/*
 * David Temming
 * 
 * Implements the Furthest In Future Cache algorithm.
 * This algorithm's efficiency doesn't really matter
 * as long as it is tractable.
 * 
 * This is an oracle algorithm, giving a collision lower bounds.
 */

public class FIF extends CacheScheme{
	public int numCollisions(int cacheSize, String word){
		
		int numCollisions = 0;
		//hits value is never returned, it is used to keep track of collisions for debugging purposes
		int hits = 0;
		String cache = "";
		
		//Collisions are impossible in this case
		if(word.length() < cacheSize) {
			return 0;
		}
		
		//making the initial cache
		for(int i = 0; i < cacheSize; i++) {
			if(word.length() == 0) {
				return 0;
			}
			if(cache.indexOf(word.charAt(0)) != -1) {
				word = word.substring(1, word.length());
				hits++;
				i--;
			} else {
				cache = cache + word.charAt(0);
				word = word.substring(1, word.length());
			}
		}
		
		//determining hits versus collisions and replacing cache elements when collisions occur
		while(word.length() != 0) {
			if(cache.indexOf(word.charAt(0)) != -1) {
				word = word.substring(1, word.length());
				hits++;
			} else {
				numCollisions++;
				int maxIndex = -2;
				String farthestChar = "";
				
				for(int i = 0; i < cache.length(); i++) {
					if((word.indexOf(cache.charAt(i)) > maxIndex && maxIndex != -1) || (word.indexOf(cache.charAt(i)) == -1 && maxIndex != -1)) {
						maxIndex = word.indexOf(cache.charAt(i));
						farthestChar = Character.toString(cache.charAt(i));
					}
					else if(word.indexOf(cache.charAt(i)) == maxIndex) {
						if(i > cache.indexOf(farthestChar)) {
							maxIndex = word.indexOf(cache.charAt(i));
							farthestChar = Character.toString(cache.charAt(i));
						}
					}
				}
				
				cache = word.charAt(0) + cache.substring(0, cache.indexOf(farthestChar)) + cache.substring(cache.indexOf(farthestChar) + 1, cache.length());
				word = word.substring(1, word.length());
			}
		}
		return numCollisions;
	}
}
