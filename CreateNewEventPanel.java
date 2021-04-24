package project;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**************************************************************
 * CreateNewEventPanel class.
 * 
 * This is a JPanel that is used to ask user for information to 
 * create a new event. The information to be asked includes:
 * 		- Event's date
 * 		- The time event starts
 * 		- The time event ends
 * 		- Name of the event.    
 *************************************************************/
public class CreateNewEventPanel  extends JPanel{
	
	private static final long serialVersionUID = -1062031996303936552L;

	public static final int OK = JOptionPane.OK_OPTION;
	public static final int CANCEL = JOptionPane.CANCEL_OPTION;
	
	private JTextField dateTF;
	private JComboBox<Integer> fromHoursCB;
	private JComboBox<Integer> toHoursCB;
	private JTextField nameTF;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a JPanel that has 4 JTextFields to ask for the Event's date,
	 * the time event starts, the time event ends, and name of the event.
	 * This panel is set to BorderLayout with the dataPanel that stores
	 * 4 JTextFields is at CENTER position and the labelPanel that stores
	 * 4 JLabels is at the WEST position. 
	 */
	public CreateNewEventPanel() {
		super();
		
		setBorder(new EmptyBorder(10,10,10,10));
		
		setLayout(new BorderLayout(30, 0));
		
		JPanel dPanel = getDataPanel();
		JPanel lPanel = getLabelPanel();
		
		add(dPanel, BorderLayout.CENTER);;
		add(lPanel, BorderLayout.WEST);
	
	}
	
	/* 
	 * getDate method.
	 * ------
	 * This method gets the text from the date JTextField, checks if it
	 * follows the form "mm/dd/yyyy", and then parses it into a LocalDate 
	 * object.
	 * 
	 * @return LocalDate object specifies the date that was entered by user
	 * 		   in the date JTextField. 
	 */
	public LocalDate getDate() throws Exception {
		String dStr = dateTF.getText();
	
		String dateRegex = "^((1[0-2])|(0*[1-9]))/(([0-2]*[0-9])|(3[0-1]))/((20[1-9]9)|(20[2-9][0-9]))$";
		
		if(!Pattern.matches(dateRegex, dStr)) {
			throw new Exception("Incorrect date format");
			
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			
		return LocalDate.parse(dStr, formatter);
		
	
	}
	
	/* 
	 * getTimeInterval method.
	 * ------
	 * This method gets the text from two time JTextFields and checks if 
	 * the start time is before the end time. If it is, both are used to
	 * create a TimeInterval object.

	 * @return TimeInterval object that specifies the period bounded by 
	 * 		   the dates entered by user in the date JTextFields. 
	 */
	public TimeInterval getTimeInterval() throws Exception {
		Integer fromHour = (Integer)fromHoursCB.getSelectedItem();
		Integer toHour = (Integer)toHoursCB.getSelectedItem();
		
		if(toHour <= fromHour) throw new Exception("Invalid Time Interval. From(hour) must be earlier than To(hour)");
		
		return new TimeInterval(fromHour + ":00", toHour + ":00");
	}
	
	/* 
	 * getEventName method.
	 * ------
	 * Get the text entered by user in the name JTextField.
	 * @return a string specifies the event's name. 
	 */
	public String getEventName() throws Exception {
		if(nameTF.getText().length() == 0 ||
				nameTF.getText().contentEquals("Event name")) throw new Exception("Event Name must be entered.");
		
		return nameTF.getText();
	}
	
	/* 
	 * getDataPanel method.
	 * ------
	 * @return a JPanel that holds 4 JTextFields for: date of the event,
	 * the time event starts and ends, and name of the event.
	 */
	private JPanel getDataPanel() {
		JPanel dP = new JPanel(new GridLayout(4, 1, 0, 10));

		dateTF = new JTextField(20);
		dateTF.setText("mm/dd/yyyy");
		
		dateTF.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				dateTF.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
		});
		
		
		Integer [] hourI = new Integer[24];
		for(int i = 0;  i < hourI.length; i++) {
			hourI[i] = i;
		}
		
		fromHoursCB = new JComboBox<Integer>(hourI);
		toHoursCB = new JComboBox<Integer>(hourI);
		
		nameTF = new JTextField(20);
		nameTF.setText("Event name");
		
		nameTF.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				nameTF.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
		});
		
		dP.add(dateTF);
		dP.add(fromHoursCB);
		dP.add(toHoursCB);
		dP.add(nameTF);
		
		
		return dP;
	}
	
	/* 
	 * getLabelPanel method.
	 * ------
	 * @return a JPanel that holds 4 JLabels for: date of the event,
	 * the time event starts and ends, and name of the event.
	 */
	private JPanel getLabelPanel() {
		JPanel lP = new JPanel(new GridLayout(4, 1, 0, 10));
		
		//JPanel lP = new JPanel(new BorderLayout(0, 10));
		
		JLabel lDate = new JLabel("DATE: ");
		JLabel lFHour = new JLabel("FROM (hour): ");
		JLabel lTHour = new JLabel("TO (hour): ");
		JLabel lName = new JLabel("NAME: ");
		
		lP.add(lDate);
		lP.add(lFHour);
		lP.add(lTHour);
		lP.add(lName);
		
		/*
		 * lP.add(lDate, BorderLayout.NORTH);
		lP.add(lHour, BorderLayout.CENTER);
		lP.add(lName, BorderLayout.SOUTH);
		*/
		return lP;
	}
	
	/*
	 * ask method.
	 * ------
	 * This method will create a Dialog window, then add CreateNewEventPanel to this
	 * window. 
	 * 
	 * @return an integer whose value is determined by whether the user clicks
	 * 		   on OK or CANCEL button. 
	 * 				- If the user clicks on OK button, the returned value will 
	 * 				  be equal to the constant OK defined in this class.
	 * 				- If the user clicks on CANCEL button, the returned value will 
	 * 				  be equal to the constant CANCEL defined in this class.
	 */
	public int ask() {
		int result = JOptionPane.showConfirmDialog(null, this,
		        "Create New Event", JOptionPane.OK_CANCEL_OPTION);
		 
				
		return result;
	}
	
}
