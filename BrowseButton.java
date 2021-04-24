package project;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;

/*************************************************************
 * BrowseButton class.
 * 
 * A JButton that implements ChangeTime interface. 
 * This JButton is the Control part of the MVC pattern.
 * 
 * Functionality: 
 *		When being clicked, this ChangeTime JButton will shift
 *		the period specified in StateMachine forward and
 *		backward in time while its length remains the same.
 *************************************************************/
public class BrowseButton extends JButton implements ChangeTime {
	private static final long serialVersionUID = -7355119209159248906L;
	public static final int BACKWARD = 11111;
	public static final int FORWARD = 22222;
	
	private int direction;
	
	/* 
	 * Constructor method.
	 * ------
	 * Create a BrowseButton object
	 * 
	 * @param label a string that will be display as the label of the button
	 * @param d an integer specifies the direction that the period in StateMachine
	 * 			will be shifted to after this Button being clicked. 
	 * @param a an ActionListener that will be activated when this button is pressed.
	 * 			It is actually the StateMachine that implements ActionListener
	 * 			interface. The StateMachine will listen to the click on this button
	 * 			and change its current specified period in the direction specified by
	 * 			the value of the second parameter of this constructor.
	 */
	public BrowseButton(String label, int d, ActionListener a)  {
		super(label);
		
		addActionListener(a);

		direction = d;
		
	}

	/*
	 * getDirection method.
	 * ------
	 * This method is from the ChangeTime interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * @return an integer. The value of this integer depends on the value
	 * 		   of the attribute direction of this BrowseButton (which is also
	 * 		   an integer). In particular:
	 * 				- If direction is equal to the constant BACKWARD defined
	 * 				  in this class, the returned value will be equal to the
	 * 				  constant BACKWARD defined in the ChangeTime interface.
	 *				- If direction is equal to the constant FORWARD defined
	 * 				  in this class, the returned value will be equal to the
	 * 				  constant FORWARD defined in the ChangeTime interface.
	 */
	@Override
	public int getDirection() {
		if(direction == BACKWARD) {
			return ChangeTime.BACKWARD;
		}
		
		if(direction == FORWARD) {
			return ChangeTime.FORWARD;
		}
		
		return 0;
	}

	
	/*
	 * getDate method.
	 * ------
	 * This method is from the ChangeTime interface. It will be called 
	 * when the StateMachine (which is also an ActionListener) receives 
	 * a MouseEvent from this button.  
	 * 
	 * @return a LocalDate object specified today's date. Since TodayButton
	 * 		   also implements ChangeTime interface, this method is specifically
	 * 		   used by TodayButton to send a LocalDate object to the StateMachine
	 * 		   so that it can set its period to the current period. In all other
	 * 		   ChangeTime buttons, including this one, getDate method also returns
	 * 		   today's date but has no actual usage. 	
	 */
	@Override
	public LocalDate getDate() {
		return LocalDate.now();
	}


}
