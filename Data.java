/*
 * Data representing information about a data request.
 * The instance variables are public to make accessing information
 * efficient.
 */
public class Data implements Comparable<Data>{
	//for linked list
	Data before = null;
	Data after = null;
	int time = 0;
	
	Character letter = null;
	boolean cached = false;
	int frequency = 0;
	
	//Compare based on frequency
	public int compareTo(Data other){
		if(this.frequency == other.frequency) {
			return this.time - other.time;
		}
		return this.frequency-other.frequency;
	}
}
