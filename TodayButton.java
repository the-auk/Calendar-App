package project;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;


/**************************************************************
 * TodayButton class.
 * 
 * A JButton that implements ChangeTime interface.
 * This JButton is the Control part of the MVC pattern. 
 * 
 * Functionality: Shift the period specified in StateMachine to
 * current period. In particular:
 * 			- If the currentView attribute of StateMachine is
 * 			  DAY_VIEW, when this button is pressed, the period
 * 			  specified in StateMachine will change to current day
 * 			  (today).
 *  		- If the currentView attribute of StateMachine is
 * 			  WEEK_VIEW, when this button is pressed, the period
 * 			  specified in StateMachine will change to current week
 * 			  (this week).
 * 			- If the currentView attribute of StateMachine is
 * 			  MONTH_VIEW, when this button is pressed, the period
 * 			  specified in StateMachine will change to current month
 * 			  (this month).
 * 			- If the currentView attribute of StateMachine is
 * 			  AGENDA_VIEW, when this button is pressed, the period
 * 			  specified in StateMachine will change to the new period
 * 			  with the same length but has the first day to be today. 
 *************************************************************/

public class TodayButton extends JButton implements ChangeTime {

	private static final long serialVersionUID = -445525408939041850L;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a DayButton object
	 * 
	 * @param a an ActionListener that will be activated when this button is pressed.
	 * 			It is actually the StateMachine that implements ActionListener
	 * 			interface. The StateMachine will listen to the click on this button
	 * 			and change its current specified period to the period that has the
	 * 			first day to be today but its length remains the same.
	 */
	public TodayButton(ActionListener a) {
		super("Today");
				
		addActionListener(a);
	}

	
	/*
	 * getDirection method.
	 * ------
	 * This method is from the ChangeTime interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * @return the integer constant SPECIFIC_DATE, declared in ChangeTime 
	 * 		   interface. Upon receiving this constant, the StateMachine 
	 * 		   will change its current specified period to the period that
	 * 		   has the first day to be today but its length remains the same.
	 */
	@Override
	public int getDirection() {
		return ChangeTime.SPECIFIC_DATE;
	}

	
	/*
	 * getDate method.
	 * ------
	 * This method is from the ChangeTime interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * @return a LocalDate object specified today's date. It will be used
	 * 		   by the setSpecificDate method of the StateMachine to shift
	 * 		   its specified period to the current period.
	 */
	@Override
	public LocalDate getDate() {
		return LocalDate.now();
	}

}
