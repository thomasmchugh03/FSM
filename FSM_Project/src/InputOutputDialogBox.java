import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class InputOutputDialogBox extends JDialog {

	
	private ArrayList<JTextField> textFields = new ArrayList<JTextField>(); //Array for the text fields
	private ArrayList<JTextField> markingTextFields = new ArrayList<JTextField>(); //Array for the text fields
	
	private boolean isMarkingBox;
	
	/*
	 *  isInputBox is for knowing if entering inputs or outputs
	 */
	public InputOutputDialogBox(int places, int transitions, String title, JFrame frame, boolean isInputBox, boolean isMarkingBox) {
		setTitle(title);
		
		if(isMarkingBox) {
			this.isMarkingBox = isMarkingBox;
			createMarkingBox(places, frame);
		}
		else{
			createBoxes(places, transitions, frame, isInputBox);
		}
	}
	
	
	public void createMarkingBox(int p, JFrame frame) {
		
		GridLayout layout = new GridLayout(0, p, 5, 5); //Creates the correctly sized grid layout 
		
		JPanel panel = new JPanel(); 
		
		panel.setLayout(layout); //set the panel to the grid layout
		
		for(int i = 0; i < p; i++) {
			JLabel label = new JLabel("P" + (i+1), SwingConstants.CENTER);
			panel.add(label);
		}
		
		for(int i =0; i < p; i++) {
			JTextField tf = new JTextField();
			tf.setPreferredSize(new Dimension(10, 10));
			markingTextFields.add(tf);
			panel.add(tf);
		}
		

		//Makes a done button and checks if all the text fields have been entered and closes the box if all fields entered
		JButton doneBtn = new JButton("Done");
		doneBtn.setSize(100, 50);
		doneBtn.addActionListener(new ActionListener() {
			
			  public void actionPerformed(ActionEvent evt) {
				  if(!didEnterData()) {
					  JOptionPane.showMessageDialog(frame, "Must fill all markings!","Input Error",JOptionPane.ERROR_MESSAGE);
				  }
				  else {
					  MainWindow.markingDoneClicked = true;
					  MainWindow.initialMarkingArray = getEnteredData();
					  dispose();
				}
				 
			}
		});
		
		panel.add(doneBtn);
		
		this.add(panel);
		
		
	}
	
	
	/*
	 * This creates the JTextFields correctly for the number of transitions and places
	 */
	public void createBoxes(int p, int t, JFrame frame, boolean isInputBox) {
		
		GridLayout layout = new GridLayout(0, p+ 1, 5, 5); //Creates the correctly sized grid layout 
		
		JPanel panel = new JPanel(); 
		
		panel.setLayout(layout); //set the panel to the grid layout
		
		//Make all the labels
		for(int i = 0; i < p + 1; i++) {
			
			if(i == 0) {
				JLabel label = new JLabel(" ", SwingConstants.CENTER);
				panel.add(label);
			}
			else{
				JLabel label = new JLabel("P" + (i), SwingConstants.CENTER);
				panel.add(label);
			}
		}
		
		int offset = p - t; //Needed for the making the grid correctly
		int tCount = 1;
		
		//Makes the transition labels and the JTextFields for the dialog box
		for(int i = 0; i < (p * t); i++) {
			
			if(i % (t+offset) == 0) {
				JLabel transLabel = new JLabel("T" + tCount, SwingConstants.CENTER);
				panel.add(transLabel);
				tCount++;
			}
			
			JTextField tf = new JTextField();
			tf.setPreferredSize(new Dimension(10, 10));
			textFields.add(tf);
			panel.add(tf);
		}
		
		//Makes a done button and checks if all the text fields have been entered and closes the box if all fields entered
		JButton doneBtn = new JButton("Done");
		doneBtn.setSize(100, 50);
		doneBtn.addActionListener(new ActionListener() {
			
			  public void actionPerformed(ActionEvent evt) {
				 
				  if(!didEnterData()) {
					  JOptionPane.showMessageDialog(frame, "Must fill all inputs!","Inout Error",JOptionPane.ERROR_MESSAGE);
				 }
				 else{
					 if(isInputBox) {
						 MainWindow.inputDoneClicked = true;
						 MainWindow.inputArray = getEnteredData();
						 dispose();
					 }
					 else {
						 MainWindow.outputDoneClicked = true;
						 MainWindow.outputArray = getEnteredData();
						 dispose();
					 }
				 }
			  }
		});
		
		panel.add(doneBtn);
		this.add(panel);
	}
	
	/*
	 * Gets all the inputs as integers from the text fields 
	 */
	public ArrayList<Integer> getEnteredData() {
		
		ArrayList<Integer> data = new ArrayList<Integer>();
		
		if(isMarkingBox) {
			for(JTextField tf: markingTextFields) {
				int num = Integer.parseInt(tf.getText());
				data.add(num);
			}
		}
		else {
			for(JTextField tf: textFields) {
				int num = Integer.parseInt(tf.getText());
				data.add(num);
			}
		}
		
		return data;
	}
	
	/*
	 * Checks if something was entered in all fields
	 */
	public boolean didEnterData() {
	
		if(isMarkingBox) {
			for(JTextField tf: markingTextFields) {
				if(tf.getText().equals("")) {
					tf.setText("0");
				}
			}
		}
		else {
			for(JTextField tf: textFields) {
				if(tf.getText().equals("")) {
					tf.setText("0");
				}
			}
		}
		return true;
	}
}
