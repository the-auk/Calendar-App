package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**************************************************************
 * Calendar class.
 * 
 * The main class of the program. This is a JPanel object that 
 * holds all GUI components of the program. It is added to the 
 * main JFrame object in the main method. It also has main method 
 * to run the program. 
 * 
 * This Calendar panel has 2 children JPanels, left and right, which 
 * are created by getLeftPanel and getRightPanel methods, respectively.
 * These two children JPanels hold all GUI components of the program.
 * 
 * Functionality: 
 *	 - Initiate all components of the program and wire them up 
 *     together.	  
 *   - Create JPanel objects that holds GUI components, format 
 * 	   their layouts, add GUI components to these panel, then finally 
 *     add them to the main JPanel (Calendar object).
 *************************************************************/
public class Calendar extends JPanel {

	private static final long serialVersionUID = -2054425670598766858L;
	
	private StateMachine stateMachine;
	private Data data;
	
	
	private ViewPanel viewPanel;
	private CalendarStatus calendarStatus;
	private CalendarTable calendarTable;
	
	private BrowseButton bButton;
	private BrowseButton fButton;
	
	//Main method
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Calendar calendar = new Calendar();	
	    
		
		frame.add(calendar);
		
			
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a Calendar object. 
	 * 
	 * In this Constructor, most components of the program are initialized, 
	 * including a StateMachine, a CalendarStatus, a CalendarTable, a Data 
	 * model, two BrowseButtons to shift the ViewPanel back and forth in 
	 * time. 
	 * 
	 * All other components that are not mentioned here are initialized 
	 * on getLeftPanel and getRightPanel methods. Then, components are wired 
	 * up together as necessary. Finally, all GUI components are added to the 
	 * Calendar panel.
	 */
	public Calendar() {
		super();
		

		stateMachine = new StateMachine();
		
		data = new Data(stateMachine);
		
		
		calendarStatus = new CalendarStatus(stateMachine);
		calendarTable = new CalendarTable(stateMachine);
		
		EventPopUp eventPopUp = getEventPopUp();
		
		viewPanel = new ViewPanel(data, stateMachine, eventPopUp);
		
		stateMachine.attach(viewPanel);
		stateMachine.attach(calendarStatus);
		stateMachine.attach(data);
		
		bButton = new BrowseButton("<", BrowseButton.BACKWARD, stateMachine);
		fButton = new BrowseButton(">", BrowseButton.FORWARD, stateMachine);
		
		
		data.attach(viewPanel);
		
		setLayout(new BorderLayout(30,0));
		
		JPanel leftP = getLeftPanel();
		JPanel rightP = getRightPanel();
		
		add(leftP, BorderLayout.WEST);
		add(rightP, BorderLayout.CENTER);
		
	
	}
	
	
	/* 
	 * getEventPopUp method.
	 * ------
	 * @return an EventPopUp object that create a JPanel showing detail
	 * 		   of an Event.	  
	 */
	private EventPopUp getEventPopUp() {
		return new EventPopUp() {

			@Override
			public JPanel getPanel(Event event) {
				JPanel panel = new JPanel();
				
				panel.setLayout(new BorderLayout(0, 15));
				
				//Title
				JLabel title = new JLabel(event.getName(), SwingConstants.CENTER);
				title.setBackground(new Color(128, 191, 255));
				title.setOpaque(true);
				title.setFont(Constants.CALENDAR_lABEL_FONT);
								
				panel.add(title, BorderLayout.NORTH);
						
				
				//Detail
				JPanel dPanel = new JPanel(new GridLayout(1,2));
				
				dPanel.setBackground(new Color(204, 204, 204));
				
				JPanel dLPanel = new JPanel(new GridLayout(2,1, 0, 10));
				JPanel dRPanel = new JPanel(new GridLayout(2,1, 0, 10));
				
				JLabel timeL = new JLabel("TIME: ");
				JLabel dateL = new JLabel("DATE: ");
				dLPanel.add(timeL);
				dLPanel.add(dateL);
				

				JLabel timeD = new JLabel(event.getTimeInterval());
				
				String dateFormat = Event.DAY_OF_WEEK + ", " 
										+ Event.MONTH + " "
										+ Event.DAY_OF_MONTH + " "
										+ Event.YEAR;
													
				JLabel dateD = new JLabel(event.getFormattedTime(dateFormat));
						
				dRPanel.add(timeD);
				dRPanel.add(dateD);
				
				dPanel.add(dLPanel);
				dPanel.add(dRPanel);
				
				panel.add(dPanel, BorderLayout.CENTER);
				
				return panel;
			}
			
		};
	}

	/* 
	 * getLeftPanel method.
	 * ------
	 * @return a JPanel that will be added to the left of the Calendar panel.
	 * 		   This JPanel has BorderLayout with 2 lines and a CalendarTable object:
	 * 			  - Line 1 and line 2 are  JPanel objects created by getLine1Left
	 * 				and getLine2Left, respectively. Then both of them will be added
	 * 				to BorderLayout.NORTH position.
	 *            - The CalendarTable object is added below line 1 and line 2 
	 *              (BorderLayout.CENTER position).	  
	 */
	private JPanel getLeftPanel() {
		JPanel lP = new JPanel(new BorderLayout());
		

		lP.setBorder(new EmptyBorder(10,10,10,10));
		
		
		JPanel line1Left = getLine1Left();
		JPanel line2Left = getLine2Left();
		
		JPanel nP = new JPanel(new GridLayout(2,1));
		
		nP.add(line1Left);
		nP.add(line2Left);
		
		lP.add(nP, BorderLayout.NORTH);
		lP.add(calendarTable, BorderLayout.CENTER);
		
		return lP;
	}
	

	/* 
	 * getRightPanel method.
	 * ------
	 * @return a JPanel that will be added to the right of the Calendar panel. This 
	 *         JPanel has BorderLayout with:
	 *         		-	1 line of buttons on top 
	 *         		-	A CalendarStatus object right below 
	 *         		- 	A ViewPanel object with a scroller at the bottom.
	 */
	private JPanel getRightPanel() {
		JPanel rP = new JPanel(new BorderLayout(0,10));
		
		rP.setBorder(new EmptyBorder(10,10,10,10));
		
		
		JPanel nP = new JPanel(new GridLayout(2,1));
		
		
		
		JPanel line1Right = getLine1Right();
		
		
		int hCalTab = calendarTable.getSize().height;
		JScrollPane scroller = new JScrollPane(viewPanel);
		scroller.setPreferredSize(new Dimension(Constants.CALENDAR_STATUS_WIDTH, hCalTab));
		
		nP.add(line1Right);
		nP.add(calendarStatus);
		
		rP.add(nP, BorderLayout.NORTH);
		rP.add(scroller, BorderLayout.CENTER);
		
		
		return rP;
	}
	
	
	/* 
	 * getLine1Left method.
	 * ------
	 * @return a JPanel that will be added to the first line of the left panel. 
	 * 		   It has 3 buttons: 
	 *         	 -	TodayButton that switch the Calendar to the current 
	 *         		day/week/month/period depends on the currentView attribute
	 *              of the StateMachine.
	 *           -  Backward and Forward buttons that shift the current periods
	 *              back and forth in time. The length of the period will remain 
	 *              the same before and after shifting.
	 */
	private JPanel getLine1Left() {
		JPanel lineP = new JPanel(new BorderLayout(30,0));
		
		lineP.setBorder(new EmptyBorder(10,0,0,0));

		
		TodayButton tButton = new TodayButton(stateMachine);
		
		
		
		JPanel bP = new JPanel(new GridLayout(1,2));
		
		bP.add(bButton);
		bP.add(fButton);
		
		lineP.add(tButton, BorderLayout.WEST);
		lineP.add(bP, BorderLayout.EAST);
		
		
		return lineP;
	}
	
	/* 
	 * getLine2Left method.
	 * ------
	 * @return a JPanel that will be added to the second line of the left panel. 
	 * 		   It has only one buttons: the CreateButton that is used to creates 
	 * 		   new Event	 
	 */
	
	private JPanel getLine2Left() {
		JPanel lineP = new JPanel(new BorderLayout());
		
		lineP.setBorder(new EmptyBorder(10,0,0,0));
		
		CreateButton createB = new CreateButton(data);
		
		lineP.add(createB, BorderLayout.WEST);
		
		return lineP;
	}
	
	
	/* 
	 * getLine1Right method.
	 * ------
	 * @return a JPanel that will be added to the top of the right panel. 
	 * 		   It has 5 buttons:
	 * 			 - DayButton: switch currentView attribute of the StateMachine
	 * 			   to DAY_VIEW.
	 * 			 - WeekButton: switch currentView attribute of the StateMachine
	 * 			   to WEEK_VIEW.
	 * 			 - MonthButton: switch currentView attribute of the StateMachine
	 * 			   to MONTH_VIEW.
	 * 			 - AgendaButton: switch currentView attribute of the StateMachine
	 * 			   to AGENDA_VIEW,then ask user to specify the exact period of time
	 * 			   to display. 
	 * 			 - FromFileButton: load a text file that contains regular events
	 * 			   to the calendar.				 
	 */
	private JPanel getLine1Right() {
		JPanel lineP = new JPanel(new BorderLayout(30,0));
		
		lineP.setBorder(new EmptyBorder(10,0,10, 0));
		
		DayButton dB = new DayButton(stateMachine);
		WeekButton wB = new WeekButton(stateMachine);
		MonthButton mB = new MonthButton(stateMachine);
		AgendaButton aB = new AgendaButton(stateMachine);
		
		FromFileButton fB = new FromFileButton(data);
		
		JPanel tP = new JPanel(new GridLayout(1,4));
		
		tP.add(dB);
		tP.add(wB);
		tP.add(mB);
		tP.add(aB);
		
		lineP.add(tP, BorderLayout.WEST);
		lineP.add(fB, BorderLayout.EAST);
		
		return lineP;
	}
	
}
