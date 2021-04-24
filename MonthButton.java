package project;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;

/**************************************************************
 * MonthButton class.
 * 
 * A JButton class that also implemented ChangeView interface.
 * This Button is the Control part of the MVC pattern.
 * 
 * Functionality: change the currentView attribute on StateMachine
 * 				  to MONTH_VIEW. This creates 2 effects:
 *  				- Make the ViewPanel only shows Events that are
 * 				  	  scheduled on a specific month.
 *                  - Make the CalendarStatus show date on the format:
 *                    "SCHEDULE FOR MM, yyyy"
 *************************************************************/
public class MonthButton extends JButton implements ChangeView {

	private static final long serialVersionUID = -3735304713766570726L;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a MonthButton object
	 * 
	 * @param a an ActionListener that will be activated when this button is pressed.
	 * 			It is actually the StateMachine that implements ActionListener
	 * 			interface. The StateMachine will listen to the click on MonthButton
	 * 			and change its currentView attribute to MONTH_VIEW.
	 */
	public MonthButton(ActionListener a) {
		super("Month");
		
		addActionListener(a);
	}
	
	/*
	 * getView method.
	 * ------
	 * This method is from the ChangeView interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * @return the integer constant MONTH_VIEW, declared in StateMachine.
	 *         This constants will become the value of currentView attribute
	 *         of the StateMachine after the StateMachine receives a MouseEvent
	 *         emitted from this button. 
	 */
	@Override
	public int getView() {
		return StateMachine.MONTH_VIEW;
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
	 * @return null. This method does not have functionality in MonthButton. 
	 */
	@Override
	public LocalDate[] getPeriod() {
		return null;
	}

}
