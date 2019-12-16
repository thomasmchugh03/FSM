public class Place {
	
	private int numTokens; //More of a number for the arc will prob change variable name
	private String name;
	private int placeNumber; //Which place the token is if there are 4 places could be 1,2,3, or 4
	
	public Place(int numTokens, String name, int placeNumber) {
		
		this.numTokens = numTokens;
		this.name = name;
		this.placeNumber = placeNumber;
	}
	
	
	public int getPlaceNumber() {
		return placeNumber;
	}
	
	/*
	 * Returns the number of tokens in the place
	 */
	public int getNumTokens() {
		return numTokens;
	}

	/*
	 * returns the name of the place 
	 */
	public String getName() {
		return name;
	}
}