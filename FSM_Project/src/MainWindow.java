import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;


public class MainWindow extends JFrame implements ActionListener{
	
	private JTextField placesInput;
	private JTextField transitionInput;	
	
	private JFrame mainFrame;
	
	private boolean validPlacesAndTransitions = false; //To make sure user has valid # of places/transitions

	
	private JLabel coverabiltiyLabel = new JLabel("");
	private JLabel reachLabel = new JLabel("");
	
	//static array lists to be set in other classes when data is entered
	public static ArrayList<Integer> inputArray;
	public static ArrayList<Integer> outputArray;
	public static ArrayList<Integer> initialMarkingArray;
	
	//static boolean values for if data was submitted for inputs, outputs, and markings
	public static boolean inputDoneClicked = false;
	public static boolean outputDoneClicked = false;
	public static boolean markingDoneClicked = false;
	
	public static void main(String[] args) {
		new MainWindow("Petri Net");
	}
	
	public MainWindow(String title) {
		super(title);
		createWindow();
	}
	
	/*
	 * Creates the frame along with the buttons and text fields for the main window
	 */
	public void createWindow() {
		
		mainFrame = new JFrame("Petri Net");
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mainFrame.setSize(600, 600);;
		JPanel mainPanel = new JPanel();
		
		//Labels for what the user has to enter
		JLabel placesLabel = new JLabel("# of Places");
		JLabel transitionLabel = new JLabel("# of Transitions");
		placesLabel.setSize(100, 50);
		transitionLabel.setSize(100, 50);
		
		
		//Text fields for the places and transitions
		placesInput = new JTextField();
		transitionInput = new JTextField();
		placesInput.setPreferredSize(new Dimension(50,30));
		transitionInput.setPreferredSize(new Dimension(50,30));
		
		//Creating the 3 buttons for the inputs, transitions and submit
		JButton setInputsBtn = new JButton("Enter Inputs");
		setInputsBtn.addActionListener(this);
		setInputsBtn.setActionCommand("inputsBtn");
		
		JButton setOutputsBtn = new JButton("Enter Ouputs");
		setOutputsBtn.addActionListener(this);
		setOutputsBtn.setActionCommand("outputsBtn");
		
		JButton setMarkingBtn = new JButton("Enter Intial Marking");
		setMarkingBtn.addActionListener(this);
		setMarkingBtn.setActionCommand("markingBtn");
		
		JButton submitBtn = new JButton("SUBMIT");
		submitBtn.addActionListener(this);
		submitBtn.setActionCommand("submitBtn");
		
		JButton coverBtn = new JButton("Coverability");
		coverBtn.addActionListener(this);
		coverBtn.setActionCommand("coverBtn");
		
		JButton reachBtn = new JButton("Reachibility");
		reachBtn.addActionListener(this);
		reachBtn.setActionCommand("reachBtn");
		
		//Adding all the components to the panel
		mainPanel.add(placesLabel);
		mainPanel.add(placesInput);
		mainPanel.add(transitionLabel);
		mainPanel.add(transitionInput);
		mainPanel.add(setInputsBtn);
		mainPanel.add(setOutputsBtn);
		mainPanel.add(setMarkingBtn);
		mainPanel.add(submitBtn);
		mainPanel.add(reachBtn);
		mainPanel.add(coverBtn);

		
		JPanel coverPanel = new JPanel();
		mainPanel.add(coverabiltiyLabel);
		
		JPanel reachPanel = new JPanel();
		mainPanel.add(reachLabel);
		
		//Creates a flow layout from left to right for the panel
		FlowLayout topLayout = new FlowLayout();
		mainPanel.setLayout(topLayout);
		mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		mainPanel.add(coverPanel);
		mainPanel.add(reachPanel);
		//Adds the panel to the frame
		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(placesInput.getText().equals("") || transitionInput.getText().equals("")) {
			JOptionPane.showMessageDialog(mainFrame, "Must Enter values for Places and Transitions","Input Error",JOptionPane.ERROR_MESSAGE);
		}
		else { 
			int p = Integer.parseInt(placesInput.getText());
			int t = Integer.parseInt(transitionInput.getText());
			
			if(p >= t){ 
				validPlacesAndTransitions = true; 
			}
			else {
				JOptionPane.showMessageDialog(mainFrame, "Number of Places must be greater than or equal to Transitions","Input Error",JOptionPane.ERROR_MESSAGE);
			}
			
			
			if(validPlacesAndTransitions) {
				
				if(e.getActionCommand().equals("inputsBtn")) { //If the inputs button is clicked
					
					InputOutputDialogBox inputBox = new InputOutputDialogBox(p,t, "Inputs", mainFrame, true, false);
					inputBox.setSize(300, 300);
					inputBox.setVisible(true);

				}
				else if(e.getActionCommand().equals("outputsBtn")) { //If the outputs button is clicked
					
					InputOutputDialogBox outputBox = new InputOutputDialogBox(p,t, "Outputs", mainFrame, false, false);
					outputBox.setSize(300, 300);
					outputBox.setVisible(true);

				}
				else if(e.getActionCommand().equals("markingBtn")) { //If markings button is clicked
					InputOutputDialogBox markingBox = new InputOutputDialogBox(p,t, "Initial Marking", mainFrame, false, true);
					markingBox.setSize(200, 100);
					markingBox.setVisible(true);
				}	
				else if(e.getActionCommand().equals("submitBtn")) { //If submit is clicked
					
					if(inputDoneClicked && outputDoneClicked && markingDoneClicked){
						System.out.println("READY!");
//						createPetriNet();
					}
					else {
						JOptionPane.showMessageDialog(mainFrame, "Must input all data!","Input Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(inputDoneClicked && outputDoneClicked && markingDoneClicked && e.getActionCommand().equals("reachBtn")){
					System.out.println("IN REACH BTN");
					createPetriNet(true);
					setReachabilityLabel();
				}
				else if(inputDoneClicked && outputDoneClicked && markingDoneClicked && e.getActionCommand().equals("coverBtn")){
					createPetriNet(false);
					setCoverabilityLabel();
				}	
			}
		}
	}
	
	
	public void setCoverabilityLabel() {
		
		String coverLabelString ="<html>";
		
		for(int i = 0; i < PetriNet.stringMarkingsCoverability.size(); i++) {
			coverLabelString = coverLabelString + PetriNet.stringMarkingsCoverability.get(i) + "<br>";
		}
		
		coverLabelString = coverLabelString + "</html>";
		
		coverabiltiyLabel.setText(coverLabelString);
		
	}
	
	public void setReachabilityLabel() {
		
		String reachLabelString ="<html>";
		
		for(int i = 0; i < PetriNet.stringMarkingsReachability.size(); i++) {
			reachLabelString = reachLabelString + PetriNet.stringMarkingsReachability.get(i) + "<br>";
		}
		
		reachLabelString = reachLabelString + "</html>";
		
		reachLabel.setText(reachLabelString);
		
	}
	
	public void createPetriNet(boolean isReach) {
		
		if(isReach) {
			int p = Integer.parseInt(placesInput.getText());
			int t = Integer.parseInt(transitionInput.getText());
			System.out.println("THERE ARE " + p + " PLACES & " + t + " TRANSITIONS");
			PetriNet net = new PetriNet(inputArray, outputArray, initialMarkingArray, p);
			net.printTrans();
			//net.getCoverabilityMarkings();
			net.getAllMarkings();
//			net.printMarkings();
			//net.printStringMarkings();
			net.setStringMarkingsReach();
		}
		else{
			int p = Integer.parseInt(placesInput.getText());
			int t = Integer.parseInt(transitionInput.getText());
			System.out.println("THERE ARE " + p + " PLACES & " + t + " TRANSITIONS");
			PetriNet net = new PetriNet(inputArray, outputArray, initialMarkingArray, p);
			net.printTrans();
			net.getCoverabilityMarkings();
			//net.getAllMarkings();
			//net.printMarkings();
			net.printStringMarkings();
			net.setStringMarkingsReach();
		}
	}
}

