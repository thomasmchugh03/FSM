import java.util.*;

public class Transition {

	private ArrayList<Place> inputPlaces = new ArrayList<Place>();
	
	private ArrayList<Place> outputPlaces = new ArrayList<Place>();
	
	private String name;
	
	
	public Transition(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	/*
	 * Checks if the transition is enabled
	 */
	public boolean isEnabled(Marking curMarking) {
		
		ArrayList<Integer> numPlacesNeeded = new ArrayList<Integer>(); //From what places do you need tokens from
		ArrayList<Integer> curMarkingNums = curMarking.getMarking(); //The current marking
		
		//Puts which places you need tokens from into array
		for(int i = 0; i < inputPlaces.size(); i++) {
			numPlacesNeeded.add(inputPlaces.get(i).getPlaceNumber());
		}
	
		//Count for how many places have enough tokens to send
		int count = 0;
		
		//curMarkingNums.get(numPlacesNeeded.get(i) - 1) is for getting the right marking number; Adds to count if enough tokens
		for(int i = 0; i < numPlacesNeeded.size(); i++) {
			if(inputPlaces.get(i).getNumTokens() <= curMarkingNums.get(numPlacesNeeded.get(i) - 1)) {
				count++;
			}
		}
		
		if(count == numPlacesNeeded.size()) {
			return true;
		}
		else {return false;}
		
	}
	
	public Marking getNewMarking(Marking curMarking, int p) {
		
		Marking minusMarking = minusInput(curMarking, p);
		Marking addedMarking = addOutputs(minusMarking, p);
		
		return addedMarking;
	}
	
	public Marking minusInput(Marking curMarking, int p) {
		
		ArrayList<Integer> newMarkings = new ArrayList<Integer>();
		
		for(int i = 0; i < curMarking.getMarking().size(); i++) {
			newMarkings.add(curMarking.getMarking().get(i));
		}
		
		
		for(int i = 0; i < inputPlaces.size(); i++) {
			
			int markingIndex = inputPlaces.get(i).getPlaceNumber() - 1;
			int inputNum = inputPlaces.get(i).getNumTokens();
 			int newMarkingNum = curMarking.getMarking().get(markingIndex) - inputNum;
 			
 			newMarkings.set(markingIndex, newMarkingNum);
		}
		
		Marking newMarking = new Marking(newMarkings);
		return newMarking;
		
	}
	
	public Marking addOutputs(Marking curMarking, int p) {
		
		ArrayList<Integer> newMarkings = new ArrayList<Integer>();
		
		for(int i = 0; i < curMarking.getMarking().size(); i++) {
			newMarkings.add(curMarking.getMarking().get(i));
		}
		
		for(int i = 0; i < outputPlaces.size(); i++) {
			
			int markingIndex = outputPlaces.get(i).getPlaceNumber() - 1;
			int outputNum = outputPlaces.get(i).getNumTokens();
 			int newMarkingNum = curMarking.getMarking().get(markingIndex) + outputNum;
 			
 			newMarkings.set(markingIndex, newMarkingNum);
		}
		
		Marking newMarking = new Marking(newMarkings);
		return newMarking;
		
	} 
	
	/*
	 * prints which places are inputs and outputs for the transitions
	 * USED FOR TESTING
	 */
	public void printTransition() {
		
		System.out.println("This is transition: " + name + " Here are the places for its inputs" );
		
		for(Place p: inputPlaces) {
			System.out.println(p.getName());
		}
		
		System.out.println("This is transition: " + name + " Here are the places for its outputs" );
		
		for(Place p: outputPlaces) {
			System.out.println(p.getName());
		}
	}
	
	/*
	 * Adds a new inputPlace to the transition
	 */
	public void addInputPlace(Place p) {
		inputPlaces.add(p);
	}
	
	/*
	 * Adds a new outputPlace for the transition
	 */
	public void addOutputPlace(Place p) {
		outputPlaces.add(p);
	}
	
}
