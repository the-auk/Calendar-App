package project;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/*************************************************************
 * AgendaButton class.
 * 
 * A JButton that implements ChangeView interface. 
 * This Button is the Control part of the MVC pattern.
 * 
 * Functionality: 
 * 		When this button is pressed, the program will show a 
 * 		dialog prompting user to enter the first date and last
 * 		date of the period they want to show on the ViewPanel.
 * 		Then, it will check for validity and send the dates to
 * 		StateMachine to shift its current specified period 
 * 		accordingly.
 *************************************************************/

public class AgendaButton extends JButton implements ChangeView {

	private static final long serialVersionUID = 7392483952823835729L;

	/* 
	 * Constructor method.
	 * ------
	 * Create an AgendaButton object
	 * 
	 * @param a an ActionListener that will be activated when this button is pressed.
	 * 			It is actually the StateMachine that implements ActionListener
	 * 			interface. The StateMachine will listen to the click on this button
	 * 			and change its currentView attribute to AGENDA_VIEW. Then, the current
	 * 			period specified by the StateMachine will be changed to the period it 
	 * 			gets from the getPeriod method of this button. 
	 */
	public AgendaButton(ActionListener a) {
		super("Agenda");
		
		addActionListener(a);
	}
	
	
	
	/*
	 * getView method.
	 * ------
	 * This method is from the ChangeView interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * @return the integer constant AGENDA_VIEW, declared in StateMachine. 
	 * 		   This constants will become the value of currentView attribute
	 *         of the StateMachine after the StateMachine receives a MouseEvent
	 *         emitted from this button. 
	 */
	@Override
	public int getView() {
		return StateMachine.AGENDA_VIEW;
	}

	
	/*
	 * getPeriod method.
	 * ------
	 * This method is from the ChangeView interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * When being called, this method will prompt user to enter the first date
	 * and last date of the period they want to show on the ViewPanel. Then, it
	 * will check for the validity (format and chronologically order - the first
	 * date must be before the last date). If these dates are valid, they will
	 * be returned in an array of 2 LocalDate objects. Otherwise, an error message
	 * will be shown. The prompt panel will be closed and no change will be made.
	 *
	 * @return an array of two LocalDate objects. The first one specifies the first
	 * 		   date of the period the user wants to show on ViewPanel. The last one
	 * 		   specifies the last date of that period.  
	 */
	@Override
	public LocalDate[] getPeriod() {
		AgendaPanel aP = new AgendaPanel();
		
		int result = aP.ask();
		
		if(result == AgendaPanel.OK) {
			
			try {
				LocalDate [] dates = new LocalDate[2];
				
				dates[0] = aP.getDate(AgendaPanel.FROM_DATE);
				dates[1] = aP.getDate(AgendaPanel.TO_DATE).plusDays(1);
				
				if(!dates[1].minusDays(1).isAfter(dates[0])) throw new Exception("From (date) must be earlier than To (date)");
								
				return dates;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return null;
	}

}
