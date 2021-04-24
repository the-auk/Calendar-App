package project;
import java.time.LocalDate;

/**************************************************************
 * ChangeView interface.
 * 
 *  This interface will be implemented by a JComponent so that 
 *  this component will be able to change the currentView attribute 
 *  of the StateMachine.
 *************************************************************/
public interface ChangeView {
	/*
	 * getView method.
	 * ------ 
	 * This method will be call in the StateMachine to change its
	 * currentView attribute.
	 * 
	 * @return an integer whose value will be equal to one of the four
	 * 		   constants defined in StateMachine: DAY_VIEW, WEEK_VIEW, 
	 * 		   MONTH_VIEW, or AGENDA_VIEW. 
	 */
	public int getView();
	
	
	/*
	 * getPeriod method.
	 * ------
	 * This method will be called in the StateMachine to change its current
	 * period.
	 * Most ChangeView components do not need this method, except AgendaButton.
	 * AgendaButton not only changes the currentView of the StateMachine to 
	 * AGENDA_VIEW but also sets the current period of the StateMachine.  
	 * As the result, in all ChangeView components other than AgendaButton, 
	 * it will return null.	 
	 *  
	 * @return an array of 2 LocalDate objects specify the start date and end date
	 * 		   of a period to be set as the current period in the StateMachine.
	 */
	public LocalDate[] getPeriod();
}
