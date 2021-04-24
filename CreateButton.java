package project;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/*************************************************************
 * CreateButton class.
 * 
 * A JButton that implements AddOneEvent interface. 
 * This JButton is the Control part of the MVC pattern.
 * 
 * Functionality: 
 * 		When this button is pressed, the program will show a 
 * 		dialog prompting user to enter information to create 
 * 		a new event. Then, it will use all these information
 * 		to create a new event.
 *************************************************************/

public class CreateButton extends JButton implements AddOneEvent {

	private static final long serialVersionUID = 3061503606761029315L;

	/* 
	 * Constructor method.
	 * ------
	 * Create a CreateButton object
	 * 
	 * @param data A Data object which is also an ActionListener. It will be
	 * 			   added to the ActionListener list so that it will be 
	 * 			   activated when user clicks on this button. 
	 */
	public CreateButton(Data data) {
		super("Create");
		
		addActionListener(data);
		
	}

	/* 
	 * getEvent method.
	 * ------
	 * This method uses a CreateNewEventPanel to ask users for information
	 * to create an Event.
	 * 
	 * @return an Event object that is created from information entered
	 * 		   by the user
	 */
	@Override
	public Event getEvent() {
		CreateNewEventPanel cNEP = new CreateNewEventPanel();
		
		int result = cNEP.ask();
		
				
		if(result == CreateNewEventPanel.OK) {
			String name = "";
			LocalDate date = null;
			TimeInterval timeI = null;
						
			
			try {

				name = cNEP.getEventName();
				date =  cNEP.getDate();
				timeI = cNEP.getTimeInterval();
				
				return new Event(name, date, timeI);
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
			}
			

		}
		
		return null;
	}
		
		
		
}
