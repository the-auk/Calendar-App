package project;
import java.time.LocalDate;

/**************************************************************
 * ChangeTime interface.
 * 
 *  This interface will be implemented by a JComponent so that 
 *  this component will be able to change the current period 
 *  specified in the StateMachine.
 *************************************************************/
public interface ChangeTime {
	public static final int BACKWARD = -1;
	public static final int SPECIFIC_DATE = 0;
	public static final int FORWARD = 1;
	
	/* 
	 * getDirection method.
	 * ------
	 * getDirection method will be called by the updateState method of StateMachine and the
	 * return value will be passed to shiftCurrentPeriod method so that the current state of 
	 * the StateMachine will be set accordingly.
	 * 
	 * @return an integer whose value is equal to one of the three constants defined
	 * 		   in ChangeTime interface: BACKWARD, SPECIFIC_DATE, and FORWARD
	 */
	public int getDirection();
	
	/* 
	 * getDate method
	 * ------
	 * @return A LocalDate object specifies the day that this cell
	 * 		   represents. It is used in the StateMachine to set its 
	 * 		   current period. 
	 */
	public LocalDate getDate();
	
}
