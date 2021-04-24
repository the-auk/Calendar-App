package project;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;

/**************************************************************
 * DayButton class.
 * 
 * A JButton class that also implemented ChangeView interface.
 * This Button is the Control part of the MVC pattern.
 * 
 * Functionality: change the currentView attribute on StateMachine
 * 				  to DAY_VIEW. This creates 2 effects:
 *  				- Make the ViewPanel only shows Events that are
 * 				  	  scheduled on a specific day.
 *                  - Make the CalendarStatus show date on the format:
 *                    "SCHEDULE FOR mm/dd/yyyy"
 *************************************************************/
public class DayButton extends JButton implements ChangeView {

	private static final long serialVersionUID = -7572212569254401628L;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a DayButton object
	 * 
	 * @param a an ActionListener that will be activated when this button is pressed.
	 * 			It is actually the StateMachine that implements ActionListener
	 * 			interface. The StateMachine will listen to the click on DayButton
	 * 			and change its currentView attribute to DAY_VIEW.
	 */
	public DayButton(ActionListener a) {
		super("Day");
		
		addActionListener(a);
	}
	
	
	/*
	 * getView method.
	 * ------
	 * This method is from the ChangeView interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * @return the integer constant DAY_VIEW, declared in StateMachine.
	 *         This constants will become the value of currentView attribute
	 *         of the StateMachine after the StateMachine receives a MouseEvent
	 *         emitted from this button. 
	 */
	@Override
	public int getView() {
		return StateMachine.DAY_VIEW;
	}

	
	/*
	 * getPeriod method.
	 * ------
	 * This method is from the ChangeView interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * Since AgendaButton needs this method to specify the period being 
	 * displayed on ViewPanel and AgendaButton is also ChangeView button, 
	 * getPeriod is added to the ChangeView interface. Thus, this method 
	 * is only used in AgendaButton. In all other ChangeView component, 
	 * it only returns null, including this one.
	 * 
	 * @return null. This method does not have functionality in DayButton. 
	 */
	@Override
	public LocalDate[] getPeriod() {
		return null;
	}

}
