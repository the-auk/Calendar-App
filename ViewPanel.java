package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*************************************************************
 * ViewPanel class.
 * 
 * A JPanel that displays list of Events depends on the current
 * state of the StateMachine. It implements ChangeListener to 
 * listen to any change in StateMachine.
 * 
 * This JPanel is the View part of the MVC pattern.
 *************************************************************/

public class ViewPanel extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 7007726725143511365L;
	
	
	
	private static final int GAP = 2;
	
	private static final String TIME_FORMAT_WITH_DOW 
									= Event.DAY_OF_WEEK + " - "
										+ Event.MONTH + " "
										+ Event.DAY_OF_MONTH + ", "
										+ Event.YEAR;
	private static final String TIME_FORMAT_WITHOUT_DOW 
									= 	  Event.MONTH + " "
										+ Event.DAY_OF_MONTH + ", "
										+ Event.YEAR;
		
	private Data data;
	private EventPopUp eventPopUp;
	
	private int width;
	
	
	private StateMachine stateMachine;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a ViewPanel object: 
	 * 		- Set the ViewPanel to BoxLayout so that it can show the
	 * 		  list of event vertically.
	 * 		- Set background color to white
	 * 		- Get the current list of events from the Data object.
	 * 		- Generate the content of the ViewPanel based on the
	 * 		  list of events.
	 * 
	 * @param d the main Data object that stores all events of the program.
	 * @param sm the StateMachine that keeps track of the current state of
	 * 			 the program. 
	 */
	public ViewPanel(Data d, StateMachine sm, EventPopUp eP) {
		super(); 
		
		data = d;
		eventPopUp = eP;
		
		width = Constants.VIEW_PANEL_WIDTH;
		
		
		stateMachine = sm;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		
		
		Event [] evs = data.getEvents();
	
		
		setContent(evs);
			
	}

	/* 
	 * stateChanged method.
	 * ------
	 * This method is from the ChangeListener interface. It will be called
	 * from the StateMachine when there is any change. When being called, it
	 * will get the current set of events from the Data object and use it to
	 * reset the content of the ViewPanel.
	 * 
	 * @param e a ChangeEvent emitted by the StateMachine.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Event [] evs = data.getEvents();
				
		setContent(evs);
		
	}
	
	
	/* 
	 * setContent method.
	 * ------
	 * This method gets an array of Event objects as a parameter and then
	 * sets the content of the ViewPanel to list all events represented by
	 * these objects.
	 * 
	 * @param evs an array of Events. These Events will be displays in the
	 * 			  ViewPanel.
	 */
	private void setContent(Event [] evs) {
		removeAll();
		revalidate();
		

		
		if(evs[0] == null) {
			JLabel lb = new JLabel("No event scheduled during this period");
			add(lb);
			return;
		}
		
		
		int currentView = stateMachine.getCurrentView();
		
		LocalDate currentDate = evs[0].getDate();
		
		String timeFormat = (currentView == StateMachine.WEEK_VIEW)?
								TIME_FORMAT_WITH_DOW :
								TIME_FORMAT_WITHOUT_DOW;
		
		
		if(currentView != StateMachine.DAY_VIEW) {
			addTimeTab(evs[0], timeFormat);
			add(Box.createVerticalStrut(2));
		}
		
		

		for(int i = 0; i < evs.length; i++) {
			if(currentView != StateMachine.DAY_VIEW && 
					!currentDate.isEqual(evs[i].getDate())) {
				
				currentDate = evs[i].getDate();
			
				addTimeTab(evs[i], timeFormat);
				add(Box.createVerticalStrut(2));
			}
			
			addEventTab(evs[i], i);
		}
		
		add(Box.createVerticalStrut(20));

		repaint();
	}
	

	/* 
	 * addEventTab method.
	 * ------
	 * This method create an EventTab from an Event object.
	 * 
	 * @param e an Event that is used to create an EventTab.
	 * @param id an integer specifies the index of the EventTab
	 * 			 in the ViewPanel. It is used to determine the
	 * 			 background color of the EventTab (based on whether
	 * 			 its index is even or odd).
	 */
	private void addEventTab(Event e, int id) {
		EventTab eT = new EventTab(e, width, id);
		add(eT);
				
	}
	
	/* 
	 * addTimeTab method.
	 * ------
	 * This method create a TimeTab - a JPanel showing time in a specified 
	 * format.
	 * 
	 * @param e      an Event whose time information is used to create the TimeTab.
	 * @param format the string specifies the format of the string in the TimeTab.
	 */
	private void addTimeTab(Event e, String format) {
		add(Box.createVerticalStrut(15));
		
		JPanel tPanel = new JPanel();
		
		tPanel.setMaximumSize(new Dimension(width, Constants.TAB_HEIGHT));
		tPanel.setLayout(new BorderLayout(GAP, GAP));
		
		JLabel tLabel = new JLabel(e.getFormattedTime(format));
		tLabel.setMaximumSize(new Dimension(width/2, Constants.TAB_HEIGHT));
		tLabel.setFont(Constants.CALENDAR_TIME_TAB_FONT);
		
		tLabel.setBorder(new EmptyBorder(0,10, 0, 0));
		tPanel.add(tLabel, BorderLayout.WEST);
		
		add(tPanel);
		
		
	}
	
	
	/*************************************************************
	 * EventTab class.
	 * 
	 * A JPanel that displays information of one Event.
	 * This EventTab is a 1-row-table with the left cell showing the 
	 * time interval of the event and the right cell showing the right 
	 * interval of the event.  
	 *************************************************************/
 	private class EventTab extends JPanel {
		
		private static final long serialVersionUID = -1218632455876253400L;
		
		private Event event;
		
		/* 
		 * Constructor method.
		 * ------
		 * Create an EventTab:
		 * 		- Set the layout to BorderLayout.
		 * 		- Create and add the JLabel showing time interval to BorderLayout.WEST.
		 * 		- Create and add the JLabel showing time interval to BorderLayout.CENTER.
		 * 		- Set the background color of the EventTab depending of whether its index
		 * 		  is even or odd.
		 * 		- Set other alignment and outlook properties.
		 * 
		 * @param e an Event object that is used to create the EventTab
		 * @param width an integer specifies the width of the EventTab.
		 * @param id an integer specifies the index order of the EventTab
		 * 			 in the ViewPanel.
		 */
		public EventTab(Event e, int width, int id) {
			super();
			
			event = e;
			
			String time = e.getTimeInterval();
			String name = e.getName();
			
			setMaximumSize(new Dimension(width, Constants.TAB_HEIGHT));
			
			setLayout(new BorderLayout(GAP,GAP));
			
			JLabel tLabel = new JLabel(time);
			JLabel nLabel = new JLabel(name);
			
			Color lColor;
			
			if(id%2 == 0) {
				lColor = Constants.EVEN_EVENT_TAB_COLOR;
			}
			else {
				lColor = Constants.ODD_EVENT_TAB_COLOR;
			}
			
			tLabel.setOpaque(true);
			nLabel.setOpaque(true);
			
			tLabel.setBackground(lColor);
			nLabel.setBackground(lColor);
			
			nLabel.setBorder(new EmptyBorder(0,10, 0, 0));
			tLabel.setBorder(new EmptyBorder(0,10, 0, 0));
			
			tLabel.setPreferredSize(new Dimension(width/3, 30));
			
			
			add(tLabel, BorderLayout.WEST);
			add(nLabel, BorderLayout.CENTER);
			
			
			setBackground(Color.WHITE);
			
			addMouseListener(getMouseListener());
		}

		/* 
		 * getMouseListener method.
		 * ------
		 * Create a MouseListener that will listen to the click on this
		 * EventTab and then show the pop-up window that has detail of
		 * the event corresponding to this EventTab.
		 * 
		 * @return a MouseListener to be added to this EventTab. 
		 */
		private MouseListener getMouseListener() {
			MouseListener ml = new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent me) {
					JPanel popup = eventPopUp.getPanel(event);
					
					JOptionPane.showMessageDialog(null, popup, "Event Detail", 
									JOptionPane.PLAIN_MESSAGE);
					
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {}

				@Override
				public void mouseExited(MouseEvent e) {}
				
			};
			
			return ml;
		}
		
		
		
	}


	
}
