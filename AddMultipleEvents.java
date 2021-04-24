package project;
import java.io.FileNotFoundException;

/**************************************************************
 * AddMultipleEvents interface.
 *	
 * This interface will be implements by a JButton to add a series
 * of events to the EventList. When being pressed, this AddMultipleEvents 
 * JButton will emit a MouseEvent to StateMachine. From there, the 
 * getEvents method of this JButton will be called to return an array
 * of Events to add to the EventList. 
 *************************************************************/
public interface AddMultipleEvents {
	
	/*
	 * getEvents method.
	 * ------
	 * Since this interface is implemented by the "From File" button
	 * that involves opening a file, the FileNotFoundException is thrown.
	 * 
	 * @return an array of Event objects. 
	 */
	public Event [] getEvents() throws FileNotFoundException;
}
