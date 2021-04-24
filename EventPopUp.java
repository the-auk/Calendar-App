import javax.swing.JPanel;

/**************************************************************
 * EventPopUp interface.
 *	
 * This interface for a strategy class in Strategy Pattern 
 * An EventPopUp class will be added to a ViewPanel object
 * via its constructor.
 * 
 * Functionality:	Create and return a JPanel that will be
 * 					shown in a pop up window when user clicks
 * 					on an EventTab in ViewPanel.
 *************************************************************/
public interface EventPopUp {
	
	/* 
	 * getPanel method.
	 * ------
	 * @param event an Event object corresponding to the EventTab that
	 * 				user click on. 			    
	 * 				
	 * @return a JPanel that will show up when user clicks on an EventTab in
	 * 		   ViewPanel.	  
	 */
	public JPanel getPanel(Event event);

}
