import java.util.*;

public class PetriNet {
	
	private ArrayList<Transition> transitions = new ArrayList<Transition>();
	
	private ArrayList<Marking> markings = new ArrayList<Marking>();
	private ArrayList<Marking> coverabilityMarkings = new ArrayList<Marking>(); 
	
	public static ArrayList<ArrayList<String>> stringMarkingsCoverability = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> stringMarkingsReachability = new ArrayList<ArrayList<String>>();
	
	private int numberOfPlaces;
	
	public PetriNet(ArrayList<Integer> inputs, ArrayList<Integer> outputs, ArrayList<Integer> initialMarking, int p) {
		
		numberOfPlaces = p;
		Marking initMarking = new Marking(initialMarking);
		markings.add(initMarking);
		coverabilityMarkings.add(initMarking);
		markings.get(0).printMarking();
		createTransitions(inputs, outputs, p);
	}
	
	
	public void printStringMarkings() {
		
		for(int i =0; i < coverabilityMarkings.size(); i++) {
			ArrayList<String> strToAdd = coverabilityMarkings.get(i).convertToString();
			stringMarkingsCoverability.add(strToAdd);
		}
		
		for(int i = 0; i < stringMarkingsCoverability.size(); i++) {
			System.out.println(stringMarkingsCoverability.get(i));
		}
		
	}
	
	
	public void setStringMarkingsReach() {
		for(int i =0; i < markings.size(); i++) {
			ArrayList<String> strToAdd = markings.get(i).convertToString();
			stringMarkingsReachability.add(strToAdd);
		}
	}
	
	public void printMarkings() {
		
		System.out.println("MARKING SIZE: " + markings.size());
		
		for(Marking m: markings) {
			m.printMarking();
		}
	}
	
	
	/*
	 * Prints out the inputs and outputs of each transition in the petri net
	 */
	public void printTrans() {
		
		for(Transition t: transitions) {
			t.printTransition();
			
			if(t.isEnabled(markings.get(0))) {
				System.out.println(t.getName() + " IS ENABLED");
			}
			else {
				System.out.println(t.getName() + " NOT ENABLED");
			}
		}	
		
	}

	
	/*
	 * Gets all the possible markings and adds them to the markings arraylist
	 */
	public void getAllMarkings() {
		
		for(int i = 0; i < markings.size(); i++) {
			ArrayList<Transition> curET = getEnabledTransitions(markings.get(i));
			
			for(int j = 0; j < curET.size(); j++) {
				
				Marking newMarking = curET.get(j).getNewMarking(markings.get(i), numberOfPlaces);

				if(validNewMarking(newMarking)) {
					System.out.println("ADDED: " + newMarking.getMarking());
					markings.add(newMarking);
				}
			}
		}
	}
	
	
	public void getCoverabilityMarkings() {
		
		for(int i = 0; i < coverabilityMarkings.size(); i++) {
			ArrayList<Transition> curET = getEnabledTransitions(coverabilityMarkings.get(i));
			
			for(int j = 0; j < curET.size(); j++) {
				
				Marking newMarking = curET.get(j).getNewMarking(coverabilityMarkings.get(i), numberOfPlaces);
				checkForOmega(newMarking);
				
				
				if(validNewMarkingCover(newMarking)) {
					System.out.println("ADDED: " + newMarking.getMarking());
					coverabilityMarkings.add(newMarking);
				}
			}
		}
	}
	
	
	
	
	public void checkForOmega(Marking newestMarking) {
		
		ArrayList<Integer> newMarkingNums = newestMarking.getMarking();
		
		int count = 0;
		ArrayList<Integer> indiciesToChange = new ArrayList<Integer>();
		
		
		System.out.println("CHECKING OMEGA FOR");
		System.out.println(newMarkingNums);
		for(int i = 0; i < coverabilityMarkings.size(); i++) {
			ArrayList<Integer> curMarkingNums = coverabilityMarkings.get(i).getMarking();
			indiciesToChange.clear();
			
			count = 0;
			
			for(int j = 0; j < curMarkingNums.size(); j++) {
				if(newMarkingNums.get(j) >= curMarkingNums.get(j)) {
					count++;
					if(newMarkingNums.get(j) > curMarkingNums.get(j)) {
						indiciesToChange.add(j);
					}
				}
				else {
					continue;
				}
			}
			
			if(count == numberOfPlaces){
				for(int k = 0; k < indiciesToChange.size(); k++){
					for(int l = 0; l < newMarkingNums.size(); l++) {
						if(indiciesToChange.get(k) == l) {
							newMarkingNums.set(l, 99);
						}
					}
				}
			}
			
//			if(count == numberOfPlaces) {
//				return true;
//			}
		}
		
//		return false;
	}
	
	
	
	/*
	 * Checks if the marking is "old" if so return false
	 */
	public boolean validNewMarking(Marking markingToFind) {
		
		ArrayList<Integer> markingToFindNums = markingToFind.getMarking();
		
		int count = 0;
		
		
		for(int i = 0; i < markings.size(); i++) {
			ArrayList<Integer> curMarkingNums = markings.get(i).getMarking();
			
			count = 0;
			
			for(int j = 0; j < curMarkingNums.size(); j++) {
				if(curMarkingNums.get(j) == markingToFindNums.get(j)) {
					count++;
				}
			}
			
			if(count == numberOfPlaces) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validNewMarkingCover(Marking markingToFind) {
		
		ArrayList<Integer> markingToFindNums = markingToFind.getMarking();
		
		int count = 0;
		
		
		for(int i = 0; i < coverabilityMarkings.size(); i++) {
			ArrayList<Integer> curMarkingNums = coverabilityMarkings.get(i).getMarking();
			
			count = 0;
			
			for(int j = 0; j < curMarkingNums.size(); j++) {
				if(curMarkingNums.get(j) == markingToFindNums.get(j)) {
					count++;
				}
			}
			
			if(count == numberOfPlaces) {
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<Transition> getEnabledTransitions(Marking curMarking) {
		
		ArrayList<Transition> curEnabledTransitions = new ArrayList<Transition>();
		
		for(Transition t: transitions) {
			if(t.isEnabled(curMarking)) {
				curEnabledTransitions.add(t);
			}
		}
		
		return curEnabledTransitions;
	}
	

	/*
	 * Creates the transitions with the correct inputs and outputs based on what the user enters
	 */
	public void createTransitions(ArrayList<Integer> inputs, ArrayList<Integer> outputs, int p) {
	
		int pCount = 1;
		int tCount = 1;
		
		System.out.println("INPUTS: " + inputs);
		System.out.println("OUTPUTS: " + outputs);
		
		Transition t = new Transition("T" + tCount);
		for(int i = 0; i < inputs.size(); i++) {
			
			
			if(pCount <= p) {
				
				if(inputs.get(i) > 0) {
					Place newPlace = new Place(inputs.get(i), ("P"+pCount), pCount);
					t.addInputPlace(newPlace);
				}
				if(outputs.get(i) > 0) {
					Place newPlace = new Place(outputs.get(i), ("P"+pCount),  pCount);
					t.addOutputPlace(newPlace);
				}
				pCount++;
			}
			if(pCount > p) {
				transitions.add(t);
				tCount++;
				t = new Transition("T" + tCount);
				pCount = 1;
			}
		}
		
	}
}
