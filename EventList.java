package project;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**************************************************************
 * EventList class.
 * Main data structure for the program. An aggregated ArrayList of 
 * Event 
 * 
 * Functionality: 
 * 		- Return a series of Event that happens during a specific 
 * 		  period of time.
 * 		- Add an event in chronologically order.
 *************************************************************/

public class EventList {
	private ArrayList<Event> list;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create an empty EventList object. 
	 */
	public EventList() {
		list = new ArrayList<Event>();
	}
	
	
	/* 
	 * addEvent method.
	 * ------
	 * Add event method.  
	 * 
	 * @param e an Event to be added to the list in chronologically order
	 * @return a boolean value specifies whether the adding process is succeed 
	 * 		   or not. Adding failed when the event e is conflict with one of 
	 * 		   the events in the list.
     */
	public boolean addEvent(Event e) {
		if(list.size() == 0) {
			list.add(e);
			return true;
		}
		
		
		Iterator<Event> it = list.iterator();
		
		Event currE = it.next();
		
		int i = 0;
		
		while(currE.compareTo(e) < 0 && it.hasNext()) {
			currE = it.next();
			i++;
		}
		
		if(currE.compareTo(e) == 0) 
			return false;
		
		if(currE.compareTo(e) > 0) {
			list.add(i, e);
			return true;
		}
		
		
		list.add(e);
		
		
		return true;
		
	}
	

	/* 
	 * getEvents method.
	 * ------
	 * Get a series of events in a specified period of time.  
	 * 
	 * @param from a LocalDate specifies the first day of the period
	 * @param to a LocalDate specifies the last day of the period
	 * @return an ArrayList contains all events that are scheduled from 
	 * 		   the Date from to the Date to, inclusively.
     */
	public ArrayList<Event> getEvents(LocalDate from, LocalDate to){
		ArrayList<Event> resultArr = new ArrayList<Event>();
		
		if(list.size() != 0 && list.get(0) != null) {
			Event fromE = new Event("From Event", from, new TimeInterval("00:00", "00:00"));
			Event toE = new Event("To Event", to, new TimeInterval("00:00", "00:00"));
			
			
			Iterator<Event> it = list.iterator();
			
			Event currE = it.next();
					 
			while(currE.compareTo(fromE) < 0 && it.hasNext()) {
				currE = it.next();
			}


			
			while(currE.compareTo(toE) < 0 && it.hasNext()) {
				resultArr.add(currE);
				currE = it.next();
			}

			
			if(currE.compareTo(toE) < 0 && currE.compareTo(fromE) >= 0) resultArr.add(currE); 
			
		}
		
		
		
		return resultArr;
	}
	
	

}
