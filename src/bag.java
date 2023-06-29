import java.util.ArrayList;
import java.util.Collections;

public class bag {
	private static ArrayList<String> bag;
	
	public bag() {
		bag = new ArrayList<String>();
	}
	public ArrayList<String> insideBag(){
		return bag;
	}
	public void addItem(String n, int amt) {
		for (int i = 0; i < amt; i++) {
			bag.add(n);
		}
	}
	public void removeItem(String n, int amt) {
	    int occurrences = Collections.frequency(bag, n);
	    if (occurrences < amt) {
	        throw new IllegalArgumentException("You don't have that item in your bag!");
	    } else {
	        for (int i = 0; i < amt; i++) {
	            bag.remove(n);
	        }
	    }
	}
	public boolean contains(String n) {
		int count = 0;
		for (int i = 0; i < bag.size(); i++) {
			if (bag.get(i).equals(n)) {
				count++;
			}
		}
		return count > 0;
	}
}
