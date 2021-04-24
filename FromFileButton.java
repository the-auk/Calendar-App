package project;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

/*************************************************************
 * FromFileButton class.
 * 
 * A JButton that implements AddMultipleEvents interface. 
 * This button is the Control part of the MVC pattern.
 * 
 * Functionality: 
 * 		When this button is pressed, the program will show a 
 * 		dialog prompting user to choose a text file. Then, it 
 * 		will use the FromFileToEvent class to parse all content
 * 		of this text file into an array of Events.
 *************************************************************/
public class FromFileButton extends JButton implements AddMultipleEvents{

	private static final long serialVersionUID = -6069641179677984507L;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a FromFileButton object
	 * 
	 * @param a an ActionListener. It is actually a Data object that 
	 * 			implements ActionListener interface. This Data object
	 * 			will be activate when there's a click on FromFileButton
	 * 			so that it can add the array of Events created by this 
	 * 			button to its EventList.   
	 */
	public FromFileButton(ActionListener a) {
		super("From File");
		
		addActionListener(a);
	}
	
	/*
	 * getEvents method.
	 * ------
	 * This method will show a dialog asking user to choose a file and then
	 * use a FromFileToEvents object to parse the content of this file 
	 * into an array of Event objects. 
	 * Since this method involves opening a file, the FileNotFoundException 
	 * is thrown.
	 * 
	 * @return an array of Event objects. 
	 */
	@Override
	public Event[] getEvents() throws FileNotFoundException {
		
		JFileChooser c = new JFileChooser(".\\");
	
		/*
		c.setFileFilter(new FileFilter() {

			   public String getDescription() {
			       return "TXT files (*.txt)";
			   }

			   public boolean accept(File f) {
			       if (f.isDirectory()) {
			           return true;
			       } else {
			           String filename = f.getName().toLowerCase();
			           return filename.endsWith(".txt");
			       }
			   }
			});
		
		*/
		int result = c.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = c.getSelectedFile();
			
			FromFileToEvents ffE = new FromFileToEvents(file);
			
			return ffE.getEvents();
		}
		
		
		return null;
	}

}
