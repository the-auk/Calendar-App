package project;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/*************************************************************
 * AgendaPanel class.
 * 
 * A JPanel that has 2 TextFields: fromTF and toTF. 
 * 
 * Functionality: 
 *		This panel prompts user to specify the first and last 
 *		date of the period they want to show on the ViewPanel.
 *************************************************************/
public class AgendaPanel extends JPanel {
	private static final long serialVersionUID = -8266636930669146622L;

	public static final int TO_DATE = 11112;
	public static final int FROM_DATE = 22221;
	
	public static final int OK = JOptionPane.OK_OPTION;
	public static final int CANCEL = JOptionPane.CANCEL_OPTION;
	
	private JTextField fromTF;
	private JTextField toTF;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create an AgendaPanel object.
	 * 
	 * This Panel is set to BorderLayout with 2 main JPanels. The data 
	 * panel (dPanel) is created by getDataPanel method. Then, it will be 
	 * added to the CENTER position. The label panel (lPanel) is created
	 * by getLabelPanel method. Then, it will be added to the WEST position.
	 */
	public AgendaPanel() {
		super();
		
		setBorder(new EmptyBorder(10,10,10,10));
		
		setLayout(new BorderLayout(30,0));
		
		JPanel dPanel = getDataPanel();
		JPanel lPanel = getLabelPanel();
		
		add(dPanel, BorderLayout.CENTER);;
		add(lPanel, BorderLayout.WEST);
	}
	
	/*
	 * getDate method.
	 * ------
	 * When being called, this method will get the string from the JTextField
	 * parameter passed to it (dateTF). This string is the date that user 
	 * is supposed to enter in the format "mm/dd/yyyy". Thus, the string will
	 * be checked if it follows the specified format. If it does, the string
	 * is parsed into a LocalDate object and then returned by this method.
	 * Otherwise, an Exception with the message "Incorrect date format" is thrown.
	 * 
	 * @return a LocalDate object specified by the string in the JTextField 
	 * 		   parameter passed to this method.
	 */
	private LocalDate getDate(JTextField dateTF) throws Exception {
		String dStr = dateTF.getText();
	
		String dateRegex = "^((1[0-2])|(0*[1-9]))/(([0-2]*[0-9])|(3[0-1]))/((20[1-9]9)|(20[2-9][0-9]))$";
		
		if(!Pattern.matches(dateRegex, dStr)) {
			throw new Exception("Incorrect date format");
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			
		return LocalDate.parse(dStr, formatter);
	
	}
	
	/*
	 * getDate method.
	 * ------
	 * This is a public aggregated method of the private method 
	 * getDate(JTextField dateTF). This method gets an integer value as a 
	 * parameter. Then, depending on value of the parameter, it will call
	 * getDate(JTextField dateTF) passing either one of the two JTextField 
	 * attributes:toTF or fromTF accordingly.
	 * 
	 * @param textField an integer. This integer will be compared with
	 * 	 	  two constants TO_DATE and FROM_DATE defined in this class.
	 * 				- If textField is equal to TO_DATE, the private 
	 * 				  getDate method is called on the toTF JTextField attribute
	 * 				  to get the first date of the period specified by user.
	 * 				- If textField is equal to FROM_DATE, the private 
	 * 				  getDate method is called on the fromTF JTextField attribute
	 * 				  to get the last date of the period specified by user.
	 * 
	 * @return a LocalDate object. This object will specify either the first
	 * 		   date or the last date of the period entered by user depending
	 * 		   on the value of the textField parameter being passed to this
	 * 		   method.
	 */
	public LocalDate getDate(int textField) throws Exception {
		if(textField == TO_DATE) return getDate(toTF);
		
		if(textField == FROM_DATE) return getDate(fromTF);
		
		return LocalDate.now();
		
	}
	

	/*
	 * getDataPanel method.
	 * ------
	 * This method will create a JPanel that has 2 JTextField: fromTF and
	 * toTF. The first one asks user to enter the first date of the period
	 * they want to show on the ViewPanel. The second one asks user to enter
	 * the last date of the period they want to show on the ViewPanel.
	 * 
	 * @return a JPanel object. This JPanel will be add to the BorderLayout.CENTER
	 * 		   of the AgendaPanel.
	 */
	public JPanel getDataPanel() {
		JPanel dP = new JPanel(new BorderLayout(0,10));
		
		fromTF = new JTextField(20);
		fromTF.setText("mm/dd/yyyy");
		
		fromTF.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				fromTF.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		toTF = new JTextField(20);
		toTF.setText("mm/dd/yyyy");
		
		toTF.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				toTF.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		dP.add(fromTF, BorderLayout.NORTH);
		dP.add(toTF, BorderLayout.CENTER);
		
		return dP;
	}

	/*
	 * getLabelPanel method.
	 * ------
	 * This method will create a JPanel that has 2 JLabel: from and
	 * to. The from JLabel is set to show the string "FROM". The to 
	 * JLabel is set to show the string "TO".
	 * 
	 * 
	 * @return a JPanel object. This JPanel will be add to the BorderLayout.WEST
	 * 		   of the AgendaPanel.
	 */
	private JPanel getLabelPanel() {
		JPanel lP = new JPanel(new BorderLayout(0, 10));
		
		JLabel from = new JLabel("FROM: ");
		JLabel to = new JLabel("TO: ");
		
		lP.add(from, BorderLayout.NORTH);
		lP.add(to, BorderLayout.CENTER);
		
		return lP;
	}
	

	/*
	 * ask method.
	 * ------
	 * This method will create a Dialog window, then add AgendaPanel to this
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
		        "Agenda Time", JOptionPane.OK_CANCEL_OPTION);
		 
		return result;
	}
}
