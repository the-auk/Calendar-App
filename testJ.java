package project;
//import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

//import org.junit.jupiter.api.Test;

class testJ {

	//@Test
	void test() {
		ArrayList<TimeInterval> tIArrL = new ArrayList<TimeInterval>();
		EventList eL = new EventList();
		
		tIArrL.add(new TimeInterval("2:00", "3:00"));
		tIArrL.add(new TimeInterval("6:00", "7:00"));
		tIArrL.add(new TimeInterval("1:00", "2:00"));
		tIArrL.add(new TimeInterval("4:00", "5:00"));
		tIArrL.add(new TimeInterval("9:30", "12:20"));
		tIArrL.add(new TimeInterval("3:00", "4:00"));
		tIArrL.add(new TimeInterval("15:00", "17:00"));
		tIArrL.add(new TimeInterval("5:00", "6:00"));
		
		
		for(int i = 0; i < tIArrL.size(); i++) {
			eL.addEvent(new Event("Event " + i, LocalDate.now(), tIArrL.get(i)));		
			
		}
		
		
		eL.addEvent(new Event("Event last 1", LocalDate.now().minusDays(1), new TimeInterval("5:00", "6:00")));
		eL.addEvent(new Event("Event last 1", LocalDate.now().minusDays(1), new TimeInterval("9:00", "16:00")));
		
		
		eL.addEvent(new Event("Event last 2", LocalDate.now().minusDays(2), new TimeInterval("9:00", "10:00")));
		eL.addEvent(new Event("Event last 2", LocalDate.now().minusDays(2), new TimeInterval("6:00", "7:00")));
		

		eL.addEvent(new Event("Event next", LocalDate.now().plusDays(3), new TimeInterval("4:00", "5:00")));
		eL.addEvent(new Event("Event next", LocalDate.now().plusDays(3), new TimeInterval("1:00", "2:00")));
		eL.addEvent(new Event("Event next", LocalDate.now().plusDays(3), new TimeInterval("7:00", "9:00")));
		
		
		
		ArrayList<Event> eA = eL.getEvents(LocalDate.now().minusDays(1), LocalDate.now().plusDays(5));
		
		for(int i = 0; i < eA.size(); i++) {
			Event e = eA.get(i);
			String printS =  e.getName() + " --- " 
							+ e.getFormattedTime(Event.DAY_OF_WEEK + ": " + Event.DAY_OF_MONTH + 
												 ", " + Event.MONTH + ", " + Event.YEAR)
							+ " ---- " + e.getTimeInterval();
							
			
			System.out.println(printS);
		}
		
		
		
		
	}

}
