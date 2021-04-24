package project;
/**************************************************************
 * TimeInterval class.
 * 
 * Represent a time interval in hour unit. It implements
 * Comparable<TimeInterval> interface so that two TimeInterval
 * object can be compared to know whether one is before the other
 * or there is a conflict between the two. 
 *************************************************************/
/*
 * TimeInterval class. 
 */
public class TimeInterval implements Comparable<TimeInterval> {
	private String startTime;
	private String endTime;
	
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a TimeInterval object
	 * 
	 * @param sT a string contains starting time in format hh:mm
	 * @param eT a string contains ending time in format hh:mm
	 */
	public TimeInterval(String sT, String eT) {
		startTime = sT;
		endTime = eT;
	}
	
	/*
	 * getStartTime method.
	 * ------
	 * An accessor that returns a string showing starting time
	 * 
	 * @return a string showing starting time in format hh:mm 
	 */
	public String getStartTime() {
		return startTime;
	}

	/*
	 * getEndTime method.
	 * ------
	 * An accessor that returns a string showing ending time
	 * 
	 * @return a string showing ending time in format hh:mm 
	 */
	public String getEndTime() {
		return endTime;
	}
	

	/*
	 * compareTo method.
	 * ------
	 * Compare this time interval with other time interval.
	 * 
	 * @param t the other TimeInterval object to be compared to this time interval
	 * 
	 * @return 1 if this time interval is after t
	 *          -1 if this time interval is before t
	 *          0 if there is a conflict between the two
	 */
	@Override
	public int compareTo(TimeInterval tI) {
	
		if(toInt(endTime) <= toInt(tI.startTime)) return -1;
		
		if(toInt(startTime) >= toInt(tI.endTime)) return 1;
				
		return 0;
	}
	
	/*
	 * toString method.
	 * ------
	 * Convert this TimeInterval into a string.
	 * 
	 * @return a string represents this TimeInterval in the format hh:mm - hh:mm
	 */
	public String toString() {
		return startTime + " - " + endTime;
	}

	
	/*
	 * toInt method.
	 * ------
	 * Translate a string represents time to an integer.
	 * 
	 * @param timeS a string represents time
	 * 
	 * @return an integer whose value is the minutes from midnight 
	 * 		   to the point in time that timeS represents.
	 */
	private int toInt(String timeS) {
		String [] timeSArr = timeS.split(":");
				
		return Integer.parseInt(timeSArr[0])*60 + Integer.parseInt(timeSArr[1]);
	}
	
	

}
