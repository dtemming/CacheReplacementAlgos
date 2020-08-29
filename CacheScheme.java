/*
 * Abstract superclass for all the cache schemes to be tested.
 */
import java.util.HashMap;

public abstract class CacheScheme {
	// Return the number of times a piece of data must be replaced. 
	// Inputs are cacheSize and the string representing each data
	// request.  There are no restrictions on which characters
	// the string may contain.
	public abstract int numCollisions(int cacheSize, String word);
}
