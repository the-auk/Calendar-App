package project;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/*************************************************************
 * FromFileToEvents class.
 * 
 * This class is created exclusively to parse the formatted content 
 * of a text file into an array of Event objects. Its constructor
 * will get the file as via the parameter and its only public method,
 * getEvents, will return an array of Events parsed from that text
 * file.
 *************************************************************/
public class FromFileToEvents {
	private Event [] events;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a FromFileToEvents object. 
	 * 
	 * @param file a File object. This file will be parsed into an
	 * 		       array of Events.
	 */
	public FromFileToEvents(File file) throws FileNotFoundException  {
		
		ArrayList<Event> evL = new ArrayList<Event>();
		
		Scanner sc = new Scanner(file);
		
		while(sc.hasNextLine()) {
			Event[] evs = toEvents(sc.nextLine());
			
			for(int i = 0; i < evs.length; i++) {
				evL.add(evs[i]);
			}
		}
		
		events = evL.toArray(new Event[1]);
		
		sc.close();
	}
	
	/* 
	 * getEvents method.
	 * ------
	 * @return an array of Events that is parsed from the file received 
	 * 		   from the construction's parameter.
	 */
	public Event [] getEvents() {
		return events;
	}

	/* 
	 * toEvents method.
	 * ------
	 * Parse a formatted line of text to an array of Events.
	 * 
	 * @param line a formatted string to be parsed into an array of Events.
	 * 
	 * @return an array of Events that is parsed from the formatted line 
	 * 		   received from the parameter.
	 */
	private Event[] toEvents(String line) {
		String [] elms = line.split(";");
		
		String name = elms[0],
			   year = elms[1],
			   monthStart = elms[2],
			   monthEnd = elms[3],
			   dOWs = elms[4],
			   hourStart = elms[5],
			   hourEnd = elms[6];
		
		hourStart += ":00";
		hourEnd += ":00";
		  
		ArrayList<Event> alE = new ArrayList<Event>();
		
		for(int i = 0; i < dOWs.length(); i++) {
			LocalDate firstDayOfMonth = toDate("01/" + monthStart + "/" + year);
		
			
			int daysfromFirstDay =  ("MTWHFAS".indexOf(dOWs.charAt(i)) + 1)
										- firstDayOfMonth
											.getDayOfWeek()
											.getValue();
			
			
			LocalDate firstDayAfterLastMonth = toDate("01/" + monthEnd + "/" + year).plusMonths(1);
					
			LocalDate currentDate = firstDayOfMonth.plusDays(daysfromFirstDay);
					
			
			while(currentDate.isBefore(firstDayAfterLastMonth)) {
				alE.add(new Event(name, currentDate, new TimeInterval(hourStart, hourEnd)));
				
				currentDate = currentDate.plusWeeks(1);
			}
			
			
		}
		
		
		return alE.toArray(new Event[1]);
	}
	
	
	/* 
	 * toDate method.
	 * ------
	 * Parse a formatted string ("d/M/yyyy") into a LocalDate object.
	 * 
	 * @param sD a formatted string to be parsed into a LocalDate object.
	 * 
	 * @return A LocalDate object that is parsed from the formatted string 
	 * 		   received from the parameter.
	 */
	private LocalDate toDate(String sD) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
		return LocalDate.parse(sD, formatter);
	}
	
	
	
	
}
