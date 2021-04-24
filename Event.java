package project;
import java.time.LocalDate;


/**************************************************************
 * Event class.
 * 
 * Functionality: Represent an event scheduled on calendar with
 * 				  for attributes: date of the event, time interval,
 * 				  and name of the event.
 *************************************************************/

public class Event implements Comparable<Event>{
	static public final String DAY_OF_WEEK = "FORMAT/DAYOFWEEK";
	static public final String MONTH = "FORMAT/MONTH";
	static public final String DAY_OF_MONTH = "FORMAT/DAYOFMONTH";
	static public final String YEAR = "FORMAT/YEAR";
	
	private LocalDate date;
	private TimeInterval timeInterval;
	private String name;
	
	
	
	static private final String [] DAY_OF_WEEK_ARR = {"Monday", "Tuesday", "Wednesday",
	                                    "Thursday", "Friday", "Saturday", "Sunday"};
	
	static private final String [] MONTH_ARR = {"January", "February", "March", "April", "May",
										 "June", "July", "August", "September", "October",
										 "November", "December"};
	
	/* 
	 * Constructor method.
	 * ------
	 * Create an Event object. 
	 * 
	 * @param n a string contains name of the event
	 * @param d a Date object represents the date of the event
	 * @param t a TimeInterval object represents the time interval when the
	 * 		    event happens
	 */
	public Event(String n, LocalDate d, TimeInterval t) {
		name = n;
		date = d;
		timeInterval = t;
	}

	
	/* 
	 * getName method.
	 * ------
	 * An accessor that returns the name of the event. 
	 * @return the name of the event	 
	 */
	public String getName() {
		return name;
	}
	
	
	/*
	 * getDate method.
	 * ------
	 * An accessor that returns the date of the event.
	 * @return the date of the event 
	 */
	public LocalDate getDate() {
		return date;
	}
	
	
	/*
	 * getFormattedTime method.
	 * ------
	 * A accessor that returns the string represents when the event happens
	 * in a specified format
	 * 
	 * @param format a string specified the format of the string result
	 * 
	 * @return a string represents time following the format specified by 
	 * 		   the parameter format. 
	 */
	public String getFormattedTime(String format) {
		return format.replace(DAY_OF_WEEK, DAY_OF_WEEK_ARR[date.getDayOfWeek().getValue() - 1])
					 .replace(MONTH, MONTH_ARR[date.getMonthValue() - 1])
					 .replace(DAY_OF_MONTH, formatDayOfMonth(date.getDayOfMonth()))
					 .replace(YEAR, date.getYear() + "");
	}
	
	/*
	 * getTimeInterval method.
	 * ------
	 * An accessor that return the time interval of the event.
	 * @return the time interval of the event in string format (hh:mm - hh:mm) 
	 */
	public String getTimeInterval() {
		return timeInterval + "";
	}
	
	
	/*
	 * compareTo method. 
	 * ------
	 * Compare this Event with other Event based on the time they occurs.
	 * 
	 * @param o the other Event object to be compared to this Event
	 * 
	 * @return  1 if this Event is scheduled after o
	 *         -1 if this Event is scheduled before o
	 *          0 if there is a conflict in time between the two
	 */
	@Override
	public int compareTo(Event oE) {
		
		if(date.isBefore(oE.date)) return -1;
		
		if(date.isAfter(oE.date)) return 1;
		
		return timeInterval.compareTo(oE.timeInterval);
		
	}
	
	/*
	 * formatDayOfMonth method. 
	 * ------
	 * Change the day-of-month number from an integer to a string
	 * Add 0 to the value if it is less than 10.
	 * 
	 * @param dN an integer specifies the day-of-month number.
	 * 
	 * @return a string represents a day-of-month number specifies
	 * 		   by the parameter.
	 */
	private String formatDayOfMonth(int dN) {
		if(dN < 10) return "0" + dN;
		
		return dN + "";
 	}


}
