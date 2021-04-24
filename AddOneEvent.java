package project;
/**************************************************************
 * AddOneEvents interface.
 *	
 * This interface will be implements by a JButton to add an event 
 * to the EventList. When being pressed, this AddOneEvent JButton 
 * will emit a MouseEvent to StateMachine. From there, the getEvent 
 * method of this JButton will be called to return an Event to add 
 * to the EventList. 
 *************************************************************/
public interface AddOneEvent {
	/*
	 * getEvent method.
	 * ------
	 * @return an Event object. 
	 */
	public Event getEvent();
}
