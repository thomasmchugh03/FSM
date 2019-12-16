import java.util.*;

public class Marking {

	private ArrayList<Integer> marking = new ArrayList<Integer>();
	
	
	public Marking(ArrayList<Integer> markingToSet) {
		
		marking = markingToSet;
	}
	
	public ArrayList<Integer> getMarking() {
		return marking;
	}
	
	public ArrayList<String> convertToString() {
		
		ArrayList<String> stringMarking = new ArrayList<String>();
		
		for(int i = 0; i < marking.size(); i++) {
			
			String markingNum;
			
			if(marking.get(i) == 99) {
				markingNum = "w";
			}
			else {
				markingNum = marking.get(i) + "";
			}
			
			stringMarking.add(markingNum);			
		}
		
		return stringMarking;
	}
	
	public void printMarking() {
		System.out.println(marking);
	}
}
