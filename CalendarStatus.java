package project;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/*************************************************************
 * CalendarStatus class.
 * 
 * A JLabel that implements ChangeListener interface. 
 * This JLabel is placed right above the ViewPanel. It is also
 * attached to StateMachine so that whenever there is a change
 * in the current period or the currentView of StateMachine, 
 * its text will change accordingly.
 * 
 * This JPanel is the View part of the MVC pattern.
 * 
 * Functionality: 
 *		This JLabel's content will change according to the current
 *		state specified in StateMachine. In particular:
 *			- If the currentView attribute of StateMachine is
 *			  DAY_VIEW, this JLabel will display the string
 *			  "SCHEDULE FOR <mm/dd/yyyy>" with <mm/dd/yyyy> is the
 *			  date specified by the current period in StateMachine. 
 *			- If the currentView attribute of StateMachine is
 *			  WEEK_VIEW, this JLabel will display the string
 *			  "SCHEDULE FOR WEEK: <mm/dd/yyyy> - <mm/dd/yyyy>" 
 *			  with the first <mm/dd/yyyy> is the first date of the 
 *			  week specified by the current period in StateMachine 
 *			  and the second one is the last date of the week.
 *			- If the currentView attribute of StateMachine is
 *			  MONTH_VIEW, this JLabel will display the string
 *			  "SCHEDULE FOR <MM, yyyy>" with <MM, yyyy> is the
 *			  month specified by the current period in StateMachine.
 *			- If the currentView attribute of StateMachine is
 *			  AGENDA_VIEW, this JLabel will display the string
 *			  "SCHEDULE FROM <mm/dd/yyyy> TO <mm/dd/yyyy>" with 
 *			  the first <mm/dd/yyyy> is the first date of the 
 *			  period specified in StateMachine and the second 
 *			  one is the last date of the period.
 *************************************************************/
public class CalendarStatus extends JLabel implements ChangeListener{
	private static final long serialVersionUID = 4514030908350309144L;
	
	private int width = Constants.CALENDAR_STATUS_WIDTH;
	private int height = Constants.ELEMENT_HEIGHT;
	private Color color = Constants.CALENDAR_STATUS_COLOR;
	
	private StateMachine stateMachine; 
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a CalendarStatus object. 
	 * 
	 * This constructor will assign the main StateMachine of the program to its
	 * StateMachine attribute. Then it will call the setOutlook method to set
	 * the outlook of the label. Finally, it will call the setContent method to
	 * set the text content of the label according to the current state in the 
	 * StateMachine.
	 *  
	 * @param sm a StateMachine object. This is also the StateMachine of the program.
	 * 		     CalendaStatus needs this StateMachine to get the current state of
	 * 			 the program to display its content accordingly.
	 */
	public CalendarStatus(StateMachine sm) {
		super();
		
		stateMachine = sm;
		
		setOutlook();		
		setContent();
	}
	
	/*
	 * stateChanged method.
	 * ------
	 * This method is from the ChangeListener interface. It will be called 
	 * when there is any change of the current state in the StateMachine. 
	 * When being called, it, in turn, will call setContent method to change
	 * the text display in CanlendarStatus label according to the current
	 * state in the StateMachine.
	 * 
	 * @param e A ChangeEvent that is emitted by the StateMachine when there
	 * 			is a change in its current state.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		setContent();
	}
	
	
	/*
	 * setOutlook method.
	 * ------
	 * This method is called in the constructor to set the outlook of the
	 * CalendarStatus label, including size, margin, font, and background
	 * color.
	 */
	private void setOutlook() {
		setPreferredSize(new Dimension(width, height));
		setBorder(new EmptyBorder(0,10,0,0));
		
		Font cSFont = new Font("Arial", Font.BOLD, height/3);
		setFont(cSFont);
		
		setOpaque(true);
		setBackground(color); 
	}
	
	
	/*
	 * setContent method.
	 * ------ 
	 * This method is used to change the text displayed in CalendarStatus 
	 * label depending on the current state of the StateMachine. In
	 * particular:
	 * 		- If the currentView attribute of StateMachine is
 *			  DAY_VIEW, this method will change the string displayed
 *			  by CalendarStatus to "SCHEDULE FOR <mm/dd/yyyy>" 
 *			  with <mm/dd/yyyy> is the date specified by the current 
 *			  period in StateMachine. 
 *			- If the currentView attribute of StateMachine is
 *			  WEEK_VIEW, this method will change the string displayed
 *			  by CalendarStatus to "SCHEDULE FOR WEEK: <mm/dd/yyyy> 
 *			  - <mm/dd/yyyy>" with the first <mm/dd/yyyy> is the first 
 *			  date of the week specified by the current period in 
 *			  StateMachine and the second one is the last date of the 
 *			  week.
 *			- If the currentView attribute of StateMachine is MONTH_VIEW, 
 *			  this method will change the string displayed by CalendarStatus 
 *			  to "SCHEDULE FOR <MM, yyyy>" with <MM, yyyy> is the month 
 *			  specified by the current period in StateMachine.
 *			- If the currentView attribute of StateMachine is AGENDA_VIEW, 
 *			  this method will change the string displayed by CalendarStatus 
 *			  to "SCHEDULE FROM <mm/dd/yyyy> TO <mm/dd/yyyy>" with the first 
 *			  <mm/dd/yyyy> is the first date of the period specified in 
 *			  StateMachine and the second one is the last date of the period.
	 */
	private void setContent() {
		removeAll();
		revalidate();
		
		String text = "SCHEDULE ";
		DateTimeFormatter formatter;
		switch(stateMachine.getCurrentView()) {
			case StateMachine.DAY_VIEW:
				formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				text += " FOR " + stateMachine
									.getFirstDay()
									.format(formatter);
			   break;
			case StateMachine.MONTH_VIEW:
				formatter = DateTimeFormatter.ofPattern("LLLL, yyyy");
				text += " FOR " + stateMachine
									.getFirstDay()
									.format(formatter);
				break;
			case StateMachine.WEEK_VIEW:
				formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				text += " FOR WEEK: " + stateMachine
											.getFirstDay()
											.format(formatter)
									  + " - "
									  + stateMachine
											.getLastDay()
									  		.minusDays(1)
									  		.format(formatter);
				break;
				
			case StateMachine.AGENDA_VIEW:
				formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				text += " FROM " + stateMachine
											.getFirstDay()
											.format(formatter)
									  + " TO "
									  + stateMachine
											.getLastDay()
									  		.minusDays(1)
									  		.format(formatter);
				break;
			
		}
		
		setText(text);
		
		
		repaint();
	}
	
}
