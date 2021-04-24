package project;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**************************************************************
 * Data class.
 * 
 * This class shares with the Data class the role of data model 
 * of the program. It holds an EventList that stores all events 
 * of the program and an ArrayList of ChangeListeners that will
 * be activated when there's a change in the Data. It also has
 * 2 LocalDate attributes to specifies the period in which all
 * events will be shown in the ViewPanel. It implements 
 * ChangeListener interface to listen to a change in StateMachine
 * and ActionListener interface to listen to a click on CreateButton
 * and FromFileButton.
 * 
 * The Data class is the Model part of the MVC pattern.
 *************************************************************/

public class Data implements ChangeListener, ActionListener {
	private EventList eventList;
	private LocalDate firstDay;
	private LocalDate lastDay;
	
	private StateMachine stateMachine;
	
	private ArrayList<ChangeListener> listeners;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a Data object. 
	 * The eventList attribute is initialized. The firstDay and lastDay attributes 
	 * are set to the currentFirstDay and currentLastDay of the StateMachine. Then,
	 * the ArrayList of ChangeListeners is initialized.
	 */
	public Data(StateMachine sm) { 
		eventList = new EventList();
		
		stateMachine = sm;
		 
		firstDay = stateMachine.getFirstDay();
		lastDay = stateMachine.getLastDay();
		
				
		listeners = new ArrayList<ChangeListener>();
	}
	

	/* 
	 * attach method.
	 * ------
	 * Attach a ChangeListener to the model.
	 * @param l a ChangeListener object to be attached to the model. 
	 * 			This object will listen if there is a change in data 
	 *          and act upon it
	 */
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
	

	/* 
	 * addEvent method.
	 * ------
	 * Add an event to the data model.
	 * @param e an Event object to be added to the data
	 * @return a boolean value. True if the adding success. False otherwise
	 */
	public boolean addEvent(Event e) {
		
		boolean result = eventList.addEvent(e);
		
		
		return result;
	}
	

	/* 
	 * addEvents method.
	 * ------
	 * Add multiple events to the data model.
	 * @param events an array of Event objects to be added to the data
	 */
	private void addEvents(Event[] events) {
		if(events == null) return;
		
		for(int i = 0; i < events.length; i++) {
			eventList.addEvent(events[i]);
		}
			
	}
	

	/* 
	 * getEvent method.
	 * ------
	 * Get all events that has been scheduled on the period specifies by 
	 * startDate and endDate
	 * @return an arrays that has all events scheduled on the period specifies 
	 * by startDate and endDate
	 */
	public Event[] getEvents(){				
		return eventList.getEvents(firstDay, lastDay).toArray(new Event[1]);
	}
	
	
	
	/* 
	 * stateChanged method.
	 * ------
	 * Notify all listeners whenever there is a change in data
	 * @param e a ChangeEvent emitted by StateMachine.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		firstDay = stateMachine.getFirstDay();	
		lastDay = stateMachine.getLastDay();
		
		updateListeners();		
	}

	
	
	/* 
	 * actionPerformed method.
	 * ------
	 * Override method when implements ActionListener. 
	 * It will be called when adding an event or multiple events to
	 * the data.
	 * @param e an ActionEvent emitted by either AddOneEvent 
	 * 			or AddMultipleEvents buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof AddOneEvent) {
			AddOneEvent aoe = (AddOneEvent)e.getSource();
			Event event = aoe.getEvent(); 
			
			if(event != null) {
				addEvent(event);
			}
			
		}
		
		if(e.getSource() instanceof AddMultipleEvents) {
			AddMultipleEvents aoe = (AddMultipleEvents)e.getSource();
			try {
				addEvents(aoe.getEvents());
			} catch (FileNotFoundException e1) {
				return;
			}
		}
		
		updateListeners();
	}
	
	

	/* 
	 * actionPerformed method.
	 * ------
	 * Notify all ChangeListeners when there is a change in the Data
	 */
	private void updateListeners() {
		for(int i = 0; i < listeners.size(); i++) {
			listeners.get(i).stateChanged(new ChangeEvent(this));
		}
	}
}
