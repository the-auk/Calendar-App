package project;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**************************************************************
 * StateMachine class.
 * 
 * It has an ArrayList of ChangeListeners that listens to any 
 * changes in current view and current period of time to act 
 * accordingly. The ChangeListeners of StateMachine in this 
 * program are ViewPanel, CalendarStatus, and Data. This class 
 * also implements the ActionListener interface to listen to 
 * the clicks on ChangeView and ChangeTime buttons.
 * 
 * The StateMachine is the Model part of the MVC pattern.
 * 
 * Functionality: 
 *	 - It keeps track of the current view of the program by its 
 *	   currentView attribute. The current view can be either 
 *     DAY_VIEW, WEEK_VIEW, MONTH_VIEW, or AGENDA_VIEW, depends 
 *     on which type of ChangeView button has been clicked most 
 *     recently.
 *   - It also keeps track of the current period of time by two
 *     LocalDate attributes: currentFirstDay and currentLastDay,
 *     in which currentLastDay is one day after the real last day
 *     of the current period.     
 *************************************************************/
public class StateMachine implements ActionListener {
	public static final int DAY_VIEW = 1111;
	public static final int WEEK_VIEW = 2222;
	public static final int MONTH_VIEW = 3333;
	public static final int AGENDA_VIEW = 4444;
	
	private int currentView;
	private LocalDate currentFirstDay;
	private LocalDate currentLastDay;

	private ArrayList<ChangeListener> listeners;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a StateMachine object. 
	 * The currentView attribute is initialized to DAY_VIEW and the current
	 * period is set to the current day (today). An ArrayList of ChangeListener 
	 * is also initialized.
	 */
	public StateMachine() { 
		currentView = DAY_VIEW;
		currentFirstDay = LocalDate.now();
		currentLastDay = LocalDate.now().plusDays(1);
		
		listeners = new ArrayList<ChangeListener>();
	}

	
	/* 
	 * attach method.
	 * ------
	 * Attach a ChangeListener object the StateMachine. This ChangeListener
	 * will be activated when there is a change in either currentView or
	 * current period (specified by currentFirstDay and currentLastDay).
	 * 
	 * @param l a ChangeListener that will be added to an ArrayList of
	 * 			ChangeListeners of this StateMachine object.
	 */
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
	
		
	/* 
	 * getFirstDay method.
	 * ------
	 * An accessor that returns the first day of the current period.
	 * 
	 * @param a LocalDate specifies the first day of the current period.
	 */
	public LocalDate getFirstDay() {
		return currentFirstDay;
	}

	
	/* 
	 * getLastDay method.
	 * ------
	 * An accessor that returns the day after the last day of the current 
	 * period.
	 * 
	 * @param a LocalDate specifies the day after the last day of the 
	 * 		  current period.
	 */
	public LocalDate getLastDay() {
		return currentLastDay;
	}
	
	
	/* 
	 * getCurrentView method.
	 * ------
	 * An accessor that returns the value of the currentView attribute of
	 * the StateMachine. Other classes will compare this value to the value 
	 * of the constants DAY_VIEW, MONTH_VIEW, and AGENDA_VIEW defined in 
	 * this StateMachine class to know the current view of the program.
	 * 
	 * @return an integer specifies the value of the currentView attribute.
	 */
	public int getCurrentView() {
		return currentView;
	}
	

	
	/* 
	 * actionPerformed method.
	 * ------
	 * Since StateMachine is also an ActionListener that listens to any click 
	 * on ChangeTime and ChangeView buttons, it implements the actionPerformed 
	 * method. 
	 * 
	 * This method simply gets the reference to the source object (either 
	 * ChangeView or ChangeTime button) and passes it to the updateState method.
	 * 
	 * @param e an ActionEvent that is emitted by one of the ChangeTime/ChangeView
	 * button.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		updateState(e.getSource());
			
	}
	
	
	/* 
	 * updateState method.
	 * ------
	 * This method will be called by actionPerformed method to update the current
	 * state of the program accordingly. Then, the updateListeners method will be
	 * called to update the change to all of the ChangeListeners of this StateMachine
	 * (ViewPanel and CalendarStatus). 
	 * 
	 * @param source an Object that is either ChangeView or ChangeTime object. 
	 * 				 -	If it is a ChangeView object, the currentView attribute 
	 * 					will receive the new value from getView method of the 
	 * 					ChangeView object. Then changeView method will be called 
	 * 					to update the current period according to the new value 
	 * 					of the currentView attribute.	 * 
	 * 				 - 	If it is a ChangeTime object, the shiftCurrentPeriod method 
	 * 					will be called to update the currentFirstDay and currentLastDay 
	 * 					accordingly depend on the direction value gotten from getDirection
	 * 					method of the ChangeTime object.
	 */
	public void updateState(Object source) {
		if(source instanceof ChangeView){
			ChangeView cv = (ChangeView)source;
			
			LocalDate[] period = cv.getPeriod();
			
			if(cv.getView() != AGENDA_VIEW || period != null) {
				currentView = cv.getView();
				changeView(period);
			}

		}

		if (source instanceof ChangeTime) {
			ChangeTime ct = (ChangeTime)source;
			
			int direction = ct.getDirection();
			
			shiftCurrentPeriod(direction, ct.getDate());
			
		}
		
		
		updateListeners();
		
	}
	
	
	
	/* 
	 * changeView method.
	 * ------
	 * This method will be called by the updateState method to update currentFirstDay
	 * and currentLastDay attributes of the StateMachine according to the value of
	 * the currentView attribute. In particular:
	 * 			- If currentView is equal to DAY_VIEW, currentFirstDay remains the same
	 * 			  while currentLastDay will be the next day of the currentFirstDay.
	 * 			- If currentView is equal to WEEK_VIEW, the new value of currentFirstDay 
	 * 			  will be set to Sunday of the week that the old value of currentFirstDay
	 * 			  is on. The currentLastDay will be set to the next Sunday right after
	 * 			  currentFirstDay.
	 * 			- If currentView is equal to MONTH_VIEW, the new value of currentFirstDay 
	 * 			  will be set to the first day of the month that the old value of 
	 * 			  currentFirstDay is on. The currentLastDay will be set to the first day
	 * 			  of the next month.
	 * 	  		- If currentView is equal to AGENDA_VIEW, the new value of currentFirstDay 
	 * 			  will be set to the value of period[0] where period is an array of 2 
	 * 			  LocalDates passed to changeView method by parameter. currentLastDay will
	 * 			  be set to period[1].
	 * 
	 * @param period an array of two LocalDate object. The first specifies the first day
	 * 				 of the period displayed in AGENDA_VIEW. The second specifies the
	 * 				 day after the last day of the period.
	 */
	private void changeView(LocalDate[] period) {
		switch(currentView) {
			case DAY_VIEW:
				currentLastDay = currentFirstDay.plusDays(1);
				break;
			case WEEK_VIEW:
				int daysFromSunday = currentFirstDay.get(ChronoField.DAY_OF_WEEK)%7; 				
				currentFirstDay = currentFirstDay.minusDays(daysFromSunday);
				currentLastDay = currentFirstDay.plusWeeks(1);
				break;
			case MONTH_VIEW:
				int daysFromFirstDay = currentFirstDay.get(ChronoField.DAY_OF_MONTH) - 1; 				
				currentFirstDay = currentFirstDay.minusDays(daysFromFirstDay);
				currentLastDay = currentFirstDay.plusMonths(1);
				break;
			case AGENDA_VIEW:
				currentFirstDay = period[0];
				currentLastDay = period[1];
				break;
		}
	}
	 
	
	/* 
	 * shiftCurrentPeriod method.
	 * ------
	 * This method will shift the current period display on ViewPanel by changing 
	 * the value of currentFirstDay and currentLastDay according to the value of 
	 * the direction parameter. It will call shiftBackward, setSpecificDate, and
	 * shiftForward methods to do the job.
	 *  
	 * @param direction an integer specifies the direction of the shift. It will
	 * 					be compared to the value of the three constants that were
	 * 					defined in the ChangeTime interface (BACKWARD, SPECIFIC_DATE,
	 * 					and FORWARD) to determine which method among shiftBackward, 
	 * 					setSpecificDate, and shiftForward to be called.
	 * @param date 		a LocalDate object. If direction is equal to SPECIFIC_DATE, this
	 * 					object will be used to set the current period to that exact date
	 */
	private void shiftCurrentPeriod(int direction, LocalDate date) {			
		switch(direction) {
			case ChangeTime.BACKWARD:
				shiftBackward();
				break;
			case ChangeTime.SPECIFIC_DATE:
				setSpecificDate(date);
				break;
			case ChangeTime.FORWARD:
				shiftForward();
				break;
		}
	}
	
	
	/* 
	 * shiftBackward method.
	 * ------
	 * This method will shift the current period backward in time while its length 
	 * stays the same. In particular:
	 * 		-	If the currentView is equal to DAY_VIEW, the current period will
	 * 			be shifted to the day right before the current day.
	 *		-	If the currentView is equal to WEEK_VIEW, the current period will
	 * 			be shifted to the week right before the current week.
	 * 	 	-	If the currentView is equal to MONTH_VIEW, the current period will
	 * 			be shifted to the month before the current month.
	 * 	 	-	If the currentView is equal to AGENDA_VIEW, the current period will
	 * 			be shifted to the same-length-period right before the current period.
	 */
	private void shiftBackward() {
		switch(currentView) {
			case DAY_VIEW:
				currentLastDay = currentLastDay.minusDays(1);
				currentFirstDay = currentFirstDay.minusDays(1);
				break;
			case WEEK_VIEW:
				currentLastDay = currentLastDay.minusWeeks(1);
				currentFirstDay = currentFirstDay.minusWeeks(1);
				break;
			case MONTH_VIEW:
				currentLastDay = currentLastDay.minusMonths(1);
				currentFirstDay = currentFirstDay.minusMonths(1);
				break;
			case AGENDA_VIEW:
				int interval = Period.between(currentFirstDay, currentLastDay).getDays();
				currentLastDay = currentLastDay.minusDays(interval);
				currentFirstDay = currentFirstDay.minusDays(interval);				
				break;
		}
	}
	

	/* 
	 * setSpecificDate method.
	 * ------
	 * This method will set the current period to the period that contains the
	 * date specified by the LocalDate parameter. In particular:
	 * 		- If the currentView is equal to DAY_VIEW, this method will set the
	 * 		  current period to its LocalDate parameter.
	 * 		- If the currentView is equal to WEEK_VIEW, this method will set the
	 * 		  current period to the week that contains the day specified by its 
	 * 		  LocalDate parameter.
	 * 		- If the currentView is equal to MONTH_VIEW, this method will set the
	 * 		  current period to the month that contains the day specified by its 
	 * 		  LocalDate parameter. 
	 * 		- If the currentView is equal to AGENDA_VIEW, this method will set the
	 * 		  current period to a new period that has the same length as the old 
	 * 		  period but has the first day specified by its LocalDate parameter.
	 *  
	 * @param date a LocalDate that will be used to set the current period.
	 */
	private void setSpecificDate(LocalDate date) {
			switch(currentView) {
				case DAY_VIEW:
					currentFirstDay = date;
					currentLastDay = date.plusDays(1);
					break;
				case WEEK_VIEW:
					int daysFromSunday = date.get(ChronoField.DAY_OF_WEEK)%7;
					currentFirstDay = date.minusDays(daysFromSunday);
					currentLastDay = currentFirstDay.plusDays(7);
					break;
				case MONTH_VIEW:
					int daysFromFirstDay = date.get(ChronoField.DAY_OF_MONTH ) - 1; 
					currentFirstDay = date.minusDays(daysFromFirstDay);
					currentLastDay = currentFirstDay.plusMonths(1);
					break;
				case AGENDA_VIEW:
					int interval = Period.between(currentFirstDay, currentLastDay).getDays();
					currentFirstDay = date;
					currentLastDay = date.plusDays(interval);
					break;
				
		}
	
	}
	
	/* 
	 * shiftForward method.
	 * ------
	 * This method will shift the current period forward in time while its length 
	 * stays the same. In particular:
	 * 		-	If the currentView is equal to DAY_VIEW, the current period will
	 * 			be shifted to the day right after the current day.
	 *		-	If the currentView is equal to WEEK_VIEW, the current period will
	 * 			be shifted to the week right after the current week.
	 * 	 	-	If the currentView is equal to MONTH_VIEW, the current period will
	 * 			be shifted to the month after the current month.
	 * 	 	-	If the currentView is equal to AGENDA_VIEW, the current period will
	 * 			be shifted to the same-length-period right after the current period.
	 */
	private void shiftForward() {
		switch(currentView) {
		case DAY_VIEW:
			currentLastDay = currentLastDay.plusDays(1);
			currentFirstDay = currentFirstDay.plusDays(1);
			break;
		case WEEK_VIEW:
			currentLastDay = currentLastDay.plusWeeks(1);
			currentFirstDay = currentFirstDay.plusWeeks(1);
			break;
		case MONTH_VIEW:
			currentLastDay = currentLastDay.plusMonths(1);
			currentFirstDay = currentFirstDay.plusMonths(1);
			break;
		case AGENDA_VIEW:
			int interval = Period.between(currentFirstDay, currentLastDay).getDays();
			currentLastDay = currentLastDay.plusDays(interval);
			currentFirstDay = currentFirstDay.plusDays(interval);				
			break;
		}
	}
	
	
	/* 
	 * updateListener method.
	 * ------
	 * This method will notify the ChangeListeners when there is any change in the
	 * program state. The ChangeListeners of this StateMachine in this program are
	 * Data, ViewPanel, and CalendarStatus.
	 */
	private void updateListeners() {
		for(int i = 0; i < listeners.size(); i++) {
			listeners.get(i).stateChanged(new ChangeEvent(this));
		}
	}
	
	
	
}
